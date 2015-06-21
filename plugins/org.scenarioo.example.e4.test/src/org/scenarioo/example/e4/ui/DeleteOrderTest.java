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
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SWTBotJunit4ClassRunner.class)
public class DeleteOrderTest extends OrderOverviewWithSomeOrders {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteOrderTest.class);

	private static final String ORDER_NUMBER_TEMP = "Temp";

	@BeforeClass
	public static void createOrderTemp() {

		// add new Order, As we don't want to interfere with other test cases
		bot.toolbarButtonWithTooltip("Create Order").click();
		SWTBotText text = bot.textWithLabel("&Order Number");
		text.typeText(ORDER_NUMBER_TEMP);
		bot.button("Next >").click();
		bot.buttonWithTooltip("Add Position").click();

		// Select Item in Table
		SWTBotTable table = bot.table();

		// Position Amount
		table.click(0, 4);
		bot.sleep(1000);
		bot.text(1).setText("2");

		// Select Item
		bot.table().click(0, 2);
		bot.sleep(1000);
		bot.ccomboBox(0).setSelection(13);

		// loose Focus
		table.click(0, 3);
		bot.sleep(1000);

		// click Finish
		bot.button("Finish").click();
	}

	@Test
	public void execute() {

		SWTBotTree tree = bot.tree();
		SWTBotMenu menu = tree.getTreeItem(ORDER_NUMBER_TEMP).contextMenu("Delete Order").click();
		LOGGER.info(menu.toString());

		bot.sleep(1000);

		// Assert 5 Orders available in OrderOverview
		Assert.assertEquals(initializedOrdersInOrderOverview + 1, tree.rowCount());

		// Verify Order has been deleted
		verifyTempOrderHasBeenDeleted();

		LOGGER.info(getClass().getSimpleName() + " successful!");
	}

	private void verifyTempOrderHasBeenDeleted() {

		SWTBotView view = wbBot.partById(PART_ID_ORDER_OVERVIEW);
		view.toolbarButton("Search Order").click();
		SWTBotText text = bot.textWithLabel("&Order Number");
		text.typeText(ORDER_NUMBER_TEMP);
		bot.buttonWithTooltip("Start Search").click();

		SWTBotTable table = bot.table();
		int rowCountAfterDelete = table.rowCount();

		Assert.assertEquals(0, rowCountAfterDelete);

		bot.button("Cancel").click();
	}
}
