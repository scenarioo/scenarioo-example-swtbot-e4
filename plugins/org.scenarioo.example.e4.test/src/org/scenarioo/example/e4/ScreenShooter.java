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

import java.io.ByteArrayOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Calculates minimal used Area and makes a screenshot of it. Handles the UI Thread Synchronization by itself.
 */
public class ScreenShooter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScreenShooter.class);

	public byte[] capture() {

		final Rectangle biggestRectangle = getWidthestAndHighestBounds();
		BytesOfScreenshot screenshooter = new BytesOfScreenshot(biggestRectangle);

		UIThreadRunnable.syncExec(screenshooter);
		return screenshooter.data;
	}

	private class BytesOfScreenshot implements Result<byte[]> {

		private byte[] data = new byte[0];
		final Rectangle biggestRectangle;

		private BytesOfScreenshot(final Rectangle biggestRectangle) {
			this.biggestRectangle = biggestRectangle;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public byte[] run() {
			final Display display = Display.getDefault();
			Image image = new Image(display, biggestRectangle);
			GC gc = new GC(Display.getDefault());
			gc.copyArea(image, biggestRectangle.x, biggestRectangle.y);

			ImageLoader imageLoader = new ImageLoader();
			imageLoader.data = new ImageData[] { image.getImageData() };
			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			imageLoader.save(byteOutput, SWT.IMAGE_PNG);
			data = byteOutput.toByteArray();

			gc.dispose();
			return data;
		}
	}

	private Rectangle getWidthestAndHighestBounds() {
		BiggestRectangleCalculator calc = new BiggestRectangleCalculator();
		Display.getDefault().syncExec(calc);
		return calc.result;
	}

	private class BiggestRectangleCalculator implements Runnable {

		private Rectangle result = null;

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			final Display display = Display.getDefault();
			Rectangle biggestRectangle = null;

			Rectangle screenBounds = display.getBounds();
			LOGGER.debug("Screen: " + screenBounds);
			int i = 0;
			for (Shell shell : display.getShells()) {
				// We dont want to have Screenshots from shells like
				// the "PartRenderingEngine's limbo" from e4
				// How can we reach that? => Filter out all Shells without name and all shells outside from screen.
				LOGGER.debug("Shell " + i + " text: " + shell.getText() + " shell.toString(): " + shell.toString());
				Rectangle rectangle = shell.getBounds();
				LOGGER.debug("Rechteck " + i + ": " + rectangle.toString());
				if (!StringUtils.isEmpty(shell.getText()) && screenBounds.intersects(rectangle)) {
					LOGGER.debug("Shell " + i + " text: " + shell.getText() + " added.");
					if (biggestRectangle == null) {
						biggestRectangle = rectangle;
					} else {
						biggestRectangle = biggestRectangle.union(rectangle);
					}
				}
				i++;
			}
			this.result = biggestRectangle;
		}
	}
}
