package main.controller.mapBuilder;

import java.awt.Point;
import java.util.LinkedList;

/**
 * 	This class finds the outline of a shape and stores the vertices into an one dimensional array of floats
 *	The shape needs to be passed in as a 2D int array with 1's representing pixels in a shape and 0's as not apart of the shape (0's are the void if you like)
 *
 *	The shape needs to be formated so that at least one pixel can be found on the top row of the matrix 
 *	i.e. For all possible values of x, the coordinates (x, 0) there needs to be one pixel 
 *	Note (for better results the same should be true for all possible values of y in (0, y) 

 * will return a list of vertices to make polygons
 *
 * @author Ben Owen
 */
public class MatrixToPolygon {

	private int[][] matrix;

	private int cursorX, cursorY;

	private LinkedList<Point> points;

	private enum Direction {MOVELEFT, MOVERIGHT, MOVEUP, MOVEDOWN, MOVEUPRIGHT, MOVEUPLEFT, MOVEDOWNRIGHT, MOVEDOWNLEFT};
	private Direction direction;

	public MatrixToPolygon(int[][] matrix) {
		this.matrix = matrix;
		points = new LinkedList<Point>();
		run();
	}

	public void run() {
		findVertices();
	}

	/**
	 * Main method loop to find the outer vertices of the matrix
	 */
	private void findVertices(){
		cursorX = cursorY = 0;
		boolean done = false;
		direction = Direction.MOVERIGHT;

		while(!hits()){
			move();
		}
		addVertices();

		while(!done){
			if(lookToTheLeftForPixels()){
				addVertices();
				move();
			}else if(lookForward()){
				move();
			}else if(LookToTheRightForPixels()){
				addVertices();
				move();
			}

			if(reachedStart()){
				done = true;
			}
		}
	}

	/**
	 * @return vertices of the matrix that was passed in reverse order (anti clockwise vertices)
	 */
	public float[] getVertices(){
		float[] vertices = new float[points.size() * 2];

		int index = points.size() * 2 -1;
		for(Point point : points) {
			vertices[index] = point.y;
			index--;
			vertices[index] = point.x;
			index--;
		}
		return vertices;
	}

