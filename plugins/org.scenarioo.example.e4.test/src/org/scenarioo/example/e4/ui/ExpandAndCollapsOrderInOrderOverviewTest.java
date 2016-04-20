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

package org.scenarioo.example.e4.ui;

import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scenarioo.example.e4.PageName;
import org.scenarioo.example.e4.ScenariooTestWrapper;
import org.scenarioo.example.e4.UseCaseName;
import org.scenarioo.example.e4.orders.parts.OrdersOverviewPart;
import org.scenarioo.example.e4.rules.InitOrderOverviewRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SWTBotJunit4ClassRunner.class)
public class ExpandAndCollapsOrderInOrderOverviewTest extends ScenariooTestWrapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExpandAndCollapsOrderInOrderOverviewTest.class);

	@Rule
	public InitOrderOverviewRule initOrderOverview = new InitOrderOverviewRule();

	/**
	 * @see org.scenarioo.example.e4.ScenariooTestWrapper#getUseCaseName()
	 */
	@Override
	protected UseCaseName getUseCaseName() {
		return UseCaseName.SHOW_ALL_ORDER_ITEMS;
	}

	/**
	 * @see org.scenarioo.example.e4.ScenariooTestWrapper#getScenariooDescription()
	 */
	@Override
	protected String getScenarioDescription() {
		return "Show the difference between order with loaded and not loaded Positions. "
				+ "Hint: The number in brackets represents the positions count of loaded orders";
	}

	@Test
	public void execute() {

		generateDocuForOrderOverview();

		SWTBotTree tree = bot.tree();
		final SWTBotTreeItem treeItem = tree.getTreeItem("Order 2");

		expandTreeItemAndGenerateDocu(treeItem);

		Assert.assertTrue(treeItem.isExpanded());

		collapseTreeItemAndGenerateDocu(treeItem);

		Assert.assertFalse(treeItem.isExpanded());

		LOGGER.info(getClass().getSimpleName() + " successful!");
	}

	/**
	 * @param treeItem
	 */
	private void collapseTreeItemAndGenerateDocu(final SWTBotTreeItem treeItem) {
		OrdersOverviewPart ordersOverviewPart = (OrdersOverviewPart) wbBot.partByTitle("Orders Overview").getPart()
				.getObject();
		ordersOverviewPart.setOverSteerNodeItemExpandedForTest(Boolean.FALSE);
		treeItem.collapse();
		bot.sleep(100);
		scenariooWriterHelper.writeStep("order_number_2_collapsed", PageName.ORDER_OVERVIEW, screenshot());
		ordersOverviewPart.setOverSteerNodeItemExpandedForTest(null);
	}

	/**
	 * @param treeItem
	 */
	private void expandTreeItemAndGenerateDocu(final SWTBotTreeItem treeItem) {
		OrdersOverviewPart ordersOverviewPart = (OrdersOverviewPart) wbBot.partByTitle("Orders Overview").getPart()
				.getObject();
		ordersOverviewPart.setOverSteerNodeItemExpandedForTest(Boolean.TRUE);
		treeItem.expand();
		bot.sleep(100);
		scenariooWriterHelper.writeStep("order_number_2_expanded", PageName.ORDER_OVERVIEW, screenshot());
		ordersOverviewPart.setOverSteerNodeItemExpandedForTest(null);
	}

}
