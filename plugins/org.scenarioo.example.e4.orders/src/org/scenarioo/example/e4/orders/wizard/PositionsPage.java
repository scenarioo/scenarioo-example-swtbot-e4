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

package org.scenarioo.example.e4.orders.wizard;

import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationListener;
import org.eclipse.jface.viewers.ColumnViewerEditorDeactivationEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.domain.OrderPositions;
import org.scenarioo.example.e4.dto.OrderPositionsTableviewDTO;
import org.scenarioo.example.e4.orders.positions.OrderPositionsTableview;
import org.scenarioo.example.e4.services.ArticleService;

public class PositionsPage extends WizardPage {

	private final ArticleService articleService;
	private final OrderPositionsTableviewDTO orderPositionsForViewDTO;
	private OrderPositionsTableview positionsPanel;

	public PositionsPage(final ArticleService articleService,
			final OrderPositionsTableviewDTO orderPositionsForViewDTO) {
		super("Positions Page");
		setTitle("Positions Page");
		setDescription("Enter the order positions");
		this.articleService = articleService;
		this.orderPositionsForViewDTO = orderPositionsForViewDTO;
		setPageComplete(false);
	}

	@Override
	public void createControl(final Composite parent) {
		this.positionsPanel = new OrderPositionsTableview(parent, articleService, orderPositionsForViewDTO);
		this.positionsPanel.addArticleIdSelectionListener(new ArticleIdSelectionListener());
		this.positionsPanel.addSelectionListenerOnAddRemovePositionButton(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				validateInput();
			}
		});
		setControl(positionsPanel.getControl());
		validateInput();
	}

	private void validateInput() {
		if (positionsPanel.isInputCorrect()) {
			setPageComplete(true);
		} else {
			setPageComplete(false);
		}
	}

	public OrderPositions getOrderPositionsForUpdate() {
		return positionsPanel.getOrderPositionsForUpdate();
	}

	public void updateOrderInfo(final Order order) {
		positionsPanel.updateOrderInfo(order);
	}

	private class ArticleIdSelectionListener extends ColumnViewerEditorActivationListener {

		@Override
		public void beforeEditorActivated(final ColumnViewerEditorActivationEvent event) {
			// Do Nothing
			// System.out.println("beforeEditorActivated");
		}

		@Override
		public void afterEditorActivated(final ColumnViewerEditorActivationEvent event) {
			// Do Nothing
			// System.out.println("afterEditorActivated");

		}

		@Override
		public void beforeEditorDeactivated(final ColumnViewerEditorDeactivationEvent event) {
			// Do Nothing
			// System.out.println("beforeEditorDeactivated");
		}

		@Override
		public void afterEditorDeactivated(final ColumnViewerEditorDeactivationEvent event) {
			// System.out.println("afterEditorDeactivated");
			validateInput();
		}
	}
}
