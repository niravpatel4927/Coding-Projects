package SnakeGame;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class SnakeGame extends Applet implements Runnable, KeyListener{
	Graphics g2;
	Image img;
	Thread thread;
	Snake snake;
	boolean gameOver;
	Token token;
	int sleepTime;
	
	public void init() {
		this.resize(400, 400);
		img = createImage(400, 400);
		sleepTime = 35;
		gameOver = false;
		this.addKeyListener(this);
		g2 = img.getGraphics();
		snake = new Snake();
		token = new Token(snake);
		thread = new Thread(this);
		thread.start();
		
	}
	
	public void paint(Graphics g) {
		g2.setColor(Color.black);
		g2.fillRect(0, 0, 400, 400);
		if(!gameOver) {
			snake.draw(g2);
			token.draw(g2);
		} else {
			g2.setColor(Color.red);
			g2.drawString("Game Over", 180, 150);
			g2.drawString("Score: " + token.getScore(), 180, 170);
		}
		g.drawImage(img, 0, 0, null);
		
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	public void repaint(Graphics g) {
		paint(g);
	}

	@Override
	public void run() {
		for(;;) {
			
			if (!gameOver) {
				snake.move();
				this.checkGameOver();
				token.snakeCollision();
				sleepTime = token.getSleepTime();
			}
			
			this.repaint();
			try {
				
				Thread.sleep(sleepTime);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
	
		
		}
		
	}
	
	public void checkGameOver() {
		if(snake.getX() < 0 || snake.getX() > 396) {
			gameOver = true;
		}
		
		if(snake.getY() < 0 || snake.getY() > 397) {
			gameOver = true;
		}
		if (snake.snakeCollision()) {
			gameOver = true;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!snake.isMoving()) {
			if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || 
					e.getKeyCode() == KeyEvent.VK_RIGHT) {
				snake.setMoving(true);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (snake.getY() != 1) {
				snake.setYDir(-1);
				snake.setXDir(0);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (snake.getY() != -1) {
				snake.setYDir(1);
				snake.setXDir(0);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (snake.getX() != 1) {
				snake.setXDir(-1);
				snake.setYDir(0);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (snake.getX() != -1) {
				snake.setXDir(1);
				snake.setYDir(0);
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
}
