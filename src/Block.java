import java.awt.Color;
import java.awt.Graphics;
/*
 * CS 6366.0U1: Computer Graphics 
 * Project3: Tetris
 * 
 * Goal: This creates blocks for the main play area and keeps track of the current falling block
 * 
 * By,
 * Michael Del Rosario (mxd120830)
 */
class Block
{
	//A square for each block
	int squareSize = 40;
	int X,Y,rotate,count=0;
	int cellLength;
	int cellHeight;
	int blockNum = 4;
	boolean customBlock = false;//For customized block
	
	//Create a grid 2D array for collision detection.
	//This grid stores each index as one square.
	//If grid[blockX][blockY] is true, then a square occupies that grid
	boolean[][] grid;
	boolean[][] customGrid = new boolean[3][3];
	
	//These two arrays stores each individual square's X and Y position
	//the index of the grid will be true based on both X and Y arrays number
	int[] blockX = new int[4];
	int[] blockY = new int[4];
	int[] blockPos = new int[4];//For custom block rotations
	
	//Create a variable 
	BlockType type;
	Color color;
	
	//Dummy should never be accessed
	Block(int X, int Y){this.X=X;this.Y=Y;}
	
	//Standard block constructor
	Block(int X, int Y, int squareSize, int width, int height, BlockType type){
		this.X=X;this.Y=Y;this.type=type;this.squareSize = squareSize;
		cellLength = width;
		cellHeight = height;
		grid = new boolean[cellLength][cellHeight];
		
		//Empty grid
		for(int i = 0;i < cellLength;i++)
		{
			for(int j = 0;j < cellHeight;j++)
			{
				grid[i][j] = false;
			}
		}
		//Set the grid
		setGrid();
	}
	
	//This constructor is used for customize blocks
	Block(int X, int Y,int squareSize, int width, int height, boolean[][] customGrid, Color col)
	{
		this.X=X;this.Y=Y;this.type=null;this.squareSize = squareSize;
		cellLength = width;
		cellHeight = height;
		grid = new boolean[cellLength][cellHeight];
		color = col;
		customBlock = true;
		int blockCount = 0;
		
		//Fill custom grid
		for(int i = 0;i < 3;i++)
		{
			for(int j = 0;j < 3;j++)
			{
				if(customGrid[i][j])
				{
					blockCount++;
					this.customGrid[i][j] = true;
				}
				else
				{
					this.customGrid[i][j] = false;
				}
				
				//System.out.println(customGrid[i][j]);
			}
		}
//		System.out.println(blockCount);
//		System.out.println(customGrid[0][0]);
		
		int counter = 0, lastX=0,lastY=0,lastPos=9;
		//Initialize each square based on the grid
		//initialization is top to bottom -> left to right
		for(int i = 0;i < 3;i++)
		{
			for(int j = 0;j < 3;j++)
			{
				if(customGrid[i][j] && blockCount != 0)
				{
					
					blockX[counter] = X + i;blockY[counter] = Y+j;
					blockPos[counter] = getCustomPosition(i,j);
//					System.out.println("Block " + counter + ": " + blockX[counter] + "," + blockY[counter]);
					lastX=i;
					lastY=j;
					lastPos = blockPos[counter];
					counter++;
					//System.out.println("Counter = " + counter);
					blockCount--;
				}
			}
		}
		
		//Initialize the remaining squares to the last initialized square
		while(counter <= 3)
		{
			blockX[counter]=X+lastX;blockY[counter]=Y+lastY;
			blockPos[counter] = lastPos;
			counter++;
		}
		
		
		//Empty grid
		for(int i = 0;i < cellLength;i++)
		{
			for(int j = 0;j < cellHeight;j++)
			{
				grid[i][j] = false;
			}
		}
		//Set the grid
		setGrid();
		
		//See blocks and their position
//		for(int i=0;i<4;i++)
//		{
//			System.out.println("Block " + i + ": " + blockX[i] + "," + blockY[i]);
//			System.out.println("Block Pos " + i + ": " + blockPos[i]);
//		}
	}
	
	//This function redirects to the appropriate draw function based on the block type
	//This function will draw the block according to the type
	public void drawBlock(Graphics g)
	{
		if(!customBlock)
		{
		switch(type)
		{
			case BOX:
				color = Color.green;
				//drawBox(g);
				break;
			case L:
				color = Color.red;
				//drawL(g);
				break;
			case REVERSEL:
				color = Color.blue;
				//drawReverseL(g);
				break;
			case Z:
				color = Color.pink;
				//drawZ(g);
				break;
			case REVERSEZ:
				color = Color.yellow;
				//drawReverseZ(g);
				break;
			case HILL:
				color = Color.cyan;
				//drawHill(g);
				break;
			case LINE:
				color = Color.orange;
				//drawLine(g);
				break;
			default:
				;
		}
		}
		
		//Begin the drawing process
		//Store beginning position
		int posX=X, posY=Y;
		
		//This for loop creates an individual square each iteration
		for(int i=0;i<4;i++)
		{
			//Update current square's condition
			switch(i)
			{
				case 0:
					posX = 10 + blockX[0]*squareSize;
					posY = 10 + blockY[0]*squareSize;
					break;
				case 1:
					posX = 10 + blockX[1]*squareSize;
					posY = 10 + blockY[1]*squareSize;
					break;
				case 2:
					posX = 10 + blockX[2]*squareSize;
					posY = 10 + blockY[2]*squareSize;
					break;
				case 3:
					posX = 10 + blockX[3]*squareSize;
					posY = 10 + blockY[3]*squareSize;
					break;
			}
			
			//DrawSquare
			g.setColor(color);
			g.fillRect(posX, posY, squareSize, squareSize);
			g.setColor(Color.black);
			g.drawRect(posX, posY, squareSize, squareSize);
			
			//Reset Positions
			posX=X;
			posY=Y;
		}
	}
	
