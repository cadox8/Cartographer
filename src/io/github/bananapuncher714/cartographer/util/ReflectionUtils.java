package io.github.bananapuncher714.cartographer.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public final class ReflectionUtils {
	
	public static boolean isMainHand( PlayerInteractEvent e ) {
		Method getHand;
		try {
			getHand = e.getClass().getMethod( "getHand" );
			return EquipmentSlot.HAND.equals( getHand.invoke( e ) );
		} catch ( NoSuchMethodException |
				SecurityException |
				IllegalAccessException |
				IllegalArgumentException |
				InvocationTargetException e1 ) {
			return true;
		}
	}
}
