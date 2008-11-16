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

/**
 * A full read/write version of ReadableConcatenatedStream.
 * 
 * @author <a href="http://hem.bredband.net/catacombae">Erik Larsson</a>
 */
public class ConcatenatedStream extends BasicRandomAccessStream {
    private ReadableConcatenatedStream backingFile;
    
    public ConcatenatedStream(RandomAccessStream firstPart, long startOffset, long length) {
	backingFile = new ReadableConcatenatedStream(firstPart, startOffset, length);
    }
    
    public void addPart(RandomAccessStream newFile, long off, long len) {
        ReadableConcatenatedStream.Part newPart = new ReadableConcatenatedStream.Part(newFile, off, len);
        backingFile.parts.add(newPart);
    }
    
    public void seek(long pos) {
        backingFile.seek(pos);
    }
    
    @Override
    public int read() {
        return backingFile.read();
    }

    @Override
    public int read(byte[] data) {
        return backingFile.read(data);
    }

    @Override
    public int read(byte[] data, int pos, int len) {
        return backingFile.read(data, pos, len);
    }

    @Override
    public void readFully(byte[] data) {
        backingFile.readFully(data);
    }

    @Override
    public void readFully(byte[] data, int offset, int length) {
        backingFile.readFully(data, offset, length);
    }
    
    public void write(byte[] data, int off, int len) {
        int bytesWritten = 0;
        RandomAccessStream currentWritableFile = (RandomAccessStream)backingFile.currentPart.file;
        
	while(true) {
	    long bytesLeftInFile = backingFile.currentPart.length - 
                    (currentWritableFile.getFilePointer() - backingFile.currentPart.startOffset);
	    int bytesLeftToWrite = len - bytesWritten;
	    int bytesToWrite = 
                    (int)((bytesLeftInFile < bytesLeftToWrite) ? bytesLeftInFile : bytesLeftToWrite);
            currentWritableFile.write(data, off+bytesWritten, bytesToWrite);
            bytesWritten += bytesToWrite;
            if(bytesWritten < len) {
                // move pointer forward, so that currentPart advances.
                backingFile.currentPartIndex++;
                backingFile.currentPart = backingFile.parts.get(backingFile.currentPartIndex);
                currentWritableFile = (RandomAccessStream)backingFile.currentPart.file;
                currentWritableFile.seek(backingFile.currentPart.startOffset);
            }
            else if(bytesWritten == len)
                return;
            else
                throw new RuntimeException("Wrote more than I was supposed to! This can't happen.");
        }
    }

    public long length() {
        return backingFile.length();
    }

    public long getFilePointer() {
        return backingFile.getFilePointer();
    }

    public void close() {
        backingFile.close();
    }

}

