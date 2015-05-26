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

package org.scenarioo.example.e4.orders.positions.edit;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Label;
import org.scenarioo.example.e4.dto.PositionWithArticleInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmountEditingSupport extends EditingSupport {

	private static Logger LOGGER = LoggerFactory.getLogger(AmountEditingSupport.class);

	private final TableViewer tableViewer;
	private final CellEditor editor;

	public AmountEditingSupport(final TableViewer tableViewer, final Label errorMsgLabel) {
		super(tableViewer);
		this.tableViewer = tableViewer;
		this.editor = new TextCellEditor(tableViewer.getTable());
		editor.setValidator(new ICellEditorValidator() {
			@Override
			public String isValid(final Object value) {
				try {
					Long longValue = Long.parseLong((String) value);
					if (longValue < 1) {
						return "Amount must be higher 0";
					}
					return null;
				} catch (NumberFormatException e) {
					return "Amount must be Long";
				}
			}
		});
		editor.addListener(new ICellEditorListener() {

			@Override
			public void editorValueChanged(final boolean oldValidState, final boolean newValidState) {
				String errorMsg = editor.getErrorMessage();
				errorMsgLabel.setText((errorMsg == null) ? "" : errorMsg);
			}

			@Override
			public void cancelEditor() {
				errorMsgLabel.setText("");
			}

			@Override
			public void applyEditorValue() {
				errorMsgLabel.setText("");
			}
		});
	}

	@Override
	protected CellEditor getCellEditor(final Object element) {
		return editor;
	}

	@Override
	protected boolean canEdit(final Object element) {
		return true;
	}

	@Override
	protected Object getValue(final Object element) {
		return ((PositionWithArticleInfo) element).getPositon().getAmount().toString();
	}

	@Override
	protected void setValue(final Object element, final Object userInputValue) {
		PositionWithArticleInfo posWithArticleInfo = (PositionWithArticleInfo) element;
		Long oldAmount = posWithArticleInfo.getPositon().getAmount();
		((PositionWithArticleInfo) element).getPositon().setAmount(Long.valueOf(userInputValue.toString()));
		tableViewer.update(element, null);
		LOGGER.info("New Amount=" + userInputValue + " (oldAmount=" + oldAmount
				+ ") has been set for OrderPosition:" + posWithArticleInfo.getPositon());
	}
}