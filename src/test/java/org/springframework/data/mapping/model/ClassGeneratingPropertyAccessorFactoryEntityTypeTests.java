/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.mapping.model;

import static org.assertj.core.api.Assertions.*;

import java.io.Serializable;

import org.junit.Test;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.context.SampleMappingContext;
import org.springframework.data.mapping.context.SamplePersistentProperty;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.support.PersistentEntityInformation;

/**
 * Unit tests for {@link ClassGeneratingPropertyAccessorFactory} covering interface and concrete class entity types.
 *
 * @author John Blum
 * @author Oliver Gierke
 */
public class ClassGeneratingPropertyAccessorFactoryEntityTypeTests {

	SampleMappingContext mappingContext = new SampleMappingContext();

	@Test // DATACMNS-853
	public void getIdentifierOfInterfaceBasedEntity() {

		Algorithm quickSort = new QuickSort();

		assertThat(getEntityInformation(Algorithm.class).getId(quickSort)).hasValue(quickSort.getName());
	}

	@Test // DATACMNS-853
	public void getIdentifierOfClassBasedEntity() {

		Person jonDoe = new Person("JonDoe");

		assertThat(getEntityInformation(Person.class).getId(jonDoe)).hasValue(jonDoe.name);
	}

	private EntityInformation<Object, Serializable> getEntityInformation(Class<?> type) {

		PersistentEntity<Object, SamplePersistentProperty> entity = mappingContext.getRequiredPersistentEntity(type);
		return new PersistentEntityInformation<>(entity);
	}

	interface Algorithm {

		@Id
		String getName();
	}

	class QuickSort implements Algorithm {

		@Override
		public String getName() {
			return getClass().toString();
		}
	}

	static class Person {

		@Id String name;

		Person(String name) {
			this.name = name;
		}
	}
}
