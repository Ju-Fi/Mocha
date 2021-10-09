package jasic;

import java.util.ArrayList;

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
				tokens.add(make_number());
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
			} else if (currentChar == ';') {
				tokens.add(new Token(Token.tokens.TERM, pos.copy(), pos.copy()));
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

	private Token make_number() {
		String num_str = "";
		int dot_count = 0;
		String digits = Token.DIGITS + ".";

		while (currentChar != '\u0000' && digits.indexOf(currentChar) != -1) {
			if (currentChar == '.') {
				if (dot_count == 1)
					break;
				dot_count++;
				num_str += '.';
			} else {
				num_str += currentChar;
			}
			advance();
		}

		if (dot_count == 0) {
			Token t = new Token(Token.tokens.INT, num_str);
			return t;
		} else {
			Token t = new Token(Token.tokens.FLOAT, num_str);
			return t;
		}
	}
}
