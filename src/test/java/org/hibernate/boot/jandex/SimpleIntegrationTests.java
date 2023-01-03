/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.boot.jandex;

import java.util.List;

import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.boot.jandex.hcann.ReflectionManagerImpl;
import org.hibernate.boot.jandex.internal.JandexContextBuilder;

import org.junit.jupiter.api.Test;

import org.jboss.jandex.AnnotationInstance;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Steve Ebersole
 */
public class SimpleIntegrationTests {
	@Test
	public void simpleTest() {
		final JandexContextBuilder jandexContextBuilder = new JandexContextBuilder();
		jandexContextBuilder.index( TestHelper.getClassFileStreamAccess( SimpleEntity.class ) );
		final JandexContext jandexContext = jandexContextBuilder.buildJandexContext();

		final ReflectionManagerImpl reflectionManager = new ReflectionManagerImpl( jandexContext );
		final XClass xClass = reflectionManager.toXClass( SimpleEntity.class );
		assertThat( xClass ).isNotNull();
		assertThat( xClass.getName() ).isEqualTo( SimpleEntity.class.getName() );

		// todo (jandex) : this is a problem...
		//  	HCANN defines this signature such that the return need be the same type as the argument
		//

		final Entity entityAnn = xClass.getAnnotation( Entity.class );
		assertThat( entityAnn ).isNotNull();
		assertThat( entityAnn.name() ).isEqualTo( SimpleEntity.class.getSimpleName() );

		final Table tableAnn = xClass.getAnnotation( Table.class );
		assertThat( tableAnn ).isNotNull();
		assertThat( tableAnn.name() ).isEqualTo( "simple_tbl" );

		// compare this with the corresponding checks in `JandexSmokeTests` - Jandex
		// returns nulls for non-specified values, but since HCANN is built on top of
		// the JDK annotations themselves, the "default" value is returned in such cases
		// which is often a hindrance

		assertThat( tableAnn.catalog() ).isEqualTo( "" );

//		final List<AnnotationInstance> entities = jandexContext.findUsages( Entity.class );
//		assertThat( entities ).hasSize( 1 );
//		final AnnotationInstance fromUsages = entities.get( 0 );
//
//		final AnnotationInstance named = jandexContext.findNamedUsage( Entity.class, "SimpleEntity" );
//		assertThat( named ).isSameAs( fromUsages );
//
//		final AnnotationInstance namedTable = jandexContext.findNamedUsage( Table.class, "simple_tbl" );
//		assertThat( namedTable ).isNotNull();
//		assertThat( namedTable.value( "catalog" ) ).isNull();
	}
}
