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
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.domain.OrderId;
import org.scenarioo.example.e4.domain.Position;
import org.scenarioo.example.e4.domain.PositionId;

public class PositionWithOrderAndArticleInfoDTO {

	private PositionWithArticleInfo posWithArticleInfo;
	private Order order;

	public PositionWithOrderAndArticleInfoDTO(final PositionWithArticleInfo posWithArticleInfo, final Order order) {
		this.posWithArticleInfo = posWithArticleInfo;
		this.order = order;
	}

	/**
	 * 
	 * @param position
	 */
	public void setPositionWithArticleInfo(final PositionWithArticleInfo posWithArticleInfo) {
		this.posWithArticleInfo = posWithArticleInfo;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(final Order order) {
		this.order = order;
	}

	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	public Article getArticle() {
		return posWithArticleInfo.getArticle();
	}

	public Integer getPositionNumber() {
		return posWithArticleInfo.getPosNr();
	}

	public Position getPosition() {
		return posWithArticleInfo.getPosition();
	}

	public PositionId getPositionId() {
		return posWithArticleInfo.getPosition().getId();
	}

	public OrderId getOrderId() {
		return order.getId();
	}

	public String getPositionDetailPartLabel() {
		String articleNumber;
		if (posWithArticleInfo.getArticle() != null) {
			articleNumber = posWithArticleInfo.getArticle().getArticleNumber();
		} else {
			articleNumber = "choose Article";
		}
		return order.getOrderNumber() + " - " + articleNumber + " - "
				+ posWithArticleInfo.getPosition().getState().getCaption();
	}

	public PositionWithArticleInfo getPositionWithArticleInfo() {
		return posWithArticleInfo;
	}
}
