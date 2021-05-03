package com.mvjstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.mvjstudios.main.Game;
import com.mvjstudios.world.Camera;

public class Shoot extends Entity {
	
	private double dx;
	private double dy;
	private double speed = 4;
	
	private int bulletAmount = 40, curBullet = 0;
	
	public Shoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}


	
	public void tick() {
		x += dx * speed;
		y += dy * speed;
		curBullet ++;
		if(curBullet == bulletAmount) {
			Game.bullets.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
	}
}
