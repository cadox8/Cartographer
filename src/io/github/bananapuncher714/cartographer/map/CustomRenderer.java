/**
 * The class that is directly responsible for drawing on the actual MapView
 * 
 * @author BananaPuncher714
 */
package io.github.bananapuncher714.cartographer.map;

import java.awt.Color;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;

import io.github.bananapuncher714.cartographer.CartographerMain;

public class CustomRenderer extends MapRenderer {
	/**
	 * Instantiates the CustomRenderer.
	 * 
	 * @param contextual
	 * Whether this will be player specific.
	 * @param p
	 * The Cartographer plugin.
	 * @param s
	 * The scale of the map.
	 * @param showPlayer
	 * Whether the viewer will show up or not.
	 * @param defPointer
	 * The default Mapcursor.Type that the player will show up as
	 * @param selectorDelay
	 * The default delay in seconds that the CursorSelectors will be re-picked from MapManager's cache of CursorSelectors.
	 */
	public CustomRenderer( boolean contextual, CartographerMain p, Scale s, int selectorDelay ) {
	}

	@Override
	public void render( MapView arg0, MapCanvas arg1, Player arg2 ) {
	}
	
	protected int[] findRelPosition( int x, int y, int a, int b ) {
		return null;
	}
	
	protected void centerPlayerCursor( MapCursor cursor, boolean overlapx, boolean overlapy, int ax, int ab, Player arg2 ) {
	}
	
	protected int getDirection( double degree ) {
		return 0;
	}
	
	public void fillMap( MapCanvas canvas, byte[][] overlay ) {
	}
	
	public void fillMap( MapCanvas canvas, Color c ) {
	}
	
	public void fillMap( MapCanvas canvas, int r, int g, int b ) {
	}
	
	public void loadCursorSelectors( Player p ) {
	}
	
	public void loadTextSelectors( Player p ) {
	}
	
	public void addActivate( UUID uuid ) {
	}
}
