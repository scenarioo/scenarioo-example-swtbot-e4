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

package org.scenarioo.example.e4.pages;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.scenarioo.example.e4.PageName;
import org.scenarioo.example.e4.ScenariooWriterHelper;
import org.scenarioo.example.e4.orders.parts.OrdersOverviewPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderOverviewPageObject extends PageObject {

	private static final String CONTEXT_MENU_OPENED_DEFAULT_DOCU_TITLE = "context_menu_opened";

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderOverviewPageObject.class);

	public enum ContextMenuActionRef {
		ADD_POSITION("Add Position"),
		EDIT_POSITION("Edit Position"),
		DELETE_POSITION("Delete Position");

		private String actionName;

		private ContextMenuActionRef(final String actionName) {
			this.actionName = actionName;
		}
	}

	private final SWTBotTree tree;

	private final OrdersOverviewPart ordersOverviewPart;

	/**
	 * @param scenariooWriterHelper
	 */
	public OrderOverviewPageObject(final ScenariooWriterHelper scenariooWriterHelper) {
		super(scenariooWriterHelper);
		this.tree = bot.tree();
		this.ordersOverviewPart = (OrdersOverviewPart) wbBot.partByTitle("Orders Overview")
				.getPart().getObject();
	}

	public String getArticleNameOfFirstExistingPosition(final String orderNumber) {
		SWTBotTreeItem orderTreeItem = tree.getTreeItem(orderNumber);
		SWTBotTreeItem firstPositionNode = orderTreeItem.getItems()[0];
		return firstPositionNode.getText();
	}

	/**
	 * @param tree
	 * @param orderTreeItem
	 */
	public void openPositionDetailsOfFirstExistingPosition(final String orderNumber, final boolean generateDocu) {
		SWTBotTreeItem orderTreeItem = tree.getTreeItem(orderNumber);
		SWTBotTreeItem firstPositionNode = orderTreeItem.getItems()[0];
		openContextMenuAndClickAction(firstPositionNode, ContextMenuActionRef.EDIT_POSITION, generateDocu,
				"context_menu_action_edit_position_clicked");
	}

	public void openPositionDetailsOfLastExistingPosition(final String orderNumber, final boolean generateDocu) {
		SWTBotTreeItem orderTreeItem = tree.getTreeItem(orderNumber);
		int length = orderTreeItem.getItems().length;
		SWTBotTreeItem lastOrderPositionNode = orderTreeItem.getItems()[length - 1];
		openContextMenuAndClickAction(lastOrderPositionNode, ContextMenuActionRef.EDIT_POSITION, generateDocu,
				"context_menu_action_edit_position_clicked");
	}

	public void addPositionForOrderViaContextMenuAndGenerateDocu(final String orderNumber) {
		addPositionForOrderViaContextMenu(orderNumber, "context_menu_action_add_position_clicked");
	}

	public void addPositionForOrderViaContextMenu(final String orderNumber, final String secondDocuTitle) {
		SWTBotTreeItem orderTreeItem = tree.getTreeItem(orderNumber);
		openContextMenuAndClickAction(orderTreeItem, ContextMenuActionRef.ADD_POSITION, true, secondDocuTitle);
	}

	/**
	 * Without Docu generation
	 * 
	 * @param orderNumber
	 */
	public void addPositionForOrderViaContextMenu(final String orderNumber) {
		SWTBotTreeItem orderTreeItem = tree.getTreeItem(orderNumber);
		openContextMenuAndClickAction(orderTreeItem, ContextMenuActionRef.ADD_POSITION, false, null);
	}

	/**
	 * @param lastOrderPositionNode
	 */
	private void openContextMenuAndClickAction(final SWTBotTreeItem treeItemNode,
			final ContextMenuActionRef contextMenuAction, final boolean generateDocu,
			final String clickedMenuActionDocuTitle) {
		openContextMenuForTreeItem(treeItemNode);
		if (generateDocu) {
			generateDocu(CONTEXT_MENU_OPENED_DEFAULT_DOCU_TITLE);
		}
		clickContextMenuActionForTreeItem(treeItemNode, contextMenuAction);
		if (generateDocu) {
			generateDocu(clickedMenuActionDocuTitle);
		}
	}

	public void expandTreeForOrder(final String orderNumber, final boolean generateDocu) {
		final SWTBotTreeItem orderTreeItem = tree.getTreeItem(orderNumber);
		expandTreeItem(orderTreeItem, generateDocu);
	}

	/**
	 * @param treeItem
	 */
	private void expandTreeItem(final SWTBotTreeItem treeItem, final boolean generateDocu) {
		OrdersOverviewPart ordersOverviewPart = (OrdersOverviewPart) wbBot.partByTitle("Orders Overview").getPart()
				.getObject();
		ordersOverviewPart.setOverSteerNodeItemExpandedForTest(Boolean.TRUE);
		treeItem.expand();
		ordersOverviewPart.setOverSteerNodeItemExpandedForTest(null);
		if (generateDocu) {
			generateDocu("order_tree_node_expanded");
		}
	}

	// public void openContextMenuForOrder(final String orderNumber) {
	//
	// final SWTBotTreeItem treeItem = tree.getTreeItem(orderNumber);
	// openContextMenuForTreeItem(treeItem);
	// }

	private void openContextMenuForTreeItem(final SWTBotTreeItem treeItem) {

		treeItem.select();
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				Menu menu = tree.widget.getMenu();
				Point menuItemLocation = getMenuItemLocation(treeItem);
				menu.setLocation(menuItemLocation.x, menuItemLocation.y);
				menu.setVisible(true);
			}
		});
		bot.sleep(100);
		LOGGER.info("popup is opened: " + treeItem.getText());
	}

	private Point getMenuItemLocation(final SWTBotTreeItem treeItem) {
		Point absolutLocation = treeItem.widget.getParent().toDisplay(0, 0);
		Rectangle bounds = treeItem.widget.getBounds();
		System.out.println("Relative Bounds: " + bounds.toString());
		return new Point(absolutLocation.x + bounds.x + bounds.width, absolutLocation.y
				+ bounds.y + bounds.height / 2);
	}

	// public void clickContextMenuActionForOrder(final String orderNumber,
	// final ContextMenuActionRef contextMenuActionRef) {
	// clickContextMenuActionForTreeItem(tree.getTreeItem(orderNumber), contextMenuActionRef);
	// }

	private void clickContextMenuActionForTreeItem(final SWTBotTreeItem treeItem,
			final ContextMenuActionRef contextMenuActionRef) {
		SWTBotMenu contextMenuAction = treeItem.contextMenu(contextMenuActionRef.actionName);
		contextMenuAction.click();
		closeContextMenu(contextMenuAction);
		bot.sleep(500);
		LOGGER.info("menu action clicked and context menu closed for: " + treeItem.getText());
	}

	private void closeContextMenu(final SWTBotMenu menu) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				menu.widget.getParent().setVisible(false);
			}
		});
	}

	public void generateDocu(final String title) {
		bot.sleep(100);
		scenariooWriterHelper.writeStep(title, PageName.ORDER_OVERVIEW, screenshot());
	}

}
