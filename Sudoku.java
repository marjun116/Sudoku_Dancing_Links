import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class Sudoku {

	private static int boardSize = 0;
	private static int partitionSize = 0;

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		String filename = args[0];
		File inputFile = new File(filename);
		Scanner input = null;
		int[][] vals = null;
		ArrayList<Cell> valsToBeFilled = null;

		int temp = 0;
		int count = 0;

		try {
			input = new Scanner(inputFile);
			temp = input.nextInt();
			boardSize = temp;
			partitionSize = (int) Math.sqrt(boardSize);
			System.out.println("Boardsize: " + temp + "x" + temp);
			vals = new int[boardSize][boardSize];
			valsToBeFilled = new ArrayList<Cell>();
			System.out.println("Input:");
			int i = 0;
			int j = 0;
			while (input.hasNext()) {
				temp = input.nextInt();
				count++;
				System.out.printf("%3d", temp);
				vals[i][j] = temp;
				if (temp == 0) {
					valsToBeFilled.add(new Cell(i, j, 0));
				}
				j++;
				if (j == boardSize) {
					j = 0;
					i++;
					System.out.println();
				}
				if (j == boardSize) {
					break;
				}
			}
			input.close();
		} catch (FileNotFoundException exception) {
			System.out.println("Input file not found: " + filename);
		}
		if (count != boardSize * boardSize)
			throw new RuntimeException("Incorrect number of inputs.");

		long startTime = System.currentTimeMillis();
		ExactMatrix myMatrix = new ExactMatrix(vals);
		DancingLinkSolver solver = new DancingLinkSolver(myMatrix.finalMatrix, boardSize);

		// boolean solved = solve(vals, valsToBeFilled, currCell);
		// long stopTime = System.currentTimeMillis();
		// long elapsedTime = stopTime - startTime;
		// System.out.println(elapsedTime);
		//
		// // Output
		// if (!solved) {
		// System.out.println("No solution found.");
		// return;
		// }
		//
		System.out.println("\nOutput\n");
		StringBuilder solutionFilename = new StringBuilder(filename.substring(4, filename.length() - 4));
		solutionFilename.append("Solution");
		String finalName = solutionFilename.toString();
		System.out.println(solutionFilename);
		try {
			PrintWriter writer = new PrintWriter(finalName, "UTF-8");
			writer.print(boardSize);
			writer.println();
			for (int i = 0; i < solver.solution.length; i++) {
				for (int j = 0; j < solver.solution.length; j++) {
					writer.print(solver.solution[i][j]+ " ");
				}
				writer.println();
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("Failed to create file");
		}
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				System.out.printf("%3d", solver.solution[i][j]);
			}
			System.out.println();
		}

		// }
		//
		// public static boolean solve(int[][] vals, ArrayList<Cell>
		// valsToBeFilled, int currCell){
		// int row = valsToBeFilled.get(currCell).row;
		// int col = valsToBeFilled.get(currCell).col;
		// int number = valsToBeFilled.get(currCell).number;
		// boolean check = false;
		// while (number < boardSize) {
		// number++;
		// vals[row][col] = number;
		// if (checkRow(vals, row, col, number) && checkColumn(vals, row, col,
		// number) &&checkGrid(vals, row, col, number)) {
		// valsToBeFilled.get(currCell).number = number;
		// if (currCell+1 == valsToBeFilled.size()){
		// return true;
		// }
		// check = solve(vals, valsToBeFilled, currCell+1);
		// }
		// if (check) return true;
		// }
		// valsToBeFilled.get(currCell).number=0;
		// vals[row][col] = 0;
		// return false;
		// }
		//
		// private static boolean checkGrid(int[][] vals, int row, int col, int
		// number) {
		//
		// int uLimitRow = partitionSize;
		// int uLimitCol = partitionSize;
		// while (uLimitRow < vals.length) {
		// if (row < uLimitRow) {
		// break;
		// }
		// uLimitRow += partitionSize;
		// }
		// while (uLimitCol < vals.length) {
		// if (col < uLimitCol) {
		// break;
		// }
		// uLimitCol += partitionSize;
		// }
		//
		// for (int i = uLimitRow-partitionSize; i < uLimitRow; i++) {
		// for (int j = uLimitCol-partitionSize; j < uLimitCol; j++) {
		// if (i != row && j !=col) {
		// if (vals[i][j]==number) {
		// return false;
		// }
		// }
		// }
		// }
		// return true;
		// }
		//
		// private static boolean checkColumn(int[][] vals, int row, int col,
		// int number) {
		// for (int i = 0; i < boardSize; i++) {
		// if (i !=row) {
		// if (number == vals[i][col]){
		// return false;
		// }
		// }
		// }
		// return true;
		// }
		//
		// private static boolean checkRow(int[][] vals, int row, int col, int
		// number) {
		// for (int i = 0; i < boardSize; i++) {
		// if (i !=col) {
		// if (number == vals[row][i]){
		// return false;
		// }
		// }
		// }
		// return true;
		// }
		//
	}
}
