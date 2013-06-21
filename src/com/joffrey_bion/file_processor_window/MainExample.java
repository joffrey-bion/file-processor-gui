package com.joffrey_bion.file_processor_window;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainExample {
    
    private static final int NB_ARGS = 2;
    
    private static final int ARG_SOURCE = 0;
    private static final int ARG_DEST = 1;
    
    public static void main(String[] args) {
        if (args.length == 0) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    openWindow();
                }
            });
        } else if (args.length == NB_ARGS) {
            processFile(args[ARG_SOURCE], args[ARG_DEST], new ConsoleLogger());
        } else {
            printUsage();
        }
    }
    
    
    private static void printUsage() {
        System.out.println("Usage: java MainExample <source> <dest>");
    }

    /**
     * Starts the GUI.
     */
    private static void openWindow() {
     // windows system look and feel for the window
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // file pickers source and destination
        JFilePickersPanel filePickers = new JFilePickersPanel("Input file", "Output file");
        @SuppressWarnings("serial")
        JFileProcessorWindow frame = new JFileProcessorWindow("Pseq File Processor", "Process",
                filePickers, null) {
            @Override
            public void process(String[] inPaths, String[] outPaths) {
                processFile(inPaths[0], outPaths[0], this);
            }
        };
        frame.setVisible(true);
    }
    
    private static void processFile(String source, String dest, Logger log) {
        log.clearLog();
        if (source == null || "".equals(source)) {
            log.printErr("No input file selected.");
            return;
        }
        try {
            log.println("Opening '" + source + "'");
            BufferedReader in = new BufferedReader(new FileReader(source));
            String destFilename;
            if (dest == null || "".equals(dest)) {
                log.println("No output file selected.");
                destFilename = generateDestFilename(source);
                log.println("Auto output filename: " + destFilename);
            } else {
                destFilename = dest;
            }
            log.println("Opening/Creating '" + destFilename + "'");
            BufferedWriter out = new BufferedWriter(new FileWriter(destFilename));
            log.println("Processing...");

            // TODO process the file here
            
            log.println("Success.");
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            log.printErr("File not found: " + e.getMessage());
        } catch (IOException e) {
            log.printErr(e.getMessage());
        }
    }

    private static String generateDestFilename(String source) {
        return addBeforeExt(source, "-out");
    }
    
    private static String addBeforeExt(String sourceName, String insert) {
        int extPosition = sourceName.lastIndexOf(".");
        if (extPosition == -1) {
            extPosition = sourceName.length();
        }
        StringBuilder b = new StringBuilder(sourceName.substring(0, extPosition));
        b.append(insert);
        b.append(sourceName.substring(extPosition));
        return b.toString();
    }
}
