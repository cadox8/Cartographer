package io.github.bananapuncher714.cartographer.map.core;

import java.awt.Color;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapCursor.Type;

import io.github.bananapuncher714.cartographer.objects.Module;

public class BorderedMap {
	
	public BorderedMap( File dataFolder, String name ) {
	}
	
	public BorderedMap( File dataFolder ) {
	}
	
	public void disable() {
	}
	
	public void loadPresetList() {
	}
	
	public void loadAllTransparentBlocks( boolean clear ) {
	}
	
	public boolean loadTransparentBlocks( FileConfiguration c, boolean clear ) {
		return true;
	}
	
	public void loadAllColors( boolean clear ) {
	}
	
	public boolean loadColors( FileConfiguration c, boolean clear ) {
		return true;
	}
	
	public void loadColor( Material m, byte data, Color c ) {
	}
	
	public void loadColor( Material m, byte data, int r, int g, int b ) {
	}
	
	public void saveData() {
	}
	
	public boolean loadMap( File file ) {
        return false;
	}

	public void saveMap( File file ) {
	}
	
	public Map< String, Module > getModules() {
		return null;
	}
	
	public void registerShader( MapShader shader ) {
	}
	
	public UUID getUID() {
		return null;
	}

	public File getDataFolder() {
		return null;
	}
	
	public void registerCursorSelector( String permission, CursorSelector selector ) {
	}
	
	public Map< String, CursorSelector > getCursorSelectors() {
		return null;
	}
	
	public void registerTextSelector( String permission, TextSelector selector ) {
	}
	
	public Map< String, TextSelector > getTextSelectors() {
		return null;
	}
	
	public int getCenterX() {
		return 0;
	}
	
	public int getCenterY() {
		return 0;
	}

	public byte[][] getMap() {
		return null;
	}
	
	public void setMap( byte[][] map ) {
	}
	
	public byte[][] getOverlay() {
		return null;
	}
	
	public void setOverlay( byte[][] overlay ) {
	}
	
	public void setOverlay( File file ) {
	}
	
	public World getWorld() {
		return null;
	}
	
	public int getXLength() {
		return 0;
	}
	
	public int getYLength() {
		return 0;
	}
	
	public boolean showPlayer() {
		return false;
	}
	
	public void showPlayer( boolean showPlayer ) {
	}
	
	public boolean removeTransparentBlock( Material material ) {
		return false;
	}
	
	public boolean addTransparetMaterial( Material material ) {
		return false;
	}
	
	public HashSet< Material > getTransparentBlocks() {
		return null;
	}
	
	public MapCursor.Type getDefPointer() {
		return null;
	}
	
	public String getName() {
		return null;
	}
	
	public List< String > getLore() {
		return null;
	}
	
	public String getId() {
		return null;
	}
	
	public MapCursor.Type setDefPointer( MapCursor.Type defPointer ) {
		return null;
	}
	
	public Type getCursorType() {
		return null;
	}
	
	public double getCursorRadius() {
		return 0;
	}
	
	public boolean isCursorEnabled() {
		return false;
	}
	
	public boolean isFancy() {
		return false;
	}
	
	public boolean isUpdateEnabled() {
		return false;
	}
	
	public boolean isCenterChunk() {
		return false;
	}
	
	public boolean isDoneLoadingMap() {
		return false;
	}
	
	public boolean isRenderOnChunkLoad() {
		return false;
	}
	
	public boolean onMap( Location location ) {
		return false;
	}
	
	public void updateLocation( Location loc ) {
	}

	public void recolorLocation( Location l) {
	}
	
	public void recolorChunk( Location location ) {
	}
	
	public void recolorChunk( World world, int x, int z ) {
	}
	
	public void refreshMap( boolean reload ) {
	}
	
	public byte[][] relocateMap( int centerX, int centerY, int width, int height, World world ) {
		return null;
	}
	
	public Color getColorAt( Location location ) {
		return null;
	}
	
	public Color shadeLocation( Location location, Color color ) {
		return null;
	}
	
	public Color getColor( Material mat, byte data ) {
		return null;
	}
}
