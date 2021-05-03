package com.mvjstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {
	
	public String[] options = {"novo jogo", "carregar jogo", "sair"};
	
	public int currentOption = 0;
	public int maxOptions = options.length - 1;
	
	public boolean up, down, enter;
	
	public boolean pause = false;
	
	public void tick() {
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0) 
				currentOption = maxOptions;
		}
		if (down) {
			down = false;
			currentOption++;
			if(currentOption > maxOptions)
				currentOption = 0;
		}
		if (enter) {
			enter = false;
			if(options[currentOption] == "novo jogo" || options[currentOption] == "continuar") {
				Game.gameState = "NORMAL";
				pause = false;
			}else if(options[currentOption] == "sair") {
				System.exit(1);
			}
		}
	}
	public void render(Graphics g) {
		
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,100,100));
		//g.setColor(Color.black);
		g2.fillRect(0, 0, Game.WIDTH *Game.SCALE , Game.HEIGHT * Game.SCALE);
		g.setColor(Color.red);
		g.setFont(new Font("comics", Font.BOLD,36));
		g.drawString("MAD GAME", (Game.WIDTH *Game.SCALE) /2 - 100, (Game.HEIGHT * Game.SCALE)/2 - 140);
		
		
		//opções de menu
		
		g.setColor(Color.white);
		g.setFont(new Font("comics", Font.BOLD,24));
		
		if(pause == false) {
			g.drawString("Novo jogo", (Game.WIDTH *Game.SCALE) /2 - 60, 200);
		}
		else {
			g.drawString("CONTINUE", (Game.WIDTH *Game.SCALE) /2 - 60, 200);
		}
		
		g.drawString("Carregar Jogo", (Game.WIDTH *Game.SCALE) /2 - 80, 240);
		g.drawString("Sair", (Game.WIDTH *Game.SCALE) /2 - 28, 280);
		
		if (options[currentOption] == "novo jogo") {
			g.drawString(">", (Game.WIDTH *Game.SCALE) /2 - 100, 200);
		}else if (options[currentOption] == "carregar jogo") {
			g.drawString(">", (Game.WIDTH *Game.SCALE) /2 - 120, 240);
		}else if (options[currentOption] == "sair") {
			g.drawString(">", (Game.WIDTH *Game.SCALE) /2 - 60, 280);
		}
		
	}
	
}
