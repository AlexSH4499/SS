import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


 public class Game extends Canvas{
	 /** The stragey that allows us to use accelerate page flipping */
		private BufferStrategy strategy;
		/** True if the game is currently "running", i.e. the game loop is looping */
		private boolean gameRunning = true;
		/** The list of all the entities that exist in our game */
		private ArrayList entities = new ArrayList();
		
		/** The entity representing the player */
	
		private double moveSpeed = 300;
		/** The message to display which waiting for a key press */
		private String message = "";
		/** True if we're holding up game play until a key has been pressed */
		private boolean waitingForKeyPress = true;
		/** True if the left cursor key is currently pressed */
		private boolean leftPressed = false;
		/** True if the right cursor key is currently pressed */
		private boolean rightPressed = false;
		/** True if we are firing */
		private boolean logicRequiredThisLoop = false;
	//ICONS
	 private Entity venus;
	 private ShipEntity ship;


	 
	 public Game() {
			
			JFrame container = new JFrame("Solar System");
			
		
			JPanel panel = (JPanel) container.getContentPane();
			panel.setPreferredSize(new Dimension(1080,720));
			panel.setLayout(null);
			
			
			setBounds(0,0,1080,720);
			panel.add(this);
			
			// Tell AWT not to bother repainting our canvas
			setIgnoreRepaint(true);
			
	
			container.pack();
			container.setResizable(false);
			container.setVisible(true);
			
		
			container.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			
			// add a key input system (defined below) to our canvas
			// so we can respond to key pressed
			addKeyListener(new KeyInputHandler());
			
			// request the focus so key events come to us
			requestFocus();

			// create the buffering strategy which will allow AWT
			// to manage our accelerated graphics
			createBufferStrategy(2);
			strategy = getBufferStrategy();
			
			// initialize the entities in our game so there's something
			// to see at startup
			initEntities();
		}
		
		/**
		 * Start a fresh game, this should clear out any old data and
		 * create a new set.
		 */
		private void startGame() {
			// clear out any existing entities and intialise a new set
			entities.clear();
			initEntities();
			
			// blank out any keyboard settings we might currently have
			leftPressed = false;
			rightPressed = false;
			
		}
		
		/**
		 * Initialise the starting state of the entities (ship and aliens). Each
		 * entitiy will be added to the overall list of entities in the game.
		 */
		private void initEntities() {
			// create the player ship and place it roughly in the center of the screen
			venus = new VenusEntity(this,"mercury.gif",0,0); // not venus but mercury, fix later
			entities.add(venus);
			
		ship = new ShipEntity(this,"ship.png",100,300); //add ship later
			entities.add(ship);
		
		}
		
	
		/**
		 * Notification from a game entity that the logic of the game
		 * should be run at the next opportunity (normally as a result of some
		 * game event)
		 */
		public void updateLogic() {
			logicRequiredThisLoop = true;
		}
		

		public void gameLoop() {
			long lastLoopTime = System.currentTimeMillis();
			
	
			while (gameRunning) {
				// work out how long its been since the last update, this
				// will be used to calculate how far the entities should
				// move this loop
				long delta = System.currentTimeMillis() - lastLoopTime;
				lastLoopTime = System.currentTimeMillis();
				
				// Get hold of a graphics context for the accelerated 
				// surface and blank it out
				Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
				g.setColor(Color.black);
				g.fillRect(0,0,1080,720);
				
				// cycle round asking each entity to move itself
				if (!waitingForKeyPress) {
					for (int i=0;i<entities.size();i++) {
				    	Entity entity = (Entity) entities.get(i);
						
						entity.move(delta);
					}
				}
				
				// cycle round drawing all the entities we have in the game
				for (int i=0;i<entities.size();i++) {
					Entity entity = (Entity) entities.get(i);
					
					entity.draw(g);
				}
				


				// if a game event has indicated that game logic should
				// be resolved, cycle round every entity requesting that
				// their personal logic should be considered.
				if (logicRequiredThisLoop) {
					for (int i=0;i<entities.size();i++) {
						Entity entity = (Entity) entities.get(i);
						entity.doLogic();
					}
					
					logicRequiredThisLoop = false;
				}
				
				// if we're waiting for an "any key" press then draw the 
				// current message 
				if (waitingForKeyPress) {
					g.setColor(Color.white);
					g.drawString(message,(800-g.getFontMetrics().stringWidth(message))/2,250);
					g.drawString("Press any key",(800-g.getFontMetrics().stringWidth("Press any key"))/2,300);
				}
				
				
				
				// finally, we've completed drawing so clear up the graphics
				// and flip the buffer over
				g.dispose();
				strategy.show();
				
				// resolve the movement of the ship. First assume the ship 
				// isn't moving. If either cursor key is pressed then
				// update the movement appropriately
					ship.setHorizontalMovement(0);
				
				if ((leftPressed) && (!rightPressed)) {
					ship.setHorizontalMovement(-moveSpeed);
				} else if ((rightPressed) && (!leftPressed)) {
					ship.setHorizontalMovement(moveSpeed);
				}
				
				

				
				// finally pause for a bit. Note: this should run us at about
				// 100 fps but on windows this might vary each loop due to
				// a bad implementation of timer
				try { Thread.sleep(90); } catch (Exception e) {} //change back to 10
			}
		}
		

		private class KeyInputHandler extends KeyAdapter {
			/** The number of key presses we've had while waiting for an "any key" press */
			private int pressCount = 1;
			
	
			public void keyPressed(KeyEvent e) {
				// if we're waiting for an "any key" typed then we don't 
				// want to do anything with just a "press"
				if (waitingForKeyPress) {
					return;
				}
				
				
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					leftPressed = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					rightPressed = true;
				}
			
			} 
			
			/**
			 * Notification from AWT that a key has been released.
			 *
			 * @param e The details of the key that was released 
			 */
			public void keyReleased(KeyEvent e) {
				// if we're waiting for an "any key" typed then we don't 
				// want to do anything with just a "released"
				if (waitingForKeyPress) {
					return;
				}
				
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					leftPressed = false; //change back to false
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					rightPressed = false; //change back to false
				}
				
			}

			/**
			 * Notification from AWT that a key has been typed. Note that
			 * typing a key means to both press and then release it.
			 *
			 * @param e The details of the key that was typed. 
			 */
			public void keyTyped(KeyEvent e) {
				// if we're waiting for a "any key" type then
				// check if we've recieved any recently. We may
				// have had a keyType() event from the user releasing
				// the shoot or move keys, hence the use of the "pressCount"
				// counter.
				if (waitingForKeyPress) {
					if (pressCount == 1) {
						// since we've now recieved our key typed
						// event we can mark it as such and start 
						// our new game
						waitingForKeyPress =false;
						startGame();
						pressCount = 0;
					}
					else {
						pressCount++;
					}
				}
				
				// if we hit escape, then quit the game
				if (e.getKeyChar() == 27) {
					System.exit(0);
				}
			}
		}
		

		public static void main(String argv[]) {
			Game g =new Game();

			// Start the main game loop, note: this method will not
			// return until the game has finished running. Hence we are
			// using the actual main thread to run the game.
			g.gameLoop();
		}
	}
