//this is a double linked list. It keeps track of each node and where its going and where it came from
public class puzzleNode {
	public puzzle p;
	public boolean correctPath;
	public puzzleNode left;
	public puzzleNode right;
	public puzzleNode top;
	public puzzleNode bottom;
	public puzzleNode previous;

	public puzzleNode(int[] i) {
		p = new puzzle(i);
	}

	public puzzleNode(puzzle pu) {
		p = pu;
	}

	public void setCorrect(boolean b) {
		correctPath = b;
	}

	public void setLeft(puzzleNode p) {
		left = p;
	}

	public void setRight(puzzleNode p) {
		right = p;
	}

	public void setTop(puzzleNode p) {
		top = p;
	}

	public void setBottom(puzzleNode p) {
		bottom = p;
	}

	public void setPrevious(puzzleNode p) {
		previous = p;
	}
}