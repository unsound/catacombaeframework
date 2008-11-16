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
 * Defines the methods that must exist for a stream to be readable.
 * 
 * @author <a href="http://hem.bredband.net/catacombae">Erik Larsson</a>
 */
public interface Readable {
    /**
     * Reads a single byte from the stream and returns it as an unsigned value (range 0-255). If the
     * end of stream has been reached, this method returns -1. This method should never return a
     * value outside the range <code>-1 &lt;= value &lt;=255</code>.
     * 
     * @return the next byte in the stream, or -1 if there are no more bytes to read.
     * @throws org.catacombae.io.RuntimeIOException if an I/O error occurred.
     */
    public int read() throws RuntimeIOException;
    
    /**
     * Reads as much data as possible from the stream into the array <code>data</code>, until the
     * end of the array has been reached. Returns the number of bytes that were read into <code>
     * array</code>. If no bytes could be read due to end of stream, -1 is returned.
     * 
     * @param data the array where the output data should be stored.
     * @return the number of bytes that was read, or -1 if no bytes could be read due to end of
     * stream.
     * @throws org.catacombae.io.RuntimeIOException if an I/O error occurred.
     */
    public int read(byte[] data) throws RuntimeIOException;
    
    /**
     * Reads as much data as possible from the stream into the array <code>data</code> at position
     * <code>pos</code>, until <code>len</code> bytes have been read. Returns the number of bytes
     * that were read into <code> array</code>. If no bytes could be read due to end of stream,
     * -1 is returned.
     * 
     * @param data the array where the output data should be stored.
     * @param pos the start position in the array where data should be stored.
     * @param len the amount of data to write into the array.
     * @return the number of bytes that was read, or -1 if no bytes could be read due to end of
     * stream.
     * @throws org.catacombae.io.RuntimeIOException if an I/O error occurred.
     */
    public int read(byte[] data, int pos, int len) throws RuntimeIOException;
    
    /**
     * Reads into <code>data</code> until the end of the array has been reached. If this is not
     * possible due to end of stream, a RuntimeIOException is thrown.
     * 
     * @param data the array where the output data should be stored.
     * @throws org.catacombae.io.RuntimeIOException if the stream doesn't contain enough data to
     * fill the output array, or if an I/O error occurred.
     */
    public void readFully(byte[] data) throws RuntimeIOException;
    
    /**
     * Reads into <code>data</code> at position <code>pos</code> until <code>len</code> bytes have
     * been read. If this is not possible due to end of stream, a RuntimeIOException is thrown.
     * 
     * @param data the array where the output data should be stored.
     * @param offset the offset in the output array where we should start writing data.
     * @param length the number of bytes to write to the output array.
     * @throws org.catacombae.io.RuntimeIOException if the stream doesn't contain enough data to
     * fill <code>len</code> bytes, or if an I/O error occurred.
     */
    public void readFully(byte[] data, int offset, int length) throws RuntimeIOException;
}
