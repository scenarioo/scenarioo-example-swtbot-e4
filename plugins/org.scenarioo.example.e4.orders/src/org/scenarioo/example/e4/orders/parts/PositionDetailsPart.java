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

package org.scenarioo.example.e4.orders.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.domain.Position;
import org.scenarioo.example.e4.dto.PositionWithArticleInfo;
import org.scenarioo.example.e4.dto.PositionWithOrderAndArticleInfoDTO;
import org.scenarioo.example.e4.events.OrderServiceEvents;
import org.scenarioo.example.e4.orders.handlers.PositionMPartFactory;
import org.scenarioo.example.e4.orders.positions.PositionDetailView;
import org.scenarioo.example.e4.services.ArticleService;
import org.scenarioo.example.e4.services.OrderService;

public class PositionDetailsPart {

	// Services
	@Inject
	private MDirtyable dirtyable;
	@Inject
	private OrderService orderService;

	private PositionDetailView positionDetailPanel;
	private PositionWithOrderAndArticleInfoDTO positionViewDTO;

	@PostConstruct
	public void createControls(final Composite parent, final ArticleService articleService,
			final ESelectionService selectionService, @Optional PositionWithOrderAndArticleInfoDTO positionViewDTO) {

		if (positionViewDTO == null) {
			positionViewDTO = (PositionWithOrderAndArticleInfoDTO) selectionService.getSelection();
		}

		this.positionViewDTO = positionViewDTO;
		this.positionDetailPanel = new PositionDetailView(parent, articleService);
		this.positionDetailPanel.setOrder(positionViewDTO.getOrder());
		this.positionDetailPanel.setPosition(positionViewDTO.getPosition());
		this.positionDetailPanel.setPositionNumber(positionViewDTO.getPositionNumber());
		this.positionDetailPanel.addChangeListeners(new ArticleModifyListener(), new AmountKeyListner());
	}

	private void updateDirtyFlag() {
		if (positionDetailPanel.hasPositionChanged()) {
			dirtyable.setDirty(true);
		} else {
			dirtyable.setDirty(false);
		}

	}

	@Inject
	@Optional
	public void subscribeToPositionDeleteTopic(
			@UIEventTopic(OrderServiceEvents.TOPIC_POSITION_DETAIL_DELETE) final PositionWithArticleInfo deletedPosition,
			final MPart mpart) {
		updatePositionInView(deletedPosition, mpart);
	}

	@Inject
	@Optional
	public void subscribeToPositionUpdateTopic(
			@UIEventTopic(OrderServiceEvents.TOPIC_POSITION_DETAIL_UPDATE) final PositionWithArticleInfo updatedPosition,
			final MPart mpart) {
		updatePositionInView(updatedPosition, mpart);
	}

	private void updatePositionInView(final PositionWithArticleInfo updatedPosition, final MPart mpart) {
		if (this.positionViewDTO.getPositionId().equals(updatedPosition.getPositionId())) {
			if (!dirtyable.isDirty()) {
				// do not overwrite inputData if it is dirty, user should always throw away changes by himself
				positionDetailPanel.setPosition(updatedPosition.getPosition());
			}
			positionDetailPanel.setPositionNumber(updatedPosition.getPosNr());
			positionViewDTO.setPositionWithArticleInfo(updatedPosition);
			mpart.setLabel(positionViewDTO.getPositionDetailPartLabel());
		}
	}

	@Inject
	@Optional
	public void subscribeToOrderDeletedTopic(
			@UIEventTopic(OrderServiceEvents.TOPIC_ORDER_DELETE) final Order deletedOrder, final MPart mpart) {
		updateOrderInView(deletedOrder, mpart);
	}

	@Inject
	@Optional
	public void subscribeToOrderUpdateTopic(
			@UIEventTopic(OrderServiceEvents.TOPIC_ORDER_UPDATE) final Order updatedOrder, final MPart mpart) {
		updateOrderInView(updatedOrder, mpart);
	}

	/**
	 * @param updatedOrder
	 */
	private void updateOrderInView(final Order updatedOrder, final MPart mpart) {
		if (this.positionViewDTO.getOrderId().equals(updatedOrder.getId())) {
			positionDetailPanel.setOrder(updatedOrder);
			positionViewDTO.setOrder(updatedOrder);
			mpart.setLabel(positionViewDTO.getPositionDetailPartLabel());
		}
	}

	@Persist
	public void save(final MPart mpart) {
		if (positionDetailPanel.validateInputData()) {
			sendUpdate(mpart);
		}
	}

	private void sendUpdate(final MPart mpart) {
		Position editedPosition = positionDetailPanel.getPositionForUpdate();
		PositionWithArticleInfo posWithArticleInfo = orderService.createOrUpdatePosition(
				this.positionViewDTO.getOrderId(), editedPosition);
		dirtyable.setDirty(false);
		PositionMPartFactory.addToCache(editedPosition.getId(), mpart);
		positionViewDTO.setPositionWithArticleInfo(posWithArticleInfo);
		updatePositionInView(posWithArticleInfo, mpart);
	}

	@PreDestroy
	public void cleanUp() {
		PositionMPartFactory.removeFromCache(positionViewDTO.getPositionId());
		dirtyable.setDirty(false); // Otherwise we get the save Dialog for Panels which are not open.
	}

	private class AmountKeyListner extends KeyAdapter {

		/**
		 * @see org.eclipse.swt.events.KeyAdapter#keyReleased(org.eclipse.swt.events.KeyEvent)
		 */
		@Override
		public void keyReleased(final KeyEvent e) {
			updateDirtyFlag();
		}
	}

	private class ArticleModifyListener implements ModifyListener {

		/**
		 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
		 */
		@Override
		public void modifyText(final ModifyEvent e) {
			updateDirtyFlag();
		}
	}
}
