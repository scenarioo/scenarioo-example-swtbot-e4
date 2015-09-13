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

package org.scenarioo.example.e4.ui;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scenarioo.example.e4.BaseSWTBotTest;
import org.scenarioo.example.e4.PageName;

@RunWith(SWTBotJunit4ClassRunner.class)
public class CreateNewOrderTest extends BaseSWTBotTest {

	@Test
	public void execute() {

		scenariooWriterHelper.writeStep("order_overview", PageName.ORDER_OVERVIEW, screenshot());
		// bot.captureScreenshot("screenshots/temp.png");

		bot.toolbarButtonWithTooltip("Create Order").click();
		scenariooWriterHelper.writeStep("new_order_page_1", PageName.ORDER_NEW_1, screenshot());

		SWTBotText text = bot.textWithLabel("&Order Number");
		text.typeText("Huhu");
		scenariooWriterHelper.writeStep("order_number_entered", PageName.ORDER_NEW_1, screenshot());

		bot.button("Next >").click();
		scenariooWriterHelper.writeStep("new_order_page_2", PageName.ORDER_NEW_2, screenshot());

		bot.buttonWithTooltip("Add Position").click();
		scenariooWriterHelper.writeStep("add_position", PageName.ORDER_NEW_2, screenshot());

		// Select Item in Table
		SWTBotTable table = bot.table();
		table.click(0, 4);
		bot.sleep(1000);
		bot.text(1).setText("3");
		scenariooWriterHelper.writeStep("position_count_entered", PageName.ORDER_NEW_2, screenshot());

		bot.table().click(0, 2);
		bot.sleep(1000);
		bot.ccomboBox(0).setSelection(6);
		table.click(0, 3);
		bot.sleep(1000);
		scenariooWriterHelper.writeStep("item_selected", PageName.ORDER_NEW_2, screenshot());

		// click Finish
		bot.button("Finish").click();
		scenariooWriterHelper.writeStep("order_visible_in_order_overview", PageName.ORDER_OVERVIEW, screenshot());

		// Assert 1 more Orders available in OrderOverview
		SWTBotTree tree = bot.tree();
		Assert.assertEquals(1, tree.rowCount());

		bot.sleep(1000);
	}

	private byte[] screenshot() {

		final Display display = Display.getDefault();
		final Rectangle biggestRectangle = getWidthestAndHighestBounds(display);
		final Image image = new Image(display, biggestRectangle);

		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				GC gc = new GC(display);
				gc.copyArea(image, biggestRectangle.x, biggestRectangle.y);
				gc.dispose();
			}
		});
		return image.getImageData().data;
	}

	private Rectangle getWidthestAndHighestBounds(final Display display) {

		BiggestRectangleCalculator rect = new BiggestRectangleCalculator(display);

		Display.getDefault().syncExec(rect);
		return rect.biggest;
	}

	private class BiggestRectangleCalculator implements Runnable {

		private Rectangle biggest = null;
		private final Display display;

		/**
		 * 
		 */
		public BiggestRectangleCalculator(final Display display) {
			this.display = display;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			Shell[] shells = display.getShells();
			Rectangle biggestRectangle = shells[0].getBounds();

			for (int i = 1; i < shells.length; i++) {
				Rectangle rectangle = shells[i].getBounds();
				biggestRectangle = biggestRectangle.union(rectangle);
			}
			this.biggest = biggestRectangle;
		}
	}
}
