package io.github.bananapuncher714;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import io.github.bananapuncher714.map.ColorMixer;
import io.github.bananapuncher714.map.CustomRenderer;

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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;
import org.bukkit.plugin.java.JavaPlugin;

public class CartographerMain extends JavaPlugin implements Listener {
	HashMap< Material, HashMap< Byte, Color > > mapColors = new HashMap< Material, HashMap< Byte, Color > >();
	private int x = 0, y = 0, waters;
	private byte[][] map = new byte[ 1024 ][ 1024 ];
	private boolean done = true;
	private boolean centerChunk = true;
	private boolean defrend = true;
	private int mapLoadSpeed = 16;
	private World w = Bukkit.getWorld( "world" );
	private Color defc = new Color( 255, 0, 255 );
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		loadConfig();
		Bukkit.getPluginManager().registerEvents( new MapManager( this ), this );
		Bukkit.getPluginManager().registerEvents( this, this );
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask( this, new Runnable() {
			int state = -1;
			int zstate = -1;
			
			@Override
			public void run() {
				if ( !( x == 0 ) && !( y == 0 ) && !done ) {
					state = ( state + 1 ) % mapLoadSpeed;
					if ( state == 0 ) zstate = ( zstate + 1 ) % mapLoadSpeed;
					for ( int xi = 0; xi < getXLength(); xi = xi + mapLoadSpeed ) {
						for ( int z = 0; z < getYLength(); z = z + mapLoadSpeed ) {
							Block b;
							int abovey;
							Location mainl = new Location( w, xi + x - ( getXLength() / 2 ) + state, 1, y + z - ( getYLength() / 2 ) + zstate );
							if ( defrend ) {
								b = w.getHighestBlockAt( mainl.getBlockX(), mainl.getBlockZ() ).getLocation().clone().subtract( 0, 1, 0 ).getBlock();
								abovey = w.getHighestBlockYAt( mainl.getBlockX(), mainl.getBlockZ() - 1 ) - 1;
							} else {
								b = Util.getHighestBlockAt( mainl );
								abovey = Util.getHighestBlockAt( mainl.clone().subtract( 0, 0, 1 ) ).getY();
							}
							Material mat = b.getType();
							byte data = b.getData();
							
							// Here we get the color of the block
							Color c = getColor( mat, data );
							
							// Use purple if there's no color for the block
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
	}
	
	public void loadConfig() {
		FileConfiguration c = getConfig();
		String[] s = c.getString( "default-color" ).split( "\\D+" );
		defc = new Color( Integer.parseInt( s[ 0 ] ), Integer.parseInt( s[ 1 ] ), Integer.parseInt( s[ 2 ] ) );
		mapLoadSpeed = c.getInt( "map-load-speed" );
		centerChunk = c.getBoolean( "center-chunk" );
		waters = c.getInt( "water-shading" );
		defrend = c.getBoolean( "default-render" );
		int height = c.getInt( "map-height" );
		int width = c.getInt( "map-width" );
		map = new byte[ width ][ height ];
		loadColors( getConfig(), true );
		loadData();
	}
	
	public void loadColors( FileConfiguration c, boolean clear ) {
		if ( clear ) mapColors.clear();
		for (String s : c.getConfigurationSection( "map-colors" ).getKeys( false ) ) {
			String[] split = c.getString( "map-colors." + s ).split( "\\D+" );
			String[] matSplit = s.split( "," );
			Material mat = Material.AIR;
			boolean skip = false;
			try {
				mat = Material.getMaterial( Integer.parseInt( matSplit[ 0 ] ) );
			} catch ( Exception e ) {
				try {
					mat = Material.getMaterial( matSplit[ 0 ] );
				} catch ( Exception e2 ) {
					getLogger().warning( matSplit[ 0 ] + " does not exist! Older version of Minecraft, perhaps?" );
					skip = true;
				}
			}
			if ( skip ) continue;
			HashMap< Byte, Color > tempMap = ( HashMap< Byte, Color > ) mapColors.get( mat );
			if ( tempMap == null ) {
				tempMap = new HashMap<Byte, Color>();
				mapColors.put( mat, tempMap );
			}
			tempMap.put( Byte.parseByte( matSplit[ 1 ] ), new Color(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
		}
	}
	
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
			loadColors( getConfig(), false );
			done = false;
		}
		return false;
	}
	
	public int getCenterX() {
		return x;
	}
	
	public int getCenterY() {
		return y;
	}
	
	public byte[][] getMap() {
		return map;
	}
	
	public World getWorld() {
		return w;
	}
	
	public int getXLength() {
		return map.length;
	}
	
	public int getYLength() {
		return map[ 0 ].length;
	}
	
	private void loadData() {
		saveResource( "data.yml", false );
		File data = new File( this.getDataFolder() + File.separator + "data.yml" );
		FileConfiguration datac = YamlConfiguration.loadConfiguration( data );
		x = datac.getInt( "x" );
		y = datac.getInt( "y" );
		w = Bukkit.getWorld( datac.getString( "world" ) );
		done = false;
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
	
	public void updateLocation( Location loc ) {
		for ( int i = -1; i < 2; i++ ) {
			Location l = loc.clone().add( 0, 0, i );
			Block b;
			int abovey;
			if ( defrend ) {
				b = w.getHighestBlockAt( l.getBlockX(), l.getBlockZ() ).getLocation().clone().subtract( 0, 1, 0 ).getBlock();
				abovey = w.getHighestBlockYAt( l.getBlockX(), l.getBlockZ() - 1 ) - 1;
			} else {
				b = Util.getHighestBlockAt( l );
				abovey = Util.getHighestBlockAt( l.clone().subtract( 0, 0, 1 ) ).getY();
			}
			Material mat = b.getType();
			byte data = b.getData();
			Color c = getColor( mat, data );
			int xdistance = b.getX() - x;
			int zdistance = b.getZ() - y;
			if ( Math.abs( xdistance ) > ( getXLength() / 2 ) || Math.abs( zdistance ) > ( getYLength() / 2 ) ) return;
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
			map[ ( getXLength() / 2 ) + xdistance ][ ( getYLength() / 2 ) + zdistance ] = MapPalette.matchColor( tempColor );
		}
	}
	
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
	
	public HashMap< Material, HashMap< Byte, Color > > getColorMap() {
		return new HashMap< Material, HashMap< Byte, Color > >( mapColors );
	}
}
