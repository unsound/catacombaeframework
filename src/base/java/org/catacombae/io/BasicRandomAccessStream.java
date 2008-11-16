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

package org.catacombae.io;

/**
 * This convenient base class provides default implementations for some of the
 * methods in RandomAccessStream.
 * 
 * @author <a href="http://hem.bredband.net/catacombae">Erik Larsson</a>
 */
public abstract class BasicRandomAccessStream extends BasicReadableRandomAccessStream implements RandomAccessStream {

    /**
     * Empty constructor (there is no state maintained in this class).
     */
    protected BasicRandomAccessStream() { }
    
    /** {@inheritDoc} */
    public void write(byte[] b) {
        BasicWritableRandomAccessStream.defaultWrite(this, b);
    }

    /** {@inheritDoc} */
    public abstract void write(byte[] b, int off, int len);

    /** {@inheritDoc} */
    public void write(int b) {
        BasicWritableRandomAccessStream.defaultWrite(this, b);
    }
}
