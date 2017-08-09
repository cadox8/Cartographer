package io.github.bananapuncher714.map;

import io.github.bananapuncher714.CartographerMain;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;

public class CustomRenderer extends MapRenderer {
	CartographerMain plugin;
	int size = 2, borderx, bordery;
	int stage = 0, stage2 = 0;
	
	public CustomRenderer( boolean contextual, CartographerMain p, Scale s ) {
		super( contextual );
		// Here the amount of blocks( size ) is determined on the scale
		if ( s.equals( Scale.CLOSEST ) ) size = 1;
		else if ( s.equals( Scale.CLOSE ) ) size = 2;
		else if ( s.equals( Scale.NORMAL ) ) size = 4;
		else if ( s.equals( Scale.FAR ) ) size = 8;
		else if ( s.equals( Scale.FARTHEST ) ) size = 16;
		/*
		 *  This bit represents the radius of the world
		 *
		 * The values represent the amount of blocks the map can be shifted
		 * so a map with a border of 1024 cannot be shifted when the scale is 'far'
		*/
		plugin = p;
		borderx = ( ( plugin.getXLength() - size * 128 ) / 2 );
		bordery = ( ( plugin.getYLength() - size * 128 ) / 2 );
	}

	@Override
	public void render( MapView arg0, MapCanvas arg1, Player arg2 ) {
		if ( arg2.getWorld() != plugin.getWorld() ) return;
		// This part here is where the x and z gets set up
		int px = arg2.getLocation().getBlockX();
		int pz = arg2.getLocation().getBlockZ();
		// xo and zo are how far away you are from the center of the map
		int xo = px - plugin.getCenterX();
		int zo = pz - plugin.getCenterY();
		// This part determines whether you are within the bounds and
		// can have the map centered around you
		boolean overlapx = Math.abs( xo ) <= borderx;
	    boolean overlapy = Math.abs( zo ) <= bordery;
		int mapcenterx;
		int mapcentery;
		// This is where we determine if the player is too close to the edge of the world
		if ( !overlapx ) {
			mapcenterx = plugin.getCenterX();
			// This part ensures that either the map is centered around you
			// or it's pushed so you are as close as possible to the center
			if ( xo < 0 ) mapcenterx -= borderx;
			else mapcenterx += borderx;
		}
		else mapcenterx = px;
		if ( !overlapy ) {
			mapcentery = plugin.getCenterY();
			if ( zo < 0 ) mapcentery -= bordery;
			else mapcentery += bordery;
		}
		else mapcentery = pz;
		// Here we start drawing the map based around fx and fz
		byte[][] map = plugin.getMap();
		if ( map != null ) {
			for ( int x = 0; x < 128; x++ ) {
				for ( int z = 0; z < 128; z++ ) {
					// Get the center point for the calculating of blocks to show
					int fx = ( plugin.getXLength() / 2 ) + ( x - 64 ) * size;
					int fz = ( plugin.getYLength() / 2 ) + ( z - 64 ) * size;
					if ( overlapx ) fx = fx + xo;
					else if ( xo > 0 ) fx += borderx;
					else fx -= borderx;
					if ( overlapy ) fz = fz + zo;
					else if ( zo > 0 ) fz += bordery;
					else fz -= bordery;
					byte b = map[ fx ][ fz ];
					if ( b != 0 ) {
						arg1.setPixel( x, z, b );
					}
				}
			}
		}
		// Now it's time to draw the player icon and the rest of the icons we want
		// But first, we have to compute the amount of icons
		int ax = 0, ab = 0;
		// Get the x and z of your cursor on the map
		if ( !overlapx ) ax = ( int ) ( 128 * ( ( Math.abs( xo ) - borderx ) / ( ( double ) size * 64) ) );
		if ( !overlapy ) ab = ( int ) ( 128 * ( ( Math.abs( zo ) - bordery ) / ( ( double ) size * 64 ) ) );
		if ( xo < 0 ) ax = -ax;
		if ( zo < 0 ) ab = -ab;
		MapCursorCollection mcc = arg1.getCursors();
		int cursorListSize = 1;
		/*
		PlayerWrapper wrapper = plugin.getPlayerWrapper( arg2 );
		if ( wrapper == null ) return;
		Clan c = wrapper.getClan();
		
		
		if ( c != null ) {
			if ( c.getHome() != null ) cursorListSize++;
			for ( UUID u : c.getMembers() ) {
				Player offp = Bukkit.getOfflinePlayer( u ).getPlayer();
				if ( offp != null && offp != arg2 ) cursorListSize++;
			}
		}
		*/
		while ( mcc.size() < cursorListSize ) mcc.addCursor( new MapCursor( ( byte ) 0, ( byte ) 0, ( byte ) 0, ( byte ) 0, true ) );
		while ( mcc.size() > cursorListSize ) mcc.removeCursor( mcc.getCursor( 0 ) );
		MapCursor cursor2;
		int playerIndex = 0;
		Player p;
		// Here we actually start drawing it
		for ( int i = 0; i < mcc.size(); i++ ) {
			cursor2 = mcc.getCursor( i );
			if ( i == 0 ) {
				centerPlayerCursor( cursor2, overlapx, overlapy, ax, ab, arg2 );
			}/* else if ( i == 1 && c.getHome() != null ) {
				cursor2.setRawType( ( byte ) 4 );
				int bedx = c.getHome().getBlockX();
				int bedy = c.getHome().getBlockZ();
				int[] bedCoord = findRelPosition( mapcenterx, mapcentery, bedx, bedy );
				cursor2.setX( ( byte ) bedCoord[ 0 ] );
				cursor2.setY( ( byte ) bedCoord[ 1 ] );
			} else {
				p = null;
				cursor2.setRawType( ( byte ) 1 );
				while ( p == null || p == arg2 ) p = Bukkit.getOfflinePlayer( c.getMembers().get( playerIndex++ ) ).getPlayer();
				int bedx = p.getLocation().getBlockX();
				int bedy = p.getLocation().getBlockZ();
				int[] bedCoord = findRelPosition( mapcenterx, mapcentery, bedx, bedy );
				cursor2.setX( ( byte ) bedCoord[ 0 ] );
				cursor2.setY( ( byte ) bedCoord[ 1 ] );
				cursor2.setDirection( ( byte ) ( ( Math.abs( ( p.getLocation().getYaw() + 360.0 ) / 22.5 ) - 1 ) % 15 ) );
			}*/
		}
	}
	
