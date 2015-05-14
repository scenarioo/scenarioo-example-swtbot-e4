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

package org.scenarioo.example.e4.services.osgi;

import java.util.Set;

import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.domain.OrderId;
import org.scenarioo.example.e4.domain.OrderPositions;
import org.scenarioo.example.e4.domain.OrderState;
import org.scenarioo.example.e4.domain.Position;
import org.scenarioo.example.e4.dto.OrderSearchFilter;
import org.scenarioo.example.e4.dto.OrderWithPositions;
import org.scenarioo.example.e4.services.OrderService;
import org.scenarioo.example.e4.services.internal.Counter;
import org.scenarioo.example.e4.services.internal.IdSetter;
import org.scenarioo.example.e4.services.internal.IdStore;
import org.scenarioo.example.e4.services.internal.SimulateServiceCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderServiceImpl implements OrderService {

	private static Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	private final IdSetter idSetter = IdSetter.getInstance();
	private final Counter counter = Counter.getInstance();
	private final IdStore<OrderId, Order> orderIdStore = IdStore.getInstance(Order.class);
	private final IdStore<OrderId, OrderPositions> positionsIdStore = IdStore.getInstance(OrderPositions.class);

	/**
	 * @see org.scenarioo.example.e4.services.OrderService#createOrder(org.scenarioo.example.e4.domain.Order)
	 */
	@Override
	public Order createOrder(final OrderWithPositions orderWithPos) {

		SimulateServiceCall.start();
		
		// Store Order part
		Order order = orderWithPos.getOrder();
		order.generateAndSetId(counter);
		orderIdStore.put(order);
		
		// Store positions part
		OrderPositions pos = new OrderPositions(orderWithPos);
		positionsIdStore.put(pos);

		LOGGER.info(order + " with " + pos + " has been created");

		return order;
	}

	/**
	 * @see org.scenarioo.example.e4.services.OrderService#getOrder(org.scenarioo.example.e4.domain.OrderId)
	 */
	@Override
	public Order getOrder(final OrderId id) {

		SimulateServiceCall.start();

		Order order = IdStore.getInstance(Order.class).get(id);
		if (order == null) {
			setNextGeneratedId(id);
			order = new Order();
			order.generateAndSetId(idSetter);
			order.setOrderNumber("(Not Found)");
			order.setState(OrderState.DELIVERED);
			orderIdStore.put(order);
			return order;
		}

		return order;
	}

	/**
	 * @param id
	 */
	private void setNextGeneratedId(final OrderId id) {
		idSetter.setNextId(id.getId());
	}

	/**
	 * @see org.scenarioo.example.e4.services.OrderService#searchForOrders(org.scenarioo.example.e4.dto.OrderSearchFilter)
	 */
	@Override
	public Set<Order> searchForOrders(final OrderSearchFilter orderSearchFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.scenarioo.example.e4.services.OrderService#getPositions(org.scenarioo.example.e4.domain.OrderId)
	 */
	@Override
	public Set<Position> getPositions(final OrderId id) {
		// TODO Auto-generated method stub
		return null;
	}

}
