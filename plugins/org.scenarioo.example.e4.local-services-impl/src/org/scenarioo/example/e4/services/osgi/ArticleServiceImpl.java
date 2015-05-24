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

import java.util.Map;
import java.util.Set;

import org.scenarioo.example.e4.domain.Article;
import org.scenarioo.example.e4.domain.ArticleId;
import org.scenarioo.example.e4.dto.ArticleSearchFilterDTO;
import org.scenarioo.example.e4.services.ArticleService;

public class ArticleServiceImpl implements ArticleService {

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
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.scenarioo.example.e4.services.ArticleService#getArticle(org.scenarioo.example.e4.dto.ArticleSearchFilterDTO)
	 */
	@Override
	public Set<Article> getArticle(final ArticleSearchFilterDTO articleSearchFilterDTO) {
		// TODO Auto-generated method stub
		return null;
	}


}
