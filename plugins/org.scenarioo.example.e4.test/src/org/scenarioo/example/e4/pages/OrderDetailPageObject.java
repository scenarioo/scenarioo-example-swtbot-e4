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
import org.junit.Assert;
import org.scenarioo.example.e4.ScenariooWriterHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an order detail view.
 */
public class OrderDetailPageObject extends PageObject {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailPageObject.class);
	private final SWTBotView orderDetailPart;

	/**
	 * 
	 * @param scenariooWriterHelper
	 * @param title
	 */
	public OrderDetailPageObject(final ScenariooWriterHelper scenariooWriterHelper, final String title) {
		super(scenariooWriterHelper);
		this.orderDetailPart = wbBot.partByTitle(title);
		Assert.assertNotNull(orderDetailPart);
	}

	public void close() {
		errorIfWidgetNotAvailable();
		orderDetailPart.close();
	}

	private void errorIfWidgetNotAvailable() {
		Object widget = orderDetailPart.getPart().getWidget();
		Object renderer = orderDetailPart.getPart().getRenderer();
		if (widget == null && renderer == null) {
			LOGGER.error("widget and renderer is null there is nothing to close!");
			String text = orderDetailPart.getPart() == null ? "part is null" : orderDetailPart.getPart()
					.toString();
			LOGGER.info("\n\n"
					+ "-------------------------------------------------\n"
					+ "part: " + text);
			String title = orderDetailPart.getTitle();
			LOGGER.info("title: " + title);
			LOGGER.info("\n"
					+ "-------------------------------------------------\n\n");
			throw new IllegalArgumentException("could not close \"" + title + "\" due to no widget available");
		}
	}
}
