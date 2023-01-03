/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.boot.jandex;

import java.io.InputStream;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Steve Ebersole
 */
public interface StreamAccess {
	String getStreamName();
	void withStream(Consumer<InputStream> consumer);
	<T> T fromStream(Function<InputStream, T> function);
}
