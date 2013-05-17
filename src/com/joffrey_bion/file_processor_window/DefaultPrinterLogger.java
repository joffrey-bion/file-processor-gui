package com.joffrey_bion.file_processor_window;

public class DefaultPrinterLogger implements Logger {

    @Override
    public void println(String line) {
        System.out.println(line);
    }

    @Override
    public void printErr(String line) {
        System.err.println(line);
    }

    @Override
    public void clearLog() {
        // nothing in the console
    }

}
