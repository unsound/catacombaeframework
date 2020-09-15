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

import java.util.zip.CRC32;

/**
 * Updates a CRC32 checksum for each byte you read from the underlying stream.
 * Seeking does not reset the checksum. It is only the read methods and what
 * they return that alter the value of the checksum.
 * 
 * @author <a href="https://catacombae.org" target="_top">Erik Larsson</a>
 */
public class ReadableCRC32FilterStream implements ReadableRandomAccessStream {
    private ReadableRandomAccessStream source;
    private CRC32 checksum;
	
    public ReadableCRC32FilterStream(ReadableRandomAccessStream source) {
	this.source = source;
	this.checksum = new CRC32();
    }
	
    public int getChecksumValue() { return (int)(checksum.getValue() & 0xFFFFFFFF); }
    public void resetChecksum() { checksum.reset(); }
    
    public void seek(long pos) {
	source.seek(pos);
    }
    public int read() {
	int res = source.read();
	if(res > 0) checksum.update(res);
	return res;
    }
    public int read(byte[] data) {
	int res = source.read(data);
	if(res > 0) checksum.update(data, 0, res);
	return res;
    }
    public int read(byte[] data, int pos, int len) {
	int res = source.read(data);
	if(res > 0) checksum.update(data, pos, res);
	return res;
    }

    public byte readFully() {
        byte res = source.readFully();
        if(res > 0) checksum.update(res & 0xFF);
        return res;
    }

    public void readFully(byte[] data) {
	source.readFully(data);
	checksum.update(data);
    }
    public void readFully(byte[] data, int offset, int length) {
	source.readFully(data, offset, length);
	checksum.update(data, offset, length);
    }
    public long length() { return source.length(); }
    public long getFilePointer() { return source.getFilePointer(); }
    public void close() { source.close(); }
}
