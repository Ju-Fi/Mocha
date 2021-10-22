package mocha;

import java.util.ArrayList;
import java.util.Arrays;

public class Lexer {
	private String text = null;
	private Position pos = new Position(-1, 1, -1);
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
			if (currentChar == ' ' || currentChar == '\t' || currentChar == '\n') {
				advance();
			}

			else if (Token.DIGITS.indexOf(currentChar) != -1) {
				tokens.add(make_number(tokens));
			}

			else if (isAlpha(currentChar)) {
				tokens.add(make_word());
			}

			else if (currentChar == '"') {
				tokens.add(make_str());
				advance();
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
			} else if (currentChar == '>') {
				advance();
				if (currentChar == '=') {
					tokens.add(new Token(Token.tokens.GTEQ, pos.copy(), pos.copy()));
					advance();
				} else {
					tokens.add(new Token(Token.tokens.GT, pos.copy(), pos.copy()));
				}
			} else if (currentChar == '<') {
				advance();
				if (currentChar == '=') {
					tokens.add(new Token(Token.tokens.LTEQ, pos.copy(), pos.copy()));
					advance();
				} else {
					tokens.add(new Token(Token.tokens.LT, pos.copy(), pos.copy()));
				}
			} else if (currentChar == '=') {
				advance();
				if (currentChar == '=') {
					tokens.add(new Token(Token.tokens.EQ, pos.copy(), pos.copy()));
					advance();
				} else {
					tokens.add(new Token(Token.tokens.ASSIGN, pos.copy(), pos.copy()));
				}

			} else if (currentChar == '!') {
				advance();
				if (currentChar == '=') {
					tokens.add(new Token(Token.tokens.NOTEQ, pos.copy(), pos.copy()));
					advance();
				} else {
					tokens.add(new Token(Token.tokens.NOT, pos.copy(), pos.copy()));
				}
			} else if (currentChar == '{') {
				tokens.add(new Token(Token.tokens.LCBRACK, pos.copy(), pos.copy()));
				advance();
			} else if (currentChar == '}') {
				tokens.add(new Token(Token.tokens.RCBRACK, pos.copy(), pos.copy()));
				advance();
			}

			else if (currentChar == '#') {
				advance();
				while (currentChar != '\n') {
					advance();
				}
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

	private Token make_str() {
		String str = "";
		Position pos_start = pos;
		advance();
		int str_start = this.pos.index;
		int str_end = str_start;
		while (currentChar != '"' && currentChar != '\u0000') {
			str_end++;
			advance();
		}
		str = text.substring(str_start, str_end);
		Position pos_end = pos;
		return new Token(Token.tokens.STRING, str, pos_start, pos_end);
	}

	private Token make_word() {
		String word = "";
		Position pos_start = pos;

		int word_start = this.pos.index;
		int word_end = word_start;

		while (currentChar != '\u0000' && isAlpha(currentChar)) {
			word_end++;
			advance();
		}
		word = text.substring(word_start, word_end);

		for (Token.tokens t : Token.keywords) {
			if (word.equals(t.name().toLowerCase())) {
				switch (word) {
					case "printlnd":
						return new Token(Token.tokens.PRINTLND, pos.copy(), pos.copy());

					case "println":
						return new Token(Token.tokens.PRINTLN, pos.copy(), pos.copy());

					case "drop":
						return new Token(Token.tokens.DROP, pos.copy(), pos.copy());

					case "dup":
						return new Token(Token.tokens.DUP, pos.copy(), pos.copy());

					case "swap":
						return new Token(Token.tokens.SWAP, pos.copy(), pos.copy());

					case "rot":
						return new Token(Token.tokens.ROT, pos.copy(), pos.copy());

					case "over":
						return new Token(Token.tokens.OVER, pos.copy(), pos.copy());

					case "if":
						return new Token(Token.tokens.IF, pos.copy(), pos.copy());

					case "else":
						return new Token(Token.tokens.ELSE, pos.copy(), pos.copy());

					case "while":
						return new Token(Token.tokens.WHILE, pos.copy(), pos.copy());

					case "do":
						return new Token(Token.tokens.DO, pos.copy(), pos.copy());

					case "and":
						return new Token(Token.tokens.AND, pos.copy(), pos.copy());

					case "or":
						return new Token(Token.tokens.OR, pos.copy(), pos.copy());

					case "dump":
						return new Token(Token.tokens.DUMP, pos.copy(), pos.copy());

					case "store":
						return new Token(Token.tokens.STORE, pos.copy(), pos.copy());

					case "load":
						return new Token(Token.tokens.LOAD, pos.copy(), pos.copy());

					case "fetch":
						return new Token(Token.tokens.FETCH, pos.copy(), pos.copy());
				}
			}
		}

		switch (word) {
			case "True":
				return new Token(Token.tokens.BOOL, "true");
			case "False":
				return new Token(Token.tokens.BOOL, "false");
		}

		Position pos_end = pos;
		return new Token(Token.tokens.VAR, word, pos_start, pos_end);

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
