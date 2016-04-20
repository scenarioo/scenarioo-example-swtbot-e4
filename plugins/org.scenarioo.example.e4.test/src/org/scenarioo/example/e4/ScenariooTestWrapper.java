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

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.scenarioo.example.e4.rules.ScenarioNameRule;

public abstract class ScenariooTestWrapper extends BaseSWTBotTest {

	private static final ScreenShooter screenShooter = new ScreenShooter();

	private static final Date scenariooBuildDate = new Date();

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
	public final ScenarioNameRule scenarioNameRule;

	public ScenariooTestWrapper() {
		UseCaseName useCaseName = getUseCaseName();
		scenariooWriterHelper.setUseCaseName(useCaseName);
		scenariooWriterHelper.setScenarioDescription(getScenarioDescription());
		scenarioNameRule = new ScenarioNameRule(scenariooWriterHelper);
		entityStateHelper = new EntityStateManager(useCaseName);
	}

	protected abstract UseCaseName getUseCaseName();

	protected String getScenarioDescription() {
		return "";
	}

	protected static byte[] screenshot() {
		return screenShooter.capture();
	}

	protected void generateDocuForOrderOverview() {
		generateDocuForInitialView(PageName.ORDER_OVERVIEW);
	}

	protected void generateDocuForInitialView(final PageName pageName) {
		bot.sleep(100);
		scenariooWriterHelper.writeStep("initial_view", pageName, screenshot());
	}

	/**
	 * PageName is Order_Overview.
	 * 
	 * @param menu
	 */
	protected void clickMenuEntryAndGenerateDocu(final SWTBotMenu menu) {
		clickMenuEntryAndGenerateDocu(menu, PageName.ORDER_OVERVIEW);
	}

	/**
	 * @param menu
	 */
	protected void clickMenuEntryAndGenerateDocu(final SWTBotMenu menu, final PageName pageName) {
		clickMenuEntryAndCloseContextMenu(menu);
		scenariooWriterHelper.writeStep(menu.getText() + "_menu_entry_clicked", pageName, screenshot());
	}

	/**
	 * @param menu
	 */
	protected void clickMenuEntryAndCloseContextMenu(final SWTBotMenu menu) {
		menu.click();
		closeContextMenu(menu);
		bot.sleep(500);
	}

	private void closeContextMenu(final SWTBotMenu menu) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				menu.widget.getParent().setVisible(false);
			}
		});
	}

	/**
	 * @param tree
	 * @return SWTBotMenu
	 */
	protected SWTBotMenu getContextMenuAndGenerateDocu(final SWTBotTree tree, final String orderNumber,
			final String actionName) {

		SWTBotMenu contextMenu = getContextMenu(tree, orderNumber, actionName);
		scenariooWriterHelper.writeStep("order_number_selected", PageName.ORDER_OVERVIEW, screenshot());
		return contextMenu;
	}

	/**
	 * @param tree
	 * @return SWTBotMenu
	 */
	protected SWTBotMenu getContextMenu(final SWTBotTree tree, final String orderNumber,
			final String actionName) {

		final SWTBotTreeItem treeItem = tree.getTreeItem(orderNumber);
		final SWTBotMenu menuAction = treeItem.contextMenu(actionName);

		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				Menu menu = tree.widget.getMenu();
				Point menuItemLocation = getMenuItemLocation(treeItem);
				menu.setLocation(menuItemLocation.x, menuItemLocation.y);
				menu.setVisible(true);
			}
		});
		bot.sleep(100);
		return menuAction;
	}

	private Point getMenuItemLocation(final SWTBotTreeItem treeItem) {
		Point absolutLocation = treeItem.widget.getParent().toDisplay(0, 0);
		Rectangle bounds = treeItem.widget.getBounds();
		System.out.println("Relative Bounds: " + bounds.toString());
		return new Point(absolutLocation.x + bounds.x + bounds.width, absolutLocation.y
				+ bounds.y + bounds.height / 2);
	}
}
