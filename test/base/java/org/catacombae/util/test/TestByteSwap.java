/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.catacombae.util.test;

import org.catacombae.util.Util;

/**
 *
 * @author erik
 */
public class TestByteSwap {
    public static void main(String[] args) {
        byte[] testArray = new byte[] {
            0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7,
            0x8, 0x9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF,
            0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17,
            0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F
        };

        short testShort = (short)0xFEDC;
        char testChar = (char)0xBA98;
        int testInt = 0xFEDCBA98;
        long testLong = 0xFEDCBA9876543210L;

        System.out.println("byte array:");
        System.out.println("  Native:  0x" + Util.byteArrayToHexString(testArray));
        System.out.println("  Swapped: 0x" + Util.byteArrayToHexString(Util.byteSwap(testArray)));
        System.out.println("short:");
        System.out.println("  Native:  0x" + Util.toHexStringBE(testShort));
        System.out.println("  Swapped: 0x" + Util.toHexStringBE(Util.byteSwap(testShort)));
        System.out.println("char:");
        System.out.println("  Native:  0x" + Util.toHexStringBE(testChar));
        System.out.println("  Swapped: 0x" + Util.toHexStringBE(Util.byteSwap(testChar)));
        System.out.println("int:");
        System.out.println("  Native:  0x" + Util.toHexStringBE(testInt));
        System.out.println("  Swapped: 0x" + Util.toHexStringBE(Util.byteSwap(testInt)));
        System.out.println("long:");
        System.out.println("  Native:  0x" + Util.toHexStringBE(testLong));
        System.out.println("  Swapped: 0x" + Util.toHexStringBE(Util.byteSwap(testLong)));
    }
}
