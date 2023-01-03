/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.boot.jandex;

import java.io.IOException;
import java.util.List;

import org.hibernate.boot.jandex.internal.JandexContextImpl;

import org.junit.jupiter.api.Test;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.Index;
import org.jboss.jandex.Indexer;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Steve Ebersole
 */
public class JandexSmokeTests {

	@Test
	public void simpleTest() throws IOException {
		final Indexer indexer = new Indexer();
		indexer.indexClass( Entity.class );
		indexer.indexClass( Table.class );
		indexer.indexClass( Id.class );
		indexer.indexClass( Basic.class );
		indexer.indexClass( SimpleEntity.class );

		final Index index = indexer.complete();

		final JandexContext jandexContext = new JandexContextImpl( index );

		final List<AnnotationInstance> entities = jandexContext.findUsages( Entity.class );
		assertThat( entities ).hasSize( 1 );
		final AnnotationInstance fromUsages = entities.get( 0 );

		final AnnotationInstance named = jandexContext.findNamedUsage( Entity.class, "SimpleEntity" );
		assertThat( named ).isSameAs( fromUsages );

		final AnnotationInstance namedTable = jandexContext.findNamedUsage( Table.class, "simple_tbl" );
		assertThat( namedTable ).isNotNull();
		assertThat( namedTable.value( "catalog" ) ).isNull();
		assertThat( namedTable.valueWithDefault( index, "catalog" ).asString() ).isEqualTo( "" );
	}


}
