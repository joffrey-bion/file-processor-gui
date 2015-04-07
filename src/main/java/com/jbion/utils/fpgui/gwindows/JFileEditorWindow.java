package com.jbion.utils.fpgui.gwindows;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import com.jbion.utils.fpgui.logging.JLoggerWindow;
import com.jbion.utils.fpgui.parameters.SaveLoadPanel;

@SuppressWarnings("serial")
public abstract class JFileEditorWindow extends JLoggerWindow {

    public JFileEditorWindow(String title, JPanel content) {
        super(title);
        JPanel paramPanel = new JPanel();
        paramPanel.setLayout(new BoxLayout(paramPanel, BoxLayout.Y_AXIS));
        paramPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        if (content != null) {
            paramPanel.add(content);
            paramPanel.add(Box.createVerticalStrut(5));
            paramPanel.add(new JSeparator());
            paramPanel.add(Box.createVerticalStrut(5));
        }
        paramPanel.add(new SaveLoadPanel() {
            @Override
            public void saveToFile(String filePath) {
                JFileEditorWindow.this.saveToFile(filePath);
            }

            @Override
            public void loadFromFile(String filePath) {
                JFileEditorWindow.this.loadFromFile(filePath);
            }
        });
        setContent(paramPanel);
    }

    public abstract void saveToFile(String filePath);

    public abstract void loadFromFile(String filePath);
}
