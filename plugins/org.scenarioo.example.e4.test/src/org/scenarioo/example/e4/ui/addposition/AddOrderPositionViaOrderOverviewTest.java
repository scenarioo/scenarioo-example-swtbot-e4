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

import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.scenarioo.example.e4.ScenariooTestWrapper;
import org.scenarioo.example.e4.UseCaseName;
import org.scenarioo.example.e4.pages.OrderOverviewPageObject;
import org.scenarioo.example.e4.pages.PositionDetailPageObject;
import org.scenarioo.example.e4.rules.CreateTempOrderRule;
import org.scenarioo.example.e4.rules.DeleteOrderRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SWTBotJunit4ClassRunner.class)
public class AddOrderPositionViaOrderOverviewTest extends ScenariooTestWrapper {

	private static final String TEST_ORDER_NUMBER = CreateTempOrderRule.ORDER_NUMBER_TEMP;
	private static final Logger LOGGER = LoggerFactory.getLogger(AddOrderPositionViaOrderOverviewTest.class);
	private static final String POSITION_STATE = "New";

	private OrderOverviewPageObject orderOverviewPage;
	private PositionDetailPageObject addedPositionDetailPage;

	@Override
	protected RuleChain appendInnerMostRules(final RuleChain outerRuleChain) {
		return outerRuleChain.around(new DeleteOrderRule(TEST_ORDER_NUMBER));
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
		return "Starts with an expanded order node in the order overview. Then it adds a new Position via context "
				+ "menu in the order overview page. The position detail view is automatically opened with an empty "
				+ "Article CB. The save button becomes available after the article is chosen. In the order overview "
				+ "the new position gets added to the order after the new position is saved the first time.";
	}

	@Before
	public void init() {
		this.orderOverviewPage = new OrderOverviewPageObject(scenariooWriterHelper);
		orderOverviewPage.expandTreeForOrder(TEST_ORDER_NUMBER, false);
		orderOverviewPage.generateDocu("initial_view");
	}

	@Test
	public void execute() {

		openAddPositionEditor();

		selectArticle();

		clickSaveAllAndGenerateDocu();

		LOGGER.info(getClass().getSimpleName() + " successful!");
	}

	@After
	public void tearDown() {
		addedPositionDetailPage.close();
	}

	private void openAddPositionEditor() {
		orderOverviewPage.addPositionForOrderViaContextMenuAndGenerateDocu(TEST_ORDER_NUMBER);
		String viewTitle = TEST_ORDER_NUMBER + " - " + "choose Article" + " - " + POSITION_STATE;
		this.addedPositionDetailPage = new PositionDetailPageObject(scenariooWriterHelper, viewTitle);
	}

	private void selectArticle() {
		addedPositionDetailPage.activate();
		addedPositionDetailPage.selectArticleAndGenerateDocu(11);
	}

}
