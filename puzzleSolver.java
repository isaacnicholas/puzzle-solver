import java.util.Queue;
import java.util.Hashtable;
import java.util.LinkedList;

public class puzzleSolver {
	static int max = Integer.MAX_VALUE;
	static puzzleNode answer;
	static puzzleNode top;
	static Hashtable<String, puzzle> list = new Hashtable<String, puzzle>();

	public static void solveBreadth(puzzle p) {
		list.clear();
		answer=null;
		// if it's already solved, just print it
		if (p.checker() == true) {
			System.out.println(p.toString());
		} else {
			// I'm using a queue to keep track of nodes correctly
			// It will always go through the tree correctly
			Queue<puzzleNode> q = new LinkedList<puzzleNode>();
			// is the puzzle solved? This breaks the while loop and the rest of
			// it
			boolean solved = false;
			// I need to save the beginning for printing later
			top = new puzzleNode(p);
			//then add a first piece to the queue
			q.add(top);
			while (!q.isEmpty() && solved == false) {
				puzzleNode pn = q.poll();
				// If it's on the left side, then I can't move left
				if (pn.p.left() == false) {
					puzzle pl = new puzzle(pn.p);
					pl.moveLeft();
					// If I haven't already found it, save it
					if (duplicateCheck(pl) == false) {
						pn.setLeft(new puzzleNode(pl));
						pn.left.setPrevious(pn);
						q.add(pn.left);
						// and see if it's the answer
						// I could put this at the beginning, but this runs much
						// faster since the queue grows exponentially
						if (pn.left.p.checker() == true) {
							solved = true;
							printAnswer(pn.left, top);
						}
					}
				}
				//and more of the same for right, top, and bottom
				if (pn.p.right() == false) {
					puzzle pl = new puzzle(pn.p);
					pl.moveRight();
					if (duplicateCheck(pl) == false) {
						pn.setRight(new puzzleNode(pl));
						pn.right.setPrevious(pn);
						q.add(pn.right);
						if (pn.right.p.checker() == true) {
							solved = true;
							printAnswer(pn.right, top);
						}
					}
				}
				if (pn.p.top() == false) {
					puzzle pl = new puzzle(pn.p);
					pl.moveUp();
					if (duplicateCheck(pl) == false) {
						pn.setTop(new puzzleNode(pl));
						pn.top.setPrevious(pn);
						q.add(pn.top);
						if (pn.top.p.checker() == true) {
							solved = true;
							printAnswer(pn.top, top);
						}
					}
				}
				if (pn.p.bottom() == false) {
					puzzle pl = new puzzle(pn.p);
					pl.moveDown();
					if (duplicateCheck(pl) == false) {
						pn.setBottom(new puzzleNode(pl));
						pn.bottom.setPrevious(pn);
						q.add(pn.bottom);
						if (pn.bottom.p.checker() == true) {
							solved = true;
							printAnswer(pn.bottom, top);
						}
					}
				}
			}
		}
	}

	//the default solveDepth, all you give it is a puzzle to solve.
	public static void solveDepth(puzzle p) {
		list.clear();
		answer=null;
		//This is the max amount of recursions (or steps away) that can be solved for. Note that this number won't be reached because of stack overflow exceptions
		max=Integer.MAX_VALUE;
		top = new puzzleNode(p);
		//goes to the recursion
		solveDepth(top, 1);
		if (answer != null) {
			printAnswer(answer, top);
		}
	}

