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

#ifndef MINDCPP_IDL_INTERFACEREFERENCE_HPP_
#define MINDCPP_IDL_INTERFACEREFERENCE_HPP_
#include "InterfaceDescriptor.hpp"

//Common ancestor for all interface references utility is not proved yet
//might use a template instead
template <typename AnInterfaceDescriptor>
class InterfaceReference {
public:
	inline InterfaceReference(){
		reference = 0; //FIXME set a reference to 0 ?
	};
//	inline InterfaceReference(AnInterfaceDescriptor* itfRef){
//		reference = itfRef;
//	};
	inline bool IsNullInterface(){
		if (reference == 0) return true; //FIXME compare a reference to 0 ?
		else return false;
	}
	inline AnInterfaceDescriptor* getReference(){
		return reference;
	}
public:
	//The Binding
	AnInterfaceDescriptor* reference;
};

#endif /* MINDCPP_IDL_INTERFACEREFERENCE_HPP_ */
