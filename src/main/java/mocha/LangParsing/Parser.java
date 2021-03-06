package mocha.LangParsing;

import java.util.ArrayList;

import mocha.Error;
import mocha.Token;
import mocha.Interpreter.Interpreter;

public class Parser {
	private final ArrayList<Token> tokens;
	private Token currentToke;
	private int toke_index;

	private Interpreter interpreter = new Interpreter();

	public Parser(ArrayList<Token> tokens) {
		this.tokens = tokens;
		this.toke_index = -1;
		advance();
	}

	public void parse() {
		int current_while = -1;
		Procedure do_proc = null;
		while (toke_index < tokens.size()) {

			Enum<Token.tokens> toke_type = currentToke.getType();

			if (toke_type == Token.tokens.INT || toke_type == Token.tokens.FLOAT
					|| toke_type == Token.tokens.BOOL || toke_type == Token.tokens.STRING) {
				interpreter.PUSH(currentToke);
				advance();
			} else if (toke_type == Token.tokens.PLUS) {
				if (!interpreter.PLUS()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Not enough values atop of the stack.");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			} else if (toke_type == Token.tokens.MINUS) {
				if (!interpreter.MINUS()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Not enough values atop of the stack.");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			} else if (toke_type == Token.tokens.MUL) {
				if (!interpreter.MUL()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Not enough values atop of the stack.");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			} else if (toke_type == Token.tokens.DIV) {
				if (!interpreter.DIV()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Not enough values atop of the stack.");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			} else if (toke_type == Token.tokens.MOD) {
				if (!interpreter.MOD()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Not enough values atop of the stack.");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			}

			else if (toke_type == Token.tokens.WHILE) {
				current_while = toke_index;
				advance();
			} else if (toke_type == Token.tokens.DO) {
				int do_res = interpreter.IF();
				if (do_res == 0) {
					int[] proc_pos = scanProcedure();
					current_while = -1;
					do_proc = null;
					if (proc_pos[0] == -1)
						System.exit(0);
				} else if (do_res == 1) {
					if (do_proc == null) {
						int[] proc_pos = scanProcedure();
						if (proc_pos[0] == -1)
							System.exit(0);
						else {
							ArrayList<Token> proc = makeProcedure(proc_pos);
							do_proc = new Procedure(proc, this.interpreter);
							do_proc.parseProcedure();
							this.toke_index = current_while;

						}
					} else {
						do_proc.parseProcedure();
						this.toke_index = current_while;
					}

				} else if (do_res == 2) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "No boolean on top of the stack.");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}

				advance();

			}

			else if (toke_type == Token.tokens.IF) {
				int if_res = interpreter.IF();
				if (if_res == 0) {
					int[] proc_pos = scanProcedure();
					if (proc_pos[0] == -1)
						System.exit(0);

					Token next = this.tokens.get(toke_index);

					try {
						next = this.tokens.get(toke_index + 1);
					} catch (Exception ignored) {
					}

					if (next.getType() == Token.tokens.ELSE) {
						advance();
						int[] else_proc_pos = scanProcedure();
						ArrayList<Token> proc = makeProcedure(else_proc_pos);
						Procedure procedure = new Procedure(proc, this.interpreter);
						procedure.parseProcedure();
					} else if (next.getType() == Token.tokens.UNLESS) {
						advance();
					}

					if (proc_pos[0] == -1)
						System.exit(0);
				} else if (if_res == 1) {
					int[] proc_pos = scanProcedure();
					if (proc_pos[0] == -1)
						System.exit(0);
					else {
						ArrayList<Token> proc = makeProcedure(proc_pos);
						Procedure procedure = new Procedure(proc, this.interpreter);
						procedure.parseProcedure();

						Token next = this.tokens.get(toke_index);

						try {
							next = this.tokens.get(toke_index + 1);
						} catch (Exception ignored) {
						}

						if (next.getType() == Token.tokens.ELSE) {
							advance();
							scanProcedure();
						} else if (next.getType() == Token.tokens.UNLESS) {
							parseUnless();
						}
					}

				} else if (if_res == 2) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "No boolean on top of the stack.");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}

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
							System.exit(0);

						}
						advance();
					}
				} catch (Exception e) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Variable has not been initialized.");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);

				}
			} else if (toke_type == Token.tokens.ASSIGN) {
				if (!interpreter.ASSIGN()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty; cannot assign values");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			}

			else if (toke_type == Token.tokens.EQ) {
				if (!interpreter.EQ()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();

			} else if (toke_type == Token.tokens.NOTEQ) {
				if (!interpreter.NOTEQ()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();

			} else if (toke_type == Token.tokens.GT) {
				if (!interpreter.GT()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			} else if (toke_type == Token.tokens.GTEQ) {
				if (!interpreter.GTEQ()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			} else if (toke_type == Token.tokens.LT) {
				if (!interpreter.LT()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			} else if (toke_type == Token.tokens.LTEQ) {
				if (!interpreter.LTEQ()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			} else if (toke_type == Token.tokens.AND) {
				if (!interpreter.AND()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Not enough booleans on stack");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			} else if (toke_type == Token.tokens.OR) {
				if (!interpreter.OR()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Not enough booleans on stack");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			}

			else if (toke_type == Token.tokens.ROT) {
				if (!interpreter.ROT()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			}

			else if (toke_type == Token.tokens.SWAP) {
				if (!interpreter.SWAP()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			}

			else if (toke_type == Token.tokens.DUP) {
				if (!interpreter.DUP()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			}

			else if (toke_type == Token.tokens.OVER) {
				if (!interpreter.OVER()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			}

			else if (toke_type == Token.tokens.DROP) {
				if (!interpreter.DROP()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			}

			else if (toke_type == Token.tokens.STORE) {
				if (!interpreter.STORE()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty; cannot store item");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			} else if (toke_type == Token.tokens.LOAD) {
				if (!interpreter.LOAD()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error",
							"Return stack is empty; cannot load item");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			} else if (toke_type == Token.tokens.FETCH) {
				if (!interpreter.FETCH()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error",
							"Return stack is empty; cannot fetch item");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			} else if (toke_type == Token.tokens.FREE) {
				if (!(this.tokens.get(toke_index - 1).getType() == Token.tokens.VAR
						&& interpreter.FREE_VAR(this.tokens.get(toke_index - 1).getValue()))) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Previous item was not a variable");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			}

			else if (toke_type == Token.tokens.PRINTLND) {
				if (!interpreter.PRINTLND()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty; no item to call");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			}

			else if (toke_type == Token.tokens.PRINTLN) {
				if (!interpreter.PRINTLN()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty; no item to call");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			}
			// TODO: Reimplement dump in std
			else if (toke_type == Token.tokens.DUMP) {
				if (!interpreter.DUMP()) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "Stack is empty; no item to call");
					System.out.println(err.InvalidSyntaxError());
					System.exit(0);
				}
				advance();
			}

			else {
				assert false;
				Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
						"Invalid Syntax Error", "invalid syntax used");
				System.out.println(err.InvalidSyntaxError());
				System.exit(0);
			}
		}

		// return interpreter.temp_print();
	}

	private void parseUnless() {
		while (currentToke.getType() != Token.tokens.IF) {
			advance();
		}
		scanProcedure();
		Token next = this.tokens.get(toke_index);

		try {
			next = this.tokens.get(toke_index + 1);
		} catch (Exception ignored) {
		}

		if (next.getType() == Token.tokens.ELSE) {
			advance();
			scanProcedure();
		} else if (next.getType() == Token.tokens.UNLESS)
			parseUnless();
	}

	private ArrayList<Token> makeProcedure(int[] proc_pos) {
		int proc_start = proc_pos[0] + 1;
		int proc_end = proc_pos[1] - 1;
		ArrayList<Token> procedure = new ArrayList<>();
		for (int i = proc_start; i < proc_end; i++) {
			procedure.add(this.tokens.get(i));
		}
		return procedure;
	}

	private int[] scanProcedure() {
		advance();
		int proc_start = toke_index;
		int proc_end = proc_start;

		int lcbrack_count = 0;
		int rcbrack_count = 0;

		if (currentToke.getType() == Token.tokens.LCBRACK) {
			lcbrack_count++;
			while (lcbrack_count > rcbrack_count) {
				advance();
				proc_end++;
				if (currentToke.getType() == Token.tokens.LCBRACK) {
					lcbrack_count++;
				} else if (currentToke.getType() == Token.tokens.RCBRACK) {
					rcbrack_count++;
				}

			}
			proc_end++;
			return new int[] { proc_start, proc_end };
		} else {
			Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
					"Invalid Syntax Error",
					"No '{' found; statement must be followed by {} to denote a procedure");
			System.out.println(err.InvalidSyntaxError());
			return new int[] { -1 };

		}
	}

	private Token advance() {
		this.toke_index++;

		if (this.toke_index < this.tokens.size()) {
			this.currentToke = this.tokens.get(toke_index);
		}
		return this.currentToke;
	}

	public void setInterpreter(Interpreter interpreter) {
		this.interpreter = interpreter;
	}
}
