package utils;

import java.awt.Point;
import java.util.ArrayList;
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
		
		if (checkSize(intervals, 2, 2, 2)) {
			
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
}
