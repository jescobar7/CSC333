/***
 * Author: Juan Carlos Escobar
 * Course: CSC 333
 *
 * Description: MixColumnsLayer.java
 * 		Performs the Mix Columns with input text
 **/
import java.math.BigInteger;
 
class MixColumnsLayer {
	
	private static final String DELIMS = "[ ]+";
	private static String[] tmpRes = new String[4];
	private static String[] tmpGF = new String[16];
	
	// Multiplication by 2 in the Galois Field GF(2^8)
	private static final String[] GF2 = new String[] {
		"00 02 04 06 08 0a 0c 0e 10 12 14 16 18 1a 1c 1e", // 0
		"20 22 24 26 28 2a 2c 2e 30 32 34 36 38 3a 3c 3e", // 1
		"40 42 44 46 48 4a 4c 4e 50 52 54 56 58 5a 5c 5e", // 2
		"60 62 64 66 68 6a 6c 6e 70 72 74 76 78 7a 7c 7e", // 3
		"80 82 84 86 88 8a 8c 8e 90 92 94 96 98 9a 9c 9e", // 4
		"a0 a2 a4 a6 a8 aa ac ae b0 b2 b4 b6 b8 ba bc be", // 5
		"c0 c2 c4 c6 c8 ca cc ce d0 d2 d4 d6 d8 da dc de", // 6
		"e0 e2 e4 e6 e8 ea ec ee f0 f2 f4 f6 f8 fa fc fe", // 7
		"1b 19 1f 1d 13 11 17 15 0b 09 0f 0d 03 01 07 05", // 8
		"3b 39 3f 3d 33 31 37 35 2b 29 2f 2d 23 21 27 25", // 9
		"5b 59 5f 5d 53 51 57 55 4b 49 4f 4d 43 41 47 45", // A
		"7b 79 7f 7d 73 71 77 75 6b 69 6f 6d 63 61 67 65", // B
		"9b 99 9f 9d 93 91 97 95 8b 89 8f 8d 83 81 87 85", // C
		"bb b9 bf bd b3 b1 b7 b5 ab a9 af ad a3 a1 a7 a5", // D
		"db d9 df dd d3 d1 d7 d5 cb c9 cf cd c3 c1 c7 c5", // E
		"fb f9 ff fd f3 f1 f7 f5 eb e9 ef ed e3 e1 e7 e5"  // F
	  //  0  1  2  3  4  5  6  7  8  9  A  B  C  D  E  F 
	};
	
	// Multiplication by 3 in the Galois Field GF(2^8)
	private static final String[] GF3 = new String[] {
		"00 03 06 05 0c 0f 0a 09 18 1b 1e 1d 14 17 12 11", // 0
		"30 33 36 35 3c 3f 3a 39 28 2b 2e 2d 24 27 22 21", // 1
		"60 63 66 65 6c 6f 6a 69 78 7b 7e 7d 74 77 72 71", // 2
		"50 53 56 55 5c 5f 5a 59 48 4b 4e 4d 44 47 42 41", // 3
		"c0 c3 c6 c5 cc cf ca c9 d8 db de dd d4 d7 d2 d1", // 4
		"f0 f3 f6 f5 fc ff fa f9 e8 eb ee ed e4 e7 e2 e1", // 5
		"a0 a3 a6 a5 ac af aa a9 b8 bb be bd b4 b7 b2 b1", // 6
		"90 93 96 95 9c 9f 9a 99 88 8b 8e 8d 84 87 82 81", // 7
		"9b 98 9d 9e 97 94 91 92 83 80 85 86 8f 8c 89 8a", // 8
		"ab a8 ad ae a7 a4 a1 a2 b3 b0 b5 b6 bf bc b9 ba", // 9
		"fb f8 fd fe f7 f4 f1 f2 e3 e0 e5 e6 ef ec e9 ea", // A
		"cb c8 cd ce c7 c4 c1 c2 d3 d0 d5 d6 df dc d9 da", // B
		"5b 58 5d 5e 57 54 51 52 43 40 45 46 4f 4c 49 4a", // C
		"6b 68 6d 6e 67 64 61 62 73 70 75 76 7f 7c 79 7a", // D
		"3b 38 3d 3e 37 34 31 32 23 20 25 26 2f 2c 29 2a", // E
		"0b 08 0d 0e 07 04 01 02 13 10 15 16 1f 1c 19 1a"  // F
	  //  0  1  2  3  4  5  6  7  8  9  A  B  C  D  E  F 
	};
	
	public static String[] mixColumns(String[] input) {
		return performMixColumns(input);
	}
	
