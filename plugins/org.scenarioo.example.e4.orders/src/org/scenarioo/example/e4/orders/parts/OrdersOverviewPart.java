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

package org.scenarioo.example.e4.orders.parts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerRow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.domain.OrderId;
import org.scenarioo.example.e4.domain.Position;
import org.scenarioo.example.e4.domain.PositionId;
import org.scenarioo.example.e4.dto.OrderPositionsTreeviewDTO;
import org.scenarioo.example.e4.dto.PositionWithArticleInfo;
import org.scenarioo.example.e4.events.OrderServiceEvents;
import org.scenarioo.example.e4.orders.ImagesOfThisPlugin;
import org.scenarioo.example.e4.orders.handlers.CreateOrderHandler;
import org.scenarioo.example.e4.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrdersOverviewPart {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateOrderHandler.class);

	// Services
	@Inject
	private OrderService orderService;

	// UI Widgets
	private TreeViewer viewer;

	// Images
	private Image orderImage;
	private Image orderNotFoundImage;
	private Image orderLoadedImage;
	private Image orderPositionImage;

	// Cached Positions
	private final Map<OrderId, OrderPositionsTreeviewDTO> cachedPositions = new HashMap<OrderId, OrderPositionsTreeviewDTO>();
	private final Map<PositionId, Order> parentOrders = new HashMap<PositionId, Order>();

	@SuppressWarnings("unchecked")
	private List<Order> getOrders() {
		return (List<Order>) viewer.getInput();
	}

	@Inject
	@Optional
	public void addOrder(@UIEventTopic(OrderServiceEvents.TOPIC_ORDERS_CREATE) final Order newOrder) {
		List<Order> orders = getOrders();
		orders.add(newOrder);
		viewer.setInput(orders);
		LOGGER.info("new " + newOrder + " added to orderOverview.");
	}

	@PostConstruct
	public void createControls(final Composite parent, final EMenuService menuService) {
		this.orderImage = ImagesOfThisPlugin.ORDER.getImage();
		this.orderNotFoundImage = ImagesOfThisPlugin.ORDER_NOT_FOUND.getImage();
		this.orderLoadedImage = ImagesOfThisPlugin.ORDER_LOADED.getImage();
		this.orderPositionImage = ImagesOfThisPlugin.ORDER_POSITION.getImage();

		// more code...
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setUseHashlookup(true);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(new ArrayList<Order>());
		viewer.addTreeListener(new ITreeViewerListener() {

			@Override
			public void treeExpanded(final TreeExpansionEvent event) {
				executeViewerUpdatePostponed(event.getElement());
			}

			@Override
			public void treeCollapsed(final TreeExpansionEvent event) {
				executeViewerUpdatePostponed(event.getElement());
			}

			/**
			 * We postpone the Update Execution a bit, otherwise we would get a "reentrant call"
			 */
			private void executeViewerUpdatePostponed(final Object element) {
				parent.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						viewer.update(element, null);
					}
				});
			}
		});
		// more code

		// register context menu on the tree
		menuService.registerContextMenu(viewer.getControl(),
				"org.scenarioo.example.e4.orders.treeview.order");
		LOGGER.info(this.getClass().getSimpleName() + " @PostConstruct method called.");
	}

	/**
	 * @param order
	 * @return
	 */
	private boolean orderHasLoaded(final Order order) {
		return cachedPositions.get(order.getId()) != null;
	}

	class ViewContentProvider implements ITreeContentProvider {

		@Override
		public void inputChanged(final Viewer v, final Object oldInput, final Object newInput) {
			// nothing
		}

		@Override
		public void dispose() {
			// nothing
		}

		@Override
		@SuppressWarnings("rawtypes")
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof Collection) {
				return ((Collection) inputElement).toArray();
			}
			return new Object[0];
		}

		@Override
		public Object[] getChildren(final Object parentElement) {
			if (parentElement instanceof Order) {
				Order order = (Order) parentElement;
				OrderPositionsTreeviewDTO orderPositionsTreeviewDTO = cachedPositions.get(order.getId());
				if (orderPositionsTreeviewDTO == null) {
					orderPositionsTreeviewDTO = orderService.getOrderPositionsTreeviewDTO(order.getId());
					cachedPositions.put(order.getId(), orderPositionsTreeviewDTO);
					for (Position pos : orderPositionsTreeviewDTO.getOrderPositions().getPositions()) {
						parentOrders.put(pos.getId(), order);
					}
				}
				return orderPositionsTreeviewDTO.getPositionsWithArticleInfo().toArray();
			}
			return new Object[0];
		}

		@Override
		public Object getParent(final Object element) {

			if (element instanceof PositionWithArticleInfo) {
				PositionWithArticleInfo pos = (PositionWithArticleInfo) element;
				return parentOrders.get(pos.getPosition().getId());
			}

			return null;
		}

		@Override
		public boolean hasChildren(final Object element) {
			if (element instanceof Order) {
				return true;
			}
			return false;
		}
	}

	class ViewLabelProvider extends StyledCellLabelProvider {
		@Override
		public void update(final ViewerCell cell) {
			Object element = cell.getElement();
			StyledString text = new StyledString();
			if (element instanceof Order) {
				Order order = (Order) element;
				if (orderHasLoaded(order)) {
					cell.setImage(orderLoadedImage);
				} else {
					cell.setImage(orderImage);
				}
				text.append(getTextForOrder(order, cell));
			} else {
				cell.setImage(orderPositionImage);
				PositionWithArticleInfo position = (PositionWithArticleInfo) element;
				text.append(position.getArticle().getArticleNumber());
			}
			cell.setText(text.toString());
			cell.setStyleRanges(text.getStyleRanges());
			super.update(cell);
		}
	}

	private StyledString getTextForOrder(final Order order, final ViewerCell cell) {

		StyledString text = new StyledString();
		text.append(order.getOrderNumber());

		if (orderHasLoaded(order) && !isNodeExpanded(cell)) {
			text.append(" (" + cachedPositions.get(order.getId()).getOrderPositions().getPositions().size()
					+ ") ", StyledString.COUNTER_STYLER);
		}
		return text;
	}

	private boolean isNodeExpanded(final ViewerCell cell) {
		ViewerRow viewerRow = cell.getViewerRow();
		TreeItem item = (TreeItem) viewerRow.getItem();
		return item.getExpanded();
	}

	@Focus
	public void setFocus() {
		this.viewer.getControl().setFocus();
	}

	@PreDestroy
	public void dispose() {
		this.orderImage.dispose();
		this.orderNotFoundImage.dispose();
		this.orderLoadedImage.dispose();
		this.orderPositionImage.dispose();
	}
}
