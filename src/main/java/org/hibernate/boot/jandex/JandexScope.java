/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.boot.jandex;

import java.lang.annotation.Annotation;
import java.util.List;

import org.hibernate.boot.jandex.internal.JandexHelper;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.DotName;

import jakarta.persistence.SequenceGenerator;

/**
 * Defines a scope for locating annotations via Jandex.
 *
 * Mainly intended for resolving identifier-generators and other
 * annotations that are attachable at multiple "levels".
 *
 * @author Steve Ebersole
 */
public interface JandexScope {
	/**
	 * Find all uses of the specified annotation
	 */
	List<AnnotationInstance> findUsages(DotName annotationClassName);

	/**
	 * Find a named use of the specified annotation.  E.g. find a generator
	 * {@linkplain SequenceGenerator#name() named} "my-seq"
	 */
	AnnotationInstance findNamedUsage(DotName annotationClassName, String name);

	/**
	 * Find a named use of the specified annotation.  E.g. find a generator
	 * {@linkplain SequenceGenerator#name() named} "my-seq".
	 * <p/>
	 * {@code `nameAttributeName`} allows to indicate a specific annotation attribute
	 * to use as the name
	 */
	AnnotationInstance findNamedUsage(DotName annotationClassName, String name, String nameAttributeName);

	default List<AnnotationInstance> findUsages(Class<? extends Annotation> type) {
		return findUsages( JandexHelper.toDotName( type ) );
	}

	default AnnotationInstance findNamedUsage(Class<? extends Annotation> type, String name) {
		return findNamedUsage( JandexHelper.toDotName( type ), name );
	}

	default AnnotationInstance findNamedUsage(Class<? extends Annotation> type, String name, String nameAttributeName) {
		return findNamedUsage( JandexHelper.toDotName( type ), name, nameAttributeName );
	}


}
