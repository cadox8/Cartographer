/**
 * A wrapper used to identify a real world location, MapCursor type, and whether to hide when out of bounds.
 * 
 * @author BananaPuncher714
 */
package io.github.bananapuncher714.cartographer.map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor;

import io.github.bananapuncher714.cartographer.map.interactive.PlayerRunnable;

public class RealWorldCursor {
	/**
	 * Constructor for a RealWorldCursor
	 * 
	 * @param l
	 * The current location of whatever the cursor is tracking
	 * @param type
	 * The MapCursor type that will represent this RealWorldCursor
	 * @param hideWhenOOB
	 * Whether or not to hide this cursor when it is not on the map.
	 */
	public RealWorldCursor( Location l, MapCursor.Type type, boolean hideWhenOOB ) {
	}

	/**
	 * Gets the location of the tracked location
	 * 
	 * @return
	 * The real world location of the location; Does not mean location on the map
	 */
	public Location getLocation() {
		return null;
	}

	/**
	 * Gets the MapCursor type of the cursor
	 * 
	 * @return
	 * Returns the type that will be displayed on the map
	 */
	public MapCursor.Type getType() {
		return null;
	}
	
	/**
	 * Out Of Bounds means when the cursor is not directly on the map.
	 * 
	 * @return
	 * Gets if this cursor should be hidden when out of bounds.
	 */
	public boolean hideWhenOOB() {
		return false;
	}
	
	public void setHoverAction( PlayerRunnable action ) {
	}
	
	public void setActivateAction( PlayerRunnable action ) {
	}
	
	public boolean executeHover( Player player, Object... objects ) {
		return false;
	}
	
	public boolean executeActivate( Player player, Object... objects ) {
		return false;
	}
}