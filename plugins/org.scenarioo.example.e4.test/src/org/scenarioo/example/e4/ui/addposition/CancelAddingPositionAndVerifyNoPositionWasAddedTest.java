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
public class CancelAddingPositionAndVerifyNoPositionWasAddedTest extends ScenariooTestWrapper {

	private static final String TEST_ORDER_NUMBER = CreateTempOrderRule.ORDER_NUMBER_TEMP;
	private static final Logger LOGGER = LoggerFactory.getLogger(CancelAddingPositionAndVerifyNoPositionWasAddedTest.class);

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
		return "There is the Temp order in the order overview which has just one position assigned. Then the user "
				+ " clicks add position via context menu for the Temp order. Then the new opened add position view "
				+ "gets closed without saving. At the end of this scenario the Temp order in the order overview is "
				+ "expanded to proof that no new position is assigned it has still one position.";
	}

	@Before
	public void init() {
		this.orderOverviewPage = new OrderOverviewPageObject(scenariooWriterHelper);
		orderOverviewPage.generateDocu("initial_view");
	}

	@Test
	public void execute() {

		openAddPositionEditorViaContextMenu();

		closeAddPositionEditor();

		verifyPositionIsNotAdded();

		LOGGER.info(getClass().getSimpleName() + " successful!");
	}

	private void openAddPositionEditorViaContextMenu() {
		orderOverviewPage.addPositionForOrderViaContextMenuAndGenerateDocu(TEST_ORDER_NUMBER);
		CreateAddPositionEditor createAddPositionEditor = new CreateAddPositionEditor(0, TEST_ORDER_NUMBER);
		this.addedPositionDetailPage = PositionDetailPageObject.createAddPositionEditor(scenariooWriterHelper,
				createAddPositionEditor);
	}

	private void closeAddPositionEditor() {
		addedPositionDetailPage.close();
		addedPositionDetailPage.generateDocu("add_position_view_closed");
	}

	private void verifyPositionIsNotAdded() {
		orderOverviewPage.expandTreeForOrder(TEST_ORDER_NUMBER, true);
	}

}
