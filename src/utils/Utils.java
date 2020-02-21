package utils;

import java.awt.Point;
import java.util.ArrayList;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import graphs.Node;
import solver.Approximation;
import solver.BenzenoidsSolver;

public class Utils {

	//DEBUG
	public static int [] t = new int [54];
	
	public static int getHexagonId(int x, int y) {
		return x + y * BenzenoidsSolver.taille;
	}
	
	public static int getEdgeId(int x, int y, int edgeBoard) {
		
		if(edgeBoard == BenzenoidsSolver.HIGH_RIGHT && y > 0)
			return (x + (y - 1) * BenzenoidsSolver.taille) * 6 + 3;
		
		else if(edgeBoard == BenzenoidsSolver.LEFT && x > 0)
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
		
		if(!(u.getX() * v.getY() - u.getY() * v.getX() < 0))
		    angle = -angle;
		
		return angle;
	}
	
	public static double orientedAngle(Node u, Node v, Node w) {

		   Vector2D v1 = new Vector2D(v.getX() - u.getX(), v.getY() - u.getY());
		   Vector2D v2 = new Vector2D(w.getX() - v.getX(), w.getY() - v.getY());
		    
		   return Math.atan2(v1.getX(), v1.getY()) - Math.atan2(v2.getX(), v2.getY());
	}
	
	public static ArrayList<Integer> computeDomainX1(Cycle cycle){
		ArrayList<Integer> domain = new ArrayList<Integer>();
		for (int i = 0 ; i < cycle.getNodes().length ; i++) {
			if (cycle.getNode(i) != -1) {
				domain.add(i);
			}
		}
		return domain;
	}
	
	public static ArrayList<Integer> computeDomainX2(Cycle cycle, int x1){
		ArrayList<Integer> domain = new ArrayList<Integer>();
		
		int v = cycle.getNode(x1);
		
		while (true) {
			domain.add(v);
			v = cycle.getNode(v);
			if (v == x1) break;
		}
		
		return domain;
	}
	
	public static ArrayList<Integer> computeDomainX3(Cycle cycle, int x1, int x2){
		ArrayList<Integer> domain = new ArrayList<Integer>();
		
		int v = x2;
		while(true) {
			domain.add(v);
			v = cycle.getNode(v);
			if (v == x1) break;
		}
		
		return domain;
	}

	public static ArrayList<Integer> computeDomainX4(Cycle cycle, int x1, int x2, int x3){
		ArrayList<Integer> domain = new ArrayList<Integer>();
		
		int v = cycle.getNode(x3);
		
		while(true) {
			
			if (v == x1) break;
			
			if (v != cycle.getNode(x3))
				domain.add(v);

			v = cycle.getNode(v);

		}
		
		return domain;
	}
	
	public static void initSubstractTable(){
		Approximation.circuitsToSubstract = new int [62][2];
		
		Approximation.circuitsToSubstract[10][0] = 3;
		Approximation.circuitsToSubstract[10][1] = 13;

		Approximation.circuitsToSubstract[18][0] = 0;
		Approximation.circuitsToSubstract[18][1] = 3;

		Approximation.circuitsToSubstract[17][0] = 0;
		Approximation.circuitsToSubstract[17][1] = 3;

		Approximation.circuitsToSubstract[18][0] = 0;
		Approximation.circuitsToSubstract[18][1] = 3;

		Approximation.circuitsToSubstract[19][0] = 0;
		Approximation.circuitsToSubstract[19][1] = 3;

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
		Approximation.circuitsToSubstract[25][1] = 1;

		Approximation.circuitsToSubstract[26][0] = 0;
		Approximation.circuitsToSubstract[26][1] = 1;

		Approximation.circuitsToSubstract[27][0] = 0;
		Approximation.circuitsToSubstract[27][1] = 1;

		Approximation.circuitsToSubstract[28][0] = 0;
		Approximation.circuitsToSubstract[28][1] = 2;

		Approximation.circuitsToSubstract[29][0] = 0;
		Approximation.circuitsToSubstract[29][1] = 2;

		Approximation.circuitsToSubstract[30][0] = 0;
		Approximation.circuitsToSubstract[30][1] = 1;

		Approximation.circuitsToSubstract[31][0] = 0;
		Approximation.circuitsToSubstract[31][1] = 1;

		Approximation.circuitsToSubstract[32][0] = 0;
		Approximation.circuitsToSubstract[32][1] = 1;

		Approximation.circuitsToSubstract[33][0] = 0;
		Approximation.circuitsToSubstract[33][1] = 1;

		Approximation.circuitsToSubstract[34][0] = 0;
		Approximation.circuitsToSubstract[34][1] = 2;

		Approximation.circuitsToSubstract[35][0] = 0;
		Approximation.circuitsToSubstract[35][1] = 2;

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
		Approximation.circuitsToSubstract[42][1] = 2;

		Approximation.circuitsToSubstract[51][0] = 0;
		Approximation.circuitsToSubstract[51][1] = 2;

		Approximation.circuitsToSubstract[52][0] = 0;
		Approximation.circuitsToSubstract[52][1] = 1;

		Approximation.circuitsToSubstract[53][0] = 0;
		Approximation.circuitsToSubstract[53][1] = 1;

		Approximation.circuitsToSubstract[54][0] = 0;
		Approximation.circuitsToSubstract[54][1] = 1;

		Approximation.circuitsToSubstract[55][0] = 0;
		Approximation.circuitsToSubstract[55][1] = 1;

		Approximation.circuitsToSubstract[56][0] = 0;
		Approximation.circuitsToSubstract[56][1] = 2;

		Approximation.circuitsToSubstract[57][0] = 0;
		Approximation.circuitsToSubstract[57][1] = 1;

		Approximation.circuitsToSubstract[58][0] = 0;
		Approximation.circuitsToSubstract[58][1] = 2;

		Approximation.circuitsToSubstract[59][0] = 0;
		Approximation.circuitsToSubstract[59][1] = 2;

		Approximation.circuitsToSubstract[60][0] = 0;
		Approximation.circuitsToSubstract[60][1] = 1;

		Approximation.circuitsToSubstract[61][0] = 0;
		Approximation.circuitsToSubstract[61][1] = 1;
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
       for (int row = 0; row < n; row++) 
       { 
           for (int col = 0; col < n; col++) 
           { 
                 
               // Copying into temporary matrix  
               // only those element which are  
               // not in given row and column 
               if (row != p && col != q) 
               { 
                   temp[i][j++] = mat[row][col]; 
     
                   // Row is filled, so increase  
                   // row index and reset col  
                   //index 
                   if (j == n - 1) 
                   { 
                       j = 0; 
                       i++; 
                   } 
               } 
           } 
       } 
   } 
     
	
	
   /* Recursive function for finding determinant 
   of matrix. n is current dimension of mat[][]. */
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
       for (int f = 0; f < n; f++) 
       { 
           // Getting Cofactor of mat[0][f] 
           getCofactor(mat, temp, 0, f, n); 
           D += sign * mat[0][f]  
              * computeMatrixDeterminant(temp, n - 1); 
     
           // terms are to be added with  
           // alternate sign 
           sign = -sign; 
       } 
     
       return D; 
   }
}

