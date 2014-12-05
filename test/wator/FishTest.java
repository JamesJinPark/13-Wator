package wator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FishTest {

	@Test
	public void fishNeverStarves() {
		Ocean ocean = new Ocean(2, 2); 
		Fish fish = new Fish(0, 0); 
		ocean.set(0, 0, fish);
		fish.timeToGestation = 1; 
		int i = 0; 
		while (i++ < 100) {
			fish.makeOneStep(ocean);
			assertTrue(fish.justMoved);
			fish.justMoved = false;
		}
	}
	
	@Test
	public void fishCanProcreate() {
	}
}
