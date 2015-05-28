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

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.scenarioo.example.e4.domain.Article;
import org.scenarioo.example.e4.domain.ArticleId;
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.domain.OrderId;
import org.scenarioo.example.e4.domain.OrderPositions;
import org.scenarioo.example.e4.domain.OrderState;
import org.scenarioo.example.e4.domain.Position;
import org.scenarioo.example.e4.dto.CreateOrderDTO;
import org.scenarioo.example.e4.dto.OrderPositionsTableviewDTO;
import org.scenarioo.example.e4.dto.OrderPositionsTreeviewDTO;
import org.scenarioo.example.e4.dto.OrderSearchFilter;
import org.scenarioo.example.e4.events.OrderServiceEvents;
import org.scenarioo.example.e4.services.OrderService;
import org.scenarioo.example.e4.services.internal.Counter;
import org.scenarioo.example.e4.services.internal.IdSetter;
import org.scenarioo.example.e4.services.internal.SimulateServiceCall;
import org.scenarioo.example.e4.services.internal.dummydata.OrdersBuilder;
import org.scenarioo.example.e4.services.internal.idstores.ArticleIdStore;
import org.scenarioo.example.e4.services.internal.idstores.IdStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderServiceImpl implements OrderService {

	private static Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	private final IdSetter idSetter = IdSetter.getInstance();
	private final Counter counter = Counter.getInstance();
	private final IdStore<OrderId, Order> orderIdStore = IdStore.getInstance(Order.class);
	private final IdStore<OrderId, OrderPositions> positionsIdStore = IdStore.getInstance(OrderPositions.class);
	private final ArticleIdStore articleIdStore = ArticleIdStore.getInstance();

	public OrderServiceImpl() {
		OrdersBuilder builder = OrdersBuilder.getInstance();
		builder.createOrder("Order 1");
		builder.createOrder("Order 2");
		builder.createOrder("Order 3");
		builder.createOrder("Order 4");
		builder.createOrder("Order 5");
		builder.createOrder("Order 6");
	}

	/**
	 * @see org.scenarioo.example.e4.services.OrderService#createOrder(org.scenarioo.example.e4.domain.Order)
	 */
	@Override
	public Order createOrder(final CreateOrderDTO orderWithPos) {

		SimulateServiceCall.start();

		// Store Order part
		Order order = orderWithPos.getOrder();
		order.generateAndSetId(counter);
		orderIdStore.add(order);

		// Store positions part
		OrderPositions pos = orderWithPos.getOrderPositions();
		pos.setOrderReference(order);
		positionsIdStore.add(pos);

		LOGGER.info(order + " with " + pos + " has been created");
		postEvent(OrderServiceEvents.TOPIC_ORDER_TREE_ADD, order);

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
			order.setState(OrderState.NOT_FOUND);
			orderIdStore.add(order);
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

		SimulateServiceCall.start();

		Set<Order> result = new HashSet<Order>();

		for (Order order : orderIdStore.values()) {

			if (StringUtils.isNotBlank(orderSearchFilter.getOrderNumber())
					&& order.getOrderNumber().contains(orderSearchFilter.getOrderNumber())) {
				result.add(order);
			}

			if (orderSearchFilter.getState() != null
					&& orderSearchFilter.getOrderNumber().equals(order)) {
				result.add(order);
			}

			if (orderHasOnePositionThatMatchArticleId(order, orderSearchFilter)) {
				result.add(order);
			}

		}

		return result;
	}

	private boolean orderHasOnePositionThatMatchArticleId(final Order order, final OrderSearchFilter orderSearchFilter) {

		ArticleId articleId = orderSearchFilter.getArticleId();
		if (articleId == null) {
			return false;
		}

		OrderPositions orderPositions = positionsIdStore.get(order.getId());

		for (Position pos : orderPositions.getPositions()) {
			if (pos.getArticleId().equals(articleId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.scenarioo.example.e4.services.OrderService#getPositions(org.scenarioo.example.e4.domain.OrderId)
	 */
	@Override
	public OrderPositionsTableviewDTO getOrderPositionsTableviewDTO(final OrderId id) {

		SimulateServiceCall.start();

		Order order = orderIdStore.get(id);
		OrderPositions orderPositions = positionsIdStore.get(id);
		Map<ArticleId, Article> articlesMap = articleIdStore.getArticles(orderPositions);

		LOGGER.info("getOrderPositionsTableviewDTO(" + id + ") : " + orderPositions);
		return new OrderPositionsTableviewDTO(order, orderPositions, articlesMap);
	}

	@Override
	public OrderPositionsTreeviewDTO getOrderPositionsTreeviewDTO(final OrderId id) {

		SimulateServiceCall.start();

		OrderPositions orderPositions = positionsIdStore.get(id);
		Map<ArticleId, Article> articlesMap = articleIdStore.getArticles(orderPositions);

		LOGGER.info("getOrderPositionsTreeviewDTO(" + id + ") : " + orderPositions);
		return new OrderPositionsTreeviewDTO(orderPositions, articlesMap);
	}

	/**
	 * We use eventAdmin due IEventBroker is not available in OSGI Context.
	 */
	private EventAdmin eventAdmin;

	private void postEvent(final String topic, final Object data) {
		Dictionary<String, Object> map = new Hashtable<String, Object>(2);
		map.put(EventConstants.EVENT_TOPIC, topic);
		map.put(IEventBroker.DATA, data);
		Event event = new Event(topic, map);
		eventAdmin.postEvent(event);
	}

	void registerEventAdmin(final EventAdmin admin) {
		this.eventAdmin = admin;
	}

	void unregisterEventAdmin(final EventAdmin admin) {
		this.eventAdmin = null;
	}
}
