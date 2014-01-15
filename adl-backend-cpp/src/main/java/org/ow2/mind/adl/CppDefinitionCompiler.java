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

import java.util.Collection;
import java.util.Map;

import org.objectweb.fractal.adl.ADLException;
import org.objectweb.fractal.adl.Definition;
import org.ow2.mind.adl.CompilationDecorationHelper.AdditionalCompilationUnitDecoration;
import org.ow2.mind.adl.ast.ImplementationContainer;
import org.ow2.mind.compilation.CompilationCommand;

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

    // TODO

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

    // TODO

  }
}
