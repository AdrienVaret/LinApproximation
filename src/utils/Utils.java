package utils;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import graphs.Node;
import graphs.UndirGraph;
import solver.Approximation;
import solver.BenzenoidsSolver;

public class Utils {

	// DEBUG
	public static int[] t = new int[54];

	public static int getHexagonId(int x, int y) {
		return x + y * BenzenoidsSolver.taille;
	}

	public static int getEdgeId(int x, int y, int edgeBoard) {

		if (edgeBoard == BenzenoidsSolver.HIGH_RIGHT && y > 0)
			return (x + (y - 1) * BenzenoidsSolver.taille) * 6 + 3;

		else if (edgeBoard == BenzenoidsSolver.LEFT && x > 0)
			return (x - 1 + y * BenzenoidsSolver.taille) * 6 + 1;

		else if (edgeBoard == BenzenoidsSolver.HIGHT_LEFT && x > 0 && y > 0)
			return (x - 1 + (y - 1) * BenzenoidsSolver.taille) * 6 + 2;

		else
			return (x + y * BenzenoidsSolver.taille) * 6 + edgeBoard;
	}

	public static Point getHexagonCoordinates(int index) {

		int y = (int) Math.floor(index / BenzenoidsSolver.taille);
		int x = index - (BenzenoidsSolver.taille * y);

		return new Point(x, y);
	}

	public static double angle(Vector2D u, Vector2D v) {
		double angle = Vector2D.angle(u, v);

		if (!(u.getX() * v.getY() - u.getY() * v.getX() < 0))
			angle = -angle;

		return angle;
	}

	public static double orientedAngle(Node u, Node v, Node w) {

		Vector2D v1 = new Vector2D(v.getX() - u.getX(), v.getY() - u.getY());
		Vector2D v2 = new Vector2D(w.getX() - v.getX(), w.getY() - v.getY());

		return Math.atan2(v1.getX(), v1.getY()) - Math.atan2(v2.getX(), v2.getY());
	}

	public static ArrayList<Integer> computeDomainX1(Cycle cycle) {
		ArrayList<Integer> domain = new ArrayList<Integer>();
		for (int i = 0; i < cycle.getNodes().length; i++) {
			if (cycle.getNode(i) != -1) {
				domain.add(i);
			}
		}
		return domain;
	}

	public static ArrayList<Integer> computeDomainX2(Cycle cycle, int x1) {
		ArrayList<Integer> domain = new ArrayList<Integer>();

		int v = cycle.getNode(x1);

		while (true) {
			domain.add(v);
			v = cycle.getNode(v);
			if (v == x1)
				break;
		}

		return domain;
	}

	public static ArrayList<Integer> computeDomainX3(Cycle cycle, int x1, int x2) {
		ArrayList<Integer> domain = new ArrayList<Integer>();

		int v = x2;
		while (true) {
			domain.add(v);
			v = cycle.getNode(v);
			if (v == x1)
				break;
		}

		return domain;
	}

	public static ArrayList<Integer> computeDomainX4(Cycle cycle, int x1, int x2, int x3) {
		ArrayList<Integer> domain = new ArrayList<Integer>();

		int v = cycle.getNode(x3);

		while (true) {

			if (v == x1)
				break;

			if (v != cycle.getNode(x3))
				domain.add(v);

			v = cycle.getNode(v);

		}

		return domain;
	}

	public static void initSubstractTable() {
		Approximation.circuitsToSubstract = new int[49][2];

		Approximation.circuitsToSubstract[12][0] = 3;
		Approximation.circuitsToSubstract[12][1] = 13;

		Approximation.circuitsToSubstract[16][0] = 0;
		Approximation.circuitsToSubstract[16][1] = 4;

		Approximation.circuitsToSubstract[17][0] = 0;
		Approximation.circuitsToSubstract[17][1] = 4;

		Approximation.circuitsToSubstract[18][0] = 0;
		Approximation.circuitsToSubstract[18][1] = 4;

		Approximation.circuitsToSubstract[19][0] = 0;
		Approximation.circuitsToSubstract[19][1] = 1;

		Approximation.circuitsToSubstract[20][0] = 0;
		Approximation.circuitsToSubstract[20][1] = 1;

		Approximation.circuitsToSubstract[21][0] = 0;
		Approximation.circuitsToSubstract[21][1] = 1;

		Approximation.circuitsToSubstract[22][0] = 0;
		Approximation.circuitsToSubstract[22][1] = 1;

		Approximation.circuitsToSubstract[23][0] = 0;
		Approximation.circuitsToSubstract[23][1] = 1;

		Approximation.circuitsToSubstract[24][0] = 0;
		Approximation.circuitsToSubstract[24][1] = 1;

		Approximation.circuitsToSubstract[25][0] = 0;
		Approximation.circuitsToSubstract[25][1] = 2;

		Approximation.circuitsToSubstract[26][0] = 0;
		Approximation.circuitsToSubstract[26][1] = 2;

		Approximation.circuitsToSubstract[27][0] = 0;
		Approximation.circuitsToSubstract[27][1] = 2;

		Approximation.circuitsToSubstract[28][0] = 0;
		Approximation.circuitsToSubstract[28][1] = 1;

		Approximation.circuitsToSubstract[29][0] = 0;
		Approximation.circuitsToSubstract[29][1] = 1;

		Approximation.circuitsToSubstract[30][0] = 0;
		Approximation.circuitsToSubstract[30][1] = 1;

		Approximation.circuitsToSubstract[31][0] = 0;
		Approximation.circuitsToSubstract[31][1] = 1;

		Approximation.circuitsToSubstract[32][0] = 0;
		Approximation.circuitsToSubstract[32][1] = 1;

		Approximation.circuitsToSubstract[33][0] = 0;
		Approximation.circuitsToSubstract[33][1] = 1;

		Approximation.circuitsToSubstract[34][0] = 0;
		Approximation.circuitsToSubstract[34][1] = 1;

		Approximation.circuitsToSubstract[35][0] = 0;
		Approximation.circuitsToSubstract[35][1] = 1;

		Approximation.circuitsToSubstract[36][0] = 0;
		Approximation.circuitsToSubstract[36][1] = 1;

		Approximation.circuitsToSubstract[37][0] = 0;
		Approximation.circuitsToSubstract[37][1] = 1;

		Approximation.circuitsToSubstract[38][0] = 0;
		Approximation.circuitsToSubstract[38][1] = 1;

		Approximation.circuitsToSubstract[39][0] = 0;
		Approximation.circuitsToSubstract[39][1] = 1;

		Approximation.circuitsToSubstract[40][0] = 0;
		Approximation.circuitsToSubstract[40][1] = 1;

		Approximation.circuitsToSubstract[41][0] = 0;
		Approximation.circuitsToSubstract[41][1] = 1;

		Approximation.circuitsToSubstract[42][0] = 0;
		Approximation.circuitsToSubstract[42][1] = 1;

		Approximation.circuitsToSubstract[43][0] = 0;
		Approximation.circuitsToSubstract[43][1] = 1;

		Approximation.circuitsToSubstract[44][0] = 0;
		Approximation.circuitsToSubstract[44][1] = 1;

		Approximation.circuitsToSubstract[45][0] = 0;
		Approximation.circuitsToSubstract[45][1] = 1;

		Approximation.circuitsToSubstract[46][0] = 0;
		Approximation.circuitsToSubstract[46][1] = 1;

		Approximation.circuitsToSubstract[47][0] = 0;
		Approximation.circuitsToSubstract[47][1] = 1;

		Approximation.circuitsToSubstract[48][0] = 0;
		Approximation.circuitsToSubstract[48][1] = 1;

	}

	public static void initTab() {
		t[0] = 0;
		t[1] = 1;
		t[2] = 5;
		t[3] = 2;
		t[4] = 6;
		t[5] = 3;
		t[6] = 9;
		t[7] = 4;
		t[8] = 32;
		t[9] = 35;
		t[10] = 7;
		t[11] = 8;
		t[12] = 12;
		t[13] = 15;
		t[14] = 38;
		t[15] = 13;
		t[16] = 14;
		t[17] = 21;
		t[18] = 44;
		t[19] = 50;
		t[20] = 33;
		t[21] = 39;
		t[22] = 34;
		t[23] = 62;
		t[24] = 65;
		t[25] = 45;
		t[26] = 68;
		t[27] = 51;
		t[28] = 74;
		t[29] = 57;
		t[30] = 80;
		t[31] = 86;
		t[32] = 63;
		t[33] = 69;
		t[34] = 64;
		t[35] = 92;
		t[36] = 75;
		t[37] = 98;
		t[38] = 81;
		t[39] = 104;
		t[40] = 87;
		t[41] = 110;
		t[42] = 116;
		t[43] = 99;
		t[44] = 105;
		t[45] = 128;
		t[46] = 111;
		t[47] = 134;
		t[48] = 117;
		t[49] = 140;
		t[50] = 146;
		t[51] = 135;
		t[52] = 141;
		t[53] = 147;
	}

