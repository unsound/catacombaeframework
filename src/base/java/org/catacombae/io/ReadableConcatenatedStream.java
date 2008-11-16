/*-
 * Copyright (C) 2007-2008 Erik Larsson
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

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of ReadableRandomAccessStream which concatenates a sequence
 * of ReadableRandomAccessStreams (or regions of them) into a new logical
 * ReadableRandomAccessStream.
 * 
 * @author <a href="http://hem.bredband.net/catacombae">Erik Larsson</a>
 */
public class ReadableConcatenatedStream extends BasicReadableRandomAccessStream {

    static class Part {

        public final ReadableRandomAccessStream file;
        public final long startOffset;
        public final long length;

        public Part(ReadableRandomAccessStream file, long startOffset, long length) {
            this.file = file;
            this.startOffset = startOffset;
            this.length = length;
        }
    }
    // private static final boolean debug = true;
    final List<Part> parts = new ArrayList<Part>();
    Part currentPart;
    int currentPartIndex;

    public ReadableConcatenatedStream(ReadableRandomAccessStream firstPart, long startOffset, long length) {
        // if(debug) System.err.println("ReadableConcatenatedStream(" + firstPart + ", " + startOffset + ", " + length + ");");
        currentPart = new Part(firstPart, startOffset, length);
        parts.add(currentPart);
        currentPartIndex = 0;
    }

    public void addPart(ReadableRandomAccessStream newFile, long off, long len) {
        // if(debug) System.err.println("ReadableConcatenatedStream.addPart(" + newFile + ", " + off + ", " + len + ");");
        Part newPart = new Part(newFile, off, len);
        parts.add(newPart);
    }
    
    public void seek(long pos) {
        // if(debug) System.err.println("ReadableConcatenatedStream.seek(" + pos + ");");
        long curPos = 0;
        for(Part p : parts) {
            if(curPos + p.length > pos) {
                currentPart = p;
                currentPart.file.seek(currentPart.startOffset + (pos - curPos));
                break;
            }
            else
                curPos += p.length;
        }
    }
    
    public int read(byte[] data, int pos, int len) {
        // if(debug) System.err.println("ReadableConcatenatedStream{" + this.hashCode() + "}.read(" + data + ", " + pos + ", " + len + ");");
        int bytesRead = 0;
        while(true) {
            // if(debug) System.err.println("  ReadableConcatenatedStream.read(3): currentPart.length = " + currentPart.length);
            // if(debug) System.err.println("  ReadableConcatenatedStream.read(3): currentPart.file.getFilePointer() = " + currentPart.file.getFilePointer());
            // if(debug) System.err.println("  ReadableConcatenatedStream.read(3): currentPart.startOffset = " + currentPart.startOffset);
            long bytesLeftInFile = currentPart.length - (currentPart.file.getFilePointer() - currentPart.startOffset);
            // if(debug) System.err.println("  ReadableConcatenatedStream.read(3): bytesLeftInFile = " + bytesLeftInFile);
            int bytesLeftToRead = len - bytesRead;
            // if(debug) System.err.println("  ReadableConcatenatedStream.read(3): bytesLeftToRead = " + bytesLeftToRead);
            int bytesToRead = (int) ((bytesLeftInFile < bytesLeftToRead) ? bytesLeftInFile : bytesLeftToRead);
            // if(debug) System.err.println("  ReadableConcatenatedStream.read(3): bytesToRead = " + bytesToRead);
            // if(debug) System.err.println("  ReadableConcatenatedStream.read(3): invoking currentPart.file.read(byte[" + data.length + "], " + (pos+bytesRead) + ", " + bytesToRead + ")");
            int res = currentPart.file.read(data, pos + bytesRead, bytesToRead);
            // if(debug) System.err.println("  ReadableConcatenatedStream.read(3): res = " + res);
            if(res > 0) {
                bytesRead += res;
                if(bytesRead < len) {
                    // move pointer forward, so that currentPart advances.
                    if(parts.size() < currentPartIndex + 1) {
                        currentPartIndex++;
                        currentPart = parts.get(currentPartIndex);
                        currentPart.file.seek(currentPart.startOffset);
                    }
                    else {
                        return bytesRead;
                    }
                }
                else if(bytesRead == len)
                    // if(debug) System.err.println("  ReadableConcatenatedStream.read(3): returning " + bytesRead);
                    return bytesRead;
                else
                    throw new RuntimeException("Read more than I was supposed to! This can't happen.");
            }
            else {
                if(bytesRead > 0)
                    return bytesRead;
                else
                    return -1;
            }
        }
    }
    
    public long length() {
        // if(debug) System.err.println("ReadableConcatenatedStream.length();");
        long result = 0;
        for(Part p : parts)
            result += p.length;
        // if(debug) System.err.println("  ReadableConcatenatedStream.length(): returning " + result);
        return result;
    }

    public long getFilePointer() {
        long fp = 0;
        for(int i = 0; i < currentPartIndex; ++i) {
            // if(debug) System.err.println("  ReadableConcatenatedStream.getFilePointer(): fp = " + fp);
            // if(debug) System.err.println("  ReadableConcatenatedStream.getFilePointer(): processing part " + i);
            fp += parts.get(i).length;
        }

        Part currentPartLocal = parts.get(currentPartIndex);
        long currentPartFP = currentPartLocal.file.getFilePointer();
        // if(debug) System.err.println("  ReadableConcatenatedStream.getFilePointer(): fp = " + fp);
        // if(debug) System.err.println("  ReadableConcatenatedStream.getFilePointer(): currentPartFP = " + currentPartFP);
        // if(debug) System.err.println("  ReadableConcatenatedStream.getFilePointer(): currentPartLocal.startOffset = " + currentPartLocal.startOffset);
    	fp += currentPartFP - currentPartLocal.startOffset;

        // if(debug) System.err.println("  ReadableConcatenatedStream.getFilePointer(): returning " + fp);
        return fp;
    }

    /** Closes all the files constituting this ReadableConcatenatedStream. */
    public void close() {
        // if(debug) System.err.println("ReadableConcatenatedStream.close();");
        for(Part p : parts)
            p.file.close();
    }
}
