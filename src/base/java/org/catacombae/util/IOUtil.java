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

import org.catacombae.io.ReadableRandomAccessStream;
import org.catacombae.io.RuntimeIOException;

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
