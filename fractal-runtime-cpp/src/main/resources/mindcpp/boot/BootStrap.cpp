/**
 * mindcpp runtime
 *
 * Copyright (C) 2014 Schneider-Electric
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 * Contact: mind@ow2.org
 *
 * Authors: Julien Tous
 */

#include "mindcpp/boot/BootStrap.adl.hpp"

using namespace __ns_mindcpp_boot_BootStrap;

//from c code possible for singletons only
//Might find a better way ???
extern mindcpp_boot_BootStrapDefinition& mindcpp_boot_BootStrapSingleton;

int main(int argc, char *argv[]) {
	int r;

	// call the "main" entry point of the application
	r = mindcpp_boot_BootStrapSingleton.entryPoint.main(argc, argv);

	return r;
}

