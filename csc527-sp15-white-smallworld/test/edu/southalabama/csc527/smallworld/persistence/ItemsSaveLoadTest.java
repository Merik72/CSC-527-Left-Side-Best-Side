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
	
	public void testItemsSavedAndLoadedRightTest() {
		World world = new World();
		world = WorldPersistence.loadWorld(TestConstants.TESTITEMSWORLD);
		assert(world.getItems().getObjects().size() == 3);
		assert(world.getLocationRules().getObjects().size()==2);
		assert(world.getItem("Key").getName().equals("Key"));
		assert(world.getLocationRule("Bedroom").get(0).getPlaceName().equals("Bedroom"));
		assert(world.getItem("Chocolate Pudding").getName().equals("Chocolate Pudding"));
		assert(world.getLocationRule("Living Room").get(0).getPlaceName().equals("Living Room"));
		assert(world.getItem("Tshirt").getName().equals("Tshirt"));
		
		WorldPersistence.saveWorld(world, new File(TestConstants.SAVEFILE));
		World world2 = new World();
		world2 = WorldPersistence.loadWorld(new File(TestConstants.SAVEFILE));
		assert(world.getItems().getObjects().size() == world2.getItems().getObjects().size() );
		assert(world.getLocationRules().getObjects().size()==world2.getLocationRules().getObjects().size() );
		assert(world.getItem("Key").getName().equals(world2.getItem("Key").getName()));
		assert(world.getLocationRule("Bedroom").get(0).getPlaceName().equals(world2.getLocationRule("Bedroom").get(0).getPlaceName()));
		assert(world.getItem("Chocolate Pudding").getName().equals(world2.getItem("Chocolate Pudding").getName()));
		assert(world.getLocationRule("Living Room").get(0).getPlaceName().equals(world2.getLocationRule("Living Room").get(0).getPlaceName()));
		assert(world.getItem("Tshirt").getName().equals(world2.getItem("Tshirt").getName()));
	}
}
