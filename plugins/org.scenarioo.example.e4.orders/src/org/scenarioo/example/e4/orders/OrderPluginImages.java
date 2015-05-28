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

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public enum OrderPluginImages {

	ORDER("folder.png"),
	ORDER_NEW("folder_add.png"),
	ORDER_NOT_FOUND("folder_silver.png"),
	ORDER_SEARCH("folder_explorer.png"),
	ORDER_LOADED("folder_open.png"),
	ORDER_CLOSE("folder_close.png"),
	ORDER_POSITION("document.png"),
	ORDER_POSITION_ADD("document_add.png"),
	ORDER_POSITION_COPY("document_copy.png"),
	ORDER_POSITION_REMOVE("document_remove.png"),
	CHECKBOX_CHECKED("checkbox_checked.gif"),
	CHECKBOX_UNCHECKED("checkbox_unchecked.gif"),
	ADD_BUTTON("button_add.png"),
	DELETE_BUTTON("button_delete.png"),
	SEARCH_24("search_24.png"),
	ADD_BUTTON_24("button_add_24.png"),
	DELETE_BUTTON_24("button_delete_24.png");

	private ImageDescriptor imageDescriptor;

	private OrderPluginImages(final String fileName) {
		Bundle bundle = FrameworkUtil.getBundle(OrderPluginImages.class);
		URL url = FileLocator.find(bundle, new Path("icons/" + fileName), null);
		imageDescriptor = ImageDescriptor.createFromURL(url);
	}

	public Image getImage() {
		return imageDescriptor.createImage();
	}
}
