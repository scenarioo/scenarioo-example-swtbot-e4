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

package org.scenarioo.example.e4.orders.positions;

import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.scenarioo.example.e4.orders.positions.edit.AmountEditingSupport;
import org.scenarioo.example.e4.orders.positions.edit.ArticleEditingSupport;
import org.scenarioo.example.e4.services.ArticleService;

public class PositionsTableHelper {

	private static final String[] TITLES = { "Nr.", "State", "Article Number", "Description", "Amount", "Unit" };
	private static final int[] BOUNDS = { 100, 100, 100, 100, 100, 100 };

	private PositionsTableHelper() {

	}

	public static void initializeColumns(final TableViewer tableViewer, final ArticleService articleService,
			final Label posAmountErrorMsg) {

		EditingSupport[] editing_support = { null, null, new ArticleEditingSupport(tableViewer, articleService), null,
				new AmountEditingSupport(tableViewer, posAmountErrorMsg), null };

		for (int i = 0; i < TITLES.length; i++) {
			TableViewerColumn col = createTableViewerColumn(tableViewer, i);
			EditingSupport editingSupport = editing_support[i];
			if (editingSupport != null) {
				col.setEditingSupport(editingSupport);
			}
		}
	}

	private static TableViewerColumn createTableViewerColumn(final TableViewer tableViewer, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(TITLES[colNumber]);
		column.setWidth(BOUNDS[colNumber]);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

}
