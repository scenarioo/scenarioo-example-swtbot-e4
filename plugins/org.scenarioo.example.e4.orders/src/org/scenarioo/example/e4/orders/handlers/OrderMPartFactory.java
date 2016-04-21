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

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.domain.OrderId;

public class OrderMPartFactory {

	private static final String MODEL_PARTSTACKS_EDITOR_ID = "org.scenarioo.example.e4.orders.partstack.editor";
	private static final String MODEL_PERSPECTIVE_ORDERS_ID = "org.scenarioo.example.e4.orders.perspective.orders";
	private static final String ORDER_ICON_URI = "platform:/plugin/org.scenarioo.example.e4.orders/icons/folder.png";
	private static final String PART_CLASS_URI = "bundleclass://org.scenarioo.example.e4.orders/org.scenarioo.example.e4.orders.parts.OrderDetailsPart";

	private static final Map<OrderId, MPart> orderIdToMPartMap = new HashMap<OrderId, MPart>();

	@Inject
	private MApplication application;
	@Inject
	private EModelService modelService;
	@Inject
	private EPartService partService;

	public void showMPartForOrder(final Order order) {

		// if already exist we show the part
		if (!orderIdToMPartMap.containsKey(order.getId())) {
			MPartStack partstack = filterOutOrdersPartStack();
			MPart newPart = createAndMapNewPart(order);
			partstack.getChildren().add(newPart);
		}
		MPart part = orderIdToMPartMap.get(order.getId());
		partService.showPart(part, PartState.ACTIVATE);
	}

	private MPartStack filterOutOrdersPartStack() {

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

	private MPart createAndMapNewPart(final Order order) {
		MPart newPart = modelService.createModelElement(MPart.class);
		newPart.setLabel(order.getOrderNumber() + " - " + order.getState().getCaption());
		newPart.setContributionURI(PART_CLASS_URI);
		newPart.setIconURI(ORDER_ICON_URI);
		newPart.setCloseable(true);
		orderIdToMPartMap.put(order.getId(), newPart);
		return newPart;
	}

	public static void removeFromCache(final OrderId orderId) {
		orderIdToMPartMap.remove(orderId);
	}
}
