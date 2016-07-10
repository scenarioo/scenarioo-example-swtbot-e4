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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swtbot.e4.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.e4.finder.widgets.WorkbenchContentsFinder;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.hamcrest.Matcher;
import org.scenarioo.example.e4.EclipseContextHelper;
import org.scenarioo.example.e4.pages.CreateAddPositionEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaitForOnlyVisibleParts extends DefaultCondition {

	private static final Logger LOGGER = LoggerFactory.getLogger(WaitForOnlyVisibleParts.class);

	private final IEclipseContext context;
	private final int indexOfVisibleParts;
	private final Matcher<MPart> partMatcher;
	private final List<MPart> onlyVisibleParts = new ArrayList<MPart>();

	private final String viewTitle;

	public WaitForOnlyVisibleParts(final CreateAddPositionEditor createAddPositionEditor) {
		this(createAddPositionEditor.getViewTitle(), createAddPositionEditor.getIndex());
	}

	private WaitForOnlyVisibleParts(final String viewTitle, final int indexOfVisibleParts) {
		this.viewTitle = viewTitle;
		this.context = EclipseContextHelper.getEclipseContext();
		this.indexOfVisibleParts = indexOfVisibleParts;
		this.partMatcher = WidgetMatcherFactory.withPartName(viewTitle);
	}

	/**
	 * @see org.eclipse.swtbot.swt.finder.waits.ICondition#test()
	 */
	@Override
	public boolean test() throws Exception {
		onlyVisibleParts.clear();
		List<MPart> findAllAvailableParts = findAllAvailableParts();
		for (MPart mpart : findAllAvailableParts) {
			if (isPartVisible(mpart)) {
				onlyVisibleParts.add(mpart);
			} else {
				LOGGER.info("There was an invisible part filtered out: " + mpart.toString());
			}
		}
		return onlyVisibleParts.size() == indexOfVisibleParts + 1;
	}

	private List<MPart> findAllAvailableParts() {
		return new WorkbenchContentsFinder(context).findParts(partMatcher);
	}

	private boolean isPartVisible(final MPart mpart) {
		Object widget = mpart.getWidget();
		Object renderer = mpart.getRenderer();
		if (widget == null && renderer == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @see org.eclipse.swtbot.swt.finder.waits.ICondition#getFailureMessage()
	 */
	@Override
	public String getFailureMessage() {
		return "Could only find " + onlyVisibleParts.size() + " parts with title=" + viewTitle + " but"
				+ (indexOfVisibleParts + 1) + " parts were expected to be visible.";
	}

	public MPart getPart() {
		return onlyVisibleParts.get(indexOfVisibleParts);
	}
}
