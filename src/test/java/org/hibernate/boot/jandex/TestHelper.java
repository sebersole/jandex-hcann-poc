/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.boot.jandex;

import java.net.URL;

import org.hibernate.boot.jandex.internal.JandexHelper;
import org.hibernate.boot.jandex.internal.StreamAccessImpl;

/**
 * @author Steve Ebersole
 */
public class TestHelper {
	public static StreamAccess getClassFileStreamAccess(Class<?> clazz) {
		final URL resource = JandexHelper.class.getClassLoader().getResource( toResourceName( clazz ) );
		if ( resource == null ) {
			throw new AnnotationAccessException( "Unable to locate class as resource : " + clazz.getName() );
		}
		return new StreamAccessImpl( resource );
	}

	private static String toResourceName(Class<?> clazz) {
		return clazz.getName().replace( '.', '/' ) + ".class";
	}

	private TestHelper() {
	}
}
