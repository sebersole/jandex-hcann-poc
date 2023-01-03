/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.boot.jandex;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.boot.jandex.hcann.ClassDescriptor;

import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;

/**
 * @author Steve Ebersole
 */
public interface JandexContext extends JandexScope {
	void withIndex(Consumer<Index> consumer);

	<T> T fromIndex(Function<Index, T> action);

	ClassInfo getClassInfo(DotName className);
}
