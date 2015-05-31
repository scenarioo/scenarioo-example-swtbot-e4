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
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
import org.scenarioo.example.e4.domain.OrderState;
import org.scenarioo.example.e4.domain.Position;
import org.scenarioo.example.e4.domain.PositionId;
import org.scenarioo.example.e4.dto.OrderPositionsTreeviewDTO;
import org.scenarioo.example.e4.dto.PositionWithArticleInfo;
import org.scenarioo.example.e4.dto.PositionWithOrderAndArticleInfoDTO;
import org.scenarioo.example.e4.events.OrderServiceEvents;
import org.scenarioo.example.e4.orders.OrderPluginImages;
import org.scenarioo.example.e4.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrdersOverviewPart {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrdersOverviewPart.class);

	// Services
	@Inject
	private OrderService orderService;

	@Inject
	private ESelectionService selectionService;

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
	private List<Order> getTreeModel() {
		return (List<Order>) viewer.getInput();
	}

	@Inject
	@Optional
	public void subscribeToOrderTreeAddTopic(@UIEventTopic(OrderServiceEvents.TOPIC_ORDER_TREE_ADD) final Order newOrder) {
		List<Order> orders = getTreeModel();
		// prevent from duplicates!
		if (!orders.contains(newOrder)) {
			orders.add(newOrder);
			viewer.setInput(orders);
			LOGGER.info("new " + newOrder + " added to orderOverview.");
		}
	}

	@Inject
	@Optional
	public void subscribeToOrderTreeRemoveTopic(
			@UIEventTopic(OrderServiceEvents.TOPIC_ORDER_TREE_REMOVE) final Order removableOrder) {

		cleanUpOrderOrder(removableOrder);
		List<Order> orders = getTreeModel();
		// prevent from duplicates!
		if (orders.contains(removableOrder)) {
			orders.remove(removableOrder);
			viewer.setInput(orders);
			LOGGER.info("order " + removableOrder + " has been removed from orderOverview.");
		}
	}

	@Inject
	@Optional
	public void subscribeToOrderDeletedTopic(
			@UIEventTopic(OrderServiceEvents.TOPIC_ORDER_DELETE) final Order deletedOrder) {
		cleanUpOrderOrder(deletedOrder);
		updateOrderInTreeModel(deletedOrder);
	}

	private void cleanUpOrderOrder(final Order order) {
		OrderPositionsTreeviewDTO orderPositions = cachedPositions.remove(order.getId());
		for (Position position : orderPositions.getOrderPositions().getPositions()) {
			parentOrders.remove(position.getId());
		}
	}

	@Inject
	@Optional
	public void subscribeToOrderUpdateTopic(
			@UIEventTopic(OrderServiceEvents.TOPIC_ORDER_UPDATE) final Order updatedOrder) {
		updateOrderInTreeModel(updatedOrder);
	}

	private void updateOrderInTreeModel(final Order order) {
		List<Order> orders = getTreeModel();
		// prevent from duplicates!
		if (orders.contains(order)) {
			int index = orders.indexOf(order);
			orders.remove(order);
			orders.add(index, order);
			viewer.setInput(orders);
			LOGGER.info("order " + order + " has been refreshed in Treeview.");
		}
	}

	@Inject
	@Optional
	public void subscribeToPositionTreeRemoveTopic(
			@UIEventTopic(OrderServiceEvents.TOPIC_POSITION_TREE_REMOVE) final PositionWithOrderAndArticleInfoDTO deletedPosition) {
		OrderId orderIdInTreeLoaded = deletedPosition.getOrderId();
		if (cachedPositions.containsKey(orderIdInTreeLoaded)) {
			OrderPositionsTreeviewDTO children = cachedPositions.get(orderIdInTreeLoaded);
			if (!children.removePosition(deletedPosition.getPosition())) {
				LOGGER.info("Position " + deletedPosition + " in OrderTreeView already removed."
						+ " You are firing too many events!");
			} else {
				LOGGER.info("Position " + deletedPosition.getPosition() + " from OrderTreeView removed.");
			}
			viewer.refresh(deletedPosition.getOrder(), false);
		}
	}

	@Inject
	@Optional
	public void subscribeToPositionUpdateTopic(
			@UIEventTopic(OrderServiceEvents.TOPIC_POSITION_TREE_UPDATE) final PositionWithOrderAndArticleInfoDTO updatedOrder) {
		addOrUpdatePositionInTree(updatedOrder);
	}

	@Inject
	@Optional
	public void subscribeToPositionAddTopic(
			@UIEventTopic(OrderServiceEvents.TOPIC_POSITION_TREE_ADD) final PositionWithOrderAndArticleInfoDTO addedPosition) {
		addOrUpdatePositionInTree(addedPosition);
	}

	private void addOrUpdatePositionInTree(final PositionWithOrderAndArticleInfoDTO addedPosition) {
		OrderId orderIdInTreeLoaded = addedPosition.getOrderId();
		if (cachedPositions.containsKey(orderIdInTreeLoaded)) {
			OrderPositionsTreeviewDTO children = cachedPositions.get(orderIdInTreeLoaded);
			children.addOrUpdatePosition(addedPosition);
			LOGGER.info("Position " + addedPosition.getPosition() + " in OrderTreeView added/updated.");
			viewer.update(addedPosition.getPosition(), null);
		}
	}

	@PostConstruct
	public void createControls(final Composite parent, final EMenuService menuService) {
		this.orderImage = OrderPluginImages.ORDER.getImage();
		this.orderNotFoundImage = OrderPluginImages.ORDER_NOT_FOUND.getImage();
		this.orderLoadedImage = OrderPluginImages.ORDER_LOADED.getImage();
		this.orderPositionImage = OrderPluginImages.ORDER_POSITION.getImage();

		// more code...
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setUseHashlookup(true);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(new ArrayList<Order>());
		viewer.addSelectionChangedListener(new TreeSelectionListener());
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
				"org.scenarioo.example.e4.orders.popupmenu.ordersoverview.order");

		LOGGER.info(this.getClass().getSimpleName() + " @PostConstruct method called.");
	}

	/**
	 * @param order
	 * @return
	 */
	private boolean orderHasLoaded(final Order order) {
		return cachedPositions.get(order.getId()) != null;
	}

	private class ViewContentProvider implements ITreeContentProvider {

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
				return getOrder((PositionWithArticleInfo) element);
			}
			return null;
		}

		@Override
		public boolean hasChildren(final Object element) {
			if (element instanceof Order) {
				Order order = (Order) element;
				if (OrderState.NOT_FOUND.equals(order.getState())) {
					return false;
				}
				return true;
			}
			return false;
		}
	}

	private Order getOrder(final PositionWithArticleInfo pos) {
		return parentOrders.get(pos.getPosition().getId());
	}

	private class ViewLabelProvider extends StyledCellLabelProvider {
		@Override
		public void update(final ViewerCell cell) {
			Object element = cell.getElement();
			StyledString text = new StyledString();
			if (element instanceof Order) {
				Order order = (Order) element;
				if (OrderState.NOT_FOUND.equals(order.getState())) {
					cell.setImage(orderNotFoundImage);
				} else if (orderHasLoaded(order)) {
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

	private class TreeSelectionListener implements ISelectionChangedListener {
		@Override
		public void selectionChanged(final SelectionChangedEvent event) {
			Object first = ((IStructuredSelection) event.getSelection()).getFirstElement();
			if (first instanceof PositionWithArticleInfo) {
				PositionWithArticleInfo posWithArticleInfo = (PositionWithArticleInfo) first;
				Order order = getOrder(posWithArticleInfo);
				selectionService.setSelection(new PositionWithOrderAndArticleInfoDTO(posWithArticleInfo, order));
			} else {
				selectionService.setSelection(first);
			}
		}
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
