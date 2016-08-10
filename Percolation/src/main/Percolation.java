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
	private boolean[] gridFull;
	private int N;
	private int numOpenSites;
			
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
    	gridOpen = new boolean[N*N + 2];
    	gridFull = new boolean[N*N + 2];
    	gridUF = new QuickUnionUF(N*N + 2);
    	for(int i = 0; i < N*N + 2; i++) {
    		this.gridOpen[i] = false;
    		this.gridFull[i] = false;  
    	}
    	
    	this.N = N;
    	this.numOpenSites = 0;
    }
    
    private int xyToLinear(int i, int j) {
    	if (i <= 0) {
    		return (N * N);
    	} else if (i >= (N - 1)) {
    		return (N * N + 1);
    	} else if ((j - 1) < 0) {
    		return (i * N);
    	} else if ((j + 1) > (N - 1)) {
    		return (i * N + N - 1);
    	} else	{    	
    		return (i * N + j);
    	}
    }
    
    public void open(int i, int j) {
    	// Any adjacent sites open?
    	// if yes connect to one else connect to self.
    	
    	int siteIndex = xyToLinear(i, j);
    	if (!gridOpen[siteIndex]) {  // if not open
    		gridOpen[siteIndex] = true;
    		numOpenSites = numOpenSites + 1;
    		
        	if (gridOpen[xyToLinear(i - 1, j)]) {
        		gridUF.union(xyToLinear(i - 1, j), siteIndex);
        	} else if (gridOpen[xyToLinear(i + 1, j)]) {
        		gridUF.union(xyToLinear(i + 1, j), siteIndex);
        	} else if (gridOpen[xyToLinear(i, j + 1)]) {
        		gridUF.union(siteIndex, xyToLinear(i, j + 1));
        	} else if (gridOpen[xyToLinear(i, j - 1)]) {
        		gridUF.union(siteIndex,  xyToLinear(i, j - 1));
        	}    		
    	}    
    	flow(i, j);
    }
    
    // determine set of full sites using depth first search
    public void flow(int i, int j) {
        int N = gridOpen.length - 2;

        // base cases
        if (i < 0 || i >= N) return;    // invalid row
        if (j < 0 || j >= N) return;    // invalid column
        if (!gridOpen[xyToLinear(i, j)]) return;      // not an open site
        if (gridFull[xyToLinear(i, j)]) return;       // already marked as full

        // mark i-j as full
        gridFull[xyToLinear(i, j)] = true;

        flow(i+1, j);   // down
        flow(i, j+1);   // right
        flow(i, j-1);   // left
        flow(i-1, j);   // up
    }


    public boolean isOpen(int i, int j){
    	/*
    	 * A site is open if it is connected to any one of adjacent site
    	 */    	
    	return gridOpen[xyToLinear(i, j)];
    }

    public boolean isFull(int i, int j) {
    	// A site is full if it is connected to the top row (virtual point)    
        return gridFull[xyToLinear(i, j)];
    }
    public boolean percolates() {
    	// percolates if top and bottom virtual sites are connected    	
        return gridUF.connected(N * N, N * N + 1);
    }
    
    public int numberOfOpenSites() {
    	return numOpenSites;
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

