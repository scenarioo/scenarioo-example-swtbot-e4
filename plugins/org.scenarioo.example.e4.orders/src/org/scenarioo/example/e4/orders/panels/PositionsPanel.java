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

package org.scenarioo.example.e4.orders.panels;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.domain.OrderPositions;
import org.scenarioo.example.e4.dto.OrderWithPositions;
import org.scenarioo.example.e4.dto.PositionWithArticleInfo;

public class PositionsPanel {

	// Data model
	private final OrderPositions orderPositions;

	// UI Widgets
	private final Text orderNumberText;
	private TableViewer viewer;
	private final Composite container;

	public PositionsPanel(final Composite parent, final OrderWithPositions orderWithPositions) {
		this.orderPositions = orderWithPositions.getOrderPositions();
		this.container = new Composite(parent, SWT.NONE);

		// Order header Information
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		// Order Number
		Label orderNumberLabel = new Label(container, SWT.NONE);
		orderNumberLabel.setText("Order Number");
		orderNumberText = new Text(container, SWT.BORDER | SWT.SINGLE);
		orderNumberText.setEnabled(false);
		orderNumberText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		updateOrderInfo(orderWithPositions.getOrder());
		
		createViewer(container);
	}

	public void updateOrderInfo(final Order order) {
		this.orderNumberText.setText(getTextOrEmpty(order.getOrderNumber()));
	}

	private void createViewer(final Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());
		// get the content for the viewer, setInput will call getElements in the
		// contentProvider
		viewer.setInput(orderPositions.getPositions());
		// make the selection available to other views
		// getSite().setSelectionProvider(viewer);
		// set the sorter for the table

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
	}

	public TableViewer getViewer() {
		return viewer;
	}

	// create the columns for the table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Nr.", "Article Number", "Description", "Amount", "Unit" };
		int[] bounds = { 100, 100, 100, 100, 100 };

		// first column is for PositionNr
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				PositionWithArticleInfo posWithArtInfo = (PositionWithArticleInfo) element;
				return posWithArtInfo.getPosNr().toString();
			}
		});

		// second column is for the ArticleNr
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				PositionWithArticleInfo posWithArtInfo = (PositionWithArticleInfo) element;
				return posWithArtInfo.getArticle().getArticleNumber();
			}
		});

		// now the Article Description
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				PositionWithArticleInfo posWithArtInfo = (PositionWithArticleInfo) element;
				return posWithArtInfo.getArticle().getDescription();
			}
		});

		// now the Position Amount
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				PositionWithArticleInfo posWithArtInfo = (PositionWithArticleInfo) element;
				return posWithArtInfo.getPositon().getAmount().toString();
			}
		});

		// now the Unit
		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				PositionWithArticleInfo posWithArtInfo = (PositionWithArticleInfo) element;
				return posWithArtInfo.getArticle().getUnit().toString();
			}
		});

	}

	private TableViewerColumn createTableViewerColumn(final String title, final int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	public void addArticleIdSelectionListener(final ISelectionChangedListener selectionChangedListener) {
		this.viewer.addSelectionChangedListener(selectionChangedListener);
	}

	public boolean mandatoryFieldsNonEmpty() {
		// ArticelId must be set!

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
		// get All the Positions from Table

		return orderPositions;
	}
}
