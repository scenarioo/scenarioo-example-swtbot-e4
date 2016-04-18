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

import java.util.HashMap;
import java.util.Map;

/**
 * Create one Manager per Scenario. Build State and Use Case state is shared between instances with static properties.
 * 
 * Be careful with parallel Test execution. This class is not thread safe.
 */
public class EntityStateManager {

	private static EntityState actualBuildState = EntityState.NOT_WRITTEN;
	private static Map<UseCaseName, EntityState> savedUseCaseStatuses = new HashMap<UseCaseName, EntityState>();

	private final UseCaseName useCaseName;
	private EntityState scenarioState = EntityState.NOT_WRITTEN;
	
	public EntityStateManager(final UseCaseName useCaseName) {
		this.useCaseName = useCaseName;
	}

	/**
	 * 
	 * @param targetEntityState
	 * @return true if there was a change in the state
	 */
	public boolean changeScenarioStateTo(final EntityState targetScenariooState) {
		if (scenarioState.isTransitionPossibleTo(targetScenariooState)) {
			scenarioState = targetScenariooState;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param targetEntityState
	 * @return true if there was a change in the state
	 */
	public boolean changeBuildStateTo(final EntityState targetBuildState) {
		if (actualBuildState.isTransitionPossibleTo(targetBuildState)) {
			actualBuildState = targetBuildState;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param entityState
	 * @return true if there was a change in the state
	 */
	public boolean changeUseCaseStateTo(final EntityState targetUseCaseState) {

		EntityState actualUseCaseState = savedUseCaseStatuses.get(useCaseName);
		if (actualUseCaseState == null) {
			actualUseCaseState = EntityState.NOT_WRITTEN;
		}

		if (actualUseCaseState.isTransitionPossibleTo(targetUseCaseState)) {
			savedUseCaseStatuses.put(useCaseName, targetUseCaseState);
			return true;
		} else {
			return false;
		}
	}

}
