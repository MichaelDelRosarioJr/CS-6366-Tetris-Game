import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.Timer;
/*
 * CS 6366.0U1: Computer Graphics 
 * Project3: Tetris
 * 
 * Goal: To further complete the game with line completion, scoring, level progression, and game termination.
 * 			There is an accompany interface to adjust various gameplay values
 * 
 * By,
 * Michael Del Rosario (mxd120830)
 */



public class Exercise_Tetris extends Frame
{

	public static void main(String[] args) 
	{
		new Exercise_Tetris();
		
	}

	
	Exercise_Tetris()
	{
		//super("Tetris_Project3");
//		addWindowListener(new WindowAdapter()
//		   {public void windowClosing(WindowEvent e){System.exit(0);}});
//		setSize(700, 900);
//		add("Center", new tetrisStartUp());
//		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//		show();
		
		//Create Start Up interface
		tetrisStartUp TU = new tetrisStartUp();
		TU.setLocation(0,0);
		TU.setSize(700,900);
		TU.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		TU.setVisible(true);
		
		
				
	}
}

class tetrisStartUp extends Frame
{
	int playAreaWidth = 10;
	int playAreaHeight = 20;
	int buttonSize = 40;
	
	tetris T;
	
	tetrisStartUp()
	{
		super("Tetris");
		
		addWindowListener(new WindowAdapter()
		   {public void windowClosing(WindowEvent e){System.exit(0);}});
		
		//Add functionality that quits the game on click
				addMouseListener(new MouseAdapter()
			      {  
					
					
					public void mousePressed(MouseEvent evt)
			         {
						int xA = evt.getX(), yA = evt.getY();
						
						//Width
			    	  	if((xA >= 100 && xA <= 100+buttonSize) && (yA >= 200 && yA <= 200+buttonSize))
			    	  	{
			    	  		playAreaWidth--;
			    	  		if(playAreaWidth < 8)
			    	  			playAreaWidth = 8;
			    	  	}
			    	  	else if((xA >= 300 && xA <= 300+buttonSize) && (yA >= 200 && yA <= 200+buttonSize))
			    	  	{
			    	  		playAreaWidth++;
			    	  		if(playAreaWidth > 12)
			    	  			playAreaWidth = 12;

			    	  	}
			    	  	
			    	  	//Height
			    	  	if((xA >= 100 && xA <= 100+buttonSize) && (yA >= 400 && yA <= 400+buttonSize))
			    	  	{
			    	  		playAreaHeight--;
			    	  		if(playAreaHeight < 18)
			    	  			playAreaHeight = 18;
			    	  	}
			    	  	else if((xA >= 300 && xA <= 300+buttonSize) && (yA >= 400 && yA <= 400+buttonSize))
			    	  	{
			    	  		playAreaHeight++;
			    	  		if(playAreaHeight > 21)
			    	  			playAreaHeight = 21;

			    	  	}
			    	  	
			    	  	//Play
			    	  	if((xA >= 100 && xA <= 200) && (yA >= 600 && yA <= 700))
			    	  	{
			    	  		//System.out.println("PLAY");
			    	  		
			    	  		//Create tetris game
			    			addWindowListener(new WindowAdapter()
			    			   {public void windowClosing(WindowEvent e){System.exit(0);}});
			    			setSize(700, 900);
			    			add("Center",new tetris(playAreaWidth,playAreaHeight));
			    			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			    			show();
			    			
			    	  	}
			    	  	
			    	  	repaint();
			         }
			      }
				);
	}
	
	public void paint(Graphics g)
	{
		g.drawString("Please input dimension of Tetris game area", 100, 100);
		
		//Area Width
		g.drawRect(100, 200, buttonSize, buttonSize);
		g.drawString("-", 125, 220);
		g.drawString("Area Width(8-12): " + playAreaWidth, 150, 220);
		g.drawString("+", 325, 220);
		g.drawRect(300, 200, buttonSize, buttonSize);
		
		//Draw Height
		g.drawRect(100, 400, buttonSize, buttonSize);
		g.drawString("-", 125, 420);
		g.drawString("Area Height (18-21): " + playAreaHeight, 150, 420);
		g.drawString("+", 325, 420);
		g.drawRect(300, 400, buttonSize, buttonSize);
		
		//Draw play button
		g.drawRect(100, 600, 100, 100);
		g.drawString("PLAY", 150, 650);
	}
	
}

