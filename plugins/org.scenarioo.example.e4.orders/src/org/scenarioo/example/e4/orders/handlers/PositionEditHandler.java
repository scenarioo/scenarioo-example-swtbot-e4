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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.scenarioo.example.e4.domain.PositionId;
import org.scenarioo.example.e4.dto.PositionWithOrderAndArticleInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PositionEditHandler {

	private static final String MODEL_PARTSTACKS_EDITOR_ID = "org.scenarioo.example.e4.orders.partstack.editor";
	private static final String MODEL_PERSPECTIVE_ORDERS_ID = "org.scenarioo.example.e4.orders.perspective.orders";
	private static final String ORDER_ICON_URI = "platform:/plugin/org.scenarioo.example.e4.orders/icons/document.png";
	private static final String PART_CLASS_URI = "bundleclass://org.scenarioo.example.e4.orders/org.scenarioo.example.e4.orders.parts.PositionDetailsPart";

	private static final Logger LOGGER = LoggerFactory.getLogger(PositionEditHandler.class);

	@Inject
	EModelService modelService;

	private PositionWithOrderAndArticleInfoDTO activePositionViewDTO;

	private final Map<PositionId, MPart> positionIdToMPartMap = new HashMap<PositionId, MPart>();

	@CanExecute
	public synchronized boolean canExecute(
			@Named(IServiceConstants.ACTIVE_SELECTION) @Optional final PositionWithOrderAndArticleInfoDTO positionViewDTO) {
		this.activePositionViewDTO = positionViewDTO;
		return activePositionViewDTO != null;
	}

	@Execute
	public synchronized void execute(final MApplication application, final EPartService partService) {

		PositionWithOrderAndArticleInfoDTO positionViewDTO = activePositionViewDTO;
		LOGGER.info(this.getClass().getSimpleName() + " called. Active Position is: "
				+ positionViewDTO.getPosition());

		// if already exist we show the part
		if (!positionIdToMPartMap.containsKey(positionViewDTO.getPositionId())) {
			MPartStack partstack = filterOutOrdersPartStack(application);
			MPart newPart = createAndMapNewPart(positionViewDTO);
			partstack.getChildren().add(newPart);
		}
		MPart part = positionIdToMPartMap.get(positionViewDTO.getPositionId());
		partService.showPart(part, PartState.ACTIVATE);
	}

	private MPartStack filterOutOrdersPartStack(final MApplication application) {

		List<MPartStack> partstacks = modelService.findElements(application,
				MODEL_PARTSTACKS_EDITOR_ID, MPartStack.class, null);

		for (MPartStack partstack : partstacks) {
			MPerspective perspective = modelService.getPerspectiveFor(partstack);
			if (MODEL_PERSPECTIVE_ORDERS_ID.equals(perspective.getElementId())) {
				return partstack;
			}
		}
		throw new IllegalStateException("no partstack of " + partstacks
				+ " does belong to a perspecktive(id=" + MODEL_PERSPECTIVE_ORDERS_ID + ")");
	}

	private MPart createAndMapNewPart(final PositionWithOrderAndArticleInfoDTO positionViewDTO) {
		MPart newPart = modelService.createModelElement(MPart.class);
		newPart.setLabel(positionViewDTO.getPositionDetailPartLabel());
		newPart.setContributionURI(PART_CLASS_URI);
		newPart.setIconURI(ORDER_ICON_URI);
		newPart.setCloseable(true);
		positionIdToMPartMap.put(positionViewDTO.getPositionId(), newPart);
		return newPart;
	}
}
