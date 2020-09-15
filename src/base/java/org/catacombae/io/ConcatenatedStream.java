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
 * A full read/write version of ReadableConcatenatedStream.
 * Note: Untested!
 *
 * @author <a href="https://catacombae.org" target="_top">Erik Larsson</a>
 */
public class ConcatenatedStream extends BasicConcatenatedStream<RandomAccessStream> implements RandomAccessStream {
    
    public ConcatenatedStream(RandomAccessStream firstPart, long startOffset, long length) {
        super(firstPart, startOffset, length);
    }

    public void write(byte[] data) throws RuntimeIOException {
        BasicWritable.defaultWrite(this, data);
    }

    public void write(byte[] data, int off, int len) throws RuntimeIOException {
        int bytesWritten = 0;

        // First: Look up the position represented by our virtual file pointer.
        long bytesToSkip = virtualFP;
        int requestedPartIndex = 0;
        for(Part p : parts) {
            if(bytesToSkip < p.length) {
                /* The first byte of virtualFP is within this part. */
                break;
            }

            ++requestedPartIndex;
            bytesToSkip -= p.length;
        }

        if(requestedPartIndex >= parts.size()) {
            throw new RuntimeIOException("Tried to write beyond end of " +
                    "stream.");
        }

        // Loop as long as we still have data to fill, and we still have parts to process.
        while(bytesWritten < len && requestedPartIndex < parts.size()) {
            Part requestedPart = parts.get(requestedPartIndex++);
            long bytesToSkipInPart = bytesToSkip;
            bytesToSkip = 0;

            int bytesLeftToWrite = len - bytesWritten;
            int bytesToWrite = (int) ((bytesLeftToWrite < requestedPart.length) ? bytesLeftToWrite : requestedPart.length);

            if(requestedPart.file == null) {
                throw new RuntimeException("Tried to write to hole at " +
                        "offset: " + requestedPart.startOffset);
            }

            requestedPart.file.seek(bytesToSkipInPart);
            requestedPart.file.write(data, off + bytesWritten, bytesToWrite);
            
            bytesWritten += bytesToWrite;
        }

        if(bytesWritten < len)
            throw new RuntimeIOException("Could not write all data requested (wrote: " +
                    bytesWritten + " requested:" + len + ".");
        
        if(bytesWritten > len) // Debug check.
            throw new RuntimeException("Wrote more than I was supposed to (" + bytesWritten +
                    " / " + len + " bytes)! This can't happen.");
    }

    public void write(int data) throws RuntimeIOException {
        BasicWritable.defaultWrite(this, data);
    }
}