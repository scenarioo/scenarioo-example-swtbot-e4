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

package org.scenarioo.example.e4.orders.handlers;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.events.OrderServiceEvents;
import org.scenarioo.example.e4.orders.OrderPluginImages;
import org.scenarioo.example.e4.orders.search.OrdersSearchDialog;
import org.scenarioo.example.e4.services.ArticleService;
import org.scenarioo.example.e4.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchOrderHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchOrderHandler.class);

	@Inject
	private IEventBroker eventBroker;

	@Execute
	public void execute(final Shell shell, final ArticleService articleService, final OrderService orderService) {

		LOGGER.info(this.getClass().getSimpleName() + " called");

		OrdersSearchDialog dialog = new OrdersSearchDialog(shell, articleService, orderService);
		Window.setDefaultImage(OrderPluginImages.ORDER_SEARCH.getImage());
		dialog.create();
		if (dialog.open() == Window.OK) {
			List<Order> ordersToOpen = dialog.getSelectedOrders();
			for (Order orderToOpen : ordersToOpen) {
				postEvent(orderToOpen);
			}
		}

	}

	private void postEvent(final Order data) {

		Dictionary<String, Object> map = new Hashtable<String, Object>(2);
		map.put(EventConstants.EVENT_TOPIC, OrderServiceEvents.TOPIC_ORDER_TREE_ADD);
		map.put(IEventBroker.DATA, data);
		Event event = new Event(OrderServiceEvents.TOPIC_ORDER_TREE_ADD, map);

		// Map<String, Order> map = new HashMap<String, Order>(1);
		// map.put(IEventBroker.DATA, data);
		// new Event(OrderServiceEvents.TOPIC_ORDER_TREE_ADD,
		// map)
		eventBroker.post(OrderServiceEvents.TOPIC_ORDER_TREE_ADD, data);
	}

}