//This class starts the game and maintains the gameplay
class tetris extends Canvas implements ActionListener
{
	//Define variables
	//Create variables for the widgets
	int scoringFactor = 1;
	int nextLevelReq = 20;
	float speedFactor = 1.0F;
	
	//Create a RNG
	Random rand = new Random();
	
	//Create Timer
	Timer time = new Timer(100, this);
	
	//Create a speed that will prevent the block from falling until delay hits the speed limit
	float speed = 1.0F;
	float speedLimit = 10.0F;
	
	//Create a block list
	Block blockList;
	previewBlock nextBlock;
	ArrayList<boolean[][]> customBlockList = new ArrayList<boolean[][]>();
	ArrayList<Color> customBlockColor = new ArrayList<Color>();
	boolean newGame = true;//When the program starts
	boolean gameOver = false;//When the game ends
	boolean activeBlock = false;
	boolean customBlock = false;
	
	//A square for each block
	int squareSize = 20;
	int squareSizeMin = 20;
	
	float x0, y0, rWidth = 10.0F, rHeight = 7.5F, pixelSize, radius = 0.0F,boxHeight=10*squareSize;
	boolean onPause = false;//Add a boolean to pause the game
	int maxX,maxY,centerX, centerY;
	
	//Create class object for game customization
	TetrisModifier TM;
	
	//Create a grid 2D array for collision detection.
	//This grid stores each index as one square.
	//If grid[blockX][blockY] is true, then a square occupies that grid
	boolean[][] grid;
	//Set a grid to remember the color of a square on a grid
	Color[][] colorGrid;
	
	//Play area's dimension and position
	int cellLength = 10;
	int cellHeight = 20;
	int playAreaX = 10;
	int playAreaY = 10;
	int playAreaWidth;
	int playAreaHeight;
	
	//Next block's dimension and position
	int nextBlockX = 11*squareSize-10;
	int nextBlockY = 10;
	int nextBlockWidth = 4*squareSize;
	int nextBlockHeight = 4*squareSize;
	
	//PLayer Information (level, lines, and score) position
	
	int levelX = 11*squareSize-10;
	int levelY = 100+3*squareSize;
	int levelNum = 1;
	
	int lineX = 11*squareSize-10;
	int lineY = 130+3*squareSize;
	int linesCompleted = 0;
	
	int scoreValue = 0;
	int scoreX = 11*squareSize-10;
	int scoreY = 160+3*squareSize;

	//Quit Button dimension, position, and text
	//Box
	int quitBoxX = 11*squareSize-10;
	int quitBoxY = 700;
	int quitBoxWidth = 3*squareSize;
	int quitBoxHeight = 2*squareSize;
	
