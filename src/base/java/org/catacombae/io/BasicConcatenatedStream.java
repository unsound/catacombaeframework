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

import java.io.PrintStream;
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

    protected final List<Part> parts = new ArrayList<Part>();
    protected long virtualFP;

    protected BasicConcatenatedStream(A firstPart, long startOffset, long length) {
        //enter(null, firstPart, startOffset, length);
        Part currentPart = new Part(firstPart, startOffset, length);
        parts.add(currentPart);
        virtualFP = 0;
    }

    private void enter(String methodName, Object... args) {
        PrintStream out = System.err;
        out.print(this.getClass().getSimpleName() + "{" +
                this.hashCode() + "}");
        if(methodName != null)
            out.print("." + methodName);
        out.print("(");
        for(int i = 0; i < args.length; ++i) {
            if(i > 0)
                out.print(", ");
            out.print(args[i].toString());
        }
        out.println(");");
    }

    private void log(String methodName, String message) {
        PrintStream out = System.err;
        out.println(this.getClass().getSimpleName() + "{" +
                this.hashCode() + "}." + methodName + ": " + message);
    }

    public void addPart(A newFile, long off, long len) {
        //enter("addPart", newFile, off, len);
        Part newPart = new Part(newFile, off, len);
        parts.add(newPart);
    }

    public void seek(long pos) {
        //enter("seek", pos);
        virtualFP = pos;
    }

    public int read(byte[] data, int off, int len) {
        //String METHOD_NAME = "read";
        //enter(METHOD_NAME, data, off, len);
        //log(METHOD_NAME, "virtualFP=" + virtualFP);
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
            Part requestedPart = parts.get(requestedPartIndex++);
            //log(METHOD_NAME, "requestedPart.length = " + requestedPart.length);
            //log(METHOD_NAME, "requestedPart.startOffset = " + requestedPart.startOffset);

            long bytesToSkipInPart = bytesToSkip;
            //log(METHOD_NAME, "bytesToSkipInPart=" + bytesToSkipInPart);

            bytesToSkip = 0;

            int bytesLeftToRead = len - bytesRead;
            //log(METHOD_NAME, "bytesLeftToRead = " + bytesLeftToRead);
            int bytesToRead = (int) ((bytesLeftToRead < requestedPart.length)
                    ? bytesLeftToRead : requestedPart.length);
            //log(METHOD_NAME, "bytesToRead = " + bytesToRead);
            //log(METHOD_NAME, "seeking to " + bytesToSkipInPart);
            requestedPart.file.seek(bytesToSkipInPart);
            //log(METHOD_NAME, "invoking requestedPart.file.read(byte[" +
            //        data.length + "], " + (off+bytesRead) + ", " + bytesToRead
            //        + ")");
            int res = requestedPart.file.read(data, off + bytesRead, bytesToRead);
            //log(METHOD_NAME, "res = " + res);
            if(res > 0) {
                virtualFP += res;
                bytesRead += res;
                if(bytesRead == len) {
                    //log(METHOD_NAME, "returning " + bytesRead);
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
        //String METHOD_NAME = "length";
        //enter(METHOD_NAME);
        long result = 0;
        for(Part p : parts)
            result += p.length;
        //log(METHOD_NAME, "returning " + result);
        return result;
    }

    public long getFilePointer() {
        //String METHOD_NAME = "getFilePointer";
        //enter(METHOD_NAME);
       return virtualFP;
    }

    /** Closes all the files constituting this BasicConcatenatedStream. */
    public void close() {
        //enter("close");
        for(Part p : parts)
            p.file.close();
    }

}
