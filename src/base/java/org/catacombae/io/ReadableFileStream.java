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
 * @author <a href="https://catacombae.org" target="_top">Erik Larsson</a>
 */
public class ReadableFileStream implements ReadableRandomAccessStream,
        AbstractFileStream
{
    private static final IOLog log =
            IOLog.getInstance(ReadableFileStream.class);

    protected final RandomAccessFile raf;
    private final String openPath;

    public ReadableFileStream(String filename) {
        this(new File(filename));
    }

    public ReadableFileStream(File file) {
        this(file, "r");
    }

    public ReadableFileStream(RandomAccessFile raf, String openPath) {
        if(log.trace)
            log.traceEnter(raf);

        try {
            if(raf == null)
                throw new IllegalArgumentException("raf may NOT be null");
            this.raf = raf;
            this.openPath = openPath;
        } finally {
            if(log.trace)
                log.traceLeave(raf);
        }
    }

    protected ReadableFileStream(String filename, String mode) {
        this(new File(filename), mode);
    }

    protected ReadableFileStream(File file, String mode) {
        if(log.trace)
            log.traceEnter(file, mode);

        try {
            this.raf = new RandomAccessFile(file, mode);
            this.openPath = file.getPath();
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave(file, mode);
        }
    }

    public void seek(long pos) {
        if(log.trace)
            log.traceEnter(pos);

        try {
            raf.seek(pos);
        } catch(IOException ioe) {
            throw new RuntimeIOException("pos=" + pos + "," + ioe.toString(),
                    ioe);
        } finally {
            if(log.trace)
                log.traceLeave(pos);
        }
    }

    public int read() {
        if(log.trace)
            log.traceEnter();

        try {
            int res = raf.read();
            if(log.trace)
                log.traceReturn(res);
            return res;
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave();
        }
    }

    public int read(byte[] data) {
        if(log.trace)
            log.traceEnter(data);

        try {
            int res = raf.read(data);
            if(log.trace)
                log.traceReturn(res);
            return res;
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave(data);
        }
    }

    public int read(byte[] data, int pos, int len) {
        if(log.trace)
            log.traceEnter(data, pos, len);

        try {
            int res = raf.read(data, pos, len);
            if(log.trace)
                log.traceReturn(res);
            return res;
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave(data, pos, len);
        }
    }

    public byte readFully() {
        if(log.trace) {
            log.traceEnter();
        }

        try {
            byte[] data = new byte[1];
            raf.readFully(data);
            return data[0];
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace) {
                log.traceLeave();
            }
        }
    }

    public void readFully(byte[] data) {
        if(log.trace)
            log.traceEnter(data);

        try {
            raf.readFully(data);
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave(data);
        }
    }

    public void readFully(byte[] data, int offset, int length) {
        if(log.trace)
            log.traceEnter(data, offset, length);

        try {
            raf.readFully(data, offset, length);
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave(data, offset, length);
        }
    }

    public long length() {
        if(log.trace)
            log.traceEnter();

        try {
            return raf.length();
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave();
        }
    }

    public long getFilePointer() {
        if(log.trace)
            log.traceEnter();

        try {
            long res = raf.getFilePointer();
            if(log.trace)
                log.traceReturn(res);
            return res;
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave();
        }
    }

    public void close() {
        if(log.trace)
            log.traceEnter();

        try {
            raf.close();
        } catch(IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            if(log.trace)
                log.traceLeave();
        }
    }

    /* @Override */
    public String getOpenPath() {
        return openPath;
    }
}