	//Pause Screen Dimensions
	int pauseBoxX;
	int pauseBoxY;
	int pauseBoxWidth = 3*squareSize;
	int pauseBoxHeight = 2*squareSize;

	
	tetris(int width, int height)
	{
		playAreaWidth = width*squareSize;
		playAreaHeight = height*squareSize;
		cellLength = width;
		cellHeight = height;
		grid = new boolean[cellLength][cellHeight];
		colorGrid = new Color[cellLength][cellHeight];
		
		//Empty grid
		for(int i = 0;i < cellLength;i++)
		{
			for(int j = 0;j < cellHeight;j++)
			{
				grid[i][j] = false;
			}
		}
		
		
		addMouseMotionListener(new MouseAdapter()
		{
			
			public void mouseMoved(MouseEvent evt)
			{
				int xA = evt.getX(), yA = evt.getY();
				
	    	  	
	    	  	//If mouse hovers on quit button change mouse cursor
	    	  	if((xA>=quitBoxX+(cellLength-10)*squareSize && xA<=quitBoxX+quitBoxWidth+(cellLength-10)*squareSize) && (yA>=quitBoxY && yA<=quitBoxY+quitBoxHeight))
	    	  	{
	    	  		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	    	  	}
	    	  	else
	    	  	{
	    	  		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	    	  	}
	    	  	
	    	  	//If mouse hovers on play screen change the pause bool to true
	    	  	if((xA>=playAreaX && xA<=playAreaX+playAreaWidth) && (yA>=playAreaY && yA<=playAreaY+playAreaHeight) && !gameOver)
	    	  	{
	    	  		//If game was not paused, pause and repaint
	    	  		if(!onPause)
	    	  		{
	    	  			onPause = true;
	    	  			repaint();
	    	  		}
	    	  	}
	    	  	else
	    	  	{
	    	  		//If game was paused, unpause and repaint
	    	  		if(onPause)
	    	  		{
	    	  			onPause = false;
	    	  			repaint();
	    	  		}
	    	  			
	    	  	}
	    	  	
	    	  	
	    	  	
	    	  	
	    	  	
			}
		});
		
		//Add functionality that quits the game on click
		addMouseListener(new MouseAdapter()
	      {  
			
			
			
			public void mousePressed(MouseEvent evt)
	         {  
				//setSize(800,1000);
				
	    	  	int xA = evt.getX(), yA = evt.getY();
	    	  	
	    	  	//If mouse click on quit button end program
	    	  	if((xA>=quitBoxX+(cellLength-10)*squareSize && xA<=quitBoxX+quitBoxWidth+(cellLength-10)*squareSize) && (yA>=quitBoxY && yA<=quitBoxY+quitBoxHeight))
	    	  	{
	    	  		System.exit(0);
	    	  	}
	    	  	else//If not, then determine the mouse press and move the block
	    	  	{
	    	  		
	    	  		
	    	  		if(evt.getButton()==MouseEvent.BUTTON1 && !onPause && !gameOver)
	    	  		{
	    	  			if(!blockList.checkSideCollision(grid,true))//If there is no left collision
	    	  			{
	    	  				//blockList.get(blockList.size()-1).X -= squareSize;
	    	  				blockList.moveLeft();
	    	  			}
	    	  			
	    	  		}
	    	  		else if(evt.getButton()==MouseEvent.BUTTON3 && !onPause && !gameOver)
	    	  		{
	    	  			//If there is no right collision
	    	  			if(!blockList.checkSideCollision(grid,false))
	    	  			{
	    	  				//blockList.get(blockList.size()-1).X += squareSize;
	    	  				blockList.moveRight();
	    	  			}
	    	  		}
	    	  		
	    	  		//If mouse clicked on the block position on pause
	    	  		if(evt.getButton()==MouseEvent.BUTTON1 && onPause)
	    	  		{
	    	  			//Call the block's clickedBlock function and see if the block needs to be replaced
	    	  			if(blockList.clickedBlock(xA, yA))
	    	  			{
	    	  				replace();
	    	  			}
	    	  		}
	    	  	}
	    	  	repaint();
	         }
	      });
		
		//Wheel Mouse Events
		addMouseWheelListener(new MouseAdapter(){
			public void mouseWheelMoved(MouseWheelEvent evt)
			{
				int move = evt.getWheelRotation();
				
				//If wheel moves upwards, rotate CW
				if(move < 0 && !gameOver && !onPause)
				{
					blockList.rotate(1,grid);
					//move =0;
				}
				else if(move > 0 && !gameOver && !onPause)//If the wheel moves downwards, rotate CCW
				{
					blockList.rotate(-1,grid);
					//move =0;
				}
				
				repaint();
			}
			
		});
		
		//Create a UI-Window for game modification
		TM = new TetrisModifier(scoringFactor,nextLevelReq,speedFactor,squareSize,cellLength,cellHeight);
		TM.setLocation(700,0);
		TM.setVisible(true);
		
	}
	
	
	public void paint(Graphics g)
	{	
		int rand;//For RNG
		
		//Draw play square
		g.setColor(Color.white);
		g.fillRect(playAreaX, playAreaY, playAreaWidth, playAreaHeight);
		g.setColor(Color.black);
		g.drawRect(playAreaX, playAreaY, playAreaWidth, playAreaHeight);
		
		//Draw next block square
		g.drawRect(nextBlockX+(cellLength-10)*squareSize,nextBlockY,nextBlockWidth, nextBlockHeight);
		
		//Draw Player info
		g.drawString("Level: " + levelNum, levelX+(cellLength-10)*squareSize, levelY);
		g.drawString("Lines Requried: " + (nextLevelReq - linesCompleted), lineX+(cellLength-10)*squareSize, lineY);
		g.drawString("Score: " + scoreValue, scoreX+(cellLength-10)*squareSize, scoreY);
		
		//Draw Quit
		g.drawRect(quitBoxX+(cellLength-10)*squareSize, quitBoxY, quitBoxWidth, quitBoxHeight);
		g.drawString("QUIT", Math.round(quitBoxX+quitBoxWidth/2)+(cellLength-10)*squareSize, Math.round(quitBoxY+quitBoxHeight/2));
		
		
		//TEST: Create a custom block for testing
		//boolean[][] cBlock = {{false,false,false},{true,false,true},{false,true,false}};
		
		//Draw blocks
		//If the program just started
		if(newGame)
		{
		

			//Randomly pick a new block
			rand = getRNG();
			blockList = new Block(Math.round(cellLength/2),0,squareSize,cellLength,cellHeight,getBlock(rand));
			
			
			
			//Store a random block
			nextBlock = new previewBlock(Math.round(nextBlockX+(cellLength-10)*squareSize+nextBlockWidth/2)-2*squareSize,Math.round(nextBlockY+nextBlockHeight/2)-squareSize,squareSize,getBlock(rand));
			
			//TEST: Use only 1 type of block
			//blockList = new Block(Math.round(cellLength/2),0,BlockType.REVERSEZ);
			//blockList = new Block(Math.round(cellLength/2),0,cBlock,Color.blue);
			//TEST: Use only 1 type of block
			//nextBlock = new previewBlock(Math.round(nextBlockX+nextBlockWidth/2)-2*squareSize,Math.round(nextBlockY+nextBlockHeight/2)-squareSize,BlockType.REVERSEZ);
			//nextBlock = new previewBlock(Math.round(nextBlockX+nextBlockWidth/2)-2*squareSize,Math.round(nextBlockY+nextBlockHeight/2)-squareSize,cBlock,Color.blue);
			//customBlock = true;
			
			activeBlock = true;
			newGame = false;
		}
		else
		{
			if(!activeBlock)//If there is no falling block
			{
				//Replace new block with next block and find next random block
				if(!customBlock)//Standard block
					blockList = new Block(Math.round(cellLength/2),0,squareSize,cellLength,cellHeight,nextBlock.type);
				else//Custom block
				{
					blockList = new Block(Math.round(cellLength/2),0,squareSize,cellLength,cellHeight,nextBlock.customGrid,nextBlock.color);
					//customBlock = false;
				}

				
				//Get the next block
				rand = getRNG();
				if(rand <= 6)//Get standard block
				{
					nextBlock = new previewBlock(Math.round(nextBlockX+(cellLength-10)*squareSize+nextBlockWidth/2)-2*squareSize,Math.round(nextBlockY+nextBlockHeight/2)-squareSize,squareSize,getBlock(rand));
					customBlock = false;
				}
				else//Get custom block from the list
				{
					//System.out.println("rand = " + rand);
					nextBlock = new previewBlock(Math.round(nextBlockX+(cellLength-10)*squareSize+nextBlockWidth/2)-2*squareSize,Math.round(nextBlockY+nextBlockHeight/2)-squareSize,squareSize,customBlockList.get(rand-7),customBlockColor.get(rand-7));
					customBlock = true;
				}

				
				//TEST: Use only 1 type of block
				//nextBlock = new previewBlock(Math.round(nextBlockX+nextBlockWidth/2)-2*squareSize,Math.round(nextBlockY+nextBlockHeight/2)-squareSize,BlockType.LINE);
				
				activeBlock = true;
			}
		}
		
		//Draw falling block
		blockList.drawBlock(g);
		
		//Draw all non-falling block
		for(int length = 0; length < cellLength; length++)
		{
			for(int height = 0; height < cellHeight; height++)
			{
				if(grid[length][height])
				{
					int posX,posY;
					posX = 10 + length*squareSize;
					posY = 10 + height*squareSize;
					
					//DrawSquare
					g.setColor(colorGrid[length][height]);
					g.fillRect(posX, posY, squareSize, squareSize);
					g.setColor(Color.black);
					g.drawRect(posX, posY, squareSize, squareSize);
				}
			}
		}
		
		//Draw the next block
		nextBlock.drawBlock(g);
		
		//Draw Pause when player hovers over play screen
		if(gameOver)
		{
			g.setColor(Color.black);
			pauseBoxX = Math.round((playAreaX+playAreaWidth/2));
			pauseBoxY = Math.round((playAreaY+playAreaHeight/2));
			g.drawRect(pauseBoxX, pauseBoxY, pauseBoxWidth, pauseBoxHeight);
			g.drawString("GAME OVER", Math.round((pauseBoxX+pauseBoxWidth/2)), Math.round((pauseBoxY+pauseBoxHeight/2)));
			g.setColor(Color.black);
			time.stop();//Stop timer
			return;
		}
		
		//Draw Pause when player hovers over play screen
		if(onPause)
		{
			g.setColor(Color.lightGray);
			pauseBoxX = Math.round((playAreaX+playAreaWidth/2));
			pauseBoxY = Math.round((playAreaY+playAreaHeight/2));
			g.drawRect(pauseBoxX, pauseBoxY, pauseBoxWidth, pauseBoxHeight);
			g.drawString("PAUSE", Math.round((pauseBoxX+pauseBoxWidth/2)), Math.round((pauseBoxY+pauseBoxHeight/2)));
			g.setColor(Color.black);
			time.stop();//Stop timer
		}
		else
		{
			time.start();//Start Timer again
		}
	}
	
