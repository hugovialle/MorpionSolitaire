package gameTests;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import gameManager.Grid;
import gameManager.MorpionSolitaireBoard;

public class MorpionSolitaireTest {
	
	@Test
	public void testMainArguments() {
		
		//gameMode validation test
		String username = "Tester";
		String gameMode = "FIVETT";
		String playerType = "HUMAN";
		Boolean test1 = false;
		try {
			new MorpionSolitaireBoard(username,gameMode,playerType);	
		}
		catch (IllegalArgumentException iae) {
			test1 = true;
		}
		assertTrue(test1);
		
		//playerType validation test
		username = "Tester";
		gameMode = "FIVET";
		playerType = "HUMANNSS";
		Boolean test2 = false;
		try {
			new MorpionSolitaireBoard(username,gameMode,playerType);	
		}
		catch (IllegalArgumentException iae) {
			test2 = true;
		}
		assertTrue(test2);
		
		//username control test
		username = "elizabethhhhhhhhhhhhhhhhhhh954854";
		gameMode = "FIVET";
		playerType = "HUMAN";
		Boolean test3 = false;
		try {
			new MorpionSolitaireBoard(username,gameMode,playerType);
		}
		catch (IllegalArgumentException iae) {
			test3 = true;
		}
		assertTrue(test3);
		
		
	}

}
