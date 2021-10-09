package jasic;

public class Position {
	public int index;
	public int line;
	public int column;
	private String fileName;

	public Position(int idx, int ln, int col) {
		index = idx;
		line = ln;
		column = col;
		// fileName = fn;
	}

	public void advance(char currentChar) {
		this.index++;
		this.column++;

		if (currentChar == '\n') {
			this.line++;
			this.column = 0;
		}
	}

	public int[] copy() {
		int[] position = { this.index, this.line, this.column };
		return position;
	}
}
