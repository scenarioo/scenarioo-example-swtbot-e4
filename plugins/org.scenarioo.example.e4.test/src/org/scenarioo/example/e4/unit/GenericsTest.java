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

package org.scenarioo.example.e4.unit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.scenarioo.example.e4.domain.AbstractDomainEntity;
import org.scenarioo.example.e4.domain.AbstractId;
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.domain.OrderId;

public class GenericsTest {

	@Test
	public void wildcardScenarioo() {

		List<AbstractDomainEntity<? extends AbstractId>> abstractIds = new ArrayList<AbstractDomainEntity<? extends AbstractId>>();
		abstractIds.add(new Order());
		// abstractIds.add(new Object()); // doesn't compile
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order());
		abstractIds.addAll(orders);

		List<? super AbstractDomainEntity<OrderId>> consumerAbstractIds = abstractIds;
		consumerAbstractIds.add(new Order()); // compiles
		// consumerAbstractIds.add(new Object()); // doesn't compiles
		// for (AbstractDomainEntity<? extends AbstractId> entity : consumerAbstractIds) {} // doesn't compile

		List<? extends AbstractDomainEntity<? extends AbstractId>> producerAbstractIds = abstractIds;
		// producerAbstractIds.add(new Order()); // doesn't compile
		for (AbstractDomainEntity<? extends AbstractId> entity : producerAbstractIds) {
			System.out.println(entity.toString());
		}
	}
}
