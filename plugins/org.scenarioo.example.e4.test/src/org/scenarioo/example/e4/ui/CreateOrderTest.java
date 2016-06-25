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
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.scenarioo.example.e4.PageName;
import org.scenarioo.example.e4.ScenariooTestWrapper;
import org.scenarioo.example.e4.UseCaseName;
import org.scenarioo.example.e4.rules.DeleteOrderRule;

@RunWith(SWTBotJunit4ClassRunner.class)
public class CreateOrderTest extends ScenariooTestWrapper {

	private static final String ORDER_NUMBER = "Huhu";

	/**
	 * @see org.scenarioo.example.e4.ScenariooTestWrapper#getUseCaseName()
	 */
	@Override
	protected UseCaseName getUseCaseName() {
		return UseCaseName.CREATE_ORDER;
	}

	/**
	 * 
	 * @see org.scenarioo.example.e4.ScenariooTestWrapper#getScenarioDescription()
	 */
	@Override
	protected String getScenarioDescription() {
		return "Steps through a wizard to create a new order with one order position. "
				+ "On the first page the order details are entered. On the second page "
				+ "the user create a new entry in the orderposition table.";
	}

	@Override
	protected RuleChain appendInnerMostRules(final RuleChain outerRuleChain) {
		return outerRuleChain.around(new DeleteOrderRule(ORDER_NUMBER));
	}

	@Test
	public void execute() {

		generateInitialViewDocuForOrderOverview();

		startNewOrderDialogAndGenerateDocu();

		enterOrderNumberAndGenerateDocu();

		clickNextPageAndGenerateDocu();

		addPositionAndGenerateDocu();

		SWTBotTable table = enterAmountForFirstRowAndGenerateDocu();

		selectItemForFirstRowAndGenerateDocu(table);

		finishNewOrderDialogAndGenerateDocu();

		// Assert 1 more Orders available in OrderOverview
		SWTBotTree tree = bot.tree();
		Assert.assertEquals(1, tree.rowCount());

		bot.sleep(1000);
	}

	private void finishNewOrderDialogAndGenerateDocu() {
		// click Finish
		bot.button("Finish").click();
		bot.sleep(100);
		scenariooWriterHelper.writeStep("order_visible_in_order_overview", PageName.ORDER_OVERVIEW, screenshot());
	}

	/**
	 * @param table
	 */
	private void selectItemForFirstRowAndGenerateDocu(final SWTBotTable table) {
		bot.table().click(0, 2);
		bot.sleep(100);
		bot.ccomboBox(0).setSelection(6);
		table.click(0, 3); // Input is accepted after focus lost
		bot.sleep(100);
		scenariooWriterHelper.writeStep("item_selected", PageName.ORDER_NEW_2, screenshot());
	}

	/**
	 * @return
	 */
	private SWTBotTable enterAmountForFirstRowAndGenerateDocu() {
		// Select Item in Table
		SWTBotTable table = bot.table();
		table.click(0, 4);
		bot.text(1).setText("3");
		bot.sleep(100);
		table.click(0, 3); // Input is accepted after focus lost
		bot.sleep(100);
		scenariooWriterHelper.writeStep("position_count_entered", PageName.ORDER_NEW_2, screenshot());
		return table;
	}

	private void addPositionAndGenerateDocu() {
		bot.buttonWithTooltip("Add Position").click();
		bot.sleep(100);
		scenariooWriterHelper.writeStep("add_position", PageName.ORDER_NEW_2, screenshot());
	}

	private void clickNextPageAndGenerateDocu() {
		bot.button("Next >").click();
		bot.sleep(100);
		scenariooWriterHelper.writeStep("new_order_page_2", PageName.ORDER_NEW_2, screenshot());
	}

	private void enterOrderNumberAndGenerateDocu() {
		SWTBotText text = bot.textWithLabel("&Order Number");
		text.typeText(ORDER_NUMBER);
		bot.sleep(100);
		scenariooWriterHelper.writeStep("order_number_entered", PageName.ORDER_NEW_1, screenshot());
	}

	private void startNewOrderDialogAndGenerateDocu() {
		bot.toolbarButtonWithTooltip("Create Order").click();
		bot.sleep(100);
		scenariooWriterHelper.writeStep("new_order_page_1", PageName.ORDER_NEW_1, screenshot());
	}
}