	public int[] findRelPosition( int x, int y, int a, int b ) {
		// This one gets the x and z that is within bounds of a player on the map
		int fx = a - x;
		int fb = b - y;
		int finalx = ( int ) ( 128 * ( fx / ( ( double ) size * 64 ) ) );
		int finaly = ( int ) ( 128 * ( fb / ( ( double ) size * 64 ) ) );
		if ( finalx > 126 ) finalx = 126;
		else if ( finalx < -128 ) finalx = -128;
		if ( finaly > 126 ) finaly = 126;
		else if ( finaly < -128 ) finaly = -128;
		return new int[] { finalx, finaly };
	}
	
	public void centerPlayerCursor( MapCursor cursor, boolean overlapx, boolean overlapy, int ax, int ab, Player arg2 ) {
		// This one here centers the player
		// Only supposed to be called once on the actual player viewing the map
		cursor.setRawType( ( byte ) 0 );
		if ( overlapx ) cursor.setX( ( byte ) 0 );
		else if ( ax <= 128 && ax >= -128 ) cursor.setX( ( byte ) ax );
		else if ( ax < -128 ) cursor.setX( ( byte ) -128 );
		else cursor.setX( ( byte )  126 );
		if ( overlapy ) cursor.setY( ( byte ) 0 );
		else if ( ab <= 128 && ab >= -128 ) cursor.setY( ( byte ) ab );
		else if ( ab < -128 ) cursor.setY( ( byte ) -128 );
		else cursor.setY( ( byte ) 126 );
		cursor.setDirection( ( byte ) ( ( Math.abs( ( arg2.getLocation().getYaw() + 360.0 ) / 22.5 ) - 1 ) % 15 ) );
	}
}
