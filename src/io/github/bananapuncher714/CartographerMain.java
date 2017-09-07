/**
 * Contains the necessary getters and setters for other plugins to hook into.
 * 
 * @author BananaPuncher714
 */

package io.github.bananapuncher714;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import io.github.bananapuncher714.MapManager.CursorSelector;
import io.github.bananapuncher714.MapManager.MapShader;
import io.github.bananapuncher714.map.ColorMixer;
import io.github.bananapuncher714.map.CustomRenderer;
import io.github.bananapuncher714.map.RealWorldCursor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;
import org.bukkit.plugin.java.JavaPlugin;

public class CartographerMain extends JavaPlugin implements Listener {
	HashMap< Material, HashMap< Byte, Color > > mapColors = new HashMap< Material, HashMap< Byte, Color > >();
	HashSet< MapShader > shaders = new HashSet< MapShader >();
	ArrayList< String > colorfiles = new ArrayList< String >();
	HashSet< Material > skipBlocks = new HashSet< Material >();
	private int state = -1;
	private int zstate = -1;
	private int x = 0, y = 0, waters, selectorLoadDelay;
	private byte[][] map = new byte[ 1024 ][ 1024 ];
	private boolean done = true;
	private boolean centerChunk = true;
	private boolean defrend = true;
	private boolean showPlayer = true;
	private int mapLoadSpeed = 16;
	private MapCursor.Type defPointer = MapCursor.Type.WHITE_POINTER;
	private World w = Bukkit.getWorld( "world" );
	private Color defc = new Color( 255, 0, 255 );
	private MapManager mm;
	
	private static CartographerMain main;
	
	@Override
	public void onEnable() {
		main = this;
		saveDefaultConfig();
		loadConfig();
		mm = new MapManager( this );
		Bukkit.getPluginManager().registerEvents( mm, this );
		Bukkit.getPluginManager().registerEvents( this, this );
		
		// These three runnables are an example of how you can use the methods in this plugin
		
		// The first one shows how you can use this like a "world border" to determine if someone is on the map or not
//		Bukkit.getScheduler().scheduleSyncRepeatingTask( this, new Runnable() {
//			@Override
//			public void run() {
//				for ( Player p : Bukkit.getOnlinePlayers() ) {
//					if ( !onMap( p.getLocation() ) ) {
//						p.sendMessage( "Return to the map now!!" );
//					}
//				}
//			}
//		}, 120, 20 );

		// This one gets all monsters near the player and the player only, and displays them as red pointers
		// This could be quite useful for treasure hunts
//		mm.registerCursorSelector( "none", new CursorSelector() {
//			@Override
//			public ArrayList< RealWorldCursor > getCursors( Player p ) {
//				ArrayList< RealWorldCursor > cursors = new ArrayList< RealWorldCursor >();
//				for ( Entity ent : p.getNearbyEntities( 10, 5, 10 ) ) {
//					if ( ent instanceof Monster ) {
//						cursors.add( new RealWorldCursor( ent.getLocation(), MapCursor.Type.RED_POINTER, true ) );
//					}
//				}
//				return cursors;
//			}
//		} );

		// The last one here determines what pixels should be shaded what color.
		// Keep in mind, though, that the changes made by this will be visible by everyone.
//		registerShader( new MapShader() {
//			@Override
//			public Color shadeLocation( Location location, Color color ) {
//				if ( location.distance( new Location( w, x, location.getY(), y ) ) <= 12.5 ) {
//					return ColorMixer.tintColor( ColorMixer.Tint.YELLOW, color, 30 );
//				}
//				return color;
//			}
//			
//		} );
		
		// This is for loading the map!!
		Bukkit.getScheduler().scheduleSyncRepeatingTask( this, new Runnable() {
			@Override
			public void run() {
				if ( !( x == 0 ) && !( y == 0 ) && !done ) {
					state = ( state + 1 ) % mapLoadSpeed;
					if ( state == 0 ) zstate = ( zstate + 1 ) % mapLoadSpeed;
					for ( int xi = 0; xi < getXLength(); xi = xi + mapLoadSpeed ) {
						for ( int z = 0; z < getYLength(); z = z + mapLoadSpeed ) {
							Location mainl = new Location( w, xi + x - ( getXLength() / 2 ) + state, 1, y + z - ( getYLength() / 2 ) + zstate );
							// Here we get the color of the block
							Color tempColor = getColorAt( mainl );
							if ( xi + state < getXLength() && z + zstate < getYLength() )
								map[ ( xi + state ) ][ ( z + zstate ) ] = MapPalette.matchColor( tempColor );
						}
					}
					if ( zstate == mapLoadSpeed - 1 && state == mapLoadSpeed - 1 ) {
						done = true;
						for ( Player p : Bukkit.getOnlinePlayers() ) {
							if ( p.hasPermission( "cartographer.admin" ) ) p.sendMessage( ChatColor.AQUA + "Map rendering finished!" );
						}
						state = -1;
						zstate = -1;
					}
				}
			}
		} , 100, 2 );
	}
	
