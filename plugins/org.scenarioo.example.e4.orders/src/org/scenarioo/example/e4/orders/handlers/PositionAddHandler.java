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

package org.scenarioo.example.e4.orders.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.dto.PositionWithArticleInfo;
import org.scenarioo.example.e4.dto.PositionWithOrderAndArticleInfoDTO;
import org.scenarioo.example.e4.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PositionAddHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(PositionAddHandler.class);

	private Order activeOrder;

	@Execute
	public void execute(final OrderService orderService, final IEclipseContext context) {
		LOGGER.info(this.getClass().getSimpleName() + " called. Active Order is: " + activeOrder);
		
		PositionWithArticleInfo posWithArticleInfo = new PositionWithArticleInfo(-1);
		PositionWithOrderAndArticleInfoDTO newPosition = new PositionWithOrderAndArticleInfoDTO(posWithArticleInfo,
				activeOrder);
		
		PositionMPartFactory positionMPartFactory = ContextInjectionFactory.make(PositionMPartFactory.class, context);
		positionMPartFactory.showMPartForPosition(newPosition);
	}

	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional final Order activeOrder) {
		this.activeOrder = activeOrder;
		// LOGGER.info("Active Order is: " + activeOrder);
		return activeOrder != null;
	}

}
