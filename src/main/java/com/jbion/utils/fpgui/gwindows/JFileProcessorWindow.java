package com.jbion.utils.fpgui.gwindows;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import com.jbion.utils.fpgui.fpickers.JFilePickersPanel;
import com.jbion.utils.fpgui.logging.JLoggerWindow;

@SuppressWarnings("serial")
public abstract class JFileProcessorWindow extends JLoggerWindow {

    public JFileProcessorWindow(String title, final JFilePickersPanel filePickers, JPanel parameters,
            String... processBtnText) {
        super(title);
        final JPanel paramPanel = new JPanel();
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

        final JPanel processBtnsPanel = new JPanel();
        paramPanel.add(processBtnsPanel);
        processBtnsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        // process start button
        final JButton[] btnProcess = new JButton[processBtnText.length];
        for (int i = 0; i < processBtnText.length; i++) {
            btnProcess[i] = new JButton(processBtnText[i]);
            btnProcess[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            processBtnsPanel.add(btnProcess[i]);
            btnProcess[i].addActionListener(new ProcessListener(filePickers, i));
        }
        setContent(paramPanel);

    }

    private class ProcessListener implements ActionListener {

        private final JFilePickersPanel filePickers;

        private final int btnIndex;

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
                        process(filePickers.getInputFilePaths(), filePickers.getOutputFilePaths(), btnIndex);
                    }
                }
            }.run();
        }

    }

    public abstract void process(String[] inFilesPaths, String[] outFilesPaths, int processBtnIndex);

}
