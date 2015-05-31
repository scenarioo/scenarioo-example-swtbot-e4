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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.scenarioo.example.e4.domain.Article;
import org.scenarioo.example.e4.domain.ArticleId;
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.domain.Position;
import org.scenarioo.example.e4.domain.PositionState;
import org.scenarioo.example.e4.dto.ArticleSearchFilterDTO;
import org.scenarioo.example.e4.orders.positions.edit.ArticleComboHelper;
import org.scenarioo.example.e4.services.ArticleService;

public class PositionDetailView {

	// UI Widgets
	private final Text orderNumberText; // Read Only in this context
	private final Combo positionStateCombo;
	private final Text positionNumberText; // Read Only in this context
	private final Label articleImageLabel;
	private final Combo articleCombo;
	private final Text articleDescriptionText;
	private final Text positionAmountText;
	private final Text articleUnitText; // Read Only
	private final Label errorMsg; // Amount must be higher 0

	// Helper for articleCombo
	private final ArticleComboHelper articleComboHelper = new ArticleComboHelper();

	// for return
	private Position position;

	public PositionDetailView(final Composite parent, final ArticleService articleService) {
		this.articleComboHelper.init(articleService.getArticle(new ArticleSearchFilterDTO()));

		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		this.orderNumberText = createOrderNumberText(container);
		this.positionNumberText = createPositionNumberText(container);
		this.positionStateCombo = createPositionStateCombo(container);
		this.articleImageLabel = createArticleImageLabel(container);
		this.articleCombo = createArticleCombo(container);
		this.articleDescriptionText = createArticleDescriptionText(container);
		this.positionAmountText = createAmountText(container);
		this.articleUnitText = createUnitText(container);
		this.errorMsg = createErrorMsgLabel(container);
	}

	private Text createOrderNumberText(final Composite container) {
		Label orderNumberLabel = new Label(container, SWT.NONE);
		orderNumberLabel.setText("Order Number");
		Text orderNumberText = new Text(container, SWT.BORDER | SWT.SINGLE);
		orderNumberText.setEnabled(false);
		orderNumberText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return orderNumberText;
	}

	private Text createPositionNumberText(final Composite container) {
		// Order State
		Label positionNumberLabel = new Label(container, SWT.NONE);
		positionNumberLabel.setText("Position Number");
		Text positionNumberText = new Text(container, SWT.BORDER | SWT.SINGLE);
		positionNumberText.setEnabled(false);
		positionNumberText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return positionNumberText;
	}

	private Combo createPositionStateCombo(final Composite container) {
		// Order State
		Label positionStateLabel = new Label(container, SWT.NONE);
		positionStateLabel.setText("Position State");
		Combo positionStateCombo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		positionStateCombo.setEnabled(false);
		positionStateCombo.setItems(PositionState.getItems());
		positionStateCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return positionStateCombo;
	}

	private Label createArticleImageLabel(final Composite container) {
		new Label(container, SWT.NONE);
		Label articleImageLabel = new Label(container, SWT.NONE);
		return articleImageLabel;
	}

