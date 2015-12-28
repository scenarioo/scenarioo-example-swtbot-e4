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

import java.util.Date;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.scenarioo.example.e4.rules.ScenariooRule;

public class ScenariooTestWrapper extends BaseSWTBotTest {

	private static final ScreenShooter screenShooter = new ScreenShooter();

	private static final Date scenariooBuildDate = new Date();

	protected final UseCaseName useCaseName = UseCaseName.ORDERS;
	protected final ScenariooWriterHelper scenariooWriterHelper = new ScenariooWriterHelper(scenariooBuildDate);
	private final EntityStateManager entityStateHelper;
	
	@Rule
	public final TestWatcher buildFileWriter = new TestWatcher() {

		@Override
		protected void failed(final Throwable e, final Description description) {
			if (entityStateHelper.changeBuildStateTo(EntityState.FAILED)) {
				scenariooWriterHelper.writeBuildFileWithFailedState();
			}
			if (entityStateHelper.changeUseCaseStateTo(EntityState.FAILED)) {
				scenariooWriterHelper.saveFailedUseCase();
			}
			if (entityStateHelper.changeScenarioStateTo(EntityState.FAILED)) {
				scenariooWriterHelper.saveFailedScenarioo();
			}
		}

		@Override
		protected void finished(final Description description) {
			if (entityStateHelper.changeBuildStateTo(EntityState.SUCCESS)) {
				scenariooWriterHelper.writeBuildFileWithSuccessState();
			}
			if (entityStateHelper.changeUseCaseStateTo(EntityState.SUCCESS)) {
				scenariooWriterHelper.saveSuccessfulUseCase();
			}
			if (entityStateHelper.changeScenarioStateTo(EntityState.SUCCESS)) {
				scenariooWriterHelper.saveSuccessfulScenario();
			}

			scenariooWriterHelper.flush();
		}
	};

	@Rule
	public final ScenariooRule scenariooRule;

	public ScenariooTestWrapper() {
		scenariooWriterHelper.setUseCaseName(useCaseName);
		scenariooRule = new ScenariooRule(scenariooWriterHelper);
		entityStateHelper = new EntityStateManager(useCaseName);
	}

	protected static byte[] screenshot() {
		return screenShooter.capture();
	}

	protected void generateDocuForInitialView() {
		bot.sleep(100);
		scenariooWriterHelper.writeStep("order_overview", PageName.ORDER_OVERVIEW, screenshot());
	}

	/**
	 * @param menu
	 */
	protected void clickMenuEntryAndGenerateDocu(final SWTBotMenu menu) {
		menu.click();
		bot.sleep(500);
		scenariooWriterHelper.writeStep("menu_entry_clicked", PageName.ORDER_OVERVIEW, screenshot());
	}

	/**
	 * @param tree
	 * @return
	 */
	protected SWTBotMenu getContextMenuAndGenerateDocu(final SWTBotTree tree, final String orderNumber,
			final String actionName) {
		SWTBotMenu menu = tree.getTreeItem(orderNumber).contextMenu(actionName);
		bot.sleep(100);
		scenariooWriterHelper.writeStep("order_number_selected", PageName.ORDER_OVERVIEW, screenshot());
		return menu;
	}
}
