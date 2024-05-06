import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements ActionListener{
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 20;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts =6;
    int appleEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		if(running) {
			/*
			for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH , i*UNIT_SIZE);
			}*/
			g.setColor(Color.RED);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			for(int i=0;i<bodyParts;i++) {
				if(i==0) {
					g.setColor(Color.GREEN);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}else {
					g.setColor(new Color(45,180,0));
					g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			//Game Score
			g.setColor(Color.RED);
			g.setFont(new Font("Ink Free",Font.BOLD,45));
			FontMetrics fontMatrics = getFontMetrics(g.getFont());  
			g.drawString("Score : "+appleEaten,(SCREEN_WIDTH-fontMatrics.stringWidth("Score : "+appleEaten))/2, g.getFont().getSize());
		}else {
			gameOver(g);
		}
	}
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
	}
	public void move() {
		for(int i=bodyParts;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;	
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;	
		case 'R':
		    x[0] = x[0] + UNIT_SIZE;
		    break;
		}
	}
	public void checkApple() {
		if((x[0]==appleX) && y[0]==appleY) {
			bodyParts++;
			appleEaten++;
			newApple();
		}
	}
	public void checkCollision() {
		//if head collision with body
		 for(int i=bodyParts;i>0;i--) {
			 if((x[0]==x[i])&& (y[0]==y[i])) {
				 running = false;
			 }
		 }
		 //if head touched with left body
		 if(x[0]<0) {
			 running=false;
		 }
		 //if head touched right body
		 if(x[0]>SCREEN_WIDTH) {
			 running = false;
		 }
		 //if head touched top body
		 if(y[0]<0) {
			 running = false;
		 }
		 //if head touched bottom border
		 if(y[0]>SCREEN_HEIGHT) {
			 running=false;
		 }
		 if(!running) {
			 timer.stop();
		 }
	}
	public void gameOver(Graphics g) {
		//Game Score
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free",Font.BOLD,45));
		FontMetrics fontMatrics1 = getFontMetrics(g.getFont());  
		g.drawString("Score : "+appleEaten,(SCREEN_WIDTH-fontMatrics1.stringWidth("Score : "+appleEaten))/2, g.getFont().getSize());
		//Game Over
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics fontMatrics2 = getFontMetrics(g.getFont());  
		g.drawString("Game Over",(SCREEN_WIDTH-fontMatrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			checkApple();
			checkCollision();
		}
		repaint();
	}
   public class MyKeyAdapter extends KeyAdapter{
	   public void keyPressed(KeyEvent e) {
		   switch(e.getKeyCode()) {
		   case KeyEvent.VK_LEFT:
		       if(direction != 'R') {
		    	   direction = 'L';
		       }
		       break;
		   case KeyEvent.VK_RIGHT:
		       if(direction != 'L') {
		    	   direction = 'R';
		       }
		       break;    
		   case KeyEvent.VK_UP:
		       if(direction != 'D') {
		    	   direction = 'U';
		       }
		       break;  
		   case KeyEvent.VK_DOWN:
		       if(direction != 'U') {
		    	   direction = 'D';
		       }
		       break;    
		   }
	   }
   }
}
