package main.controller.mapBuilder;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;

import main.controller.mapBuilder.mapBuilderExceptions.DifferentSizedImageException;
import main.model.mapModels.Continent;
import main.model.mapModels.Map;
import main.model.mapModels.Territory;

/**
 * builds a map for the game from a color encoded image
 *
 * @author Ben Owen
 *
 */
public class MapMaker {

	private Map map;
	private File imageFile, paintedImageFile;
	private BufferedImage mapImagePainted, mapImage;
	private List<MapBuilderTerritory> mBTerritories;

	// Colours that define a region i.e. its ID
	private List<Integer> existingRgbs;

	// Holds the built territories
	private List<Territory> territories;

	// Holds the built continents
	private ArrayList<Continent> continents;

	/**
	 * @param color encoded map Image file Path
	 * @param texture map file path
	 * @param number of territories
	 */
	public MapMaker(String paintedImagePath, String mapImagePath, int numberOfTerritories) {

		mBTerritories = new ArrayList<MapBuilderTerritory>();
		existingRgbs = new ArrayList<Integer>();
		
		/* load images */
		try {
			loadImages(paintedImagePath, mapImagePath);
			run();
		} catch (IIOException | DifferentSizedImageException e) {
			e.printStackTrace();
		}
	}

	/**
	 * runs necessary method to build a map
	 */
	private void run() {
		findTerritories();
		buildTerritories();
		//########Temporary##########
		hardCodedMapInfo();//#######
		//########Temporary##########
		buildMap();
	}

	private void buildMap() {
		map = new Map(continents);
	}

