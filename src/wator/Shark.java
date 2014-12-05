package wator;

import java.awt.Color;

/**
 * @author David Matuszek
 * @author James Park
 * @author
 */
public class Shark extends Denizen {
    
    /**
     * Constructs a Shark at a given (row, column) location. 
     * @param row The row to contain the Shark.
     * @param column The column to contain the Shark.
     */
    public Shark(int row, int column) {
        super(row, column);
        timeToGestation = Parameters.sharkGestationPeriod;
        timeToStarvation = Parameters.sharkStarvationPeriod;
    }
    
    /* (non-Javadoc)
     * @see wator.Denizen#getColor()
     */
    @Override
    public Color getColor() {
        return Color.red;
    }

    /* (non-Javadoc)
     * @see wator.Denizen#canMove(wator.Ocean, wator.Direction)
     */
    @Override
    public boolean canMove(Ocean ocean, Direction direction) {
        if (justMoved) {
            justMoved = false;
            return false;
        }
        Denizen neighbor = ocean.get(myRow, myColumn, direction);
        return ! (neighbor instanceof Shark);
    }
    
    public boolean canEat(Ocean ocean, Direction direction){
    	Denizen neighbor = ocean.get(myRow, myColumn, direction);
    	return neighbor instanceof Fish; 
    }
    
    /* (non-Javadoc)
     * @see wator.Denizen#moveAndMaybeGiveBirth(wator.Ocean, wator.Direction)
     */
    @Override
    public void moveAndMaybeGiveBirth(Ocean ocean, Direction direction) {
        if (timeToGestation <= 0) {
            giveBirth(ocean, myRow, myColumn);
            this.timeToGestation = Parameters.sharkGestationPeriod;
        } else {
            ocean.set(myRow, myColumn, WATER);
        }        
        if (canEat(ocean, direction)){
        	timeToStarvation = Parameters.sharkStarvationPeriod;
        }
        ocean.set(myRow, myColumn, direction, this);
        justMoved = true;
        System.out.println(this + " just moved.");
    }

    /* (non-Javadoc)
     * @see wator.Denizen#giveBirth(wator.Ocean, int, int)
     */
    @Override
    public Shark giveBirth(Ocean ocean, int row, int column) {
        Shark babyShark = new Shark(row, column);
        ocean.set(row, column, babyShark);
        return babyShark;
    }
    
    @Override
    public String toString() {
        return "Shark at (" + myRow + ", " + myColumn + ")";
    }

	@Override
	public boolean canStarve() {
		// TODO Auto-generated method stub
		return true;
	}
    
}
