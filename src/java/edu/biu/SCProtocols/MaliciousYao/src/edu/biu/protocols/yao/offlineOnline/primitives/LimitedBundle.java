package edu.biu.protocols.yao.offlineOnline.primitives;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import edu.biu.protocols.CommitmentWithZkProofOfDifference.DifferenceCommitmentReceiverBundle;
import edu.biu.protocols.yao.primitives.CircuitInput;
import edu.biu.scapi.circuits.garbledCircuit.GarbledTablesHolder;
import edu.biu.scapi.interactiveMidProtocols.commitmentScheme.CmtCCommitmentMsg;
import edu.biu.scapi.interactiveMidProtocols.commitmentScheme.CmtCDecommitmentMessage;
import edu.biu.scapi.interactiveMidProtocols.commitmentScheme.simpleHash.CmtSimpleHashCommitmentMessage;

/**
 * A bundle is a struct that holds a limited data regarding the protocol.  <p>
 * 
 * These parameters are the garbled table and translation table of the circuit, commitments on the keys and indices of the wires, 
 * inputs for the circuit, etc. <P>
 * 
 * The limited bundle is used by p2 during the offline and online phases of the protocol.
 * 
 * @author Cryptography and Computer Security Research Group Department of Computer Science Bar-Ilan University (Asaf Cohen)
 *
 */
public class LimitedBundle implements Serializable {
	private static final long serialVersionUID = 8986229088379999867L;

	/**
	 * This is an inner class that builds the LimitedBundle.
	 * 
	 * @author Cryptography and Computer Security Research Group Department of Computer Science Bar-Ilan University (Asaf Cohen)
	 *
	 */
	public static class Builder {
		
		private GarbledTablesHolder garbledTables;
		private byte[] translationTable;

		//wires' indices.
		private int[] inputLabelsX;
		private int[] inputLabelsY1Extended;
		private int[] inputLabelsY2;
		private int[] outputLabels;

		//Commitments on the keys.
		private CommitmentBundle commitmentsX;
		private CommitmentBundle commitmentsY1Extended;
		private CommitmentBundle commitmentsY2;
		private CmtCCommitmentMsg commitmentsOutput;
		private CmtCDecommitmentMessage decommitmentsOutput;
		private DifferenceCommitmentReceiverBundle diffCommitments;
		private String tablesFile;
		
		/**
		 * Sets the circuit parameters.
		 * @return The builder that contains these arguments.
		 */
		public Builder circuit(GarbledTablesHolder garbledTables, byte[] translationTable, String tablesFile) {
			this.garbledTables = garbledTables;
			this.translationTable = translationTable;
			this.tablesFile = tablesFile;
			return this;
		}
		
		/**
		 * Sets the wires' indices.
		 * @return The builder that contains these arguments.
		 */
		public Builder labels(int[] inputLabelsX, int[] inputLabelsY1Extended, int[] inputLabelsY2, int[] outputLabels) {
			this.inputLabelsX = inputLabelsX;
			this.inputLabelsY1Extended = inputLabelsY1Extended;
			this.inputLabelsY2 = inputLabelsY2;
			this.outputLabels = outputLabels;
			return this;
		}
		
		/**
		 * Sets the commitments on the keys.
		 * @return The builder that contains these arguments.
		 */
		public Builder commitments(CommitmentBundle commitmentsX, CommitmentBundle commitmentsY1Extended, CommitmentBundle commitmentsY2, 
				CmtCCommitmentMsg commitmentsOutput, CmtCDecommitmentMessage decommitmentsOutput, DifferenceCommitmentReceiverBundle diffCommitments) {
			this.commitmentsX = commitmentsX;
			this.commitmentsY1Extended = commitmentsY1Extended;
			this.commitmentsY2 = commitmentsY2;
			this.commitmentsOutput = commitmentsOutput;
			this.decommitmentsOutput = decommitmentsOutput;
			this.diffCommitments = diffCommitments;
			return this;
		}
		