	/**
	 * This will set up all the adjacent territories as well as give them all names
	 * I want a UI to do this. Not this, not even a text file.
	 */
	private void hardCodedMapInfo() {
		String[][] territoryNamesAndAdjacents =
				{{"GREENLAND", "NORTHWEST TERRITORY", "ONTARIO", "QUEBEC","ICELAND"}
						,{"SIBERIA","URAL","CHINA","MONGOLIA","IRKUTSK","YAKUTSK"}
						,{"YAKUTSK", "SIBERIA", "IRKUTSK", "KAMCHATKA"}
						,{"ALASKA", "KAMCHATKA", "NORTHWEST TERRITORY", "ALBERTA"}
						,{"NORTHWEST TERRITORY", "ALASKA", "ALBERTA", "GREENLAND", "ONTARIO"}
						,{"URAL", "SIBERIA", "UKRAINE", "AFGHANISTAN","CHINA"}
						,{"KAMCHATKA", "ALASKA","YAKUTSK","IRKUTSK","JAPAN"}
						,{"SCANDINAVIA","UKRAINE","ICELAND","GREAT BRITAIN","NORTHERN EUROPE"}
						,{"UKRAINE","SCANDINAVIA","URAL","AFGHANISTAN","NORTHERN EUROPE","SOUTHERN EUROPE","MIDDLE EAST"}
						,{"ICELAND","GREENLAND","GREAT BRITAIN","SCANDINAVIA"}
						,{"QUEBEC","GREENLAND","ONTARIO","EASTERN UNITED STATES"}
						,{"ALBERTA","ALASKA","NORTHWEST TERRITORY","ONTARIO","WESTERN UNITED STATES"}
						,{"ONTARIO","GREENLAND","QUEBEC","EASTERN UNITED STATES","WESTERN UNITED STATES","ALBERTA","NORTHWEST TERRITORY"}
						,{"IRKUTSK","YAKUTSK","KAMCHATKA","MONGOLIA","SIBERIA"}
						,{"AFGHANISTAN","UKRAINE","URAL","CHINA","INDIA","MIDDLE EAST"}
						,{"GREAT BRITAIN","ICELAND","SCANDINAVIA","NORTHERN EUROPE","WESTERN EUROPE"}
						,{"NORTHERN EUROPE", "SCANDINAVIA", "UKRAINE","SOUTHERN EUROPE","WESTERN EUROPE","GREAT BRITAIN"}
						,{"MONGOLIA","KAMCHATKA","JAPAN","CHINA","URAL","SIBERIA","IRKUTSK"}
						,{"WESTERN UNITED STATES","ALBERTA","ONTARIO","EASTERN UNITED STATES","CENTRAL AMERICA"}
						,{"EASTERN UNITED STATES","QUEBEC","CENTRAL AMERICA","WESTERN UNITED STATES","ONTARIO"}
						,{"WESTERN EUROPE","GREAT BRITAIN","NORTHERN EUROPE","SOUTHERN EUROPE","NORTH AFRICA"}
						,{"SOUTHERN EUROPE","NORTHERN EUROPE","WESTERN EUROPE","UKRAINE","MIDDLE EAST","EGYPT","NORTH AFRICA"}
						,{"CHINA","URAL","SIBERIA","MONGOLIA","SIAM","INDIA","AFGHANISTAN"}
						,{"JAPAN","MONGOLIA","KAMCHATKA"}
						,{"MIDDLE EAST", "SOUTHERN EUROPE","UKRAINE","AFGHANISTAN","INDIA","EGYPT"}
						,{"CENTRAL AMERICA","WESTERN UNITED STATES","EASTERN UNITED STATES","VENEZUELA"}
						,{"INDIA","MIDDLE EAST","AFGHANISTAN","CHINA","SIAM"}
						,{"NORTH AFRICA","BRAZIL","WESTERN EUROPE","SOUTHERN EUROPE","EGYPT","EAST AFRICA","CONGO"}
						,{"EGYPT","SOUTHERN EUROPE","MIDDLE EAST","EAST AFRICA","NORTH AFRICA"}
						,{"SIAM","CHINA","INDIA","INDONESIA"}
						,{"VENEZUELA","CENTRAL AMERICA","BRAZIL","PERU"}
						,{"EAST AFRICA","EGYPT","NORTH AFRICA","CONGO","SOUTH AFRICA","MADAGASCAR"}
						,{"BRAZIL","VENEZUELA","PERU","ARGENTINA", "NORTH AFRICA"}
						,{"PERU","VENEZUELA","BRAZIL","ARGENTINA"}
						,{"CONGO","NORTH AFRICA","EAST AFRICA","SOUTH AFRICA"}
						,{"INDONESIA", "SIAM", "NEW GUINEA", "WESTERN AUSTRALIA"}
						,{"NEW GUINEA","INDONESIA","WESTERN AUSTRALIA","EASTERN AUSTRALIA"}
						,{"ARGENTINA","PERU","BRAZIL"}
						,{"SOUTH AFRICA","CONGO","EAST AFRICA","MADAGASCAR"}
						,{"EASTERN AUSTRALIA","NEW GUINEA", "WESTERN AUSTRALIA"}
						,{"WESTERN AUSTRALIA","NEW GUINEA","INDONESIA", "EASTERN AUSTRALIA"}
						,{"MADAGASCAR","EAST AFRICA","SOUTH AFRICA"}
				};


		int index = 0;

		//set the names up for the territory
		for(Territory territory : territories) {
			territory.setName(territoryNamesAndAdjacents[index][0]);
			index++;
		}

		index = 0;
		// gives each territory a list of adjacent territories	
		for(Territory territory : territories) {
			for(int i = 1; i < territoryNamesAndAdjacents[index].length; i++) {
				territory.addAdjacentTerritory(map.getTerritoryByName(territoryNamesAndAdjacents[index][i]));
			}
			index++;
		}

		//Build Continents

		String[][] continentInfo = {
				{"N America", "5", "GREENLAND", "NORTHWEST TERRITORY", "WESTERN UNITED STATES", "EASTERN UNITED STATES", "ALASKA", "QUEBEC", "CENTRAL AMERICA"},
				{"S America", "2", "BRAZIL", "PERU", "ARGENTINA", "VENEZUELA"},
				{"Africa", "3", "EAST AFRICA", 	"SOUTH AFRICA", "NORTH AFRICA", "EGYPT", "CONGO", "MADAGASCAR"},
				{"Asia", "7", "MONGOLIA", "CHINA", "JAPAN", "MIDDLE EAST", "SIBERIA", "YAKUTSK", "URAL", "KAMCHATKA", "ALBERTA", "ONTARIO", "IRKUTSK", "AFGHANISTAN", "INDIA", "SIAM"},
				{"Europe", "5", "ICELAND","GREAT BRITAIN", "NORTHERN EUROPE", "SCANDINAVIA", "UKRAINE", "WESTERN EUROPE", "SOUTHERN EUROPE"},
				{"Australia", "2", 	"EASTERN AUSTRALIA", "WESTERN AUSTRALIA", "INDONESIA", "NEW GUINEA"}};

		continents = new ArrayList<Continent>();

		// Builds each continent
		for(int i = 0; i < continentInfo.length; i++) {
			// A place to hold all the territories for a continent
			ArrayList<Territory> continentTerritories = new ArrayList<Territory>();
			//name of the continent
			String continentName = continentInfo[i][0];
			//value of the continent
			int continentValue = Integer.parseInt(continentInfo[i][1]);
			// the position and width and height of the continent
			float x, y, width, height;

			// look through all existing territories and add any that should be in this continent
			for(Territory territory : territories) {
				// loop through all the territories names in the continent info 
				for(int j = 2; i < continentInfo[i].length; j++) {
					if(territory.getName().equals(continentInfo[i][j])) {
						continentTerritories.add(territory);
					}
				}
			}

			// set the intial values to one of the existing territories in this continent
			x = continentTerritories.get(0).getX();
			y = continentTerritories.get(0).getY();

			float greatestX = x;
			Territory territoryWithGreatestX = continentTerritories.get(0);
			Territory territoryWithGreatestY = continentTerritories.get(0);
			float greatestY = y;

			// work out the x, y (position) of the continent
			// also finds the right and down most territory so we can work out the continents width
			for(Territory territory : continentTerritories) {
				if(territory.getX() < x) {
					x = territory.getX();
				}else if(territory.getX() > greatestX) {
					territoryWithGreatestX = territory;
				}

				if(territory.getY() < y) {
					y = territory.getY();
				}else if(territory.getY() > greatestY) {
					territoryWithGreatestY = territory;
				}
			}

			// work out width and height of this continent
			width = (territoryWithGreatestX.getX() + territoryWithGreatestX.getWidth()) - x;
			height = (territoryWithGreatestY.getY() + territoryWithGreatestY.getHeight()) - y;

			continents.add(new Continent(x, y, width, height, continentValue, continentTerritories, continentName));
		}

	}

