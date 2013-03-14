/***
 * Author: Juan Carlos Escobar
 * Course: CSC 333
 *
 * Usage : java AES < input.txt
 * Description: AES.java
 * 		AES Implementation that takes a file with 
 *		plaintext and key schedules as input and returns
 *		the ciphered text in hex.
 *	
 * Comments: 568 lines of code within five *.java files
 **/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class AES {
		
	private static String[] plainText = new String[16];
	private static String[] keySchedule = new String[11];
	
	/**
	 * Read input file
	 */
	public static void main (String[] args) {
 		try {
			BufferedReader file = 
				new BufferedReader(new InputStreamReader(System.in));
			
			String fileLine;
			int index = 0;
			// Seperate and store the plain text and key schedule
			// from input file
			while ((fileLine = file.readLine()) != null) {
				if (index == 0) {
					String delims = "[ ]+";
					plainText = fileLine.split(delims);
				}
				else {
					keySchedule[index-1] = fileLine;
				}
				index++;
			}
			file.close();
		}
		catch (IOException e) {
			System.out.println("Error: Cannot find specified file.");
		}
		plainText = toColumnMajor(plainText);
		
		System.out.println();
		System.out.println("Original Text State:");
		printState(plainText);
		
		encrypt();		
	}
	
	// Encrypt the plain text from file
	private static void encrypt() {
		final int MAX_ROUNDS = 10;
	
		// Setup
		KeyAdditionLayer kal = new KeyAdditionLayer();
		ByteSubstitutionLayer bsl = new ByteSubstitutionLayer();
		ShiftRowsLayer srl = new ShiftRowsLayer();
		MixColumnsLayer mcl = new MixColumnsLayer();
		String[] cipherText = new String[16];
		String delims = "[ ]+";
				
		cipherText = kal.addRoundKey(plainText, toColumnMajor((keySchedule[0]).split(delims)));
		for (int round = 0; round < MAX_ROUNDS; round++) {
			System.out.println("\n\nSubkey "+ round+ " State:");
			printState(toColumnMajor((keySchedule[round]).split(delims)));
		
			System.out.println("\n\nProtocol Round: "+(round+1));
			System.out.println("Text State at Start of Round:");			
			printState(cipherText);
			
			if (round < (MAX_ROUNDS-1)) {
				// Byte Substitution
				cipherText = bsl.subBytes(cipherText);
				System.out.println("\n\nText State After Sub Bytes:");			
				printState(cipherText);
				
				// Shift Rows
				cipherText = srl.shiftRows(cipherText);
				System.out.println("\n\nText State After Shift Rows:");			
				printState(cipherText);
				
				// Mix Columns
				cipherText = mcl.mixColumns(cipherText);
				System.out.println("\n\nText State After Mix Columns:");			
				printState(cipherText);
				
				// Key Addition
				cipherText = kal.addRoundKey(cipherText, toColumnMajor((keySchedule[round+1]).split(delims)));
				//System.out.println("\nText State After Sub Bytes:");			
				//printState(cipherText);
			}
			else {
				// Byte Substitution
				cipherText = bsl.subBytes(cipherText);
				System.out.println("\n\nText State After Sub Bytes:");			
				printState(cipherText);
				
				// Shift Rows
				cipherText = srl.shiftRows(cipherText);
				System.out.println("\n\nText State After Shift Rows:");			
				printState(cipherText);
				
				// Key Addition
				cipherText = kal.addRoundKey(cipherText, toColumnMajor((keySchedule[round+1]).split(delims)));
			}
		}
		// Final Encrypted Output
		System.out.println();
		System.out.println("\nFinal Ouput Text State:");
		printState(cipherText);
		
	}
	
	// Format input string array into Column Major order
	private static String[] toColumnMajor(String[] input) {
		String[] cm = new String[16];
		
		cm[0] = input[0];
		cm[4] = input[1];
		cm[8] = input[2];
		cm[12] = input[3];
		cm[1] = input[4];
		cm[5] = input[5];
		cm[9] = input[6];
		cm[13] = input[7];
		cm[2] = input[8];
		cm[6] = input[9];
		cm[10] = input[10];
		cm[14] = input[11];
		cm[3] = input[12];
		cm[7] = input[13];
		cm[11] = input[14];
		cm[15] = input[15];
		
		return cm;
	}
		
	// Print String Array
	private static void printState(String[] input) {
		for (int i = 0; i < input.length; i++) {
			if ( (i+1) % 4 == 0) {
				System.out.println(input[i] + " ");
			}
			else {
				System.out.print(input[i] + " ");
			}
		}
	}
	
}