	private static void getCofactor(int mat[][], int temp[][], int p, int q, int n) {
		int i = 0, j = 0;

		// Looping for each element of
		// the matrix
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {

				// Copying into temporary matrix
				// only those element which are
				// not in given row and column
				if (row != p && col != q) {
					temp[i][j++] = mat[row][col];

					// Row is filled, so increase
					// row index and reset col
					// index
					if (j == n - 1) {
						j = 0;
						i++;
					}
				}
			}
		}
	}

	/*
	 * Recursive function for finding determinant of matrix. n is current dimension
	 * of mat[][].
	 */
	public static int computeMatrixDeterminant(int mat[][], int n) {
		int D = 0; // Initialize result

		// Base case : if matrix contains single
		// element
		if (n == 1)
			return mat[0][0];

		// To store cofactors
		int temp[][] = new int[n][n];

		// To store sign multiplier
		int sign = 1;

		// Iterate for each element of first row
		for (int f = 0; f < n; f++) {
			// Getting Cofactor of mat[0][f]
			getCofactor(mat, temp, 0, f, n);
			D += sign * mat[0][f] * computeMatrixDeterminant(temp, n - 1);

			// terms are to be added with
			// alternate sign
			sign = -sign;
		}

		return D;
	}

	public static boolean checkSize(ArrayList<Interval> intervals, int s1) {
		if (intervals.size() == 1) {
			if (intervals.get(0).size() == s1)
				return true;
		}
		return false;
	}
	
	public static boolean checkSize(ArrayList<Interval> intervals, int s1, int s2) {
		if (intervals.size() == 2) {
			if (intervals.get(0).size() == s1 && intervals.get(1).size() == s2)
				return true;
		}
		return false;
	}

	public static boolean checkSize(ArrayList<Interval> intervals, int s1, int s2, int s3) {
		if (intervals.size() == 3) {
			if (intervals.get(0).size() == s1 && intervals.get(1).size() == s2 && intervals.get(2).size() == s3)
				return true;
		}
		return false;
	}

	public static boolean checkSize(ArrayList<Interval> intervals, int s1, int s2, int s3, int s4) {
		if (intervals.size() == 4) {
			if (intervals.get(0).size() == s1 && intervals.get(1).size() == s2 && intervals.get(2).size() == s3
					&& intervals.get(3).size() == s4)
				return true;
		}
		return false;
	}

	public static boolean checkSize(ArrayList<Interval> intervals, int s1, int s2, int s3, int s4, int s5) {
		if (intervals.size() == 5) {
			if (intervals.get(0).size() == s1 && intervals.get(1).size() == s2 && intervals.get(2).size() == s3
					&& intervals.get(3).size() == s4 && intervals.get(4).size() == s5)
				return true;
		}
		return false;
	}
	
	public static int identifyCircuitV2(UndirGraph molecule, ArrayList<Integer> cycle, ArrayList<Interval> intervals) {
		
		Interval i0 = null;
		Interval i1 = null;
		Interval i2 = null;
		Interval i3 = null;
		Interval i4 = null;

		for (int i = 0; i < intervals.size(); i++) {
			if (i == 0)
				i0 = intervals.get(i);
			if (i == 1)
				i1 = intervals.get(i);
			if (i == 2)
				i2 = intervals.get(i);
			if (i == 3)
				i3 = intervals.get(i);
			if (i == 4)
				i4 = intervals.get(i);
		}
		
		/*
		 * 0
		 */
		
		if (checkSize(intervals, 2))
			return 0;
		
		/* 
		 * 1
		 */
		
		if (checkSize(intervals, 4))
			return 1;
		
		if (checkSize(intervals, 2, 2)) {
			
			if (i0.x1() == i1.x1() - 1)
				return 2;
			
			if (i0.x2() == i1.x2() + 1)
				return 3;
		}
		
		
		/*
		 * 2
		 */
		
		if (checkSize(intervals, 6))
			return 4;
		
		if (checkSize(intervals, 2, 2, 2)) {
			
			if (i0.x1() == i1.x1() + 1 && i1.x1() == i2.x1() + 1) 
				return 5;
			
			if (i0.x2() == i1.x2() - 1 && i1.x2() == i2.x2() - 1)
				return 6;
		}
		
		/*
		 * 3
		 */
		
		if (checkSize(intervals, 4, 2)) {
			
			if (i0.x2() == i1.x2() - 1)
				return 7;
			
			if (i0.x1() == i1.x1() + 1)
				return 8;
		}
		
		if (checkSize(intervals, 2, 4)) {
			
			if (i0.x2() == i1.x2() + 1)
				return 9;
			
			if (i0.x1() == i1.x1() - 1)
				return 10;
		}
		
		if (checkSize(intervals, 2, 2, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 11;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1)
				return 12;
		}
		
		/*
		 * 4
		 */
		
		if (checkSize(intervals, 4, 4)) {
			
			if (i0.x1() == i1.x1() + 1)
				return 13;
			
			if (i0.x2() == i1.x2() -1)
				return 14;
		}
		
		if (checkSize(intervals, 2, 4, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() -1)
				return 15;
		}
		
		/*
		 * 5
		 */
		
		if (checkSize(intervals, 8))
			return 16;
		
		if (checkSize(intervals, 2, 2, 2, 2)) {
			
			if (i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1 && i2.x1() == i3.x1() - 1)
				return 17;
			
			if (i0.x2() == i1.x2() + 1 && i1.x2() == i2.x2() + 1 && i2.x2() == i3.x2() + 1)
				return 18;
		}
		
		/*
		 * 6
		 */
		
		if (checkSize(intervals, 2, 6)) {
			
			if (i0.x2() == i1.x2() + 1)
				return 19;
			
			if (i0.x1() == i1.x1() - 1)
				return 20;
			
		}
		
		if (checkSize(intervals, 6, 2)) {
		
			if (i0.x2() == i1.x2() - 1)
				return 21;
			
			if (i0.x1() == i1.x1() + 1)
				return 22;
		}
		
		if (checkSize(intervals, 2, 2, 2, 2)) {
			
			if (i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1 && i1.x1() == i3.x1())
				return 23;
			
			if (i0.x2() == i1.x2() + 1 && i1.x2() == i2.x2() + 1 && i1.x2() == i3.x2())
				return 24;
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1 && i3.x1() == i2.x1() - 1)
				return 25;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() - 1 && i3.x2() == i2.x2() + 1)
				return 26;
		}
		
		if (checkSize(intervals, 4, 2, 2)) {
			
			if (i0.x2() == i1.x2() - 1 && i1.x2() == i2.x2() - 1)
				return 27;
			
			if (i0.x1() == i1.x1() + 1 && i1.x1() == i2.x1() + 1)
				return 28;
		}
		
		if (checkSize(intervals, 2, 2, 4)) {
			
			if (i0.x2() == i1.x2() + 1 && i1.x2() == i2.x2() + 1)
				return 29;
			
			if (i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1)
				return 30;
			
		}
		
		/*
		 * 7
		 */
		
		if (checkSize(intervals, 2, 4, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1)
				return 31;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() - 1)
				return 32;
		}
		
		/*
		 * 8
		 */
		
		if (checkSize(intervals, 4, 4, 2)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() - 1)
				return 33;
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1)
				return 34;
		}
		
		if (checkSize(intervals, 2, 4, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1)
				return 35;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() - 1)
				return 36;
		}
		
		if (checkSize(intervals, 2, 6, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 37;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1)
				return 38;
		}
		
		/*
		 * 9
		 */
		
		if (checkSize(intervals, 4, 6)) {
			
			if (i0.x2() == i1.x2() + 1)
				return 39;
			
			if (i0.x1() == i1.x1() - 1)
				return 40;
		}
		
		if (checkSize(intervals, 6, 4)) {
			
			if (i0.x2() == i1.x2() - 1)
				return 41;
			
			if (i0.x1() == i1.x1() + 1)
				return 42;
		}
		
		if (checkSize(intervals, 2, 2, 4, 2)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1)
				return 43;
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1)
				return 44;
		}
		
		if (checkSize(intervals, 2, 4, 2, 2)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() + 1)
				return 45;
				
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() - 1)
				return 46;
		}
		
		if (checkSize(intervals, 2, 4, 4)) {
			
			if (i0.x2() == i1.x2() + 1 && i1.x2() == i2.x2() + 1)
				return 47;
			
			if (i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1)
				return 48;
		}
		
		if (checkSize(intervals, 4, 4, 2)) {
			
			if (i0.x1() == i1.x1() + 1 && i1.x1() == i2.x1() + 1)
				return 49;
			
			if (i0.x2() == i1.x2() - 1 && i1.x2() == i2.x2() - 1)
				return 50;
		}
		
		/*
		 * 10
		 */
		
		if (checkSize(intervals, 4, 4, 4)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() - 1)
				return 51;
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1)
				return 52;
		}
		
		if (checkSize(intervals, 2, 6, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 53;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1)
				return 54;
		}
		
		if (checkSize(intervals, 4, 6, 2)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1)
				return 55;
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 56;
		}
		
		/*
		 * 12
		 */
		
		if (checkSize(intervals, 4, 6, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 57;
		}
		
		/*
		 * 15
		 */
		
		if (checkSize(intervals, 6, 6)) {
			
			if (i0.x1() == i1.x1() + 1)
				return 58;
			
			if (i0.x2() == i1.x2() - 1)
				return 59;
		}
		
		if (checkSize(intervals, 4, 4, 4)) {
			
			if (i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1)
				return 60;
			
			if (i0.x2() == i1.x2() + 1 && i1.x2() == i2.x2() + 1)
				return 61;
		}
		
		if (checkSize(intervals, 2, 4, 4, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x2() == i3.x2() && i1.x1() == i0.x1() - 1)
				return 62;
			
			if (i0.x2() == i2.x2() && i1.x1() == i3.x1() && i1.x2() == i0.x2() + 1)
				return 63;
		}
		
		/*
		 * Redundant circuits
		 */
		
		/*
		 * 16
		 */
		
		if (checkSize(intervals, 4, 8, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 64;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1)
				return 65;
		}
		
		/*
		 * 17
		 */
		
		if (checkSize(intervals, 2, 4, 6, 4)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1)
				return 66;
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1)
				return 67;
		}
		
		/*
		 * 18
		 */
		
		if (checkSize(intervals, 4, 6, 4, 2)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i0.x2() == i1.x2() - 1)
				return 68;
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i0.x1() == i1.x1() + 1)
				return 69;
		}
		
		/*
		 * 19
		 */
		
		if (checkSize(intervals, 4, 6, 6, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x2() == i1.x2())
				return 70;
			
			if (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x1() == i1.x1())
				return 71;
		}
		
		/*
		 * 20
		 */
		
		if (checkSize(intervals, 4, 6, 4, 4)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i2.x2() == i1.x2() - 1)
				return 72;
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i2.x1() == i1.x1() + 1)
				return 73;
		}
		
		/*
		 * 21
		 */
		
		if (checkSize(intervals, 4, 8, 6)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 74;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1)
				return 75;
		}
		
		/*
		 * 22
		 */
		
		if (checkSize(intervals, 6, 8, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 76;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1)
				return 77;
		}
		
		/*
		 * 23
		 */
		
		if (checkSize(intervals, 2, 6, 6, 4)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1 && i3.x2() == i2.x2() - 1)
				return 78;
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1 && i3.x1() == i2.x1() + 1)
				return 79;
		}
		
		/*
		 * 24
		 */
		
		if (checkSize(intervals, 4, 4, 6, 4)) {
			
			if(i0.x2() == i2.x2() && i1.x2() == i3.x2() && i3.x2() == i2.x2() - 1)
				return 80;
			
			if(i0.x1() == i2.x1() && i1.x1() == i3.x1() && i3.x1() == i2.x1() + 1)
				return 81;
			
		}
		
		/*
		 * 25
		 */
		
		if (checkSize(intervals, 4, 10, 4)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 3)
				return 82;
		}
		
		/*
		 * 26, 27
		 */
		
		if (checkSize(intervals, 2, 4, 6, 4, 2)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i3.x2() == i2.x2() - 1 && i4.x1() == i2.x1())
				return 83;
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i3.x1() == i2.x1() + 1 && i4.x2() == i2.x2())
				return 84;
		}
		
		
		/*
		 * 28
		 */
		
		if (checkSize(intervals, 4, 6, 4, 2, 2) && Approximation.intervalsOnSameLine(i3, i4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i2.x1() == i1.x1() + 1 && i4.x2() == i1.x2())
				return 85;
		}
		
		/*
		 * 29
		 */
		
		if (checkSize(intervals, 2, 2, 4, 6, 4) && Approximation.intervalsOnSameLine(i0, i1)) {
			
			if (i0.x1() == i3.x1() && i2.x1() == i4.x1() && i2.x1() == i0.x1() + 1 && i1.x2() == i3.x2())
				return 86;
		}
		
		/*
		 * 49
		 */
		
		if (checkSize(intervals, 2, 4, 8, 4)) {
			
			if (i1.x2() == i3.x2() && i2.x2() == i1.x2() + 3 && i0.x2() == i1.x2() + 1)
				return 87;
			
			if (i1.x1() == i3.x1() && i2.x1() == i1.x1() - 3 && i0.x1() == i1.x1() - 1)
				return 88;
			
		}
		
		/*
		 * 50
		 */
		
		if (checkSize(intervals, 4, 8, 4, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1 && i3.x1() == i2.x1() + 3)
				return 89;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1 && i3.x2() == i2.x2() - 3)
				return 90;
		}
		
		/*
		 * 30
		 */
		
		if (checkSize(intervals, 4, 10, 6)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i2.x1() - 3)
				return 91;
			
			if (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 3)
				return 92;
		}
		
		/*
		 * 31
		 */
		
		if (checkSize(intervals, 6, 10, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i2.x1() - 3)
				return 93;
			
			if (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 3)
				return 94;
		}
		
		/*
		 * 32
		 */
		
		if (checkSize(intervals, 2, 4, 6, 4, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1 && i4.x1() == i3.x1() + 1)
				return 95;
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1 && i4.x2() == i3.x2() - 1)
				return 96;
		}
		
		/*
		 * 33
		 */
		
		if (checkSize(intervals, 2, 4, 6, 6, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1 && i4.x2() == i2.x2())
				return 97;
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1 && i4.x1() == i2.x1())
				return 98;
		}
		
		/*
		 * 34
		 */
		
		if (checkSize(intervals, 2, 6, 6, 4, 2)) {
			
			if (i1.x1() == i3.x1() && i2.x1() == i4.x1() && i1.x1() == i2.x1() + 1 && i0.x1() == i1.x1() + 3)
				return 99;
			
			if (i1.x2() == i3.x2() && i2.x2() == i4.x2() && i1.x2() == i2.x2() - 1 && i0.x2() == i1.x2() - 3)
				return 100;
		}
		
		/*
		 * 35
		 */
		
		if (checkSize(intervals, 4, 4, 6, 4, 2)) {
			
			if(i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i2.x2() - 1 && i4.x1() == i2.x1())
				return 101;
			
			if(i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i2.x1() + 1 && i4.x2() == i2.x2())
				return 102;
		}
		
		/*
		 * 36
		 */
		
		if (checkSize(intervals, 2, 2, 6, 6, 4) && Approximation.intervalsOnSameLine(i0, i1)) {
			
			if (i0.x1() == i3.x1() && i2.x1() == i0.x1() - 1 && i4.x1() == i3.x1() + 1 && i1.x2() == i3.x2())
				return 103;
			
			if (i0.x1() == i3.x1() && i2.x1() == i4.x1() && i2.x1() == i0.x1() + 1 && i1.x2() == i3.x2())
				return 104;
		}
		
		/*
		 * 37
		 */
		
		if (checkSize(intervals, 4, 6, 6, 2, 2) && Approximation.intervalsOnSameLine(i3, i4)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i4.x2() && i2.x2() == i4.x2() - 1 && i3.x1() == i1.x1())
				return 105;
			 
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i0.x1() == i1.x1() + 1 && i4.x2() == i1.x2())
				return 106;
		}
		
		/*
		 * 38
		 */
		
		if (checkSize(intervals, 2, 4, 8, 6)) {
			
			if (i1.x2() == i3.x2() && i2.x2() == i1.x2() + 1 && i0.x2() == i1.x2() - 3)
				return 107;
			
			if (i1.x1() == i3.x1() && i2.x1() == i1.x1() - 1 && i0.x1() == i1.x1() + 3)
				return 108;
		}
		
		/*
		 * 39
		 */
		
		if (checkSize(intervals, 6, 8, 4, 2)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 3)
				return 109;
			
			if (i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x1() == i2.x1() + 3)
				return 110;
		}
		
		/*
		 * 40
		 */
		
		if (checkSize(intervals, 4, 8, 4, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x1() == i2.x1() + 1)
				return 111;
			
			if (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 1)
				return 112;
		}
		
		/*
		 * 41
		 */
		
		if (checkSize(intervals, 4, 4, 8, 4)) {
			
			if (i1.x1() == i3.x1() && i0.x1() == i1.x1() + 1 && i2.x1() == i3.x1() - 1)
				return 113;
			
			if (i1.x2() == i3.x2() && i0.x2() == i1.x2() - 1 && i2.x2() == i3.x2() + 1)
				return 114;
		}
		
		/*
		 * 42
		 */
		
		if (checkSize(intervals, 6, 10, 6)) {
			
			if (i0.x2() == i1.x2() - 1 && i2.x2() == i1.x2() - 3)
				return 115;
			
			if (i0.x1() == i1.x1() + 1 && i2.x1() == i1.x1() + 3)
				return 116;
		}
		
		/*
		 * 43
		 */
		
		if (checkSize(intervals, 4, 4, 6, 4, 4)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1 && i4.x2() == i3.x2() - 1)
				return 117;
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1 && i4.x1() == i3.x1() + 1)
				return 118;
		}
		
		/*
		 * 44
		 */
		
		if (checkSize(intervals, 2, 6, 6, 6, 2)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1 && i3.x2() == i2.x2() - 1 && i4.x2() == i3.x2() - 3)
				return 119;
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1 && i3.x1() == i2.x1() + 1 && i4.x1() == i3.x1() + 3)
				return 120;
		}
		
		/*
		 * 45
		 */
		
		if (checkSize(intervals, 2, 2, 8, 6, 4) && Approximation.intervalsOnSameLine(i0, i1)) {
			
			if (i0.x1() == i3.x1() && i2.x1() == i0.x1() - 1 && i4.x1() == i3.x1() + 1 && i1.x2() == i3.x2())
				return 121;
		}
		
		/*
		 * 46
		 */
		
		if (checkSize(intervals, 4, 6, 8, 2, 2) && Approximation.intervalsOnSameLine(i3, i4)) {
			
			if (i1.x1() == i3.x1() && i2.x1() == i3.x1() - 1 && i0.x1() == i1.x1() + 1 && i4.x2() == i1.x2())
				return 122;
		}
		
		/*
		 * 47
		 */
		
		if (checkSize(intervals, 6, 8, 4, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x1() == i2.x1() + 1)
				return 123;
			
			if (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 1)
				return 124;
		}
		
		/*
		 * 48
		 */
		
		if (checkSize(intervals, 4, 4, 8, 6)) {
			
			if (i0.x1() == i1.x1() + 1 && i1.x1() == i3.x1() && i2.x1() == i1.x1() - 1)
				return 125;
			
			if (i0.x2() == i1.x2() - 1 && i1.x2() == i3.x2() && i2.x2() == i1.x2() + 1)
				return 126;
		}
		
		/*
		 * 49
		 */
		
		if (checkSize(intervals, 4, 10, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 127;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1)
				return 128;
		}
		
		/*
		 * 50
		 */
		
		if (checkSize(intervals, 2, 2, 4, 6, 4)) {
			
			if (i2.x1() == i4.x1() && i3.x1() == i2.x1() - 1 && i1.x2() == i3.x2() && i0.x2() == i1.x2() + 1)
				return 129;
			
			if (i2.x1() == i4.x1() && i3.x1() == i2.x1() - 1 && i1.x1() == i3.x1() && i0.x1() == i1.x1() - 1)
				return 130;
		}
		
		/*
		 * 51
		 */
		
		if (checkSize(intervals, 4, 6, 4, 2, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1 && i1.x1() == i3.x1() && i4.x1() == i3.x1() - 1)
				return 131;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1 && i1.x2() == i3.x2() && i4.x2() == i3.x2() + 1)
				return 132;
		}
		
		/*
		 * Default
		 */
		
		return -1;
	}
	
	public static int identifyCircuit(UndirGraph molecule, ArrayList<Integer> cycle, ArrayList<Interval> intervals) {

		Interval i0 = null;
		Interval i1 = null;
		Interval i2 = null;
		Interval i3 = null;
		Interval i4 = null;

		for (int i = 0; i < intervals.size(); i++) {
			if (i == 0)
				i0 = intervals.get(i);
			if (i == 1)
				i1 = intervals.get(i);
			if (i == 2)
				i2 = intervals.get(i);
			if (i == 3)
				i3 = intervals.get(i);
			if (i == 4)
				i4 = intervals.get(i);
		}

		/*
		 * I
		 */

		if (checkSize(intervals, 4, 6, 4)) {

			if (i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1)
				return 12;
		}

		/*
		 * II
		 */

		if (checkSize(intervals, 4, 8, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1) || 
				(i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1))
				return 16;
		}

		if (checkSize(intervals, 2, 4, 6, 4)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1) || 
				(i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1))
				return 17;
		}

		if (checkSize(intervals, 4, 6, 4, 2)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i0.x2() == i1.x2() - 1)
			 || (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i0.x1() == i1.x1() + 1))
				return 18;
		}

		if (checkSize(intervals, 4, 6, 6, 2)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x2() == i1.x2())
			|| (i0.x2() == i2.x2() && i1.x1() == i3.x1() && i2.x1() == i1.x1() - 1))
				return 19;
		}

		if (checkSize(intervals, 4, 6, 4, 4)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i2.x2() == i1.x2() - 1)
			 || (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i2.x1() == i1.x1() + 1))
				return 20;
		}

		if (checkSize(intervals, 2, 8, 6)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1) || 
				(i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1))
				return 21;
		}

		if (checkSize(intervals, 6, 8, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1) || (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1))
				return 22;
		}

		if (checkSize(intervals, 2, 6, 6, 4)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1 && i3.x2() == i2.x2() - 1)
			 || (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1 && i3.x1() == i2.x1() + 1))
				return 23;
		}

		if (checkSize(intervals, 4, 4, 6, 2)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i3.x2() == i2.x2() - 1)
			  || (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i3.x1() == i2.x1() + 1))
				return 24;
		}

		/*
		 * V
		 */

		if (checkSize(intervals, 4, 10, 4)) {

			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 3)
				return 25;
		}

		if (checkSize(intervals, 2, 4, 6, 4, 2)) {

			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i3.x2() == i2.x2() - 1 && i4.x2() == i3.x2() - 3)
				return 26;

			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i3.x1() == i2.x1() + 1 && i4.x1() == i3.x1() + 3)
				return 27;
		}

		if (checkSize(intervals, 4, 6, 4, 2, 2) && Approximation.intervalsOnSameLine(i3, i4)) {

			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i2.x1() == i1.x1() + 1 && i4.x2() == i1.x2())
				return 28;
		}

		if (checkSize(intervals, 2, 2, 4, 6, 4) && Approximation.intervalsOnSameLine(i0, i1)) {

			if (i0.x1() == i3.x1() && i2.x1() == i4.x1() && i2.x1() == i0.x1() + 1 && i1.x2() == i3.x2())
				return 29;
		}

		/*
		 * VII
		 */

		if (checkSize(intervals, 4, 10, 6)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i2.x1() - 3) || 
				(i0.x2() == i2.x2() && i1.x2() == i2.x2() + 3))
				return 30;

		}

		if (checkSize(intervals, 6, 10, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i2.x1() - 3) || 
				(i0.x2() == i2.x2() && i1.x2() == i2.x2() + 3))
				return 31;
		}

		if (checkSize(intervals, 2, 4, 6, 4, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1 && i4.x1() == i3.x1() + 1)
			 || (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i2.x2() - 1 && i4.x2() == i3.x2() - 1))
				return 32;
		}

		if (checkSize(intervals, 2, 4, 6, 6, 2)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1 && i1.x1() == i3.x1() && i4.x2() == i2.x2())
			 || (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1 && i4.x1() == i2.x1()))
				return 33;
		}

		if (checkSize(intervals, 2, 6, 6, 4, 2)) {

			if ((i1.x1() == i3.x1() && i2.x1() == i4.x1() && i1.x1() == i2.x1() + 1 && i0.x1() == i1.x1() + 3)
			 || (i1.x2() == i3.x2() && i2.x2() == i4.x2() && i1.x2() == i2.x2() - 1 && i0.x2() == i1.x2() - 3))
				return 34;
		}

		if (checkSize(intervals, 4, 4, 6, 4, 2)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i2.x2() - 1 && i4.x1() == i2.x1())
			 || (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i2.x1() + 1 && i4.x2() == i2.x2()))
				return 35;
		}

		if (checkSize(intervals, 2, 2, 6, 6, 4) && Approximation.intervalsOnSameLine(i0, i1)) {

			if ((i0.x1() == i3.x1() && i2.x1() == i0.x1() - 1 && i4.x1() == i3.x1() + 1 && i1.x2() == i3.x2())
			 || (i0.x1() == i3.x1() && i2.x1() == i4.x1() && i2.x1() == i0.x1() + 1 && i1.x2() == i3.x2()))
				return 36;

		}

		if (checkSize(intervals, 4, 6, 6, 2, 2) && Approximation.intervalsOnSameLine(i3, i4)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i4.x2() && i2.x2() == i4.x2() - 1 && i3.x1() == i1.x1())
			 || (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i0.x1() == i1.x1() + 1 && i4.x2() == i1.x2()))
				return 37;
		}

		if (checkSize(intervals, 2, 4, 8, 6)) {
			if ((i1.x2() == i3.x2() && i2.x2() == i1.x2() + 1 && i0.x2() == i1.x2() - 3)
			 || (i1.x1() == i3.x1() && i2.x1() == i1.x1() - 1 && i0.x1() == i1.x1() + 3))
				return 38;
		}

		if (checkSize(intervals, 6, 8, 4, 2)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 3)
			 || (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1 && i3.x1() == i2.x1() + 3))
				return 39;
		}

		if (checkSize(intervals, 4, 8, 4, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x1() == i2.x1() + 1)
			 || (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 1 /* i2.x1() - 1 avant*/))
				return 40;
		}

		//ATTENTION: bizarre
		if (checkSize(intervals, 4, 4, 8, 4)) {

			if ((i1.x1() == i3.x1() && i0.x1() == i1.x1() + 1 && i2.x1() == i3.x1() - 1)
			 || (i1.x2() == i3.x2() && i0.x2() == i1.x2() - 1 && i3.x2() == i2.x2() - 1))
				return 41;
		}

		/*
		 * X
		 */

		if (checkSize(intervals, 6, 10, 6)) {

			if ((i0.x2() == i1.x2() - 1 && i2.x2() == i1.x2() - 3)
			 || (i0.x1() == i1.x1() + 1 && i2.x1() == i1.x1() + 3))
				return 42;
		}

		if (checkSize(intervals, 4, 4, 6, 4, 4)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1 && i4.x2() == i3.x2() - 1)
			||  (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1 && i4.x1() == i3.x1() + 1))
				return 43;
		}

		if (checkSize(intervals, 2, 6, 6, 6, 2)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1 && i3.x2() == i2.x2() - 1 && i4.x2() == i3.x2() - 3)
			 || (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1 && i3.x1() == i2.x1() + 1 && i4.x1() == i3.x1() + 3))
				return 44;
		}

		if (checkSize(intervals, 2, 2, 8, 6, 4) && Approximation.intervalsOnSameLine(i0, i1)) {

			if (i0.x1() == i3.x1() && i2.x1() == i0.x1() - 1 && i4.x1() == i3.x1() + 1 && i1.x2() == i3.x2())
				return 45;
		}

		if (checkSize(intervals, 4, 6, 8, 2, 2) && Approximation.intervalsOnSameLine(i3, i4)) {

			if (i1.x1() == i3.x1() && i2.x1() == i3.x1() - 1 && i0.x1() == i1.x1() + 1 && i4.x2() == i1.x2())
				return 46;
		}

		if (checkSize(intervals, 6, 8, 4, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x1() == i2.x1() + 1)
			 || (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 1))
				return 47;
		}

		if (checkSize(intervals, 4, 4, 8, 6)) {

			if ((i0.x1() == i1.x1() + 1 && i1.x1() == i3.x1() && i2.x1() == i1.x1() - 1)
					|| (i0.x2() == i1.x2() - 1 && i1.x2() == i3.x2() && i2.x2() == i1.x2() + 1))
				return 48;
		}

		if (checkSize(intervals, 2, 4, 8, 4)) {
			
			if ((i1.x2() == i3.x2() && i2.x2() == i1.x2() + 3 && i0.x2() == i1.x1() + 1) ||
				(i1.x1() == i3.x1() && i2.x1() == i1.x1() - 3 && i0.x1() == i1.x1() - 1))
				return 49;
		}
		
		
		if (checkSize(intervals, 4, 8, 4, 2)) {
			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1 && i3.x1() == i2.x1() + 3) || 
				(i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1 && i3.x2() == i2.x2() - 3))
				return 50;
		}
		
		
		return -1;
	}

	public static int identifyMinimalCycle(UndirGraph molecule, ArrayList<Integer> cycle,
			ArrayList<Interval> intervals) {
		
		Interval i0 = null;
		Interval i1 = null;
		Interval i2 = null;
		Interval i3 = null;

		for (int i = 0; i < intervals.size(); i++) {
			if (i == 0)
				i0 = intervals.get(i);
			if (i == 1)
				i1 = intervals.get(i);
			if (i == 2)
				i2 = intervals.get(i);
			if (i == 3)
				i3 = intervals.get(i);
		}

		/*
		 * 0
		 */

		if (intervals.size() == 1 && i0.size() == 2)
			return 0;

		/*
		 * 1
		 */

		if (intervals.size() == 1 && i0.size() == 4)
			return 1;

		if (checkSize(intervals, 2, 2)) {

			if (i0.x1() == i1.x1() - 1 || i0.x1() == i1.x1() + 1)
				return 1;
		}

		/*
		 * 2
		 */

		if (intervals.size() == 1 && i0.size() == 6)
			return 2;

		if (checkSize(intervals, 2, 2, 2)) {

			if ((i0.x1() == i1.x1() + 1 && i1.x1() == i2.x1() + 1)
					|| (i0.x2() == i1.x2() - 1 && i1.x2() == i2.x2() - 1))
				return 2;

		}

		/*
		 * 3
		 */

		if (checkSize(intervals, 4, 2)) {

			if (i0.x2() == i1.x2() - 1 || i0.x1() == i1.x1() + 1)
				return 3;
		}

		if (checkSize(intervals, 2, 4)) {

			if (i0.x2() == i1.x2() + 1 || i0.x1() == i1.x1() - 1)
				return 3;
		}

		if (checkSize(intervals, 2, 2, 2)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1) || (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1))
				return 3;

		}

		/*
		 * 4
		 */

		if (checkSize(intervals, 4, 4)) {

			if (i0.x1() == i1.x1() + 1 || i0.x2() == i1.x2() - 1)
				return 4;
		}

		if (checkSize(intervals, 2, 4, 2)) {

			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 4;
		}

		/*
		 * 5
		 */

		if (intervals.size() == 1 && i0.size() == 8)
			return 5;

		if (checkSize(intervals, 2, 2, 2, 2)) {

			if (i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1 && i2.x1() == i3.x1() - 1)
				return 5;

			if (i0.x2() == i1.x2() + 1 && i1.x2() == i2.x2() + 1 && i2.x2() == i3.x2() + 1)
				return 5;
		}

		/*
		 * 6
		 */

		if (checkSize(intervals, 2, 6)) {

			if (i0.x2() == i1.x2() + 1 || i0.x1() == i1.x1() - 1)
				return 6;
		}

		if (checkSize(intervals, 6, 2)) {

			if (i0.x2() == i1.x2() - 1 || i0.x1() == i1.x1() + 1)
				return 6;
		}

		if (checkSize(intervals, 4, 2, 2)) {

			if ((i0.x2() == i1.x2() - 1 && i1.x2() == i2.x2() - 1)
					|| (i0.x1() == i1.x1() + 1 && i1.x1() == i2.x1() + 1))
				return 6;
		}

		if (checkSize(intervals, 2, 2, 4)) {

			if ((i0.x2() == i1.x2() + 1 && i1.x2() == i2.x2() + 1)
					|| (i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1))
				return 6;
		}

		if (checkSize(intervals, 2, 2, 2, 2)) {

			if ((i1.x2() == i3.x2() && i2.x2() == i1.x2() + 1 && i0.x2() == i1.x2() - 1)
					|| (i1.x1() == i3.x1() && i2.x1() == i1.x1() - 1 && i0.x1() == i1.x1() + 1)
					|| (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 1)
					|| (i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x1() == i2.x1() + 1))
				return 6;

		}

		/*
		 * 7
		 */

		if (checkSize(intervals, 2, 4, 2)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1) || (i0.x2() == i2.x2() && i1.x2() == i0.x2() - 1))
				return 7;
		}

		/*
		 * 8
		 */

		if (checkSize(intervals, 4, 4, 2)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i2.x2() - 1) || (i0.x1() == i2.x1() && i1.x1() == i2.x1() + 1))
				return 8;
		}

		if (checkSize(intervals, 2, 4, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i2.x1() + 1) || (i0.x2() == i2.x2() && i1.x2() == i2.x2() - 1))
				return 8;
		}

		if (checkSize(intervals, 2, 6, 2)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1) || (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1))
				return 8;
		}

		/*
		 * 9
		 */

		if (checkSize(intervals, 4, 6)) {

			if (i0.x2() == i1.x2() + 1 || i0.x1() == i1.x1() - 1)
				return 9;
		}

		if (checkSize(intervals, 6, 4)) {

			if (i0.x2() == i1.x2() - 1 || i0.x1() == i1.x1() + 1)
				return 9;
		}

		if (checkSize(intervals, 2, 2, 4, 2)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1)
					|| (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1))
				return 9;
		}

		if (checkSize(intervals, 2, 4, 2, 2)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i2.x2() == i3.x2() - 1)
					|| (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i2.x1() == i3.x1() + 1))
				return 9;
		}

		if (checkSize(intervals, 2, 4, 4)) {

			if ((i0.x2() == i1.x2() + 1 && i1.x2() == i2.x2() + 1)
					|| (i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1))
				return 9;
		}

		if (checkSize(intervals, 4, 4, 2)) {
			if ((i0.x1() == i1.x1() + 1 && i1.x1() == i2.x1() + 1)
					|| (i0.x2() == i1.x2() - 1 && i1.x2() == i2.x2() - 1))
				return 9;
		}

		/*
		 * 10
		 */

		if (checkSize(intervals, 4, 4, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1) || (i0.x2() == i2.x2() && i1.x2() == i0.x2() - 1))
				return 10;
		}

		if (checkSize(intervals, 2, 6, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1) || (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1))
				return 10;

		}

		if (checkSize(intervals, 4, 6, 2)) {
			if ((i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1) || (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1))
				return 10;
		}

		/*
		 * 15
		 */

		if (checkSize(intervals, 6, 6)) {

			if (i0.x1() == i1.x1() - 1 || i0.x1() == i1.x1() + 1)
				return 15;
		}

		if (checkSize(intervals, 4, 4, 4)) {

			if ((i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1)
					|| (i0.x1() == i1.x1() + 1 && i1.x1() == i2.x1() + 1))
				return 15;
		}

		if (checkSize(intervals, 2, 4, 4, 2)) {
			if ((i1.x2() == i3.x2() && i0.x2() == i1.x2() - 1 && i2.x2() == i1.x2() + 1)
					|| (i1.x1() == i3.x1() && i0.x1() == i1.x1() + 1 && i2.x1() == i1.x1() - 1))
				return 15;
		}

		return -1;
	}

	public static int identifyCycle(UndirGraph molecule, ArrayList<Integer> cycle, ArrayList<Interval> intervals) {

		Interval i0 = null;
		Interval i1 = null;
		Interval i2 = null;
		Interval i3 = null;
		Interval i4 = null;

		for (int i = 0; i < intervals.size(); i++) {
			if (i == 0)
				i0 = intervals.get(i);
			if (i == 1)
				i1 = intervals.get(i);
			if (i == 2)
				i2 = intervals.get(i);
			if (i == 3)
				i3 = intervals.get(i);
			if (i == 4)
				i4 = intervals.get(i);
		}

		/**
		 * Minimal cycles
		 */

		/*
		 * 0
		 */

		if (intervals.size() == 1 && i0.size() == 2)
			return 0;

		/*
		 * 1
		 */

		if (intervals.size() == 1 && i0.size() == 4)
			return 1;

		if (checkSize(intervals, 2, 2)) {

			if (i0.x1() == i1.x1() - 1 || i0.x1() == i1.x1() + 1)
				return 1;
		}

		/*
		 * 2
		 */

		if (intervals.size() == 1 && i0.size() == 6)
			return 2;

		if (checkSize(intervals, 2, 2, 2)) {

			if ((i0.x1() == i1.x1() + 1 && i1.x1() == i2.x1() + 1)
					|| (i0.x2() == i1.x2() - 1 && i1.x2() == i2.x2() - 1))
				return 2;

		}

		/*
		 * 3
		 */

		if (checkSize(intervals, 4, 2)) {

			if (i0.x2() == i1.x2() - 1 || i0.x1() == i1.x1() + 1)
				return 3;
		}

		if (checkSize(intervals, 2, 4)) {

			if (i0.x2() == i1.x2() + 1 || i0.x1() == i1.x1() - 1)
				return 3;
		}

		if (checkSize(intervals, 2, 2, 2)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1) || (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1))
				return 3;

		}

		/*
		 * 4
		 */

		if (checkSize(intervals, 4, 4)) {

			if (i0.x1() == i1.x1() + 1 || i0.x2() == i1.x2() - 1)
				return 4;
		}

		if (checkSize(intervals, 2, 4, 2)) {

			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 4;
		}

		/*
		 * 5
		 */

		if (intervals.size() == 1 && i0.size() == 8)
			return 5;

		if (checkSize(intervals, 2, 2, 2, 2)) {

			if (i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1 && i2.x1() == i3.x1() - 1)
				return 5;

			if (i0.x2() == i1.x2() + 1 && i1.x2() == i2.x2() + 1 && i2.x2() == i3.x2() + 1)
				return 5;
		}

		/*
		 * 6
		 */

		if (checkSize(intervals, 2, 6)) {

			if (i0.x2() == i1.x2() + 1 || i0.x1() == i1.x1() - 1)
				return 6;
		}

		if (checkSize(intervals, 6, 2)) {

			if (i0.x2() == i1.x2() - 1 || i0.x1() == i1.x1() + 1)
				return 6;
		}

		if (checkSize(intervals, 4, 2, 2)) {

			if ((i0.x2() == i1.x2() - 1 && i1.x2() == i2.x2() - 1)
					|| (i0.x1() == i1.x1() + 1 && i1.x1() == i2.x1() + 1))
				return 6;
		}

		if (checkSize(intervals, 2, 2, 4)) {

			if ((i0.x2() == i1.x2() + 1 && i1.x2() == i2.x2() + 1)
					|| (i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1))
				return 6;
		}

		if (checkSize(intervals, 2, 2, 2, 2)) {

			if ((i1.x2() == i3.x2() && i2.x2() == i1.x2() + 1 && i0.x2() == i1.x2() - 1)
					|| (i1.x1() == i3.x1() && i2.x1() == i1.x1() - 1 && i0.x1() == i1.x1() + 1)
					|| (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 1)
					|| (i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x1() == i2.x1() + 1))
				return 6;

		}

		/*
		 * 7
		 */

		if (checkSize(intervals, 2, 4, 2)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1) || (i0.x2() == i2.x2() && i1.x2() == i0.x2() - 1))
				return 7;
		}

		/*
		 * 8
		 */

		if (checkSize(intervals, 4, 4, 2)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i2.x2() - 1) || (i0.x1() == i2.x1() && i1.x1() == i2.x1() + 1))
				return 8;
		}

		if (checkSize(intervals, 2, 4, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i2.x1() + 1) || (i0.x2() == i2.x2() && i1.x2() == i2.x2() - 1))
				return 8;
		}

		if (checkSize(intervals, 2, 6, 2)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1) || (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1))
				return 8;
		}

		/*
		 * 9
		 */

		if (checkSize(intervals, 4, 6)) {

			if (i0.x2() == i1.x2() + 1 || i0.x1() == i1.x1() - 1)
				return 9;
		}

		if (checkSize(intervals, 6, 4)) {

			if (i0.x2() == i1.x2() - 1 || i0.x1() == i1.x1() + 1)
				return 9;
		}

		if (checkSize(intervals, 2, 2, 4, 2)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1)
					|| (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1))
				return 9;
		}

		if (checkSize(intervals, 2, 4, 2, 2)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i2.x2() == i3.x2() - 1)
					|| (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i2.x1() == i3.x1() + 1))
				return 9;
		}

		if (checkSize(intervals, 2, 4, 4)) {

			if ((i0.x2() == i1.x2() + 1 && i1.x2() == i2.x2() + 1)
					|| (i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1))
				return 9;
		}

		if (checkSize(intervals, 4, 4, 2)) {
			if ((i0.x1() == i1.x1() + 1 && i1.x1() == i2.x1() + 1)
					|| (i0.x2() == i1.x2() - 1 && i1.x2() == i2.x2() - 1))
				return 9;
		}

		/*
		 * 10
		 */

		if (checkSize(intervals, 4, 4, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1) || (i0.x2() == i2.x2() && i1.x2() == i0.x2() - 1))
				return 10;
		}

		if (checkSize(intervals, 2, 6, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1) || (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1))
				return 10;

		}

		if (checkSize(intervals, 4, 6, 2)) {
			if ((i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1) || (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1))
				return 10;
		}

		/*
		 * 15
		 */

		if (checkSize(intervals, 6, 6)) {

			if (i0.x1() == i1.x1() - 1 || i0.x1() == i1.x1() + 1)
				return 15;
		}

		if (checkSize(intervals, 4, 4, 4)) {

			if ((i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1)
					|| (i0.x1() == i1.x1() + 1 && i1.x1() == i2.x1() + 1))
				return 15;
		}

		if (checkSize(intervals, 2, 4, 4, 2)) {
			if ((i1.x2() == i3.x2() && i0.x2() == i1.x2() - 1 && i2.x2() == i1.x2() + 1)
					|| (i1.x1() == i3.x1() && i0.x1() == i1.x1() + 1 && i2.x1() == i1.x1() - 1))
				return 15;
		}

		/**
		 * Redundant cycles
		 */

		/*
		 * I
		 */

		if (checkSize(intervals, 4, 6, 4)) {

			if (i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1)
				return 12;
		}

		/*
		 * II
		 */

		if (checkSize(intervals, 4, 8, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1) || (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1))
				return 16;
		}

		if (checkSize(intervals, 2, 4, 6, 4)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1)
					|| (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1))
				return 17;
		}

		if (checkSize(intervals, 4, 6, 4, 2)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i0.x2() == i1.x2() - 1)
					|| (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i0.x1() == i1.x1() + 1))
				return 18;
		}

		if (checkSize(intervals, 4, 6, 6, 2)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x2() == i1.x2())
					|| (i0.x2() == i2.x2() && i1.x1() == i3.x1() && i2.x1() == i1.x1() - 1))
				return 19;
		}

		if (checkSize(intervals, 4, 6, 4, 4)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i2.x2() == i1.x2() - 1)
					|| (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i2.x1() == i1.x1() + 1))
				return 20;
		}

		if (checkSize(intervals, 2, 8, 6)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1) || (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1))
				return 21;
		}

		if (checkSize(intervals, 6, 8, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1) || (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1))
				return 22;
		}

		if (checkSize(intervals, 2, 6, 6, 4)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1 && i3.x2() == i2.x2() - 1)
					|| (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1 && i3.x1() == i2.x1() + 1))
				return 23;
		}

		if (checkSize(intervals, 4, 4, 6, 2)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i3.x2() == i2.x2() - 1)
					|| (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i3.x1() == i2.x1() + 1))
				return 24;
		}

		/*
		 * V
		 */

		if (checkSize(intervals, 4, 10, 4)) {

			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 3)
				return 25;
		}

		if (checkSize(intervals, 2, 4, 6, 4, 2)) {

			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i3.x2() == i2.x2() - 1 && i4.x2() == i3.x2() - 3)
				return 26;

			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i3.x1() == i2.x1() + 1 && i4.x1() == i3.x1() + 3)
				return 27;
		}

		if (checkSize(intervals, 4, 6, 4, 2, 2) && Approximation.intervalsOnSameLine(i3, i4)) {

			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i2.x1() == i1.x1() + 1 && i4.x2() == i1.x2())
				return 28;
		}

		if (checkSize(intervals, 2, 2, 4, 6, 4) && Approximation.intervalsOnSameLine(i0, i1)) {

			if (i0.x1() == i3.x1() && i2.x1() == i4.x1() && i2.x1() == i0.x1() + 1 && i1.x2() == i3.x2())
				return 29;
		}

		/*
		 * VII
		 */

		if (checkSize(intervals, 4, 10, 6)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i2.x1() - 3) || (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 3))
				return 30;

		}

		if (checkSize(intervals, 6, 10, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i2.x1() - 3) || (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 3))
				return 31;
		}

		if (checkSize(intervals, 2, 4, 6, 4, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1 && i4.x1() == i3.x1() + 1)
					|| (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i2.x2() - 1 && i4.x2() == i3.x2() - 1))
				return 32;
		}

		if (checkSize(intervals, 2, 4, 6, 6, 2)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1 && i1.x1() == i3.x1() && i4.x2() == i2.x2())
					|| (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1 && i4.x1() == i2.x1()))
				return 33;
		}

		if (checkSize(intervals, 2, 6, 6, 4, 2)) {

			if ((i1.x1() == i3.x1() && i2.x1() == i4.x1() && i1.x1() == i2.x1() + 1 && i0.x1() == i1.x1() + 3)
					|| (i1.x2() == i3.x2() && i2.x2() == i4.x2() && i1.x2() == i2.x2() - 1 && i0.x2() == i1.x2() - 3))
				return 34;
		}

		if (checkSize(intervals, 4, 4, 6, 4, 2)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i2.x2() - 1 && i4.x1() == i2.x1())
					|| (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i2.x1() + 1 && i4.x2() == i2.x2()))
				return 35;
		}

		if (checkSize(intervals, 2, 2, 6, 6, 4) && Approximation.intervalsOnSameLine(i0, i1)) {

			if ((i0.x1() == i3.x1() && i2.x1() == i0.x1() - 1 && i4.x1() == i3.x1() + 1 && i1.x2() == i3.x2())
					|| (i0.x1() == i3.x1() && i2.x1() == i4.x1() && i2.x1() == i0.x1() + 1 && i1.x2() == i3.x2()))
				return 36;

		}

		if (checkSize(intervals, 4, 6, 6, 2, 2) && Approximation.intervalsOnSameLine(i3, i4)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i4.x2() && i2.x2() == i4.x2() - 1 && i3.x1() == i1.x1())
					|| (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i0.x1() == i1.x1() + 1 && i4.x2() == i1.x2()))
				return 37;
		}

		if (checkSize(intervals, 2, 4, 8, 6)) {
			if ((i1.x2() == i3.x2() && i2.x2() == i1.x2() + 1 && i0.x2() == i1.x2() - 3)
					|| (i1.x1() == i3.x1() && i2.x1() == i1.x1() - 1 && i0.x1() == i1.x1() + 3))
				return 38;
		}

		if (checkSize(intervals, 6, 8, 4, 2)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 3)
					|| (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1 && i3.x1() == i2.x1() + 3))
				return 39;
		}

		if (checkSize(intervals, 4, 8, 4, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x1() == i2.x1() + 1)
					|| (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x1() - 1))
				return 40;
		}

		if (checkSize(intervals, 4, 4, 8, 4)) {

			if ((i1.x1() == i3.x1() && i0.x1() == i1.x1() + 1 && i2.x1() == i3.x1() - 1)
					|| (i1.x2() == i3.x2() && i0.x2() == i1.x2() - 1 && i3.x2() == i2.x2() - 1))
				return 41;
		}

		/*
		 * X
		 */

		if (checkSize(intervals, 6, 10, 6)) {

			if ((i0.x2() == i1.x2() - 1 && i2.x2() == i1.x2() - 3)
					|| (i0.x1() == i1.x1() + 1 && i2.x1() == i1.x1() + 3))
				return 42;
		}

		if (checkSize(intervals, 4, 4, 6, 4, 4)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1 && i4.x2() == i3.x2() - 1)
					|| (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1 && i4.x1() == i3.x1() + 1))
				return 43;
		}

		if (checkSize(intervals, 2, 6, 6, 6, 2)) {

			if ((i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1 && i3.x2() == i2.x2() - 1 && i4.x2() == i3.x2() - 3)
					|| (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1 && i3.x1() == i2.x1() + 1
							&& i4.x1() == i3.x1() + 3))
				return 44;
		}

		if (checkSize(intervals, 2, 2, 8, 6, 4) && Approximation.intervalsOnSameLine(i0, i1)) {

			if (i0.x1() == i3.x1() && i2.x1() == i0.x1() - 1 && i4.x1() == i3.x1() + 1 && i1.x2() == i3.x2())
				return 45;
		}

		if (checkSize(intervals, 4, 6, 8, 2, 2) && Approximation.intervalsOnSameLine(i3, i4)) {

			if (i1.x1() == i3.x1() && i2.x1() == i3.x1() - 1 && i0.x1() == i1.x1() + 1 && i4.x2() == i1.x2())
				return 46;
		}

		if (checkSize(intervals, 6, 8, 4, 4)) {

			if ((i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x1() == i2.x1() + 1)
					|| (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 1))
				return 47;
		}

		if (checkSize(intervals, 4, 4, 8, 6)) {

			if ((i0.x1() == i1.x1() + 1 && i1.x1() == i3.x1() && i2.x1() == i1.x1() - 1)
					|| (i0.x2() == i1.x2() - 1 && i1.x2() == i3.x2() && i2.x2() == i1.x2() + 1))
				return 48;
		}

		return -1;
	}
	
	public static void readFileEnergy() throws IOException{
		
		String filename = "energy_circuits.txt";
		
		BufferedReader r = new BufferedReader(new FileReader(new File(filename)));
		BufferedWriter w = new BufferedWriter(new FileWriter(new File("matrix_init.txt")));
		String line;
		
		w.write("int [][][] energies = new int[127][11][4];" + "\n\n");
		
		int id = -1;
		
		while ((line = r.readLine()) != null) {
			String [] splittedLine = line.split(" ");
			
			if (line.equals("")) {
				System.out.println("\n");
				w.write("\n");
			}
			
			else if (splittedLine.length == 1) {
				System.out.println(line + " ");
				id = Integer.parseInt(line);
			}
			
			else {
				
				int hexagon = Integer.parseInt(splittedLine[0]);
				int [] R = new int [4];
				
				R[0] = Integer.parseInt(splittedLine[2]);
				R[1] = Integer.parseInt(splittedLine[3]);
				R[2] = Integer.parseInt(splittedLine[4]);
				R[3] = Integer.parseInt(splittedLine[5]);
				
				System.out.print("h_" + hexagon + " += ");
				
				for (int i = 0 ; i < R.length ; i++) {
					
					if (R[i] != 0) {
						
						if (R[i] < 0)
							System.out.print(R[i]);
						else
							System.out.print("+" + R[i]);
						
						System.out.print("R_" + (i+1) + " ");
						w.write("energies[" + id + "][" + hexagon + "][" + i + "] = " + R[i] + ";" + "\n");
					}
				}
				
				System.out.println("");	
			}
		}
		
		w.close();
		r.close();
	}
	
	public static int [][][] initEnergies() {
		
		int [][][] energies = new int[133][11][4];

		energies[0][0][0] = 2;

		energies[1][0][1] = 1;
		energies[1][1][1] = 1;

		energies[2][0][1] = 1;
		energies[2][1][1] = 1;

		energies[3][0][1] = 1;
		energies[3][1][1] = 1;

		energies[4][0][2] = 1;
		energies[4][2][2] = 1;

		energies[5][0][2] = 1;
		energies[5][2][2] = 1;

		energies[6][0][2] = 1;
		energies[6][2][2] = 1;

		energies[7][1][2] = 1;

		energies[8][0][2] = 1;

		energies[9][2][2] = 1;

		energies[10][1][2] = 1;

		energies[11][1][2] = 1;

		energies[12][1][2] = 1;

		energies[13][0][2] = 1;
		energies[13][3][2] = 1;

		energies[14][2][2] = 1;
		energies[14][1][2] = 1;

		energies[15][2][2] = 1;
		energies[15][1][2] = 1;

		energies[16][0][3] = 1;
		energies[16][3][3] = 1;

		energies[17][0][3] = 1;
		energies[17][3][3] = 1;

		energies[18][0][3] = 1;
		energies[18][3][3] = 1;

		energies[19][3][3] = 1;

		energies[20][1][3] = 1;

		energies[21][2][3] = 1;

		energies[22][0][3] = 1;

		energies[23][2][3] = 1;

		energies[24][2][3] = 1;

		energies[25][1][3] = 1;

		energies[26][1][3] = 1;

		energies[27][1][3] = 1;

		energies[28][0][3] = 1;

		energies[29][3][3] = 1;

		energies[30][2][3] = 1;

		energies[31][1][3] = 1;

		energies[32][2][3] = 1;

		energies[33][3][3] = 1;

		energies[34][2][3] = 1;

		energies[35][1][3] = 1;

		energies[36][2][3] = 1;

		energies[37][2][3] = 1;

		energies[38][2][3] = 1;

		energies[39][4][3] = 1;

		energies[40][2][3] = 1;

		energies[41][2][3] = 1;

		energies[42][0][3] = 1;

		energies[43][2][3] = 1;

		energies[44][3][3] = 1;

		energies[45][1][3] = 1;

		energies[46][2][3] = 1;

		energies[47][4][3] = 1;

		energies[48][3][3] = 1;

		energies[49][0][3] = 1;

		energies[50][1][3] = 1;

		energies[51][3][3] = 1;

		energies[52][2][3] = 1;

		energies[53][2][3] = 1;

		energies[54][2][3] = 1;

		energies[55][3][3] = 1;

		energies[56][3][3] = 1;

		energies[57][3][2] = -3;
		energies[57][3][3] = -13;//-13 avant, à voir

		energies[58][0][3] = 1;
		energies[58][5][3] = 1;

		energies[59][2][3] = 1;
		energies[59][3][3] = 1;

		energies[60][1][3] = 1;
		energies[60][4][3] = 1;

		energies[61][0][3] = 1;
		energies[61][5][3] = 1;

		energies[62][1][3] = 1;
		energies[62][4][3] = 1;

		energies[63][2][3] = 1;
		energies[63][3][3] = 1;

		// 64 - 69 : -4 avant
		
		energies[64][3][3] = -5;

		energies[65][4][3] = -5;

		energies[66][4][3] = -5;

		energies[67][4][3] = -5;

		energies[68][3][3] = -5;

		energies[69][3][3] = -5;

		energies[70][3][3] = -1;

		energies[71][3][3] = -1;

		energies[72][3][3] = -1;

		energies[73][3][3] = -1;

		energies[74][3][3] = -1;

		energies[75][4][3] = -1;

		energies[76][4][3] = -1;

		energies[77][5][3] = -1;

		energies[78][5][3] = -1;

		energies[79][5][3] = -1;

		energies[80][5][3] = -1;

		energies[81][5][3] = -1;

		energies[82][4][3] = -2;

		energies[83][4][3] = -2;

		energies[84][4][3] = -2;

		energies[85][3][3] = -1;

		energies[86][5][3] = -1;

		energies[87][4][3] = -1;

		energies[88][5][3] = -1;

		energies[89][3][3] = -1;

		energies[90][4][3] = -1;

		energies[91][4][3] = -1;

		energies[92][4][3] = -1;

		energies[93][5][3] = -1;

		energies[94][5][3] = -1;

		energies[95][4][3] = -1;

		energies[96][4][3] = -1;

		energies[97][4][3] = -1;

		energies[98][4][3] = -1;

		energies[99][5][3] = -1;

		energies[100][5][3] = -1;

		energies[101][5][3] = -1;

		energies[102][5][3] = -1;

		energies[103][6][3] = -1;

		energies[104][6][3] = -1;

		energies[105][3][3] = -1;

		energies[106][3][3] = -1;

		energies[107][5][3] = -1;

		energies[108][4][3] = -1;

		energies[109][5][3] = -1;

		energies[110][4][3] = -1;

		energies[111][3][3] = -1;

		energies[112][4][3] = -1;

		energies[113][5][3] = -1;

		energies[114][6][3] = -1;

		energies[115][5][3] = -1;

		energies[116][5][3] = -1;

		energies[117][5][3] = -1;

		energies[118][5][3] = -1;

		energies[119][5][3] = -1;

		energies[120][5][3] = -1;

		energies[121][7][3] = -1;

		energies[122][3][3] = -1;

		energies[123][4][3] = -1;

		energies[124][5][3] = -1;

		energies[125][5][3] = -1;

		energies[126][6][3] = -1;
		
		energies[127][3][3] = -1;
		
		energies[128][5][3] = -1;
		
		energies[129][5][3] = -1;
		
		energies[130][5][3] = -1;
		
		energies[131][3][3] = -1;
		
		energies[132][3][3] = -1;
		
		return energies;
	}
	
	public static String displayCycle(ArrayList<Integer> cycles) {
		
		ArrayList<Integer> l = new ArrayList<Integer>();
		
		for (Integer i : cycles)
			if (!l.contains(i))
				l.add(i);
		
		return l.toString();
	}
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader r = new BufferedReader(new FileReader(new File("logs.txt")));
		String line;
		
		ArrayList<String> lines = new ArrayList<String>();
		int somme = 0;
		
		while ((line = r.readLine()) != null) {
			String mult = line.split(Pattern.quote(" : "))[1];
			String [] splitMult = mult.split(Pattern.quote(" * "));
			int a = Integer.parseInt(splitMult[0]);
			int b = Integer.parseInt(splitMult[1]);
			
			somme += (a * b);
		}
		
		r.close();
		
		System.out.println(somme);
	}
}
