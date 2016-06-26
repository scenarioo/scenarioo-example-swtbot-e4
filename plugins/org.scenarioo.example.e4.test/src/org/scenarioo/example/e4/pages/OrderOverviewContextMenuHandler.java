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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderOverviewContextMenuHandler extends PageObject {

	private static final String CONTEXT_MENU_STEP_ONE_DOCU_TITLE = "right_click_on_selected_tree_node";
	private static final String CONTEXT_MENU_DOCU_DESCRIPTION = "context menu opened";
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderOverviewPageObject.class);

	private final SWTBotTree orderOverviewTree;
	private final ContextMenuAction contextMenuAction;
	private boolean generateDocu = true;
	private String docuEndDescription;

	public OrderOverviewContextMenuHandler(final OrderOverviewPageObject orderOverviewPageObject,
			final ContextMenuAction contextMenuAction) {
		super(orderOverviewPageObject.scenariooWriterHelper);
		this.orderOverviewTree = orderOverviewPageObject.getTree();
		this.contextMenuAction = contextMenuAction;
		this.docuEndDescription = contextMenuAction.getDefaultDocuDescription();
	}

	public void setDocuEndDescription(final String docuEndDescription) {
		this.docuEndDescription = docuEndDescription;
	}

	public void supressDocuGeneration() {
		generateDocu = false;
	}

	public void openContextMenuAndClickAction(final SWTBotTreeItem treeItemNode) {
		openContextMenuForTreeItem(treeItemNode);
		if (generateDocu) {
			generateDocu(CONTEXT_MENU_STEP_ONE_DOCU_TITLE, CONTEXT_MENU_DOCU_DESCRIPTION);
		}
		clickContextMenuActionForTreeItem(treeItemNode, contextMenuAction);
		if (generateDocu) {
			generateDocu(contextMenuAction.getDocuTitle(), docuEndDescription);
		}
	}

	private void openContextMenuForTreeItem(final SWTBotTreeItem treeItem) {

		treeItem.select();
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				Menu menu = orderOverviewTree.widget.getMenu();
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

	private void clickContextMenuActionForTreeItem(final SWTBotTreeItem treeItem,
			final ContextMenuAction contextMenuAction) {
		SWTBotMenu botMenuAction = treeItem.contextMenu(contextMenuAction.getActionName());
		botMenuAction.click();
		closeContextMenu(botMenuAction);
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

	private void generateDocu(final String title, final String description) {
		bot.sleep(100);
		scenariooWriterHelper.writeStep(title, description, PageName.ORDER_OVERVIEW, screenshot());
	}
}
