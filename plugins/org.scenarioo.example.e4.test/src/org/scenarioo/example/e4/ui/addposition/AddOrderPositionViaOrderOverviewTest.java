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

package org.scenarioo.example.e4.ui.addposition;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.e4.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
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
public class AddOrderPositionViaOrderOverviewTest extends ScenariooTestWrapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddOrderPositionViaOrderOverviewTest.class);

	private static final String POSITION_STATE = "New";

	@Override
	protected RuleChain appendInnerRules(final RuleChain outerRuleChain) {
		return outerRuleChain.around(new DeleteOrderRule(CreateTempOrderRule.ORDER_NUMBER_TEMP));
	}

	@Rule
	public CreateTempOrderRule createTempOrderRule = new CreateTempOrderRule();

	/**
	 * @see org.scenarioo.example.e4.ScenariooTestWrapper#getUseCaseName()
	 */
	@Override
	protected UseCaseName getUseCaseName() {
		return UseCaseName.ADD_POSITION;
	}

	/**
	 * @see org.scenarioo.example.e4.ScenariooTestWrapper#getScenariooDescription()
	 */
	@Override
	protected String getScenarioDescription() {
		return "Starts with an expanded order node. Adds a Position via context menu in the order overview page. "
				+ "The position detail view is automatically opened with an empty Article CB. It shows that the "
				+ "order node in the overview is updated with the new position after the new position is saved.";
	}

	@Test
	public void execute() {

		SWTBotTree tree = bot.tree();
		final SWTBotTreeItem treeItem = tree.getTreeItem(CreateTempOrderRule.ORDER_NUMBER_TEMP);
		expandTreeItem(treeItem);

		generateDocu("order_tree_node_expanded", PageName.ORDER_OVERVIEW);

		openContextMenuForOrderTreeNode(tree, CreateTempOrderRule.ORDER_NUMBER_TEMP);

		generateDocu("context_menu_opened", PageName.ORDER_OVERVIEW);

		clickContextMenuActionForOrderTreeNode(tree, CreateTempOrderRule.ORDER_NUMBER_TEMP, "Add Position");

		generateDocu("context_menu_item_add_position_clicked", PageName.POSITION_DETAIL);

		SWTBotView partByTitle = wbBot.partByTitle(CreateTempOrderRule.ORDER_NUMBER_TEMP + " - " + "choose Article"
				+ " - " + POSITION_STATE);
		Assert.assertNotNull(partByTitle);

		selectArticleAndGenerateDocu();

		saveAllAndGenerateDocu();

		// close order details
		partByTitle.close();

		LOGGER.info(getClass().getSimpleName() + " successful!");
	}

	private void selectArticleAndGenerateDocu() {
		final SWTBotCombo combo = bot.comboBoxWithLabel("&Article Number");
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				combo.widget.setListVisible(true);
				bot.waitUntil(new PopUpOpenedCondition(combo));
			}
		});
		bot.sleep(100);
		scenariooWriterHelper.writeStep("combo_popup_opened", PageName.POSITION_DETAIL, screenshot());
		combo.setSelection(14); // Jawas Jawas

		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				combo.widget.setListVisible(false);
				bot.waitUntil(new ComboListClosedCondition(combo));
			}
		});
		bot.sleep(100);
		scenariooWriterHelper.writeStep("article_selected", PageName.POSITION_DETAIL, screenshot());
	}

	private void saveAllAndGenerateDocu() {
		bot.toolbarButtonWithTooltip("Save All (Shift+Ctrl+S)").click();
		// bot.toolbarButtonWithTooltip("Save All").click();
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
		treeItem.expand();
		ordersOverviewPart.setOverSteerNodeItemExpandedForTest(null);
	}

	private static class ComboListClosedCondition extends DefaultCondition {

		final SWTBotCombo combo;

		private ComboListClosedCondition(final SWTBotCombo combo) {
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
