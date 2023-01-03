/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.boot.jandex.hcann;

import java.lang.annotation.Annotation;

import org.hibernate.annotations.common.reflection.AnnotationReader;
import org.hibernate.boot.jandex.JandexContext;

/**
 * @author Steve Ebersole
 */
public class JandexAnnotationReader implements AnnotationReader {
	private final JandexContext jandexContext;

	public JandexAnnotationReader(JandexContext jandexContext) {
		this.jandexContext = jandexContext;
	}

	@Override
	public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
		return null;
	}

	@Override
	public <T extends Annotation> boolean isAnnotationPresent(Class<T> annotationType) {
		return false;
	}

	@Override
	public Annotation[] getAnnotations() {
		return new Annotation[ 0 ];
	}
}
