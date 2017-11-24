package io.github.bananapuncher714.locale;

import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

public class Locale {
	public Locale( String id, String name, boolean color, Map< String, String > messages ) {
	}
	
	public Locale( FileConfiguration config ) {
	}
	
	public String getName() {
		return null;
	}
	
	public String getId() {
		return null;
	}
	
	public boolean useColor() {
		return false;
	}
	
	public String parse( String identifier, Object... paramStrings ) {
		return null;
	}
	
	public static Locale getDefault() {
		return null;
	}
	
	public static void setDefaultLocale( Locale locale ) {
	}
}