	//This function sets the grid of a newly created block
	//This function fill the grid and store the individual block positions based on the block type
	public void setGrid()
	{
		if(!customBlock)
		{
		switch(type)
		{
			case BOX:
				blockX[0]=X;blockX[1]=X+1;blockX[2]=X;blockX[3]=X+1;
				blockY[0]=Y;blockY[1]=Y;blockY[2]=Y+1;blockY[3]=Y+1;
				
				
				break;
			case L:
				blockX[0]=X;blockX[1]=X+1;blockX[2]=X+2;blockX[3]=X+2;
				blockY[0]=Y+1;blockY[1]=Y+1;blockY[2]=Y+1;blockY[3]=Y;
				
				
				break;
			case REVERSEL:
				blockX[0]=X;blockX[1]=X;blockX[2]=X+1;blockX[3]=X+2;
				blockY[0]=Y;blockY[1]=Y+1;blockY[2]=Y+1;blockY[3]=Y+1;
				
				
				break;
			case Z:
				blockX[0]=X;blockX[1]=X+1;blockX[2]=X+1;blockX[3]=X+2;
				blockY[0]=Y;blockY[1]=Y;blockY[2]=Y+1;blockY[3]=Y+1;
				
				
				break;
			case REVERSEZ:
				blockX[0]=X;blockX[1]=X+1;blockX[2]=X+1;blockX[3]=X+2;
				blockY[0]=Y+1;blockY[1]=Y+1;blockY[2]=Y;blockY[3]=Y;
				
				
				break;
			case HILL:
				blockX[0]=X+1;blockX[1]=X;blockX[2]=X+1;blockX[3]=X+2;
				blockY[0]=Y;blockY[1]=Y+1;blockY[2]=Y+1;blockY[3]=Y+1;
				
				
				break;
			case LINE:
				blockX[0]=X;blockX[1]=X+1;blockX[2]=X+2;blockX[3]=X+3;
				blockY[0]=Y;blockY[1]=Y;blockY[2]=Y;blockY[3]=Y;
				
				
				break;
			default:
				;
		}
		}
		
		grid[blockX[0]][blockY[0]] = true;
		grid[blockX[1]][blockY[1]] = true;
		grid[blockX[2]][blockY[2]] = true;
		grid[blockX[3]][blockY[3]] = true;
	}
	
	//This function gets the grid for fallen block and adds it to the grid from the Tetris class
	//returns the full grid
	public boolean[][] getGrid(boolean[][] gameGrid)
	{
		//Set the color according to the blocks on the grid.
		for(int i = 0;i<blockNum;i++)
		{
			gameGrid[blockX[i]][blockY[i]] = true;
		}
		
		return gameGrid;
	}
	
	//This function receives the click position and determines
	//if the click is inside the block (inside the grid), return true; returns false if not
	//This is used for the replacement mechanic
	public boolean clickedBlock(int clickX,int clickY)
	{
		for(int i = 0;i<blockNum;i++)
		{
			int posX = 10+blockX[i]*squareSize;
			int posY = 10+blockY[i]*squareSize;
			
			//TEST: Check position and click values
//			System.out.println("posX " + i + " = " + posX);
//			System.out.println("posY " + i + " = " + + posY);
//			System.out.println("clickX " + i + " = " + + clickX);
//			System.out.println("clickY " + i + " = " + + clickY);
			
			//if click is inside a square return true.
			if((posX <= clickX && clickX <= posX + squareSize) && (posY <= clickY && clickY <= posY + squareSize))
			{
				return true;
			}
		}
		
		return false;
	}
	
	//This function updates the color grid from the Tetris class and returns the color grid
	//This function is used to allow the Tetris to draw all non-moving blocks and is called when
	//this block has stopped falling
	public Color[][] getColorGrid(Color[][] C_Grid)
	{
		//Set the color according to the blocks on the grid.
		for(int i = 0;i<blockNum;i++)
		{
			C_Grid[blockX[i]][blockY[i]] = color;
		}
		
		return C_Grid;
	}
		
	public void drawBox(Graphics g)
	{
		//Store beginning position
		int posX=X, posY=Y;
		
		//This for loop creates an individual square each iteration
		for(int i=0;i<4;i++)
		{
			//Update current square's condition
			switch(i)
			{
				case 0:
					posX = 10 + blockX[0]*squareSize;
					posY = 10 + blockY[0]*squareSize;
					break;
				case 1:
					posX = 10 + blockX[1]*squareSize;
					posY = 10 + blockY[1]*squareSize;
					break;
				case 2:
					posX = 10 + blockX[2]*squareSize;
					posY = 10 + blockY[2]*squareSize;
					break;
				case 3:
					posX = 10 + blockX[3]*squareSize;
					posY = 10 + blockY[3]*squareSize;
					break;
			}
			
			//DrawSquare
			g.setColor(color);
			g.fillRect(posX, posY, squareSize, squareSize);
			g.setColor(Color.black);
			g.drawRect(posX, posY, squareSize, squareSize);
			
			//Reset Positions
			posX=X;
			posY=Y;
		}
	}
	
