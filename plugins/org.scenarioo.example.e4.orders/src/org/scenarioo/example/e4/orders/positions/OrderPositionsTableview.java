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

package org.scenarioo.example.e4.orders.positions;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.domain.OrderPositions;
import org.scenarioo.example.e4.dto.OrderPositionsTableviewDTO;
import org.scenarioo.example.e4.dto.PositionWithArticleInfo;
import org.scenarioo.example.e4.orders.ImagesOfThisPlugin;
import org.scenarioo.example.e4.services.ArticleService;

public class OrderPositionsTableview {

	// Data model
	private final OrderPositions orderPositions;

	// UI Widgets
	private final Label posAmountErrorMsg;
	private final Label inputCorrectMsgLabel;
	private final Text orderNumberText;
	private final Button addPositionButton;
	private final Button removePositionButton;
	private final TableViewer viewer;
	private final Composite container;

	public OrderPositionsTableview(final Composite parent, final ArticleService articleService,
			final OrderPositionsTableviewDTO orderPositionsForViewDTO) {
		this.orderPositions = orderPositionsForViewDTO.getOrderPositions();
		this.container = new Composite(parent, SWT.NONE);

		// Order header Information
		GridLayout layout = new GridLayout(4, true);
		container.setLayout(layout);

		// Order Number
		Label orderNumberLabel = new Label(container, SWT.NONE);
		orderNumberLabel.setText("Order Number");
		orderNumberText = new Text(container, SWT.BORDER | SWT.SINGLE);
		orderNumberText.setEnabled(false);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		orderNumberText.setLayoutData(gridData);
		updateOrderInfo(orderPositionsForViewDTO.getOrder());

		this.viewer = new TableViewer(container, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		// Add Position Button
		this.inputCorrectMsgLabel = new Label(container, SWT.NONE);
		GridData errorMsgGridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		this.inputCorrectMsgLabel.setLayoutData(errorMsgGridData);
		new Label(container, SWT.NONE);
		addPositionButton = new Button(container, SWT.PUSH);
		addPositionButton.setImage(ImagesOfThisPlugin.ADD_BUTTON.getImage());
		addPositionButton.setToolTipText("Add Position");
		GridData buttonGridData = new GridData();
		buttonGridData.horizontalAlignment = GridData.END;
		addPositionButton.setLayoutData(buttonGridData);
		addPositionButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				List<PositionWithArticleInfo> inputData = getInputData();
				Integer porNr = inputData.size() + 1;
				inputData.add(new PositionWithArticleInfo(porNr));
				viewer.setInput(inputData);
			}
		});
		Label createPositionLabel = new Label(container, SWT.NONE);
		createPositionLabel.setText("Add Position");

		// Remove Position Button
		this.posAmountErrorMsg = new Label(container, SWT.NONE);
		posAmountErrorMsg.setLayoutData(errorMsgGridData);
		new Label(container, SWT.NONE);
		this.removePositionButton = new Button(container, SWT.PUSH);
		this.removePositionButton.setImage(ImagesOfThisPlugin.DELETE_BUTTON.getImage());
		this.removePositionButton.setToolTipText("Remove Position");
		this.removePositionButton.setLayoutData(buttonGridData);
		this.removePositionButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				Table table = viewer.getTable();
				int[] selectedRows = table.getSelectionIndices();
				List<PositionWithArticleInfo> inputData = getInputData();
				// reverse iteration over selection Array
				for (int i = selectedRows.length - 1; i >= 0; i--) {
					// remove the deleted data
					inputData.remove(selectedRows[i]);
				}
				// renew the posNr of the remaining data
				int i = 1;
				for (PositionWithArticleInfo posWithArticleInfo : inputData) {
					posWithArticleInfo.setPosNr(i);
					i++;
				}
				viewer.setInput(inputData);
			}
		});
		Label removePositionLabel = new Label(container, SWT.NONE);
		removePositionLabel.setText("Remove position");

		// initialize Table
		PositionsTableHelper.initializeColumns(viewer, articleService, posAmountErrorMsg);
		setTableInputAndLayout(orderPositionsForViewDTO.getPositionsWithArticleInfo());
	}

	@SuppressWarnings("unchecked")
	private List<PositionWithArticleInfo> getInputData() {
		return (List<PositionWithArticleInfo>) viewer.getInput();
	}

	public void updateOrderInfo(final Order order) {
		this.orderNumberText.setText(getTextOrEmpty(order.getOrderNumber()));
	}

	private void setTableInputAndLayout(final List<PositionWithArticleInfo> input) {
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new PositionsTableLabelProvider());
		// get the content for the viewer, setInput will call getElements in the
		// contentProvider
		viewer.setInput(input);
		// make the selection available to other views
		// getSite().setSelectionProvider(viewer);
		// set the sorter for the table

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 4;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
	}

	public TableViewer getViewer() {
		return viewer;
	}

	public void addArticleIdSelectionListener(final ColumnViewerEditorActivationListener listener) {
		viewer.getColumnViewerEditor().addEditorActivationListener(listener);
	}

	public void addSelectionListenerOnAddRemovePositionButton(final SelectionListener listener) {
		this.addPositionButton.addSelectionListener(listener);
		this.removePositionButton.addSelectionListener(listener);
	}

	public boolean isInputCorrect() {
		// ArticelId must be set!

		List<PositionWithArticleInfo> inputData = getInputData();
		if (inputData.size() == 0) {
			inputCorrectMsgLabel.setText("Add at Least one Position.");
			return false;
		}
		for (PositionWithArticleInfo articleInfo : inputData) {
			if (articleInfo.getArticle() == null) {
				inputCorrectMsgLabel.setText("PosNr. " + articleInfo.getPosNr() + " has no Article Selected.");
				return false;
			}
		}

		inputCorrectMsgLabel.setText("");
		return true;
	}

	public Composite getControl() {
		return container;
	}

	private String getTextOrEmpty(final String text) {
		return text != null ? text : "";
	}

	public OrderPositions getOrderPositionsForUpdate() {
		OrderPositions orderPositions = new OrderPositions(this.orderPositions);

		// add All the data from Table
		List<PositionWithArticleInfo> inputData = getInputData();
		for (PositionWithArticleInfo pos : inputData) {
			orderPositions.addPosition(pos.getPosition());
		}
		return orderPositions;
	}
}
