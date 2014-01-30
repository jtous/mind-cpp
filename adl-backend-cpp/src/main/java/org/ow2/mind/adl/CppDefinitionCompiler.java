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

import static org.ow2.mind.PathHelper.fullyQualifiedNameToPath;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;

import org.objectweb.fractal.adl.ADLException;
import org.objectweb.fractal.adl.CompilerError;
import org.objectweb.fractal.adl.Definition;
import org.objectweb.fractal.adl.error.GenericErrors;
import org.ow2.mind.SourceFileWriter;
import org.ow2.mind.adl.CompilationDecorationHelper.AdditionalCompilationUnitDecoration;
import org.ow2.mind.adl.ast.ImplementationContainer;
import org.ow2.mind.adl.ast.Source;
import org.ow2.mind.compilation.CompilationCommand;
import org.ow2.mind.compilation.CompilerCommand;
import org.ow2.mind.io.IOErrors;
import org.ow2.mind.st.BackendFormatRenderer;

public class CppDefinitionCompiler extends BasicDefinitionCompiler {

  // ---------------------------------------------------------------------------
  // Implementation of the Visitor interface
  // ---------------------------------------------------------------------------

  /*
   * For Visitor interface related fields and their details
   * (dependency-injection etc),
   * @see org.ow2.mind.adl.BasicDefinitionCompiler
   */

  // ---------------------------------------------------------------------------
  // Helper methods
  // ---------------------------------------------------------------------------

  /**
   * @see BasicDefinitionCompiler#visitImplementation(Definition,
   *      ImplementationContainer, Collection, Map)
   */
  @Override
  protected void visitImplementation(final Definition definition,
      final ImplementationContainer container,
      final Collection<CompilationCommand> compilationTasks,
      final Map<Object, Object> context) throws ADLException {

    // We deal with the user .cpp sources (adl source keyword list)
    final Source[] sources = container.getSources();
    for (int i = 0; i < sources.length; i++) {

      final Source src = sources[i];
      final String implSuffix = "_impl" + i;

      final File objectFile = outputFileLocatorItf.getCCompiledOutputFile(
          fullyQualifiedNameToPath(definition.getName(), implSuffix, ".o"),
          context);

      // handle source differently whether it's ADL inline or a normal reference
      final File srcFile;
      String inlinedCCode = src.getCCode();
      if (inlinedCCode != null) {
        // Implementation code is inlined in the ADL. Dump it in a file.
        srcFile = outputFileLocatorItf.getCSourceOutputFile(
            fullyQualifiedNameToPath(definition.getName(), implSuffix, ".cpp"),
            context);
        inlinedCCode = BackendFormatRenderer.sourceToLine(src) + "\n"
            + inlinedCCode + "\n";
        try {
          SourceFileWriter.writeToFile(srcFile, inlinedCCode);
        } catch (final IOException e) {
          throw new CompilerError(IOErrors.WRITE_ERROR, e,
              srcFile.getAbsolutePath());
        }
      } else {
        // usual user .cpp file
        assert src.getPath() != null;
        final URL srcURL = implementationLocatorItf.findSource(src.getPath(),
            context);
        try {
          srcFile = new File(srcURL.toURI());
        } catch (final URISyntaxException e) {
          throw new CompilerError(GenericErrors.INTERNAL_ERROR, e);
        }
      }

      // Compilation commands
      final CompilerCommand gccCommand = compilationCommandFactory
          .newCompilerCommand(definition, src, srcFile, true, null, null,
              objectFile, context);

      gccCommand.setAllDependenciesManaged(true);

      compilationTasks.add(gccCommand);
    }

  }

  /**
   * see
   * {@link BasicDefinitionCompiler#visitAdditionalCompilationUnits(Definition, Collection, Collection, Map)}
   */
  @Override
  protected void visitAdditionalCompilationUnits(
      final Definition definition,
      final Collection<AdditionalCompilationUnitDecoration> additionalCompilationUnits,
      final Collection<CompilationCommand> compilationTasks,
      final Map<Object, Object> context) throws ADLException {

    // TODO: We do not use any membrane yet in Mind C++

  }
}