	/**
	 *
	 * 1. Cuts the map texture up and puts it in a textureRegion object.
	 * 2. Sends shape matrix to be processed into polygon Vetrices     
	 * 3. Gets triangles of vertices using the EarClippingTriangulator in libgdx    
	 * 4. puts triangles, vertices and textureRegions together to make a polygonRegion    
	 * 5. The polygonRegion and other relevant data are then used to make a territory object     
	 *
	 */
	private void buildTerritories() {

		//A list of all the territories
		territories = new ArrayList<Territory>();
		//hold the vertices temararly for each polygon
		float[] vertices;
		//The map texture 
		Texture texture = new Texture(new FileHandle(imageFile));
		// The texture region (essentially an area of an image)
		TextureRegion textureRegion;
		// the combination of a polgon and a texture region
		PolygonRegion polygonRegion;
		// The triangulator for the polgons
		EarClippingTriangulator triangulator = new EarClippingTriangulator();


		for (MapBuilderTerritory mbTerritory : mBTerritories) {
			// Origin of current territory
			int originX = mbTerritory.getOrigin().x;
			int originY =  mbTerritory.getOrigin().y;
			int width = mbTerritory.getWidth();
			int height = mbTerritory.getHeight();
			short[] triangles;

			//matrix of shape of territory
			int [][] territoryMatrix = mbTerritory.getMatrix();

			// Matrix to polygon convertor. Actually gets vertices though
			MatrixToPolygon matrixToPolygon = new MatrixToPolygon(territoryMatrix);

			// get vertices of the matrix
			vertices = matrixToPolygon.getVertices();			
			
			/* Cut the image at a certain point for the texture 
			 * (TODO make some nice textures for this)*/
			textureRegion = new TextureRegion(texture, originX, originY, width, height);

			//Gets the triangles from the vertices (triangulation)
			triangles = triangulator.computeTriangles(vertices).toArray();

			// Creates a new polygon region with the vertices, texture region, computed Triangles
			polygonRegion = new PolygonRegion(textureRegion, vertices, triangles);
			
			/* adds the polygon region, sets the origins and width and height 
			 * of the new territory and adds it to the list that will eventually 
			 * make it to the main map*/
			territories.add(new Territory(originX, originY, width, height, null, polygonRegion));
		}
	}

