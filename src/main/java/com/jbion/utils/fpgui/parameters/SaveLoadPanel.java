package com.jbion.utils.fpgui.parameters;

import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.jbion.utils.fpgui.fpickers.FilePicker;

@SuppressWarnings("serial")
public abstract class SaveLoadPanel extends JPanel {

    private FilePicker saveFilePicker;
    private FilePicker loadFilePicker;

    public SaveLoadPanel() {
        this("Save...", "Load...");
    }

    public SaveLoadPanel(String saveBtnText, String loadBtnText) {
        super();
        FlowLayout flowLayout = (FlowLayout) getLayout();
        flowLayout.setHgap(0);
        flowLayout.setVgap(0);

        JButton btnSave = new JButton(saveBtnText);
        saveFilePicker = new FilePicker(this, btnSave, FilePicker.MODE_SAVE) {
            @Override
            protected void onSelect() {
                String paramFilePath = getSelectedFilePath();
                try {
                    saveToFile(paramFilePath);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        };
        add(btnSave);

        JButton btnLoad = new JButton(loadBtnText);
        loadFilePicker = new FilePicker(this, btnLoad, FilePicker.MODE_OPEN) {
            @Override
            protected void onSelect() {
                String paramFilePath = getSelectedFilePath();
                loadFromFile(paramFilePath);
            }
        };
        add(btnLoad);
    }
    
    public void addFileTypeFilter(String extension, String description) {
        loadFilePicker.addFileTypeFilter(extension, description);
        saveFilePicker.addFileTypeFilter(extension, description);
    }

    public abstract void saveToFile(String filePath) throws IOException;

    public abstract void loadFromFile(String filePath);

}
