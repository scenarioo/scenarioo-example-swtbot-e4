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

import org.eclipse.swtbot.e4.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.scenarioo.example.e4.PageName;
import org.scenarioo.example.e4.ScenariooWriterHelper;
import org.scenarioo.example.e4.orders.parts.OrdersOverviewPart;

public class OrderOverviewPageObject extends BasePageObject {

	// private static final Logger LOGGER = LoggerFactory.getLogger(OrderOverviewPageObject.class);

	private final SWTBotTree tree;
	private final SWTBotView swtBotView;

	/**
	 * @param scenariooWriterHelper
	 */
	public OrderOverviewPageObject(final ScenariooWriterHelper scenariooWriterHelper) {
		super(scenariooWriterHelper);
		this.tree = bot.tree();
		this.swtBotView = wbBot.partByTitle("Orders Overview");
	}

	public String getArticleNameOfFirstExistingPosition(final String orderNumber) {
		return getFirstTreeItemForOrder(orderNumber).getText();
	}

	public String getArticleNameOfLastExistingPosition(final String orderNumber) {
		return getLastTreeItemForOrder(orderNumber).getText();
	}

	public void openOrderDetails(final String orderNumber) {
		SWTBotTreeItem orderNode = tree.getTreeItem(orderNumber);
		createOrderDetailsMenuHandler().openContextMenuAndClickAction(orderNode);
	}

	public void openPositionDetailsOfFirstExistingPosition(final String orderNumber, final String docuEndDescription) {
		SWTBotTreeItem firstPositionNode = getFirstTreeItemForOrder(orderNumber);
		OrderOverviewContextMenuHandler positionDetailsMenuHandler = createPositionDetailsMenuHandler();
		positionDetailsMenuHandler.setDocuEndDescription(docuEndDescription);
		positionDetailsMenuHandler.openContextMenuAndClickAction(firstPositionNode);
	}

	private SWTBotTreeItem getFirstTreeItemForOrder(final String orderNumber) {
		SWTBotTreeItem orderTreeItem = tree.getTreeItem(orderNumber);
		return orderTreeItem.getItems()[0];
	}

	public void openPositionDetailsOfLastExistingPosition(final String orderNumber) {
		SWTBotTreeItem lastOrderPositionNode = getLastTreeItemForOrder(orderNumber);
		createPositionDetailsMenuHandler().openContextMenuAndClickAction(lastOrderPositionNode);
	}

	private SWTBotTreeItem getLastTreeItemForOrder(final String orderNumber) {
		SWTBotTreeItem orderTreeItem = tree.getTreeItem(orderNumber);
		int length = orderTreeItem.getItems().length;
		return orderTreeItem.getItems()[length - 1];
	}

	public void addPositionForOrderViaContextMenuAndGenerateDocu(final String orderNumber) {
		SWTBotTreeItem orderTreeItem = tree.getTreeItem(orderNumber);
		createAddPositionMenuHandler().openContextMenuAndClickAction(orderTreeItem);
	}

	public void addPositionForOrderViaContextMenu(final String orderNumber, final String docuEndDescription) {
		OrderOverviewContextMenuHandler addPositionMenuHandler = createAddPositionMenuHandler();
		addPositionMenuHandler.setDocuEndDescription(docuEndDescription);
		SWTBotTreeItem orderTreeItem = tree.getTreeItem(orderNumber);
		addPositionMenuHandler.openContextMenuAndClickAction(orderTreeItem);
	}

	public void addPositionViaContextMenuWithoutDocuGeneration(final String orderNumber) {
		OrderOverviewContextMenuHandler addPositionMenuHandler = createAddPositionMenuHandler();
		addPositionMenuHandler.supressDocuGeneration();
		SWTBotTreeItem orderTreeItem = tree.getTreeItem(orderNumber);
		addPositionMenuHandler.openContextMenuAndClickAction(orderTreeItem);
	}

	public void expandTreeForOrder(final String orderNumber, final boolean generateDocu) {
		final SWTBotTreeItem orderTreeItem = tree.getTreeItem(orderNumber);
		expandTreeItem(orderTreeItem, generateDocu);
	}

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

	private OrderOverviewContextMenuHandler createOrderDetailsMenuHandler() {
		return new OrderOverviewContextMenuHandler(this, ContextMenuAction.OPEN_ORDER_DETAILS);
	}

	private OrderOverviewContextMenuHandler createPositionDetailsMenuHandler() {
		return new OrderOverviewContextMenuHandler(this, ContextMenuAction.OPEN_POSITION_DETAILS);
	}

	private OrderOverviewContextMenuHandler createAddPositionMenuHandler() {
		return new OrderOverviewContextMenuHandler(this, ContextMenuAction.ADD_POSITION);
	}

	public void generateDocu(final String title) {
		bot.sleep(100);
		scenariooWriterHelper.writeStep(title, PageName.ORDER_OVERVIEW, screenshot());
	}

	public void generateDocu(final String title, final String description) {
		bot.sleep(100);
		scenariooWriterHelper.writeStep(title, description, PageName.ORDER_OVERVIEW, screenshot());
	}

	public void close() {
		swtBotView.close();
	}

	SWTBotTree getTree() {
		return tree;
	}
}
