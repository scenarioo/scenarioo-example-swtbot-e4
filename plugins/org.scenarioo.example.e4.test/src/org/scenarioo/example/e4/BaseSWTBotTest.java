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

package org.scenarioo.example.e4;

import org.eclipse.swtbot.e4.finder.widgets.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.scenarioo.example.e4.rules.OrderOverviewCleanUpRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseSWTBotTest {

	public static final String PART_ID_ORDER_OVERVIEW = "org.scenarioo.example.e4.orders.part.ordersoverview";
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseSWTBotTest.class);
	protected static SWTBot bot;
	protected static SWTWorkbenchBot wbBot;

	@Rule
	public final RuleChain ruleChain = createRuleChain();

	private RuleChain createRuleChain() {
		RuleChain ruleChain = RuleChain.outerRule(new LogTestStartedAndFinishedTestRule());
		ruleChain = ruleChain.around(new OrderOverviewCleanUpRule());
		ruleChain = appendInnerRules(ruleChain);
		ruleChain = appendInnerMostRules(ruleChain);
		return ruleChain;
	}

	protected RuleChain appendInnerMostRules(final RuleChain ruleChain) {
		return ruleChain;
	}

	protected RuleChain appendInnerRules(final RuleChain ruleChain) {
		return ruleChain;
	}

	@BeforeClass
	public static void setup() throws Exception {
		// don't use SWTWorkbenchBot here which relies on Platform 3.x
		bot = new SWTBot();
		wbBot = new SWTWorkbenchBot(EclipseContextHelper.getEclipseContext());
	}

	private static class LogTestStartedAndFinishedTestRule implements TestRule {

		/**
		 * @see org.junit.rules.TestRule#apply(org.junit.runners.model.Statement, org.junit.runner.Description)
		 */
		@Override
		public Statement apply(final Statement base, final Description description) {
			return new Statement() {

				@Override
				public void evaluate() throws Throwable {
					LOGGER.info("\n"
							+ "-----------------------------------------------------------------\n"
							+ "Test class " + description.getClassName() + " started..\n"
							+ "-----------------------------------------------------------------\n\n");
					base.evaluate();
					LOGGER.info("\n"
							+ "-----------------------------------------------------------------\n"
							+ "Test class " + description.getClassName() + " finished..\n"
							+ "-----------------------------------------------------------------\n\n");
				}
			};
		}
	}
}
