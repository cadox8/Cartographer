/**
 * Manages maps and CursorSelectors for the CustomRenderer to use
 * 
 *  @author BananaPuncher714
 */

package io.github.bananapuncher714.cartographer;

import java.util.Map;
import java.util.UUID;

import io.github.bananapuncher714.cartographer.map.core.BorderedMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;

/**
 * A Singleton class to manage all maps
 * 
 * @author BananaPuncher714
 */
public class MapManager implements Listener {
	
	public void disable() {
	}

	public MapView getMapView( BorderedMap bMap, MapView map, boolean zoom, boolean refresh ) {
		return null;
	}
	
	public MapView getMapView( BorderedMap bMap, MapView map, boolean zoom ) {
		return null;
	}
	
	public MapView getMapView( ItemStack map, boolean zoom ) {
		return null;
	}
	
	public Scale getHighestScale( BorderedMap map ) {
		return null;
	}
	
	public boolean isValidScale( BorderedMap map, Scale scale ) {
		return true;
	}
	
	public void registerBorderedMap( BorderedMap map ) {
	}
	
	public Map< UUID, BorderedMap > getBorderedMaps() {
		return null;
	}
	
	public BorderedMap getBorderedMap( UUID uuid ) {
		return null;
	}
	
	public BorderedMap getBorderedMap( String id ) {
		return null;
	}
	
	public BorderedMap getPlayerMap( Player player ) {
		return null;
	}
	
	public void setPlayerMap( Player player, BorderedMap map ) {
	}
	
	public static MapManager getInstance() {
		return null;
	}
}