	public void drawL(Graphics g)
	{
		//Store beginning position
		int posX=X, posY=Y;
		int dummy = 0;
		
		switch(dummy)
		{
			case 0:
				//This for loop creates an individual square each iteration
				for(int i=0;i<4;i++)
				{
					//Update current square's condition
					switch(i)
					{
					case 0:
						posX = 10 + blockX[0]*squareSize;
						posY = 10 + blockY[0]*squareSize;
						break;
					case 1:
						posX = 10 + blockX[1]*squareSize;
						posY = 10 + blockY[1]*squareSize;
						break;
					case 2:
						posX = 10 + blockX[2]*squareSize;
						posY = 10 + blockY[2]*squareSize;
						break;
					case 3:
						posX = 10 + blockX[3]*squareSize;
						posY = 10 + blockY[3]*squareSize;
						break;
					}
					
					//DrawSquare
					g.setColor(color);
					g.fillRect(posX, posY, squareSize, squareSize);
					g.setColor(Color.black);
					g.drawRect(posX, posY, squareSize, squareSize);
					
					//Reset Positions
					posX=X;
					posY=Y;
				}
				break;
		}
	}
			
	public void drawReverseL(Graphics g)
	{
		//Store beginning position
		int posX=X, posY=Y;
		int dummy = 0;
		
		switch(dummy)
		{
			case 0:
				//This for loop creates an individual square each iteration
				for(int i=0;i<4;i++)
				{
					//Update current square's condition
					switch(i)
					{
					case 0:
						posX = 10 + blockX[0]*squareSize;
						posY = 10 + blockY[0]*squareSize;
						break;
					case 1:
						posX = 10 + blockX[1]*squareSize;
						posY = 10 + blockY[1]*squareSize;
						break;
					case 2:
						posX = 10 + blockX[2]*squareSize;
						posY = 10 + blockY[2]*squareSize;
						break;
					case 3:
						posX = 10 + blockX[3]*squareSize;
						posY = 10 + blockY[3]*squareSize;
						break;
					}
					
					//DrawSquare
					g.setColor(color);
					g.fillRect(posX, posY, squareSize, squareSize);
					g.setColor(Color.black);
					g.drawRect(posX, posY, squareSize, squareSize);
					
					//Reset Positions
					posX=X;
					posY=Y;
				}
				break;
		}
	}
	
	public void drawZ(Graphics g)
	{
		//Store beginning position
		int posX=X, posY=Y;
		int dummy = 0;
		
		switch(dummy)
		{
			case 0:
				//This for loop creates an individual square each iteration
				for(int i=0;i<4;i++)
				{
					//Update current square's condition
					switch(i)
					{
					case 0:
						posX = 10 + blockX[0]*squareSize;
						posY = 10 + blockY[0]*squareSize;
						break;
					case 1:
						posX = 10 + blockX[1]*squareSize;
						posY = 10 + blockY[1]*squareSize;
						break;
					case 2:
						posX = 10 + blockX[2]*squareSize;
						posY = 10 + blockY[2]*squareSize;
						break;
					case 3:
						posX = 10 + blockX[3]*squareSize;
						posY = 10 + blockY[3]*squareSize;
						break;
					}
					
					//DrawSquare
					g.setColor(color);
					g.fillRect(posX, posY, squareSize, squareSize);
					g.setColor(Color.black);
					g.drawRect(posX, posY, squareSize, squareSize);
					
					//Reset Positions
					posX=X;
					posY=Y;
				}
				break;
		}
	}
	
	public void drawReverseZ(Graphics g)
	{
		//Store beginning position
		int posX=X, posY=Y;
		int dummy = 0;
				
		switch(dummy)
		{
			case 0:
				//This for loop creates an individual square each iteration
				for(int i=0;i<4;i++)
				{
					//Update current square's condition
					switch(i)
					{
					case 0:
						posX = 10 + blockX[0]*squareSize;
						posY = 10 + blockY[0]*squareSize;
						break;
					case 1:
						posX = 10 + blockX[1]*squareSize;
						posY = 10 + blockY[1]*squareSize;
						break;
					case 2:
						posX = 10 + blockX[2]*squareSize;
						posY = 10 + blockY[2]*squareSize;
						break;
					case 3:
						posX = 10 + blockX[3]*squareSize;
						posY = 10 + blockY[3]*squareSize;
						break;
					}
					
					//DrawSquare
					g.setColor(color);
					g.fillRect(posX, posY, squareSize, squareSize);
					g.setColor(Color.black);
					g.drawRect(posX, posY, squareSize, squareSize);
					
					//Reset Positions
					posX=X;
					posY=Y;
				}
				break;
		}
	}
	
	public void drawLine(Graphics g)
	{
		//Store beginning position
		int posX=X, posY=Y;
		
		int dummy = 0;
				
		switch(dummy)
		{
			case 0:
				//This for loop creates an individual square each iteration
				for(int i=0;i<4;i++)
				{
					//Update current square's condition
					switch(i)
					{
					case 0:
						posX = 10 + blockX[0]*squareSize;
						posY = 10 + blockY[0]*squareSize;
						break;
					case 1:
						posX = 10 + blockX[1]*squareSize;
						posY = 10 + blockY[1]*squareSize;
						break;
					case 2:
						posX = 10 + blockX[2]*squareSize;
						posY = 10 + blockY[2]*squareSize;
						break;
					case 3:
						posX = 10 + blockX[3]*squareSize;
						posY = 10 + blockY[3]*squareSize;
						break;
					}
					
					//DrawSquare
					g.setColor(color);
					g.fillRect(posX, posY, squareSize, squareSize);
					g.setColor(Color.black);
					g.drawRect(posX, posY, squareSize, squareSize);
					
					//Reset Positions
					posX=X;
					posY=Y;
				}
				break;
			
			}
	}
	
