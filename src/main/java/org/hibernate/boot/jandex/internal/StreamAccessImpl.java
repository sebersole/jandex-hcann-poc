/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.boot.jandex.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.boot.jandex.AnnotationAccessException;
import org.hibernate.boot.jandex.StreamAccess;

/**
 * @author Steve Ebersole
 */
public class StreamAccessImpl implements StreamAccess {
	private final URL location;

	public StreamAccessImpl(URL location) {
		assert location != null;
		this.location = location;
	}

	@Override
	public String getStreamName() {
		return location.getFile();
	}

	@Override
	public void withStream(Consumer<InputStream> consumer) {
		try ( InputStream stream = location.openStream() ) {
			consumer.accept( stream );
		}
		catch (IOException e) {
			throw new AnnotationAccessException( "Unable to open stream `" + getStreamName() + "`", e );
		}
	}

	@Override
	public <T> T fromStream(Function<InputStream, T> function) {
		try ( InputStream stream = location.openStream() ) {
			return function.apply( stream );
		}
		catch (IOException e) {
			throw new AnnotationAccessException( "Unable to open stream `" + getStreamName() + "`", e );
		}
	}
}
