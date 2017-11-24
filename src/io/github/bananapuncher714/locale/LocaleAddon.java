package io.github.bananapuncher714.locale;

import java.io.File;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocaleAddon {
	public void load(File dataFolder) {
	}
	
	public void loadConfig( File file ) {
	}

	public void unload() {
	}
	
	public Locale getLocale( Player player ) {
		return null;
	}
	
	public String parse( CommandSender player, String identifier, Object... paramStrings ) {
		return null;
	}
	
	public void setPreference( Player player, Locale locale ) {
	}
	
	public void setPreference( Player player, String id ) {
	}
	
	public Map< String, Locale > getLocales() {
		return null;
	}
}
