/***
 * Author: Juan Carlos Escobar
 * Course: CSC 333
 *
 * Description: ShiftRowsLayer.java
 * 		Performs the Shift Rows with input text
 **/
class ShiftRowsLayer {
	
	public static String[] shiftRows(String[] in) {
		return performShift(in);
	}

	// Perform Row Shifts
	private static String[] performShift(String[] input) {
		String[] state = new String[16];
		
		state[0] = input[0];
		state[1] = input[1];
		state[2] = input[2];
		state[3] = input[3];
		state[7] = input[4];
		state[4] = input[5];
		state[5] = input[6];
		state[6] = input[7];
		state[10] = input[8];
		state[11] = input[9];
		state[8] = input[10];
		state[9] = input[11];
		state[13] = input[12];
		state[14] = input[13];
		state[15] = input[14];
		state[12] = input[15];

		return state;
	}

}