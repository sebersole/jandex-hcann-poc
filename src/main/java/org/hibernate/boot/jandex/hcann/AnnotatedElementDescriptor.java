/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.boot.jandex.hcann;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.annotations.common.reflection.XAnnotatedElement;
import org.hibernate.boot.jandex.JandexContext;
import org.hibernate.boot.jandex.internal.JandexHelper;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.AnnotationTarget;
import org.jboss.jandex.DotName;

/**
 * @author Steve Ebersole
 */
public class AnnotatedElementDescriptor implements XAnnotatedElement {
	private final AnnotationTarget target;
	private final Map<Class<? extends Annotation>, Annotation> annotationMap;

	public AnnotatedElementDescriptor(
			AnnotationTarget target,
			JandexContext context) {
		this.target = target;

		// unfortunately, `#getAnnotations` forces us to eagerly resolve all annotations from the target
		this.annotationMap = resolveAnnotations( target, context );
	}

	public AnnotationTarget getTarget() {
		return target;
	}

	@Override
	public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
		//noinspection unchecked
		return (T) annotationMap.get( annotationType );
	}

	@Override
	public <T extends Annotation> boolean isAnnotationPresent(Class<T> annotationType) {
		return annotationMap.containsKey( annotationType );
	}

	@Override
	public Annotation[] getAnnotations() {
		return annotationMap.values().toArray( new Annotation[0] );
	}

	private Map<Class<? extends Annotation>, Annotation> resolveAnnotations(
			AnnotationTarget target,
			JandexContext context) {
		final Collection<AnnotationInstance> annotations = target.annotations();
		if ( annotations == null || annotations.isEmpty() ) {
			return Collections.emptyMap();
		}

		final ConcurrentHashMap<Class<? extends Annotation>, Annotation> result = new ConcurrentHashMap<>();
		for ( AnnotationInstance annotation : annotations ) {
			final DotName annotationTypeName = annotation.name();
			final Class<? extends Annotation> annotationJavaType = JandexHelper.resolveAnnotationClass( annotationTypeName );
			final Annotation proxy = AnnotationProxy.createProxy( annotationJavaType, annotation, context );
			result.put( annotationJavaType, proxy );
		}

		return result;
	}
}
