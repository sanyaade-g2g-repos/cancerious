package util;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.PriorityQueue;

public class BidirectionalAdjecencyMatrix implements Serializable{

	private static final long serialVersionUID = 3608589617663227922L;

	private double[][] matrix;
	
	private int n;
	
	public BidirectionalAdjecencyMatrix (int n){
		this.n = n;
		matrix = new double[n][];
		for (int i=0; i<n; i++){
			matrix[i]=new double[i];
			for (int j=0; j<i; j++){
				matrix[i][j] = 0.0;
			}
		}
	}
	
	public double get (int row, int col){
		if(row==col){
			throw new RuntimeException("row==col is not permitted");
		}
		else if(row<0 || row>=n || col<0 || col>=n){
			throw new RuntimeException(String.format("out of bounds. row: %d, col: %d, n: %d", row, col, n));
		}
		else if(row>col){
			return matrix[row][col];			
		}
		else{
			return matrix[col][row];
		}
	}
	
	public void set (int row, int col, double val){
		if(row==col){
			throw new RuntimeException("row==col is not permitted");
		}
		else if(row<0 || row>=n || col<0 || col>=n){
			throw new RuntimeException(String.format("out of bounds. row: %d, col: %d, n: %d", row, col, n));
		}
		else if(row>col){
			matrix[row][col] = val;
		}
		else{
			matrix[col][row] = val;			
		}
	}
	
	public double[] getAdjecencies(int index){
		if(index<0 || index>=n){
			return null;
		}
		double[] ret = new double[n];
		for (int i = 0; i < n; i++) {
			if(i==index){
				ret[i]=Double.MAX_VALUE;
			}
			else{
				ret[i]=get(i,index);
			}
		}
		return ret;
	}
	
	public int getLeastEdgedVertice(){
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		for (int i = 0; i < n; i++) {
			double[] adj = getAdjecencies(i);
			int zero = 0;
			for (int j = 0; j < n; j++) {
				if(adj[j]==0.0)
					zero++;
			}
			pq.add(0-zero);
		}
		return pq.poll();
	}
	
	public int[] getMaxValuedEdgeIndexes(int index, int count){
		double[] adj = getAdjecencies(index);
		PriorityQueue<EdgeValueVertexIndex> pq = new PriorityQueue<EdgeValueVertexIndex>();
		for (int i = 0; i < adj.length; i++) {
			if(i!=index)
				pq.add(new EdgeValueVertexIndex(i, adj[i]));
		}
		int[] ret = new int[count];
		for (int i = 0; i < count; i++) {
			ret[i] = pq.poll().index;
		}
		return ret;
	}
	
	private class EdgeValueVertexIndex implements Comparable<EdgeValueVertexIndex>{

		int index;
		double value;
		
		public EdgeValueVertexIndex(int index, double value) {
			super();
			this.index = index;
			this.value = value;
		}

		@Override
		public int compareTo(EdgeValueVertexIndex o) {
			double thisVal = this.value;
			double anotherVal = o.value;
			return (thisVal<anotherVal ? -1 : (thisVal==anotherVal ? 0 : 1));
		}
		
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("#.##");
		sb.append('\t');
		for (int i = 0; i < n; i++) {
			sb.append(i+"\t");
		}
		sb.append('\n');
		for (int i = 0; i < n; i++) {
			sb.append(i+"\t");
			for (int j = 0; j < n; j++) {
				if(i<=j) continue;
				else{
					sb.append(df.format(matrix[i][j])+"\t");
				}
					
			}
			sb.append('\n');
		}
		sb.append('\n');
		return sb.toString();
	}
}
