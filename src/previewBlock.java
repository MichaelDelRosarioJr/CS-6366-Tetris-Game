import java.awt.Color;
import java.awt.Graphics;

/*
 * CS 6366.0U1: Computer Graphics 
 * Project3: Tetris
 * 
 * Goal: This creates the block for the next block area
 * 
 * By,
 * Michael Del Rosario (mxd120830)
 */

//This class stores the block type and draw functions of the next block
class previewBlock
{
	int squareSize = 20;
	int X,Y,rotate,count=0;
	BlockType type;
	boolean customBlock = false;
	boolean[][] customGrid = new boolean[3][3];
	Color color;
	
	previewBlock(int X, int Y,int squareSize, BlockType type){this.X=X;this.Y=Y;this.squareSize=squareSize;this.type=type;}
	
	//For custom blocks
	previewBlock(int X, int Y,int squareSize, boolean[][] customGrid, Color col){
		this.X=X;this.Y=Y;this.squareSize=squareSize;customBlock = true;color = col;

		//Set Grid
		for(int i = 0;i < 3;i++)
		{
			for(int j = 0;j < 3;j++)
			{
				if(customGrid[i][j])
				{
					this.customGrid[i][j] = true;
				}
				else
				{
					this.customGrid[i][j] = false;
				}
				
				//System.out.println(customGrid[i][j]);
			}
		}
	}
	
	//This function redirects to the appropriate draw function based on the block type
		//This function will draw the block according to the type
		public void drawBlock(Graphics g)
		{
			if(customBlock)
			{
				drawCustom(g);
			}
			else
			{
				switch(type)
				{
					case BOX:
						drawBox(g);
						break;
					case L:
						drawL(g);
						break;
					case REVERSEL:
						drawReverseL(g);
						break;
					case Z:
						drawZ(g);
						break;
					case REVERSEZ:
						drawReverseZ(g);
						break;
					case HILL:
						drawHill(g);
						break;
					case LINE:
						drawLine(g);
						break;
					default:
						;
				}
			}
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
						break;
					case 1:
						posX+=squareSize;
						break;
					case 2:
						posX+=squareSize;
						posY+=squareSize;
						break;
					case 3:
						posY+=squareSize;
						break;
				}
				
				//DrawSquare
				g.setColor(Color.green);
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
			
			switch(rotate)
			{
				case 0:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								posX+=2*squareSize;
								break;
							case 1:
								posX+=2*squareSize;
								posY+=squareSize;
								break;
							case 2:
								posX+=squareSize;
								posY+=squareSize;
								break;
							case 3:
								posY+=squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.red);
						g.fillRect(posX, posY, squareSize, squareSize);
						g.setColor(Color.black);
						g.drawRect(posX, posY, squareSize, squareSize);
						
