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

package org.catacombae.util;

import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Utility functions that the GUI designer can use to ease his/her existential suffering.
 * 
 * @author <a href="https://catacombae.org" target="_top">Erik Larsson</a>
 */
public class GUIUtil {
    /**
     * Displays an exception dialog with a stack trace. Convenience method that omits a user defined
     * message and sets the default window title to "Exception" and the message type to
     * JOptionPane.ERROR_MESSAGE. Maximum lines shown in the stack trace is set to 10.
     * 
     * @see #displayExceptionDialog(Throwable, int, Component, String, String, int)
     * @param t the exception thrown.
     * @param c the dialog's parent component.
     */
    public static void displayExceptionDialog(Throwable t, Component c) {
        displayExceptionDialog(t, 10, c, "", "Exception", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Displays an exception dialog with a stack trace. Convenience method that omits a user defined
     * message and sets the default window title to "Exception" and the message type to
     * JOptionPane.ERROR_MESSAGE.
     * 
     * @see #displayExceptionDialog(Throwable, int, Component, String, String, int)
     * @param t the exception thrown.
     * @param maxStackTraceLines the maximum number of lines of the stack trace to display.
     * @param c the dialog's parent component.
     */
    public static void displayExceptionDialog(Throwable t, int maxStackTraceLines, Component c) {
        displayExceptionDialog(t, maxStackTraceLines, c, "", "Exception", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Displays an exception dialog with a stack trace. Convenience method that sets the default
     * window title to "Exception" and the message type to JOptionPane.ERROR_MESSAGE.
     * 
     * @see #displayExceptionDialog(Throwable, int, Component, String, String, int)
     * @param t the exception thrown.
     * @param maxStackTraceLines the maximum number of lines of the stack trace to display.
     * @param c the dialog's parent component.
     * @param message the message to be printed above the exception.
     */
    public static void displayExceptionDialog(Throwable t, int maxStackTraceLines, Component c,
            String message) {
        displayExceptionDialog(t, maxStackTraceLines, c, message, "Exception", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Displays an exception dialog with a stack trace.
     * 
     * @param t the exception thrown.
     * @param maxStackTraceLines the maximum number of lines of the stack trace to display.
     * @param c the dialog's parent component.
     * @param message the message to be printed above the exception.
     * @param title the dialog's title.
     * @param messageType the dialog's message type (JOptionPane.ERROR_MESSAGE,
     * JOptionPane.INFORMATION_MESSAGE, etc.).
     */
    public static void displayExceptionDialog(final Throwable t, final int maxStackTraceLines,
            final Component c, final String message, final String title, final int messageType) {
        StringBuilder sb = new StringBuilder();
        if(message.length() > 0) {
            sb.append(message);
            sb.append("\n\n");
        }
        
        Util.buildStackTrace(t, maxStackTraceLines, sb);
        
        final String finalMessage = sb.toString();
        try {
            Runnable r = new Runnable() {
                //@Override
                public void run() {
                    JOptionPane.showMessageDialog(c, finalMessage, title, messageType);
                }
            };
            if(SwingUtilities.isEventDispatchThread())
                r.run();
            else
                SwingUtilities.invokeAndWait(r);
        } catch(Exception e) {
            throw new RuntimeException("Exception during invokeAndWait!", e);
        }
        
    }
}
