/**
 * Edits simple colors.
 * 
 * @author BananaPuncher714
 */
package io.github.bananapuncher714.map;

import java.awt.Color;

public class ColorMixer {
	
	/**
	 * Tint types for tinting a color.
	 * 
	 * @author BananaPuncher714
	 */
	public enum Tint {
		RED(),
		GREEN(),
		BLUE(),
		YELLOW();
		
		Tint() {
			
		}
	}
	
	/**
	 * Brightens a given color for a percent; Negative values darken the color.
	 * 
	 * @param c
	 * The color to brighten.
	 * @param percent
	 * The percentage to brighten; Must not exceed 100 percent.
	 * @return
	 * The new brightened color.
	 */
	public static Color brightenColor( Color c, int percent ) {
		if ( percent == 0 ) return c;
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		if ( percent > 0 ) {
			int newr = r + percent * ( 255 - r ) / 100;
			int newg = g + percent * ( 255 - g ) / 100;
			int newb = b + percent * ( 255 - b ) / 100;
			return new Color( newr, newg, newb );
		}
		int newr = r + percent * r / 100;
		int newg = g + percent * g / 100;
		int newb = b + percent * b / 100;
		return new Color( newr, newg, newb );
	}

	/**
	 * Tints a given color with a certain color and percent.
	 * 
	 * @param type
	 * The tint that will be applied.
	 * @param c
	 * The color to tint.
	 * @param percent
	 * The percentage to tint the given color; Must not exceed 100 percent.
	 * @return
	 * The newly tinted color.
	 */
	public static Color tintColor( Tint type, Color c, int percent) {
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		int newr = r, newg = g, newb = b;
		if ( type == Tint.YELLOW || type == Tint.RED ) newr = r + percent * ( 255 - r ) / 100;
		if ( type == Tint.YELLOW || type == Tint.GREEN ) newg = g + percent * ( 255 - g ) / 100;
		if ( type == Tint.BLUE ) newb = b + percent * ( 255 - b ) / 100;
		return new Color( newr, newg, newb );
	}
}
