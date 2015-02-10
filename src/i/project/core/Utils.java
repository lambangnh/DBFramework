/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i.project.core;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author auliayf
 */
public class Utils {

    /**
     * Alert Box Builder
     *
     * @param infoMessage Box Message
     * @param titleBar Title
     */
    public static void alertBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Implodes and combines the keys and the values
     *
     * @param map Base map
     * @param delimiter Delimiter
     * @param param Tags
     * @return Imploded map
     */
    public static String implode_kv(HashMap<String, String> map, String delimiter, String[] param) {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!first) {
                builder.append(delimiter);
            }
            builder.append(entry.getKey() + param[0] + entry.getValue() + param[1]);
            first = false;
        }
        return builder.toString();
    }

    /**
     * Implodes and combines the keys
     *
     * @param map Base Map
     * @param delimiter Delimiter
     * @return Imploded map
     */
    public static String implode_k(HashMap<String, String> map, String delimiter) {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!first) {
                builder.append(delimiter);
            }
            builder.append(entry.getKey());
            first = false;
        }
        return builder.toString();
    }

    /**
     * Implodes and combines the values
     *
     * @param map Base map
     * @param delimiter Delimiter
     * @param tag Tag
     * @return Imploded map
     */
    public static String implode_v(HashMap<String, String> map, String delimiter, Object tag) {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!first) {
                builder.append(delimiter);
            }
            builder.append(tag + entry.getValue() + tag);
            first = false;
        }
        return builder.toString();
    }
}
