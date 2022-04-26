package application;

public class Tree implements Comparable<Tree>{
	
	private char c;
	private int freq;
	public Tree left;
	public Tree right;
	
	
	public Tree()
	{
		
	}
	
	public Tree(char c, int freq, Tree left, Tree right) {
		this.c = c;
		this.freq = freq;
		this.left = left;
		this.right = right;
	}

	public Tree(char c, int freq)
	{
		this.c = c;
		this.freq = freq;
	}
	
	public Tree(char c)
	{
		this.c = c;
	}


	public char getC() {
		return c;
	}


	public void setC(char c) {
		this.c = c;
	}


	public int getFreq() {
		return freq;
	}


	public void setFreq(int freq) {
		this.freq = freq;
	}

	@Override
	public int compareTo(Tree o) {
		// TODO Auto-generated method stub
		return this.freq - o.freq;
	}
	
	
	public boolean isLeaf()
	{
		return ((left == null) && (right == null));
	}
	
	
	
	

}
