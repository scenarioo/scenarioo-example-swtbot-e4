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

package org.scenarioo.example.e4.services.osgi;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scenarioo.example.e4.domain.Article;
import org.scenarioo.example.e4.domain.ArticleId;
import org.scenarioo.example.e4.domain.Unit;
import org.scenarioo.example.e4.dto.ArticleSearchFilterDTO;
import org.scenarioo.example.e4.services.ArticleService;
import org.scenarioo.example.e4.services.internal.Counter;
import org.scenarioo.example.e4.services.internal.SimulateServiceCall;
import org.scenarioo.example.e4.services.internal.idstores.ArticleIdStore;
import org.scenarioo.example.e4.services.internal.idstores.IdStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleServiceImpl implements ArticleService {

	private static Logger LOGGER = LoggerFactory.getLogger(ArticleServiceImpl.class);

	private final Counter counter = Counter.getInstance();
	private final ArticleIdStore articleIdStore = (ArticleIdStore) IdStore.getInstance(Article.class);

	public ArticleServiceImpl() {
		articleIdStore.put(createDarthVaderArticle());
		articleIdStore.put(createAnakinSkywalkerArticle());
		articleIdStore.put(createYodaArticle());
		articleIdStore.put(createLukeSkywalkerArticle());
		articleIdStore.put(createHanSoloArticle());
		articleIdStore.put(createObiWanKenobiArticle());
	}

	/**
	 * @see org.scenarioo.example.e4.services.ArticleService#createArticle(org.scenarioo.example.e4.domain.Article)
	 */
	@Override
	public Article createArticle(final Article article) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.scenarioo.example.e4.services.ArticleService#getArticle(org.scenarioo.example.e4.domain.ArticleId[])
	 */
	@Override
	public Map<ArticleId, Article> getArticle(final ArticleId... articleIds) {

		SimulateServiceCall.start();

		return articleIdStore.get(articleIds);
	}

	/**
	 * @see org.scenarioo.example.e4.services.ArticleService#getArticle(org.scenarioo.example.e4.dto.ArticleSearchFilterDTO)
	 */
	@Override
	public Set<Article> getArticle(final ArticleSearchFilterDTO articleSearchFilterDTO) {

		SimulateServiceCall.start();

		Set<Article> articles = new HashSet<Article>();
		for (Article article : articleIdStore.values()) {
			articles.add(article);
		}

		LOGGER.info(articles.size() + " Articles has been returned!");

		return articles;
	}

	private Article createDarthVaderArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Darth Vader");
		article.setDescription("Supreme Commander of the Imperial Fleet");
		article.setUnit(Unit.PIECE);
		return article;
	}

	private Article createAnakinSkywalkerArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Anakin Skywalker");
		article.setDescription("Jedi Knight, General in the Grand Army of the Republic");
		article.setUnit(Unit.PIECE);
		return article;
	}

	private Article createYodaArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Yoda");
		article.setDescription("Jedi Grand Master of the Order, Jedi Master of the High Council");
		article.setUnit(Unit.PIECE);
		return article;
	}

	private Article createLukeSkywalkerArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Luke Skywalker");
		article.setDescription("Jedi Knight, Jedi Master, Grand Master of the New Jedi Order");
		article.setUnit(Unit.PIECE);
		return article;
	}

	private Article createHanSoloArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Han Solo");
		article.setDescription("Captain of the Millennium Falcon, General in the Rebel Alliance/New Republic smuggler");
		article.setUnit(Unit.PIECE);
		return article;
	}

	private Article createObiWanKenobiArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Obi-Wan Kenobi");
		article.setDescription("Jedi Padawan, Jedi Knight, Jedi Master, Jedi Council Member, Jedi General");
		article.setUnit(Unit.PIECE);
		return article;
	}

}
