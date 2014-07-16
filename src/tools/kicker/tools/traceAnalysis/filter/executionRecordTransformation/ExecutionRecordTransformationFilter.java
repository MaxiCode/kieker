/***************************************************************************
 * Copyright 2014 Kicker Project (http://kicker-monitoring.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/

package kicker.tools.traceAnalysis.filter.executionRecordTransformation;

import kicker.analysis.IProjectContext;
import kicker.analysis.plugin.annotation.InputPort;
import kicker.analysis.plugin.annotation.OutputPort;
import kicker.analysis.plugin.annotation.Plugin;
import kicker.analysis.plugin.annotation.RepositoryPort;
import kicker.common.configuration.Configuration;
import kicker.common.record.controlflow.OperationExecutionRecord;
import kicker.common.util.signature.ClassOperationSignaturePair;
import kicker.tools.traceAnalysis.filter.AbstractTraceAnalysisFilter;
import kicker.tools.traceAnalysis.systemModel.Execution;
import kicker.tools.traceAnalysis.systemModel.repository.SystemModelRepository;

/**
 * Transforms {@link OperationExecutionRecord}s into {@link Execution} objects.<br>
 * 
 * This class has exactly one input port and one output port. It receives objects inheriting from {@link OperationExecutionRecord}. The received object is
 * transformed into an instance of {@link Execution}.
 * 
 * @author Andre van Hoorn
 * 
 * @since 1.1
 */
@Plugin(description = "A filter transforming OperationExecutionRecords into Execution objects",
		outputPorts = {
			@OutputPort(name = ExecutionRecordTransformationFilter.OUTPUT_PORT_NAME_EXECUTIONS, description = "Provides transformed executions",
					eventTypes = { Execution.class })
		},
		repositoryPorts = {
			@RepositoryPort(name = AbstractTraceAnalysisFilter.REPOSITORY_PORT_NAME_SYSTEM_MODEL, repositoryType = SystemModelRepository.class)
		})
public class ExecutionRecordTransformationFilter extends AbstractTraceAnalysisFilter {

	/** This is the name of the input port receiving new operation execution records. */
	public static final String INPUT_PORT_NAME_RECORDS = "operationExecutionRecords";

	/** This is the name of the output port delivering the transformed executions. */
	public static final String OUTPUT_PORT_NAME_EXECUTIONS = "transformedExecutions";

	/**
	 * Creates a new instance of this class using the given parameters.
	 * 
	 * @param configuration
	 *            The configuration for this component.
	 * @param projectContext
	 *            The project context for this component.
	 */
	public ExecutionRecordTransformationFilter(final Configuration configuration, final IProjectContext projectContext) {
		super(configuration, projectContext);
	}

	/**
	 * This method represents the input port, processing incoming operation execution records.
	 * 
	 * @param execRec
	 *            The next operation execution record.
	 */
	@InputPort(
			name = INPUT_PORT_NAME_RECORDS,
			description = "Receives operation execution records to be transformed",
			eventTypes = { OperationExecutionRecord.class })
	public void inputOperationExecutionRecords(final OperationExecutionRecord execRec) {
		final ClassOperationSignaturePair fqComponentNameSignaturePair = ClassOperationSignaturePair.splitOperationSignatureStr(execRec.getOperationSignature());

		final Execution execution = this.createExecutionByEntityNames(execRec.getHostname(), fqComponentNameSignaturePair.getFqClassname(),
				fqComponentNameSignaturePair.getSignature(),
				execRec.getTraceId(), execRec.getSessionId(), execRec.getEoi(), execRec.getEss(), execRec.getTin(), execRec.getTout(), false);
		super.deliver(OUTPUT_PORT_NAME_EXECUTIONS, execution);
	}

}