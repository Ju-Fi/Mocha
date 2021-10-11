package mocha.LangParsing;

import java.util.ArrayList;

import mocha.Error;
import mocha.Token;
import mocha.Interpreter.Interpreter;

public class Parser {
	private ArrayList<Token> tokens;
	private Token currentToke;
	private int toke_index;

	private Interpreter interpreter = new Interpreter();

	public Parser(ArrayList<Token> tokens) {
		this.tokens = tokens;
		this.toke_index = -1;
		advance();
	}

	public void parse() {
		while (toke_index < tokens.size()) {

			Enum<Token.tokens> toke_type = currentToke.getType();

			if (toke_type == Token.tokens.INT || toke_type == Token.tokens.FLOAT
					|| toke_type == Token.tokens.BOOL) {
				interpreter.PUSH(currentToke);
				advance();
			} else if (toke_type == Token.tokens.PLUS) {
				interpreter.PLUS();
				advance();
			} else if (toke_type == Token.tokens.MINUS) {
				interpreter.MINUS();
				advance();
			} else if (toke_type == Token.tokens.MUL) {
				interpreter.MUL();
				advance();
			} else if (toke_type == Token.tokens.DIV) {
				interpreter.DIV();
				advance();
			} else if (toke_type == Token.tokens.MOD) {
				interpreter.MOD();
				advance();
			}

			else if (toke_type == Token.tokens.VAR) {
				try {
					if (!interpreter.IS_EMPTY() && this.tokens.get(toke_index + 1)
							.getType() == Token.tokens.ASSIGN) {
						interpreter.PUSH(currentToke);
						advance();
					} else {
						if (!interpreter.PUSH_VAR(currentToke.getValue())) {
							Error err = new Error(currentToke.getPosStart(),
									currentToke.getPosEnd(), "Invalid Syntax Error",
									"Variable has not been initialized.");
							System.out.println(err.InvalidSyntaxError());
							break;

						}
						advance();
					}
				} catch (Exception e) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Variable has not been initialized.");
					System.out.println(err.InvalidSyntaxError());
					break;

				}
			} else if (toke_type == Token.tokens.ASSIGN) {
				if (!interpreter.ASSIGN()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty; cannot assign values");
					System.out.println(err.InvalidSyntaxError());
					break;
				}
				advance();
			}

			else if (toke_type == Token.tokens.EQ) {
				if (!interpreter.EQ()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					break;
				}
				advance();

			} else if (toke_type == Token.tokens.NOTEQ) {
				if (!interpreter.NOTEQ()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					break;
				}
				advance();

			} else if (toke_type == Token.tokens.GT) {
				if (!interpreter.GT()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					break;
				}
				advance();
			} else if (toke_type == Token.tokens.GTEQ) {
				if (!interpreter.GTEQ()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					break;
				}
				advance();
			} else if (toke_type == Token.tokens.LT) {
				if (!interpreter.LT()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					break;
				}
				advance();
			} else if (toke_type == Token.tokens.LTEQ) {
				if (!interpreter.LTEQ()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					break;
				}
				advance();
			}

			else if (toke_type == Token.tokens.VAR) {
				assert false;
				System.out.println("Variables not implemented yet");
				break;
			}

			else if (toke_type == Token.tokens.ROT) {
				if (!interpreter.ROT()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					break;
				}
				advance();
			}

			else if (toke_type == Token.tokens.SWAP) {
				if (!interpreter.SWAP()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					break;
				}
				advance();
			}

			else if (toke_type == Token.tokens.DUP) {
				if (!interpreter.DUP()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					break;
				}
				advance();
			}

			else if (toke_type == Token.tokens.DROP) {
				if (!interpreter.DROP()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					break;
				}
				advance();
			}

			else if (toke_type == Token.tokens.PRINTLND) {
				if (!interpreter.PRINTLND()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty; no item to call");
					System.out.println(err.InvalidSyntaxError());
					break;
				}
				advance();
			}

			else if (toke_type == Token.tokens.PRINTLN) {
				if (!interpreter.PRINTLN()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty; no item to call");
					System.out.println(err.InvalidSyntaxError());
					break;
				}
				advance();
			}

			else {
				assert false;
				Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
						"Invalid Syntax Error", "invalid syntax used");
				System.out.println(err.InvalidSyntaxError());
				break;
			}
		}

		// return interpreter.temp_print();
	}

	private Token advance() {
		this.toke_index++;

		if (this.toke_index < this.tokens.size()) {
			this.currentToke = this.tokens.get(toke_index);
		}
		return this.currentToke;
	}
}
