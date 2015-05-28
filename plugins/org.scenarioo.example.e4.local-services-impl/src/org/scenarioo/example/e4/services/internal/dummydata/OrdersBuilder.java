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

package org.scenarioo.example.e4.services.internal.dummydata;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.scenarioo.example.e4.domain.Article;
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.domain.OrderId;
import org.scenarioo.example.e4.domain.OrderPositions;
import org.scenarioo.example.e4.domain.Position;
import org.scenarioo.example.e4.services.internal.Counter;
import org.scenarioo.example.e4.services.internal.idstores.ArticleIdStore;
import org.scenarioo.example.e4.services.internal.idstores.IdStore;

public class OrdersBuilder {

	private static final OrdersBuilder INSTANCE = new OrdersBuilder();

	private final Counter counter;
	private final ArticleIdStore articleIdStore;
	private final IdStore<OrderId, Order> orderIdStore;
	private final IdStore<OrderId, OrderPositions> positionsIdStore;

	private OrdersBuilder() {
		this.counter = Counter.getInstance();
		this.articleIdStore = ArticleIdStore.getInstance();
		this.orderIdStore = IdStore.getInstance(Order.class);
		this.positionsIdStore = IdStore.getInstance(OrderPositions.class);
	}

	public static OrdersBuilder getInstance() {
		return INSTANCE;
	}

	public void createOrder(final String orderNumber) {
		Order order = new Order();
		order.generateAndSetId(counter);
		order.setDeliveryDate(createDateTomorrow());
		order.setOrderNumber(orderNumber);
		order.setRecipientFullName("Any Name");
		orderIdStore.add(order);
		createPositions(order);
	}

	private Date createDateTomorrow() {
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}

	private void createPositions(final Order order) {
		int numberOfPositions = getNumberOfPositions();
		List<Article> allArticles = new ArrayList<Article>(articleIdStore.values());

		OrderPositions orderPositions = new OrderPositions();
		orderPositions.setOrderReference(order);

		for (int i = 0; i < numberOfPositions; i++) {
			Position pos = new Position();
			int index = getRandomNumber(allArticles.size());
			pos.setArticleId(allArticles.get(index).getId());
			orderPositions.addPosition(pos);
		}
		positionsIdStore.add(orderPositions);
	}

	private int getRandomNumber(final int size) {
		double random = Math.random();
		double result = random * size;
		return (int) result;
	}

	private int getNumberOfPositions() {
		double random = Math.random();
		if (random > 0.8) {
			return 5;
		} else if (random > 0.6) {
			return 4;
		} else if (random > 0.4) {
			return 3;
		} else if (random > 0.2) {
			return 2;
		} else {
			return 1;
		}
	}
}
