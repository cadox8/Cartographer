package io.github.bananapuncher714.cartographer.util;

import java.util.Random;
import java.util.Set;

import io.netty.util.internal.ThreadLocalRandom;

public final class FailSafe {
	
	public static < T > T getEnum( Class< T > clazz, String value ) {
		if ( !clazz.isEnum() ) return null;
		if ( value == null ) return clazz.getEnumConstants()[ 0 ];
		for ( Object object : clazz.getEnumConstants() ) {
			if ( object.toString().equals( value ) ) {
				return ( T ) object;
			}
		}
		return clazz.getEnumConstants()[ 0 ];
	}
}
