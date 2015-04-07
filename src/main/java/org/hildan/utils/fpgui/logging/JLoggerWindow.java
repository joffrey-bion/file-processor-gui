package org.hildan.utils.fpgui.logging;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class JLoggerWindow extends JFrame {

    private static final String ERROR_PREFIX = "[ERROR] ";

    private final JPanel contentPane;

    private final JPanel paramPanel;

    private final JTextArea logTextArea;

    public JLoggerWindow(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        paramPanel = new JPanel();
        paramPanel.setLayout(new BoxLayout(paramPanel, BoxLayout.Y_AXIS));
        paramPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        // log area
        final JPanel logPanel = new JPanel();
        logPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        logPanel.setLayout(new BorderLayout(0, 0));
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setRows(8);
        final JScrollPane scrollPane = new JScrollPane(logTextArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        logPanel.add(scrollPane, BorderLayout.CENTER);

        final JSplitPane splitPane = new JSplitPane();
        splitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(paramPanel);
        splitPane.setBottomComponent(logPanel);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(splitPane);

        System.setOut(new PrintStreamCapturer(logTextArea, System.out));
        System.setErr(new PrintStreamCapturer(logTextArea, System.err, ERROR_PREFIX));
    }

    public void clearLog() {
        logTextArea.setText("");
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
    }

    protected void setContent(JPanel contentPanel) {
        paramPanel.removeAll();
        paramPanel.add(contentPanel);
    }
}
