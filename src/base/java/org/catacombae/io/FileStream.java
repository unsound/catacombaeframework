/*-
 * Copyright (C) 2007-2008 Erik Larsson
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

import java.io.File;
import java.io.IOException;


/**
 * This class wraps a java.io.RandomAccessFile (opened in read/write mode) and
 * maps its operations to the operations of RandomAccessStream.
 *
 * @author <a href="https://catacombae.org" target="_top">Erik Larsson</a>
 */
public class FileStream extends ReadableFileStream implements TruncatableRandomAccessStream {
    public FileStream(String filename) {
	super(new File(filename), "rw");
    }
    
    public FileStream(File file) {
        super(file, "rw");
    }
    
    public void write(byte[] b) {
	try {
	    raf.write(b);
	} catch(IOException ex) { throw new RuntimeIOException(ex); }
    }
    
    public void write(byte[] b, int off, int len) {
	try {
	    raf.write(b, off, len);
	} catch(IOException ex) { throw new RuntimeIOException(ex); }
    }
    
    public void write(int b) {
	try {
	    raf.write(b);
	} catch(IOException ex) { throw new RuntimeIOException(ex); }
    }

    public void setLength(long newLength) throws RuntimeIOException {
	try {
	    raf.setLength(newLength);
	} catch(IOException ex) { throw new RuntimeIOException(ex); }
    }
}
