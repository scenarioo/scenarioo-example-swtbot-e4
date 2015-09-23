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

package org.scenarioo.example.e4;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.scenarioo.api.ScenarioDocuWriter;
import org.scenarioo.model.docu.entities.Build;
import org.scenarioo.model.docu.entities.Page;
import org.scenarioo.model.docu.entities.Scenario;
import org.scenarioo.model.docu.entities.Status;
import org.scenarioo.model.docu.entities.Step;
import org.scenarioo.model.docu.entities.StepDescription;
import org.scenarioo.model.docu.entities.UseCase;

public class ScenariooWriterHelper {

	private final Build build;
	private final Scenario scenario = new Scenario();
	private String useCaseName;
	private final ScenarioDocuWriter docuWriter;

	private int currentIndex = 0;

	/**
	 * @param scenarioobuildinfo2
	 */
	public ScenariooWriterHelper(final Date date) {
		String scenariooBuildInfo = getDateString(date);
		this.docuWriter = new ScenarioDocuWriter(new File("scenarioo-data"),
				"HEAD", scenariooBuildInfo);
		this.build = new Build(scenariooBuildInfo);
		this.build.setName(scenariooBuildInfo);
		this.build.setStatus(Status.SUCCESS);
		this.build.setDate(date);
		this.build.setRevision(scenariooBuildInfo);
		this.docuWriter.saveBuildDescription(build);
	}

	private String getDateString(final Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy-HHmm");
		String dateString = formatter.format(date);
		return dateString;
	}

	public void setUseCaseName(final String useCaseName) {
		this.useCaseName = useCaseName;
	}

	public void saveSuccessfulScenario(final String scenarioName) {
		scenario.setName(scenarioName);
		scenario.setStatus(Status.SUCCESS);
		docuWriter.saveScenario(useCaseName, scenario);
	}

	public void writeStep(final String title, final PageName pageName, final byte[] screenshot) {
		Step step = new Step();

		StepDescription stepDescription = new StepDescription();
		stepDescription.setTitle(title);
		stepDescription.setIndex(currentIndex);
		step.setStepDescription(stepDescription);

		step.setPage(new Page(pageName.name()));

		saveScreenshotAsPng(currentIndex++, screenshot);
		saveStep(step);
	}

	public void saveUseCase() {
		UseCase useCase = new UseCase();
		useCase.setName(useCaseName);
		docuWriter.saveUseCase(useCase);
	}

	private void saveScreenshotAsPng(final int currentIndex, final byte[] screenshot) {
		docuWriter.saveScreenshotAsPng(useCaseName, scenario.getName(), currentIndex,
				screenshot);
	}

	/**
	 * @param step
	 */
	private void saveStep(final Step step) {
		docuWriter.saveStep(useCaseName, scenario.getName(), step);
	}

	/**
	 * @param scenarioobuildinfo
	 */
	public void writeFailedBuildFile() {
		build.setStatus(Status.FAILED);
		this.docuWriter.saveBuildDescription(build);
	}

	/**
	 * 
	 */
	public void flush() {
		this.docuWriter.flush();
	}

	/**
	 * 
	 */
	public void writeScenariooFailedFile() {
		scenario.setStatus(Status.FAILED);
		this.docuWriter.saveScenario(useCaseName, scenario);
	}
}
