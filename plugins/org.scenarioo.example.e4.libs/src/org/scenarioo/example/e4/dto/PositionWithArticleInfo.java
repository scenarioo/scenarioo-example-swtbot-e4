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

import org.scenarioo.example.e4.domain.Article;
import org.scenarioo.example.e4.domain.Position;
import org.scenarioo.example.e4.domain.PositionId;

public class PositionWithArticleInfo {

	private Integer posNr; // starts with zero
	private Position position;
	private Article article; // Immutable in Context of OrderPosition

	public PositionWithArticleInfo(final Integer posNr, final Position position, final Article article) {
		this.posNr = posNr;
		this.position = new Position(position);
		this.article = article;
	}

	public PositionWithArticleInfo(final Integer posNr) {
		this.posNr = posNr;
		this.position = new Position();
	}

	/**
	 * @return the article
	 */
	public Article getArticle() {
		return article;
	}

	public void setArticle(final Article article) {
		this.article = article;
		this.position.setArticleId(article.getId());
	}

	public void setPosNr(final Integer posNr) {
		this.posNr = posNr;
	}

	/**
	 * @return the posNr
	 */
	public Integer getPosNr() {
		return posNr;
	}

	public PositionId getPositionId() {
		return position.getId();
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

}
