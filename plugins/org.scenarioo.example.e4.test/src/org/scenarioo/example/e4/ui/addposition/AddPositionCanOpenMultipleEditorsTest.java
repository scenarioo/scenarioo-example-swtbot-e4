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
import org.scenarioo.example.e4.pages.CreateAddPositionEditor;
import org.scenarioo.example.e4.pages.OrderOverviewPageObject;
import org.scenarioo.example.e4.pages.PositionDetailPageObject;
import org.scenarioo.example.e4.rules.CreateTempOrderRule;
import org.scenarioo.example.e4.rules.DeleteOrderRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SWTBotJunit4ClassRunner.class)
public class AddPositionCanOpenMultipleEditorsTest extends ScenariooTestWrapper {

	private static final String TEST_ORDER_NUMBER = CreateTempOrderRule.ORDER_NUMBER_TEMP;
	private static final Logger LOGGER = LoggerFactory.getLogger(AddPositionCanOpenMultipleEditorsTest.class);

	private OrderOverviewPageObject orderOverviewPage;
	private PositionDetailPageObject firstAddedPositionDetailPage;
	private PositionDetailPageObject secondAddedPositionDetailPage;
	private PositionDetailPageObject thirdAddedPositionDetailPage;

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
		return "adds three new positions in one order. Demonstrates that it is acceptable "
				+ "to have multiple add position pages open at the same time. At the end "
				+ "all three editors get's saved with the save all action.";
	}

	@Before
	public void init() {
		this.orderOverviewPage = new OrderOverviewPageObject(scenariooWriterHelper);
		orderOverviewPage.expandTreeForOrder(TEST_ORDER_NUMBER, false);
		orderOverviewPage.generateDocu("initial_view");
	}

	@Test
	public void execute() {

		openFirstAddPositionEditor();
		openSecondAddPositionEditor();
		openThirdAddPositionEditor();

		fillOutFirstAddedPosition();
		fillOutSecondAddedPosition();
		fillOutThirdAddedPosition();

		saveAllAddedPositions();

		LOGGER.info(getClass().getSimpleName() + " successful!");
	}

	@After
	public void tearDown() {
		closeAllViews();
	}

	private void openFirstAddPositionEditor() {

		orderOverviewPage.addPositionForOrderViaContextMenu(TEST_ORDER_NUMBER, "first add position editor opened");

		this.firstAddedPositionDetailPage = createAddPositionEditor(0);
	}

	private void openSecondAddPositionEditor() {

		orderOverviewPage.addPositionForOrderViaContextMenu(TEST_ORDER_NUMBER, "second add position editor opened");

		this.secondAddedPositionDetailPage = createAddPositionEditor(1);
	}

	private void openThirdAddPositionEditor() {

		orderOverviewPage.addPositionForOrderViaContextMenu(TEST_ORDER_NUMBER, "third add position editor opened");

		this.thirdAddedPositionDetailPage = createAddPositionEditor(2);
	}

	private PositionDetailPageObject createAddPositionEditor(final int index) {
		CreateAddPositionEditor createAddPositionEditor = new CreateAddPositionEditor(index, TEST_ORDER_NUMBER);
		return PositionDetailPageObject.createAddPositionEditor(scenariooWriterHelper, createAddPositionEditor);
	}

	private void fillOutFirstAddedPosition() {
		firstAddedPositionDetailPage.activate();
		firstAddedPositionDetailPage.selectArticleAndGenerateDocu(15);
	}

	private void fillOutSecondAddedPosition() {
		secondAddedPositionDetailPage.activate();
		secondAddedPositionDetailPage.selectArticleAndGenerateDocu(16);
	}

	private void fillOutThirdAddedPosition() {
		thirdAddedPositionDetailPage.activate();
		thirdAddedPositionDetailPage.selectArticleAndGenerateDocu(17);
	}

	private void saveAllAddedPositions() {
		clickSaveAllAndGenerateDocu();
	}

	private void closeAllViews() {
		firstAddedPositionDetailPage.close();
		secondAddedPositionDetailPage.close();
		thirdAddedPositionDetailPage.close();
	}
}
