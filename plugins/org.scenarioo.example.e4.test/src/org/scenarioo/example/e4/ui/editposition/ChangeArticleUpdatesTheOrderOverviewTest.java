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

package org.scenarioo.example.e4.ui.editposition;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.e4.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.scenarioo.example.e4.PageName;
import org.scenarioo.example.e4.ScenariooTestWrapper;
import org.scenarioo.example.e4.UseCaseName;
import org.scenarioo.example.e4.orders.parts.OrdersOverviewPart;
import org.scenarioo.example.e4.rules.CreateTempOrderRule;
import org.scenarioo.example.e4.rules.DeleteOrderRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SWTBotJunit4ClassRunner.class)
public class ChangeArticleUpdatesTheOrderOverviewTest extends ScenariooTestWrapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChangeArticleUpdatesTheOrderOverviewTest.class);

	private static final String POSITION_STATE = "New";

	@Override
	protected RuleChain appendInnerMostRules(final RuleChain outerRuleChain) {
		return outerRuleChain.around(new DeleteOrderRule(CreateTempOrderRule.ORDER_NUMBER_TEMP));
	}

	@Rule
	public CreateTempOrderRule createTempOrderRule = new CreateTempOrderRule();

	/**
	 * @see org.scenarioo.example.e4.ScenariooTestWrapper#getUseCaseName()
	 */
	@Override
	protected UseCaseName getUseCaseName() {
		return UseCaseName.EDIT_POSITION;
	}

	/**
	 * @see org.scenarioo.example.e4.ScenariooTestWrapper#getScenariooDescription()
	 */
	@Override
	protected String getScenarioDescription() {
		return "Changes the article of an existing Position in the position detail view and shows that the "
				+ " name of the corresponding position in the order overview get's updated as well.";
	}

	@Test
	public void execute() {

		SWTBotTree tree = bot.tree();
		final SWTBotTreeItem orderTreeItem = tree.getTreeItem(CreateTempOrderRule.ORDER_NUMBER_TEMP);
		expandTreeItem(orderTreeItem);

		generateInitialViewDocuForOrderOverview();

		SWTBotTreeItem firstPositionNode = orderTreeItem.getItems()[0];
		SWTBotMenu menu = getContextMenuAndGenerateDocu(tree, firstPositionNode, "Edit Position");
		LOGGER.info(menu.toString());

		clickMenuEntryAndGenerateDocu(menu, PageName.POSITION_DETAIL);

		String articleName = firstPositionNode.getText();
		String editorTitle = CreateTempOrderRule.ORDER_NUMBER_TEMP + " - " + articleName + " - " + POSITION_STATE;
		LOGGER.info("select Position Editor: " + editorTitle);
		SWTBotView partByTitle = wbBot.partByTitle(editorTitle);
		Assert.assertNotNull(partByTitle);

		selectArticleAndGenerateDocu(articleName);

		saveAllAndGenerateDocu();

		// close order details
		partByTitle.close();

		LOGGER.info(getClass().getSimpleName() + " successful!");
	}

	private void selectArticleAndGenerateDocu(final String existingAssignedArticle) {
		final SWTBotCombo combo = bot.comboBoxWithLabel("&Article Number");
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				combo.widget.setListVisible(true);
				bot.waitUntil(new PopUpOpenedCondition(combo));
			}
		});
		bot.sleep(500);
		scenariooWriterHelper.writeStep("combo_popup_opened", PageName.POSITION_DETAIL, screenshot());
		combo.setSelection(1); // General Ackbar

		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				combo.widget.setListVisible(false);
				bot.waitUntil(new PopUpDisappearedCondition(combo));
			}
		});
		bot.sleep(100);
		scenariooWriterHelper.writeStep("article_selected", PageName.POSITION_DETAIL, screenshot());
	}

	private void saveAllAndGenerateDocu() {
		bot.toolbarButtonWithTooltip("Save All").click();
		bot.sleep(100);
		scenariooWriterHelper.writeStep("save_all_clicked", PageName.ORDER_DETAIL, screenshot());
	}

	/**
	 * @param treeItem
	 */
	private void expandTreeItem(final SWTBotTreeItem treeItem) {
		OrdersOverviewPart ordersOverviewPart = (OrdersOverviewPart) wbBot.partByTitle("Orders Overview").getPart()
				.getObject();
		ordersOverviewPart.setOverSteerNodeItemExpandedForTest(Boolean.TRUE);
		treeItem.expand().getNodes();
		ordersOverviewPart.setOverSteerNodeItemExpandedForTest(null);
	}

	private static class PopUpDisappearedCondition extends DefaultCondition {

		final SWTBotCombo combo;

		private PopUpDisappearedCondition(final SWTBotCombo combo) {
			this.combo = combo;
		}

		/**
		 * @see org.eclipse.swtbot.swt.finder.waits.ICondition#test()
		 */
		@Override
		public boolean test() throws Exception {
			return !combo.widget.getListVisible();
		}

		/**
		 * @see org.eclipse.swtbot.swt.finder.waits.ICondition#getFailureMessage()
		 */
		@Override
		public String getFailureMessage() {
			return "Could not close popup of combo: " + combo;
		}

	}

	private static class PopUpOpenedCondition extends DefaultCondition {

		final SWTBotCombo combo;

		private PopUpOpenedCondition(final SWTBotCombo combo) {
			this.combo = combo;
		}

		/**
		 * @see org.eclipse.swtbot.swt.finder.waits.ICondition#test()
		 */
		@Override
		public boolean test() throws Exception {
			return combo.widget.getListVisible();
		}

		/**
		 * @see org.eclipse.swtbot.swt.finder.waits.ICondition#getFailureMessage()
		 */
		@Override
		public String getFailureMessage() {
			return "Could not open popup of combo: " + combo;
		}

	}

}
