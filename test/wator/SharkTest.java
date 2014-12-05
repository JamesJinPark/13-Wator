package wator;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SharkTest {
	
	@Test
	public void sharkStarvesAfterXMoves() {
		Ocean ocean = new Ocean(2, 2); 
		Shark shark = new Shark(0, 0); 
		ocean.set(0, 0, shark);
		Denizen denizen0 = ocean.get(0, 0);
		assertEquals(denizen0, shark);
		shark.timeToStarvation = 5; 
		shark.timeToGestation = 1; 
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
		shark.timeToGestation= 1; 
		int i = 0; 
		while (i++ < 5){
			/* As the shark moves and finds fish to eat, timeToStarvation gets
			 * reset to the value set in Parameters.setSharkStatistics or 4.
			 * By moving more than 2 times, this test shows that the shark does 
			 * not starve if it eats fish.
			 * */
			shark.makeOneStep(ocean);
			assertTrue(shark.justMoved);
			shark.justMoved = false;
		} 
	}
	
	@Test
	public void sharkCanProcreate() {
	}
}
