/**
 * Copyright (C) 2014 Schneider-Electric
 *
 * This file is part of "Mind Compiler" is free software: you can redistribute 
 * it and/or modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact: mind@ow2.org
 *
 * Authors: Stephane Seyvoz (Assystem)
 * Contributors: 
 */

package org.ow2.mind.adl;

import static org.ow2.mind.PathHelper.replaceExtension;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.objectweb.fractal.adl.ADLException;
import org.objectweb.fractal.adl.CompilerError;
import org.objectweb.fractal.adl.error.GenericErrors;
import org.ow2.mind.compilation.CompilationCommand;
import org.ow2.mind.compilation.CompilerCommand;

public class CppInstanceCompiler extends BasicInstanceCompiler {

  // ---------------------------------------------------------------------------
  // Implementation of the Visitor interface
  // ---------------------------------------------------------------------------

  /**
   * @see BasicInstanceCompiler#visit(InstancesDescriptor, Map)
   */
  @Override
  public Collection<CompilationCommand> visit(
      final InstancesDescriptor instanceDesc, final Map<Object, Object> context)
      throws ADLException {

    instanceSourceGeneratorItf.visit(instanceDesc, context);
    final String instancesFileName = CppInstanceSourceGenerator
        .getInstancesFileName(instanceDesc);

    // localize file _instances.cpp
    final File srcFile = outputFileLocatorItf.getCSourceOutputFile(
        instancesFileName, context);
    if (!srcFile.exists()) {
      throw new CompilerError(GenericErrors.INTERNAL_ERROR,
          "Can't find source file \"" + instancesFileName + "\"");
    }

    // Removed ImplementationHeaderSourceGenerator handling
    // TODO: check if it's necessary to reintroduce it ?

    final File objectFile = outputFileLocatorItf.getCCompiledOutputFile(
        replaceExtension(instancesFileName, ".o"), context);

    final CompilerCommand gccCommand = compilationCommandFactory
        .newCompilerCommand(instanceDesc.instanceDefinition, null, srcFile,
            true, null, null, objectFile, context);

    gccCommand.setAllDependenciesManaged(true);

    final List<CompilationCommand> compilationTasks = new ArrayList<CompilationCommand>();
    compilationTasks.add(gccCommand);

    return compilationTasks;
  }

}
