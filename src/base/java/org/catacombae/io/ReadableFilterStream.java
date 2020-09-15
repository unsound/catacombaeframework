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

/**
 * Wraps a ReadableRandomAccessStream inside this one and maps all operations
 * one to one to the underlying ReadableRandomAccessStream.
 * There's no practical use for this class other than to facilitate
 * filtering subclasses which can override all operations, or just a few of
 * them.
 * 
 * @author <a href="https://catacombae.org" target="_top">Erik Larsson</a>
 */
public class ReadableFilterStream implements ReadableRandomAccessStream {
    protected ReadableRandomAccessStream backingStore;
    
    public ReadableFilterStream(ReadableRandomAccessStream backing) {
	this.backingStore = backing;
    }
    public void seek(long pos) {
	backingStore.seek(pos);
    }
    public int read() {
	return backingStore.read();
    }
    public int read(byte[] data) {
	return backingStore.read(data);
    }
    public int read(byte[] data, int pos, int len) {
	return backingStore.read(data, pos, len);
    }

    public byte readFully() {
        return backingStore.readFully();
    }

    public void readFully(byte[] data) {
	backingStore.readFully(data);
    }
    public void readFully(byte[] data, int offset, int length) {
	backingStore.readFully(data, offset, length);
    }
    public long length() {
	return backingStore.length();
    }
    public long getFilePointer() {
	return backingStore.getFilePointer();
    }
    public void close() {
	backingStore.close();
    }
}
