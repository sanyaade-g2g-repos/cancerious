package util;

import java.io.Serializable;

public class BidirectionalAdjecencyMatrix implements Serializable{

	private static final long serialVersionUID = 3608589617663227922L;

	private double[][] matrix;
	
	public BidirectionalAdjecencyMatrix (int n){
		matrix = new double[n][];
		for (int i=0; i<n; i++){
			matrix[i]=new double[i];
			for (int j=0; j<i; j++){
				matrix[i][j] = 0.0;
			}
		}
	}
	
	public double get (int row, int col){
		return matrix[row][col];
	}
	
	public void set (int row, int col, int val){
		matrix[row][col] = val;
	}
}