	@Override
	public void onDisable() {
		saveData();
		if ( done ) saveMap();
	}
	
	// Here begins a LOT of methods to load and save data from files...
	
	private void loadConfig() {
		FileConfiguration c = getConfig();
		mapLoadSpeed = c.getInt( "map-load-speed" );
		selectorLoadDelay = c.getInt( "selector-load-delay" );
		centerChunk = c.getBoolean( "center-chunk" );
		loadPresetList();
		int height = c.getInt( "map-height" );
		int width = c.getInt( "map-width" );
		map = new byte[ width ][ height ];
		loadMapSettings();
		loadAllColors( true );
		loadAllTransparentBlocks( true );
		loadData();
	}
	
	private void loadMapSettings() {
		String[] s = getConfig().getString( "default-color" ).split( "\\D+" );
		defc = new Color( Integer.parseInt( s[ 0 ] ), Integer.parseInt( s[ 1 ] ), Integer.parseInt( s[ 2 ] ) );
		waters = getConfig().getInt( "water-shading" );
		defrend = getConfig().getBoolean( "default-render" );
		showPlayer = getConfig().getBoolean( "show-player" );
	}
	
	/**
	 * Loads the list of presets from the config.
	 */
	public void loadPresetList() {
		colorfiles = new ArrayList< String >( getConfig().getStringList( "presets" ) );
	}
	
	/**
	 * Takes the files specified by the preset list and the config and extract the transparent blocks
	 * Calling this will re-build the transparent block list.
	 * 
	 * @param clear
	 * Whether or not to clear the previous list before re-loading the new ones.
	 */
	public void loadAllTransparentBlocks( boolean clear ) {
		loadTransparentBlocks( getConfig(), clear );
		new File( getDataFolder() + File.separator + "presets" + File.separator ).mkdirs();
		for ( String name : colorfiles ) {
			File colorConfig = new File( getDataFolder() + File.separator + "presets" + File.separator + name );
			if ( !colorConfig.exists() ) {
				getLogger().warning( "preset '" + name + "' does not exist! Some blocks may not appear properly!" );
				continue;
			}
			FileConfiguration cc = YamlConfiguration.loadConfiguration( colorConfig );
			if ( loadTransparentBlocks( cc, false ) ) {
				getLogger().info( "Loaded the transparent blocks of preset '" + name + "' successfully!" );
			}
		}
	}
	
