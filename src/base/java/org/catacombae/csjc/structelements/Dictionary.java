/*-
 * Copyright (C) 2008 Erik Larsson
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catacombae.csjc.structelements;

import java.util.Hashtable;
import org.catacombae.hfsexplorer.Util;

public class Dictionary extends StructElement {

    private final String[] keys;
    private final Hashtable<String,StructElement> mappings;

    Dictionary(String typeName, String[] keys, Hashtable<String,StructElement> mappings) {
        super(typeName);
        this.keys = new String[keys.length];
        System.arraycopy(keys, 0, this.keys, 0, keys.length);
        this.mappings = new Hashtable<String,StructElement>();
        for(String key : keys) {
            this.mappings.put(key, mappings.get(key));
        }
    }

    public StructElement getElement(String name) {
        return mappings.get(name);
    }

    public int getElementCount() {
        return keys.length;
    }

    public String[] getKeys() {
        return Util.arrayCopy(keys, new String[keys.length]);
    }
}
