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

package org.scenarioo.example.e4.orders.search;

import java.text.SimpleDateFormat;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.scenarioo.example.e4.domain.Order;
import org.scenarioo.example.e4.orders.OrderPluginImages;

public final class OrderSearchResultTableLabelProvider extends LabelProvider implements ITableLabelProvider {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		TableResult tableResult = (TableResult) element;
		switch (columnIndex) {
		case 5:
			if (tableResult.isImport()) {
				return OrderPluginImages.CHECKBOX_CHECKED.getImage();
			} else {
				return OrderPluginImages.CHECKBOX_UNCHECKED.getImage();
			}
		default:
			return null;
		}
	}

	@Override
	public String getColumnText(final Object element, final int columnIndex) {
		TableResult tableResult = (TableResult) element;
		Order order = tableResult.getOrder();
		switch (columnIndex) {
		case 0:
			return order.getOrderNumber();
		case 1:
			return DATE_FORMAT.format(order.getCreationDate());
		case 2:
			return order.getState().getCaption();
		case 3:
			return DATE_FORMAT.format(order.getDeliveryDate());
		case 4:
			return order.getRecipientFullName();
		case 5:
			return null;
		default:
			return "";
		}
	}
}