		public LimitedBundle build() {
			return new LimitedBundle(this);
		}
	}
	
	
	private GarbledTablesHolder garbledTables;
	private byte[] translationTable;
	
	//Wires' indices.
	private int[] inputLabelsX;
	private int[] inputLabelsY1Extended;
	private int[] inputLabelsY2;
	private int[] outputLabels;
	
	//Commitments on the keys.
	private CommitmentBundle commitmentsX;
	private CommitmentBundle commitmentsY1Extended;
	private CommitmentBundle commitmentsY2;
	private byte[] commitmentsOutput;
	private long commitmentsOutputId;
	private CmtCDecommitmentMessage decommitmentsOutput;
	private DifferenceCommitmentReceiverBundle diffCommitments;
	
	//Input for the circuit.
	private CircuitInput y1;
	private byte[] inputKeysX;
	private byte[] inputKeysY;
	private byte[] inputKeysY1Extended;
	
	//Masks.
	private byte[] placementMaskDifference;
	private byte[] commitmentMask;
	
	private String tablesFile;
	
	/**
	 * A constructor that sets the parameters from the builder and initializes the other parameters.
	 * @param builder Contains some parameters for the LimitedBundle.
	 */
	private LimitedBundle(Builder builder) {
		this.garbledTables = builder.garbledTables;
		this.translationTable = builder.translationTable;
		
		this.inputLabelsX = builder.inputLabelsX;
		this.inputLabelsY1Extended = builder.inputLabelsY1Extended;
		this.inputLabelsY2 = builder.inputLabelsY2;
		this.outputLabels = builder.outputLabels;
		
		this.commitmentsX = builder.commitmentsX;
		this.commitmentsY1Extended = builder.commitmentsY1Extended;
		this.commitmentsY2 = builder.commitmentsY2;
		this.commitmentsOutput = ((CmtSimpleHashCommitmentMessage) builder.commitmentsOutput).getCommitment();
		this.commitmentsOutputId = builder.commitmentsOutput.getId();
		this.decommitmentsOutput = builder.decommitmentsOutput;
		this.diffCommitments = builder.diffCommitments;
		
		this.y1 = null;
		this.inputKeysX = null;
		this.inputKeysY = null;
		this.inputKeysY1Extended = null;
		this.commitmentMask = null;
		
		this.tablesFile = builder.tablesFile;
	}
	
	/*
	 * Getters and setters.
	 */
	
	public void setY1(CircuitInput y1) {
		this.y1 = y1;
	}
	
	public CircuitInput getY1() {
		return y1;
	}
	
	public void setXInputKeys(byte[] inputKeys) {
		this.inputKeysX = inputKeys;
	}
	
	public void setYInputKeys(byte[] inputKeys) {
		this.inputKeysY = inputKeys;
	}
	
	public void setY1ExtendedInputKeys(byte[] inputKeys) {
		this.inputKeysY1Extended = inputKeys;
	}
	
	public byte[] getXInputKeys() {
		return this.inputKeysX;
	}
	
	public byte[] getYInputKeys() {
		return this.inputKeysY;
	}
	
	public byte[] getY1ExtendedInputKeys() {
		return this.inputKeysY1Extended;
	}
	
	public GarbledTablesHolder getGarbledTables() {
		return garbledTables;
	}

	public byte[] getTranslationTable() {
		return translationTable;
	}
	
	public int[] getInputLabelsX() {
		return inputLabelsX;
	}

	public int[] getInputLabelsY1Extended() {
		return inputLabelsY1Extended;
	}

	public int[] getInputLabelsY2() {
		return inputLabelsY2;
	}

	public int[] getOutputLabels() {
		return outputLabels;
	}
	
	public CommitmentBundle getCommitmentsX() {
		return commitmentsX;
	}

	public CommitmentBundle getCommitmentsY1Extended() {
		return commitmentsY1Extended;
	}

