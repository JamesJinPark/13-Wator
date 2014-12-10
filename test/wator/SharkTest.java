package wator;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 * Tests the Shark class.
 * 
 * @author James Park
 * @author Manusnan Suriyalaksh
 *
 */
public class SharkTest {
	
	/**
	 * Tests whether a shark can starve after moving a certain 
	 * number of times.
	 */
	@Test
	public void sharkStarvesAfterXMoves() {
		Ocean ocean = new Ocean(2, 2); 
		Shark shark = new Shark(0, 0); 
		ocean.set(0, 0, shark);
		Denizen denizen0 = ocean.get(0, 0);
		assertEquals(denizen0, shark);
		shark.timeToStarvation = 5; 
		shark.timeToGestation = 10; 
		int i = 0; 
		while (i++ < 4){
			shark.makeOneStep(ocean);
			assertTrue(shark.justMoved);
			shark.justMoved = false;
		} 
        shark.makeOneStep(ocean);
        Denizen denizen1 = ocean.get(0, 0);	
		Denizen denizen2 = ocean.get(0, 1);
		Denizen denizen3 = ocean.get(1, 0);
		Denizen denizen4 = ocean.get(1, 1);
		Denizen WATER = Water.getInstance();	
        assertEquals(denizen1, WATER);
		assertEquals(denizen2, WATER);
		assertEquals(denizen3, WATER);
		assertEquals(denizen4, WATER);
	}
	
	/**
	 *  Tests whether a shark will be able to nourish itself
	 *  and not starve after eating a fish.
	 */
	@Test
	public void sharkNotStarveAfterEat() {
		Ocean ocean = new Ocean(2, 2); 
		Shark shark = new Shark(0, 0); 
		Fish fish = new Fish(0, 1);
		ocean.set(0, 0, shark);
		Denizen denizen0 = ocean.get(0, 0);
		assertEquals(denizen0, shark);
		ocean.set(0, 1, fish);
		ocean.set(1, 0, fish);
		ocean.set(1, 1, fish);
		Denizen denizen1 = ocean.get(0, 0);	
		Denizen denizen2 = ocean.get(0, 1);
		Denizen denizen3 = ocean.get(1, 0);
		Denizen denizen4 = ocean.get(1, 1);
		assertEquals(denizen1, shark);
		assertEquals(denizen2, fish);
		assertEquals(denizen3, fish);
		assertEquals(denizen4, fish);
		Parameters.setSharkStatistics(1, 1, 4);		    
		shark.timeToStarvation = 2; 
		shark.timeToGestation= 10; 
		int i = 0; 
		while (i++ < 5){
			/* As the shark moves and finds fish to eat, timeToStarvation gets
			 * reset to the value set in Parameters.setSharkStatistics or 4.
			 * By moving more than 2 times, this test shows that the shark does 
			 * not starve if it eats fish. Also, shows that fish do not impede
			 * shark movement.
			 * */
			shark.makeOneStep(ocean);
			assertTrue(shark.justMoved);
			shark.justMoved = false;
		} 
	}
	
	/**
	 * Tests that a shark cannot move towards a spot if a shark is already 
	 * in that spot.
	 */
	@Test
	public void sharkCantMoveTowardsShark() {
		Ocean ocean = new Ocean(2, 2);
		Shark shark1 = new Shark(0, 0);
		Shark shark2 = new Shark(0, 1);
		Shark shark3 = new Shark(1, 0);
		Shark shark4 = new Shark(1, 1);
		int sharkMovement = 0;
		ocean.set(0, 0, shark1);
		ocean.set(0, 1, shark2);
		ocean.set(1, 0, shark3);
		ocean.set(1, 1, shark4);
		shark1.timeToStarvation = 5; 
		shark1.timeToGestation = 10; 
		int i = 0; 
		while (i++ < 4){
			shark1.makeOneStep(ocean);
			if (shark1.justMoved == true){
				sharkMovement++;
			}
			shark1.justMoved = false;
		} 
		assertTrue(sharkMovement == 0 ); //shark1 has not been able to move
	}
	
	
	/**
	 * Test whether a shark can procreate.
	 */
	@Test
	public void sharkCanProcreate() {
		Ocean ocean = new Ocean(2, 2);
		Shark shark = new Shark(0, 0);
		ocean.set(0, 0, shark);
		shark.timeToStarvation = 2;
		shark.timeToGestation = 0; // timeToGestation set 0 so move will make baby
		shark.makeOneStep(ocean); // move and make baby
		int i = 0; 
		int j = 0;
		int sharkCounter = 0;
		int waterCounter = 0;	
		for (i = 0; i < ocean.getNRows(); i += 1) {
            for (j = 0; j < ocean.getNColumns(); j += 1) {
            	if (ocean.getArray()[i][j] instanceof Shark){
            		sharkCounter++; 
            	}
            	if (ocean.getArray()[i][j] instanceof Water){
            		waterCounter++;
            	}
            }
		}
		assertTrue(sharkCounter == 2);	
		assertTrue(waterCounter == 2);	
	}
}
