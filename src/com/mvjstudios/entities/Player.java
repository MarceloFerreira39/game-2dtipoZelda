package com.mvjstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.mvjstudios.main.Game;
import com.mvjstudios.world.Camera;
import com.mvjstudios.world.World;


public class Player extends Entity{
	
	public boolean up, left, down, right, idle;

	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3; 
    public int dir = 0;
	public double speed = 1.4;
	

	private int frames = 0,maxFrames = 8, index = 0,maxIndex = 3;
	private boolean moved = false;
	
	private BufferedImage[] idleRight;
	private BufferedImage[] idleLeft;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	private BufferedImage[] playerDamage;
	
	private boolean hasGun = false;
	
	
	
	public int ammo = 0;
	
	public boolean isDamage = false;
	
	private int damageFrames = 0, damageIndex = 0;

	public boolean shoot = false, mouseShoot = false;
	
	public int mouseX, mouseY;
	
	public  double life = 100, maxLife = 100;
	
	public boolean jump = false;
	
	public boolean isJumping = false;
	
	public static int z = 0;
	
	public int jumpFrames = 25, jumpCur = 0;
	
	public boolean jumpUp = false , jumpDown = false;
	
	public int jumpSpd = 2;
	
	

	
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
        
		idleRight = new BufferedImage[4];
		idleLeft = new BufferedImage[4];
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		
		playerDamage = new BufferedImage[2];
		
		for(damageFrames = 0; damageFrames < 2; damageFrames++) {
			playerDamage[damageFrames] = Game.spritesheet.getSprite(64 + (damageFrames * 16),32,16,16);//Esquerda
		}
		for(damageFrames = 0; damageFrames < 2; damageFrames++) {
			playerDamage[damageFrames] = Game.spritesheet.getSprite(64 + (damageFrames * 16),48,16,16);//Direita
		}
	
		for(int i = 0; i < 4; i++) {
			idleRight[i] = Game.spritesheet.getSprite(0 + (i * 16),48,16,16);
		}
		for(int i = 0; i < 4; i++) {
			idleLeft[i] = Game.spritesheet.getSprite(0 + (i * 16),32,16,16);
		
		}	