	public CommitmentBundle getCommitmentsY2() {
		return commitmentsY2;
	}

	public CmtCCommitmentMsg getCommitmentsOutputKeys() {
		return new CmtSimpleHashCommitmentMessage(commitmentsOutput, commitmentsOutputId);
	}
	
	public CmtCDecommitmentMessage getDecommitmentsOutputKeys() {
		return decommitmentsOutput;
	}

	public DifferenceCommitmentReceiverBundle getDifferenceCommitmentBundle() {
		return diffCommitments;
	}

	public void setPlacementMaskDifference(byte[] mask) {
		this.placementMaskDifference = mask;
		
	}
	
	public byte[] getPlacementMaskDifference() {
		return placementMaskDifference;
	}

	public void setCommitmentMask(byte[] mask) {
		this.commitmentMask = mask;
	}
	
	public byte[] getCommitmentMask() {
		return commitmentMask;
	}
	
	/**
	 * This function overrides the function from the Serializable interface because we want only part of the 
	 * members to be written to file.
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException{
		
		if (tablesFile != null){
		
			//Open the file.
			File file = new File(tablesFile+".txt");
			ObjectInput garbledTableFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			try {
				garbledTables = (GarbledTablesHolder) garbledTableFile.readObject();
				
				garbledTableFile.close();
				file.delete();
			} catch (ClassNotFoundException e) {
				// Should not occur since the file contains GarbledTablesHolder.
			}
		}
		
		out.writeObject(garbledTables);
		garbledTables = null;
		out.writeObject(translationTable);
		
		//Wires' indices.
		out.writeObject(inputLabelsX);
		out.writeObject(inputLabelsY1Extended);
		out.writeObject(inputLabelsY2);
		out.writeObject(outputLabels);
		
		//Commitments on the keys.
		out.writeObject(commitmentsX);
		out.writeObject(commitmentsY1Extended);
		out.writeObject(commitmentsY2);
		out.writeObject(commitmentsOutput);
		out.writeObject(commitmentsOutputId);
		out.writeObject(decommitmentsOutput);
		out.writeObject(diffCommitments);
		
		//Input for the circuit.
		out.writeObject(y1);
		out.writeObject(inputKeysX);
		out.writeObject(inputKeysY);
		out.writeObject(inputKeysY1Extended);
		
		//Masks.
		out.writeObject(placementMaskDifference);
		out.writeObject(commitmentMask);
	}
	
	
	/**
	 * This function overrides the function from the Serializable interface because only some of members should be read from the file.
	 * @param out
	 * @throws IOException
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		
		garbledTables = (GarbledTablesHolder) in.readObject();
		translationTable = (byte[]) in.readObject();
		
		inputLabelsX = (int[]) in.readObject();
		inputLabelsY1Extended = (int[]) in.readObject();
		
		inputLabelsY2 = (int[]) in.readObject();
		outputLabels = (int[]) in.readObject();
		
		//Commitments on the keys.
		commitmentsX = (CommitmentBundle) in.readObject();
		commitmentsY1Extended = (CommitmentBundle) in.readObject();
		commitmentsY2 = (CommitmentBundle) in.readObject();
		commitmentsOutput = (byte[]) in.readObject();
		commitmentsOutputId = (Long) in.readObject();
		decommitmentsOutput = (CmtCDecommitmentMessage) in.readObject();
		diffCommitments = (DifferenceCommitmentReceiverBundle) in.readObject();
		
		//Input for the circuit.
		y1 = (CircuitInput) in.readObject();
		inputKeysX = (byte[]) in.readObject();
		inputKeysY = (byte[]) in.readObject();
		inputKeysY1Extended = (byte[]) in.readObject();
		
		//Masks.
		placementMaskDifference = (byte[]) in.readObject();
		commitmentMask = (byte[]) in.readObject();
	}
	
}
