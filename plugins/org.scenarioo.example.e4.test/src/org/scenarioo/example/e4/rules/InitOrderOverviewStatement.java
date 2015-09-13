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

import org.eclipse.swtbot.e4.finder.widgets.SWTBotView;
import org.eclipse.swtbot.e4.finder.widgets.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.runners.model.Statement;
import org.scenarioo.example.e4.BaseSWTBotTest;
import org.scenarioo.example.e4.EclipseContextHelper;

public class InitOrderOverviewStatement extends Statement {

	private final Statement base;
	private final SWTBot bot;
	private final SWTWorkbenchBot wbBot;

	public int totalPersistedOrders;
	public int initializedOrdersInOrderOverview;

	public InitOrderOverviewStatement(final Statement base) {
		this.base = base;
		this.bot = new SWTBot();
		this.wbBot = new SWTWorkbenchBot(EclipseContextHelper.getEclipseContext());
	}

	/**
	 * @see org.junit.runners.model.Statement#evaluate()
	 */
	@Override
	public void evaluate() throws Throwable {
		
		// We execute before the test
		addFourOrdersToOrdersOverview();
		initializedOrdersInOrderOverview = bot.tree().rowCount();
		
		base.evaluate();
	}

	private void addFourOrdersToOrdersOverview() {

		SWTBotView view = wbBot.partById(BaseSWTBotTest.PART_ID_ORDER_OVERVIEW);
		view.toolbarButton("Search Order").click();

		SWTBotText text = bot.textWithLabel("&Order Number");
		text.typeText("Order");

		bot.buttonWithTooltip("Start Search").click();

		// Select Item in Table
		SWTBotTable table = bot.table();
		totalPersistedOrders = table.rowCount();
		table.click(0, 5);
		bot.sleep(1000);
		table.click(1, 5);
		bot.sleep(1000);
		table.click(3, 5);
		bot.sleep(1000);
		table.click(5, 5);
		bot.sleep(1000);

		// click Finish
		bot.button("OK").click();

	}

}
