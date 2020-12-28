package gameTests;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import gameManager.Grid;
public class GridTest {

	@Test
	public void gridTests() {
		//User click test
				Boolean test = false;
				Grid g = new Grid(30, 900, 900, "FIVED", "TestJUnit");	
				try {
					g.move(950, 950);
				}
				catch(AssertionError ae) {
					test = true;
				}
				assertTrue(test);
				
				g.move(490,370); // point 16,12
				assertEquals(g.getScore(),1);
				g.move(490, 430); // point 16,14 (black point, should do nothing)
				assertEquals(g.getScore(),1);
				g.move(610,760); //point 20,25 (should do nothing, point unavailable)
				assertEquals(g.getScore(),1);
	}
}
