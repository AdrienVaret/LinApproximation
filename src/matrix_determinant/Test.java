package matrix_determinant;

import java.util.concurrent.ThreadLocalRandom;

public class Test {

	private static double [][] genMatrix(int size){
		
		double [][] M = new double[size][size];
		
		for (int i = 0 ; i < size ; i++) {
			int j1 = -1, j2 = -1;
			j1 = ThreadLocalRandom.current().nextInt(size);
			
			while (j2 == -1) {
				int rand = ThreadLocalRandom.current().nextInt(size);
				if (rand != j1)
					j2 = rand;
			}
			
			M[i][j1] = 1;
			M[i][j2] = 1;
		}
		
		return M;
	}
	
	public static void main(String [] args) {
		
		double [][] M = genMatrix(20);
		
		long begin = System.currentTimeMillis();
		double D = MatrixOperations.matrixDeterminant(M);
		long end = System.currentTimeMillis();
		System.out.println("D = " + D + ", time : " + (end - begin) + " ms.");
	}
}