	// this one gives a max recursion value. This is handy since it probably
	// won't be incredibly far away and won't go straight to the max possible
	public static void solveDepth(puzzle p, int maximum) {
		list.clear();
		answer=null;
		top = new puzzleNode(p);
		max = maximum;
			solveDepth(top, 1);
		if (answer != null) {
			printAnswer(answer, top);
		}
	}
	//This is the recursion piece. This is actualy what does the solving
	// I need what node I'm checking, where the top is (for printing), what
	// level of recursion I am at, and how far I can go
	private static boolean solveDepth(puzzleNode pn, int level) {
		// false until proven true
		boolean solved = false;
		// if it's the correct answer, print it and return true
		if (pn.p.checker() == true) {
			answer = pn;
			return true;
		} else {
			++level;
			// if it's not on the left, has yet to be solved, and not maxing out
			// the recursion level
			if (pn.p.left() == false && solved == false && level < max) {
				puzzle pl = new puzzle(pn.p);
				pl.moveLeft();
				// check, once again for duplicates
				if (duplicateCheck(pl) == false) {
					puzzleNode pln = new puzzleNode(pl);
					pn.setLeft(pln);
					pln.setPrevious(pn);
					// if I run out of space in the stack, then don't do it
					try {
						// if I solved it, than it will pass through the solved
						// boolean
						solved = solveDepth(pln, level);
					} catch (StackOverflowError e) {
						//However, if I go as far as I can go, I want to delete the current and next pieced from the stack so I can reach them again (in case they are one step away)
						list.remove(pn.p.toString());
						list.remove(pln.p.toString());
					}

				}
			}
			//once again, more of the same for top, right, and bottom
			if (pn.p.top() == false && solved == false && level < max) {
				puzzle pl = new puzzle(pn.p);
				pl.moveUp();
				if (duplicateCheck(pl) == false) {
					puzzleNode pln = new puzzleNode(pl);
					pn.setTop(pln);
					pln.setPrevious(pn);
					try {
						solved = solveDepth(pln, level);
					} catch (StackOverflowError e) {
						list.remove(pn.p.toString());
						list.remove(pln.p.toString());
					}
				}
			}
			if (pn.p.right() == false && solved == false && level < max) {
				puzzle pl = new puzzle(pn.p);
				pl.moveRight();
				if (duplicateCheck(pl) == false) {
					puzzleNode pln = new puzzleNode(pl);
					pn.setRight(pln);
					pln.setPrevious(pn);
					try {
						solved = solveDepth(pln, level);
					} catch (StackOverflowError e) {
						list.remove(pn.p.toString());
						list.remove(pln.p.toString());
					}
					}
			}
			if (pn.p.bottom() == false && solved == false && level < max) {
				puzzle pl = new puzzle(pn.p);
				pl.moveDown();
				if (duplicateCheck(pl) == false) {
					puzzleNode pln = new puzzleNode(pl);
					pn.setBottom(pln);
					pln.setPrevious(pn);
					try {
						solved = solveDepth(pln, level);
					} catch (StackOverflowError e) {
						list.remove(pn.p.toString());
						list.remove(pln.p.toString());
					}
				}
			}
		}
		return solved;
	}

	//this looks through the stack to see if there is any puzzle piece like it
	private static boolean duplicateCheck(puzzle p) {
		if (list.containsKey(p.toString()))
			//if there is one, say so by returning true
			return true;
		else {
			//if there isn't, add it and return false
			list.put(p.toString(), p);
			return false;
		}
		
	}

	private static void printAnswer(puzzleNode end, puzzleNode beginning) {
		// I need to start with the solved node and go back through and mark all
		// as the correct path
		markTrue(end);
		// then follow that path back down and print the elements
		printElements(beginning);
	}

	private static void markTrue(puzzleNode pn) {
		if (pn.previous != null) {
			pn.correctPath = true;
			markTrue(pn.previous);
		}
	}

	private static void printElements(puzzleNode pn) {
		System.out.print(pn.p.toString());
		if (pn.left != null) {
			if (pn.left.correctPath == true) {
				printElements(pn.left);
			}
		}
		if (pn.right != null) {
			if (pn.right.correctPath == true) {
				printElements(pn.right);
			}
		}
		if (pn.top != null) {
			if (pn.top.correctPath == true) {
				printElements(pn.top);
			}
		}
		if (pn.bottom != null) {
			if (pn.bottom.correctPath == true) {
				printElements(pn.bottom);
			}
		}
	}
}