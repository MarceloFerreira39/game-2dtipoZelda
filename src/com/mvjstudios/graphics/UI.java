package com.mvjstudios.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.mvjstudios.main.Game;

public class UI {
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(20, 8, 50, 8);
		g.setColor(Color.green);
		g.fillRect(20, 8, (int) ((Game.player.life/Game.player.maxLife)*50), 8);
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.BOLD,9));
		g.drawString((int)Game.player.life +  "  " /* (int)Player.maxLife*/,38,15);
	}
}
