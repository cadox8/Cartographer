/**
 * The base of Cartographer
 * 
 * @author BananaPuncher714
 */

package io.github.bananapuncher714.cartographer;

import org.bukkit.event.Listener;
import org.bukkit.map.MapFont;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.bananapuncher714.cartographer.map.core.BorderedMap;
import io.github.bananapuncher714.locale.LocaleAddon;

public class CartographerMain extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
	}
	
	@Override
	public void onDisable() {
	}

	public void reload() {
	}
	
	public void saveConfig() {
	}

	public static CartographerMain getMain() {
		return null;
	}
	
	public int getMapLoadSpeed() {
		return 0;
	}
	
	public int getSelectorLoadDelay() {
		return 0;
	}
	
	public MapFont getFont( String id ) {
		return null;
	}
	
	public LocaleAddon getLocaleAddon() {
		return null;
	}
	
	public byte[][] getMissingMap() {
		return null;
	}
	
	public BorderedMap createNewMap( boolean register, String id ) {
		return null;
	}
	
	public BorderedMap createNewMap( boolean register, String id, String name ) {
		return null;
	}
}
