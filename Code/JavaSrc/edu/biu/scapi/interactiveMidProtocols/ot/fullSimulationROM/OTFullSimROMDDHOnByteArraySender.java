/**
* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
* 
* Copyright (c) 2012 - SCAPI (http://crypto.biu.ac.il/scapi)
* This file is part of the SCAPI project.
* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
* to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
* and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
* FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
* WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
* 
* We request that any publication and/or code referring to and/or based on SCAPI contain an appropriate citation to SCAPI, including a reference to
* http://crypto.biu.ac.il/SCAPI.
* 
* SCAPI uses Crypto++, Miracl, NTL and Bouncy Castle. Please see these projects for any further licensing issues.
* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
* 
*/
package edu.biu.scapi.interactiveMidProtocols.ot.fullSimulationROM;

import java.io.IOException;
import java.security.SecureRandom;

import edu.biu.scapi.exceptions.CheatAttemptException;
import edu.biu.scapi.exceptions.CommitValueException;
import edu.biu.scapi.exceptions.FactoriesException;
import edu.biu.scapi.exceptions.InvalidDlogGroupException;
import edu.biu.scapi.exceptions.SecurityLevelException;
import edu.biu.scapi.interactiveMidProtocols.ot.OTSInput;
import edu.biu.scapi.interactiveMidProtocols.ot.OTSMsg;
import edu.biu.scapi.interactiveMidProtocols.ot.OTSOnByteArrayInput;
import edu.biu.scapi.interactiveMidProtocols.ot.OTSOnByteArrayMsg;
import edu.biu.scapi.primitives.dlog.DlogGroup;
import edu.biu.scapi.primitives.dlog.GroupElement;
import edu.biu.scapi.primitives.kdf.KeyDerivationFunction;
import edu.biu.scapi.primitives.randomOracle.RandomOracle;
import edu.biu.scapi.securityLevel.Malicious;
import edu.biu.scapi.tools.Factories.KdfFactory;

/**
 * Concrete implementation of the sender side in oblivious transfer based on the DDH assumption that achieves full simulation in the random oracle model.
 * 
 * This class derived from OTFullSimROMDDHSenderAbs and implements the functionality 
 * related to the byte array inputs.
 * 
 * @author Cryptography and Computer Security Research Group Department of Computer Science Bar-Ilan University (Moriya Farbstein)
 *
 */
public class OTFullSimROMDDHOnByteArraySender extends OTFullSimROMDDHSenderAbs implements Malicious{
	
	private KeyDerivationFunction kdf; //Used in the calculation.
	
	/**
	 * Constructor that chooses default values of DlogGroup, kdf, randomOracle and SecureRandom.
	 * 
	 */
	public OTFullSimROMDDHOnByteArraySender() {
		super();
		try {
			this.kdf = KdfFactory.getInstance().getObject("HKDF(HMac(SHA-256))");
		} catch (FactoriesException e) {
			// will not occur since the given KDF name is valid.
		}
	}
	
	/**
	 * Constructor that sets the given , dlogGroup, kdf and random.
	 * @param dlog must be DDH secure.
	 * @param kdf
	 * @param ro random oracle
	 * @param random
	 * @throws SecurityLevelException if the given DlogGroup is not DDH secure.
	 * @throws InvalidDlogGroupException if the given dlog is invalid.
	 * @throws CheatAttemptException 
	 * @throws IOException if failed to receive a message during pre process.
	 * @throws ClassNotFoundException 
	 * @throws CommitValueException 
	 */
	public OTFullSimROMDDHOnByteArraySender(DlogGroup dlog, KeyDerivationFunction kdf, RandomOracle ro, SecureRandom random) throws SecurityLevelException, InvalidDlogGroupException, ClassNotFoundException, IOException, CheatAttemptException, CommitValueException{
		super(dlog, ro, random);
		this.kdf = kdf;
	}

	/**
	 * Runs the following lines from the protocol:
	 * "COMPUTE:
	 *		COMPUTE c0 = x0 XOR KDF(|x0|,v0)
	 *		COMPUTE c1 = x1 XOR KDF(|x1|,v1)"
	 * @param input must be a OTSOnByteArrayInput.
	 * @param u0
	 * @param u1
	 * @param v0
	 * @param v1
	 * @return tuple contains (u0, c0, u1, c1) to send to the receiver.
	 */
	protected OTSMsg computeTuple(OTSInput input, GroupElement u0, GroupElement u1, GroupElement v0, GroupElement v1) {
		//If input is not instance of OTSOnByteArrayInput, throw Exception.
		if (!(input instanceof OTSOnByteArrayInput)){
			throw new IllegalArgumentException("x0 and x1 should be binary strings.");
		}
		OTSOnByteArrayInput inputStrings = (OTSOnByteArrayInput)input;
		
		//If x0, x1 are not of the same length, throw Exception.
		if (inputStrings.getX0().length != inputStrings.getX1().length){
			throw new IllegalArgumentException("x0 and x1 should be of the same length.");
		}
		
		//Get x0, x1.
		byte[] x0 = inputStrings.getX0();
		byte[] x1 = inputStrings.getX1();
				
		//Calculate c0:
		byte[] v0Bytes = dlog.mapAnyGroupElementToByteArray(v0);
		int len = x0.length;
		byte[] c0 = kdf.deriveKey(v0Bytes, 0, v0Bytes.length, len).getEncoded();
		
		//Xores the result from the kdf with x0.
		for(int i=0; i<len; i++){
			c0[i] = (byte) (c0[i] ^ x0[i]);
		}
		
		//Calculate c1:
		byte[] v1Bytes = dlog.mapAnyGroupElementToByteArray(v1);
		byte[] c1 = kdf.deriveKey(v1Bytes, 0, v1Bytes.length, len).getEncoded();
		
		//Xores the result from the kdf with x1.
		for(int i=0; i<len; i++){
			c1[i] = (byte) (c1[i] ^ x1[i]);
		}
		
		//Create and return sender message.
		return new OTSOnByteArrayMsg(u0.generateSendableData(), c0, u1.generateSendableData(), c1);
	}

}