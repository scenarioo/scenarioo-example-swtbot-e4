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

package org.scenarioo.example.e4.services.internal;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public enum ArticleImages {

	AAYLA_SECURA("aayla_secura_jedi_128x128.png"),
	ACKBAR("ackbar_128x128.png"),
	AHSOKA_TANO("ahsoka_tano_128x128.png"),
	ANAKIN_JEDI("anakin_jedi_128x128.png"),
	BOBA_FETT("boba_fett_128x128.png"),
	BOSS_NASS("boss_nass_128x128.png"),
	CHEWBACCA("chewbacca_128x128.png"),
	GENERAL_TARPALS("general_tarpals_128x128.png"),
	GREEDO("greedo_128x128.png"),
	HAN_SOLO("han_solo_128x128.png"),
	IMPERIAL_PROPE_DROID("imperial_probe_droid_128x128.png"),
	JABBA_THE_HUTT("jabba_the_hutt_128x128.png"),
	JAR_JAR_BINKS("jar_jar_binks_128x128.png"),
	JAWAS("jawas_128x128.png"),
	KIT_FISTO("kit_fisto_128x128.png"),
	LUKE_SKYWALKER("luke_skywalker_128x128.png"),
	MASTER_OBI_WAN_KENOBI("master_obi_wan_128x128.png"),
	NUTE_GUNRAY("nute_gunray_128x128.png"),
	PADME_AMIDALA("padme_amidala_128x128.png"),
	PLO_KOON_JEDI("plo_koon_jedi_128x128.png"),
	R2D2("R2D2_128x128.png"),
	SEBULBA("sebulba_128x128.png"),
	SUPER_BATTLE_DROID("super_battle_droid_128x128.png"),
	TUSKEN_RIDERS("tusken_riders_128x128.png"),
	VADER("vader_128x128.png"),
	WATTO("watto_128x128.png"),
	YODA("yoda_128x128.png");

	private ImageDescriptor imageDescriptor;

	private ArticleImages(final String fileName) {
		Bundle bundle = FrameworkUtil.getBundle(ArticleImages.class);
		URL url = FileLocator.find(bundle, new Path("icons/" + fileName), null);
		imageDescriptor = ImageDescriptor.createFromURL(url);
	}

	public ImageDescriptor getImageDescriptor() {
		return imageDescriptor;
	}
}
