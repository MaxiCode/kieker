package kieker.tpan.plugins.callTree;

import kieker.tpan.datamodel.AssemblyComponentInstance;
import kieker.tpan.datamodel.Operation;

/*
 * ==================LICENCE=========================
 * Copyright 2006-2010 Kieker Project
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
 * ==================================================
 */

/**
 *
 * @author Andre van Hoorn
 */
public class AssemblyComponentOperationPair {
    private final int id;
    private final Operation operation;

    private final AssemblyComponentInstance assemblyComponent;

    private AssemblyComponentOperationPair (){
        this.id = -1;
        this.operation = null;
        this.assemblyComponent = null;
    }

    public AssemblyComponentOperationPair (
            final int id, final Operation operation, final AssemblyComponentInstance AssemblyComponent){
        this.id = id;
        this.operation = operation;
        this.assemblyComponent = AssemblyComponent;
    }

    public final int getId() {
        return this.id;
    }

    public final AssemblyComponentInstance getAssemblyComponent() {
        return this.assemblyComponent;
    }

    public final Operation getOperation() {
        return this.operation;
    }

    @Override
    public String toString() {
        return  +  this.assemblyComponent.getId()+":"
                + this.operation.getId()
                + "@"+this.id + "";
    }
}
