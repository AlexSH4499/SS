import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Game extends Canvas {
	public Game(){
	
		Framework.gameState = Framework.gameState.GAME_CONTENT_LOADING;
		
		Thread threadForInitGame = new Thread(){
		@Override
		public void  run(){
			Initialize();
			LoadContent();
			
			Framework.gameState = 
		Framework.GameState.PLAYING;
		}
		
		
		
	};
	threadForInitGame.start();
	
}
	private static BufferedImage img1,img2,img3;
	
	
	private static void loadImage()throws IOException
	{
		img1 = ImageIO.read(new File("src/resources/Latias-V_1-by-Alex-SH.png")); //not final background image, only test
		img2 = ImageIO.read(new File("src/resources/mercury.png"));
		img3 = ImageIO.read(new File("src/resources/spaceship.png"));
	}
	
	private void Initialize()
		{
		 	g.add(img1);
		 	g.add(img2);
		 	g.add(img3);
		 	
		}
	private void LoadContent()
	 {
		
		loadContent lc;
		
		lc.loadImage();
	 } 
	
	
	public void restartGame() {
		if(KeyListener e = false){
			
		}
		
	}

	public void Draw(Graphics2D g2)//add back mousePosition
	{
		g2.drawRect(1920, 1920,1080,1080);
		g2.fillRect(1920, 1920,1080,1080);
		g2.drawImage(img1, 1920, 1920, 1080, 1080, null);
		g2.drawImage(img2,100,100,100,100,null);
		g2.drawImage(img3,50,50,75,75,null);
	
	}

	public void UpdateGame(long gameTime, Point mousePosition) {
	//add if keys get pressed, update
	}
	

}
