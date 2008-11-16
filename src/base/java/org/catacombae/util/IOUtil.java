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

package org.catacombae.util;

/**
 * CatacombaeIO-specific utility class.
 * 
 * @author Erik Larsson
 */
public class IOUtil {
    /**
     * Reads the supplied ReadableRandomAccessStream from its current position
     * until the end of the stream.
     *
     * @param s
     * @return the contents of the remainder of the stream.
     * @throws org.catacombae.io.RuntimeIOException if an I/O error occurred
     * when reading the stream.
     */
    public static byte[] readFully(ReadableRandomAccessStream s) throws RuntimeIOException {
        if(s.length() < 0 || s.length() > Integer.MAX_VALUE)
            throw new IllegalArgumentException("Length of s is out of range: " + s.length());

        byte[] res = new byte[(int)(s.length()-s.getFilePointer())];
        s.readFully(res);
        return res;
    }
}
