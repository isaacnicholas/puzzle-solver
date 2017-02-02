//Note: all modifiable lines have a comment out from them explaining what they can do
public class test {
	public static void main(String args[]) {
		System.out.println("Attempting to solve a puzzle with the default 8 numbers");
		puzzle p = new puzzle();
		p.shuffle(100); // change the amount of shuffeling, 100 seems to be
						// enough to completely randomize the board
		System.out.println("Current puzzle after shuffling: ");
		System.out.print(p.toString());
		System.out.println("Solving Breadth");
		puzzleSolver.solveBreadth(p);
		System.out.println("Solving Depth");
		puzzleSolver.solveDepth(p);
		System.out.println("Now trying a random puzzle of a wrong size: 12");
		try {
			new puzzle(12); // change the puzzle size. If set to a correct size,
							// nothing happens
		} catch (IllegalArgumentException e) {
			System.out.println("It worked");
		}
		System.out.println("Now trying a puzzle of 15 Characters, a good number");
		puzzle p2 = new puzzle(15); // change the N value (or size of the
									// puzzle)
		System.out.println("But first, without shuffling to make sure 1 still works");
		System.out.println("Solving as a Breadth");
		puzzleSolver.solveBreadth(p2);
		System.out.println("And Depth");
		puzzleSolver.solveDepth(p2);
		System.out.println("Now Shuffling a little");
		p2.shuffle(10); // shuffling the custom sized puzzle. Note: I chose 10
						// to make it quick, but meaningful
		System.out.println("Current puzzle after shuffling: ");
		System.out.print(p2.toString());
		System.out.println("As a Breadth");
		puzzleSolver.solveBreadth(p2);
		System.out.println("And depth, but with a depth cap of 20, signifying don't look more than 20 steps");
		puzzleSolver.solveDepth(p2, 20); // change the depth cap, though any
											// higher runs out of memory
		System.out.println("Now trying passing an array with an invalid size");
		try {
			new puzzle(new int[] { 1, 2, 0 }); // change the direct integer
												// array value. If set to a
												// correct size and value count,
												// nothing happens
		} catch (IllegalArgumentException e) {
			System.out.println("It worked");
		}
		System.out.println("Now for incorrect values");
		try {
			new puzzle(new int[] { 0, 1, 3, 1 }); // change the direct integer
													// array value. If set to a
													// correct size and value
													// count, nothing happens
		} catch (IllegalArgumentException e) {
			System.out.println("It worked");
		}
		System.out.println("And finally, a correct inputed array (size 4)");
		puzzle p3 = new puzzle(new int[] { 1, 3, 0, 2 }); // change the direct
															// integer value for
															// testing
		System.out.println("Current puzzle: ");
		System.out.print(p3.toString());
		System.out.println("Solving Breadth");
		puzzleSolver.solveBreadth(p3);
		System.out.println("Solvind Depth");
		puzzleSolver.solveDepth(p3);
		System.out.println("Finished");
	}
}
