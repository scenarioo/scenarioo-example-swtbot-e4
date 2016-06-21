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

package org.scenarioo.example.e4.services;

import java.util.Set;

import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.domain.OrderId;
import org.scenarioo.example.e4.domain.Position;
import org.scenarioo.example.e4.domain.PositionId;
import org.scenarioo.example.e4.dto.CreateOrderDTO;
import org.scenarioo.example.e4.dto.OrderPositionsTableviewDTO;
import org.scenarioo.example.e4.dto.OrderPositionsTreeviewDTO;
import org.scenarioo.example.e4.dto.OrderSearchFilter;
import org.scenarioo.example.e4.dto.PositionWithArticleInfo;

public interface OrderService {

	Order createOrder(CreateOrderDTO createOrderDTO);

	Boolean deleteOrder(OrderId orderId);

	Order saveOrder(Order order);

	Order getOrder(OrderId orderId);

	Boolean deletePosition(OrderId orderId, PositionId posId);

	/**
	 * It's also possible that there was a change to the order Domain object so we transfer the order data as well.
	 * 
	 * @param orderId
	 * @param position
	 * @return PositionWithOrderAndArticleInfoDTO
	 */
	PositionWithArticleInfo createOrUpdatePosition(OrderId orderId, Position position);

	OrderPositionsTableviewDTO getOrderPositionsTableviewDTO(OrderId orderId);

	OrderPositionsTreeviewDTO getOrderPositionsTreeviewDTO(OrderId orderId);

	Set<Order> searchForOrders(OrderSearchFilter orderSearchFilter);
}
