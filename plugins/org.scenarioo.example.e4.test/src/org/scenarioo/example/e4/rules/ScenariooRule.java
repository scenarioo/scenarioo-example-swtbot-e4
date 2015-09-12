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

package org.scenarioo.example.e4.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.scenarioo.api.ScenarioDocuWriter;
import org.scenarioo.model.docu.entities.Scenario;
import org.scenarioo.model.docu.entities.UseCase;

public class ScenariooRule implements TestRule {

	 private final String useCaseName;
	 private final ScenarioDocuWriter writer;
	
	 public ScenariooRule( final String useCaseName,final ScenarioDocuWriter writer) { // (1)
		 this.useCaseName = useCaseName;
		 this.writer = writer;
	 }
	
	 @Override
	 public Statement apply(final Statement base, final Description description) {
	 return new Statement() {
	
	 @Override
	 public void evaluate() throws Throwable {
	 saveUseCase(description); // (2)
	 saveScenario(description);
	 base.evaluate();
	 }
	
	 private void saveScenario(final Description description) {
	 Scenario scenario = new Scenario();
	 scenario.setName(description.getMethodName()); // (4)
	 writer.saveScenario(getUseCaseName(description), scenario);
	 }
	
	
	 private void saveUseCase(final Description description) {
	 UseCase useCase = new UseCase();
	 useCase.setName(getUseCaseName(description));
	 writer.saveUseCase(useCase);
	 }
	
	 private String getUseCaseName(final Description description) {
	 return description.getTestClass().getSimpleName(); // (3)
	 }
	 };
	 }

}
