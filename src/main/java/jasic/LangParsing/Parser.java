package jasic.LangParsing;

import java.util.ArrayList;

import jasic.Error;
import jasic.token;
import jasic.Interpreter.Interpreter;

public class Parser {
	private ArrayList<token> tokens;
	private token currentToke;
	private int toke_index;

	private Interpreter interpreter = new Interpreter();

	public Parser(ArrayList<token> tokens) {
		this.tokens = tokens;
		this.toke_index = -1;
		advance();
	}

	public void parse() {
		while (toke_index < tokens.size()) {

			Enum<token.tokens> toke_type = currentToke.getType();

			if (toke_type == token.tokens.INT || toke_type == token.tokens.FLOAT) {
				interpreter.PUSH(currentToke);
				advance();
			} else if (toke_type == token.tokens.PLUS) {
				interpreter.PLUS();
				advance();
			} else if (toke_type == token.tokens.MINUS) {
				interpreter.MINUS();
				advance();
			} else if (toke_type == token.tokens.MUL) {
				interpreter.MUL();
				advance();
			} else if (toke_type == token.tokens.DIV) {
				interpreter.DIV();
				advance();
			} else if (toke_type == token.tokens.MOD) {
				interpreter.MOD();
				advance();
			} else if (toke_type == token.tokens.TERM) {
				interpreter.TERM();
				advance();

			} else if (toke_type == token.tokens.PRINTLN) {
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

	private token advance() {
		this.toke_index++;

		if (this.toke_index < this.tokens.size()) {
			this.currentToke = this.tokens.get(toke_index);
		}
		return this.currentToke;
	}
}
