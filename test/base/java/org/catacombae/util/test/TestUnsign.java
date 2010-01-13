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
public class TestUnsign {
    public static void main(String[] args) {
        byte bLow = 0x0, bMiddle = 0x7F, bHigh = (byte)0xFF;
        short sLow = 0x0, sMiddle = 0x7FFF, sHigh = (short)0xFFFF;
        char cLow = 0x0, cMiddle = 0x7FFF, cHigh = 0xFFFF;
        int iLow = 0x0, iMiddle = 0x7FFFFFFF, iHigh = 0xFFFFFFFF;
        long lLow = 0x0, lMiddle = 0x7FFFFFFFFFFFFFFFL, lHigh = 0xFFFFFFFFFFFFFFFFL;

        System.out.println("byte:");
        System.out.println("  Low (natural): " + bLow);
        System.out.println("  Low (unsigned): " + Util.unsign(bLow));
        System.out.println("  Middle (natural): " + bMiddle);
        System.out.println("  Middle (unsigned): " + Util.unsign(bMiddle));
        System.out.println("  High (natural): " + bHigh);
        System.out.println("  High (unsigned): " + Util.unsign(bHigh));

        System.out.println("short:");
        System.out.println("  Low (natural): " + sLow);
        System.out.println("  Low (unsigned): " + Util.unsign(sLow));
        System.out.println("  Middle (natural): " + sMiddle);
        System.out.println("  Middle (unsigned): " + Util.unsign(sMiddle));
        System.out.println("  High (natural): " + sHigh);
        System.out.println("  High (unsigned): " + Util.unsign(sHigh));

        System.out.println("char:");
        System.out.println("  Low (natural): " + cLow);
        System.out.println("  Low (unsigned): " + Util.unsign(cLow));
        System.out.println("  Middle (natural): " + cMiddle);
        System.out.println("  Middle (unsigned): " + Util.unsign(cMiddle));
        System.out.println("  High (natural): " + cHigh);
        System.out.println("  High (unsigned): " + Util.unsign(cHigh));

        System.out.println("int:");
        System.out.println("  Low (natural): " + iLow);
        System.out.println("  Low (unsigned): " + Util.unsign(iLow));
        System.out.println("  Middle (natural): " + iMiddle);
        System.out.println("  Middle (unsigned): " + Util.unsign(iMiddle));
        System.out.println("  High (natural): " + iHigh);
        System.out.println("  High (unsigned): " + Util.unsign(iHigh));

        System.out.println("long:");
        System.out.println("  Low (natural): " + lLow);
        System.out.println("  Low (unsigned): " + Util.unsign(lLow));
        System.out.println("  Middle (natural): " + lMiddle);
        System.out.println("  Middle (unsigned): " + Util.unsign(lMiddle));
        System.out.println("  High (natural): " + lHigh);
        System.out.println("  High (unsigned): " + Util.unsign(lHigh));
    }
}