	public int getRNG()
	{
		int random = rand.nextInt(7 + customBlockList.size());
		
		return random;
	}
	
	
	//This function randomly picks a block type and returns the type
	public BlockType getBlock(int random)
	{
		
		BlockType block;
		switch(random)
		{
			case 0:
				block = BlockType.BOX;
				break;
			case 1:
				block = BlockType.HILL;
				break;
			case 2:
				block = BlockType.L;
				break;
			case 3:
				block = BlockType.REVERSEL;
				break;
			case 4:
				block = BlockType.LINE;
				break;
			case 5:
				block = BlockType.Z;
				break;
			case 6:
				block = BlockType.REVERSEZ;
				break;
			default:
				block = BlockType.BOX;
				break;
		}
		
		return block;
	}


	//This function will activate after the timer ends and will lower the current block each trigger
	public void actionPerformed(ActionEvent e) 
	{	
		//Get modifiers from Tetris Modifier
		getModifiers();
		
		
		//This point variable is for testing when mouse is off screen
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		
	  	//If mouse is outside this game screen, pause the game
	  	if((mouse.x < getLocationOnScreen().x || mouse.x > getLocationOnScreen().x + getSize().width) 
	  			|| (mouse.y < getLocationOnScreen().y || mouse.y > getLocationOnScreen().y + getSize().height))
	  	{
	  		//If game was not paused, pause and repaint
	  		if(!onPause)
	  		{
	  			onPause = true;
	  		}
	  	}
	  	else
	  	{
	  		//If game was paused, unpause and repaint
	  		if(onPause)
	  		{
	  			onPause = false;
	  		}
	  			
	  	}
		
		//If there is a block collision
		if(blockList.checkCollision(grid))
		{
			//Get grid of the finished block
			grid = blockList.getGrid(grid);
			
			//Get the color grid
			colorGrid = blockList.getColorGrid(colorGrid);
			
			//Get a new block
			activeBlock=false;	
			
			//Check each row for a line
			checkRows();
			
			//If there the grid for the block spawn is true than the player is blocked and the game ends
			if(grid[cellLength/2][0])
			{
				//TEST: Print to system
				//System.out.println("GAME OVER");
				gameOver = true;

				//System.exit(0);
			}
		}
		else if(!gameOver)//Continue with the block
		{
			
			
			if(speed >= speedLimit)//If delay surpasses maxDelay, drop block
			{
				speed = 1;
				blockList.fall();
			}
			else//Else increment the delay
			{
				speed = speed*(1+levelNum*speedFactor);
				//System.out.println("Speed: " + speed);
			}
			
		}

		repaint();
	}
	
