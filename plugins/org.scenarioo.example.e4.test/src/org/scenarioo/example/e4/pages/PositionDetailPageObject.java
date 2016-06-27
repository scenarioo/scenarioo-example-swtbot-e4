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

package org.scenarioo.example.e4.pages;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.e4.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.junit.Assert;
import org.scenarioo.example.e4.PageName;
import org.scenarioo.example.e4.ScenariooWriterHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PositionDetailPageObject extends PageObject {

	private static final Logger LOGGER = LoggerFactory.getLogger(PositionDetailPageObject.class);
	private final SWTBotView positionDetailPart;

	/**
	 * @param scenariooWriterHelper
	 */
	public PositionDetailPageObject(final ScenariooWriterHelper scenariooWriterHelper, final String title) {
		super(scenariooWriterHelper);
		this.positionDetailPart = checkPositionDetailPart(wbBot.partByTitle(title));
		logPartInfos();
	}

	public PositionDetailPageObject(final ScenariooWriterHelper scenariooWriterHelper,
			final SWTBotView positionDetailPart) {
		super(scenariooWriterHelper);
		this.positionDetailPart = checkPositionDetailPart(positionDetailPart);
		logPartInfos();
	}

	private SWTBotView checkPositionDetailPart(final SWTBotView positionDetailPart) {
		Assert.assertNotNull(positionDetailPart);
		// bot.waitUntil(new WidgetAppearedCondition(positionDetailPart), 20000);
		return positionDetailPart;
	}

	public void generateDocu(final String title) {
		bot.sleep(100);
		scenariooWriterHelper.writeStep(title, PageName.POSITION_DETAIL, screenshot());
	}

	public void selectArticleAndGenerateDocu() {
		selectArticleAndGenerateDocu(14);// Jawas Jawas
	}

	public void selectArticleAndGenerateDocu(final int indexArticleCB) {
		final SWTBotCombo combo = bot.comboBoxWithLabel("&Article Number");
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				combo.widget.setListVisible(true);
				bot.waitUntil(new PopUpOpenedCondition(combo));
			}
		});
		generateDocu("combo_popup_opened");
		combo.setSelection(indexArticleCB);

		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				combo.widget.setListVisible(false);
				bot.waitUntil(new ComboListClosedCondition(combo));
			}
		});
		generateDocu("article_combo_box_selected");
	}

	private static class ComboListClosedCondition extends DefaultCondition {

		private final SWTBotCombo combo;

		private ComboListClosedCondition(final SWTBotCombo combo) {
			this.combo = combo;
		}

		/**
		 * @see org.eclipse.swtbot.swt.finder.waits.ICondition#test()
		 */
		@Override
		public boolean test() throws Exception {
			return !combo.widget.getListVisible();
		}

		/**
		 * @see org.eclipse.swtbot.swt.finder.waits.ICondition#getFailureMessage()
		 */
		@Override
		public String getFailureMessage() {
			return "Could not close popup of combo: " + combo;
		}
	}

	private static class PopUpOpenedCondition extends DefaultCondition {

		private final SWTBotCombo combo;

		private PopUpOpenedCondition(final SWTBotCombo combo) {
			this.combo = combo;
		}

		/**
		 * @see org.eclipse.swtbot.swt.finder.waits.ICondition#test()
		 */
		@Override
		public boolean test() throws Exception {
			return combo.widget.getListVisible();
		}

		/**
		 * @see org.eclipse.swtbot.swt.finder.waits.ICondition#getFailureMessage()
		 */
		@Override
		public String getFailureMessage() {
			return "Could not open popup of combo: " + combo;
		}
	}

	private class WidgetAppearedCondition extends DefaultCondition {

		private final SWTBotView positionDetailPart;

		private WidgetAppearedCondition(final SWTBotView positionDetailPart) {
			this.positionDetailPart = positionDetailPart;
		}

		/**
		 * @see org.eclipse.swtbot.swt.finder.waits.ICondition#test()
		 */
		@Override
		public boolean test() throws Exception {
			return positionDetailPart.getPart().getWidget() != null
					&& positionDetailPart.getPart().getRenderer() != null;
		}

		/**
		 * @see org.eclipse.swtbot.swt.finder.waits.ICondition#getFailureMessage()
		 */
		@Override
		public String getFailureMessage() {
			generateDocu("error_position_detail_part_not_available");
			return "Could not find positionDetailPart with label: " + positionDetailPart.getPart().getLabel();
		}
	}

	public void activate() {
		errorIfWidgetNotAvailable();
		positionDetailPart.show();
	}

	public void close() {
		errorIfWidgetNotAvailable();
		positionDetailPart.close();
	}

	private void errorIfWidgetNotAvailable() {
		Object widget = positionDetailPart.getPart().getWidget();
		Object renderer = positionDetailPart.getPart().getRenderer();
		if (widget == null && renderer == null) {
			LOGGER.error("widget and renderer is null there is nothing to close!");
			logPartInfos();
			// throw new IllegalStateException("could not close \"" + title + "\" due to no widget available");
		}
	}

	private void logPartInfos() {
		LOGGER.info("\n"
				+ "------------------PARTINFO-----------------------\n"
				+ "part: " + positionDetailPart.getPart().toString());
		String title = positionDetailPart.getTitle();
		LOGGER.info("title: " + title);
		LOGGER.info("\n"
				+ "-------------------------------------------------\n");

	}
}
