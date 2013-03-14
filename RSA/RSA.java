/***
 * Author: Juan Carlos Escobar
 * Course: CSC 333
 *
 * Usage: java RSA
 * Description:
 *		Generate 5 keys (p, q, n, e, d) , encrypt the plaintext and decrypt the ciphertext.
 *		Prints the 5 keys, plaintext, ciphertext and deciphered ciphertext.
 *
 **/
import java.security.SecureRandom;
import java.util.Random;
import java.math.BigInteger;
import java.util.ArrayList;

class RSA {
	
	private static final SecureRandom secureRand = new SecureRandom();
	private static final Random rand = new Random();
	private static final int PRIME_NUMBER_BITS_P = rand.nextInt(512)+128;		// Random bit precision for exponent key p
	private static final int PRIME_NUMBER_BITS_Q = rand.nextInt(512)+128;		// Random bit precision for exponent key q
	private static final int EXPONENT_BITS_E = rand.nextInt(300)+2;				// Random bit precision for exponent key e
	private static final int PLAIN_TEXT_PRECISION_BITS = rand.nextInt(256)+3;	// Random bit precision for plaintext x where (2 <= x <= n)
	private static final BigInteger ONE = BigInteger.ONE;						// BigInteger Constant 1
	
	public static void main (String[] args) {
	
		// Generate arbitrary precision integer plaintext
		final BigInteger plaintext = new BigInteger(PLAIN_TEXT_PRECISION_BITS, secureRand);
		
		// Call generateKeys method
		ArrayList<BigInteger> keyList = generateKeys();
		
		// Print the five key values
		int key = 0;
		for (BigInteger l : keyList) {
			switch(key) {
				// Print key value p
				case 0:
					System.out.println("\nPrivate Key - Large Prime p:\n" + l);
					break;
				// Print key value q
				case 1:
					System.out.println("\nPrivate Key - Large Prime q:\n" + l);
					break;
				// Print key value n
				case 2:
					System.out.println("\nPublic Key - n:\n" + l);
					break;
				// Print key value e
				case 3:
					System.out.println("\nPublic Key - Encryption Exponent e:\n" + l);
					break;
				// Print key value d
				case 4:
					System.out.println("\nPrivate Key - Decryption Exponent d:\n" + l);
					break;
				/*
				// Print key value Phi
				case 5:
					System.out.println("\nKey Phi:\n" + l);
					break;
				*/
				default:
					System.out.println("\nError: Could not retrieve key(s).");
					break;
			}
			key++;
		}
		// Print the arbitrary precision integer plaintext
		System.out.println("\nArbitrary Precision Integer Plaintext: \n" + plaintext);
		
		// Print the cipher text
		BigInteger ciphertext = encrypt(plaintext, keyList.get(3), keyList.get(2));
		System.out.println("\nCiphertext:\n" + ciphertext);
		
		// Print the deciphered text
		BigInteger decipheredtext = decrypt(ciphertext, keyList.get(4), keyList.get(2));
		System.out.println("\nPlaintext from ciphertext:\n" + decipheredtext);		
	}
	
	private static ArrayList<BigInteger> generateKeys() {
		ArrayList<BigInteger> keyList = new ArrayList<BigInteger>();
		
		BigInteger p = BigInteger.probablePrime(PRIME_NUMBER_BITS_P, secureRand);		// Generate prime number p
		BigInteger q = BigInteger.probablePrime(PRIME_NUMBER_BITS_Q, secureRand);		// Generate prime number q
		BigInteger n = p.multiply(q);													// Calculate modulus key n
		BigInteger phi = (p.subtract(ONE)).multiply(q.subtract(ONE));					// Calculate Phi

		BigInteger e = new BigInteger(EXPONENT_BITS_E, secureRand);						// Generate public key	 e

		// Regenerate public key e if gcd is not 1
		BigInteger gcd = e.gcd(phi);
		while ((gcd.compareTo(ONE)) != 0) {
			e = new BigInteger(EXPONENT_BITS_E, secureRand);
			gcd = e.gcd(phi);
		}
		
		BigInteger d = e.modInverse(phi);												// Calculate private key d
		
		// Add keys to keyList
		keyList.add(p);
		keyList.add(q);
		keyList.add(n);
		keyList.add(e);
		keyList.add(d);
		//keyList.add(phi);
							
		return keyList;
	}
	
	// Encrypt the plaintext
	private static BigInteger encrypt(BigInteger plaintext, BigInteger e, BigInteger n) {
		return plaintext.modPow(e,n);
	}

	// Decrypt the ciphertext
	private static BigInteger decrypt(BigInteger ciphertext, BigInteger d, BigInteger n) {
		return ciphertext.modPow(d,n);
	}

}