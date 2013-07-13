package com.joffrey_bion.file_processor_window.logging;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;

public class StreamCapturer extends OutputStream {

    private StringBuilder buffer;
    private JTextArea textArea;
    private PrintStream old;

    public StreamCapturer(JTextArea textArea, PrintStream capturedStream) {
        this.textArea = textArea;
        this.old = capturedStream;
        this.buffer = new StringBuilder(128);
    }

    @Override
    public void write(int b) throws IOException {
        buffer.append(Character.toChars((b + 256) % 256));
        if ((char) b == '\n') {
            writeToTextArea(buffer.toString());
            buffer.delete(0, buffer.length());
        }
        old.write(b);
    }

    private void writeToTextArea(String str) {
        if (textArea != null) {
            textArea.append(str);
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }
}
