package SnakeGame;

import java.awt.Color;
import java.awt.Graphics;

public class Token {

	private int x;
	private int y;
	private int score = 0;
	private Snake snake;
	private int sleepTime = 35;
	public Token(Snake s) {
		x = (int) (Math.random() * 395);
		y = (int) (Math.random() * 395);
		this.snake = s;

	}
	
	public void changePosition() {
		x = (int) (Math.random() * 395);
		y = (int) (Math.random() * 395);
		
	}
	
	public int getScore() {
		return score;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, 6, 6);
	}
	
	public int getSleepTime() {
		return sleepTime;
	}
	
	public boolean snakeCollision() {
		int snakeX = snake.getX() + 2;
		int snakeY = snake.getY() + 2;
		if (snakeX >= x - 1 && snakeX <= (x + 7)) {
			if (snakeY >= y - 1 && snakeY <= (y + 7)) {
				changePosition();
				snake.setElongate(true);
				score++;
				sleepTime = sleepTime - 2;
				return true;
			}
		}
		return false;
	}
}