	public void drawHill(Graphics g)
	{
		//Store beginning position
		int posX=X, posY=Y;
		int dummy = 0;
		
		switch(dummy)
		{
			case 0:
				//This for loop creates an individual square each iteration
				for(int i=0;i<4;i++)
				{
					//Update current square's condition
					switch(i)
					{
					case 0:
						posX = 10 + blockX[0]*squareSize;
						posY = 10 + blockY[0]*squareSize;
						break;
					case 1:
						posX = 10 + blockX[1]*squareSize;
						posY = 10 + blockY[1]*squareSize;
						break;
					case 2:
						posX = 10 + blockX[2]*squareSize;
						posY = 10 + blockY[2]*squareSize;
						break;
					case 3:
						posX = 10 + blockX[3]*squareSize;
						posY = 10 + blockY[3]*squareSize;
						break;
					}
					
					//DrawSquare
					g.setColor(color);
					g.fillRect(posX, posY, squareSize, squareSize);
					g.setColor(Color.black);
					g.drawRect(posX, posY, squareSize, squareSize);
					
					//Reset Positions
					posX=X;
					posY=Y;
				}
				break;
		}
	}
	
	//This function checks the falling block collided with the blocks or the wall when moving to the side
	//already on the play area
	public boolean checkSideCollision(boolean[][] gameGrid, boolean left)
	{
		
		//Check for side block collision
		for(int l=0;l<cellLength;l++)
		{
			for(int h=0;h<cellHeight;h++)
			{
				if(grid[l][h] && gameGrid[l][h])
				{
					//System.out.println("TRUE");
					return true;
				}
				
				if(l>1 && left)
				{
					if(grid[l][h] && gameGrid[l-1][h])
					{
						//System.out.println("TRUE");
						return true;
					}
				}
				
				if(l<cellLength-1 && ! left)
				{
					if(grid[l][h] && gameGrid[l+1][h])
					{
						//System.out.println("TRUE");
						return true;
					}
				}
			}
		}
		
		//Check for wall collision
		if((blockX[0]-1 < 0 || blockX[1]-1 < 0 || blockX[2]-1 < 0 || blockX[3]-1 < 0) && left)
		{
			return true;
		}
		
		if((blockX[0] + 1 >= cellLength || blockX[1] +1 >= cellLength || blockX[2]+ 1 >= cellLength || blockX[3] +1 >= cellLength) && !left)
		{
			return true;
		}
		
		return false;
	}
	
	
	//This function checks the falling block collided with the blocks or with the floor
	//already on the play area
	public boolean checkCollision(boolean[][] gameGrid)
	{	
		
		for(int l=0;l<cellLength;l++)
		{
			for(int h=0;h<cellHeight;h++)
			{
				if(grid[l][h] && gameGrid[l][h])
				{
					//System.out.println("TRUE");
					return true;
				}
				
				if(h<cellHeight-1)
				{
					if(grid[l][h] && gameGrid[l][h+1])
					{
						//System.out.println("TRUE");
						return true;
					}
				}
			}
		}
		
		//Check if any blocks will hit the floor
		for(int i = 0;i<blockNum;i++)
		{
			if(blockY[i] + 1 >= cellHeight)
				return true;
		}
		
		return false;//falling block did not collide
	}
	
	
	//Move the block and grid to the left
	public void moveLeft()
	{
		//Empty grid
		for(int i = 0;i < cellLength;i++)
		{
			for(int j = 0;j < cellHeight;j++)
			{
				grid[i][j] = false;
			}
		}
		
		if(!(blockX[0]-1 < 0 || blockX[1]-1 < 0 || blockX[2]-1 < 0 || blockX[3]-1 < 0))
		{
			//Move the grid to the left
			blockX[0]--;blockX[1]--;blockX[2]--;blockX[3]--;
		}
		
		grid[blockX[0]][blockY[0]] = true;
		grid[blockX[1]][blockY[1]] = true;
		grid[blockX[2]][blockY[2]] = true;
		grid[blockX[3]][blockY[3]] = true;
	}
	
	//Move the block and grid to the right
	public void moveRight()
	{
		//Empty grid
		for(int i = 0;i < cellLength;i++)
		{
			for(int j = 0;j < cellHeight;j++)
			{
				grid[i][j] = false;
			}
		}
		
		if(!(blockX[0] + 1 >= cellLength || blockX[1] +1 >= cellLength || blockX[2]+ 1 >= cellLength || blockX[3] +1 >= cellLength))
		{
			//Move the grid to the left
			blockX[0]++;blockX[1]++;blockX[2]++;blockX[3]++;
		}
		
		
		
		grid[blockX[0]][blockY[0]] = true;
		grid[blockX[1]][blockY[1]] = true;
		grid[blockX[2]][blockY[2]] = true;
		grid[blockX[3]][blockY[3]] = true;
	}
	
	
	//This function rises the block a whole half square height
	public void riseHalf()
	{	
		//Empty grid
		for(int i = 0;i < cellLength;i++)
		{
			for(int j = 0;j < cellHeight;j++)
			{
				grid[i][j] = false;
			}
		}
		
		//Increment the blocks position
		if(count==1)//Move the grid upwards if it was dropped a whole block
		{
			blockY[0]--;blockY[1]--;blockY[2]--;blockY[3]--;
			
			grid[blockX[0]][blockY[0]] = true;
			grid[blockX[1]][blockY[1]] = true;
			grid[blockX[2]][blockY[2]] = true;
			grid[blockX[3]][blockY[3]] = true;
			
			//System.out.println(blockY[3]);
			
			count = 0;
		}
		else
		{
			count = 1;
		}
	}
	
