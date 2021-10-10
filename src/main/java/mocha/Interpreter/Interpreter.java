package mocha.Interpreter;

import java.util.Stack;

import mocha.Token;

public class Interpreter {
	private Stack<Token> OP_STACK = new Stack<>();

	public void PUSH(Token tok) {
		this.OP_STACK.push(tok);
	}

	// TODO Add Errors

	public void CLEAR() {
		this.OP_STACK.clear();
	}

	// Keywords

	public boolean PRINTLND() {
		if (!OP_STACK.empty()) {
			System.out.println(OP_STACK.pop().getValue());
			return true;
		} else {
			return false;
		}
	}

	public boolean PRINTLN() {
		if (!OP_STACK.empty()) {
			System.out.println(OP_STACK.peek().getValue());
			return true;
		} else {
			return false;
		}
	}

	// Operations

	public void MOD() {
		Token a = new Token(Token.tokens.INT, "0");
		Token b = new Token(Token.tokens.INT, "0");
		try {
			a = this.OP_STACK.pop();
			b = this.OP_STACK.pop();
		} catch (Exception e) {
		}
		// mod ints
		if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.INT) {
			int res = Integer.parseInt(b.getValue()) % Integer.parseInt(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.INT, Integer.toString(res)));
		}
		// mod floats
		else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.FLOAT) {
			double res = Double.parseDouble(b.getValue()) % Double.parseDouble(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.FLOAT, Double.toString(res)));
		}
		// mod floats and ints
		else if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.FLOAT) {
			double res = Integer.parseInt(b.getValue()) % Double.parseDouble(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.FLOAT, Double.toString(res)));
		} else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.INT) {
			double res = Double.parseDouble(b.getValue()) % Integer.parseInt(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.FLOAT, Double.toString(res)));
		}

	}

	public void DIV() {
		Token a = new Token(Token.tokens.INT, "0");
		Token b = new Token(Token.tokens.INT, "0");
		try {
			a = this.OP_STACK.pop();
			b = this.OP_STACK.pop();
		} catch (Exception e) {
		}
		// divide ints
		if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.INT) {
			int res = Integer.parseInt(b.getValue()) / Integer.parseInt(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.INT, Integer.toString(res)));
		}
		// divide floats
		else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.FLOAT) {
			double res = Double.parseDouble(b.getValue()) / Double.parseDouble(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.FLOAT, Double.toString(res)));
		}
		// divide floats and ints
		else if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.FLOAT) {
			double res = Integer.parseInt(b.getValue()) / Double.parseDouble(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.FLOAT, Double.toString(res)));
		} else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.INT) {
			double res = Double.parseDouble(b.getValue()) / Integer.parseInt(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.FLOAT, Double.toString(res)));
		}

	}

	public void MUL() {
		Token a = new Token(Token.tokens.INT, "0");
		Token b = new Token(Token.tokens.INT, "0");
		try {
			a = this.OP_STACK.pop();
			b = this.OP_STACK.pop();
		} catch (Exception e) {
		}
		// multiply ints
		if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.INT) {
			int res = Integer.parseInt(b.getValue()) * Integer.parseInt(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.INT, Integer.toString(res)));
		}
		// multiply floats
		else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.FLOAT) {
			double res = Double.parseDouble(b.getValue()) * Double.parseDouble(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.FLOAT, Double.toString(res)));
		}
		// multiply floats and ints
		else if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.FLOAT) {
			double res = Integer.parseInt(b.getValue()) * Double.parseDouble(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.FLOAT, Double.toString(res)));
		} else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.INT) {
			double res = Double.parseDouble(b.getValue()) * Integer.parseInt(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.FLOAT, Double.toString(res)));
		}

	}

	public void MINUS() {
		Token a = new Token(Token.tokens.INT, "0");
		Token b = new Token(Token.tokens.INT, "0");
		try {
			a = this.OP_STACK.pop();
			b = this.OP_STACK.pop();
		} catch (Exception e) {
		}
		// subtract ints
		if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.INT) {
			int res = Integer.parseInt(b.getValue()) - Integer.parseInt(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.INT, Integer.toString(res)));
		}
		// subtract floats
		else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.FLOAT) {
			double res = Double.parseDouble(b.getValue()) - Double.parseDouble(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.FLOAT, Double.toString(res)));
		}
		// subtract floats and ints
		else if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.FLOAT) {
			double res = Integer.parseInt(b.getValue()) - Double.parseDouble(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.FLOAT, Double.toString(res)));
		} else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.INT) {
			double res = Double.parseDouble(b.getValue()) - Integer.parseInt(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.FLOAT, Double.toString(res)));
		}

	}

	public void PLUS() {
		Token a = new Token(Token.tokens.INT, "0");
		Token b = new Token(Token.tokens.INT, "0");
		try {
			a = this.OP_STACK.pop();
			b = this.OP_STACK.pop();
		} catch (Exception e) {
		}
		// add ints
		if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.INT) {
			int res = Integer.parseInt(b.getValue()) + Integer.parseInt(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.INT, Integer.toString(res)));
		}
		// add floats
		else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.FLOAT) {
			double res = Double.parseDouble(b.getValue()) + Double.parseDouble(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.FLOAT, Double.toString(res)));
		}
		// add floats and ints
		else if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.FLOAT) {
			double res = Integer.parseInt(b.getValue()) + Double.parseDouble(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.FLOAT, Double.toString(res)));
		} else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.INT) {
			double res = Double.parseDouble(b.getValue()) + Integer.parseInt(a.getValue());
			this.OP_STACK.push(new Token(Token.tokens.FLOAT, Double.toString(res)));
		}

	}

	public String temp_print() {
		return this.OP_STACK.peek().repr();
	}
}
