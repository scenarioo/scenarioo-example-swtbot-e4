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

package org.scenarioo.example.e4.orders.createorder;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.orders.OrderDetailView;

public class OrderPage extends WizardPage {

	private final Order order;

	private OrderDetailView orderDetailPanel;

	public OrderPage(final Order order) {
		super("Order Page");
		setTitle("Order Page");
		setDescription("Enter the order details");
		this.order = order;
	}

	@Override
	public void createControl(final Composite parent) {
		this.orderDetailPanel = new OrderDetailView(parent, order);
		this.orderDetailPanel.addOrderNumberKeyListener(
				new KeyAdapter() {

					@Override
					public void keyReleased(final KeyEvent e) {
						if (orderDetailPanel.mandatoryFieldsNonEmpty()) {
							setPageComplete(true);
						} else {
							setPageComplete(false);
						}
					}

				});

		// required to avoid an error in the system
		setControl(orderDetailPanel.getControl());
		setPageComplete(false);
	}

	public Order getOrderForUpdate() {
		return orderDetailPanel.getOrderForUpdate();
	}

}