	/**
	 * Loads the list of transparent blocks from a YAML file.
	 * 
	 * @param c
	 * The file that will be loaded.
	 * @param clear
	 * Whether or not the previous list of blocks will be cleared.
	 * @return
	 * Whether the file was successfully loaded; If it didn't contain the proper section it will return false
	 * This has nothing to do with whether any blocks that were loaded existed or not!
	 */
	public boolean loadTransparentBlocks( FileConfiguration c, boolean clear ) {
		if ( clear ) skipBlocks.clear();
		if ( !c.contains( "transparent-blocks" ) ) return false;
		for ( String s : c.getStringList( "transparent-blocks" ) ) {
			String sf = s;
			boolean negate = false;
			if ( s.startsWith( "-" ) ) {
				sf = s.substring( 1 );
				negate = true;
			}
			Material mat;
			try {
				mat = Material.getMaterial( Integer.parseInt( sf ) );
			} catch ( Exception e ) {
				try {
					mat = Material.getMaterial( sf );
				} catch ( Exception e2 ) {
					getLogger().warning( sf + " does not exist! Older version of Minecraft, perhaps?" );
					continue;
				}
			}
			if ( negate ) {
				if ( skipBlocks.contains( mat ) ) skipBlocks.remove( mat );
			} else {
				skipBlocks.add( mat );
			}
		}
		return true;
	}
	
	/**
	 * Loads all the colors used by the map; The colors will be fetched from the config first, then the presets.
	 * 
	 * @param clear
	 * Determines whether or not to clear the list of colors before reloading the new ones.
	 */
	public void loadAllColors( boolean clear ) {
		loadColors( getConfig(), clear );
		new File( getDataFolder() + File.separator + "presets" + File.separator ).mkdirs();
		for ( String name : colorfiles ) {
			File colorConfig = new File( getDataFolder() + File.separator + "presets" + File.separator + name );
			if ( !colorConfig.exists() ) {
				getLogger().warning( "preset '" + name + "' does not exist! Some colors may be missing!" );
				continue;
			}
			FileConfiguration cc = YamlConfiguration.loadConfiguration( colorConfig );
			if ( loadColors( cc, false ) ) {
				getLogger().info( "Loaded the colors of preset '" + name + "' successfully!" );
			}
		}
	}
	
	/**
	 * Loads all colors from a YAML file.
	 * 
	 * @param c
	 * The YAML file to load.
	 * @param clear
	 * Whether or not to clear all the previous colors loaded.
	 * @return
	 * Whether this file contained a map color section; returning true does NOT mean that some materials did not exist
	 */
	public boolean loadColors( FileConfiguration c, boolean clear ) {
		if ( clear ) mapColors.clear();
		if ( c.getConfigurationSection( "map-colors" ) == null ) return false;
		for (String s : c.getConfigurationSection( "map-colors" ).getKeys( false ) ) {
			String[] split = c.getString( "map-colors." + s ).split( "\\D+" );
			String[] matSplit = s.split( "," );
			loadColor( matSplit, split );
		}
		return true;
	}
	
	/**
	 * Loads a color for the material and data value; This does NOT refresh the map.
	 * 
	 * @param m
	 * The material to set the new color for.
	 * @param data
	 * The data value to set the new color for.
	 * @param c
	 * The color that will represent the material and data value.
	 */
	public void loadColor( Material m, byte data, Color c ) {
		Material mat = m;
		HashMap< Byte, Color > tempMap = ( HashMap< Byte, Color > ) mapColors.get( mat );
		if ( tempMap == null ) {
			tempMap = new HashMap<Byte, Color>();
			mapColors.put( mat, tempMap );
		}
		tempMap.put( data , c );
		return;
	}
	
	/**
	 * Loads a color for the material and data value, except it uses RGB values, not a color.
	 * 
	 * @param m
	 * The material to set the new color for.
	 * @param data
	 * The data value to set the new color for.
	 * @param r
	 * The RED amount of the new color.
	 * @param g
	 * The GREEN amount of the new color.
	 * @param b
	 * The BLUE amount of the new color.
	 */
	public void loadColor( Material m, byte data, int r, int g, int b ) {
		loadColor( m, data, new Color( r, g, b ) );
	}
	
