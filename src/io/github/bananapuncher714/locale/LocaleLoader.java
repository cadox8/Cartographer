package io.github.bananapuncher714.locale;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public final class LocaleLoader {
	
	/**
	 * Loads all the locale files from the directory and returns a set of them
	 * 
	 * @param directory
	 * The directory to load them from
	 * @return
	 * The locales loaded from that directory
	 */
	public static Map< String, Locale > loadLocales( File directory ) {
		return null;
	}
	
	public static Locale loadLocale( File localeFile ) {
		return null;
	}
	
	public static void savePreference( UUID uuid, Locale locale, File file ) {
	}
	
	public static String loadPreference( UUID uuid, File file ) {
		return null;
	}
}
