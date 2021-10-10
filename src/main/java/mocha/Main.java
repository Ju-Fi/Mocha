package mocha;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import mocha.LangParsing.Parser;

public class Main {
	public static void main(String[] args) throws IOException {
		if (args.length != 0) {
			if (args[0].equals("run") || args[0].equals("-r")) {
				try {
					byte[] encoded = Files.readAllBytes(Paths.get(args[1]));
					String input = new String(encoded);
					input = input.replace("\n", " ");
					run(input);
				} catch (Exception e) {
					System.out.println("Error: Invalid File Arguments");
				}
			}

			else if (args[0].equals("shell") || args[0].equals("-s")) {
				shell();
			}

			else {
				usage();
			}

		} else {
			usage();
		}
	}

	public static void shell() {
		Scanner scan = new Scanner(System.in);
		String input;
		while (true) {

			System.out.print("MOCHA > ");
			input = scan.nextLine();
			// test(input);
			run(input);
			// System.out.println(result);

		}
	}

	/*
	 * public static void test(String text) { Lexer2 lex = new Lexer2(text);
	 * ArrayList<Token> tokens = lex.make_tokens(); for (Token t : tokens) {
	 * System.out.println(t.repr()); } }
	 */
	public static void run(String text) {
		// Generate tokens
		Lexer lexer = new Lexer(text);
		ArrayList<Token> tokens = lexer.make_tokens();

		// Parse tokens
		Parser parser = new Parser(tokens);
		parser.parse();
	}

	public static void usage() {
		System.out.println("Usage: java -jar Mocha.jar <options> [directory]");
		System.out.println("\tOptions/Arguments:");
		System.out.println("\t\trun    -r : Run the *.mocha file specified");
		System.out.println("\t\tshell  -s : Open Mocha shell");
	}
}
