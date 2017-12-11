package main.controller.mapBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * This class is a temprary map they has logic to help build a map and its territories
 * @author Ben Owen
 *
 */
public class MapBuilderMap implements Iterable<MapBuilderTerritory>{

	ArrayList<MapBuilderTerritory> regions;

	public MapBuilderMap(ArrayList<MapBuilderTerritory> regions) {
		this.regions = regions;
	}

	@Override
	public Iterator<MapBuilderTerritory> iterator() {
		return regions.iterator();
	}
}
 