	//checkRows look for a filled line.
	//If there is a line, the line is erased, all squares above will fall down 1 row,
	//and the player will be awarded points and progress the level requirement
	public void checkRows()
	{
		boolean lineFilled;//this bool keeps track of a line
		
		for(int height = 0; height < cellHeight; height++)
		{
			lineFilled = true;//Set line fill to true
			
			for(int length = 0; length < cellLength; length++)
			{
				if(!(grid[length][height]))//If there is a cell in a horizontal grid that is false, set bool value to false
				{
					lineFilled = false;
				}
			}
			
			//If the lineFilled has not turn to false, then the line is filled
			if(lineFilled)
			{
				dropRows(height);
				
				//Update score and line complete
				linesCompleted = linesCompleted + 1;
				scoreValue = scoreValue + levelNum*scoringFactor;
				
				//If lines completed surpass the level requirement, go to next level.
				if(linesCompleted >= nextLevelReq)
				{
					levelNum = levelNum + 1;
					linesCompleted = 0;
				}
				
				//TEST: Print Success
				//System.out.println("LINE FILLED");
			}
			
		}
	}
	
	//This function clears the current row and drops all blocks above by 1 square
	public void dropRows(int h)
	{
		//Clear Row
		for(int r = 0; r < cellLength; r++)
		{
			grid[r][h] = false;
		}
		
		//Drop all blocks above
		for(int height = h; height >= 0; height--)
		{
			for(int length = 0; length < cellLength; length++)
			{
				if(grid[length][height])
				{
					//Set the grid of the block lower to true along with the appropriate block color
					grid[length][height+1] = true;
					colorGrid[length][height+1] = colorGrid[length][height];
					
					//Set the grid from the previous position to false
					grid[length][height] = false;
				}
			}
		}
	}
	
