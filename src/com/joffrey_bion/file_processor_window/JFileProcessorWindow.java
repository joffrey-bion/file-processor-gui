package com.joffrey_bion.file_processor_window;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
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

import com.joffrey_bion.file_processor_window.file_picker.JFilePickersPanel;
import com.joffrey_bion.file_processor_window.logging.PrintStreamCapturer;
import javax.swing.JSplitPane;

@SuppressWarnings("serial")
public abstract class JFileProcessorWindow extends JFrame {

    private JPanel contentPane;
    private JTextArea logTextArea;

    public static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public JFileProcessorWindow(String title, String processBtnText,
            final JFilePickersPanel filePickers, JPanel parameters) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        if (filePickers != null) {
            panel.add(filePickers);
            panel.add(Box.createVerticalStrut(5));
            panel.add(new JSeparator());
            panel.add(Box.createVerticalStrut(5));
        }
        if (parameters != null) {
            panel.add(parameters);
            panel.add(Box.createVerticalStrut(5));
            panel.add(new JSeparator());
            panel.add(Box.createVerticalStrut(5));
        }

        // process start button
        JButton btnProcess = new JButton(processBtnText);
        btnProcess.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(btnProcess);
        btnProcess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process(filePickers.getInputFilePaths(), filePickers.getOutputFilePaths());
            }
        });

        // log area
        JPanel logPanel = new JPanel();
        logPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        logPanel.setLayout(new BorderLayout(0, 0));
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setRows(8);
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        logPanel.add(scrollPane, BorderLayout.CENTER);
        System.setOut(new PrintStreamCapturer(logTextArea, System.out));
        System.setErr(new PrintStreamCapturer(logTextArea, System.err, "[ERROR] "));

        JSplitPane splitPane = new JSplitPane();
        splitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(panel);
        splitPane.setBottomComponent(logPanel);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(splitPane);

    }

    public abstract void process(String[] inFilesPaths, String[] outFilesPaths);

    public void clearLog() {
        logTextArea.setText("");
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
    }
}
