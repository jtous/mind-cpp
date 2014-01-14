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

import java.io.File;
import java.util.Map;

import org.objectweb.fractal.adl.ADLException;
import org.objectweb.fractal.adl.Definition;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * {@link DefinitionSourceGenerator} component that generated {@value #FILE_EXT}
 * files using the {@value #DEFAULT_TEMPLATE} template.
 */
public class CppDefinitionIncSourceGenerator
    extends
      DefinitionIncSourceGenerator {

  /** The name to be used to inject the templateGroupName used by this class. */
  public static final String    TEMPLATE_NAME    = "cpp.definitions.implementations";

  /** The default templateGroupName used by this class. */
  public static final String    DEFAULT_TEMPLATE = "st.cpp.definitions.implementations.Component";

  protected final static String FILE_EXT         = ".hppinc";

  @Inject
  protected CppDefinitionIncSourceGenerator(
      @Named(TEMPLATE_NAME) final String templateGroupName) {
    super(templateGroupName);
  }

  // ---------------------------------------------------------------------------
  // Implementation of the DefinitionSourceGenerator interface
  // ---------------------------------------------------------------------------

  @Override
  public void visit(final Definition definition,
      final Map<Object, Object> context) throws ADLException {

    final File outputFile = outputFileLocatorItf.getCSourceOutputFile(
        getOutputFileName(definition), context);

    if (regenerate(outputFile, definition, context)) {

      // TODO

    }
  }

}
