package jasic;

import java.util.Arrays;

public class Error {
	private String error_name;
	private String details;
	private int[] pos_start;
	private int[] pos_end;

	public Error(int[] pos_start, int[] pos_end, String error_name, String details) {
		this.pos_start = pos_start;
		this.pos_end = pos_end;
		this.error_name = error_name;
		this.details = details;
	}

	public String IllegalCharError(char c) {
		return "Illegal Character: " + "\'" + c + "\'" + " found at " + Arrays.toString(this.pos_start) + " "
				+ Arrays.toString(this.pos_end);
	}

	public String InvalidSyntaxError() {
		return "Invalid Syntax: " + details + " at " + Arrays.toString(this.pos_start) + " "
				+ Arrays.toString(this.pos_end);
	}

}
