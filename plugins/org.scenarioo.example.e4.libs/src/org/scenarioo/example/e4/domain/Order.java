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

package org.scenarioo.example.e4.domain;

import java.util.Date;

public class Order extends AbstractDomainEntity<OrderId> {

	private String orderNumber;

	private OrderState state;

	private Date creationDate;

	private Date deliveryDate;

	private String recipientFullName;

	private String recipientAddress;

	private String recipientZipCode;

	private String recipientCity;

	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber
	 *            the orderNumber to set
	 */
	public void setOrderNumber(final String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @return the state
	 */
	public OrderState getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(final OrderState state) {
		this.state = state;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the deliveryDate
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * @param deliveryDate
	 *            the deliveryDate to set
	 */
	public void setDeliveryDate(final Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/**
	 * @return the name
	 */
	public String getRecipientFullName() {
		return recipientFullName;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setRecipientFullName(final String recipientFullName) {
		this.recipientFullName = recipientFullName;
	}

	/**
	 * @return the address
	 */
	public String getRecipientAddress() {
		return recipientAddress;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setRecipientAddress(final String recipientAddress) {
		this.recipientAddress = recipientAddress;
	}

	/**
	 * @return the zipCode
	 */
	public String getRecipientZipCode() {
		return recipientZipCode;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setRecipientZipCode(final String recipientZipCode) {
		this.recipientZipCode = recipientZipCode;
	}

	/**
	 * @return the city
	 */
	public String getRecipientCity() {
		return recipientCity;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setRecipientCity(final String recipientCity) {
		this.recipientCity = recipientCity;
	}

	/**
	 * @see org.scenarioo.example.e4.domain.AbstractDomainEntity#createInstance(java.lang.Long)
	 */
	@Override
	protected OrderId createInstance(final Long id) {
		return new OrderId(id);
	}
}
