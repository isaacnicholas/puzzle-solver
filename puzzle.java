import java.lang.Math;
import java.lang.IllegalArgumentException;
import java.lang.StringBuilder;

public class puzzle {
	public int[] box;
	public int l;
	public int i;

	public puzzle() {
		this(8);
	}

	public puzzle(int newi) {
		i = newi;
		// see if the number will work
		i = i + 1;
		double isq = Math.sqrt(i);
		isq = isq % 1;
		if (isq != 0) {
			throw new IllegalArgumentException("");
		} else {
			// if it works, set the size of the array and the height/width
			l = (int) Math.sqrt(i);
			box = new int[i];
			for (int cnt = 0; cnt < i; cnt++) {
				box[cnt] = cnt;
			}
		}
	}

	public puzzle(int[] array) {
		// Once, again, check the size
		i = array.length;
		double isq = Math.sqrt(i);
		isq = isq % 1;
		if (isq != 0) {
			throw new IllegalArgumentException("");
		} else {
			// And to check for valid values
			for (int cnt = 0; cnt < i; cnt++) {
				boolean correctvalue = false;
				for (int cnt2 = 0; cnt2 < i; cnt2++) {
					if (array[cnt2] == cnt)
						correctvalue = true;
				}
				if (correctvalue == false)
					throw new IllegalArgumentException("");
			}
			l = (int) Math.sqrt(i);
			box = array;
		}
	}

	// Creates a puzzle from an already existing puzzle (that way we can modify
	// a new one)
	public puzzle(puzzle p) {
		int[] newbox;
		newbox = new int[p.box.length];
		for (int cnt = 0; cnt < newbox.length; cnt++)
			newbox[cnt] = p.box[cnt];
		this.box = newbox;
		this.l = p.l;
		this.i = p.i;
	}

	// The next set of commands return its place
	// ex. if it is at the top left, return true
	public boolean topLeft() {
		int zero = findZero();
		if (zero == 0)
			return true;
		else
			return false;
	}

	public boolean bottomLeft() {
		int zero = findZero();
		if (zero == i - l)
			return true;
		else
			return false;
	}

	public boolean topRight() {
		int zero = findZero();
		if (zero == i / l - 1)
			return true;
		else
			return false;
	}

	public boolean bottomRight() {
		int zero = findZero();
		if ((zero + 1) % l == 0 && zero + l > i)
			return true;
		else
			return false;
	}

	public boolean left() {
		int zero = findZero();
		if ((zero + 1) % l == 1)
			return true;
		else
			return false;
	}

	public boolean right() {
		int zero = findZero();
		if ((zero) % l == l - 1)
			return true;
		else
			return false;
	}

	public boolean top() {
		int zero = findZero();
		if (zero - l < 0)
			return true;
		else
			return false;
	}

	public boolean bottom() {
		int zero = findZero();
		if (zero + l > i - 1)
			return true;
		else
			return false;
	}

	public void shuffle(int count) {
		// basically a for loop
		while (count > 0) {
			// creates the random number
			double r = java.lang.Math.random();
			// top left corner
			// there are only two options, right and down, so each has a half
			// chance of being selected
			if (topLeft() == true) {
				if (r < 0.5)
					moveRight();
				else
					moveDown();
			}
			// bottom left corner
			else if (bottomLeft() == true) {
				if (r < 0.5)
					moveRight();
				else
					moveUp();
			}
			// top right corner
			else if (topRight() == true) {
				if (r < 0.5)
					moveLeft();
				else
					moveDown();
			}
			// bottom right corner
			else if (bottomRight() == true) {
				if (r < 0.5)
					moveLeft();
				else
					moveUp();
			}
			// top side
			// three options, so each has a 1/3 chance of being selected
			else if (top() == true) {
				if (r < (double) 1 / 3)
					moveLeft();
				else if (r >= (double) 2 / 3)
					moveRight();
				else
					moveDown();
			}
			// left side
			else if (left() == true) {
				if (r < (double) 1 / 3)
					moveUp();
				else if (r >= (double) 2 / 3)
					moveRight();
				else
					moveDown();
			}
			// bottom side
			else if (bottom() == true) {
				if (r < (double) 1 / 3)
					moveLeft();
				else if (r >= (double) 2 / 3)
					moveRight();
				else
					moveUp();
			}
			// right side
			else if (right() == true) {
				if (r < (double) 1 / 3)
					moveUp();
				else if (r >= (double) 2 / 3)
					moveLeft();
				else
					moveDown();
			}
			// middle
			else {
				if (r < 0.25)
					moveUp();
				else if (r >= 0.75)
					moveDown();
				else if (r >= 0.25 && r < 0.5)
					moveRight();
				else
					moveLeft();
			}
			--count;
		}
	}

	// The next set of commands actually does the moving
	// note that I don't check for a valid move since I take care of that in the
	// shuffle/solve
	public void moveLeft() {
		// find the box to move
		int zero = findZero();
		// move it to the left by one
		box[zero] = box[zero - 1];
		box[zero - 1] = 0;
	}

	public void moveRight() {
		int zero = findZero();
		box[zero] = box[zero + 1];
		box[zero + 1] = 0;
	}

	public void moveUp() {
		int zero = findZero();
		// moves it to the left the length of the puzzle, the equivalent of
		// moving it up
		box[zero] = box[zero - l];
		box[zero - l] = 0;
	}

	public void moveDown() {
		int zero = findZero();
		box[zero] = box[zero + l];
		box[zero + l] = 0;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		// if the first character is a 0, append a space to take its place
		if (box[0] == 0)
			sb.append(' ');
		else
			sb.append(box[0]);
		// add a comma and space to make it look presentable
		sb.append(", ");
		for (int cnt = 2; cnt < i; cnt++) {
			if (box[cnt - 1] == 0)
				sb.append(" ");
			else
				sb.append(box[cnt - 1]);
			if (cnt % l != 0)
				sb.append(", ");
			else
				sb.append("\n");
		}
		if (box[i - 1] > 0)
			sb.append(box[i - 1]);
		// adds two new lines to make it presentable when printing
		sb.append("\n\n");
		return sb.toString();
	}

	// checks to see if it is solved
	public boolean checker() {
		boolean solved = true;
		for (int cnt = 0; cnt < i; cnt++) {
			// if the value isn't in its place, its not solved
			if (box[cnt] != cnt) {
				solved = false;
				break;
			}
		}
		return solved;
	}

	// finds where the zero is so I know what to move
	private int findZero() {
		for (int cnt = 0; cnt < i; cnt++) {
			if (box[cnt] == 0)
				return cnt;
		}
		return -1;
	}
}