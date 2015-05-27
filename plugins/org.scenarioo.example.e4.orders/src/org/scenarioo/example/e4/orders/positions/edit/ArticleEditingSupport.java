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

package org.scenarioo.example.e4.orders.positions.edit;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.scenarioo.example.e4.domain.Article;
import org.scenarioo.example.e4.dto.ArticleSearchFilterDTO;
import org.scenarioo.example.e4.dto.PositionWithArticleInfo;
import org.scenarioo.example.e4.services.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleEditingSupport extends EditingSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleEditingSupport.class);

	private final TableViewer viewer;
	private final ArticleService articleService;
	private final ArticleComboHelper articleComboHelper;

	public ArticleEditingSupport(final TableViewer viewer, final ArticleService articleService) {
		super(viewer);
		this.viewer = viewer;
		this.articleService = articleService;
		this.articleComboHelper = new ArticleComboHelper();
	}

	@Override
	protected CellEditor getCellEditor(final Object element) {
		articleComboHelper.init(articleService.getArticle(new ArticleSearchFilterDTO()));
		return new ComboBoxCellEditor(viewer.getTable(), articleComboHelper.getArticlesForCB(), SWT.READ_ONLY);
	}

	@Override
	protected boolean canEdit(final Object element) {
		return true;
	}

	@Override
	protected Object getValue(final Object element) {
		PositionWithArticleInfo posWithArticleInfo = (PositionWithArticleInfo) element;
		Integer index;
		if (posWithArticleInfo.getArticle() != null) {
			index = articleComboHelper.getIndexForArticleId(posWithArticleInfo.getArticle().getId());
		} else {
			index = new Integer(0);
		}
		return index;
	}

	@Override
	protected void setValue(final Object element, final Object value) {
		PositionWithArticleInfo posWithArticleInfo = (PositionWithArticleInfo) element;
		Article oldArticle = posWithArticleInfo.getArticle();
		String oldArticleId = (oldArticle == null) ? "null" : oldArticle.getId().toString();
		posWithArticleInfo.setArticle(articleComboHelper.getArticleForIndex((Integer) value));
		viewer.update(element, null);
		LOGGER.info("New ArticleId=" + posWithArticleInfo.getArticle().getId() + " (oldArticleId=" + oldArticleId
				+ ") has been set for OrderPosition:" + posWithArticleInfo.getPosition());
	}
}