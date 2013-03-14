/***
 * Author: Juan Carlos Escobar
 * Course: CSC 333
 *
 * Description: KeyAdditionLayer.java
 * 		Performs the XOR key addition with input text
 **/
import java.math.BigInteger;
 
class KeyAdditionLayer {
	
	private static final boolean DEBUG = false;
	
	// Takes as arguments (plain text, subkey n)
	public static String[] addRoundKey(String[] inText, String[] inKey) {
		return performKeyAdd(inText, inKey);
	}
	
	/**
	 * Perform Key Addition
	 */
	private static String[] performKeyAdd(String[] pText, String[] nKey) {	
		String[] xorStr = new String[16];
		for (int i = 0; i < pText.length; i++) { 
			String s1 = pText[i];			
			String s2 = nKey[i];
			
			BigInteger one = new BigInteger(s1, 16);
			BigInteger two = new BigInteger(s2, 16);
			
			BigInteger three = one.xor(two);
			
			String xorTmp = "";
			xorTmp = three.toString(16);
			
			// Add leading zero to hex string if missing
			if (xorTmp.length() < 2) {
				xorStr[i] = "0" + xorTmp;
			}
			else {
				xorStr[i] = xorTmp;
			}
		}
		return xorStr;
	}
	
}