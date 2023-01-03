/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.boot.jandex.internal;

import java.lang.annotation.Annotation;

import org.jboss.jandex.DotName;

import jakarta.persistence.Basic;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Inheritance;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.SequenceGenerators;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.TableGenerators;
import jakarta.persistence.Version;

import static org.hibernate.boot.jandex.internal.JandexHelper.toDotName;

/**
 * @author Steve Ebersole
 */
public enum AnnotationDetails {
	ENTITY( toDotName( Entity.class ), Entity.class ),
	MAPPED_SUPERCLASS( toDotName( MappedSuperclass.class ), MappedSuperclass.class ),
	EMBEDDABLE( toDotName( Embeddable.class ), Embeddable.class ),

	ID( toDotName( Id.class ), Id.class ),
	EMBEDDED_ID( toDotName( EmbeddedId.class ), EmbeddedId.class ),
	ID_CLASS( toDotName( IdClass.class ), IdClass.class ),

	GENERATED_VALUE( toDotName( GeneratedValue.class ), GeneratedValue.class ),
	SEQUENCE_GENERATORS( toDotName( SequenceGenerators.class ), SequenceGenerators.class ),
	SEQUENCE_GENERATOR( toDotName( SequenceGenerator.class ), SequenceGenerator.class, SEQUENCE_GENERATORS ),
	TABLE_GENERATORS( toDotName( TableGenerators.class ), TableGenerators.class ),
	TABLE_GENERATOR( toDotName( TableGenerator.class ), TableGenerator.class, TABLE_GENERATORS ),

	VERSION( toDotName( Version.class ), Version.class ),
	INHERITANCE( toDotName( Inheritance.class ), Inheritance.class ),
	DISCRIMINATOR_COLUMN( toDotName( DiscriminatorColumn.class ), DiscriminatorColumn.class ),
	DISCRIMINATOR_VALUE( toDotName( DiscriminatorValue.class ), DiscriminatorValue.class ),

	BASIC( toDotName( Basic.class ), Basic.class ),
	EMBEDDED( toDotName( Embedded.class ), Embedded.class ),
	ONE_TO_ONE( toDotName( OneToOne.class ), OneToOne.class ),
	MANY_TO_ONE( toDotName( ManyToOne.class ), ManyToOne.class ),
	ELEMENT_COLLECTION( toDotName( ElementCollection.class ), ElementCollection.class ),
	ONE_TO_MANY( toDotName( OneToMany.class ), OneToMany.class ),
	MANY_TO_MANY( toDotName( ManyToMany.class ), ManyToMany.class ),

	TABLE( toDotName( Table.class ), Table.class )
	;

	private final DotName jandexName;
	private final Class<? extends Annotation> javaType;
	private final AnnotationDetails groupingAnnotationDetails;

	AnnotationDetails(DotName jandexName, Class<? extends Annotation> javaType) {
		this( jandexName, javaType, null );
	}

	AnnotationDetails(DotName jandexName, Class<? extends Annotation> javaType, AnnotationDetails groupingAnnotationDetails) {
		this.jandexName = jandexName;
		this.javaType = javaType;
		this.groupingAnnotationDetails = groupingAnnotationDetails;
	}

	public DotName getJandexName() {
		return jandexName;
	}

	public Class<? extends Annotation> getJavaType() {
		return javaType;
	}

	public AnnotationDetails getGroupingAnnotationDetails() {
		return groupingAnnotationDetails;
	}
}
