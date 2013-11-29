import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Game extends Canvas {
	public Game(){
		Game g;
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
	private static BufferedImage img;
	private static void loadImage()throws IOException{ img = ImageIO.read(new File("src/resources/Latias-V_1-by-Alex-SH.png"));
	}
	private void Initialize(){
		
	}
	private void LoadContent(){
		
		try {
			Game.loadImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void restartGame() {
		
		
	}

	public void Draw(Graphics2D g2)//add back mousePosition
	{
		g2.drawRect(800, 800,800,800);
		g2.fillRect(800, 800,800,800);
		g2.drawImage(img, 1920, 1920, 1080, 1080, null);
		g2.dispose();
	}

	public void UpdateGame(long gameTime, Point mousePosition) {
		// TODO Auto-generated method stub
		
	}
	

}