		for(int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(0 + (i * 16),0,16,16);
		}
		for(int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(0 + (i * 16),16,16,16);
		}
		for(int i = 0; i < 4; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(16 ,64 + (i * 16),16,16);
		}
		for(int i = 0; i < 4; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(0 ,64 + (i * 16),16,16);
		}
		
	}
        @Override
	public void tick() {
        if (jump) {
        	if (isJumping == false) {
        		jump = false;
        		isJumping = true;
        		jumpUp = true;
        	
        	}
        }
        
        if(isJumping == true) {
        	//if(jumpCur < jumpFrames) {
        		if (jumpUp) {
        			jumpCur+= 2;
        			
        		}else if (jumpDown) {
        			jumpCur-=2;
        			
        			if ( jumpCur <= 0) {
        				isJumping = false;
        				jumpDown = false;
        				jumpUp = false;
        			}
        			
        		}
        		z = jumpCur;
        		if(jumpCur >= jumpFrames) {
        			jumpUp = false;
        			jumpDown = true;
        			//System.out.println("Chegou na altura maxima");
        		}
        	//}
        }
        	
        moved = false;
        idle = true;
        isDamage = false;
        	
        if(right && World.isFree((int)(x + speed),this.getY(), z)) {
        	moved = true;
			dir = right_dir;
			x += speed;
		}
		else if (left && World.isFree((int)(x - speed),this.getY(), z)) {
			moved = true;
			dir = left_dir;
			x -= speed;
		}	
		if(up && World.isFree(this.getX(),(int) (y - speed),  z)) {
			moved = true;
			dir = up_dir;
			y -= speed;
		}
		else if (down && World.isFree(this.getX(),(int) (y + speed), z)) {
			moved = true;
            dir = down_dir;         
			y += speed;
		} 
		//if(!moved) { para deixar o player movimentando mesmo em idle
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			//}
		
		}
			
		
	
			
		checkCollisionLifePack();
		checkCollisionAmmo();
		checkCollisionGun();
		
		if(shoot && hasGun && ammo > 0) {
			ammo--;
			 // Criar as balas e atirar!!
			 shoot = false;
			 int dx = 0;
			 int dy = 0;
			 int px = 0;
			 int py = 7;
			 if(dir == right_dir) {
				  dx = 1;
				  px = 12;
			 }else if(dir == left_dir) {
				  dx = -1;
				  px = -4;
			 }else if(dir == up_dir) {
				  dy = -1;
				  px = 9;
				  py = -4;
			 }else if(dir == down_dir) {
				  dy = 1;
				  px = 4;
				  py = 12;
			 
			 }
			 Shoot shoot = new Shoot(this.getX() + px,this.getY() + py, 3, 3, null, dx, dy);
			 Game.bullets.add(shoot);
		}
		
		if(mouseShoot) {
			//System.out.println("Mouse Atirando!!!!");
			 mouseShoot = false;
			 double angle = Math.atan2(mouseY - (this.getY()+8 - Camera.y),mouseX - (this.getX()+8 - Camera.x));
			 
			 if(hasGun && ammo > 0) {
			 ammo--;
			
			
			 double dx = Math.cos(angle);
			 double dy = Math.sin(angle);
			 int px = 0;
			 int py = 7;
			 
			
			 Shoot shoot = new Shoot(this.getX() + px,this.getY() + py, 3, 3, null, dx, dy);
			 Game.bullets.add(shoot);
			 }
		}
		
		if(life <= 0) {
			//Game Over
			life = 0;
			Game.gameState = "GAME_OVER";
			
		}
		updateCamera();
	
	}
        
        public void updateCamera() {
        	Camera.x = Camera.clamp(this.getX() - (Game.WIDTH /2 ),0 ,World.WIDTH * 16 - Game.WIDTH);
    		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT /2 ),0 ,World.HEIGHT * 16 - Game.HEIGHT);
        }
        
        public void checkCollisionGun() {
        	for(int  i = 0; i< Game.entities.size(); i++) {
      		  Entity atual = Game.entities.get(i);
      		  if(atual instanceof Weapon) {
      			  if(Entity.isColidding(this, atual)) {
      				hasGun = true;
      				if (hasGun) {
      					ammo = 10;
      				}
      				//System.out.println("Arma na Mão");
      				
      				 Game.entities.remove(atual);
      			  }
      		  }
        	}
        }
        public void checkCollisionAmmo() {
        	for(int  i = 0; i< Game.entities.size(); i++) {
      		  Entity atual = Game.entities.get(i);
      		  if(atual instanceof Bullet) {
      			  if(Entity.isColidding(this, atual)) {
      				 ammo+=10;
      				 //System.out.println("Munição Atual " + ammo);
      				 Game.entities.remove(atual);
      			  }
      		  }
        	}
        }
		
        public void checkCollisionLifePack(){
    	  for(int  i = 0; i< Game.entities.size(); i++) {
    		  Entity atual = Game.entities.get(i);
    		  if(atual instanceof LifePack) {
    			  if(Entity.isColidding(this, atual)) {
    				  life+=10;
    				  if(life > 100)
    					 life = 100;
    				  Game.entities.remove(atual);
    			  }
    		  }
    	  }
      }
       
     

        @Override
	public void render(Graphics g) {
        if(!isDamage){
	        if(moved == false ){ // Voltar corrigindo idle esquerda e idle direita!!!
	        	
	    		if((idle == true) && (dir == right_dir)) {
		    		g.drawImage(idleRight[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
		    		if(hasGun) {
		    			g.drawImage(Entity.GUN_RIGHT, this.getX()+9 - Camera.x,this.getY() - Camera.y - z, null);
		    		}	
		        	
		        }else if((idle == true) && (dir == right_dir)) {
		    		g.drawImage(idleRight[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
		    			
		        }else if( (idle == true) && (dir == right_dir)) {
		    		g.drawImage(idleRight[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
		        	
		        }else if((idle == true) && !(dir == right_dir)) {
	        		g.drawImage(idleLeft[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
		        	if(hasGun) {
		    			g.drawImage(Entity.GUN_LEFT, this.getX()-9 - Camera.x,this.getY() - Camera.y - z, null);
		    			
		    		}	
		        	
	
		        }else if( dir == up_dir) {
		    			g.drawImage(idleLeft[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
		    			
		        }else if( dir == down_dir) {
		    			g.drawImage(idleLeft[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
		    			
		        }
	
	       }else if(dir == right_dir) {// Lados em Movimento
				
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				if(hasGun) {
					//Desenhar arma direita
					g.drawImage(Entity.GUN_RIGHT, this.getX()+ 9 - Camera.x,this.getY() - Camera.y- z, null);
				}
	       }else if(dir == left_dir) {
				
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				if(hasGun) {
					//Desenhar arma esquerda
					g.drawImage(Entity.GUN_LEFT, this.getX() - 9 - Camera.x,this.getY() - Camera.y- z, null);
				}
	       }else if(dir == up_dir) {
				
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY()  - Camera.y - z, null);
				if(hasGun) {
					//Desenhar arma cima
					g.drawImage(Entity.GUN_UP, this.getX() + 2 - Camera.x,this.getY() - 6 - Camera.y - z, null);
				}
	       }else if(dir == down_dir) {
				
				g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				if(hasGun) {
					//Desenhar arma baixo
					g.drawImage(Entity.GUN_DOWN, this.getX() - 2 - Camera.x,this.getY() + 6 - Camera.y - z, null);
				}
	       }  
	    
        }else if((isDamage == true) && (dir == right_dir))  {
        	
        	g.drawImage(playerDamage[damageIndex], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
        	
        }else if((isDamage == true) && (dir == left_dir)) {
        	g.drawImage(playerDamage[damageIndex], this.getX() - Camera.x, this.getY() - Camera.y - z , null);
        	
        }else if((isDamage == true) && (dir == up_dir)) {
        	
       
        	g.drawImage(playerDamage[damageIndex], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
        	
        }else if((isDamage == true) && (dir == down_dir)) {
        	
        	g.drawImage(playerDamage[damageIndex], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
        }
        if (isJumping) {
        	g.setColor(Color.black);
        	g.fillOval(this.getX() - Camera.x + 2, this.getY() - Camera.y + 12, 10 ,5);
        }
    }
}