	/**
	 * checks to see if the first pixel of the image found is the same as the last one found. 
	 * Essentially, this returns true if the polygon is a complete shape 
	 * @return
	 */
	private boolean reachedStart(){
		if(points.getFirst().x == points.getLast().x && points.getFirst().y == points.getLast().y) {
			if(points.size() > 2) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Moves the cursor in the current direction of travel	 * 
	 */
	private void move() {
		switch (direction) {
			case MOVELEFT:
				moveLeft();
				break;
			case MOVERIGHT:
				moveRight();
				break;
			case MOVEUP:
				moveUp();
				break;
			case MOVEDOWN:
				moveDown();
				break;
			case MOVEUPRIGHT:
				moveUpRight();
				break;
			case MOVEUPLEFT:
				moveUpLeft();
				break;
			case MOVEDOWNRIGHT:
				moveDownRight();
				break;
			case MOVEDOWNLEFT:
				moveDownLeft();
				break;
		}
	}

	/**
	 * looks (relative to the direction of travel) left, then up
	 * will return new direction or current direction
	 */
	private boolean lookToTheLeftForPixels(){
		for(int i = -2; i < 0; i++){
			if(searchArea(i)){
				return true;
			}
		}
		return false;
	}

	/**
	 * looks (relative to the direction of travel) looks 45 degrees right, and 
	 * Continuous to look an additional 45 degrees to the right until looking a 
	 * 180 degrees of current direction of travel
	 */
	private boolean LookToTheRightForPixels(){
		//check from up right. continue clock wise
		for(int i = 1; i < 5; i++){
			if(searchArea(i)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Looks ahead in the direction of travel
	 * @return true if a pixel is found 
	 */
	private boolean lookForward(){
		switch (direction) {
			case MOVELEFT:
				return lookLeft();
			case MOVERIGHT:
				return lookRight();
			case MOVEUP:
				return lookUp();
			case MOVEDOWN:
				return lookDown();
			case MOVEUPRIGHT:
				return lookUpRight();
			case MOVEUPLEFT:
				return lookUpLeft();
			case MOVEDOWNRIGHT:
				return lookDownRight();
			case MOVEDOWNLEFT:
				return lookDownLeft();
		}
		return false;
	} 
	
	/* <---*collapsed*    [COMPASS MAPPING + LOGIC ]   
	 * 
	 *						1 					
	 * 			 	   2	^	 8
	 * 					\	|   /
	 * 					 \	|  /
	 * 					  \	| /
	 * 					   \|/
	 * 			  3<----------------->7
	 *					   /|\
	 * 					  / | \
	 * 				     /  |  \
	 * 					/	|   \
	 *                 4    v    6
	 *						5 
	 * 
	 * 				^^^ this is how the compass is mapped
	 * 				The offset should deal with mapping current direction onto the compass
	 * 
	 * anti clock wise check:
	 * 		moving <direction>
	 * 			> first <direction>(anti clockwise by 45 degrees) - pass -1 <---is the place we a currently going to look
	 * 		 	> then up <direction>(anti clockwise by 90 degrees) - pass -2
	 * 
	 * look forwards:
	 * 		> look ahead in the current direction of travel to see if there is a pixel
	 * 			> if there is move and don't save pixel location (addVertices()) 
	 * 			  (don't save because it is a straight line. Only need the beginning and end)
	 * 				
	 * clock wise check:
	 * 		<direction>
	 * 			> 1. look <direction>(clockwise by 45 degrees)- pass 1
	 * 			> 2. look <direction>(clockwise by 90 degrees) - pass 2
	 * 			> 3. ... - pass 3
	 * 			> 4. ... - pass 4 
	 * 			
	 */

	/**
	 *
	 * @param searchPhase - the iterative instance of direction: used to define the looking direction relative to direction of travel
	 * @return true if a pixel is found
	 */
	private boolean searchArea(int searchPhase){

		int offset = 0;

		switch (direction) {
			case MOVEUP:
				offset = 1;
				break;
			case MOVEUPLEFT:
				offset = 2;
				break;
			case MOVELEFT:
				offset = 3;
				break;
			case MOVEDOWNLEFT:
				offset = 4;
				break;
			case MOVEDOWN:
				offset = 5;
				break;
			case MOVEDOWNRIGHT:
				offset = 6;
				break;
			case MOVERIGHT:
				offset = 7;
				break;
			case MOVEUPRIGHT:
				offset = 8;
				break;
		}

		if(searchPhase < 0){//anti clock wise
			searchPhase = (searchPhase * -1) + offset;
			if(searchPhase > 8){
				searchPhase -= 8;
			}
		}else if(searchPhase > 0){//clock wise
			if(offset - searchPhase < 1){
				searchPhase = offset - searchPhase + 8;
			}else{
				searchPhase = offset - searchPhase;
			}
		}
		return look(searchPhase);
	}

	/**
	 * if pixel is found, the direction is changed 
	 */
	private boolean look(int phase){
		switch (phase) {
			case 1:
				if(lookUp()){
					direction = Direction.MOVEUP;
					return true;
				}
				break;
			case 2:
				if(lookUpLeft()){
					direction = Direction.MOVEUPLEFT;
					return true;
				}
				break;
			case 3:
				if(lookLeft()){
					direction = Direction.MOVELEFT;
					return true;
				}
				break;
			case 4:
				if(lookDownLeft()){
					direction = Direction.MOVEDOWNLEFT;
					return true;
				}
				break;
			case 5:
				if(lookDown()){
					direction = Direction.MOVEDOWN;
					return true;
				}
				break;
			case 6:
				if(lookDownRight()){
					direction = Direction.MOVEDOWNRIGHT;
					return true;
				}
				break;
			case 7:
				if(lookRight()){
					direction = Direction.MOVERIGHT;
					return true;
				}
				break;
			case 8:
				if(lookUpRight()){
					direction = Direction.MOVEUPRIGHT;
					return true;
				}
				break;
		}
		return false;
	}

	/**
	 * Looks at the current cursor location for a pixel
	 * @return true if there is a pixel on the current cursor position
	 */
	private boolean hits() {
		if(matrix[cursorY][cursorX] == 1) {
			return true;
		}
		return false;
	}

	/**
	 * adds the current position to the vertices array
	 */
	private void addVertices() {
		points.add(new Point(cursorX, cursorY));
	}	
	
	
	/*
	 * ------------------------------------------------------------------------------
	 * 						MOVEMENT AND LOOKING METHODS
	 * ------------------------------------------------------------------------------
	 */

	private boolean lookRight() {
		if(cursorX == matrix[0].length -1){
			return false;
		}else if(matrix[cursorY][cursorX + 1] == 1) {
			return true;
		}
		return false;
	}

	private boolean lookLeft() {
		if(cursorX == 0){
			return false;
		}else if(matrix[cursorY][cursorX - 1] == 1) {
			return true;
		}
		return false;
	}

	private boolean lookUp() {
		if(cursorY == 0){
			return false;
		}else if(matrix[cursorY - 1][cursorX] == 1) {
			return true;
		}
		return false;
	}

	private boolean lookDown() {
		if(cursorY == matrix.length - 1){
			return false;
		}else if(matrix[cursorY + 1][cursorX] == 1) {
			return true;
		}
		return false;
	}

	private boolean lookUpRight() {

		if(cursorY == 0 || cursorX == matrix[0].length -1){
			return false;
		}else if(matrix[cursorY - 1][cursorX + 1] == 1) {
			return true;
		}
		return false;
	}

	private boolean lookUpLeft() {
		if(cursorY == 0 || cursorX == 0){
			return false;
		}else if(matrix[cursorY - 1][cursorX - 1] == 1) {
			return true;
		}
		return false;
	}

	private boolean lookDownRight() {
		if(cursorY == matrix.length - 1 || cursorX == matrix[0].length -1){
			return false;
		}else if(matrix[cursorY + 1][cursorX + 1] == 1) {
			return true;
		}
		return false;
	}

	private boolean lookDownLeft() {
		if(cursorY == matrix.length -1 || cursorX == 0){
			return false;
		}else if(matrix[cursorY + 1][cursorX - 1] == 1) {
			return true;
		}
		return false;
	}

	private boolean moveRight() {
		if(cursorX < matrix[0].length -1) {
			cursorX++;
			return true;
		}
		return false;
	}

	private boolean moveLeft() {
		if(cursorX > 0) {
			cursorX--;
			return true;
		}
		return false;
	}

	private boolean moveUp() {
		if(cursorY > 0) {
			cursorY--;
			return true;
		}
		return false;
	}

	private boolean moveDown() {
		if(cursorY < matrix.length - 1) {
			cursorY++;
			return true;
		}
		return false;
	}

	private boolean moveUpRight() {
		if(cursorX < matrix[0].length -1 && cursorY > 0) {
			cursorY--;
			cursorX++;
			return true;
		}
		return false;
	}

	private boolean moveUpLeft() {
		if(cursorX > 0 && cursorY > 0) {
			cursorY--;
			cursorX--;
			return true;
		}
		return false;
	}

	private boolean moveDownRight() {
		if(cursorX < matrix[0].length -1 && cursorY < matrix.length - 1) {
			cursorY++;
			cursorX++;
			return true;
		}
		return false;
	}

	private boolean moveDownLeft() {
		if(cursorX > 0 && cursorY < matrix.length - 1) {
			cursorY++;
			cursorX--;
			return true;
		}
		return false;
	}
}