	private Combo createArticleCombo(final Composite container) {
		Label articleNumberLabel = new Label(container, SWT.NONE | SWT.READ_ONLY);
		articleNumberLabel.setText("Article Number");
		Combo articleCombo = new Combo(container, SWT.BORDER | SWT.SINGLE);
		articleCombo.setItems(articleComboHelper.getArticlesForCB());
		articleCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		articleCombo.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				setArticle(getSelectedArticleId());
				hasErrorForInputData();
			}
		});
		return articleCombo;
	}

	public void addChangeListeners(final ModifyListener modifyListener, final KeyListener keyListner) {
		this.articleCombo.addModifyListener(modifyListener);
		this.positionAmountText.addKeyListener(keyListner);
	}

	private Text createArticleDescriptionText(final Composite container) {
		Label articleDescriptionLabel = new Label(container, SWT.NONE);
		articleDescriptionLabel.setText("Article Description");
		// final ScrolledComposite composite = new ScrolledComposite(container, SWT.MULTI | SWT.H_SCROLL |
		// SWT.V_SCROLL);
		final Text articleDescriptionText = new Text(container, SWT.READ_ONLY | SWT.V_SCROLL | SWT.MULTI | SWT.BORDER
				| SWT.WRAP);
		GridData grid = new GridData(GridData.FILL_HORIZONTAL);
		grid.heightHint = 150;
		articleDescriptionText.setLayoutData(grid);
		// composite.setContent(articleDescriptionText);
		// composite.setExpandHorizontal(true);
		// composite.setExpandVertical(true);
		// articleDescriptionText.setEnabled(false);
		return articleDescriptionText;
	}

	private Text createAmountText(final Composite container) {
		Label amountLabel = new Label(container, SWT.NONE);
		amountLabel.setText("Amount");
		Text amountText = new Text(container, SWT.BORDER | SWT.SINGLE);
		amountText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		amountText.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(final KeyEvent e) {
				hasErrorForInputData();
			}
		});
		return amountText;
	}

	private Text createUnitText(final Composite container) {
		Label unitLabel = new Label(container, SWT.NONE);
		unitLabel.setText("Unit");
		Text unitText = new Text(container, SWT.BORDER | SWT.SINGLE);
		unitText.setEnabled(false);
		unitText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return unitText;
	}

	private Label createErrorMsgLabel(final Composite container) {
		Label errorMsgLabel = new Label(container, SWT.NONE);
		return errorMsgLabel;
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

	public boolean hasErrorForInputData() {

		int index = articleCombo.getSelectionIndex();
		if (index == -1) {
			errorMsg.setText("Please Select an Article");
			return false;
		}
		try {
			Long amount = Long.parseLong(positionAmountText.getText());
			if (amount < 1) {
				errorMsg.setText("The minimum amount must be 1.");
				return false;
			}
		} catch (NumberFormatException ex) {
			errorMsg.setText("Your Amount must be a Number without decimal.");
			return false;
		}

		return true;
	}

	public void setPositionNumber(final Integer positionNumber) {
		positionNumberText.setText(positionNumber.toString());
	}

	/**
	 * After savePosition we need to receive the new Position with new version.
	 * 
	 * @param order
	 */
	public void setPosition(final Position position) {
		this.position = position;
		positionStateCombo.select(PositionState.getSelectedIndex(position.getState()));
		positionAmountText.setText(position.getAmount().toString());
		setArticle(position.getArticleId());
		if (PositionState.DELETED.equals(position.getState())) {
			disableAllInputFields();
		}
	}

	private void disableAllInputFields() {
		this.articleCombo.setEnabled(false);
		this.positionAmountText.setEnabled(false);
	}

	private void setArticle(final ArticleId articleId) {
		Article article = articleComboHelper.getArticleForId(articleId);
		this.articleCombo.select(articleComboHelper.getIndexForArticleId(articleId));
		this.articleImageLabel.setImage(article.getImageDescriptor().createImage());
		this.articleImageLabel.setSize(articleImageLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		this.articleDescriptionText.setText(article.getDescription());
		this.articleUnitText.setText(article.getUnit().getCaption());
	}

	public Position getPositionForUpdate() {
		Position position = new Position(this.position);
		position.setArticleId(getSelectedArticleId());
		position.setAmount(Long.parseLong(positionAmountText.getText()));
		return position;
	}

	/**
	 * @param order
	 */
	public void setOrder(final Order order) {
		orderNumberText.setText(order.getOrderNumber());
	}

	/**
	 * @return
	 */
	public boolean hasPositionChanged() {

		if (!this.position.getArticleId().equals(getSelectedArticleId())) {
			return true;
		}
		if (!this.position.getState().equals(PositionState.getSelectedState(positionStateCombo.getSelectionIndex()))) {
			return true;
		}
		try {
			Long amount = Long.parseLong(positionAmountText.getText());
			if (!amount.equals(this.position.getAmount())) {
				return true;
			}
		} catch (NumberFormatException ex) {
			return true;
		}

		return false;
	}

}
