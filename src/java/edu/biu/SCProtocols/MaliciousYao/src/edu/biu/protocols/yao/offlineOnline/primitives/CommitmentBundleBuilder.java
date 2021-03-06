package edu.biu.protocols.yao.offlineOnline.primitives;

import java.security.SecureRandom;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import edu.biu.protocols.yao.common.KeyUtils;
import edu.biu.protocols.yao.primitives.CryptoPrimitives;
import edu.biu.scapi.comm.Channel;
import edu.biu.scapi.exceptions.CommitValueException;
import edu.biu.scapi.exceptions.InvalidInputException;
import edu.biu.scapi.interactiveMidProtocols.commitmentScheme.CmtCCommitmentMsg;
import edu.biu.scapi.interactiveMidProtocols.commitmentScheme.CmtCDecommitmentMessage;
import edu.biu.scapi.interactiveMidProtocols.commitmentScheme.CmtCommitValue;
import edu.biu.scapi.interactiveMidProtocols.commitmentScheme.CmtCommitter;
import edu.biu.scapi.interactiveMidProtocols.commitmentScheme.simpleHash.CmtSimpleHashCommitmentMessage;
import edu.biu.scapi.interactiveMidProtocols.commitmentScheme.simpleHash.CmtSimpleHashCommitter;
import edu.biu.scapi.interactiveMidProtocols.commitmentScheme.simpleHash.CmtSimpleHashDecommitmentMessage;
import edu.biu.scapi.primitives.hash.CryptographicHash;
import edu.biu.scapi.primitives.hash.cryptopp.CryptoPpSHA1;

/**
 * This class builds the CommitmentBundle. <p>
 * Unlike the CommitmentBundle class (that is just a struct that hold data), this class also has functionality that creates 
 * the inline members. <p>
 * 
 * It contains a build function that Xors keys, commit on the keys, etc.
 * 
 * @author Cryptography and Computer Security Research Group Department of Computer Science Bar-Ilan University (Asaf Cohen)
 *
 */
public class CommitmentBundleBuilder {
	private final CmtCommitter committer;		// The commitment object that used to commit on the keys.
	private int commitLabel;					// The current wire to commit on.
	private final int keyLength;				// The size of key, in bytes.

	/**
	 * A constructor that sets the given arguments.
	 * @param random Source of randomness.
	 * @param primitives Contains the primitives objects to use.
	 * @param channel Used to communicates between the parties.
	 * @param keyLength The size of each key, in bytes.
	 */
	public CommitmentBundleBuilder(SecureRandom random, CryptoPrimitives primitives, Channel channel, int keyLength) {
		//When using threads, the OpenSSLSHA1 causing problems. Thus, we use cryptoPPSHA1.
		CryptographicHash hash = new CryptoPpSHA1();//primitives.getCryptographicHash();
		this.keyLength = keyLength;
		//Create committer object.
		try {
			this.committer = new CmtSimpleHashCommitter(channel, hash, random, hash.getHashedMsgSize());
		} catch (Exception e) {
			// No matter what the exception is, it is unexpected and thus illegal.
			throw new IllegalStateException(e);
		}
		this.commitLabel = 0;
	}
	
	/**
	 * Builds the CommitmentBundle, using the given keys and masks.
	 * @param wires both keys of each wire.
	 * @param labels wires' indices.
	 * @param commitmentMask 
	 * @param placementMask
	 * @return the created CommitmentBundle.
	 */
	public CommitmentBundle build(byte[] wires, int[] labels, byte[] commitmentMask, byte[] placementMask) {
		int cmtSize = 20;
		int keySize = 16;
		byte[] commitments = new byte[labels.length * 2 * cmtSize];
		long[] commitmentIds = new long[labels.length * 2];
		byte[] decommitments = new byte[labels.length *2 *keySize];
		byte[] decommitmentsRandoms = new byte[labels.length *2 *cmtSize];
		
		// For each wire w (indexed with i)
		for (int i = 0; i < labels.length; i++) {
			SecretKey[] keys = new SecretKey[2];
			keys[0] = new SecretKeySpec(wires, i*keyLength*2, keyLength, "");
			keys[1] = new SecretKeySpec(wires, (i*2+1)*keyLength, keyLength, "");
			
			// Switch the keys according to mask[j]
			if (null != placementMask && 1 == placementMask[i]) {
				SecretKey temp = keys[0];
				keys[0] = keys[1];
				keys[1] = temp;
			}
			
			// Generate Com(K0), Com(K1), Decom(K0), Decom(K1) according to the ordering in B[j].
			for (int k = 0; k < keys.length; k++) {
				CmtCommitValue commitValue;
				SecretKey effectiveKey = keys[k];
				if (null != commitmentMask) {
					try {
						effectiveKey = KeyUtils.xorKeys(effectiveKey, new SecretKeySpec(commitmentMask, ""));
					} catch (InvalidInputException e) {
						throw new IllegalStateException(e);
					}
				}

				try {
					commitValue = committer.generateCommitValue(effectiveKey.getEncoded());

				} catch (CommitValueException e) {
					throw new IllegalStateException(e);
				}
				
				CmtCCommitmentMsg commitment = committer.generateCommitmentMsg(commitValue, commitLabel);
				CmtCDecommitmentMessage decommitment = committer.generateDecommitmentMsg(commitLabel);
				System.arraycopy(((CmtSimpleHashCommitmentMessage)commitment).getCommitment(), 0, commitments, i*2*cmtSize+k*cmtSize, cmtSize);
				commitmentIds[i*2+k] = ((CmtSimpleHashCommitmentMessage)commitment).getId();
				System.arraycopy(((CmtSimpleHashDecommitmentMessage)decommitment).getX(), 0, decommitments, i*2*keySize+k*keySize, keySize);
				System.arraycopy(((CmtSimpleHashDecommitmentMessage)decommitment).getR().getR(), 0, decommitmentsRandoms, i*2*cmtSize+k*cmtSize, cmtSize);
				
				commitLabel++;
			}
		}

		return new CommitmentBundle(commitments, commitmentIds, decommitments, decommitmentsRandoms);
	}
	
	/**
	 * Builds the CommitmentBundle, using the given keys and masks.
	 * @param wires both keys of each wire.
	 * @param labels wires' indices.
	 * @param commitmentMask 
	 * @return the created CommitmentBundle.
	 */
	public CommitmentBundle build(byte[] wires, int[] labels, byte[] commitmentMask) {
		//Call the other build function, with placementMask = null.
		return build(wires, labels, commitmentMask, null);
	}

	/**
	 * Builds the CommitmentBundle, using the given keys.
	 * @param wires both keys of each wire.
	 * @param labels wires' indices.
	 * @return the created CommitmentBundle.
	 */
	public CommitmentBundle build(byte[] wires, int[] labels) {
		//Call the other build function, with commitmentMask = null.
		return build(wires, labels, null);
	}
}
