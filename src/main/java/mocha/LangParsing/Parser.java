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

			if (toke_type == Token.tokens.INT || toke_type == Token.tokens.FLOAT) {
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
			} else if (toke_type == Token.tokens.PRINTLND) {
				if (!interpreter.PRINTLND()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty; no item to call");
					System.out.println(err.InvalidSyntaxError());
				}
				advance();
			}

			else if (toke_type == Token.tokens.PRINTLN) {
				if (!interpreter.PRINTLN()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty; no item to call");
					System.out.println(err.InvalidSyntaxError());
				}
				advance();
			}

			else {
				assert false;
				Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
						"Invalid Syntax Error", "invalid syntax used");
				System.out.println(err.InvalidSyntaxError());
				advance();
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
