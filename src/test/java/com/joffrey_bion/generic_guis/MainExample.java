package com.joffrey_bion.generic_guis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.SwingUtilities;

import org.hildan.utils.fpgui.LookAndFeel;
import org.hildan.utils.fpgui.fpickers.JFilePickersPanel;
import org.hildan.utils.fpgui.gwindows.JFileProcessorWindow;

public class MainExample {

    private static final int NB_ARGS = 2;

    private static final int ARG_SOURCE = 0;

    private static final int ARG_DEST = 1;

    public static void main(String[] args) {
        if (args.length == 0) {
            SwingUtilities.invokeLater(() -> openWindow());
        } else if (args.length == NB_ARGS) {
            processFile(args[ARG_SOURCE], args[ARG_DEST]);
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
        LookAndFeel.setSystemLookAndFeel();
        // file pickers source and destination
        final JFilePickersPanel filePickers = new JFilePickersPanel("Input file", "Output file");
        @SuppressWarnings("serial")
        final JFileProcessorWindow frame = new JFileProcessorWindow("Example", filePickers, null, "Process") {

            @Override
            public void process(String[] inPaths, String[] outPaths, int processBtnIndex) {
                clearLog();
                processFile(inPaths[0], outPaths[0]);
            }
        };
        frame.setVisible(true);
    }

    private static void processFile(String source, String dest) {
        if (source == null || "".equals(source)) {
            System.err.println("No input file selected.");
            return;
        }
        String destFilename;
        if (dest == null || "".equals(dest)) {
            System.out.println("No output file selected.");
            destFilename = generateDestFilename(source);
            System.out.println("Auto output filename: " + destFilename);
        } else {
            destFilename = dest;
        }
        try (BufferedReader in = new BufferedReader(new FileReader(source));
                BufferedWriter out = new BufferedWriter(new FileWriter(destFilename))) {
            System.out.println("Processing...");

            // process the files here

            System.out.println("Success.");
        } catch (final FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (final IOException e) {
            System.err.println(e.getMessage());
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
        final StringBuilder b = new StringBuilder(sourceName.substring(0, extPosition));
        b.append(insert);
        b.append(sourceName.substring(extPosition));
        return b.toString();
    }
}
