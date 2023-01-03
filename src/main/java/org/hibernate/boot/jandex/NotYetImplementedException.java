/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.boot.jandex;

import org.hibernate.HibernateException;

/**
 * @author Steve Ebersole
 */
public class NotYetImplementedException extends HibernateException {
	// todo (jandex) : remove this

	public NotYetImplementedException() {
		this( "Feature not yet implemented" );
	}

	public NotYetImplementedException(String message) {
		super( message );
	}

	public NotYetImplementedException(String message, Throwable cause) {
		super( message, cause );
	}
}
