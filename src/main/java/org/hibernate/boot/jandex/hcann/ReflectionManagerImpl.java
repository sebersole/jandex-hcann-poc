/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.boot.jandex.hcann;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.annotations.common.reflection.AnnotationReader;
import org.hibernate.annotations.common.reflection.ReflectionManager;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XMethod;
import org.hibernate.annotations.common.reflection.XPackage;
import org.hibernate.boot.jandex.AnnotationAccessException;
import org.hibernate.boot.jandex.JandexContext;
import org.hibernate.boot.jandex.NotYetImplementedException;
import org.hibernate.boot.jandex.internal.JandexHelper;

import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;

/**
 * Implementation of HCANN ReflectionManager
 *
 * @author Steve Ebersole
 */
public class ReflectionManagerImpl implements ReflectionManager {
	private final JandexContext jandexContext;

	private final Map<DotName, ClassDescriptor> classDescriptorMap = new ConcurrentHashMap<>();
	private final Map<ClassDescriptor,Class<?>> javaClassMap = new ConcurrentHashMap<>();

	public ReflectionManagerImpl(JandexContext jandexContext) {
		this.jandexContext = jandexContext;
	}

	@Override
	public AnnotationReader buildAnnotationReader(AnnotatedElement annotatedElement) {
		throw new NotYetImplementedException();
	}

	@Override
	public Map<?,?> getDefaults() {
		throw new NotYetImplementedException();
	}

	@Override
	public <T> XClass toXClass(Class<T> javaType) {
		final DotName dotName = JandexHelper.toDotName( javaType );

		final ClassDescriptor existing = classDescriptorMap.get( dotName );
		if ( existing != null ) {
			return existing;
		}

		final ClassInfo classInfo = jandexContext.fromIndex( (index) -> index.getClassByName( dotName ) );
		final ClassDescriptor classDescriptor = new ClassDescriptor( classInfo, jandexContext, this );
		classDescriptorMap.put( dotName, classDescriptor );
		return classDescriptor;
	}

	@Override
	public Class<?> toClass(XClass xClazz) {
		final ClassDescriptor classDescriptor = (ClassDescriptor) xClazz;

		final Class<?> existing = javaClassMap.get( classDescriptor );
		if ( existing != null ) {
			return existing;
		}

		// todo (jandex) : need ClassLoaderService
		//		- for now, just use our ClassLoader
		try {
			final Class<?> loadedClass = getClass().getClassLoader().loadClass( xClazz.getName() );
			javaClassMap.put( classDescriptor, loadedClass );
			return loadedClass;
		}
		catch (ClassNotFoundException e) {
			throw new AnnotationAccessException( "Unable to locate Java Class for XClass - " + xClazz.getName(), e );
		}
	}

	@Override
	public Type toType(XClass xClazz) {
		throw new NotYetImplementedException();
	}

	@Override
	public Method toMethod(XMethod method) {
		throw new NotYetImplementedException();
	}

	@Override
	public XPackage toXPackage(Package pkg) {
		throw new NotYetImplementedException();
	}

	@Override
	public <T> boolean equals(XClass xClass, Class<T> javaClass) {
		if ( xClass == null ) {
			return javaClass == null;
		}
		return xClass.getName().equals( javaClass.getName() );
	}
}
