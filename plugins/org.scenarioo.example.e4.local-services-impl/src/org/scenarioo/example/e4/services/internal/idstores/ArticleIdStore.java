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

package org.scenarioo.example.e4.services.internal.idstores;

import java.util.HashMap;
import java.util.Map;

import org.scenarioo.example.e4.domain.Article;
import org.scenarioo.example.e4.domain.ArticleId;
import org.scenarioo.example.e4.domain.OrderPositions;
import org.scenarioo.example.e4.domain.Position;
import org.scenarioo.example.e4.services.internal.dummydata.ArticlesBuilder;

public class ArticleIdStore extends IdStore<ArticleId, Article> {

	protected ArticleIdStore() {
		ArticlesBuilder articlesBuilder = ArticlesBuilder.getInstance();
		super.add(articlesBuilder.createDarthVaderArticle());
		super.add(articlesBuilder.createAnakinSkywalkerArticle());
		super.add(articlesBuilder.createYodaArticle());
		super.add(articlesBuilder.createLukeSkywalkerArticle());
		super.add(articlesBuilder.createHanSoloArticle());
		super.add(articlesBuilder.createObiWanKenobiArticle());
		super.add(articlesBuilder.createAaylaSecuraArticle());
		super.add(articlesBuilder.createBobaFettArticle());
		super.add(articlesBuilder.createAckbarArticle());
		super.add(articlesBuilder.createAhsokaTanoArticle());
		super.add(articlesBuilder.createBossNassArticle());
		super.add(articlesBuilder.createChewbaccaArticle());
		super.add(articlesBuilder.createGeneralTarpalsArticle());
		super.add(articlesBuilder.createGreedoArticle());

		super.add(articlesBuilder.createImperialProbeDroidArticle());
		super.add(articlesBuilder.createJarJarBinksArticle());
		super.add(articlesBuilder.createJabbaTheHuttArticle());
		super.add(articlesBuilder.createJawasArticle());
		super.add(articlesBuilder.createKitFistoArticle());
		super.add(articlesBuilder.createNuteGunrayArticle());
		super.add(articlesBuilder.createPadmeAmidalaArticle());
		super.add(articlesBuilder.createPloKoonJediArticle());
		super.add(articlesBuilder.createR2D2Article());
		super.add(articlesBuilder.createSebulbaArticle());
		super.add(articlesBuilder.createSuperBattleDroidArticle());
		super.add(articlesBuilder.createTuskenRidersArticle());
		super.add(articlesBuilder.createWattoArticle());

	}

	public Map<ArticleId, Article> getArticles(
			final OrderPositions positions) {

		Map<ArticleId, Article> articlesMap = new HashMap<ArticleId, Article>();
		for (Position pos : positions.getPositions()) {
			Article article = super.get(pos.getArticleId());
			articlesMap.put(article.getId(), article);
		}

		return articlesMap;
	}

	public static ArticleIdStore getInstance() {
		return (ArticleIdStore) IdStore.getInstance(Article.class);
	}
}
