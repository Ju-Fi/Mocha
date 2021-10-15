package mocha.Interpreter;

import java.util.HashMap;
import java.util.Stack;

import mocha.Token;

public class Interpreter {
	private Stack<Token> OP_STACK = new Stack<>();
	private HashMap<String, Token> DATA_VAR = new HashMap<>();

	public void PUSH(Token tok) {
		this.OP_STACK.push(tok);
	}

	public boolean IS_EMPTY() {
		return OP_STACK.empty();
	}

	public boolean PUSH_VAR(String varn) {
		Token value = DATA_VAR.get(varn);
		if (value != null) {
			this.OP_STACK.push(value);
			return true;
		} else
			return false;

	}

	// TODO Add Errors

	// Keywords

	public boolean OR() {
		if (!OP_STACK.empty()) {
			Token a = this.OP_STACK.pop();
			Token b = this.OP_STACK.pop();

			if (a.getType() == Token.tokens.BOOL && b.getType() == Token.tokens.BOOL) {
				boolean res = Boolean.parseBoolean(b.getValue()) || Boolean.parseBoolean(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			} else {
				return false;
			}

			return true;
		} else {
			return false;
		}

	}

	public boolean AND() {
		if (!OP_STACK.empty()) {
			Token a = this.OP_STACK.pop();
			Token b = this.OP_STACK.pop();

			if (a.getType() == Token.tokens.BOOL && b.getType() == Token.tokens.BOOL) {
				boolean res = Boolean.parseBoolean(b.getValue()) && Boolean.parseBoolean(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			} else {
				return false;
			}

			return true;
		} else {
			return false;
		}

	}

	public int IF() {
		if (!OP_STACK.empty()) {
			Token a = OP_STACK.pop();
			if (a.getType() == Token.tokens.BOOL) {
				if (a.getValue() == "true")
					return 1;
				else
					return 0;
			}
		}
		return 2;
	}

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

	// manipulation

	public boolean ROT() {
		if (!OP_STACK.empty()) {
			Token a = OP_STACK.pop();
			Token b = OP_STACK.pop();
			Token c = OP_STACK.pop();
			OP_STACK.push(b);
			OP_STACK.push(a);
			OP_STACK.push(c);
			return true;
		} else {
			return false;
		}
	}

	public boolean SWAP() {
		if (!OP_STACK.empty()) {
			Token a = OP_STACK.pop();
			Token b = OP_STACK.pop();
			OP_STACK.push(a);
			OP_STACK.push(b);
			return true;
		} else {
			return false;
		}
	}

	public boolean DUP() {
		if (!OP_STACK.empty()) {
			Token a = OP_STACK.pop();
			PUSH(a);
			PUSH(a);
			return true;
		} else {
			return false;
		}
	}

	public boolean DROP() {
		if (!OP_STACK.empty()) {
			OP_STACK.pop();
			return true;
		} else {
			return false;
		}
	}

	public void CLEAR() {
		this.OP_STACK.clear();
	}

	// Conditions

	public boolean NOTEQ() {
		if (OP_STACK.size() > 1) {

			Token a = this.OP_STACK.pop();
			Token b = this.OP_STACK.pop();

			// compare booleans
			if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.INT) {
				boolean res = Integer.parseInt(b.getValue()) != Integer.parseInt(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			// compare floats
			else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.FLOAT) {
				boolean res = Double.parseDouble(b.getValue()) != Double.parseDouble(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			// compare floats and booleans
			else if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.FLOAT) {
				boolean res = Integer.parseInt(b.getValue()) != Double.parseDouble(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			} else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.INT) {
				boolean res = Double.parseDouble(b.getValue()) != Integer.parseInt(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			return true;
		} else
			return false;
	}

	public boolean EQ() {
		if (OP_STACK.size() > 1) {

			Token a = this.OP_STACK.pop();
			Token b = this.OP_STACK.pop();

			// compare booleans
			if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.INT) {
				boolean res = Integer.parseInt(b.getValue()) == Integer.parseInt(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			// compare floats
			else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.FLOAT) {
				boolean res = Double.parseDouble(b.getValue()) == Double.parseDouble(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			// compare floats and booleans
			else if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.FLOAT) {
				boolean res = Integer.parseInt(b.getValue()) == Double.parseDouble(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			} else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.INT) {
				boolean res = Double.parseDouble(b.getValue()) == Integer.parseInt(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			return true;
		} else
			return false;
	}

	public boolean GTEQ() {
		if (OP_STACK.size() > 1) {

			Token a = this.OP_STACK.pop();
			Token b = this.OP_STACK.pop();

			// compare booleans
			if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.INT) {
				boolean res = Integer.parseInt(b.getValue()) >= Integer.parseInt(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			// compare floats
			else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.FLOAT) {
				boolean res = Double.parseDouble(b.getValue()) >= Double.parseDouble(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			// compare floats and booleans
			else if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.FLOAT) {
				boolean res = Integer.parseInt(b.getValue()) >= Double.parseDouble(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			} else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.INT) {
				boolean res = Double.parseDouble(b.getValue()) >= Integer.parseInt(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			return true;
		} else
			return false;
	}

	public boolean LTEQ() {
		if (OP_STACK.size() > 1) {

			Token a = this.OP_STACK.pop();
			Token b = this.OP_STACK.pop();

			// compare booleans
			if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.INT) {
				boolean res = Integer.parseInt(b.getValue()) <= Integer.parseInt(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			// compare floats
			else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.FLOAT) {
				boolean res = Double.parseDouble(b.getValue()) <= Double.parseDouble(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			// compare floats and booleans
			else if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.FLOAT) {
				boolean res = Integer.parseInt(b.getValue()) <= Double.parseDouble(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			} else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.INT) {
				boolean res = Double.parseDouble(b.getValue()) <= Integer.parseInt(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			return true;
		} else
			return false;
	}

	public boolean LT() {
		if (OP_STACK.size() > 1) {

			Token a = this.OP_STACK.pop();
			Token b = this.OP_STACK.pop();

			// compare booleans
			if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.INT) {
				boolean res = Integer.parseInt(b.getValue()) < Integer.parseInt(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			// compare floats
			else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.FLOAT) {
				boolean res = Double.parseDouble(b.getValue()) < Double.parseDouble(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			// compare floats and booleans
			else if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.FLOAT) {
				boolean res = Integer.parseInt(b.getValue()) < Double.parseDouble(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			} else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.INT) {
				boolean res = Double.parseDouble(b.getValue()) < Integer.parseInt(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			return true;
		} else
			return false;
	}

	public boolean GT() {
		if (OP_STACK.size() > 1) {

			Token a = this.OP_STACK.pop();
			Token b = this.OP_STACK.pop();

			// compare booleans
			if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.INT) {
				boolean res = Integer.parseInt(b.getValue()) > Integer.parseInt(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			// compare floats
			else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.FLOAT) {
				boolean res = Double.parseDouble(b.getValue()) > Double.parseDouble(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			// compare floats and booleans
			else if (a.getType() == Token.tokens.INT && b.getType() == Token.tokens.FLOAT) {
				boolean res = Integer.parseInt(b.getValue()) > Double.parseDouble(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			} else if (a.getType() == Token.tokens.FLOAT && b.getType() == Token.tokens.INT) {
				boolean res = Double.parseDouble(b.getValue()) > Integer.parseInt(a.getValue());
				this.OP_STACK.push(new Token(Token.tokens.BOOL, Boolean.toString(res)));
			}
			return true;
		} else
			return false;
	}

	// Operations

	public boolean ASSIGN() {
		if (OP_STACK.size() > 1) {
			String var_name = OP_STACK.pop().getValue();
			Token value = OP_STACK.pop();
			DATA_VAR.put(var_name, value);
			return true;
		} else
			return false;
	}

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
