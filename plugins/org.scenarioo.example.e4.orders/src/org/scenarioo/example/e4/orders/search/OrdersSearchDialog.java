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

package org.scenarioo.example.e4.orders.search;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.scenarioo.example.e4.domain.Article;
import org.scenarioo.example.e4.domain.ArticleId;
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.domain.OrderState;
import org.scenarioo.example.e4.dto.ArticleSearchFilterDTO;
import org.scenarioo.example.e4.dto.OrderSearchFilter;
import org.scenarioo.example.e4.orders.OrderPluginImages;
import org.scenarioo.example.e4.orders.positions.edit.ArticleComboHelper;
import org.scenarioo.example.e4.services.ArticleService;
import org.scenarioo.example.e4.services.OrderService;

public class OrdersSearchDialog extends TitleAreaDialog {

	// Services
	private final OrderService orderService;

	// UI Widgets
	private Text orderNumberText;
	private Combo stateCombo;
	private DateTime deliveryDateTime;
	private Combo articleCombo;
	private TableViewer resultTableViewer;

	// start search activity
	private Button startSearchActivity;

	// Helper for articleCombo
	private final ArticleComboHelper articleComboHelper = new ArticleComboHelper();

	// for return
	private final List<Order> importOrders = new ArrayList<Order>();

	public OrdersSearchDialog(final Shell parentShell, final ArticleService articleService,
			final OrderService orderService) {
		super(parentShell);
		this.articleComboHelper.init(articleService.getArticle(new ArticleSearchFilterDTO()));
		this.orderService = orderService;
	}

	@Override
	public void create() {
		super.create();
		setTitle("Choose the Orders which you want to open");
		setMessage("Activate the Checkbox beside the Order", IMessageProvider.INFORMATION);
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(4, true);
		container.setLayout(layout);

		createOrderNumberText(container);
		createOrderStateCombo(container);
		createDeliveryDateTime(container);
		createArticleCombo(container);
		createResultTableViewer(container);
		createSearchActivityButton(container);

		return area;
	}

	private void createOrderNumberText(final Composite container) {
		Label orderNumberLabel = new Label(container, SWT.NONE);
		orderNumberLabel.setText("Order Number");
		orderNumberText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		orderNumberText.setLayoutData(gridData);
	}

	private void createOrderStateCombo(final Composite container) {
		Label orderStateLabel = new Label(container, SWT.NONE);
		orderStateLabel.setText("Order State");
		stateCombo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		stateCombo.setItems(OrderState.getItems());
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		stateCombo.setLayoutData(gridData);
	}

	private void createDeliveryDateTime(final Composite container) {
		Label deliveryDateLabel = new Label(container, SWT.NONE);
		deliveryDateLabel.setText("Delivery Date");
		deliveryDateTime = new DateTime(container, SWT.BORDER | SWT.SINGLE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		deliveryDateTime.setLayoutData(gridData);
	}

	private void createArticleCombo(final Composite container) {
		Label articleNumberLabel = new Label(container, SWT.NONE | SWT.READ_ONLY);
		articleNumberLabel.setText("Article Number");
		articleCombo = new Combo(container, SWT.BORDER | SWT.SINGLE);
		articleCombo.setItems(articleComboHelper.getArticlesForCB());
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		articleCombo.setLayoutData(gridData);
	}

	private void createResultTableViewer(final Composite container) {
		this.resultTableViewer = new TableViewer(container, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		OrderSearchTableHelper.initializeColumns(resultTableViewer);

		final Table table = resultTableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		resultTableViewer.setContentProvider(new ArrayContentProvider());
		resultTableViewer.setLabelProvider(new OrderSearchResultTableLabelProvider());

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 4;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		resultTableViewer.getControl().setLayoutData(gridData);
	}

	private void createSearchActivityButton(final Composite container) {
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		this.startSearchActivity = new Button(container, SWT.PUSH);
		this.startSearchActivity.setImage(OrderPluginImages.SEARCH_24.getImage());
		this.startSearchActivity.setToolTipText("Start Search");
		GridData buttonGridData = new GridData();
		buttonGridData.horizontalAlignment = GridData.END;
		this.startSearchActivity.setLayoutData(buttonGridData);
		this.startSearchActivity.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				List<OrderTableResult> tableResults = new ArrayList<OrderTableResult>();
				Set<Order> orders = orderService.searchForOrders(getOrderSearchInput());
				for (Order order : orders) {
					OrderTableResult tableResult = new OrderTableResult(order);
					tableResults.add(tableResult);
				}
				resultTableViewer.setInput(tableResults);
			}
		});
		Label removePositionLabel = new Label(container, SWT.NONE);
		removePositionLabel.setText("Start Search");
	}

	private OrderSearchFilter getOrderSearchInput() {

		OrderSearchFilter osf = new OrderSearchFilter();
		osf.setOrderNumber(orderNumberText.getText());
		osf.setDeliveryDate(getDeliveryDate());
		osf.setState(OrderState.getSelectedState(stateCombo.getSelectionIndex()));
		osf.setArticleId(getSelectedArticleId());

		return osf;
	}

	private ArticleId getSelectedArticleId() {

		int index = articleCombo.getSelectionIndex();
		if (index != -1) {
			Article article = articleComboHelper.getArticleForIndex(index);
			if (article != null) {
				return article.getId();
			}
		}
		return null;
	}

	private Date getDeliveryDate() {
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.DAY_OF_MONTH, deliveryDateTime.getDay());
		instance.set(Calendar.MONTH, deliveryDateTime.getMonth());
		instance.set(Calendar.YEAR, deliveryDateTime.getYear());
		return instance.getTime();
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	// save content of the Text fields because they get disposed
	// as soon as the Dialog closes
	private void saveInput() {
		List<OrderTableResult> tableResults = getTableViewerInputData();
		for (OrderTableResult tr : tableResults) {
			if (tr.isImport()) {
				importOrders.add(tr.getOrder());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private List<OrderTableResult> getTableViewerInputData() {
		return (List<OrderTableResult>) resultTableViewer.getInput();
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public List<Order> getSelectedOrders() {
		return importOrders;
	}

}
