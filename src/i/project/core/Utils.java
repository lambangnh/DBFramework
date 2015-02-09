/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package i.project.core;

import javax.swing.JOptionPane;

/**
 *
 * @author auliayf
 */
public class Utils {
    /**
     * Alert Box Builder
     * @param infoMessage   Box Message
     * @param titleBar      Title
     */
    public static void alertBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.WARNING_MESSAGE);
    }
}