	/**
	 * @return A map object that has been built. Will be null if no map has been built.
	 */
	public Map getBuiltMap(){
		return map;
	}

	/**
	 * Tries to loads images from there files
	 */
	private void loadImages(String paintedImagePath, String mapImagePath)
			throws IIOException, DifferentSizedImageException {

		paintedImageFile = new File(paintedImagePath);
		imageFile = new File(mapImagePath);

		try {
			mapImage = ImageIO.read(imageFile);
			mapImagePainted = ImageIO.read(paintedImageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * checks to see if one or both images are null and if they are both of the same
		 * height and width
		 */
		if (mapImage == null || mapImagePainted == null) {
			throw new IIOException("One or more of the image files did not load correctly");
		} else if (!(mapImage.getWidth() == mapImagePainted.getWidth()
				&& mapImage.getHeight() == mapImagePainted.getHeight())) {
			throw new DifferentSizedImageException("Images are not the same resolution. \nPainted Image: "
					+ mapImagePainted.getWidth() + " x " + mapImagePainted.getHeight() + "\nPlain Image: "
					+ mapImage.getWidth() + " x " + mapImage.getHeight());
		}
	}

	/**
	 * Build map from edited (painted map). Each region object found gets a list of
	 * x and y values that defines its area/position on the map. (Step 1 of
	 * mapBuilder)
	 */
	public void findTerritories() {
		int lastRgb = 0;
		int rgb = -1;//set it to non existent colour value

		// loops through every pixel in the colour encoded image. Any pixel associted with a particulat colour will be stored with corrisponding colours
		for (int y = 0; y < mapImagePainted.getHeight(); y++) {
			for (int x = 0; x < mapImagePainted.getWidth(); x++) {
				// remeber the last colour
				lastRgb = rgb;
				// get the rgb of the current pixel
				rgb = mapImagePainted.getRGB(x, y);

				// ignore white pixels. White is considered void. So paint anything white that you don't want to be a thing in the game
				if (rgb != Color.WHITE.getRGB()) {
					// if the current rgb is not the same as the last and does not already exist create a new territory with that colour id
					if (rgb != lastRgb && !existingRgbs.contains(rgb)) {
						// add another colour to the existing rgb
						existingRgbs.add(rgb);
						// create a new territory with that colour id
						mBTerritories.add(new MapBuilderTerritory(rgb, new Point(x, y), mapImagePainted.getHeight()));
					}
					addPointToTerritory(rgb, x, y);
				}
			}
		}
	}

	/**
	 * Adds an x,y coord that defines a shape to a mapTerritory object using rgb as its identifier. x and y values are 
	 * put together as a point object.
	 *
	 * @param rgb (ID)
	 * @param x - coord
	 * @param y - coord
	 */
	public void addPointToTerritory(int rgb, int x, int y) {
		getTerritoryByRgb(rgb).addPoint(new Point(x, y));
	}

	/**
	 *
	 * @param rgbId - the id/rgb of the region you want
	 * @return region - the found region
	 */
	public MapBuilderTerritory getTerritoryByRgb(int rgbId) {
		for (MapBuilderTerritory region : mBTerritories) {
			if (region.getId() == rgbId) {
				return region;
			}
		}
		return null; // should never be called. There should always be a region
	}

}
