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
import org.catacombae.util.Util;

/**
 * This class wraps a java.io.RandomAccessFile (opened in read-only mode) and
 * maps its operations to the operations of ReadableRandomAccessStream.
 *
 * @author <a href="http://hem.bredband.net/catacombae">Erik Larsson</a>
 */
public class ReadableFileStream implements ReadableRandomAccessStream {

    private static final IOLog log =
            IOLog.getInstance(ReadableFileStream.class);

    static {
        log.debug = Util.booleanEnabledByProperties(log.debug,
                "org.catacombae.debug",
                "org.catacombae.io.debug",
                "org.catacombae.io." +
                ReadableFileStream.class.getSimpleName() + ".debug");

        log.trace = Util.booleanEnabledByProperties(log.trace,
                "org.catacombae.debug",
                "org.catacombae.io.debug",
                "org.catacombae.io." +
                ReadableFileStream.class.getSimpleName() + ".trace");
    }

    protected final RandomAccessFile raf;

    public ReadableFileStream(String filename) {
        this(new File(filename));
    }

    public ReadableFileStream(File file) {
        this(file, "r");
    }

    public ReadableFileStream(RandomAccessFile raf) {
        if(log.trace)
            log.traceEnter(null, raf);

        try {
            if(raf == null)
                throw new IllegalArgumentException("iRaf may NOT be null");
            this.raf = raf;
        } finally {
            if(log.trace)
                log.traceLeave(null, raf);
        }
    }

    protected ReadableFileStream(String filename, String mode) {
        this(new File(filename), mode);
    }

    protected ReadableFileStream(File file, String mode) {
        if(log.trace)
            log.traceEnter(null, file, mode);

        try {
            this.raf = new RandomAccessFile(file, mode);
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave(null, file, mode);
        }
    }

    public void seek(long pos) {
        if(log.trace)
            log.traceEnter("seek", pos);

        try {
            raf.seek(pos);
        } catch(IOException ioe) {
            throw new RuntimeIOException("pos=" + pos + "," + ioe.toString(),
                    ioe);
        } finally {
            if(log.trace)
                log.traceLeave("seek", pos);
        }
    }

    public int read() {
        if(log.trace)
            log.traceEnter("read");

        try {
            int res = raf.read();
            if(log.trace)
                log.traceReturn(res);
            return res;
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave("read");
        }
    }

    public int read(byte[] data) {
        if(log.trace)
            log.traceEnter("read", data);

        try {
            int res = raf.read(data);
            if(log.trace)
                log.traceReturn(res);
            return res;
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave("read", data);
        }
    }

    public int read(byte[] data, int pos, int len) {
        if(log.trace)
            log.traceEnter("read", data, pos, len);

        try {
            int res = raf.read(data, pos, len);
            if(log.trace)
                log.traceReturn(res);
            return res;
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave("read", data, pos, len);
        }
    }

    public void readFully(byte[] data) {
        if(log.trace)
            log.traceEnter("readFully", data);

        try {
            raf.readFully(data);
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave("readFully", data);
        }
    }

    public void readFully(byte[] data, int offset, int length) {
        if(log.trace)
            log.traceEnter("readFully", data, offset, length);

        try {
            raf.readFully(data, offset, length);
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave("readFully", data, offset,
                        length);
        }
    }

    public long length() {
        if(log.trace)
            log.traceEnter("length");

        try {
            return raf.length();
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave("length");
        }
    }

    public long getFilePointer() {
        if(log.trace)
            log.traceEnter("getFilePointer");

        try {
            long res = raf.getFilePointer();
            if(log.trace)
                log.traceReturn(res);
            return res;
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave("getFilePointer");
        }
    }

    public void close() {
        if(log.trace)
            log.traceEnter("close");

        try {
            raf.close();
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave("close");
        }
    }
}