	public void riseWhole()
	{
		//Empty grid
		for(int i = 0;i < cellLength;i++)
		{
			for(int j = 0;j < cellHeight;j++)
			{
				grid[i][j] = false;
			}
		}
		
		//Move the grid upwards
		blockY[0]--;blockY[1]--;blockY[2]--;blockY[3]--;
		
		grid[blockX[0]][blockY[0]] = true;
		grid[blockX[1]][blockY[1]] = true;
		grid[blockX[2]][blockY[2]] = true;
		grid[blockX[3]][blockY[3]] = true;
		
		//System.out.println(blockY[3]);
		
		count = 0;
	}
	
	public void fall()
	{
		//Empty grid
		for(int i = 0;i < cellLength;i++)
		{
			for(int j = 0;j < cellHeight;j++)
			{
				grid[i][j] = false;
			}
		}
		
		blockY[0]++;blockY[1]++;blockY[2]++;blockY[3]++;
		
		grid[blockX[0]][blockY[0]] = true;
		grid[blockX[1]][blockY[1]] = true;
		grid[blockX[2]][blockY[2]] = true;
		grid[blockX[3]][blockY[3]] = true;
	}
	
	//This function is for rotation of customized blocks
	//Rotation is based on grid the blocks were placed when added
	//	-------------
	//	|1	|2	|3	|
	//	--------------
	//	|8	|9	|4	|
	//	-------------
	//	|7	|6	|5	|
	//	-------------
	//CW: 1->3->5->7 and 2->4->6->8
	//CCW: 7->5->3->1 and 8->6->4->2
	//position 9 does not change
	public void rotateCustom(boolean[][] gameGrid, int rot)
	{
		if(rot > 0)//Clockwise Rotation
		{
			for(int i = 0;i < 4;i++)
			{
				switch(blockPos[i])
				{
					case 1:
						blockX[i] += 2; blockY[i] += 0;
						blockPos[i] += 2;
						break;
					case 3:
						blockX[i] += 0; blockY[i] += 2;
						blockPos[i] += 2;
						break;
					case 5:
						blockX[i] -= 2; blockY[i] += 0;
						blockPos[i] += 2;
						break;
					case 7:
						blockX[i] += 0; blockY[i] -= 2;
						blockPos[i] = 1;
						break;
					case 2:
						blockX[i] += 1; blockY[i] += 1;
						blockPos[i] += 2;
						break;
					case 4:
						blockX[i] -= 1; blockY[i] += 1;
						blockPos[i] += 2;
						break;
					case 6:
						blockX[i] -= 1; blockY[i] -= 1;
						blockPos[i] += 2;
						break;
					case 8:
						blockX[i] += 1; blockY[i] -= 1;
						blockPos[i] = 2;
						break;
				}
			}
		}
		else//Counter clockwise
		{
			for(int i = 0;i < 4;i++)
			{
				switch(blockPos[i])
				{
					case 1:
						blockX[i] += 0; blockY[i] += 2;
						blockPos[i] = 7;
						break;
					case 3:
						blockX[i] -= 2; blockY[i] += 0;
						blockPos[i] -= 2;
						break;
					case 5:
						blockX[i] -= 0; blockY[i] -= 2;
						blockPos[i] -= 2;
						break;
					case 7:
						blockX[i] += 2; blockY[i] -= 0;
						blockPos[i] -= 2;
						break;
					case 2:
						blockX[i] -= 1; blockY[i] += 1;
						blockPos[i] = 8;
						break;
					case 4:
						blockX[i] -= 1; blockY[i] -= 1;
						blockPos[i] -= 2;
						break;
					case 6:
						blockX[i] += 1; blockY[i] -= 1;
						blockPos[i] -= 2;
						break;
					case 8:
						blockX[i] += 1; blockY[i] += 1;
						blockPos[i] -= 2;
						break;
				}
			}
		}
		
		
		int minX=cellLength, maxX=0,minY=cellLength,maxY =0;
		
		//Find the maximum and minimum X and Y of all blocks
				for(int i = 0;i<blockNum;i++)
				{
					if(blockX[i] < minX)
						minX = blockX[i];
					if(blockX[i] > maxX)
						maxX = blockX[i];
					
					if(blockY[i] < minY)
						minY = blockY[i];
					if(blockY[i] > maxY)
						maxY = blockY[i];
				}
				
				//If there is a square that is outside play area move block back in
				while(minX < 0)
				{
					blockX[0]++;blockX[1]++;blockX[2]++;blockX[3]++;
					minX++;
				}
				while(maxX >= cellLength)
				{
					blockX[0]--;blockX[1]--;blockX[2]--;blockX[3]--;
					maxX--;
				}
				while(minY < 0)
				{
					blockY[0]++;blockY[1]++;blockY[2]++;blockY[3]++;
					minY++;
				}
				while(maxY >= cellHeight)
				{
					blockY[0]--;blockY[1]--;blockY[2]--;blockY[3]--;
					maxY--;
				}
		
		//Set new grid
		grid[blockX[0]][blockY[0]] = true;
		grid[blockX[1]][blockY[1]] = true;
		grid[blockX[2]][blockY[2]] = true;
		grid[blockX[3]][blockY[3]] = true;
	}
	
