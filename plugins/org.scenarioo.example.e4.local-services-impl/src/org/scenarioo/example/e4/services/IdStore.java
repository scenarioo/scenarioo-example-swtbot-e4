/* Copyright (c) 2014, scenarioo.org Development Team
 * All rights reserved.
 *
 * See https://github.com/scenarioo?tab=members
 * for a complete list of contributors to this project.
 *
 * Redistribution and use of the Scenarioo Examples in source and binary forms,
 * with or without modification, are permitted provided that the following
 * conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice, this
 *   list of conditions and the following disclaimer in the documentation and/or
 *   other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.scenarioo.example.e4.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.scenarioo.example.e4.domain.AbstractDomainEntity;
import org.scenarioo.example.e4.domain.AbstractId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * holds entities mapped by their ids.
 */
public class IdStore<S extends AbstractId, T extends AbstractDomainEntity<S>> {

	private static Logger LOGGER = LoggerFactory.getLogger(IdStore.class);

	private final Map<S, T> map = new HashMap<S, T>();

	private IdStore() {

	}

	public void put(final T obj) {
		map.put(obj.getId(), obj);
	}

	public T get(final S id) {
		return map.get(id);
	}

	public Collection<T> values() {
		return map.values();
	}

	public static <H extends AbstractId, I extends AbstractDomainEntity<H>> IdStore<H, I> getInstance(
			final Class<I> clazz) {
		IdStore<H, I> idStore = SingletonHolder.getInstance(clazz);
		if (idStore == null) {
			try {
				idStore = new IdStore<H, I>();
				SingletonHolder.INSTANCE.put(clazz, idStore);
				LOGGER.info("idStore for Domain Class: " + clazz + " created!");
			} catch (Exception ex) {
				LOGGER.error("Could not create IdStore for class " + clazz, ex);
			}
		}
		return idStore;
	}

	private static class SingletonHolder {

		@SuppressWarnings("unchecked")
		private static <H extends AbstractId, I extends AbstractDomainEntity<H>> IdStore<H, I> getInstance(
				final Class<I> clazz) {
			return (IdStore<H, I>) INSTANCE.get(clazz);
		}

		private static Map<Class<? extends AbstractDomainEntity<? extends AbstractId>>, IdStore<? extends AbstractId, ? extends AbstractDomainEntity<? extends AbstractId>>> INSTANCE =
				new HashMap<Class<? extends AbstractDomainEntity<? extends AbstractId>>, IdStore<? extends AbstractId, ? extends AbstractDomainEntity<? extends AbstractId>>>();
	}
}
