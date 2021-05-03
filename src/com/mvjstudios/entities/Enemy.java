package com.mvjstudios.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.mvjstudios.main.Game;
import com.mvjstudios.main.Sound;
import com.mvjstudios.world.Camera;
import com.mvjstudios.world.World;

public class Enemy extends Entity {
	
	private double speed = 0.7;
	
	private int maskx = 8, masky = 9, maskw = 8, maskh =8;

	private int frames = 0,maxFrames = 12, index = 0,maxIndex = 1;
	
	private BufferedImage[] sprites;
	
	private int life = 10;
	
	private boolean isDamaged = false;
	private int damageFrames = 10, damageCurrent = 0;
	
	
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites= new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(80, 0, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(96, 0, 16, 16);
	}
	public void tick() {
		
		if (isColiddingWithPlayer() == false) {
		if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY(),  z)
				&& !isColidding((int)(x+speed), this.getY())){
			x+=speed;
		}
		else if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY(),z)
				&& !isColidding((int)(x-speed), this.getY())) {
			x-=speed;
		}
		
		if((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed),z) &&
				!isColidding(this.getX(), (int)(y+speed))){
			y+=speed;
		}
		else if((int)y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed),z) &&
				!isColidding(this.getX(), (int)(y-speed))) {
			y-=speed;
		}
		}else {
			// Estamos colidindo
			if(Game.rand.nextInt(100) < 10) {
				Sound.HURTEFFECT.play();
				Game.player.life-= Game.rand.nextInt(3);
				Game.player.isDamage = true;
				//System.out.println("Vida" + Game.player.life); deixando aqui , para configurar no Player
				//(Game.player.life <=0 ) {
					//Game Over
				//	System.exit(1);
				//}
				//System.out.println();
			}
		
			
		}
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
			
			collidingBullet();
			
			if(life <= 0) {
				destroySelf();
				return;
			}
			
			if(isDamaged) {
				damageCurrent++;
				if (damageCurrent == damageFrames) {
					damageCurrent = 0;
					isDamaged = false;
					
				}
			}
			
	}
	
	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
	}
	
	public void collidingBullet() {
		for(int i = 0; i < Game.bullets.size(); i ++) {
			Entity e = Game.bullets.get(i);
			if(e instanceof Shoot) {
				if(Entity.isColidding(this, e)) {
					isDamaged = true;
					life--;
					Game.bullets.remove(i);
					System.out.println("COLISÃO!!!!!!");
					return;
				}
			}
		}
		
		
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);	
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16,16);
		
		return enemyCurrent.intersects(player);

	}
	public boolean isColidding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh); // criar retangulos para colisões
		
		for(int i = 0; i< Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this)
				continue;
			
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx, e.getY()+ masky,  maskw, maskh);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
				
			}
		}
		
		return false;
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
			g.drawImage(sprites[index],this.getX() - Camera.x,this.getY() - Camera.y, null);
		} else {
			g.drawImage(Entity.ENEMY_FEEDBACK,this.getX() - Camera.x,this.getY() - Camera.y, null);
		}
		
		
		//super.render(g);
		//g.setColor(Color.blue);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
	}
}
