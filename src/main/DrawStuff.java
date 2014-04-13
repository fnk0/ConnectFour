package main;
//======================================================================
// CS I : #  Program 10
// # Connect 4
// Semester : # (Fall 2013)
//
// # Marcus Andreo Gabilheri
// # 002
//
// Description: This class takes care of Drawing the circles.
//======================================================================

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class DrawStuff extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private int type;
	
	public DrawStuff(int t) {
		this.setBackground(Color.BLUE);
		type = t;
	}
	
	public void setType(int t) {
		type = t;
	}
	
	public int getType() {
		return type;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int x = getWidth();
		int h = getHeight();
		
		if(type == 0) {
			g.setColor(Color.lightGray);
			g.fillOval(0, 0, x, h);
		} else if(type == 1) {
			g.setColor(Color.YELLOW);
			g.fillOval(0, 0, x, h);
		} else if(type == 2) {
			g.setColor(Color.RED);
			g.fillOval(0, 0, x, h);
		}
	}
}
