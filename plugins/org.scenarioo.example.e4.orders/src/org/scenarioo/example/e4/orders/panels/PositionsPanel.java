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

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.scenarioo.example.e4.domain.Article;
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.domain.OrderPositions;
import org.scenarioo.example.e4.dto.OrderPositionsViewDTO;
import org.scenarioo.example.e4.dto.PositionWithArticleInfo;
import org.scenarioo.example.e4.orders.ImagesOfThisPlugin;
import org.scenarioo.example.e4.services.ArticleService;

public class PositionsPanel {

	// Data model
	private final OrderPositions orderPositions;

	// UI Widgets
	private final Text orderNumberText;
	private TableViewer viewer;
	private final Composite container;

	// For ArticleSelection is Service required
	private final ArticleService articleService;

	public PositionsPanel(final Composite parent, final ArticleService articleService,
			final OrderPositionsViewDTO orderPositionsForViewDTO) {
		this.articleService = articleService;
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

		createTableViewer(container, orderPositionsForViewDTO.getPositionsWithArticleInfo());

		// Add Position Line
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		Button createButton = new Button(container, SWT.PUSH);
		createButton.setImage(ImagesOfThisPlugin.ADD_BUTTON.getImage());
		createButton.setToolTipText("Add Position");
		GridData buttonGridData = new GridData();
		buttonGridData.horizontalAlignment = GridData.END;
		createButton.setLayoutData(buttonGridData);
		createButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				List<PositionWithArticleInfo> inputData = getInputData();
				Integer porNr = inputData.size() + 1;
				inputData.add(new PositionWithArticleInfo(porNr));
				viewer.setInput(inputData);
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Label createPositionLabel = new Label(container, SWT.NONE);
		createPositionLabel.setText("Add Position");

		// Remove Position Button
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		Button deleteButton = new Button(container, SWT.PUSH);
		deleteButton.setImage(ImagesOfThisPlugin.DELETE_BUTTON.getImage());
		deleteButton.setToolTipText("Remove Position");
		deleteButton.setLayoutData(buttonGridData);
		deleteButton.addSelectionListener(new SelectionListener() {

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

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Label removePositionLabel = new Label(container, SWT.NONE);
		removePositionLabel.setText("Remove position");
	}

	@SuppressWarnings("unchecked")
	private List<PositionWithArticleInfo> getInputData() {
		return (List<PositionWithArticleInfo>) viewer.getInput();
	}

	public void updateOrderInfo(final Order order) {
		this.orderNumberText.setText(getTextOrEmpty(order.getOrderNumber()));
	}

	private void createTableViewer(final Composite parent, final List<PositionWithArticleInfo> input) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());
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

	// create the columns for the table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Nr.", "State", "Article Number", "Description", "Amount", "Unit" };
		int[] bounds = { 100, 100, 100, 100, 100, 100 };

		// first column is for PositionNr
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				PositionWithArticleInfo posWithArtInfo = (PositionWithArticleInfo) element;
				return posWithArtInfo.getPosNr().toString();
			}
		});

		// second column is for the Position State
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				PositionWithArticleInfo posWithArtInfo = (PositionWithArticleInfo) element;
				return posWithArtInfo.getPositon().getState().getCaption();
			}
		});

		// now the ArticleNr
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				PositionWithArticleInfo posWithArtInfo = (PositionWithArticleInfo) element;
				Article article = posWithArtInfo.getArticle();
				return article == null ? "" : article.getArticleNumber();
			}
		});

		// now the Article Description
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				PositionWithArticleInfo posWithArtInfo = (PositionWithArticleInfo) element;
				Article article = posWithArtInfo.getArticle();
				return article == null ? "" : article.getDescription();
			}
		});

		// now the Position Amount
		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				PositionWithArticleInfo posWithArtInfo = (PositionWithArticleInfo) element;
				return posWithArtInfo.getPositon().getAmount().toString();
			}
		});

		// now the Unit
		col = createTableViewerColumn(titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				PositionWithArticleInfo posWithArtInfo = (PositionWithArticleInfo) element;
				Article article = posWithArtInfo.getArticle();
				return article == null ? "" : article.getUnit().getCaption();
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

		// add All the data from Table
		List<PositionWithArticleInfo> inputData = getInputData();
		for (PositionWithArticleInfo pos : inputData) {
			orderPositions.addPosition(pos.getPositon());
		}
		return orderPositions;
	}
}
