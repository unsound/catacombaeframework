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

package org.catacombae.io;

import java.io.IOException;

/**
 * Being forced to deal with IOExceptions all the time is one of the most
 * annoying things about the Java API. I prefer most exceptions to be
 * RuntimeExceptions.<br>
 * This is a subclass of RuntimeExceptions that are meant to be thrown when
 * it is caused by I/O problems.
 * 
 * @author <a href="http://hem.bredband.net/catacombae">Erik Larsson</a>
 */
public class RuntimeIOException extends RuntimeException {
    private final IOException ioCause;
    
    public RuntimeIOException(String s) {
        super(s);
        ioCause = null;
    }
    
    public RuntimeIOException(IOException iIoCause) {
        super(iIoCause);
        if(iIoCause == null)
            throw new IllegalArgumentException("RuntimeIOExceptions can not " +
                    "be thrown without an underlying IOException");
        ioCause = iIoCause;
    }
    
    public RuntimeIOException(String message, IOException iIoCause) {
        super(message, iIoCause);
        if(iIoCause == null)
            throw new IllegalArgumentException("RuntimeIOExceptions can not " +
                    "be thrown without an underlying IOException");
        ioCause = iIoCause;
    }
    
    /**
     * Returns the IOException that caused this RuntimeIOException to be thrown,
     * or <code>null</code> if the RuntimeIOException was thrown independently.
     * 
     * @return the IOException that caused this RuntimeIOException to be thrown,
     * or <code>null</code> if the RuntimeIOException was thrown independently.
     */
    public IOException getIOCause() {
        return ioCause;
    }
}
