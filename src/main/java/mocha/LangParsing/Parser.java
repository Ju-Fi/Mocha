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
		int current_while = -1;
		Procedure do_proc = null;
		while (toke_index < tokens.size()) {

			Enum<Token.tokens> toke_type = currentToke.getType();

			if (toke_type == Token.tokens.INT || toke_type == Token.tokens.FLOAT
					|| toke_type == Token.tokens.BOOL || toke_type == Token.tokens.STRING) {
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
						break;
				} else if (do_res == 1) {
					if (do_proc == null) {
						int[] proc_pos = scanProcedure();
						if (proc_pos[0] == -1)
							break;
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
					break;
				}

				advance();

			}

			else if (toke_type == Token.tokens.IF) {
				int if_res = interpreter.IF();
				if (if_res == 0) {
					int[] proc_pos = scanProcedure();
					if (proc_pos[0] == -1)
						break;

					Token next = this.tokens.get(toke_index);

					try {
						next = this.tokens.get(toke_index + 1);
					} catch (Exception e) {
					}

					if (next.getType() == Token.tokens.ELSE) {
						advance();
						int[] else_proc_pos = scanProcedure();
						ArrayList<Token> proc = makeProcedure(else_proc_pos);
						Procedure procedure = new Procedure(proc, this.interpreter);
						procedure.parseProcedure();
					}

					if (proc_pos[0] == -1)
						break;
				} else if (if_res == 1) {
					int[] proc_pos = scanProcedure();
					if (proc_pos[0] == -1)
						break;
					else {
						ArrayList<Token> proc = makeProcedure(proc_pos);
						Procedure procedure = new Procedure(proc, this.interpreter);
						procedure.parseProcedure();

						Token next = this.tokens.get(toke_index);

						try {
							next = this.tokens.get(toke_index + 1);
						} catch (Exception e) {
						}

						if (next.getType() == Token.tokens.ELSE) {
							advance();
							scanProcedure();
						}
					}

				} else if (if_res == 2) {
					Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
							"Invalid Syntax Error", "No boolean on top of the stack.");
					System.out.println(err.InvalidSyntaxError());
					break;
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
			int[] proc_pos = { proc_start, proc_end };
			return proc_pos;
		} else {
			Error err = new Error(currentToke.getPosStart(), currentToke.getPosEnd(),
					"Invalid Syntax Error",
					"No '{' found; statement must be followed by {} to denote a procedure");
			System.out.println(err.InvalidSyntaxError());
			int[] error = { -1 };
			return error;

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
