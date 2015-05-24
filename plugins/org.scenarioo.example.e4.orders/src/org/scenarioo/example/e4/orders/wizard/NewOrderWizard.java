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

package org.scenarioo.example.e4.orders.wizard;

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.scenarioo.example.e4.dto.CreateOrderDTO;
import org.scenarioo.example.e4.dto.OrderPositionsForViewDTO;
import org.scenarioo.example.e4.services.ArticleService;
import org.scenarioo.example.e4.services.OrderService;

public class NewOrderWizard extends Wizard implements IPageChangedListener {

	private final ArticleService articleService;
	private final OrderService orderService;
	protected OrderPage one;
	protected PositionsPage two;

	public NewOrderWizard(final OrderService orderService, final ArticleService articleService) {
		super();
		this.orderService = orderService;
		this.articleService = articleService;
		setNeedsProgressMonitor(true);
	}

	@Override
	public String getWindowTitle() {
		return "Create New Order";
	}

	@Override
	public void addPages() {
		OrderPositionsForViewDTO orderPositionsForViewDTO = new OrderPositionsForViewDTO();
		one = new OrderPage(orderPositionsForViewDTO.getOrder());
		two = new PositionsPage(articleService, orderPositionsForViewDTO);
		addPage(one);
		addPage(two);

		WizardDialog dialog = (WizardDialog) getContainer();
		dialog.addPageChangedListener(this);
	}

	@Override
	public boolean performFinish() {
		CreateOrderDTO orderWithPos = new CreateOrderDTO(one.getOrderForUpdate(),
				two.getOrderPositionsForUpdate());
		orderService.createOrder(orderWithPos);
		return true;
	}

	/**
	 * @see org.eclipse.jface.dialogs.IPageChangedListener#pageChanged(org.eclipse.jface.dialogs.PageChangedEvent)
	 */
	@Override
	public void pageChanged(final PageChangedEvent event) {
		if (two.equals(event.getSelectedPage())) {
			two.updateOrderInfo(one.getOrderForUpdate());
		}
	}

}