	//This function is called to replace the current falling block with the next block
	//and updates the score
	public void replace()
	{
		activeBlock = false;
		
		//Update score
		scoreValue = scoreValue - levelNum*scoringFactor;
		
		//If score is less than 0, then set score to 0 to prevent negative scores
		if(scoreValue < 0)
			scoreValue = 0;
	}
	
	
	
	//This fuction gets the modifiers from the Tetris Modifier java file for game customization
	public void getModifiers()
	{ 
		//Update Factor modifiers
		scoringFactor = TM.getS_F();
		nextLevelReq = TM.getR_R();
		speedFactor = TM.getSP_F();

		//Update square size and play area
		squareSize = TM.getsquareSize();
		blockList.squareSize = TM.getsquareSize();
		nextBlock.squareSize = TM.getsquareSize();
		
		//Update custom blocks
		customBlockList = TM.getBlockList();
		customBlockColor = TM.getColorList();
		
		//Test: see block
//		if(customBlockList.size() > 0)
//		{
//			for(int i = 0;i < customBlockList.size();i++)
//			{
//				System.out.println("Block " + i);
//				
//				for(int m = 0;m < 3;m++)
//				{
//					for(int n = 0;n < 3;n++)
//					{
//						System.out.println(customBlockList.get(i)[m][n]);
//					}
//				}
//				//System.out.println("Color " + customBlockColor.get(i));
//			}
//		}
		
		//Update values
		playAreaWidth = squareSize*cellLength;
		playAreaHeight = squareSize*cellHeight;
		nextBlockX = 11*squareSize-10;
		nextBlockY = 10;
		nextBlockWidth = 4*squareSize;
		nextBlockHeight = 4*squareSize;
		levelX = 11*squareSize-10;
		levelY = 100+3*squareSize;
		scoreX = 11*squareSize-10;
		scoreY = 160+3*squareSize;
		lineX = 11*squareSize-10;
		lineY = 130+3*squareSize;
		scoreX = 11*squareSize-10;
		scoreY = 160+3*squareSize;
		quitBoxX = 11*squareSize-10;
		quitBoxY = 700;
		quitBoxWidth = 3*squareSize;
		quitBoxHeight = 2*squareSize;
		pauseBoxWidth = 3*squareSize;
		pauseBoxHeight = 2*squareSize;
		
		nextBlock.X = Math.round(nextBlockX+(cellLength-10)*squareSize+nextBlockWidth/2)-2*squareSize;
		nextBlock.Y = Math.round(nextBlockY+nextBlockHeight/2)-squareSize;
	}
}





//This enum stores all variables for each block type
enum BlockType{BOX,L,REVERSEL,Z,REVERSEZ,HILL,LINE};
