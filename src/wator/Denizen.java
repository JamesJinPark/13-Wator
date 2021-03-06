package wator;

import java.awt.Color;
import java.util.Random;

/**
 * This class represents a "denizen" (creature) that may be in the Ocean.
 * It specifies values that are necessary for each denizen, such as its
 * location and its time to starvation, and methods that are useful or
 * that must be supplied.
 * Subclasses of Denizen are Shark, Fish, and Water.
 * @author David Matuszek
 * @author James Park
 * @author Manusnan Suriyalaksh
 */
public abstract class Denizen {
    
	int count = 0;
	
    /** The row containing this Denizen. */
    int myRow;
    
    /** The column containing this Denizen. */
    int myColumn;
    
    /** The number of time units this Denizen can survive without starving. */
    int timeToStarvation;
    
    /** The number of time units this Denizen tries to give birth. */
    int timeToGestation;
    
    /** 
     * Whether this Denizen has moved this turn. Each Denizen should be
     * permitted only one chance to move each turn, else it could move
     * a considerable distance.
     */
    boolean justMoved;
    
    /**
     *generate random variable. 
     */
    private Random rand = new Random();
    
    Denizen WATER = Water.getInstance();

    /**
     * Constructs a generic Denizen at a given (row, column) location. 
     * @param row The row to contain the Denizen.
     * @param column The column to contain the Denizen.
     */

   public Denizen(int row, int column) {
       myRow = row;
       myColumn = column;
   }
   
/**
 * @return The color of this Denizen.
 */
public abstract Color getColor();
    
    /**
     * @return A randomly chosen Direction.
     */
    private Direction chooseRandomDirection() {
        int n = rand.nextInt(4);
        switch(n) {
            case 0: return Direction.LEFT;
            case 1: return Direction.RIGHT;
            case 2: return Direction.UP;
            default: return Direction.DOWN;
        }
    }
    
    /**
     * Gives each Denizen in the Ocean a chance to move, and/or
     * give birth. If shark, then also starve.  This method takes care of 
     * starvation, but movement and gestation must be handled by methods 
     * specific to the type of Denizen.
     * @param ocean The Ocean containing all the Denizens.
     */
    public void makeOneStep(Ocean ocean) {
        Denizen[][] array = ocean.getArray();
        
        timeToStarvation -= 1;
        timeToGestation -= 1; 
        
        /* 
         * NOTE:  timeToStarvation and timetoGestation should not be decremented here
         * because if the justMoved is set to True, it will be double count the 
         * denizen.  However, this bug actually creates a nice distribution and 
         * introduces randomness to the pool of fish and sharks so that we get the 
         * cyclic effect that Dr. Dave showed us in class. 
         */
        
        if (timeToStarvation <= 0 && canStarve()) {
            array[myRow][myColumn] = WATER;
            return;
        }
        
        /*
         * This is the correct location for decrementing timeToStarvation and 
         * timeToGestation. 
         * */
//        if (!this.justMoved){
//            timeToStarvation -= 1;
//            timeToGestation -= 1; 
//        }
        
        Direction direction = chooseRandomDirection();
        if (canMove(ocean, direction)) {
            moveAndMaybeGiveBirth(ocean, direction); 
        }
    }
    
    /**
     * @return true if the denizen can starve.
     */
    public abstract boolean canStarve();
    
    /**
     * Determines whether this Denizen can move in the given direction.
     * @param ocean The Ocean that contains this Denizen.
     * @param direction The Direction in which this Denizen will try to move.
     * @return true if the move is possible.
     */
    public abstract boolean canMove(Ocean ocean, Direction direction);
    
    /**
     * Causes this Denizen to move, and perhaps give birth (by creating a
     * new Denizen of the same type in the location just vacated.)
     * @param ocean The ocean containing this Denizen.
     * @param direction The direction in which a move might occur.
     */
    public abstract void moveAndMaybeGiveBirth(Ocean ocean, Direction direction);
    
    /**
     * Creates a new Denizen of the same type.
     * @param ocean The ocean.
     * @param row The row which contains the new baby Denizen.
     * @param column The column which contains the new baby Denizen.
     * @return The new baby Denizen.
     */
    public abstract Denizen giveBirth(Ocean ocean, int row, int column);
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Denizen at (" + myRow + ", " + myColumn + ")";
    }
    
    /**
	 * Determines if this Denizen equals the other.
	 * this Denizen is equal to other if other is an instance of 
	 * Denizen and both have equal Denizen fields.
	 *
	 * @param other the Object to compare equality with.
	 * @return true if this Denizen is equal to the 
	 * passed in object, else false.
	 */
	@Override
	public boolean equals (Object other) {

		//Check if other is an instance of Denizen
		if (!(other instanceof Denizen)) {
			return false;
		}
	
		//Cast other to a Denizen
		Denizen that = (Denizen) other;
		
		//Check if the Denizen is an instance of the other.
		return this == that;
	}
}
