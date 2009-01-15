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

import java.util.ArrayList;
import java.util.List;

/**
 * Common superclass of ReadableConcatenatedStream and ConcatenatedStream.
 *
 * @author <a href="http://hem.bredband.net/catacombae">Erik Larsson</a>
 */
public abstract class BasicConcatenatedStream<A extends ReadableRandomAccessStream> extends BasicReadableRandomAccessStream {

    protected class Part {

        public final A file;
        public final long startOffset;
        public final long length;

        public Part(A file, long startOffset, long length) {
            this.file = file;
            this.startOffset = startOffset;
            this.length = length;
        }
    }
    // private static final boolean debug = true;
    protected final List<Part> parts = new ArrayList<Part>();
    //Part currentPart;
    protected long virtualFP;

    protected BasicConcatenatedStream(A firstPart, long startOffset, long length) {
        // if(debug) System.err.println("ReadableConcatenatedStream(" + firstPart + ", " + startOffset + ", " + length + ");");
        Part currentPart = new Part(firstPart, startOffset, length);
        parts.add(currentPart);
        virtualFP = 0;
    }

    public void addPart(A newFile, long off, long len) {
        // if(debug) System.err.println("ReadableConcatenatedStream.addPart(" + newFile + ", " + off + ", " + len + ");");
        Part newPart = new Part(newFile, off, len);
        parts.add(newPart);
    }

    public void seek(long pos) {
        // if(debug) System.err.println("ReadableConcatenatedStream.seek(" + pos + ");");
        long curPos = 0;
        for(Part p : parts) {
            if(curPos + p.length > pos) {
                //currentPart = p;
                //currentPart.file.seek(currentPart.startOffset + (pos - curPos));
                break;
            }
            else
                curPos += p.length;
        }
        virtualFP = curPos;
    }

    public int read(byte[] data, int off, int len) {
        //boolean debug = true;
        // if(debug) System.err.println("ReadableConcatenatedStream{" + this.hashCode() + "}.read(" + data + ", " + pos + ", " + len + ");");
        int bytesRead = 0;

        long bytesToSkip = virtualFP;
        int requestedPartIndex = -1;
        for(Part p : parts) {
            ++requestedPartIndex;

            if(bytesToSkip > p.length) {
                bytesToSkip -= p.length;
            }
            else {
                break;
            }
        }
        if(requestedPartIndex == -1)
            return -1;

        while(requestedPartIndex < parts.size()) {
            //if(debug) System.err.println("  ReadableConcatenatedStream.read(3): currentPart.length = " + currentPart.length);
            //if(debug) System.err.println("  ReadableConcatenatedStream.read(3): currentPart.startOffset = " + currentPart.startOffset);
            Part requestedPart = parts.get(requestedPartIndex++);
            long bytesToSkipInPart = bytesToSkip;
            bytesToSkip = 0;

            /*
            long currentFP = currentPart.file.getFilePointer();
            if(currentFP < currentPart.startOffset) {
                currentFP = currentPart.startOffset;
                currentPart.file.seek(currentFP);
            }
            else if(currentFP > (currentPart.startOffset+currentPart.length)) {
                currentFP = currentPart.startOffset+currentPart.length;
                currentPart.file.seek(currentFP);
            }
             * */
            //if(debug) System.err.println("  ReadableConcatenatedStream.read(3): currentPart.file.getFilePointer() = " + currentFP);

            //long bytesLeftInPart = requestedPart.length;
            //if(debug) System.err.println("  ReadableConcatenatedStream.read(3): bytesLeftInFile = " + bytesLeftInFile);
            int bytesLeftToRead = len - bytesRead;
            //if(debug) System.err.println("  ReadableConcatenatedStream.read(3): bytesLeftToRead = " + bytesLeftToRead);
            int bytesToRead = (int) ((bytesLeftToRead < requestedPart.length) ? bytesLeftToRead : requestedPart.length);
            //if(debug) System.err.println("  ReadableConcatenatedStream.read(3): bytesToRead = " + bytesToRead);
            //if(debug) System.err.println("  ReadableConcatenatedStream.read(3): invoking currentPart.file.read(byte[" + data.length + "], " + (pos+bytesRead) + ", " + bytesToRead + ")");
            requestedPart.file.seek(bytesToSkipInPart);
            int res = requestedPart.file.read(data, off + bytesRead, bytesToRead);
            //if(debug) System.err.println("  ReadableConcatenatedStream.read(3): res = " + res);
            if(res > 0) {
                virtualFP += res;
                bytesRead += res;
                if(bytesRead == len) {
                    //if(debug) System.err.println("  ReadableConcatenatedStream.read(3): returning " + bytesRead);
                    return bytesRead;
                }
                else if(bytesRead > len)
                    throw new RuntimeException("Read more than I was supposed to! This can't happen.");
            }
            else {
                if(bytesRead > 0)
                    return bytesRead;
                else
                    return -1;
            }
        }

        return bytesRead;
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
        /*
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
        */
        return virtualFP;
    }

    /** Closes all the files constituting this ReadableConcatenatedStream. */
    public void close() {
        // if(debug) System.err.println("ReadableConcatenatedStream.close();");
        for(Part p : parts)
            p.file.close();
    }

}