	/**
	 * Perform Mix Columns
	 */
	private static String[] performMixColumns(String[] input) {
		String[] mixColumnString = new String[16];
		String[] fourBytes1 = new String[4];
		String[] fourBytes2 = new String[4];
		String[] fourBytes3 = new String[4];
		String[] fourBytes4 = new String[4];
		
		// Seperate input into 4 string arrays of 4 strings each
		fourBytes1[0] = input[0];
		fourBytes1[1] = input[4];
		fourBytes1[2] = input[8];
		fourBytes1[3] = input[12];
		fourBytes2[0] = input[1];
		fourBytes2[1] = input[5];
		fourBytes2[2] = input[9];
		fourBytes2[3] = input[13];
		fourBytes3[0] = input[2];
		fourBytes3[1] = input[6];
		fourBytes3[2] = input[10];
		fourBytes3[3] = input[14];
		fourBytes4[0] = input[3];
		fourBytes4[1] = input[7];
		fourBytes4[2] = input[11];
		fourBytes4[3] = input[15];
		
		// Create the string array with mixed columns
		mixColumnString[0] = R0(fourBytes1);
		mixColumnString[1] = R1(fourBytes1);
		mixColumnString[2] = R2(fourBytes1);
		mixColumnString[3] = R3(fourBytes1);
		mixColumnString[4] = R0(fourBytes2);
		mixColumnString[5] = R1(fourBytes2);
		mixColumnString[6] = R2(fourBytes2);
		mixColumnString[7] = R3(fourBytes2);
		mixColumnString[8] = R0(fourBytes3);
		mixColumnString[9] = R1(fourBytes3);
		mixColumnString[10] = R2(fourBytes3);
		mixColumnString[11] = R3(fourBytes3);
		mixColumnString[12] = R0(fourBytes4);
		mixColumnString[13] = R1(fourBytes4);
		mixColumnString[14] = R2(fourBytes4);
		mixColumnString[15] = R3(fourBytes4);
		
		return toColumnMajor(mixColumnString);
	}
	
	// Multiplication - 2 3 1 1
	private static String R0(String[] fourBytes) {
		for (int i = 0; i < fourBytes.length; i++) {
			String hex = fourBytes[i];
			int int1 = Character.digit(hex.charAt(0),16);
			int int2 = Character.digit(hex.charAt(1),16);
			
			switch (i) {
				case 0:
					tmpGF = (GF2[int1]).split(DELIMS);
					tmpRes[i] = tmpGF[int2];
					break;
				case 1:
					tmpGF = (GF3[int1]).split(DELIMS);
					tmpRes[i] = tmpGF[int2];
					break;
				case 2:
					tmpRes[i] = fourBytes[i];
					break;
				case 3:
					tmpRes[i] = fourBytes[i];
					break;
				default:
					break;
			}
		}
		return xor(tmpRes);
	}
	
	// Multiplication - 1 2 3 1
	private static String R1(String[] fourBytes) {
		for (int i = 0; i < fourBytes.length; i++) {
			String hex = fourBytes[i];
			int int1 = Character.digit(hex.charAt(0),16);
			int int2 = Character.digit(hex.charAt(1),16);
			
			switch (i) {
				case 0:
					tmpRes[i] = fourBytes[i];
					break;
				case 1:
					tmpGF = (GF2[int1]).split(DELIMS);
					tmpRes[i] = tmpGF[int2];
					break;
				case 2:
					tmpGF = (GF3[int1]).split(DELIMS);
					tmpRes[i] = tmpGF[int2];
					break;
				case 3:
					tmpRes[i] = fourBytes[i];
					break;
				default:
					break;
			}
		}
		return xor(tmpRes);
	}
	
	// Multiplication - 1 1 2 3
	private static String R2(String[] fourBytes) {
		for (int i = 0; i < fourBytes.length; i++) {
			String hex = fourBytes[i];
			int int1 = Character.digit(hex.charAt(0),16);
			int int2 = Character.digit(hex.charAt(1),16);
			
			switch (i) {
				case 0:
					tmpRes[i] = fourBytes[i];
					break;
				case 1:
					tmpRes[i] = fourBytes[i];
					break;
				case 2:
					tmpGF = (GF2[int1]).split(DELIMS);
					tmpRes[i] = tmpGF[int2];
					break;
				case 3:
					tmpGF = (GF3[int1]).split(DELIMS);
					tmpRes[i] = tmpGF[int2];
					break;
				default:
					break;
			}
		}
		return xor(tmpRes);
	}
	
	// Multiplication - 3 1 1 2
	private static String R3(String[] fourBytes) {
		for (int i = 0; i < fourBytes.length; i++) {
			String hex = fourBytes[i];
			int int1 = Character.digit(hex.charAt(0),16);
			int int2 = Character.digit(hex.charAt(1),16);
			
			switch (i) {
				case 0:
					tmpGF = (GF3[int1]).split(DELIMS);
					tmpRes[i] = tmpGF[int2];
					break;
				case 1:
					tmpRes[i] = fourBytes[i];
					break;
				case 2:
					tmpRes[i] = fourBytes[i];
					break;
				case 3:
					tmpGF = (GF2[int1]).split(DELIMS);
					tmpRes[i] = tmpGF[int2];
					break;
				default:
					break;
			}
		}
		return xor(tmpRes);
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
	
	/**
	 * XOR the input
	 */
	private static String xor(String[] input) {
		String xorRes = "";
		
		BigInteger one = new BigInteger(input[0], 16);
		BigInteger two = new BigInteger(input[1], 16);
		BigInteger three = new BigInteger(input[2], 16);
		BigInteger four = new BigInteger(input[3], 16);
		
		BigInteger res = ((one.xor(two)).xor(three)).xor(four);
		xorRes = res.toString(16);
		
		// Add leading zero to hex string if missing
		if (xorRes.length() < 2) {
			xorRes = "0" + xorRes;
		}
		
		return xorRes;
	}
	
}