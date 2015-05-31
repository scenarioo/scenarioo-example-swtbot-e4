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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scenarioo.example.e4.domain.Article;
import org.scenarioo.example.e4.domain.ArticleId;

public class ArticleComboHelper {

	private static final String DISPLAYABLE_SEPARATOR = "-";

	private final Map<ArticleId, Integer> indexForArticleId = new HashMap<ArticleId, Integer>();
	private final Map<Integer, Article> articleForIndex = new HashMap<Integer, Article>();
	private String[] articlesForCB;

	public void init(final Set<Article> articlesSet) {

		List<Article> articles = new ArrayList<Article>(articlesSet);

		Collections.sort(articles, new ArticleNrSort());

		// Array
		this.articlesForCB = new String[articles.size()];
		this.indexForArticleId.clear();
		this.articleForIndex.clear();
		int i = 0;
		for (Article article : articles) {
			this.articlesForCB[i] = getDisplayableArticleString(article);
			this.indexForArticleId.put(article.getId(), i);
			this.articleForIndex.put(i, article);
			i++;
		}
	}

	public String[] getArticlesForCB() {
		return articlesForCB;
	}

	public Integer getIndexForArticleId(final ArticleId articleId) {
		return this.indexForArticleId.get(articleId);
	}

	public Article getArticleForIndex(final Integer index) {
		return articleForIndex.get(index);
	}

	public Article getArticleForId(final ArticleId articleId) {
		int index = getIndexForArticleId(articleId);
		return getArticleForIndex(index);
	}

	private String getDisplayableArticleString(final Article article) {
		StringBuilder sb = new StringBuilder();
		sb.append(article.getId().getId());
		sb.append(DISPLAYABLE_SEPARATOR);
		sb.append(article.getArticleNumber());
		sb.append(DISPLAYABLE_SEPARATOR);
		sb.append(article.getShortDescription());
		sb.append(DISPLAYABLE_SEPARATOR);
		sb.append(article.getUnit().getCaption());
		return sb.toString();
	}

	private class ArticleNrSort implements Comparator<Article> {

		/**
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(final Article o1, final Article o2) {

			int artNrCompare = o1.getArticleNumber().compareToIgnoreCase(o2.getArticleNumber());
			if (artNrCompare != 0) {
				return artNrCompare;
			}

			return o1.getDescription().compareToIgnoreCase(o2.getDescription());
		}
	}
}
