package wator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FishTest {

	@Test
	public void fishNeverStarves() { 
		Ocean ocean = new Ocean(2, 2); 
		Fish fish = new Fish(0, 0); 
		ocean.set(0, 0, fish);
		fish.timeToGestation = 101; 
		int i = 0; 
		while (i++ < 100) {
			fish.makeOneStep(ocean);
			assertTrue(fish.justMoved);
			fish.justMoved = false;
		}
	}
	
	@Test
	public void fishCantMoveTowardsShark() {
		Ocean ocean = new Ocean(2, 2);
		Fish fish = new Fish(0, 0);
		Shark shark2 = new Shark(0, 1);
		Shark shark3 = new Shark(1, 0);
		Shark shark4 = new Shark(1, 1);
		int fishMovement = 0;
		ocean.set(0, 0, fish);
		ocean.set(0, 1, shark2);
		ocean.set(1, 0, shark3);
		ocean.set(1, 1, shark4);
		fish.timeToStarvation = 5; 
		fish.timeToGestation = 10; 
		int i = 0; 
		while (i++ < 4){
			fish.makeOneStep(ocean);
			if (fish.justMoved == true){
				fishMovement++;
			}
			fish.justMoved = false;
		} 
		assertTrue(fishMovement == 0 ); //fish has not been able to move
	}
	
	@Test
	public void fishCanProcreate() {
		Ocean ocean = new Ocean(2, 2);
		Fish fish = new Fish(0, 0);
		ocean.set(0, 0, fish);
		fish.timeToGestation = 0; // timeToGestation set 0 so move will make baby
		fish.makeOneStep(ocean);  // move and make baby
		int i = 0; 
		int j = 0;
		int fishCounter = 0;
		int waterCounter = 0;	
		for (i = 0; i < ocean.getNRows(); i += 1) {
            for (j = 0; j < ocean.getNColumns(); j += 1) {
            	if (ocean.getArray()[i][j] instanceof Fish){
            		fishCounter++; 
            	}
            	if (ocean.getArray()[i][j] instanceof Water){
            		waterCounter++;
            	}
            }
		}
		assertTrue(fishCounter == 2);	
		assertTrue(waterCounter == 2);	
	}
}
