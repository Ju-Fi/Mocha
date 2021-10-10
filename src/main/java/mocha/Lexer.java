package mocha;

import java.util.ArrayList;
import java.util.Arrays;

public class Lexer {
	private String text = null;
	private Position pos = new Position(-1, 0, -1);
	private char currentChar;

	public Lexer(String text) {
		this.text = text;
		// System.out.println("lexer: " + text);
	}

	public void advance() {
		this.pos.advance(currentChar);
		// System.out.println("advance: " + text);
		if (this.pos.index < this.text.length()) {
			this.currentChar = text.charAt(pos.index);
		} else {
			currentChar = '\u0000';
		}
	}

	public ArrayList<Token> make_tokens() {
		ArrayList<Token> tokens = new ArrayList<>();

		advance();
		while (this.currentChar != '\u0000') {
			if (currentChar == ' ' || currentChar == '\t') {
				advance();
			}

			else if (Token.DIGITS.indexOf(currentChar) != -1) {
				tokens.add(make_number(tokens));
			}

			else if (isAlpha(currentChar)) {
				tokens.add(make_word());
			}

			else if (currentChar == '+') {
				tokens.add(new Token(Token.tokens.PLUS, pos.copy(), pos.copy()));
				advance();
			} else if (currentChar == '-') {
				tokens.add(new Token(Token.tokens.MINUS, pos.copy(), pos.copy()));
				advance();
			} else if (currentChar == '*') {
				tokens.add(new Token(Token.tokens.MUL, pos.copy(), pos.copy()));
				advance();
			} else if (currentChar == '/') {
				tokens.add(new Token(Token.tokens.DIV, pos.copy(), pos.copy()));
				advance();
			} else if (currentChar == '(') {
				tokens.add(new Token(Token.tokens.LPAREN, pos.copy(), pos.copy()));
				advance();
			} else if (currentChar == ')') {
				tokens.add(new Token(Token.tokens.RPAREN, pos.copy(), pos.copy()));
				advance();
			} else if (currentChar == '%') {
				tokens.add(new Token(Token.tokens.MOD, pos.copy(), pos.copy()));
				advance();
			} else if (currentChar == '.') {
				tokens.add(new Token(Token.tokens.PRINTLN, pos.copy(), pos.copy()));
				advance();
			}

			else {

				int[] pos_start = pos.copy();
				char c = currentChar;
				advance();
				Error err = new Error(pos_start, pos.copy(), "IllegalCharError",
						"Illegal character used");
				System.out.println(err.IllegalCharError(c).toString());

			}

		}
		return tokens;

	}

	private Token make_word() {
		String word = "";

		int word_start = this.pos.index;
		int word_end = word_start;

		while (currentChar != '\u0000' && isAlpha(currentChar)) {
			word_end++;
			advance();
		}
		word = text.substring(word_start, word_end);
		Token token;

		for (Token.tokens t : Token.keywords) {
			if (word.equals(t.name().toLowerCase())) {
				switch (word) {
					case "printlnd":
						return new Token(Token.tokens.PRINTLND);

					case "println":
						return new Token(Token.tokens.PRINTLN);
				}
			}
		}

		token = new Token(Token.tokens.VAR, word);
		return token;

	}

	private Token make_number(ArrayList<Token> tokens) {
		String num_str = "";

		int num_start = this.pos.index;
		int num_end = num_start;

		int dot_count = 0;
		String digits = Token.DIGITS + ".";

		char prev_char = '\u0000';
		boolean is_neg = false;
		if (text.length() > 1) {
			try {
				prev_char = this.text.charAt(this.pos.index - 1);
			} catch (Exception e) {
			}
		}
		if (prev_char == '-') {
			is_neg = true;
			tokens.remove(tokens.size() - 1);
		}

		while (currentChar != '\u0000' && digits.indexOf(currentChar) != -1) {
			if (currentChar == '.') {
				if (dot_count == 1)
					break;
				dot_count++;
				num_end++;
			} else {
				num_end++;
			}
			advance();
		}

		num_str = this.text.substring(num_start, num_end);
		if (is_neg) {
			num_str = '-' + num_str;
		}

		if (dot_count == 0) {
			Token t = new Token(Token.tokens.INT, num_str);
			return t;
		} else {
			Token t = new Token(Token.tokens.FLOAT, num_str);
			return t;
		}
	}

	private boolean isAlpha(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';

	}
}
