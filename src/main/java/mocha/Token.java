package mocha;

import java.lang.reflect.Array;
import java.util.HashMap;

public class Token {
	private Enum<tokens> type;
	private String value;

	private int[] pos_start;
	private int[] pos_end;

	static final String DIGITS = "0123456789";

	public Token(Enum<tokens> type, String value) {
		this.type = type;
		this.value = value;
	}

	// generate tokens
	public Token(Enum<tokens> type) {
		this.type = type;
	}

	public Token(Enum<tokens> type, int[] pos_start, int[] pos_end) {
		this.type = type;
		this.pos_start = pos_start;
		this.pos_end = pos_end;
	}

	public Token(Enum<tokens> type, String value, Position pos_start, Position pos_end) {
		this.type = type;
		this.value = value;
		this.pos_start = pos_start.copy();
		this.pos_end = pos_end.copy();
	}

	// Token types
	public static enum tokens {
		// types
		INT, FLOAT, BOOL, STRING,
		// operations
		PLUS, MINUS, MUL, DIV, MOD, LPAREN, RPAREN, ASSIGN,
		// keywords
		PRINTLND, PRINTLN,
		// manipulation
		DROP, DUP, SWAP, ROT,
		// conditionals
		GT, LT, EQ, GTEQ, LTEQ, NOTEQ, NOT, AND, OR,
		// control flow
		IF, ELSE, WHILE, DO,
		// names
		VAR,
		// misc
		LCBRACK, RCBRACK

	}

	// keywords
	public static final tokens[] keywords = { tokens.PRINTLN, tokens.PRINTLND, tokens.DROP, tokens.DUP, tokens.SWAP,
			tokens.ROT, tokens.IF, tokens.ELSE, tokens.WHILE, tokens.DO, tokens.AND, tokens.OR

	};

	// getters

	public int[] getPosEnd() {
		return this.pos_end;
	}

	public int[] getPosStart() {
		return this.pos_start;
	}

	public Enum<tokens> getType() {
		return this.type;
	}

	public String getValue() {
		return this.value;
	}

	public String repr() {
		if (this.value != null) {
			return this.type + ":" + this.value;
		} else {
			return this.type.toString();
		}
	}

}
