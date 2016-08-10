package main;

/**
 * The <tt>Percolation</tt> class implements a percolation model represented as
 * a grid of N-by-N sites (open or blocked). The class provides functions to
 * check whether a chosen site is <i>open</i> or full. A full site is an open site that
 * can be connected to the top row via a chain of neighboring open sites.
 *
 * @author sauravrt
 */

import edu.princeton.cs.algs4.QuickUnionUF;
import java.util.Arrays;
public class Percolation {
	private QuickUnionUF gridUF;
	private boolean[] gridOpen;	
	private int N;
	private int numOpenSites;
	private int virtualTop;
	private int virtualBottom;
			
/**
 * Constructor initializes N-by-N grid of blocked sites. A block site is 
 * indicated by -1 as its value.
 * @param N
 */
    public Percolation(int N) {  
    	/*
    	 * Initialize an empty UF grid with N*N + 2 sites. +2 sites for the top and
    	 * the bottom virtual sites. Index 0 corresponds to top virtual site and 
    	 * the index N*N + 1 corresponds to the bottom virtual site. Index 0 to (N*N - 1) 
    	 * correspond to the test sites.
    	 */
    	if (N <= 0)
    		throw new IllegalArgumentException("N must be > 0");
    	this.N = N;
    	this.numOpenSites = 0;
    	this.virtualTop = N*N;
    	this.virtualBottom = N*N + 1;    	
    	this.gridOpen = new boolean[N*N];    	//
    	gridUF = new QuickUnionUF(N*N + 2);
    	}
    
    /**
     * Map from a 2-dimensional (row, column) pair to a 1-dimensional 
     * union find object index
     * @param i Row index
     * @param j Column index
     * @return 1-D linear coordinate p = i*N + j
     */
    private int xyToLinear(int i, int j) {    	
    	if((i < 0) || (i > (N-1)) || (j < 0) || (j > (N-1))) {
    		return -1;
    	} else {
    		return (i * N + j);    	
    	}    	
    }
    
    public void open(int i, int j) {
    	// Any adjacent sites open?
    	// if yes connect to one else connect to self.
    	
    	int p = xyToLinear(i, j);
    	if (!gridOpen[p]) {  // if not open
    		gridOpen[p] = true;
    		numOpenSites++;
    	}       	    	
    	// union with neighbors
    	connectToNeighbors(i, j);
    }	
   
    private void connectToNeighbors(int i, int j) {
		// 
    	int p = xyToLinear(i, j);
		int[] nhd = {xyToLinear(i + 1, j),
				xyToLinear(i - 1, j),
				xyToLinear(i, j - 1),
				xyToLinear(i, j + 1)};
		
		for(int itr=0; itr < nhd.length; itr++){
			int q = nhd[itr];
			if(q != -1 && gridOpen[q]==true) {
				gridUF.union(p, q);
			}
		}
		if (i == 0) {
			gridUF.union(p, virtualTop);			
		} else if (i == N -1) {
			gridUF.union(virtualBottom, p);
		}
	}

	public boolean isOpen(int i, int j){
    	/*
    	 * A site is open if it is connected to any one of adjacent site
    	 */    	
		checkIndex(i, j);
    	return gridOpen[xyToLinear(i, j)];
    }

    public boolean isFull(int i, int j) {
    	// A site is full if it is connected to the top row (virtual point)
    	checkIndex(i, j);
    	int q = xyToLinear(i, j);
    	return gridUF.connected(this.virtualTop, q);
    }
    
    public boolean percolates() {
    	// percolates if top and bottom virtual sites are connected    	
        return gridUF.connected(this.virtualBottom, this.virtualTop);
    }
    
    public int numberOfOpenSites() {
    	return numOpenSites;
    }
    
    private void checkIndex(int i, int j) {
    	if((i < 0) || (i > (N-1)) || (j < 0) || (j > (N-1))) {
    		throw  new java.lang.IndexOutOfBoundsException();
    	}
    }

    public static void main(String[] args) {
    	int testRow = 4;
    	int testCol = 3;
    	Percolation perc = new Percolation(10);    	
    	System.out.println(perc.isOpen(testRow, testCol));
    	perc.open(testRow, testCol);
    	System.out.println(perc.isOpen(testRow, testCol));    	
	}    
}

