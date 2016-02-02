#pragma once
#include "ZeroKnowledge.hpp"
#include "CommitmentSchemePedersen.hpp"

/**
* Concrete implementation of Zero Knowledge prover.<p>
* This is a transformation that takes any Sigma protocol and any perfectly hiding
* trapdoor(equivocal) commitment scheme and yields a zero - knowledge proof of knowledge.<p>
* For more information see Protocol 6.5.4, page 165 of Hazay - Lindell.<p>
* The pseudo code of this protocol can be found in Protocol 2.2 of pseudo codes
* document at{ @link http://cryptobiu.github.io/scapi/SDK_Pseudocode.pdf}.<p>
*/
class ZKPOKFromSigmaCmtPedersenProver : public ZKPOKProver {
public:
	/**
	* Constructor that accepts the underlying channel and sigma protocol's prover.
	* @param channel used for communication
	* @param sProver underlying sigma prover to use.
	*/
	ZKPOKFromSigmaCmtPedersenProver(ChannelServer* channel, SigmaProverComputation* sProver) {
		//this->sProver = sProver;
		//this->receiver = new CmtPedersenTrapdoorReceiver(channel);
		//this->channel = channel;
	}

	/**
	* Runs the prover side of the Zero Knowledge proof.<p>
	* Let (a,e,z) denote the prover1, verifier challenge and prover2 messages of the sigma protocol.<p>
	* This function computes the following calculations:<p>
	*
	*		 RUN the receiver in TRAP_COMMIT.commit; let trap be the output<p>
	* 		 COMPUTE the first message a in sigma, using (x,w) as input<p>
	*		 SEND a to V<p>
	*		 RUN the receiver in TRAP_COMMIT.decommit<p>
	*		 IF TRAP_COMMIT.decommit returns some e<p>
	*		      COMPUTE the response z to (a,e) according to sigma<p>
	*		      SEND z and trap to V<p>
	*		      OUTPUT nothing<p>
	*		 ELSE (IF COMMIT.decommit returns INVALID)<p>
	*			  OUTPUT ERROR (CHEAT_ATTEMPT_BY_V)<p>
	*
	* @param input must be an instance of SigmaProverInput.
	*/
	void prove(ZKProverInput* input);

	/**
	* Processes the second message of the Zero Knowledge protocol.<p>
	* 	"COMPUTE the response z to (a,e) according to sigma<p>
	*   SEND z to V<p>
	*   OUTPUT nothing".<p>
	* This is a blocking function!
	*/
	void processSecondMsg(byte* e, CmtRCommitPhaseOutput* trap);

private:
	ChannelServer* channel;
	// underlying prover that computes the proof of the sigma protocol.
	SigmaProverComputation* sProver;
//	CmtPedersenTrapdoorReceiver* receiver; // underlying Commitment receiver to use.

	/**
	* Runs the receiver in TRAP_COMMIT.commit with P as the receiver.
	*/
	CmtRCommitPhaseOutput* receiveCommit();

	/**
	* Processes the first message of the Zero Knowledge protocol:
	*  "COMPUTE the first message a in sigma, using (x,w) as input
	*	SEND a to V".
	* @param input
	*/
	void processFirstMsg(SigmaProverInput*  input) {
		// compute the first message by the underlying proverComputation.
		SigmaProtocolMsg* a = sProver->computeFirstMsg(input);
		byte* msg = a->toByteArray();
		int len = a->size();
		// send the first message.
		sendMsgToVerifier(msg, len);
	}

	/**
	* Runs the receiver in TRAP_COMMIT.decommit.
	* If decommit returns INVALID output ERROR (CHEAT_ATTEMPT_BY_V)
	* @param id
	* @param ctOutput
	* @return
	*/
	byte* receiveDecommit(long id);

	/**
	* Sends the given message to the verifier.
	* @param message to send to the verifier.
	*/
	void sendMsgToVerifier(byte* msg, int size) { channel->write_fast(msg, size); };
};


/**
* Concrete implementation of Zero Knowledge verifier.<p>
* This is a transformation that takes any Sigma protocol and any perfectly hiding trapdoor (equivocal)
* commitment scheme and yields a zero-knowledge proof of knowledge.<p>
* For more information see Protocol 6.5.4, page 165 of Hazay-Lindell.<p>
* The pseudo code of this protocol can be found in Protocol 2.2 of pseudo 
* codes document at {@link http://cryptobiu.github.io/scapi/SDK_Pseudocode.pdf}.<p>
*/
class ZKPOKFromSigmaCmtPedersenVerifier : public ZKPOKVerifier {
private:
	ChannelServer* channel;
	// underlying verifier that computes the proof of the sigma protocol.
	SigmaVerifierComputation* sVerifier;
//	CmtPedersenTrapdoorCommitter* committer; // underlying Commitment committer to use.
	std::mt19937 random;
	/**
	* Runs COMMIT.commit as the committer with input e.
	*/
	long commit(byte* e, int eSize);
	/**
	* Waits for a message a from the prover.
	* @return the received message
	*/
	SigmaProtocolMsg* receiveMsgFromProver();
	/**
	* Waits for a trapdoor a from the prover.
	* @return the received message
	*/
	CmtRCommitPhaseOutput* receiveTrapFromProver();
	/**
	* Verifies the proof.
	* @param input
	* @param a first message from prover.
	* @param z second message from prover.
	*/
	bool proccessVerify(SigmaCommonInput* input, SigmaProtocolMsg* a, SigmaProtocolMsg* z) {
		// run transcript (a, e, z) is accepting in sigma on input x
		return sVerifier->verify(input, a, z);
	};

public:
	/**
	* Constructor that accepts the underlying channel and sigma protocol's verifier.
	* @param channel used for communication
	* @param sVerifier underlying sigma verifier to use.
	* @param random
	*/
	ZKPOKFromSigmaCmtPedersenVerifier(ChannelServer* channel, SigmaVerifierComputation* sVerifier,
		std::mt19937 random) {
		//this->channel = channel;
		//this->sVerifier = sVerifier; 
		//this->committer = new CmtPedersenTrapdoorCommitter(channel); 
		//this->random = random;
	};

	/**
	* Runs the verifier side of the Zero Knowledge proof.<p>
	* Let (a,e,z) denote the prover1, verifier challenge and prover2 messages of the sigma protocol.<p>
	* This function computes the following calculations:<p>
	*
	*		 SAMPLE a random challenge  e <- {0, 1^t <p>
	*		 RUN TRAP_COMMIT.commit as the committer with input e<p>
	*		 WAIT for a message a from P<p>
	*		 RUN TRAP_COMMIT.decommit as the decommitter<p>
	*		 WAIT for a message (z,trap) from P<p>
	*		 IF  <p>
	*				TRAP_COMMIT.valid(T,trap) = 1, where T  is the transcript from the commit phase, AND<p>
	*				Transcript (a, e, z) is accepting in sigma on input x<p>
	*          OUTPUT ACC<p>
	*       ELSE 	<p>
	*          OUTPUT REJ<p>

	* @param input must be an instance of SigmaCommonInput.
	*/
	bool verify(ZKCommonInput* input) override;

};