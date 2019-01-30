import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javafx.scene.control.Slider;

import javax.swing.*;
import javax.swing.Timer;

/*
 * CS 6366.0U1: Computer Graphics 
 * Project3: Tetris
 * 
 * Goal: This is a superate UI window for game manipulation.
 * 		 The player can change score and speed multipliers, rows to complete the level, square size,
 * 		 and add custom blocks to the game.
 * 
 * By,
 * Michael Del Rosario (mxd120830)
 */

public class TetrisModifier extends Frame
{
	//Create Variables
	int buttonSize = 40;
		
	//Create adjustable factors
	int S_F = 0;//Scoring Factor
	int R_R = 0;//Rows Required
	float SP_F = 0.0F;//Speed Factor
	int squareSize = 0;
	int cellLength = 0;
	int cellHeight = 0;
	int cellHighlight = 0;
	int i = 0;
	
	//Create a cell Grid for custom block insertion
	Color color = Color.red;
	boolean[][] grid = new boolean[3][3];
	ArrayList<boolean[][]> newBlockList = new ArrayList<boolean[][]>();
	ArrayList<Color> newBlockColor = new ArrayList<Color>();
	
	int[] blockX = new int[3];
	int[] blockY = new int[3];
	
	TetrisModifier(int scoreF, int rowR, float speedF, int SQ, int C_L, int C_H)
	{
		super("Tetris_Modifier");
		
		addWindowListener(new WindowAdapter()
		   {public void windowClosing(WindowEvent e){System.exit(0);}});
		
		//Set Factor variables
		S_F = scoreF;//Scoring Factor
		R_R = rowR;//Rows Required
		SP_F = speedF;//Speed Factor
		squareSize = SQ;
		cellLength = C_L;
		cellHeight = C_H;
		
		
		//setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(500, 900);
		setVisible(true);
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
		//Empty grid
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				grid[i][j] = false;
			}
		}
		
		addMouseMotionListener(new MouseAdapter()
		{
			
			public void mouseMoved(MouseEvent evt)
			{
				int xA = evt.getX(), yA = evt.getY();
				
				//If mouse hovers on S_F button change mouse cursor
	    	  	if((xA >= 10 && xA <= 50) && (yA >= 50 && yA <= 90) || (xA >= getSize().width-(10+buttonSize) && xA <= getSize().width-(10)) && (yA >= 50 && yA <= 90))
	    	  	{
	    	  		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	    	  	}
	    	  	else
	    	  	{
	    	  		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	    	  	}
	    	  	
	    	  	//If mouse hovers on R_R button change mouse cursor
	    	  	if((xA >= 10 && xA <= 50) && (yA >= 100 && yA <= 140) || (xA >= getSize().width-(10+buttonSize) && xA <= getSize().width-(10)) && (yA >= 100 && yA <= 140))
	    	  	{
	    	  		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	    	  	}
	    	  	else
	    	  	{
	    	  		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	    	  	}
	    	  	
	    	  	//If mouse hovers on SP_F button change mouse cursor
	    	  	if((xA >= 10 && xA <= 50) && (yA >= 150 && yA <= 190) || (xA >= getSize().width-(10+buttonSize) && xA <= getSize().width-(10)) && (yA >= 150 && yA <= 190))
	    	  	{
	    	  		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	    	  	}
	    	  	else
	    	  	{
	    	  		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	    	  	}
	    	  	
	    	  	
	    	  	
	    	  	//If mouse hovers on square Size button change mouse cursor
	    	  	if((xA >= 10 && xA <= 50) && (yA >= 300 && yA <= 340) || (xA >= getSize().width-(10+buttonSize) && xA <= getSize().width-(10)) && (yA >= 300 && yA <= 340))
	    	  	{
	    	  		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	    	  	}
	    	  	else
	    	  	{
	    	  		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	    	  	}
	    	  	
			}
		});
		
		//Add functionality that quits the game on click
		addMouseListener(new MouseAdapter()
	      {  
			
			
			public void mousePressed(MouseEvent evt)
	         {  
				
				
				int xA = evt.getX(), yA = evt.getY();
				
				//If mouse hovers on S_F button change mouse cursor
	    	  	if((xA >= 10 && xA <= 50) && (yA >= 50 && yA <= 90))
	    	  	{
	    	  		S_F--;
	    	  		if(S_F < 1)
	    	  			S_F = 1;
	    	  	}
	    	  	else if((xA >= getSize().width-(10+buttonSize) && xA <= getSize().width-(10)) && (yA >= 50 && yA <= 90))
	    	  	{
	    	  		S_F++;
	    	  		if(S_F > 10)
	    	  			S_F = 10;

	    	  	}
	    	  	
	    	  	//If mouse hovers on R_R button change mouse cursor
	    	  	if((xA >= 10 && xA <= 50) && (yA >= 100 && yA <= 140))
	    	  	{
	    	  		R_R--;
	    	  		if(R_R < 20)
	    	  			R_R = 20;
	    	  	}
	    	  	else if((xA >= getSize().width-(10+buttonSize) && xA <= getSize().width-(10)) && (yA >= 100 && yA <= 140))
	    	  	{
	    	  		R_R++;
	    	  		if(R_R > 50)
	    	  			R_R = 50;
	    	  	}
	    	  	
	    	  	//If mouse hovers on SP_F button change mouse cursor
	    	  	if((xA >= 10 && xA <= 50) && (yA >= 150 && yA <= 190))
	    	  	{
	    	  		SP_F -=0.1F;
	    	  		if(SP_F < 0.1F)
	    	  			SP_F = 0.1F;
	    	  	}
	    	  	else if((xA >= getSize().width-(10+buttonSize) && xA <= getSize().width-(10)) && (yA >= 150 && yA <= 190))
	    	  	{
	    	  		SP_F +=0.1F;
	    	  		if(SP_F > 1.0F)
	    	  			SP_F = 1.0F;
	    	  	}
	    	  	
	    	  	
	    	  	//If mouse hovers on cells wide button change mouse cursor
	    	  	if((xA >= 10 && xA <= 50) && (yA >= 300 && yA <= 340))
	    	  	{
	    	  		squareSize = squareSize - 5;
	    	  		if(squareSize < 20)
	    	  			squareSize = 20;
	    	  	}
	    	  	else if((xA >= getSize().width-(10+buttonSize) && xA <= getSize().width-(10)) && (yA >= 300 && yA <= 340))
	    	  	{
	    	  		squareSize = squareSize + 5;
	    	  		if(squareSize > 45)
	    	  			squareSize = 45;
	    	  	}
	    	  	
	    	  	
	    	  	//Check for click on block grid
	    	  	clickGrid(xA,yA);
	    	  	
	    	  	//Check if hovering over color and add block button
	    	  	//Red
	    	  	if((xA >= getSize().width-325 && xA <= getSize().width-325+buttonSize) && (yA >= 350 && yA <= 350+buttonSize))
	    	  	{
	    	  		int redTemp = color.getRed() - 16;
	    	  		if(redTemp < 0)
	    	  			redTemp = 0;
	    	  		
	    	  		color = new Color(redTemp,color.getGreen(),color.getBlue());
	    	  		//System.out.println(color.toString());
	    	  	}
	    	  	else if((xA >= getSize().width-225 && xA <= getSize().width-225+buttonSize) && (yA >= 350 && yA <= 350+buttonSize))
	    	  	{
	    	  		int redTemp = color.getRed() + 16;
	    	  		if(redTemp > 255)
	    	  			redTemp = 255;
	    	  		
	    	  		color = new Color(redTemp,color.getGreen(),color.getBlue());
	    	  	}
	    	  	//Green
	    	  	if((xA >= getSize().width-325 && xA <= getSize().width-325+buttonSize) && (yA >= 425 && yA <= 425+buttonSize))
	    	  	{
	    	  		int greenTemp = color.getGreen() - 16;
	    	  		if(greenTemp < 0)
	    	  			greenTemp = 0;
	    	  		
	    	  		color = new Color(color.getRed(),greenTemp,color.getBlue());
	    	  		//System.out.println(color.toString());
	    	  	}
	    	  	else if((xA >= getSize().width-225 && xA <= getSize().width-225+buttonSize) && (yA >= 425 && yA <= 425+buttonSize))
	    	  	{
	    	  		int greenTemp = color.getGreen() + 16;
	    	  		if(greenTemp > 255)
	    	  			greenTemp = 255;
	    	  		
	    	  		color = new Color(color.getRed(),greenTemp,color.getBlue());
	    	  	}
	    	  	//Blue
	    	  	if((xA >= getSize().width-325 && xA <= getSize().width-325+buttonSize) && (yA >= 500 && yA <= 500+buttonSize))
	    	  	{
	    	  		int blueTemp = color.getBlue() - 16;
	    	  		if(blueTemp < 0)
	    	  			blueTemp = 0;
	    	  		
	    	  		color = new Color(color.getRed(),color.getGreen(),blueTemp);
	    	  		//System.out.println(color.toString());
	    	  	}
	    	  	else if((xA >= getSize().width-225 && xA <= getSize().width-225+buttonSize) && (yA >= 500 && yA <= 500+buttonSize))
	    	  	{
	    	  		int blueTemp = color.getBlue() + 16;
	    	  		if(blueTemp > 255)
	    	  			blueTemp = 255;
	    	  		
	    	  		color = new Color(color.getRed(),color.getGreen(),blueTemp);
	    	  	}
	    	  	
	    	  	//Add block
	    	  	if((xA >= getSize().width-175 && xA <= getSize().width-175+100) && (yA >= 350 && yA <= 400) && cellHighlight > 0)
	    	  	{
	    	  		insertNewBlock();
	    	  		//System.out.println("Block added");
	    	  	}
	    	  	
	    	  	
	    	  	
	    	  	repaint();
	         }
			
			
	      });
		
		
		
	}
	
	TetrisModifier()
	{
		
		
		super("Tetris_Modifier");
		
		
		//setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(500, 900);
		setVisible(true);
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		JLabel test = new JLabel("HEY");
		JSlider scoreSlider = new JSlider(JSlider.HORIZONTAL,0,10,5);
		
		
		//add(new JLabel("HEY"));
		
	}
	
	public void paint(Graphics g)
	{
		//Draw Scoring Factor
		g.drawRect(10, 50, buttonSize, buttonSize);
		g.drawString("-",30, 75);
		g.drawString("+", getSize().width-30, 75);
		g.drawString("Score Factor (1-10): " + S_F, getSize().width/2, 75);
		g.drawRect(getSize().width-(10+buttonSize), 50, buttonSize, buttonSize);
		
		
		//Draw Rows Required Factor
		g.drawRect(10, 100, buttonSize, buttonSize);
		g.drawString("-",30, 125);
		g.drawString("+", getSize().width-30, 125);
		g.drawString("Line Goal (20-50): " + R_R, getSize().width/2, 125);
		g.drawRect(getSize().width-(10+buttonSize), 100, buttonSize, buttonSize);
		
		//Draw Speed Factor Factor
		g.drawRect(10, 150, buttonSize, buttonSize);
		g.drawString("-",30, 175);
		g.drawString("+", getSize().width-30, 175);
		g.drawString("Speed Factor (0.1-1.0): " + SP_F, getSize().width/2, 175);
		g.drawRect(getSize().width-(10+buttonSize), 150, buttonSize, buttonSize);
		
			
		//Draw Square Size Factor
		g.drawRect(10, 300, buttonSize, buttonSize);
		g.drawString("-",30, 325);
		g.drawString("+", getSize().width-30, 325);
		g.drawString("Square Size (20-45): " + squareSize, getSize().width/2, 325);
		g.drawRect(getSize().width-(10+buttonSize), 300, buttonSize, buttonSize);
		
		//Draw Custom square grid
		int iterateX = 0;
		int iterateY = 0;
		for(int length = 0; length < 3;length++)
		{
			for(int height = 0; height < 3;height++)
			{
				
				
				//Fill square if it was clicked
				if(grid[length][height])
				{
					g.setColor(color);
					g.fillRect(10+iterateX, 350+iterateY, 40, 40);
					g.setColor(Color.black);
				}
				
				//Draw square
				g.drawRect(10+iterateX, 350+iterateY, 40, 40);
				
				iterateY+=40;
			}
			
			iterateX += 40;
			iterateY = 0;
		}
		
		//Draw Color Buttons
		g.drawRect(getSize().width-325, 350, buttonSize, buttonSize);
		g.drawString("-",getSize().width-300, 375);
		g.drawString("Red: " + color.getRed(),getSize().width-275, 375);
		g.drawString("+",getSize().width-200, 375);
		g.drawRect(getSize().width-225, 350, buttonSize, buttonSize);
		
		g.drawRect(getSize().width-325, 425, buttonSize, buttonSize);
		g.drawString("-",getSize().width-300, 450);
		g.drawString("Green: " + color.getGreen(),getSize().width-275, 450);
		g.drawString("+",getSize().width-200, 450);
		g.drawRect(getSize().width-225, 425, buttonSize, buttonSize);
		
		g.drawRect(getSize().width-325, 500, buttonSize, buttonSize);
		g.drawString("-",getSize().width-300, 525);
		g.drawString("Blue: " + color.getBlue(),getSize().width-275, 525);
		g.drawString("+",getSize().width-200, 525);
		g.drawRect(getSize().width-225, 500, buttonSize, buttonSize);
		
		//Draw add block button
		g.drawRect(getSize().width-175, 350, 100, 50);
		g.drawString("ADD",getSize().width-150, 375);
		
		
	}
	
	//This function checks to see if user clicked inside the 3X3 grid
	public void clickGrid(int cX, int cY)
	{
		int iterateX = 10;
		int iterateY = 350;
		
		for(int length = 0; length < 3;length++)
		{
			for(int height = 0; height < 3;height++)
			{
				if((cX >= iterateX && cX <= iterateX + 40) && (cY >= iterateY && cY <= iterateY + 40) && cellHighlight < 3)
				{
					grid[length][height] = true;
					cellHighlight++;
				}
				else if((cX >= iterateX && cX <= iterateX + 40) && (cY >= iterateY && cY <= iterateY + 40) && grid[length][height])
				{
					grid[length][height] = false;
					cellHighlight--;
				}
				iterateY+=40;
			}
			
			iterateX += 40;
			iterateY = 350;
		}
	}
	
	//This function will add a new block to both the newBlockList and ColorList, so main Tetris file will
	//retrieve both list as CustomBlockList and cusomtColorList
	public void insertNewBlock()
	{
		boolean[][] tempGrid = new boolean[3][3];
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(grid[i][j])
					tempGrid[i][j] = true;
				else
					tempGrid[i][j] = false;
			}
		}
		
		//add block grid to list
		newBlockList.add(tempGrid);
		
		//Test see new grid