	//Rotate the shape
	public void rotate(int rot, boolean[][] gameGrid)
	{
		//Empty grid
		for(int i = 0;i < cellLength;i++)
		{
			for(int j = 0;j < cellHeight;j++)
			{
				grid[i][j] = false;
			}
		}

		//If block is custom, use rotateCustom instead
		if(customBlock)
		{
			rotateCustom(gameGrid, rot);
			return;
		}
		
		int minX=cellLength, maxX=0,minY=cellLength,maxY =0;
		
		
		
		rotate += rot;
		
		if(rotate >= 4)
			rotate = 0;
		if(rotate < 0)
			rotate = 3;
		
		//Transfer the rotation to the block grid
		if(rot > 0)//Clockwise Rotation
		{
			switch(rotate)
			{
				case 0:
					switch(type)
					{
						case BOX:
							break;
						case L:
							blockX[0] -=1;blockX[1] +=0;blockX[2] +=1;blockX[3] +=2;
							blockY[0] -=1;blockY[1] +=0;blockY[2] +=1;blockY[3] -=0;
							break;
						case REVERSEL:
							blockX[0] -=0;blockX[1] -=1;blockX[2] +=0;blockX[3] +=1;
							blockY[0] -=2;blockY[1] -=1;blockY[2] +=0;blockY[3] +=1;
							break;
						case Z:
							blockX[0] -=2;blockX[1] +=0;blockX[2] -=1;blockX[3] +=1;
							blockY[0] +=1;blockY[1] +=0;blockY[2] +=1;blockY[3] -=0;
							break;
						case REVERSEZ:
							blockX[0] -=1;blockX[1] +=0;blockX[2] +=1;blockX[3] +=2;
							blockY[0] -=1;blockY[1] +=0;blockY[2] -=1;blockY[3] +=0;
							break;
						case LINE:
							blockX[0] -=0;blockX[1] +=1;blockX[2] +=2;blockX[3] +=3;
							blockY[0] -=0;blockY[1] -=1;blockY[2] -=2;blockY[3] -=3;
							break;
						case HILL:
							blockX[0] +=1;blockX[1] -=1;blockX[2] -=0;blockX[3] +=1;
							blockY[0] -=1;blockY[1] -=1;blockY[2] -=0;blockY[3] +=1;
							break;
					}
					break;
				case 1:
					switch(type)
					{
						case BOX:
							break;
						case L:
							blockX[0] +=1;blockX[1] +=0;blockX[2] -=1;blockX[3] -=0;
							blockY[0] -=1;blockY[1] +=0;blockY[2] +=1;blockY[3] +=2;
							break;
						case REVERSEL:
							blockX[0] +=1;blockX[1] +=0;blockX[2] -=1;blockX[3] -=2;
							blockY[0] -=0;blockY[1] -=1;blockY[2] +=0;blockY[3] +=1;
							break;
						case Z:
							blockX[0] +=2;blockX[1] +=0;blockX[2] +=1;blockX[3] -=1;
							blockY[0] -=1;blockY[1] +=0;blockY[2] -=1;blockY[3] +=0;
							break;
						case REVERSEZ:
							blockX[0] +=1;blockX[1] +=0;blockX[2] -=1;blockX[3] -=2;
							blockY[0] +=1;blockY[1] +=0;blockY[2] +=1;blockY[3] +=0;
							break;
						case LINE:
							blockX[0] -=0;blockX[1] -=1;blockX[2] -=2;blockX[3] -=3;
							blockY[0] -=0;blockY[1] +=1;blockY[2] +=2;blockY[3] +=3;
							break;
						case HILL:
							blockX[0] +=1;blockX[1] +=1;blockX[2] -=0;blockX[3] -=1;
							blockY[0] +=1;blockY[1] -=1;blockY[2] -=0;blockY[3] +=1;
							break;
					}
					break;
				case 2:
					switch(type)
					{
						case BOX:
							break;
						case L:
							blockX[0] +=1;blockX[1] +=0;blockX[2] -=1;blockX[3] -=2;
							blockY[0] +=1;blockY[1] +=0;blockY[2] -=1;blockY[3] +=0;
							break;
						case REVERSEL:
							blockX[0] +=1;blockX[1] +=2;blockX[2] +=1;blockX[3] -=0;
							blockY[0] +=1;blockY[1] -=0;blockY[2] -=1;blockY[3] -=2;
							break;
						case Z:
							blockX[0] -=2;blockX[1] +=0;blockX[2] -=1;blockX[3] +=1;
							blockY[0] +=1;blockY[1] +=0;blockY[2] +=1;blockY[3] -=0;
							break;
						case REVERSEZ:
							blockX[0] -=1;blockX[1] +=0;blockX[2] +=1;blockX[3] +=2;
							blockY[0] -=1;blockY[1] +=0;blockY[2] -=1;blockY[3] +=0;
							break;
						case LINE:
							blockX[0] -=0;blockX[1] +=1;blockX[2] +=2;blockX[3] +=3;
							blockY[0] -=0;blockY[1] -=1;blockY[2] -=2;blockY[3] -=3;
							break;
						case HILL:
							blockX[0] -=1;blockX[1] +=1;blockX[2] -=0;blockX[3] -=1;
							blockY[0] +=1;blockY[1] +=1;blockY[2] -=0;blockY[3] -=1;
							break;
					}
					break;
				case 3:
					switch(type)
					{
						case BOX:
							break;
						case L:
							blockX[0] -=1;blockX[1] +=0;blockX[2] +=1;blockX[3] -=0;
							blockY[0] +=1;blockY[1] +=0;blockY[2] -=1;blockY[3] -=2;
							break;
						case REVERSEL:
							blockX[0] -=2;blockX[1] -=1;blockX[2] -=0;blockX[3] +=1;
							blockY[0] +=1;blockY[1] +=2;blockY[2] +=1;blockY[3] +=0;
							break;
						case Z:
							blockX[0] +=2;blockX[1] +=0;blockX[2] +=1;blockX[3] -=1;
							blockY[0] -=1;blockY[1] +=0;blockY[2] -=1;blockY[3] +=0;
							break;
						case REVERSEZ:
							blockX[0] +=1;blockX[1] +=0;blockX[2] -=1;blockX[3] -=2;
							blockY[0] +=1;blockY[1] +=0;blockY[2] +=1;blockY[3] +=0;
							break;
						case LINE:
							blockX[0] -=0;blockX[1] -=1;blockX[2] -=2;blockX[3] -=3;
							blockY[0] -=0;blockY[1] +=1;blockY[2] +=2;blockY[3] +=3;
							break;
						case HILL:
							blockX[0] -=1;blockX[1] -=1;blockX[2] -=0;blockX[3] +=1;
							blockY[0] -=1;blockY[1] +=1;blockY[2] -=0;blockY[3] -=1;
							break;
					}
					break;
			}
		}
		else if(rot < 0)//Counter Clockwise Rotation
		{
			switch(rotate)
			{
				case 0:
					switch(type)
					{
						case BOX:
							break;
						case L:
							blockX[0] -=1;blockX[1] +=0;blockX[2] +=1;blockX[3] -=0;
							blockY[0] +=1;blockY[1] +=0;blockY[2] -=1;blockY[3] -=2;
							break;
						case REVERSEL:
							blockX[0] -=2;blockX[1] -=1;blockX[2] -=0;blockX[3] +=1;
							blockY[0] +=1;blockY[1] +=2;blockY[2] +=1;blockY[3] +=0;
							break;
						case Z:
							blockX[0] -=2;blockX[1] +=0;blockX[2] -=1;blockX[3] +=1;
							blockY[0] +=1;blockY[1] +=0;blockY[2] +=1;blockY[3] -=0;
							break;
						case REVERSEZ:
							blockX[0] -=1;blockX[1] +=0;blockX[2] +=1;blockX[3] +=2;
							blockY[0] -=1;blockY[1] +=0;blockY[2] -=1;blockY[3] +=0;
							break;
						case LINE:
							blockX[0] -=0;blockX[1] +=1;blockX[2] +=2;blockX[3] +=3;
							blockY[0] -=0;blockY[1] -=1;blockY[2] -=2;blockY[3] -=3;
							break;
						case HILL:
							blockX[0] -=1;blockX[1] -=1;blockX[2] -=0;blockX[3] +=1;
							blockY[0] -=1;blockY[1] +=1;blockY[2] -=0;blockY[3] -=1;
							break;
					}
					break;
				case 1:
					switch(type)
					{
						case BOX:
							break;
						case L:
							blockX[0] -=1;blockX[1] +=0;blockX[2] +=1;blockX[3] +=2;
							blockY[0] -=1;blockY[1] +=0;blockY[2] +=1;blockY[3] +=0;
							break;
						case REVERSEL:
							blockX[0] -=0;blockX[1] -=1;blockX[2] +=0;blockX[3] +=1;
							blockY[0] -=2;blockY[1] -=1;blockY[2] +=0;blockY[3] +=1;
							break;
						case Z:
							blockX[0] +=2;blockX[1] +=0;blockX[2] +=1;blockX[3] -=1;
							blockY[0] -=1;blockY[1] +=0;blockY[2] -=1;blockY[3] +=0;
							break;
						case REVERSEZ:
							blockX[0] +=1;blockX[1] +=0;blockX[2] -=1;blockX[3] -=2;
							blockY[0] +=1;blockY[1] +=0;blockY[2] +=1;blockY[3] +=0;
							break;
						case LINE:
							blockX[0] -=0;blockX[1] -=1;blockX[2] -=2;blockX[3] -=3;
							blockY[0] -=0;blockY[1] +=1;blockY[2] +=2;blockY[3] +=3;
							break;
						case HILL:
							blockX[0] +=1;blockX[1] -=1;blockX[2] -=0;blockX[3] +=1;
							blockY[0] -=1;blockY[1] -=1;blockY[2] -=0;blockY[3] +=1;
							break;
					}
					break;
				case 2:
					switch(type)
					{
						case BOX:
							break;
						case L:
							blockX[0] +=1;blockX[1] +=0;blockX[2] -=1;blockX[3] -=0;
							blockY[0] -=1;blockY[1] +=0;blockY[2] +=1;blockY[3] +=2;
							break;
						case REVERSEL:
							blockX[0] +=1;blockX[1] +=0;blockX[2] -=1;blockX[3] -=2;
							blockY[0] -=0;blockY[1] -=1;blockY[2] +=0;blockY[3] +=1;
							break;
						case Z:
							blockX[0] -=2;blockX[1] +=0;blockX[2] -=1;blockX[3] +=1;
							blockY[0] +=1;blockY[1] +=0;blockY[2] +=1;blockY[3] -=0;
							break;
						case REVERSEZ:
							blockX[0] -=1;blockX[1] +=0;blockX[2] +=1;blockX[3] +=2;
							blockY[0] -=1;blockY[1] +=0;blockY[2] -=1;blockY[3] +=0;
							break;
						case LINE:
							blockX[0] -=0;blockX[1] +=1;blockX[2] +=2;blockX[3] +=3;
							blockY[0] -=0;blockY[1] -=1;blockY[2] -=2;blockY[3] -=3;
							break;
						case HILL:
							blockX[0] +=1;blockX[1] +=1;blockX[2] -=0;blockX[3] -=1;
							blockY[0] +=1;blockY[1] -=1;blockY[2] -=0;blockY[3] +=1;
							break;
					}
					break;
				case 3:
					switch(type)
					{
						case BOX:
							break;
						case L:
							blockX[0] +=1;blockX[1] +=0;blockX[2] -=1;blockX[3] -=2;
							blockY[0] +=1;blockY[1] +=0;blockY[2] -=1;blockY[3] -=0;
							break;
						case REVERSEL:
							blockX[0] +=1;blockX[1] +=2;blockX[2] +=1;blockX[3] -=0;
							blockY[0] +=1;blockY[1] -=0;blockY[2] -=1;blockY[3] -=2;
							break;
						case Z:
							blockX[0] +=2;blockX[1] +=0;blockX[2] +=1;blockX[3] -=1;
							blockY[0] -=1;blockY[1] +=0;blockY[2] -=1;blockY[3] +=0;
							break;
						case REVERSEZ:
							blockX[0] +=1;blockX[1] +=0;blockX[2] -=1;blockX[3] -=2;
							blockY[0] +=1;blockY[1] +=0;blockY[2] +=1;blockY[3] +=0;
							break;
						case LINE:
							blockX[0] -=0;blockX[1] -=1;blockX[2] -=2;blockX[3] -=3;
							blockY[0] -=0;blockY[1] +=1;blockY[2] +=2;blockY[3] +=3;
							break;
						case HILL:
							blockX[0] -=1;blockX[1] +=1;blockX[2] -=0;blockX[3] -=1;
							blockY[0] +=1;blockY[1] +=1;blockY[2] -=0;blockY[3] -=1;
							break;
					}
					break;
			}
		}
		
		//TEST: See rotate number
		//System.out.println(rotate);
		
		//Find the maximum and minimum X and Y of all blocks
		for(int i = 0;i<blockNum;i++)
		{
			if(blockX[i] < minX)
				minX = blockX[i];
			if(blockX[i] > maxX)
				maxX = blockX[i];
			
			if(blockY[i] < minY)
				minY = blockY[i];
			if(blockY[i] > maxY)
				maxY = blockY[i];
		}
		
		//If rotation causes collision with other blocks move block up.
//		while(checkSideCollision(gameGrid,true))
//		{
//			System.out.println("TRUE");
//			blockY[0]--;blockY[1]--;blockY[2]--;blockY[3]--;
//		}
//		
//		while(checkSideCollision(gameGrid,false))
//		{
//			System.out.println("TRUE");
//			blockY[0]--;blockY[1]--;blockY[2]--;blockY[3]--;
//		}
		
		//If there is a square that is outside play area move block back in
		while(minX < 0)
		{
			blockX[0]++;blockX[1]++;blockX[2]++;blockX[3]++;
			minX++;
		}
		while(maxX >= cellLength)
		{
			blockX[0]--;blockX[1]--;blockX[2]--;blockX[3]--;
			maxX--;
		}
		while(minY < 0)
		{
			blockY[0]++;blockY[1]++;blockY[2]++;blockY[3]++;
			minY++;
		}
		while(maxY >= cellHeight)
		{
			blockY[0]--;blockY[1]--;blockY[2]--;blockY[3]--;
			maxY--;
		}
		
		
		
		//Set new grid
		grid[blockX[0]][blockY[0]] = true;
		grid[blockX[1]][blockY[1]] = true;
		grid[blockX[2]][blockY[2]] = true;
		grid[blockX[3]][blockY[3]] = true;
	}
	
	//Return the position of the custom block based on the grid position on the TetrisModifier program
	//	-------------
	//	|1	|2	|3	|
	//	--------------
	//	|8	|9	|4	|
	//	-------------
	//	|7	|6	|5	|
	//	-------------
	public int getCustomPosition(int x, int y)
	{
		if(x==0&&y==0)
			return 1;
		if(x==2&&y==0)
			return 3;
		if(x==2&&y==2)
			return 5;
		if(x==0&&y==2)
			return 7;
		if(x==1&&y==0)
			return 2;
		if(x==2&&y==1)
			return 4;
		if(x==1&&y==2)
			return 6;
		if(x==0&&y==1)
			return 8;
		return 9;
	}
}