	private boolean loadColor( String[] md, String[] rgb ) {
		Material mat = Material.AIR;
		try {
			mat = Material.getMaterial( Integer.parseInt( md[ 0 ] ) );
		} catch ( Exception e ) {
			try {
				mat = Material.getMaterial( md[ 0 ] );
			} catch ( Exception e2 ) {
				getLogger().warning( md[ 0 ] + " does not exist! Older version of Minecraft, perhaps?" );
				return false;
			}
		}
		loadColor( mat, Byte.parseByte( md[ 1 ] ), new Color( Integer.parseInt( rgb[ 0 ] ), Integer.parseInt( rgb[ 1 ] ), Integer.parseInt( rgb[ 2 ] ) ) );
		return true;
	}
	
	private void loadData() {
		saveResource( "data.yml", false );
		File data = new File( this.getDataFolder() + File.separator + "data.yml" );
		FileConfiguration datac = YamlConfiguration.loadConfiguration( data );
		x = datac.getInt( "x" );
		y = datac.getInt( "y" );
		w = Bukkit.getWorld( datac.getString( "world" ) );
		done = loadMap();
	}
	
	private void saveData() {
		File data = new File( this.getDataFolder() + File.separator + "data.yml" );
		FileConfiguration datac = YamlConfiguration.loadConfiguration( data );
		datac.set( "x", x);
		datac.set( "y", y );
		datac.set( "world", w.getName() );
		try {
			datac.save( data );
		} catch (IOException e) {
			System.out.println( "There was an error while trying to save the data to file!" );
		}
	}
	
	private boolean loadMap() {
		File mapFile = new File( getDataFolder(), "map.ser" );
		if ( !mapFile.exists() ) {
			getLogger().warning( "Map data not found! Reloading the map!" );
			return false;
		}
		try {
            FileInputStream fileIn = new FileInputStream( mapFile );
            ObjectInputStream in = new ObjectInputStream( fileIn );
            map = ( byte[][] ) in.readObject();
            in.close();
            fileIn.close();
            getLogger().info( "A saved copy of the map was found!" );
            return true;
        }catch(IOException i) {
            getLogger().warning( "There was an error while reading the map data on disk!" );
            return false;
        }catch(ClassNotFoundException c) {
        	getLogger().severe( "'map.ser' is not a proper map file! Reloading the map!" );
            return false;
        }
	}
	
	private void saveMap() {
		try {
            FileOutputStream fileOut = new FileOutputStream( new File( getDataFolder(), "map.ser" ) );
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject( map );
            out.close();
            fileOut.close();
            getLogger().info( "Saved map successfully to 'map.ser'" );
        }catch(IOException i) {
            getLogger().warning( "There was an error while saving the map to disk!" );
        }
	}
	
	// Finally!! We get to move onto some more basic command stuff!
	
	@Override
	public boolean onCommand( CommandSender s, Command c, String l, String[] a ) {
		if ( c.getName().equalsIgnoreCase( "setmapcenter" ) && s instanceof Player ) {
			Player p = ( Player ) s;
			if ( !p.hasPermission( "cartographer.admin" ) ) return false;
			if ( centerChunk ) {
				Chunk ch = p.getLocation().getChunk();
				x = ( ch.getX() * 16 ) + 8;
				y = ( ch.getZ() * 16 ) + 8;
				w = ch.getWorld();
			} else {
				Location ploc = p.getLocation();
				x = ploc.getBlockX();
				y = ploc.getBlockZ();
				w = p.getWorld();
			}
			for ( Player pl : Bukkit.getOnlinePlayers() ) {
				if ( pl.hasPermission( "cartographer.admin" ) ) pl.sendMessage( ChatColor.AQUA + "The map has been centered around " + x + ", " + y );
			}
			done = false;
			return false;
		} else if ( c.getName().equalsIgnoreCase( "mapreload" ) ) {
			if ( !s.hasPermission( "cartographer.admin" ) ) return false;
			for ( Player pl : Bukkit.getOnlinePlayers() ) {
				if ( pl.hasPermission( "cartographer.admin" ) ) pl.sendMessage( ChatColor.AQUA + "Reloading map..." );
			}
			refreshMap( false );
			done = false;
		}
		return false;
	}
	
