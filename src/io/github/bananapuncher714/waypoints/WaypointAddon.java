package io.github.bananapuncher714.waypoints;

import java.io.File;

import io.github.bananapuncher714.cartographer.map.core.BorderedMap;
import io.github.bananapuncher714.cartographer.objects.Module;
import io.github.bananapuncher714.waypoints.WaypointViewer.DisplayType;

public class WaypointAddon extends Module {
	@Override
	public void load( BorderedMap map, File data ) {
	}
	
	@Override
	public void unload() {
	}
	
	public int maxWaypoints() {
		return 0;
	}
	
	public double getDefaultRange() {
		return 0;
	}
	
	public boolean showDiscoverMessage() {
		return false;
	}
	
	public DisplayType getDefaultDiscoverDisplay() {
		return null;
	}
	
	public File getDataFolder() {
		return null;
	}
	
	public WaypointManager getManager() {
		return null;
	}
	
	public WaypointTextSelector getTextSelector() {
		return null;
	}
}
