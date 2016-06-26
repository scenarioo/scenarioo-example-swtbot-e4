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

package org.scenarioo.example.e4.ui.openorderdetails;

import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scenarioo.example.e4.ScenariooTestWrapper;
import org.scenarioo.example.e4.UseCaseName;
import org.scenarioo.example.e4.pages.OrderDetailPageObject;
import org.scenarioo.example.e4.pages.OrderOverviewPageObject;
import org.scenarioo.example.e4.rules.InitOrderOverviewRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SWTBotJunit4ClassRunner.class)
public class OpenOrderDetailsTest extends ScenariooTestWrapper {

	private static final String TEST_ORDER_NUMBER = "Order 2";
	private static final String ORDER_STATE = "New";
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenOrderDetailsTest.class);

	private OrderOverviewPageObject orderOverviewPage;
	private OrderDetailPageObject orderDetailPage;

	@Rule
	public InitOrderOverviewRule initOrderOverview = new InitOrderOverviewRule();

	/**
	 * @see org.scenarioo.example.e4.ScenariooTestWrapper#getUseCaseName()
	 */
	@Override
	protected UseCaseName getUseCaseName() {
		return UseCaseName.OPEN_ORDER_DETAILS;
	}

	/**
	 * @see org.scenarioo.example.e4.ScenariooTestWrapper#getScenariooDescription()
	 */
	@Override
	protected String getScenarioDescription() {
		return "Opens an Order in the order detail view to show all properties of the order.";
	}

	@Before
	public void init() {
		this.orderOverviewPage = new OrderOverviewPageObject(scenariooWriterHelper);
		orderOverviewPage.generateDocu("initial_view", "application_started");
	}

	@Test
	public void execute() {
		openOrderDetailsView();
		LOGGER.info(getClass().getSimpleName() + " successful!");
	}

	@After
	public void tearDown() {
		orderDetailPage.close();
	}

	private void openOrderDetailsView() {
		orderOverviewPage.openOrderDetails(TEST_ORDER_NUMBER);
		String orderDetailEditorTitle = getOrderDetailEditorTitle();
		this.orderDetailPage = new OrderDetailPageObject(scenariooWriterHelper, orderDetailEditorTitle);
	}

	private String getOrderDetailEditorTitle() {
		return TEST_ORDER_NUMBER + " - " + ORDER_STATE;
	}
}
