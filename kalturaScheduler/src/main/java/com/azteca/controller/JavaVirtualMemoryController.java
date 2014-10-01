package com.azteca.controller;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Santa
 */
public class JavaVirtualMemoryController {

    private static final Runtime runtime = Runtime.getRuntime();

    public static String totalMemory() {
        return String.valueOf(runtime.totalMemory() / 1048576);
    }

    public static String freeMemory() {
        return String.valueOf(runtime.freeMemory() / 1048576);
    }

    public static String usedMemory() {
        return String.valueOf((runtime.totalMemory() / 1048576) - (runtime.freeMemory() / 1048576));
    }

    public static String msgComplete() {
        return "Memoria utilizada " + JavaVirtualMemoryController.usedMemory() + " Memoria libre " + JavaVirtualMemoryController.freeMemory() + " Memoria total " + JavaVirtualMemoryController.totalMemory();
    }
}
