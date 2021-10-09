package jasic.Interpreter;

import java.util.Stack;

import jasic.token;
import jasic.Error;

public class Interpreter {
	private Stack<token> OP_STACK = new Stack<>();

	public void PUSH(token tok) {
		this.OP_STACK.push(tok);
	}

	// TODO Add Errors

	public void TERM() {
		this.OP_STACK.clear();
	}

	// Keywords
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
		token a = new token(token.tokens.INT, "0");
		token b = new token(token.tokens.INT, "0");
		try {
			a = this.OP_STACK.pop();
			b = this.OP_STACK.pop();
		} catch (Exception e) {
		}
		// mod ints
		if (a.getType() == token.tokens.INT && b.getType() == token.tokens.INT) {
			int res = Integer.parseInt(a.getValue()) % Integer.parseInt(b.getValue());
			this.OP_STACK.push(new token(token.tokens.INT, Integer.toString(res)));
		}
		// mod floats
		else if (a.getType() == token.tokens.FLOAT && b.getType() == token.tokens.FLOAT) {
			double res = Double.parseDouble(a.getValue()) % Double.parseDouble(b.getValue());
			this.OP_STACK.push(new token(token.tokens.FLOAT, Double.toString(res)));
		}
		// mod floats and ints
		else if (a.getType() == token.tokens.INT && b.getType() == token.tokens.FLOAT) {
			double res = Integer.parseInt(a.getValue()) % Double.parseDouble(b.getValue());
			this.OP_STACK.push(new token(token.tokens.FLOAT, Double.toString(res)));
		} else if (a.getType() == token.tokens.FLOAT && b.getType() == token.tokens.INT) {
			double res = Double.parseDouble(a.getValue()) % Integer.parseInt(b.getValue());
			this.OP_STACK.push(new token(token.tokens.FLOAT, Double.toString(res)));
		}

	}

	public void DIV() {
		token a = new token(token.tokens.INT, "0");
		token b = new token(token.tokens.INT, "0");
		try {
			a = this.OP_STACK.pop();
			b = this.OP_STACK.pop();
		} catch (Exception e) {
		}
		// divide ints
		if (a.getType() == token.tokens.INT && b.getType() == token.tokens.INT) {
			int res = Integer.parseInt(a.getValue()) / Integer.parseInt(b.getValue());
			this.OP_STACK.push(new token(token.tokens.INT, Integer.toString(res)));
		}
		// divide floats
		else if (a.getType() == token.tokens.FLOAT && b.getType() == token.tokens.FLOAT) {
			double res = Double.parseDouble(a.getValue()) / Double.parseDouble(b.getValue());
			this.OP_STACK.push(new token(token.tokens.FLOAT, Double.toString(res)));
		}
		// divide floats and ints
		else if (a.getType() == token.tokens.INT && b.getType() == token.tokens.FLOAT) {
			double res = Integer.parseInt(a.getValue()) / Double.parseDouble(b.getValue());
			this.OP_STACK.push(new token(token.tokens.FLOAT, Double.toString(res)));
		} else if (a.getType() == token.tokens.FLOAT && b.getType() == token.tokens.INT) {
			double res = Double.parseDouble(a.getValue()) / Integer.parseInt(b.getValue());
			this.OP_STACK.push(new token(token.tokens.FLOAT, Double.toString(res)));
		}

	}

	public void MUL() {
		token a = new token(token.tokens.INT, "0");
		token b = new token(token.tokens.INT, "0");
		try {
			a = this.OP_STACK.pop();
			b = this.OP_STACK.pop();
		} catch (Exception e) {
		}
		// multiply ints
		if (a.getType() == token.tokens.INT && b.getType() == token.tokens.INT) {
			int res = Integer.parseInt(a.getValue()) * Integer.parseInt(b.getValue());
			this.OP_STACK.push(new token(token.tokens.INT, Integer.toString(res)));
		}
		// multiply floats
		else if (a.getType() == token.tokens.FLOAT && b.getType() == token.tokens.FLOAT) {
			double res = Double.parseDouble(a.getValue()) * Double.parseDouble(b.getValue());
			this.OP_STACK.push(new token(token.tokens.FLOAT, Double.toString(res)));
		}
		// multiply floats and ints
		else if (a.getType() == token.tokens.INT && b.getType() == token.tokens.FLOAT) {
			double res = Integer.parseInt(a.getValue()) * Double.parseDouble(b.getValue());
			this.OP_STACK.push(new token(token.tokens.FLOAT, Double.toString(res)));
		} else if (a.getType() == token.tokens.FLOAT && b.getType() == token.tokens.INT) {
			double res = Double.parseDouble(a.getValue()) * Integer.parseInt(b.getValue());
			this.OP_STACK.push(new token(token.tokens.FLOAT, Double.toString(res)));
		}

	}

	public void MINUS() {
		token a = new token(token.tokens.INT, "0");
		token b = new token(token.tokens.INT, "0");
		try {
			a = this.OP_STACK.pop();
			b = this.OP_STACK.pop();
		} catch (Exception e) {
		}
		// subtract ints
		if (a.getType() == token.tokens.INT && b.getType() == token.tokens.INT) {
			int res = Integer.parseInt(a.getValue()) - Integer.parseInt(b.getValue());
			this.OP_STACK.push(new token(token.tokens.INT, Integer.toString(res)));
		}
		// subtract floats
		else if (a.getType() == token.tokens.FLOAT && b.getType() == token.tokens.FLOAT) {
			double res = Double.parseDouble(a.getValue()) - Double.parseDouble(b.getValue());
			this.OP_STACK.push(new token(token.tokens.FLOAT, Double.toString(res)));
		}
		// subtract floats and ints
		else if (a.getType() == token.tokens.INT && b.getType() == token.tokens.FLOAT) {
			double res = Integer.parseInt(a.getValue()) - Double.parseDouble(b.getValue());
			this.OP_STACK.push(new token(token.tokens.FLOAT, Double.toString(res)));
		} else if (a.getType() == token.tokens.FLOAT && b.getType() == token.tokens.INT) {
			double res = Double.parseDouble(a.getValue()) - Integer.parseInt(b.getValue());
			this.OP_STACK.push(new token(token.tokens.FLOAT, Double.toString(res)));
		}

	}

	public void PLUS() {
		token a = new token(token.tokens.INT, "0");
		token b = new token(token.tokens.INT, "0");
		try {
			a = this.OP_STACK.pop();
			b = this.OP_STACK.pop();
		} catch (Exception e) {
		}
		// add ints
		if (a.getType() == token.tokens.INT && b.getType() == token.tokens.INT) {
			int res = Integer.parseInt(a.getValue()) + Integer.parseInt(b.getValue());
			this.OP_STACK.push(new token(token.tokens.INT, Integer.toString(res)));
		}
		// add floats
		else if (a.getType() == token.tokens.FLOAT && b.getType() == token.tokens.FLOAT) {
			double res = Double.parseDouble(a.getValue()) + Double.parseDouble(b.getValue());
			this.OP_STACK.push(new token(token.tokens.FLOAT, Double.toString(res)));
		}
		// add floats and ints
		else if (a.getType() == token.tokens.INT && b.getType() == token.tokens.FLOAT) {
			double res = Integer.parseInt(a.getValue()) + Double.parseDouble(b.getValue());
			this.OP_STACK.push(new token(token.tokens.FLOAT, Double.toString(res)));
		} else if (a.getType() == token.tokens.FLOAT && b.getType() == token.tokens.INT) {
			double res = Double.parseDouble(a.getValue()) + Integer.parseInt(b.getValue());
			this.OP_STACK.push(new token(token.tokens.FLOAT, Double.toString(res)));
		}

	}

	public String temp_print() {
		return this.OP_STACK.peek().repr();
	}
}
