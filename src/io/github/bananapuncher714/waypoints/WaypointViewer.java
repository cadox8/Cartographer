package io.github.bananapuncher714.waypoints;

import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class WaypointViewer {
	public enum DisplayType {
		HIDDEN( "Hidden" ), VISIBLE( "Visible" ), HIGHLIGHTED( "Highlighted");
		
		String name;
		
		DisplayType( String name ) {
			this.name = name;
		}
		
		public String getDisplayName() {
			return name;
		}
	}
	
	protected WaypointViewer( WaypointAddon addon, Player player ) {
	}
	
	protected WaypointViewer( WaypointAddon addon, FileConfiguration config ) {
	}
	
	public UUID getUUID() {
		return null;
	}
	
	public String getName() {
		return null;
	}
	
	public Map< Waypoint, DisplayType > getWaypoints() {
		return null;
	}
	
	public Map< Waypoint, DisplayType > getShared() {
		return null;
	}
	
	public Map< UUID, DisplayType > getPublic() {
		return null;
	}
	
	public void refreshPublic() {
	}
}