//		for(int i = 0; i < 3; i++)
//		{
//			for(int j = 0; j < 3; j++)
//			{
//				System.out.println(newBlockList.get(newBlockList.size()-1)[i][j]);
//			}
//		}
		
		//add color to list
		newBlockColor.add(color);
		
		//Clear grid
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				grid[i][j] = false;
			}
		}
		
		//TEST: See new block grid
//		for(int i = 0; i < 3; i++)
//		{
//			for(int j = 0; j < 3; j++)
//			{
//				System.out.println(newBlockList.get(newBlockList.size()-1)[i][j]);
//			}
//		}
		
		//Reset counter
		cellHighlight = 0;
	}
	
	public int getS_F()
	{
		return S_F;
	}
	
	public int getR_R()
	{
		return R_R;
	}
	
	public float getSP_F()
	{
		return SP_F;
	}
	
	public int getsquareSize()
	{
		return squareSize;
	}
	
	public int getcellLength()
	{
		return cellLength;
	}
	
	public int getcellHeight()
	{
		return cellHeight;
	}
	
	public ArrayList<boolean[][]> getBlockList()
	{
		
		//Test see new grid
//		if(newBlockList.size() > 0)
//		{
//			for(int i = 0; i < 3; i++)
//			{
//				for(int j = 0; j < 3; j++)
//				{
//					System.out.println(newBlockList.get(newBlockList.size()-1)[i][j]);
//				}
//			}
//		}
		return newBlockList;
	}
	
	public ArrayList<Color> getColorList()
	{
		return newBlockColor;
	}

}
