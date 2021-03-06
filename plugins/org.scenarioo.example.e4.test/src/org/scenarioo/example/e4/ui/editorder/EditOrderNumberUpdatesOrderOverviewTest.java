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

package org.scenarioo.example.e4.ui.editorder;

import org.eclipse.swtbot.e4.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.scenarioo.example.e4.PageName;
import org.scenarioo.example.e4.ScenariooTestWrapper;
import org.scenarioo.example.e4.UseCaseName;
import org.scenarioo.example.e4.rules.CreateTempOrderRule;
import org.scenarioo.example.e4.rules.DeleteOrderRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SWTBotJunit4ClassRunner.class)
public class EditOrderNumberUpdatesOrderOverviewTest extends ScenariooTestWrapper {

	private static final String TARGET_ORDER_NUMBER = "New Order Number";

	private static final Logger LOGGER = LoggerFactory.getLogger(EditOrderNumberUpdatesOrderOverviewTest.class);

	private static final String ORDER_STATE = "New";

	@Override
	protected RuleChain appendInnerMostRules(final RuleChain outerRuleChain) {
		return outerRuleChain.around(new DeleteOrderRule(TARGET_ORDER_NUMBER));
	}

	@Rule
	public CreateTempOrderRule createTempOrderRule = new CreateTempOrderRule();

	/**
	 * @see org.scenarioo.example.e4.ScenariooTestWrapper#getUseCaseName()
	 */
	@Override
	protected UseCaseName getUseCaseName() {
		return UseCaseName.EDIT_ORDER;
	}

	/**
	 * @see org.scenarioo.example.e4.ScenariooTestWrapper#getScenariooDescription()
	 */
	@Override
	protected String getScenarioDescription() {
		return "A change of the order number in the order detail view updates the "
				+ "corresponding order in the order overview.";
	}

	@Test
	public void execute() {

		clickContextMenuActionForOrder(bot.tree(), CreateTempOrderRule.ORDER_NUMBER_TEMP, "Edit Order");

		generateDocuForInitialView(PageName.ORDER_DETAIL);

		SWTBotView partByTitle = wbBot.partByTitle(CreateTempOrderRule.ORDER_NUMBER_TEMP + " - " + ORDER_STATE);
		Assert.assertNotNull(partByTitle);

		enterOrderNumberAndGenerateDocu();

		saveAllAndGenerateDocu();

		// close order details
		partByTitle.close();

		LOGGER.info(getClass().getSimpleName() + " successful!");
	}

	private void enterOrderNumberAndGenerateDocu() {
		SWTBotText text = bot.textWithLabel("&Order Number");
		text.setText("");
		text.typeText(TARGET_ORDER_NUMBER);
		bot.sleep(100);
		scenariooWriterHelper.writeStep("order_number_entered", PageName.ORDER_DETAIL, screenshot());
	}

	private void saveAllAndGenerateDocu() {
		bot.toolbarButtonWithTooltip("Save All").click();
		bot.sleep(100);
		scenariooWriterHelper.writeStep("save_all_clicked", PageName.ORDER_DETAIL, screenshot());
	}

}