	// Here are a bunch of getters and setters for external plugin use
	
	/**
	 * Fetches the main instance of this plugin
	 * 
	 * @return
	 * The instance created by the plugin manager
	 */
	public static CartographerMain getMain() {
		return main;
	}
	
	/**
	 * Gets the X value of the center of the map at the current time.
	 * 
	 * @return
	 * The value may not stay the same if the map is re-centered
	 */
	public int getCenterX() {
		return x;
	}
	
	/**
	 * Gets the Y value of the center of the map
	 * 
	 * @return
	 * The Y value of the map center.
	 */
	public int getCenterY() {
		return y;
	}
	
	/**
	 * Gets the map and all its colors; May or may not have blank spots.
	 * 
	 * @return
	 * An immutable array of colors for the map.
	 */
	public byte[][] getMap() {
		return map.clone();
	}
	
	/**
	 * Returns the world that the map represents.
	 * 
	 * @return
	 * The map world.
	 */
	public World getWorld() {
		return w;
	}
	
	/**
	 * This can only be changed in the config or using relocateMap().
	 * 
	 * @return
	 * The map width.
	 */
	public int getXLength() {
		return map.length;
	}
	
	/**
	 * This can only be changed in the config or using relocateMap().
	 * 
	 * @return
	 * The map height.
	 */
	public int getYLength() {
		return map[ 0 ].length;
	}
	
	/**
	 * Represents how long it takes for each CustomRenderer to update the selectors that it should have.
	 * 
	 * @return
	 * The delay in seconds.
	 */
	public int getSelectorLoadDelay() {
		return selectorLoadDelay;
	}
	
	/**
	 * Represents if the player wielding the map will see himself on it.
	 * 
	 * @return
	 * Wielder visibility
	 */
	public boolean showPlayer() {
		return showPlayer;
	}
	
	/**
	 * Sets if the player holding the map will see himself on it.
	 * 
	 * @param showPlayer
	 * Whether or not they will show up.
	 */
	public void showPlayer( boolean showPlayer ) {
		this.showPlayer = showPlayer;
	}
	
	/**
	 * Removes a material from the list of transparent blocks; blocks that should not be rendered when using the custom renderer.
	 * 
	 * @param material
	 * The material to remove.
	 * @return
	 * If the set contained the material to remove.
	 */
	public boolean removeTransparentBlock( Material material ) {
		if ( skipBlocks.contains( material ) ) {
			skipBlocks.remove( material );
			return true;
		}
		return false;
	}
	
