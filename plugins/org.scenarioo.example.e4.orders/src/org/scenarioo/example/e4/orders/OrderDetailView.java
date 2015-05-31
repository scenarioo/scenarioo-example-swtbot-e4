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

package org.scenarioo.example.e4.orders;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.domain.OrderState;

public class OrderDetailView {

	// Data model
	private Order order;

	// UI Widgets
	private final Text orderNumberText;
	private final Text creationDateText;
	private final Combo stateCombo;
	private final DateTime deliveryDateTime;
	private final Text recipientFullNameText;
	private final Text recipientAddressText;
	private final Text recipientZipCodeText;
	private final Text recipientCityText;
	private final Composite container;

	public OrderDetailView(final Composite parent, final Order order) {

		this.order = order;
		this.container = new Composite(parent, SWT.NONE);

		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		// Order Number
		Label orderNumberLabel = new Label(container, SWT.NONE);
		orderNumberLabel.setText("Order Number");
		orderNumberText = new Text(container, SWT.BORDER | SWT.SINGLE);
		orderNumberText.setText(getTextOrEmpty(order.getOrderNumber()));
		orderNumberText.setLayoutData(gd);

		// Creation Date
		Label creationDateLabel = new Label(container, SWT.NONE);
		creationDateLabel.setText("Creation Date");
		creationDateText = new Text(container, SWT.BORDER | SWT.SINGLE);
		creationDateText.setEnabled(false);
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
		creationDateText.setText(DATE_FORMAT.format(order.getCreationDate()));
		creationDateText.setLayoutData(gd);

		// Order State
		Label orderStateLabel = new Label(container, SWT.NONE);
		orderStateLabel.setText("Order State");
		stateCombo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		stateCombo.setEnabled(false);
		stateCombo.setItems(OrderState.getItems());
		stateCombo.select(OrderState.getSelectedIndex(order.getState()));
		stateCombo.setLayoutData(gd);

		// Delivery Date
		Label deliveryDateLabel = new Label(container, SWT.NONE);
		deliveryDateLabel.setText("Delivery Date");
		deliveryDateTime = new DateTime(container, SWT.BORDER | SWT.SINGLE);
		setDeliveryDate(order.getDeliveryDate());
		deliveryDateTime.setLayoutData(gd);

		// Full Name
		Label recipientFullNameLabel = new Label(container, SWT.NONE);
		recipientFullNameLabel.setText("Full Name");
		recipientFullNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		recipientFullNameText.setText(getTextOrEmpty(order.getRecipientFullName()));
		recipientFullNameText.setLayoutData(gd);

		// Address
		Label recipientAddressLabel = new Label(container, SWT.NONE);
		recipientAddressLabel.setText("Address");
		recipientAddressText = new Text(container, SWT.BORDER | SWT.SINGLE);
		recipientAddressText.setText(getTextOrEmpty(order.getRecipientAddress()));
		recipientAddressText.setLayoutData(gd);

		// ZIP Code
		Label recipientZipCodeLabel = new Label(container, SWT.NONE);
		recipientZipCodeLabel.setText("ZIP");
		recipientZipCodeText = new Text(container, SWT.BORDER | SWT.SINGLE);
		recipientZipCodeText.setText(getTextOrEmpty(order.getRecipientZipCode()));
		recipientZipCodeText.setLayoutData(gd);

		// City
		Label recipientCityLabel = new Label(container, SWT.NONE);
		recipientCityLabel.setText("City");
		recipientCityText = new Text(container, SWT.BORDER | SWT.SINGLE);
		recipientCityText.setText(getTextOrEmpty(order.getRecipientCity()));
		recipientCityText.setLayoutData(gd);

	}

	public void addOrderNumberKeyListener(final KeyListener keyListener) {
		orderNumberText.addKeyListener(keyListener);
	}

	public boolean mandatoryFieldsNonEmpty() {
		return !orderNumberText.getText().isEmpty();
	}

	public Composite getControl() {
		return container;
	}

	public Order getOrderForUpdate() {
		Order order = new Order(this.order);
		order.setOrderNumber(orderNumberText.getText());
		order.setDeliveryDate(getDeliveryDate());
		order.setRecipientFullName(recipientFullNameText.getText());
		order.setRecipientAddress(recipientFullNameText.getText());
		order.setRecipientZipCode(recipientZipCodeText.getText());
		order.setRecipientCity(recipientCityText.getText());
		return order;
	}

	private String getTextOrEmpty(final String text) {
		return text != null ? text : "";
	}

	private void setDeliveryDate(final Date date) {
		if (date != null) {
			Calendar instance = Calendar.getInstance();
			instance.setTime(date);
			deliveryDateTime.setDate(instance.get(Calendar.YEAR), instance.get(Calendar.MONTH),
					instance.get(Calendar.DAY_OF_MONTH));
		}
	}

	private Date getDeliveryDate() {
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.DAY_OF_MONTH, deliveryDateTime.getDay());
		instance.set(Calendar.MONTH, deliveryDateTime.getMonth());
		instance.set(Calendar.YEAR, deliveryDateTime.getYear());
		return instance.getTime();
	}

	/**
	 * @param order
	 */
	public void setOrder(final Order order) {
		this.order = order;
	}

	/**
	 * @param integer
	 */
	public void setPositionCount(final Integer integer) {
		// TODO positionCount not yet implemented
	}
}
