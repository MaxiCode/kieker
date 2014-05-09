/***************************************************************************
 * Copyright 2014 Kieker Project (http://kieker-monitoring.net)
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

package kieker.panalysis.examples.countWords;

import java.io.File;

import de.chw.util.Pair;

import kieker.panalysis.framework.core.Analysis;
import kieker.panalysis.framework.core.IPipeline;
import kieker.panalysis.framework.sequential.MethodCallPipe;
import kieker.panalysis.stage.basic.RepeaterSource;
import kieker.panalysis.stage.basic.distributor.Distributor;
import kieker.panalysis.stage.basic.merger.Merger;

/**
 * @author Christian Wulf
 * 
 * @since 1.10
 */
public class CountWordsAnalysis extends Analysis {

	private IPipeline pipeline;

	private RepeaterSource<String> repeaterSource;
	private DirectoryName2Files findFilesStage;
	private Distributor<File> distributor;
	private CountWordsStage countWordsStage0;
	private CountWordsStage countWordsStage1;
	private Merger<Pair<File, Integer>> merger;
	private OutputWordsCountSink outputWordsCountStage;

	@Override
	public void init() {
		super.init();

		this.repeaterSource = RepeaterSource.create(".", 4000);
		this.findFilesStage = new DirectoryName2Files();
		this.distributor = new Distributor<File>();
		this.countWordsStage0 = new CountWordsStage();
		this.countWordsStage1 = new CountWordsStage();
		this.merger = new Merger<Pair<File, Integer>>();
		this.outputWordsCountStage = new OutputWordsCountSink();

		this.pipeline = Pipeline.create();
		this.pipeline.addStage(this.repeaterSource);
		this.pipeline.addStage(this.findFilesStage);
		this.pipeline.addStage(this.distributor);
		this.pipeline.addStage(this.countWordsStage0);
		this.pipeline.addStage(this.countWordsStage1);
		this.pipeline.addStage(this.merger);
		this.pipeline.addStage(this.outputWordsCountStage);

		this.pipeline.setStartStages(this.repeaterSource);
		this.repeaterSource.START.setAssociatedPipe(new MethodCallPipe<Boolean>(Boolean.TRUE));

		this.pipeline.connect(this.repeaterSource.OUTPUT, this.findFilesStage.DIRECTORY_NAME);
		this.pipeline.connect(this.findFilesStage.FILE, this.distributor.OBJECT);
		this.pipeline.connect(this.distributor.getNewOutputPort(), this.countWordsStage0.FILE);
		this.pipeline.connect(this.distributor.getNewOutputPort(), this.countWordsStage1.FILE);
		this.pipeline.connect(this.countWordsStage0.WORDSCOUNT, this.merger.getNewInputPort());
		this.pipeline.connect(this.countWordsStage1.WORDSCOUNT, this.merger.getNewInputPort());
		this.pipeline.connect(this.merger.OBJECT, this.outputWordsCountStage.FILE_WORDCOUNT_TUPLE);

		// pipeline.init();
	}

	@Override
	public void start() {
		super.start();
		this.pipeline.fireStartNotification();
		this.pipeline.getStartStages()[0].execute();
	}

	public static void main(final String[] args) {
		final CountWordsAnalysis analysis = new CountWordsAnalysis();
		analysis.init();
		final long start = System.currentTimeMillis();
		analysis.start();
		final long end = System.currentTimeMillis();
		// analysis.terminate();
		final long duration = end - start;
		System.out.println("duration: " + duration + " ms"); // NOPMD (Just for example purposes)

		System.out.println("repeaterSource: " + (analysis.repeaterSource.getOverallDuration() - // NOPMD (Just for example purposes)
				analysis.findFilesStage.getOverallDuration()) + " ms");
		System.out.println("findFilesStage: " + (analysis.findFilesStage.getOverallDuration() - // NOPMD (Just for example purposes)
				analysis.countWordsStage0.getOverallDuration()) + " ms");
		System.out.println("countWordsStage0: " + (analysis.countWordsStage0.getOverallDuration() - // NOPMD (Just for example purposes)
				analysis.outputWordsCountStage.getOverallDuration()) + " ms");
		System.out.println("countWordsStage1: " + (analysis.countWordsStage1.getOverallDuration() - // NOPMD (Just for example purposes)
				analysis.outputWordsCountStage.getOverallDuration()) + " ms");
		System.out.println("outputWordsCountStage: " + analysis.outputWordsCountStage.getOverallDuration() + " ms"); // NOPMD (Just for example purposes)

		System.out.println("findFilesStage: " + analysis.findFilesStage.getNumFiles()); // NOPMD (Just for example purposes)
		System.out.println("outputWordsCountStage: " + analysis.outputWordsCountStage.getNumFiles()); // NOPMD (Just for example purposes)
	}
}