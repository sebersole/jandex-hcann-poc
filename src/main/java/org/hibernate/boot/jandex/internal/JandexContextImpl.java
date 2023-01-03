/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.boot.jandex.internal;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.boot.jandex.JandexContext;
import org.hibernate.boot.jandex.JandexScope;
import org.hibernate.boot.jandex.hcann.ClassDescriptor;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.AnnotationValue;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;

/**
 * Context for a Jandex {@link Index}.  Acts as the root {@link JandexScope}
 *
 * @author Steve Ebersole
 */
public class JandexContextImpl implements JandexContext {
	public static final String DEFAULT_NAME_ATTR_NAME = "name";

	private final Index jandexIndex;

	public JandexContextImpl(Index jandexIndex) {
		this.jandexIndex = jandexIndex;
	}

	public Index getJandexIndex() {
		return jandexIndex;
	}

	@Override
	public void withIndex(Consumer<Index> consumer) {
		consumer.accept( jandexIndex );
	}

	@Override
	public <T> T fromIndex(Function<Index, T> action) {
		return action.apply( jandexIndex );
	}

	@Override
	public ClassInfo getClassInfo(DotName className) {
		return jandexIndex.getClassByName( className );
	}

	@Override
	public List<AnnotationInstance> findUsages(DotName annotationClassName) {
		return jandexIndex.getAnnotations( annotationClassName );
	}

	@Override
	public AnnotationInstance findNamedUsage(DotName annotationClassName, String name) {
		return findNamedUsage( annotationClassName, name, DEFAULT_NAME_ATTR_NAME );
	}

	@Override
	public AnnotationInstance findNamedUsage(DotName annotationClassName, String name, String nameAttributeName) {
		final List<AnnotationInstance> usages = findUsages( annotationClassName );
		for ( int i = 0; i < usages.size(); i++ ) {
			final AnnotationInstance usage = usages.get( i );
			final AnnotationValue usageName = usage.value( nameAttributeName );
			if ( usageName != null && usageName.asString().equals( name ) ) {
				return usage;
			}
		}

		return null;
	}
}
