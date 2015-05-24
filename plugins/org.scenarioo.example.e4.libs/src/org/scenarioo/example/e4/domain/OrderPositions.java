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

package org.scenarioo.example.e4.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class OrderPositions extends AbstractDomainEntity<OrderId> {

	/**
	 * @see org.scenarioo.example.e4.domain.AbstractDomainEntity#createInstance(java.lang.Long)
	 */
	@Override
	protected OrderId createInstance(final Long id) {
		throw new IllegalStateException(
				"Don't create new Id for OrderPositions. Use the Id of the corresponding Order object!");
	}

	private final List<Position> positions;

	public OrderPositions() {
		super();
		this.positions = new ArrayList<Position>();
	}

	public OrderPositions(final OrderPositions orderPositions) {
		super(orderPositions);
		positions = new ArrayList<Position>();
	}

	/**
	 * @return the positions
	 */
	public List<Position> getPositions() {
		return Collections.unmodifiableList(positions);
	}

	public void addPosition(final Position pos) {
		positions.add(pos);
	}

	public void removePosition(final Position pos) {
		positions.add(pos);
	}

	/**
	 * @param Order
	 */
	public void setOrderReference(final Order order) {
		super.setId(order.getId());
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
