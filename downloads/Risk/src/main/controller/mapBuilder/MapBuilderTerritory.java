package main.controller.mapBuilder;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Ben Owen
 *
 */
public class MapBuilderTerritory{
	private ArrayList<Point> points;
	private int rgbColourId, mapHeight;
	private Point leftMost, rightMost, upMost, downMost;

	/**
	 * @param rgbColourId - the RGB colour of the painted map region. 
	 * @param firstPoint - the first point (x, y) found that defines this region
	 */
	public MapBuilderTerritory(int rgbColourId, Point firstPoint, int height) {
		points = new ArrayList<Point>();
		this.rgbColourId = rgbColourId;
		this.mapHeight = height;
		addFirstPoint(firstPoint);
	}

	/**
	 * The first point (x and y coord) added needs to be added with this method.
	 * @param firstPoint
	 */
	private void addFirstPoint(Point firstPoint){
		//set the left, right, up and down most points equal to the first point added
		leftMost = rightMost = upMost = downMost = new Point(firstPoint.x, (mapHeight - firstPoint.y - 1));
		addPoint(firstPoint);
	}

	/**
	 * Gets the rgb of this territory. Rgb is used as its ID
	 * @return
	 */
	public int getId() {
		return rgbColourId;
	}

	public void addPoint(Point point) {
		point = new Point(point.x, (mapHeight - point.y - 1));
		points.add(point);
		checkAgainstCurrentPixels(point);
	}

	/**
	 * @return the origin of this territory
	 */
	public Point getOrigin(){
		return new Point(leftMost.x, upMost.y);
	}

	/**
	 * the matrix returned is in reverse i.e. x is where y should be and y is where x should be. 
	 * i.e. int[y][x]. 
	 *
	 * @return matrix of 0's and 1's of the shape (1's being the shape and 0's not being the shape) -
	 *  dimensions are: int[][] matrix = new int[getHeight()][getWidth()];
	 */
	public int[][] getMatrix(){
		//the array the matrix will be stored in
		int[][] matrix = new int[getHeight()][getWidth()];

		//builds the matrix from all the existing points in this shape
		for(Point point : points){
			matrix[point.y - getOrigin().y][point.x - getOrigin().x] = 1;
		}
		return matrix;
	}

	/**
	 * looks to see if the new point added is further to the left,right or higher and lower than the left most up most ect. 
	 * Values here are used to create the boundary box later on
	 * @param newPoint - the point that has been found and added
	 */
	public void checkAgainstCurrentPixels(Point newPoint){
		//if a new point is further left than the left most, then it is now the new left most point
		if(newPoint.x < leftMost.x){
			leftMost = newPoint;
		}

		//if a new point is further right than the right most, then it is now the new right most point
		if(newPoint.x > rightMost.x){
			rightMost = newPoint;
		}

		//if a new point is further up than the up most, then it is now the new up most point
		if(newPoint.y < upMost.y){
			upMost = newPoint;
		}

		//if a new point is further down than the down most, then it is now the new down most point
		if(newPoint.y > downMost.y){
			downMost = newPoint;
		}
	}

	/**
	 * @return the width of this shape
	 */
	public int getWidth(){
		return (rightMost.x  - leftMost.x) + 1;
	}

	/**
	 * @return the height of this shape
	 */
	public int getHeight(){
		return (downMost.y - upMost.y) + 1;
	}

	/**
	 * @return the number of points in this shape
	 */
	public int getSize() {
		return points.size();
	}
} 
