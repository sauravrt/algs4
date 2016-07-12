package main;

/**
 * The <tt>Percolation</tt> class implements a percolation model represented as
 * a grid of N-by-N sites (open or blocked). The class provides functions to
 * check whether a chosen site is <i>open</i> or full. A full site is an open site that
 * can be connected to the top row via a chain of neighboring open sites.
 *
 * @author sauravrt
 */
public class Percolation {
	private int[][] siteGrid;
	private final int VIRTUAL_SITE = -2;
	
/**
 * Constructor initializes N-by-N grid of blocked sites. A block site is 
 * indicated by -1 as its value.
 * @param N
 */
    public Percolation(int N) {  
    	siteGrid = new int[N][N];		
    	for(int i = 0; i < N; i++){
    		for(int j = 0; j < N; j++) {
    		siteGrid[i][j] = -1;   // blocked sites
    		}
    	}
    }
    
    public void open(int i, int j) {
    	// Any adjacent sites open?
    	// if yes connect to one else connect to self.
    }
    
    public boolean isOpen(int i, int j){
        return (siteGrid[i][j] != -1);
    }

    public boolean isFull(int i, int j) {
    	// A site is full if it is connected to the top row (virtual point)    	
        return (siteGrid[i][j] == VIRTUAL_SITE);
    }
    public boolean percolates() {
    	// percolates if there is a full site at the bottom row
        return true;
    }

//    public static void main(String[] args) {
//	}
}

