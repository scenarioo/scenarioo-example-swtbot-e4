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

import org.eclipse.swtbot.e4.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.scenarioo.example.e4.BaseSWTBotTest;
import org.scenarioo.example.e4.PageName;
import org.scenarioo.example.e4.ScenariooWriterHelper;

public class SearchOrdersDialogPageObject extends PageObject {

	public SearchOrdersDialogPageObject(final ScenariooWriterHelper scenariooWriterHelper) {
		super(scenariooWriterHelper);
	}

	public void open() {
		SWTBotView view = wbBot.partById(BaseSWTBotTest.PART_ID_ORDER_OVERVIEW);
		view.toolbarButton("Search Order").click();
		bot.sleep(100);
		scenariooWriterHelper.writeStep("search_dialog_action_clicked", PageName.SEARCH_DIALOG, screenshot());
	}

	/**
	 * 
	 */
	public void enterOrderNumber(final String orderNumber) {
		SWTBotText text = bot.textWithLabel("&Order Number");
		text.typeText(orderNumber);
		bot.sleep(100);
		scenariooWriterHelper.writeStep("order_number_entered", PageName.SEARCH_DIALOG, screenshot());
	}

	/**
	 * 
	 */
	public void startSearch() {
		bot.buttonWithTooltip("Start Search").click();
		bot.sleep(100);
		scenariooWriterHelper.writeStep("start_search_clicked", PageName.SEARCH_DIALOG, screenshot());
	}

	/**
	 * 
	 */
	public void ok() {
		bot.button("OK").click();
		bot.sleep(100);
		scenariooWriterHelper.writeStep("order_dialog_ok", PageName.ORDER_OVERVIEW, screenshot());
	}

	/**
	 * 
	 * @param table
	 * @param rowindex
	 */
	public void selectOrderAndGenerateDocu(final int rowindex) {
		SWTBotTable table = bot.table();
		table.click(rowindex, 5);
		bot.sleep(100);
		scenariooWriterHelper.writeStep("order_" + rowindex + "_selected", PageName.SEARCH_DIALOG, screenshot());
	}

}