	/**
	 * Adds a material to the list of transparent blocks; blocks that should not be rendered when using the custom renderer.
	 * 
	 * @param material
	 * The material to add.
	 * @return
	 * If the set already contained the material;
	 */
	public boolean addTransparetMaterial( Material material ) {
		if ( !skipBlocks.contains( material ) ) {
			skipBlocks.add( material );
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the set of materials that should not be rendered when using a custom renderer; They will simply not show up
	 * 
	 * @return
	 * An immutable set of blocks containing the materials;
	 */
	public HashSet< Material > getTransparentBlocks() {
		return new HashSet< Material >( skipBlocks );
	}
	
	/**
	 * The map cursor that will represent the player; A white pointer makes the most sense.
	 * 
	 * @return
	 * The cursor type of the default pointer.
	 */
	public MapCursor.Type getDefPointer() {
		return defPointer;
	}
	
	/**
	 * Sets the default pointer that is used to represent the wielder on a map.
	 * 
	 * @param defPointer
	 * The cursor type that should be set.
	 * @return
	 * The old cursor type that was replaced.
	 */
	public MapCursor.Type setDefPointer( MapCursor.Type defPointer ) {
		MapCursor.Type old = defPointer;
		this.defPointer = defPointer;
		return old;
		
	}
	
	/**
	 * Gets the map manager; used to register CursorSelectors and get custom maps.
	 * 
	 * @return
	 * The MapManager instance created by this plugin
	 */
	public MapManager getMapManager() {
		return mm;
	}
	
	// These are multipurpose and not normally used unless you are really manipulating data, like land marking
	/**
	 * Determines whether or not a location is on the map; It includes world detection as well.
	 * 
	 * @param location
	 * The location to check.
	 * @return
	 * If the location is on the map.
	 */
	public boolean onMap( Location location ) {
		int xdistance = location.getBlockX() - x;
		int zdistance = location.getBlockZ() - y;
		return ( location.getWorld() == w && Math.abs( xdistance ) <= ( getXLength() / 2 ) && Math.abs( zdistance ) <= ( getYLength() / 2 ) );
	}
	
	/**
	 * Updates three locations on the map; The one above the location and the one below it; Used to redraw a block with proper shading.
	 * 
	 * @param loc
	 * The location to update; will skip if it is not on the map.
	 */
	public void updateLocation( Location loc ) {
		for ( int i = -1; i < 2; i++ ) {
			Location l = loc.clone().add( 0, 0, i );
			int xdistance = l.getBlockX() - x;
			int zdistance = l.getBlockZ() - y;
			if ( !onMap( l ) ) continue;
			Color tempColor = getColorAt( l );
			map[ ( getXLength() / 2 ) + xdistance ][ ( getYLength() / 2 ) + zdistance ] = MapPalette.matchColor( tempColor );
		}
	}
	
	/**
	 * Updates only one location on the map; Useful for more delicate shading matters.
	 * 
	 * @param l
	 * The location to recolor.
	 */
	public void recolorLocation( Location l ) {
		int xdistance = l.getBlockX() - x;
		int zdistance = l.getBlockZ() - y;
		if ( !onMap( l ) ) return;
		Color tempColor = getColorAt( l );
		map[ ( getXLength() / 2 ) + xdistance ][ ( getYLength() / 2 ) + zdistance ] = MapPalette.matchColor( tempColor );
	}
	
	/**
	 * Reloads the settings, presets, colors, and transparent blocks, then redraw the map in that order.
	 * 
	 * @param reload
	 * Whether or not to reload the config settings and clear all colors and transparent blocks.
	 */
	public void refreshMap( boolean reload ) {
		if ( reload ) {
			loadMapSettings();
		}
		loadPresetList();
		loadAllColors( reload );
		loadAllTransparentBlocks( reload );
		done = false;
		state = -1;
		zstate = -1;
	}
	
	/**
	 * Recenters the entire map and resets the width, height, and world.
	 * 
	 * @param centerX
	 * The X value of center of the map
	 * @param centerY
	 * The Y value of the center of the map
	 * @param width
	 * The new width of the map
	 * @param height
	 * The new height of the map
	 * @param world
	 * The new world the map will represent
	 * @return
	 * The old map
	 */
	public byte[][] relocateMap( int centerX, int centerY, int width, int height, World world ) {
		x = centerX;
		y = centerY;
		w = world;
		byte[][] savedMap = map.clone();
		map = new byte[ width ][ height ];
		refreshMap( false );
		return savedMap;
	}
	
	/**
	 * This gets the color of a block at any location; it does not have to be on the map.
	 * 
	 * @param location
	 * The location to get the color of
	 * @return
	 * The color that represents the highest block with proper shading.
	 */
	public Color getColorAt( Location location ) {
		Block b;
		int abovey;
		if ( defrend ) {
			b = location.getWorld().getHighestBlockAt( location.getBlockX(), location.getBlockZ() ).getLocation().clone().subtract( 0, 1, 0 ).getBlock();
			abovey = location.getWorld().getHighestBlockYAt( location.getBlockX(), location.getBlockZ() - 1 ) - 1;
		} else {
			b = Util.getHighestBlockAt( location, skipBlocks );
			abovey = Util.getHighestBlockAt( location.clone().subtract( 0, 0, 1 ), skipBlocks ).getY();
		}
		Material mat = b.getType();
		byte data = b.getData();
		Color c = getColor( mat, data );
		Color tempColor = c;
		if ( tempColor == null ) tempColor = defc;
		if ( b.getY() < abovey ) {
			tempColor = ColorMixer.brightenColor( tempColor, -30 );
		} else if ( b.getY() == abovey ) {
			tempColor = ColorMixer.brightenColor( tempColor, -10 );
		}
		if ( b.getType() == Material.WATER || b.getType() == Material.STATIONARY_WATER ) {
			tempColor = ColorMixer.brightenColor( tempColor, - Math.min( 100, ( waters * Util.getWaterDepth( b.getLocation() ) ) / 100 ) );
		}
		return shadeLocation( location, tempColor );
	}
	
	/**
	 * Sets the color at the location provided; does not do anything if the location is not on the map.
	 * 
	 * @param location
	 * The location to set the color.
	 * @return
	 * The previous color at the location provided; returns null if the location is not on the map.
	 */
	public Color setColorAt( Location location) {
		int xdistance = location.getBlockX() - x;
		int zdistance = location.getBlockZ() - y;
		if ( location.getWorld() != w || Math.abs( xdistance ) > ( getXLength() / 2 ) || Math.abs( zdistance ) > ( getYLength() / 2 ) ) return null;
		Color tempColor = getColorAt( location );
		Color bc = new Color( map[ ( getXLength() / 2 ) + xdistance ][ ( getYLength() / 2 ) + zdistance ] );
		map[ ( getXLength() / 2 ) + xdistance ][ ( getYLength() / 2 ) + zdistance ] = MapPalette.matchColor( tempColor );
		return bc;
	}
	
	/**
	 * Shades the color at a location using all MapShaders registered
	 * 
	 * @param location
	 * The location to shade
	 * @param color
	 * The color of the location
	 * @return
	 * The shaded color
	 */
	public Color shadeLocation( Location location, Color color ) {
		Color c = color;
		for ( MapShader shader : shaders ) {
			c = shader.shadeLocation( location, c );
		}
		return c;
	}
	
	/**
	 * Gets the color loaded using a material and byte as keys.
	 * 
	 * @param mat
	 * The material to use
	 * @param data
	 * The data value to use
	 * @return
	 * The color of the material and data
	 */
	public Color getColor( Material mat, byte data ) {
		Color c = null;
		if ( mapColors.get( mat ) != null) {
			c = ( Color ) ( mapColors.get(mat) ).get( Byte.valueOf( data ) );
			if (c == null) {
				c = ( Color ) ( mapColors.get(mat) ).get(Byte.valueOf( ( byte ) - 1 ) );
			}
		}
		return c;
	}
	
	@EventHandler( priority = EventPriority.MONITOR )
	public void onBlockBreakEvent( BlockBreakEvent e ) {
		if ( e.isCancelled() ) return;
		Bukkit.getScheduler().runTask( this, new Runnable() {
			@Override
			public void run() {
				updateLocation( e.getBlock().getLocation() );
			}
		} );
	}
	
	@EventHandler( priority = EventPriority.MONITOR )
	public void onBlockBreakEvent( BlockPlaceEvent e ) {
		if ( e.isCancelled() ) return;
		Bukkit.getScheduler().runTask( this, new Runnable() {
			@Override
			public void run() {
				updateLocation( e.getBlock().getLocation() );
			}
		} );
	}
	
	/**
	 * Gets an immutable map of all the colors set for each material and data 
	 * @return
	 * The colors mapped out
	 */
	public HashMap< Material, HashMap< Byte, Color > > getColorMap() {
		return new HashMap< Material, HashMap< Byte, Color > >( mapColors );
	}
	
	public void registerShader( MapShader shader ) {
		shaders.add( shader );
	}
}
