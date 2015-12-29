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

import org.eclipse.swtbot.e4.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scenarioo.example.e4.BaseSWTBotTest;
import org.scenarioo.example.e4.ScenariooTestWrapper;
import org.scenarioo.example.e4.rules.InitOrderOverviewRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SWTBotJunit4ClassRunner.class)
public class RemoveOrderTest extends ScenariooTestWrapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoveOrderTest.class);

	@Rule
	public InitOrderOverviewRule initOrderOverview = new InitOrderOverviewRule();

	@Test
	public void execute() {

		generateDocuForInitialView();

		SWTBotTree tree = bot.tree();

		SWTBotMenu menu = getContextMenuAndGenerateDocu(tree, "Order 2", "Remove Order");
		LOGGER.info(menu.toString());

		clickMenuEntryAndGenerateDocu(menu);

		// Assert 3 Orders available in OrderOverview
		Assert.assertEquals(initOrderOverview.getInitializedOrdersInOrderOverview() - 1, tree.rowCount());

		verifyTheOrderIsNotDeleted();

		LOGGER.info(getClass().getSimpleName() + " successful!");

		throw new IllegalStateException("test the statuses");
	}

	private void verifyTheOrderIsNotDeleted() {

		SWTBotView view = BaseSWTBotTest.wbBot.partById(PART_ID_ORDER_OVERVIEW);
		view.toolbarButton("Search Order").click();
		SWTBotText text = bot.textWithLabel("&Order Number");
		text.typeText("Order");
		bot.buttonWithTooltip("Start Search").click();

		SWTBotTable table = bot.table();
		SWTBotTableItem item = table.getTableItem("Order 2");

		LOGGER.info(item.toString());
		Assert.assertNotNull(item);

		bot.button("Cancel").click();
	}

}
