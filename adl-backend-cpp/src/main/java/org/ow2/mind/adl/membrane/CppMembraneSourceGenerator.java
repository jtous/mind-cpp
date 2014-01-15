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

package org.ow2.mind.adl.membrane;

import static org.ow2.mind.adl.CompilationDecorationHelper.addAdditionalCompilationUnit;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.objectweb.fractal.adl.ADLException;
import org.objectweb.fractal.adl.Definition;
import org.ow2.mind.adl.CompilationDecorationHelper.AdditionalCompilationUnitDecoration;
import org.ow2.mind.adl.ImplementationHeaderSourceGenerator;
import org.ow2.mind.adl.ast.ImplementationContainer;
import org.ow2.mind.adl.ast.Source;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class CppMembraneSourceGenerator extends MembraneSourceGenerator {

  /** The name to be used to inject the templateGroupName used by this class. */
  public static final String    TEMPLATE_NAME    = "cpp.membrane.implementation";

  /** The default templateGroupName used by this class. */
  public static final String    DEFAULT_TEMPLATE = "st.cpp.membrane.MembraneImplementation";

  protected static final String FILE_SUFFIX      = "_ctrl_impl";
  protected static final String FILE_EXT         = ".cpp";

  @Inject
  protected CppMembraneSourceGenerator(
      @Named(TEMPLATE_NAME) final String templateGroupName) {
    super(templateGroupName);
  }

  // ---------------------------------------------------------------------------
  // Implementation of the Visitor interface
  // ---------------------------------------------------------------------------

  @Override
  public void visit(final Definition definition,
      final Map<Object, Object> context) throws ADLException {

    final String outputFileName = getCtrlImplFileName(definition);
    final File outputFile = outputFileLocatorItf.getCSourceOutputFile(
        outputFileName, context);

    if (regenerate(outputFile, definition, context)) {

      // TODO

    }

    // TODO: check BasicMembraneSourceGenerator
    // (here copied brutally from Optim backend)
    Collection<File> dependencies = null;
    if (definition instanceof ImplementationContainer) {
      final Source sources[] = ((ImplementationContainer) definition)
          .getSources();
      if (sources.length == 1) {
        dependencies = new ArrayList<File>();
        dependencies.add(outputFileLocatorItf.getCCompiledTemporaryOutputFile(
            ImplementationHeaderSourceGenerator
                .getImplHeaderFileName(definition), context));
      } else if (sources.length > 1) {
        dependencies = new ArrayList<File>();
      }
    }

    addAdditionalCompilationUnit(definition,
        new AdditionalCompilationUnitDecoration(outputFileName, true,
            dependencies));
  }

}
