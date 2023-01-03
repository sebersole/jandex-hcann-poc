/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.boot.jandex.hcann;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.hibernate.boot.jandex.JandexContext;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.AnnotationValue;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.MethodInfo;

/**
 * {@linkplain InvocationHandler} implementation for building proxies for
 * annotation usages
 *
 * @see AnnotationInstance
 *
 * @implNote This class does not implement {@linkplain #hashCode hashCode} and
 * {@linkplain #equals equals} meaning it does not follow the recommendations of
 * the {@link Annotation} javadoc about these two methods. Never mix proxies and
 * real annotations
 *
 * @author Steve Ebersole
 * @author Paolo Perrotta
 * @author Davide Marchignoli
 */
public final class AnnotationProxy implements InvocationHandler {
	/**
	 * Create a proxy of the specified annotation using an explicit class-loader
	 */
	public static <T extends Annotation> T createProxy(
			Class<? extends Annotation> annotationType,
			AnnotationInstance details,
			JandexContext context) {
		return createProxy( annotationType, details, context, annotationType.getClassLoader() );
	}

	/**
	 * Create a proxy of the specified annotation using an explicit class-loader
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T createProxy(
			Class<? extends Annotation> annotationType,
			AnnotationInstance details,
			JandexContext context,
			ClassLoader classLoader) {
		return (T) Proxy.newProxyInstance(
				classLoader,
				new Class[] {annotationType},
				new AnnotationProxy( annotationType, details, context )
		);
	}

	private final Class<? extends Annotation> annotationType;
	private final AnnotationInstance details;
	private final JandexContext jandexContext;

	public AnnotationProxy(Class<? extends Annotation> annotationType, AnnotationInstance details, JandexContext jandexContext) {
		this.annotationType = annotationType;
		this.details = details;
		this.jandexContext = jandexContext;
	}

	public Class<? extends Annotation> getAnnotationType() {
		return annotationType;
	}

	public AnnotationInstance getDetails() {
		return details;
	}

	public Object invoke(Object proxy, Method method, Object[] args) {
		final AnnotationValue value = details.value( method.getName() );
		if ( value == null ) {
			// indicates the "default" value should apply
			return jandexContext.fromIndex( (index) -> {
				final ClassInfo annotationDescriptor = index.getClassByName( details.name() );
				final MethodInfo method1 = annotationDescriptor.method( method.getName() );
				return method1 == null ? null : method1.defaultValue().value();
			} );
		}
		return value.value();
	}

	public String toString() {
		return "AnnotationProxy( " + details + " )";
	}
}
