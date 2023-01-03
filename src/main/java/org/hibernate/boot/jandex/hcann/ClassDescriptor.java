/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.boot.jandex.hcann;

import java.util.List;

import org.hibernate.annotations.common.reflection.Filter;
import org.hibernate.annotations.common.reflection.XAnnotatedElement;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XMethod;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.boot.jandex.JandexContext;

import org.jboss.jandex.ClassInfo;

/**
 * Jandex-based {@link XClass} descriptor
 *
 * @author Steve Ebersole
 */
public class ClassDescriptor extends AnnotatedElementDescriptor implements XClass {
	public ClassDescriptor(ClassInfo target, JandexContext context, ReflectionManagerImpl reflectionManager) {
		super( target, context );
	}

	@Override
	public ClassInfo getTarget() {
		return (ClassInfo) super.getTarget();
	}

	@Override
	public String getName() {
		return getTarget().name().toString();
	}

	@Override
	public XClass getSuperclass() {
		return null;
	}

	@Override
	public XAnnotatedElement getContainingElement() {
		return null;
	}

	@Override
	public XClass[] getInterfaces() {
		return new XClass[ 0 ];
	}

	@Override
	public boolean isInterface() {
		return false;
	}

	@Override
	public boolean isAbstract() {
		return false;
	}

	@Override
	public boolean isPrimitive() {
		return false;
	}

	@Override
	public boolean isEnum() {
		return false;
	}

	@Override
	public boolean isAssignableFrom(XClass c) {
		return false;
	}

	@Override
	public List<XProperty> getDeclaredProperties(String accessType) {
		return null;
	}

	@Override
	public List<XProperty> getDeclaredProperties(String accessType, Filter filter) {
		return null;
	}

	@Override
	public List<XMethod> getDeclaredMethods() {
		return null;
	}
}