						//Reset Positions
						posX=X;
						posY=Y;
					}
					break;
				case 1:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								break;
							case 1:
								posY+=squareSize;
								break;
							case 2:
								posY+=2*squareSize;
								break;
							case 3:
								posX+=squareSize;
								posY+=2*squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.red);
						g.fillRect(posX, posY, squareSize, squareSize);
						g.setColor(Color.black);
						g.drawRect(posX, posY, squareSize, squareSize);
						
						//Reset Positions
						posX=X;
						posY=Y;
						
						
					}
					break;
				case 2:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								break;
							case 1:
								posY+=squareSize;
								break;
							case 2:
								posX+=squareSize;
								break;
							case 3:
								posX+=2*squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.red);
						g.fillRect(posX, posY, squareSize, squareSize);
						g.setColor(Color.black);
						g.drawRect(posX, posY, squareSize, squareSize);
						
						//Reset Positions
						posX=X;
						posY=Y;
					}
					break;
				case 3:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								break;
							case 1:
								posX+=squareSize;
								break;
							case 2:
								posX+=squareSize;
								posY+=squareSize;
								break;
							case 3:
								posX+=squareSize;
								posY+=2*squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.red);
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
			
			switch(rotate)
			{
				case 0:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								break;
							case 1:
								posY+=squareSize;
								break;
							case 2:
								posX+=squareSize;
								posY+=squareSize;
								break;
							case 3:
								posX+=2*squareSize;
								posY+=squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.blue);
						g.fillRect(posX, posY, squareSize, squareSize);
						g.setColor(Color.black);
						g.drawRect(posX, posY, squareSize, squareSize);
						
						//Reset Positions
						posX=X;
						posY=Y;
					}
					break;
				case 1:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								break;
							case 1:
								posX+=squareSize;
								break;
							case 2:
								posY+=squareSize;
								break;
							case 3:
								posY+=2*squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.blue);
						g.fillRect(posX, posY, squareSize, squareSize);
						g.setColor(Color.black);
						g.drawRect(posX, posY, squareSize, squareSize);
						
						//Reset Positions
						posX=X;
						posY=Y;
					}
					break;
				case 2:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								break;
							case 1:
								posX+=squareSize;
								break;
							case 2:
								posX+=2*squareSize;
								break;
							case 3:
								posX+=2*squareSize;
								posY+=squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.blue);
						g.fillRect(posX, posY, squareSize, squareSize);
						g.setColor(Color.black);
						g.drawRect(posX, posY, squareSize, squareSize);
						
						//Reset Positions
						posX=X;
						posY=Y;
					}
					break;
				case 3:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								posY+=2*squareSize;
								break;
							case 1:
								posX+=squareSize;
								posY+=2*squareSize;
								break;
							case 2:
								posX+=squareSize;
								posY+=squareSize;
								break;
							case 3:
								posX+=squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.blue);
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
			int rot = rotate %2;
			
			switch(rot)
			{
				case 0:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								break;
							case 1:
								posX+=squareSize;
								break;
							case 2:
								posX+=squareSize;
								posY+=squareSize;
								break;
							case 3:
								posX+=2*squareSize;
								posY+=squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.pink);
						g.fillRect(posX, posY, squareSize, squareSize);
						g.setColor(Color.black);
						g.drawRect(posX, posY, squareSize, squareSize);
						
						//Reset Positions
						posX=X;
						posY=Y;
					}
					break;
				case 1:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								posX+=squareSize;
								break;
							case 1:
								posX+=squareSize;
								posY+=squareSize;
								break;
							case 2:
								posY+=squareSize;
								break;
							case 3:
								posY+=2*squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.pink);
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
			
			int rot = 0;
					
			switch(rot)
			{
				case 0:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								posX+=2*squareSize;
								break;
							case 1:
								posX+=squareSize;
								break;
							case 2:
								posX+=squareSize;
								posY+=squareSize;
								break;
							case 3:
								posY+=squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.yellow);
						g.fillRect(posX, posY, squareSize, squareSize);
						g.setColor(Color.black);
						g.drawRect(posX, posY, squareSize, squareSize);
						
						//Reset Positions
						posX=X;
						posY=Y;
					}
					break;
				case 1:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								break;
							case 1:
								posY+=squareSize;
								break;
							case 2:
								posX+=squareSize;
								posY+=squareSize;
								break;
							case 3:
								posX+=squareSize;
								posY+=2*squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.yellow);
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
			
			int rot = rotate %2;
					
			switch(rot)
			{
				case 0:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								break;
							case 1:
								posX+=squareSize;
								break;
							case 2:
								posX+=2*squareSize;
								break;
							case 3:
								posX+=3*squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.orange);
						g.fillRect(posX, posY, squareSize, squareSize);
						g.setColor(Color.black);
						g.drawRect(posX, posY, squareSize, squareSize);
						
						//Reset Positions
						posX=X;
						posY=Y;
					}
					break;
				case 1:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								break;
							case 1:
								posY+=squareSize;
								break;
							case 2:
								posY+=2*squareSize;
								break;
							case 3:
								posY+=3*squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.orange);
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
			
			switch(rotate)
			{
				case 0:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								posY+=squareSize;
								break;
							case 1:
								posY+=squareSize;
								posX+=squareSize;
								break;
							case 2:
								posX+=2*squareSize;
								posY+=squareSize;
								break;
							case 3:
								posX+=squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.cyan);
						g.fillRect(posX, posY, squareSize, squareSize);
						g.setColor(Color.black);
						g.drawRect(posX, posY, squareSize, squareSize);
						
						//Reset Positions
						posX=X;
						posY=Y;
					}
					break;
				case 1:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								break;
							case 1:
								posY+=squareSize;
								break;
							case 2:
								posY+=2*squareSize;
								break;
							case 3:
								posX+=squareSize;
								posY+=squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.cyan);
						g.fillRect(posX, posY, squareSize, squareSize);
						g.setColor(Color.black);
						g.drawRect(posX, posY, squareSize, squareSize);
						
						//Reset Positions
						posX=X;
						posY=Y;
					}
					break;
				case 2:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								break;
							case 1:
								posX+=squareSize;
								break;
							case 2:
								posX+=2*squareSize;
								break;
							case 3:
								posX+=squareSize;
								posY+=squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.cyan);
						g.fillRect(posX, posY, squareSize, squareSize);
						g.setColor(Color.black);
						g.drawRect(posX, posY, squareSize, squareSize);
						
						//Reset Positions
						posX=X;
						posY=Y;
					}
					break;
				case 3:
					//This for loop creates an individual square each iteration
					for(int i=0;i<4;i++)
					{
						//Update current square's condition
						switch(i)
						{
							case 0:
								posX+=squareSize;
								break;
							case 1:
								posX+=squareSize;
								posY+=squareSize;
								break;
							case 2:
								posY+=squareSize;
								break;
							case 3:
								posX+=squareSize;
								posY+=2*squareSize;
								break;
						}
						
						//DrawSquare
						g.setColor(Color.cyan);
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
		
		//This function draws the custom block
		public void drawCustom(Graphics g)
		{
			int posX = X, posY = Y;
			
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3;j++)
				{
					if(customGrid[i][j])
					{
						posX = X + i*squareSize;
						posY = Y + j*squareSize;
						
						//System.out.println("PosX = " + posX + " posY = " + posY);
						
						//DrawSquare
						g.setColor(color);
						g.fillRect(posX, posY, squareSize, squareSize);
						g.setColor(Color.black);
						g.drawRect(posX, posY, squareSize, squareSize);
					}

				}
			}
		}
}