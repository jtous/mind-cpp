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
 * Authors: Stephane Seyvoz
 * Contributors: 
 */

package org.ow2.mind.adl;

import org.ow2.mind.adl.idl.IDLDefinitionSourceGenerator;
import org.ow2.mind.adl.interfaces.CollectionInterfaceDefinitionSourceGenerator;
import org.ow2.mind.inject.AbstractMindModule;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

/**
 * Inspired from...
 * 
 * @see ADLBackendModule from adl-backend
 * @see ADLStaticBackendModule from adl-backend-static
 * @see src/main/resources/mind-plugin.xml for plugin configuration
 */
public class ADLCPPBackendModule extends AbstractMindModule {

  /**
   * Returns the {@link Multibinder} that can be used to add
   * {@link DefinitionSourceGenerator}.
   * 
   * @param binder the local binder.
   * @return the {@link Multibinder} that can be used to add
   *         {@link DefinitionSourceGenerator}.
   */
  public static Multibinder<DefinitionSourceGenerator> getDefinitionSourceGeneratorMultiBinder(
      final Binder binder) {

    /*
     * We use a CppAnnotation to differentiate THIS MultiBinder from the
     * standard MultiBinder. Otherwise, the Google Guice MultiBinder API (at
     * least in version 2.0) gives you the DefinitionSourceGenerator that the
     * MultiBinder already populated in the Standard backend Module. This means
     * the "addBinding" calls results were concatenated, leading to queue the
     * c++ backend source generation tasks in the delegation chain, meaning our
     * code generation would be called first, then OVERWRITTEN by the standard
     * backend source generators afterwards (first in the delegation chain =
     * last called, like a stack). We discriminate the code generators injection
     * in the CppDefinitionSourceGeneratorDispatcher by using our custom
     * @CppAnnotation on the injection field, recognized by Google Guice as it
     * implements @BindingAnnotation. For more information, see
     * "public static Multibinder<T> newSetBinder (Binder binder, Class<T> type, Annotation annotation)"
     * at:
     * http://google-guice.googlecode.com/svn/trunk/latest-javadoc/com/google
     * /inject/multibindings/Multibinder.html and
     * http://code.google.com/p/google-guice/wiki/BindingAnnotations The same
     * way was used for instance code generation.
     */
    final Multibinder<DefinitionSourceGenerator> setBinder = Multibinder
        .newSetBinder(binder, DefinitionSourceGenerator.class,
            CppAnnotation.class);
    return setBinder;
  }

  protected void configureDefinitionSourceGenerator() {
    bind(DefinitionSourceGenerator.class).toChainStartingWith(
        CollectionInterfaceDefinitionSourceGenerator.class).endingWith(
        CppDefinitionSourceGeneratorDispatcher.class);

    final Multibinder<DefinitionSourceGenerator> setBinder = ADLCPPBackendModule
        .getDefinitionSourceGeneratorMultiBinder(binder());
    setBinder.addBinding().to(CppDefinitionHeaderSourceGenerator.class);
// setBinder.addBinding().to(CppDefinitionIncSourceGenerator.class);
// setBinder.addBinding().to(CppImplementationHeaderSourceGenerator.class);
// setBinder.addBinding().to(CppDefinitionMacroSourceGenerator.class);
// setBinder.addBinding().to(CppMembraneSourceGenerator.class);
    setBinder.addBinding().to(IDLDefinitionSourceGenerator.class);
// setBinder.addBinding().to(GenericDefinitionNameSourceGenerator.class);
    setBinder.addBinding().to(BinaryADLWriter.class);
  }

  protected void configureDefinitionHeaderSourceGenerator() {
    bind(String.class).annotatedWith(
        Names.named(CppDefinitionHeaderSourceGenerator.TEMPLATE_NAME))
        .toInstance(CppDefinitionHeaderSourceGenerator.DEFAULT_TEMPLATE);
  }

// protected void configureDefinitionIncSourceGenerator() {
// bind(String.class).annotatedWith(
// Names.named(CppDefinitionIncSourceGenerator.TEMPLATE_NAME)).toInstance(
// CppDefinitionIncSourceGenerator.DEFAULT_TEMPLATE);
// }

// protected void configureDefinitionMacroSourceGenerator() {
// bind(String.class).annotatedWith(
// Names.named(CppDefinitionMacroSourceGenerator.TEMPLATE_NAME))
// .toInstance(CppDefinitionMacroSourceGenerator.DEFAULT_TEMPLATE);
// }

// protected void configureMembraneSourceGenerator() {
// bind(String.class).annotatedWith(
// Names.named(CppMembraneSourceGenerator.TEMPLATE_NAME)).toInstance(
// CppMembraneSourceGenerator.DEFAULT_TEMPLATE);
// }

  /**
   * Returns the {@link Multibinder} that can be used to add
   * {@link InstanceSourceGenerator}.
   * 
   * @param binder the local binder.
   * @return the {@link Multibinder} that can be used to add
   *         {@link InstanceSourceGenerator}.
   */
  public static Multibinder<InstanceSourceGenerator> getInstanceSourceGeneratorMultiBinder(
      final Binder binder) {

    /*
     * SSZ: We use an CppAnnotation to differenciate THIS MultiBinder from the
     * standard MultiBinder. Otherwise, the Google Guice MultiBinder API (at
     * least in version 2.0) gives you the already created
     * InstanceSourceGenerator MultiBinder already populated with the Standard
     * backend Module. This means the "addBinding" calls results were
     * concatenated, leading to queue the C++ backend source generation tasks in
     * the delegation chain, meaning our code generation would be called first,
     * then OVERWRITTEN by the standard backend source generators afterwards
     * (first in the delegation chain = last called, like a stack). We
     * discriminate the code generators injection in the
     * CppInstanceSourceGeneratorDispatcher by using our custom
     * @CppAnnotation on the injection field, recognized by Google Guice as it
     * implements @BindingAnnotation. For more information, see
     * "public static Multibinder<T> newSetBinder (Binder binder, Class<T> type, Annotation annotation)"
     * at:
     * http://google-guice.googlecode.com/svn/trunk/latest-javadoc/com/google
     * /inject/multibindings/Multibinder.html and
     * http://code.google.com/p/google-guice/wiki/BindingAnnotations The same
     * way was used for instance code generation.
     */

    final Multibinder<InstanceSourceGenerator> setBinder = Multibinder
        .newSetBinder(binder, InstanceSourceGenerator.class,
            CppAnnotation.class);
    return setBinder;
  }

  protected void configureInstanceSourceGenerator() {
    bind(InstanceSourceGenerator.class).to(
        CppInstanceSourceGeneratorDispatcher.class);

    final Multibinder<InstanceSourceGenerator> setBinder = ADLCPPBackendModule
        .getInstanceSourceGeneratorMultiBinder(binder());
    setBinder.addBinding().to(CppInstanceSourceGenerator.class);
  }

  protected void configureCppInstanceSourceGenerator() {
    bind(String.class).annotatedWith(
        Names.named(CppInstanceSourceGenerator.TEMPLATE_NAME)).toInstance(
        CppInstanceSourceGenerator.DEFAULT_TEMPLATE);
  }

  protected void configureDefinitionCompiler() {
    bind(DefinitionCompiler.class).to(CppDefinitionCompiler.class);
  }

  protected void configureInstanceCompiler() {
    bind(InstanceCompiler.class).to(CppInstanceCompiler.class);
  }

}
