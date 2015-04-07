package com.jbion.utils.fpgui.logging;

import java.io.PrintStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class PrintStreamCapturer extends PrintStream {

    private final JTextArea text;

    private boolean atLineStart;

    private final String indent;

    public PrintStreamCapturer(JTextArea textArea, PrintStream capturedStream, String prefix) {
        super(capturedStream);
        this.text = textArea;
        this.indent = prefix;
        this.atLineStart = true;
    }

    public PrintStreamCapturer(JTextArea textArea, PrintStream capturedStream) {
        this(textArea, capturedStream, "");
    }

    private void writeToTextArea(final String str) {
        if (text != null) {
            SwingUtilities.invokeLater(() -> {
                synchronized (text) {
                    text.setCaretPosition(text.getDocument().getLength());
                    text.append(str);
                }
            });

        }
    }

    private void write(String str) {
        final String[] s = str.split("\n", -1);
        if (s.length == 0) {
            return;
        }
        for (int i = 0; i < s.length - 1; i++) {
            writeWithPotentialIndent(s[i]);
            writeWithPotentialIndent("\n");
            atLineStart = true;
        }
        final String last = s[s.length - 1];
        if (!last.equals("")) {
            writeWithPotentialIndent(last);
        }
    }

    private void writeWithPotentialIndent(String s) {
        if (atLineStart) {
            writeToTextArea(indent + s);
            atLineStart = false;
        } else {
            writeToTextArea(s);
        }
    }

    private void newLine() {
        write("\n");
    }

    @Override
    public void print(boolean b) {
        synchronized (this) {
            super.print(b);
            write(String.valueOf(b));
        }
    }

    @Override
    public void print(char c) {
        synchronized (this) {
            super.print(c);
            write(String.valueOf(c));
        }
    }

    @Override
    public void print(char[] s) {
        synchronized (this) {
            super.print(s);
            write(String.valueOf(s));
        }
    }

    @Override
    public void print(double d) {
        synchronized (this) {
            super.print(d);
            write(String.valueOf(d));
        }
    }

    @Override
    public void print(float f) {
        synchronized (this) {
            super.print(f);
            write(String.valueOf(f));
        }
    }

    @Override
    public void print(int i) {
        synchronized (this) {
            super.print(i);
            write(String.valueOf(i));
        }
    }

    @Override
    public void print(long l) {
        synchronized (this) {
            super.print(l);
            write(String.valueOf(l));
        }
    }

    @Override
    public void print(Object o) {
        synchronized (this) {
            super.print(o);
            write(String.valueOf(o));
        }
    }

    @Override
    public void print(String s) {
        synchronized (this) {
            super.print(s);
            if (s == null) {
                write("null");
            } else {
                write(s);
            }
        }
    }

    @Override
    public void println() {
        synchronized (this) {
            newLine();
            super.println();
        }
    }

    @Override
    public void println(boolean x) {
        synchronized (this) {
            print(x);
            newLine();
            super.println();
        }
    }

    @Override
    public void println(char x) {
        synchronized (this) {
            print(x);
            newLine();
            super.println();
        }
    }

    @Override
    public void println(int x) {
        synchronized (this) {
            print(x);
            newLine();
            super.println();
        }
    }

    @Override
    public void println(long x) {
        synchronized (this) {
            print(x);
            newLine();
            super.println();
        }
    }

    @Override
    public void println(float x) {
        synchronized (this) {
            print(x);
            newLine();
            super.println();
        }
    }

    @Override
    public void println(double x) {
        synchronized (this) {
            print(x);
            newLine();
            super.println();
        }
    }

    @Override
    public void println(char x[]) {
        synchronized (this) {
            print(x);
            newLine();
            super.println();
        }
    }

    @Override
    public void println(String x) {
        synchronized (this) {
            print(x);
            newLine();
            super.println();
        }
    }

    @Override
    public void println(Object x) {
        final String s = String.valueOf(x);
        synchronized (this) {
            print(s);
            newLine();
            super.println();
        }
    }
}
