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

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateTempOrderRule implements TestRule {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateTempOrderRule.class);

	public static final String ORDER_NUMBER_TEMP = "Temp";

	/**
	 * @see org.junit.rules.TestRule#apply(org.junit.runners.model.Statement, org.junit.runner.Description)
	 */
	@Override
	public Statement apply(final Statement base, final Description description) {
		return new CreateTempOrderStatement(base);
	}

	private class CreateTempOrderStatement extends Statement{

		private final Statement base;
		private final SWTBot bot;

		private CreateTempOrderStatement(final Statement base){
			this.base = base;
			this.bot = new SWTBot();
		}
		
		
		/**
		 * @see org.junit.runners.model.Statement#evaluate()
		 */
		@Override
		public void evaluate() throws Throwable {
			createOrderTemp();
			// Write usecase.xml sceanrio.xml before Test execution
			base.evaluate();
		}
		
		private void createOrderTemp() {

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
			bot.sleep(100);
			bot.text(1).setText("2");

			// Select Item
			bot.table().click(0, 2);
			bot.sleep(100);
			bot.ccomboBox(0).setSelection(13);

			// loose Focus
			table.click(0, 3);
			bot.sleep(100);

			// click Finish
			bot.button("Finish").click();

			bot.sleep(500);

			LOGGER.info("\n"
					+ "-----------------------------------------------------------------\n"
					+ "create temp order rule : order " + ORDER_NUMBER_TEMP + " created.\n"
					+ "-----------------------------------------------------------------\n\n");
		}
	}

}
