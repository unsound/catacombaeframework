/*-
 * Copyright (C) 2008 Erik Larsson
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.catacombae.util;

/**
 * Utility class for checking the version number of the currently running JRE. This class does not
 * reference any actual Java 6-only classes, so it's safe to call from any VM (or at least Java 5
 * VM:s, as I never write code for anything less than Java 5...).
 * 
 * @author <a href="https://catacombae.org" target="_top">Erik Larsson</a>
 */
public class Java6Util {
    /**
     * Tests whether or not the current VM is a Java 6 or higher VM. This method
     * should always be invoked to check that the version number of the
     * currently running JRE is higher than or equal to 1.6 before invoking any
     * of the methods in Java6Specific.
     * 
     * @return whether or not the current VM is a Java 6 or higher VM.
     */
    public static boolean isJava6OrHigher() {
    	return System.getProperty("java.specification.version").
                compareTo("1.6") >= 0;
    }
}
