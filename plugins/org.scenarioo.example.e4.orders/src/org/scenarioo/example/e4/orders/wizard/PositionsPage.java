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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.scenarioo.example.e4.domain.Position;

public class PositionsPage extends WizardPage {

	private final List<Position> positions = new ArrayList<Position>();

	private Text text1;
	private Composite container;

	public PositionsPage() {
		super("Positions Page");
		setTitle("Positions Page");
		setDescription("Enter the order positions");
		setControl(text1);
	}

	@Override
	public void createControl(final Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		Label label1 = new Label(container, SWT.NONE);
		label1.setText("Say hello to Fred");

		text1 = new Text(container, SWT.BORDER | SWT.SINGLE);
		text1.setText("");
		text1.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(final KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(final KeyEvent e) {
				if (!text1.getText().isEmpty()) {
					setPageComplete(true);
				}
			}

		});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		text1.setLayoutData(gd);
		Label labelCheck = new Label(container, SWT.NONE);
		labelCheck.setText("This is a check");
		Button check = new Button(container, SWT.CHECK);
		check.setSelection(true);
		// required to avoid an error in the system
		setControl(container);
		setPageComplete(false);
	}

	public String getText1() {
		return text1.getText();
	}

	public List<Position> getPositions(){
		return positions;
	}
}
