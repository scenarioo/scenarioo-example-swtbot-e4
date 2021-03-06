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
public class AddPositionEditorBecomesAPositionDetailEditorAndGetsFocusedTest extends ScenariooTestWrapper {

	private static final String TEST_ORDER_NUMBER = CreateTempOrderRule.ORDER_NUMBER_TEMP;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddPositionEditorBecomesAPositionDetailEditorAndGetsFocusedTest.class);
	private static final String POSITION_STATE = "New";

	private OrderOverviewPageObject orderOverviewPage;
	private PositionDetailPageObject addedPositionDetailPage;
	private PositionDetailPageObject existingPositionDetailPage;

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
		return "The form of the added position is filled out and saved. Then another position is opened "
				+ "and get's the focus. The added position is reopened again and get's back the "
				+ "focus in the editor section.";
	}

	@Before
	public void init() {

		this.orderOverviewPage = new OrderOverviewPageObject(scenariooWriterHelper);

		orderOverviewPage.expandTreeForOrder(TEST_ORDER_NUMBER, false);

		orderOverviewPage.addPositionViaContextMenuWithoutDocuGeneration(TEST_ORDER_NUMBER);

		initAddPositionDetailPage();

		addedPositionDetailPage.generateDocu("add_position_page_opened");
	}

	@Test
	public void execute() {

		fillOutPositionDetailPanelOfAddedPosition();

		saveAllAndReinitAddedPositionPage();

		openSecondEditorLoosesFocusOnAddedPositionEditor();

		reopenAddedPosition();

		LOGGER.info(getClass().getSimpleName() + " successful!\n\n");
	}

	@After
	public void tearDown() {
		LOGGER.info("\n\n\n"
				+ "-------------------------------------------------\n"
				+ "tearDown for test " + getClass().getSimpleName()
				+ "\n-------------------------------------------------");

		closeAllViews();
	}

	private void initAddPositionDetailPage() {
		CreateAddPositionEditor createAddPositionEditor = new CreateAddPositionEditor(0, TEST_ORDER_NUMBER);
		this.addedPositionDetailPage = PositionDetailPageObject.createAddPositionEditor(scenariooWriterHelper,
				createAddPositionEditor);
	}

	private void fillOutPositionDetailPanelOfAddedPosition() {
		addedPositionDetailPage.selectArticleAndGenerateDocu();
	}

	private void saveAllAndReinitAddedPositionPage() {
		clickSaveAllAndGenerateDocu();
		String articleNameOfLastPosition = orderOverviewPage.getArticleNameOfLastExistingPosition(TEST_ORDER_NUMBER);
		String viewTitle = TEST_ORDER_NUMBER + " - " + articleNameOfLastPosition + " - " + POSITION_STATE;
		this.addedPositionDetailPage = new PositionDetailPageObject(scenariooWriterHelper, viewTitle);
	}

	private void reopenAddedPosition() {
		orderOverviewPage.openPositionDetailsOfLastExistingPosition(TEST_ORDER_NUMBER);
	}

	private void openSecondEditorLoosesFocusOnAddedPositionEditor() {
		String articleNameOfFirstExistingPosition = orderOverviewPage
				.getArticleNameOfFirstExistingPosition(TEST_ORDER_NUMBER);
		orderOverviewPage.openPositionDetailsOfFirstExistingPosition(TEST_ORDER_NUMBER,
				"focus from added position editor removed");
		String positionDetailEditorTitle = TEST_ORDER_NUMBER + " - " + articleNameOfFirstExistingPosition + " - "
				+ POSITION_STATE;
		this.existingPositionDetailPage = new PositionDetailPageObject(scenariooWriterHelper, positionDetailEditorTitle);
	}

	private void closeAllViews() {
		addedPositionDetailPage.close();
		existingPositionDetailPage.close();
	}
}
