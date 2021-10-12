package mocha.LangParsing;

import java.util.ArrayList;

import mocha.Token;
import mocha.Interpreter.Interpreter;

public class Procedure {
	private Interpreter interpreter;
	private ArrayList<Token> tokens;

	public Procedure(ArrayList<Token> tokens, Interpreter interpreter) {
		this.interpreter = interpreter;
		this.tokens = tokens;
	}

	public void parseProcedure() {
		Parser parser = new Parser(tokens);
		parser.setInterpreter(this.interpreter);
		/*
		 * for (Token t : this.tokens) { System.out.println(t.repr()); }
		 */
		parser.parse();
	}
}
