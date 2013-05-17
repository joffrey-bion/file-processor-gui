package com.joffrey_bion.file_processor_window;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.BevelBorder;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public abstract class JFileProcessorWindow extends JFrame implements Logger {

    private JPanel contentPane;
    private JTextArea logTextArea;

    public JFileProcessorWindow(String title, String processBtnText,
            final JFilePickersPanel filePickers, JPanel parameters) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        if (filePickers != null) {
            contentPane.add(filePickers);
            contentPane.add(Box.createVerticalStrut(5));
            contentPane.add(new JSeparator());
            contentPane.add(Box.createVerticalStrut(5));
        }
        if (parameters != null) {
            contentPane.add(parameters);
            contentPane.add(Box.createVerticalStrut(5));
            contentPane.add(new JSeparator());
            contentPane.add(Box.createVerticalStrut(5));
        }

        // process start button
        JButton btnNewButton = new JButton(processBtnText);
        btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process(filePickers.getInputFilePaths(), filePickers.getOutputFilePaths());
            }
        });
        contentPane.add(Box.createVerticalStrut(5));

        // log area
        JPanel logPanel = new JPanel();
        logPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        logPanel.setLayout(new BorderLayout(0, 0));
        contentPane.add(logPanel);
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setRows(8);
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        logPanel.add(scrollPane, BorderLayout.CENTER);
    }

    public abstract void process(String[] inFilesPaths, String[] outFilesPaths);

    @Override
    public void println(String line) {
        logTextArea.append(line + "\n");
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
    }

    @Override
    public void printErr(String line) {
        println(line);
    }

    @Override
    public void clearLog() {
        logTextArea.setText("");
    }
}
