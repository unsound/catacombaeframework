/*-
 * Copyright (C) 2006-2008 Erik Larsson
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
import java.io.RandomAccessFile;

/**
 * This class wraps a java.io.RandomAccessFile (opened in read-only mode) and
 * maps its operations to the operations of ReadableRandomAccessStream.
 *
 * @author <a href="http://hem.bredband.net/catacombae">Erik Larsson</a>
 */
public class ReadableFileStream implements ReadableRandomAccessStream {

    protected final RandomAccessFile raf;

    public ReadableFileStream(String filename) {
        this(new File(filename));
    }

    public ReadableFileStream(File file) {
        this(file, "r");
    }

    public ReadableFileStream(RandomAccessFile iRaf) {
        if(iRaf == null)
            throw new IllegalArgumentException("iRaf may NOT be null");
        this.raf = iRaf;
    }

    protected ReadableFileStream(String filename, String mode) {
        this(new File(filename), mode);
    }

    protected ReadableFileStream(File file, String mode) {
        try {
            this.raf = new RandomAccessFile(file, mode);
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        }
    }

    public void seek(long pos) {
        //System.err.println("ReadableFileStream.seek(" + pos + ");");
        try {
            raf.seek(pos);
        } catch(IOException ioe) {
            throw new RuntimeIOException("pos=" + pos + "," + ioe.toString(),
                    ioe);
        }
    }

    public int read() {
        //System.err.println("ReadableFileStream.read();");
        try {
            return raf.read();
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        }
    }

    public int read(byte[] data) {
        //System.err.println("ReadableFileStream.read(" + data + ");");
        try {
            return raf.read(data);
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        }
    }

    public int read(byte[] data, int pos, int len) {
        //System.err.println("ReadableFileStream.read(" + data + ", " + pos +
        //        ", " + len + ");");
        try {
            return raf.read(data, pos, len);
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        }
    }

    public void readFully(byte[] data) {
        //System.err.println("ReadableFileStream.readFully(" + data + ");");
        try {
            raf.readFully(data);
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        }
    }

    public void readFully(byte[] data, int offset, int length) {
        //System.err.println("ReadableFileStream.readFully(" + data + ", " +
        //        offset + ", " + length + ");");
        try {
            raf.readFully(data, offset, length);
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        }
    }

    public long length() {
        //System.err.println("ReadableFileStream.length();");
        try {
            return raf.length();
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        }
    }

    public long getFilePointer() {
        //System.err.println("ReadableFileStream.getFilePointer();");
        try {
            return raf.getFilePointer();
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        }
    }

    public void close() {
        //System.err.println("ReadableFileStream.close();");
        try {
            raf.close();
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        }
    }
}
