package edu.southalabama.csc527.smallworld.persistence;

import java.io.File;

import junit.framework.TestCase;
import edu.southalabama.csc527.smallworld.TestConstants;
import edu.southalabama.csc527.smallworld.model.World;
import edu.southalabama.csc527.smallworld.persistence.WorldPersistence;

public class ItemsSaveLoadTest extends TestCase {
	public void testLoadTestItemsWorld() {
		World world = new World();
		try {
			world = WorldPersistence.loadWorld(TestConstants.TESTITEMSWORLD);
		} catch (IllegalStateException e) {
			fail();
		}
		
	}
	
	// insufficient tests but bare minimum functionalities
	public void testItemsLoadedRightTest() {
		World world = new World();
		world = WorldPersistence.loadWorld(TestConstants.TESTITEMSWORLD);
		assert(world.getItems().getObjects().size() == 3);
		assert(world.getLocationRules().getObjects().size()==2);
		assert(world.getItem("Key").getName().equals("Key"));
		assert(world.getLocationRule("Bedroom").get(0).getPlaceName().equals("Bedroom"));
		assert(world.getItem("Chocolate Pudding").getName().equals("Chocolate Pudding"));
		assert(world.getLocationRule("Living Room").get(0).getPlaceName().equals("Living Room"));
		assert(world.getItem("Tshirt").getName().equals("Tshirt"));
	}
}
