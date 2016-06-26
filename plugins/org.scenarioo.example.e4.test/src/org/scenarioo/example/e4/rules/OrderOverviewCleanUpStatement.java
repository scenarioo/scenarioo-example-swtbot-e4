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

package org.scenarioo.example.e4.rules;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderOverviewCleanUpStatement extends Statement {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderOverviewCleanUpStatement.class);

	private final Statement base;
	private final SWTBot bot;

	public OrderOverviewCleanUpStatement(final Statement base) {
		this.base = base;
		this.bot = new SWTBot();
	}

	/**
	 * @see org.junit.runners.model.Statement#evaluate()
	 */
	@Override
	public void evaluate() throws Throwable {
		try {
			LOGGER.info("\n\nThe order overview clean up statement is executed after the test.\n\n");
			base.evaluate();
		} finally {
			// We execute after the test
			removeAllOrdersFromOrderOverview();
		}
	}

	private void removeAllOrdersFromOrderOverview() {

		LOGGER.info("\n-----------------------------------------------------------------"
				+ "\nThe order overview clean up is executed..\n"
				+ "-----------------------------------------------------------------");
		SWTBotTree tree = bot.tree();
		int ordersCount = tree.getAllItems().length;
		for (int i = ordersCount - 1; i >= 0; i--) {
			SWTBotTreeItem treeItem = tree.getAllItems()[i];
			List<SWTBotTreeItem> allItems = Arrays.asList(tree.getAllItems());
			LOGGER.info("remove order: " + treeItem.getText() + ", size=" + tree.getAllItems().length + ", allItems="
					+ allItems.toString());
			treeItem.contextMenu("Remove Order").click();
			bot.waitUntil(new OrderRemovedCondition(treeItem));
		}
		LOGGER.info("The order overview clean up statement is finished.\n\n");
	}

	private static class OrderRemovedCondition extends DefaultCondition {

		final SWTBotTreeItem treeItem;

		private OrderRemovedCondition(final SWTBotTreeItem treeItem) {
			this.treeItem = treeItem;
		}

		/**
		 * @see org.eclipse.swtbot.swt.finder.waits.ICondition#test()
		 */
		@Override
		public boolean test() throws Exception {
			List<SWTBotTreeItem> allItems = Arrays.asList(bot.tree().getAllItems());
			return !allItems.contains(treeItem);
		}

		/**
		 * @see org.eclipse.swtbot.swt.finder.waits.ICondition#getFailureMessage()
		 */
		@Override
		public String getFailureMessage() {
			return "Could not remove treeItem: " + treeItem;
		}
	}

}
