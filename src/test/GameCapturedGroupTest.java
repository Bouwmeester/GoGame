package test;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.nedap.go.gogame.Game;
import com.nedap.go.gogame.Humanplayer;
import com.nedap.go.gogame.Player;
import com.nedap.go.gogame.Board;
import com.nedap.go.gogame.Field;


public class GameCapturedGroupTest {
	private Game game; 
	//private Board board;
	private Player player1;
	private Player player2;
	
	
	
	@Before
	public void setUp() {
		player1 = new Humanplayer();
		player2 = new Humanplayer();
		game = new Game(player1, player2);	//null = player 
		//board = new Board(9);
	}
	
	@Test
	public void testgetNeighbors() {
		game.getBoard().addStone(2, 2, true);
		game.getBoard().addStone(2, 3, true);
		game.getBoard().addStone(1, 2, false);
		game.getBoard().addStone(1, 3, false);
		game.getBoard().addStone(2, 4, false);
		game.getBoard().addStone(3, 3, false);
		game.getBoard().addStone(2, 3, false);
		game.getBoard().addStone(1, 2, false);
		
//		Point point = new Point(2, 3);
//		ArrayList<Point> testSet = game.getNeighbor(point);
//		//ArrayList<Point> list = new ArrayList<Point>(testSet);
//		
//		assertEquals(testSet.size(), 4);
//		assertEquals(testSet.get(0).x, 1);
//		assertEquals(testSet.get(0).y, 3);
//		
		Point point2 = new Point(0, 0);
		ArrayList<Point> testSet = game.getNeighbor(point2);
		//ArrayList<Point> list = new ArrayList<Point>(testSet);
		
		assertEquals(testSet.size(), 2);
		assertEquals(testSet.get(0).x, 1);
		assertEquals(testSet.get(0).y, 0);
		
		//assertEquals((2,2), (2,3), game.getNeighbor(point));
		
	}
	
	@Test
	public void testCapturedGroup() {
		game.getBoard().addStone(2, 2, true);
		game.getBoard().addStone(2, 3, true);
		game.getBoard().addStone(1, 2, false);
		game.getBoard().addStone(1, 3, false);
		game.getBoard().addStone(2, 4, false);
		game.getBoard().addStone(3, 3, false);
		game.getBoard().addStone(2, 3, false);
		game.getBoard().addStone(1, 2, false);
		
		
		
	}
}



//*/
//public class DictionaryAttackTest {
//
//   /** Testvariabele for a <tt>DictionaryAttack</tt> object. */
//   private DictionaryAttack dictionaryAttack;
//
//   /** Path to the text file. */
//   private static final String PATH = "src/ss/week6/test/"; //Your path to the test folder
//
//   @Before
//   public void setUp() {
//       dictionaryAttack = new DictionaryAttack();
//       try {
//           dictionaryAttack.readPasswords(PATH + "LeakedPasswords.txt");
//       } catch (IOException e) {
//           e.printStackTrace();
//       }
//   }
//
//   /**
//    * Test for <tt>getPasswordHash</tt>.
//    */
//   @Test
//   public void testGetPasswordHash() {
//       assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", 
//       		dictionaryAttack.getPasswordHash("password"));
//   }
//
//   /**
//    * Test for <tt>checkPassword</tt>.
//    */
//   @Test
//   public void testCheckPassword() {
//       assertTrue(dictionaryAttack.checkPassword("katrine", "spongebob"));
//   }
//
//}