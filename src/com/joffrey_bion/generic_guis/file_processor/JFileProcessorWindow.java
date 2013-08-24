package com.joffrey_bion.generic_guis.file_processor;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSeparator;

import com.joffrey_bion.generic_guis.file_picker.JFilePickersPanel;
import com.joffrey_bion.generic_guis.logging.JLoggerWindow;

@SuppressWarnings("serial")
public abstract class JFileProcessorWindow extends JLoggerWindow {

    public JFileProcessorWindow(String title, String processBtnText,
            final JFilePickersPanel filePickers, JPanel parameters) {
        super(title);
        JPanel paramPanel = new JPanel();
        paramPanel.setLayout(new BoxLayout(paramPanel, BoxLayout.Y_AXIS));
        paramPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        if (filePickers != null) {
            paramPanel.add(filePickers);
            paramPanel.add(Box.createVerticalStrut(5));
            paramPanel.add(new JSeparator());
            paramPanel.add(Box.createVerticalStrut(5));
        }
        if (parameters != null) {
            paramPanel.add(parameters);
            paramPanel.add(Box.createVerticalStrut(5));
            paramPanel.add(new JSeparator());
            paramPanel.add(Box.createVerticalStrut(5));
        }

        // process start button
        JButton btnProcess = new JButton(processBtnText);
        btnProcess.setAlignmentX(Component.CENTER_ALIGNMENT);
        paramPanel.add(btnProcess);
        btnProcess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        if (filePickers == null) {
                            process(null, null);
                        } else {
                            process(filePickers.getInputFilePaths(), filePickers
                                    .getOutputFilePaths());
                        }
                    }
                }.run();
            }
        });
        setContent(paramPanel);
    }

    public abstract void process(String[] inFilesPaths, String[] outFilesPaths);

}
