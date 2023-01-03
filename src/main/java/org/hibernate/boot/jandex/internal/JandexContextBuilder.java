/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.boot.jandex.internal;

import java.io.IOException;
import java.lang.annotation.Annotation;

import org.hibernate.boot.jandex.AnnotationAccessException;
import org.hibernate.boot.jandex.JandexContext;
import org.hibernate.boot.jandex.StreamAccess;

import org.jboss.jandex.ClassSummary;
import org.jboss.jandex.Index;
import org.jboss.jandex.Indexer;

/**
 * @author Steve Ebersole
 */
public class JandexContextBuilder {
	// todo (jandex) : allow for pre-built Index

	private final Indexer indexer;

	public JandexContextBuilder() {
		indexer = new Indexer();

		final AnnotationDetails[] annotationDetails = AnnotationDetails.values();
		for ( int i = 0; i < annotationDetails.length; i++ ) {
			final Class<? extends Annotation> annotationJavaType = annotationDetails[ i ].getJavaType();
			try {
				indexer.indexClass( annotationJavaType );
			}
			catch (IOException e) {
				throw new AnnotationAccessException( "Unable to index class - " + annotationJavaType, e );
			}
		}
	}

	public void index(StreamAccess streamAccess) {
		final ClassSummary classSummary = streamAccess.fromStream( (stream) -> {
			try {
				return indexer.indexWithSummary( stream );
			}
			catch (IOException e) {
				throw new AnnotationAccessException( "Unable to index stream - " + streamAccess.getStreamName(), e );
			}
		} );
	}


	public JandexContext buildJandexContext() {
		final Index complete = indexer.complete();
		return new JandexContextImpl( complete );
	}
}
