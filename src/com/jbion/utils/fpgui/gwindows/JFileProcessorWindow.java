package com.jbion.utils.fpgui.gwindows;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSeparator;

import com.jbion.utils.fpgui.fpickers.JFilePickersPanel;
import com.jbion.utils.fpgui.logging.JLoggerWindow;

import java.awt.FlowLayout;

@SuppressWarnings("serial")
public abstract class JFileProcessorWindow extends JLoggerWindow {

    public JFileProcessorWindow(String title, final JFilePickersPanel filePickers,
            JPanel parameters, String... processBtnText) {
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

        JPanel processBtnsPanel = new JPanel();
        paramPanel.add(processBtnsPanel);
        processBtnsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        // process start button
        JButton[] btnProcess = new JButton[processBtnText.length];
        for (int i = 0; i < processBtnText.length; i++) {
            btnProcess[i] = new JButton(processBtnText[i]);
            btnProcess[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            processBtnsPanel.add(btnProcess[i]);
            btnProcess[i].addActionListener(new ProcessListener(filePickers, i));
        }
        setContent(paramPanel);

    }

    private class ProcessListener implements ActionListener {
        private JFilePickersPanel filePickers;
        private int btnIndex;

        public ProcessListener(final JFilePickersPanel filePickers, int btnIndex) {
            this.filePickers = filePickers;
            this.btnIndex = btnIndex;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread() {
                @Override
                public void run() {
                    if (filePickers == null) {
                        process(null, null, btnIndex);
                    } else {
                        process(filePickers.getInputFilePaths(), filePickers.getOutputFilePaths(),
                                btnIndex);
                    }
                }
            }.run();
        }

    }

    public abstract void process(String[] inFilesPaths, String[] outFilesPaths, int processBtnIndex);

}
