package io.github.bananapuncher714;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.bananapuncher714.map.CustomRenderer;
import io.github.bananapuncher714.map.RealWorldCursor;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;

public class MapManager implements Listener {
	private static HashMap< String, CursorSelector > cursors = new HashMap< String, CursorSelector >();
	CartographerMain plugin;
	
	public MapManager( CartographerMain m ) {
		plugin = m;
	}
	
	public interface CursorSelector {
		ArrayList< RealWorldCursor > getCursors( Player p );
	}
	
	public void registerCursorSelector( String permission, CursorSelector selector ) {
		cursors.put( permission, selector );
	}
	
	public static HashMap< String, CursorSelector > getCursorSelectors() {
		return new HashMap< String, CursorSelector >( cursors );
	}
	
	public MapView getMap( MapView map, boolean zoom ) {
		// Get mapview
		MapView mv = map;
		// Clear the renderers
		for ( MapRenderer r : mv.getRenderers() ) {
			mv.removeRenderer( r );
		}
		// Set the scale
		int maxScale = Math.min( plugin.getXLength(), plugin.getYLength() );
		Scale s = mv.getScale();
		if ( s.equals( Scale.CLOSEST ) ) {
			if ( !zoom && maxScale >= 256 ) mv.setScale( Scale.CLOSE );
		} else if ( s.equals( Scale.CLOSE ) ) {
			if ( zoom ) mv.setScale( Scale.CLOSEST );
			else if ( maxScale >= 512 ) mv.setScale( Scale.NORMAL );
		} else if ( s.equals( Scale.NORMAL ) ) {
			if ( zoom ) mv.setScale( Scale.CLOSE );
			else if ( maxScale >= 1024 ) mv.setScale( Scale.FAR );
		} else if ( s.equals( Scale.FAR ) ) {
			if ( zoom ) mv.setScale( Scale.NORMAL );
			else if ( maxScale >= 2048 ) mv.setScale( Scale.FARTHEST );
		} else if ( s.equals( Scale.FARTHEST ) ) {
			if ( zoom ) mv.setScale( Scale.FAR );
		} else {
			mv.setScale( Scale.CLOSEST );
		}
		// And add the custom renderer
		mv.addRenderer( new CustomRenderer( true, plugin, mv.getScale(), plugin.showPlayer(), plugin.getDefPointer() ) );
		return mv;
	}
	
	public MapView getMap( ItemStack map, boolean zoom ) {
		// Get mapview
		short d = map.getDurability();
		MapView mv = plugin.getServer().getMap( d );
		return getMap( mv, zoom );
	}
	
	public void setCenter( Player p, MapView mv ) {
		// Totally unnecessary, maybe?
		int distance;
		switch( mv.getScale() ) {
		case CLOSEST: distance = 64; break;
		case CLOSE: distance = 128; break;
		case NORMAL: distance = 256; break;
		case FAR: distance = 512; break;
		case FARTHEST: distance = 1024; break;
		default: distance = 128; break;
		}
		Location ploc = p.getLocation();
		int padding = distance / 2;
		int centerx = plugin.getCenterX();
		int centerz = plugin.getCenterY();
		if ( ploc.getX() > ( centerx + ( plugin.getXLength() * 16 ) - distance ) ) {
			p.sendMessage( "x1" );
			mv.setCenterX( centerx + ( plugin.getXLength() * 16 ) - distance );
		} else if ( ploc.getX() < centerx - ( plugin.getXLength() * 16 ) + distance ) {
			p.sendMessage( "x2" );
			mv.setCenterX( centerx - ( plugin.getXLength() * 16 ) + distance );
		} else {
			p.sendMessage( "x3" );
			mv.setCenterX( ploc.getBlockX() );
		}
		if ( ploc.getZ() > centerz + ( plugin.getYLength() * 16 ) - distance ) {
			p.sendMessage( "z1" );
			mv.setCenterZ( centerz + ( plugin.getYLength() * 16 ) - distance );
		} else if ( ploc.getZ() < centerz - ( plugin.getYLength() * 16 ) + distance ) {
			p.sendMessage( "z2" );
			mv.setCenterZ( centerz - ( plugin.getYLength() * 16 ) + distance );
		} else {
			p.sendMessage( "z3" );
			mv.setCenterZ( ploc.getBlockZ() );
		}
		p.sendMessage( String.valueOf( mv.getCenterX() ) );
		p.sendMessage( String.valueOf( mv.getCenterZ() ) );
	}
	
	@EventHandler
	public void onPlayerInteractEvent( PlayerInteractEvent e ) {
		// Must change that for 1.8/1.11
		Action a = e.getAction();
		if ( e.getHand() != EquipmentSlot.HAND ) return;
		if ( !a.equals( Action.RIGHT_CLICK_BLOCK ) && !a.equals( Action.LEFT_CLICK_BLOCK ) && !a.equals( Action.RIGHT_CLICK_AIR ) && !a.equals( Action.LEFT_CLICK_AIR ) ) return;
		if ( e.getPlayer().getInventory().getItemInHand().getType() != Material.MAP ) return;
		Player p = e.getPlayer();
		e.setCancelled( true );
		if ( plugin.getCenterX() == 0 && plugin.getCenterY() == 0 ) return;
		if ( a.equals( Action.RIGHT_CLICK_BLOCK ) || a.equals( Action.RIGHT_CLICK_AIR ) ) p.sendMap( getMap( p.getInventory().getItemInHand(), false ) );
		else p.sendMap( getMap( p.getInventory().getItemInHand(), true ) );
	}
	
	@EventHandler
	public void onMapInitializeEvent( MapInitializeEvent e ) {
		MapView map = e.getMap();
		map.setScale( Scale.CLOSEST );
		map = getMap( map, true );
	}

}
