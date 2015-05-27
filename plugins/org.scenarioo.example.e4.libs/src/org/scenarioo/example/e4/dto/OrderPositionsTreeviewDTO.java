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

package org.scenarioo.example.e4.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.scenarioo.example.e4.domain.Article;
import org.scenarioo.example.e4.domain.ArticleId;
import org.scenarioo.example.e4.domain.OrderPositions;
import org.scenarioo.example.e4.domain.Position;

public class OrderPositionsTreeviewDTO {

	private final OrderPositions orderPositions;
	private final Map<ArticleId, Article> articleInfoFromPositions;

	public OrderPositionsTreeviewDTO() {
		this.orderPositions = new OrderPositions();
		this.articleInfoFromPositions = new HashMap<ArticleId, Article>();
	}

	public OrderPositionsTreeviewDTO(final OrderPositions orderPositions,
			final Map<ArticleId, Article> articleInfoFromPositions) {
		this.orderPositions = orderPositions;
		this.articleInfoFromPositions = articleInfoFromPositions;
	}

	/**
	 * @return the positions
	 */
	public OrderPositions getOrderPositions() {
		return orderPositions;
	}

	public List<PositionWithArticleInfo> getPositionsWithArticleInfo() {
		List<PositionWithArticleInfo> posWithArticleInfos = new ArrayList<PositionWithArticleInfo>();
		int nr = 0;
		for (Position pos : orderPositions.getPositions()) {
			Article article = articleInfoFromPositions.get(pos.getArticleId());
			PositionWithArticleInfo posWithArticleInfo = new PositionWithArticleInfo(nr, pos, article);
			posWithArticleInfos.add(posWithArticleInfo);
		}
		return posWithArticleInfos;
	}
}
