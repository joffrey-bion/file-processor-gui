package com.joffrey_bion.file_processor_window.parameters;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.joffrey_bion.file_processor_window.file_picker.FilePicker;

@SuppressWarnings("serial")
public abstract class ParamsSaverPanel extends JPanel {

    public ParamsSaverPanel() {
        super();
        FlowLayout flowLayout_1 = (FlowLayout) getLayout();
        flowLayout_1.setHgap(0);
        flowLayout_1.setVgap(0);

        JButton btnSave = new JButton("Save params...");
        FilePicker saveFilePicker = new FilePicker(this, btnSave, FilePicker.MODE_SAVE) {
            @Override
            protected void onSelect() {
                String paramFilePath = getSelectedFilePath();
                loadParamsFromFile(paramFilePath);
            }
        };
        saveFilePicker.addFileTypeFilter(".xml", "XML Parameter File");
        add(btnSave);

        JButton btnLoad = new JButton("Load params...");
        FilePicker loadFilePicker = new FilePicker(this, btnLoad, FilePicker.MODE_OPEN) {
            @Override
            protected void onSelect() {
                String paramFilePath = getSelectedFilePath();
                loadParamsFromFile(paramFilePath);
            }
        };
        loadFilePicker.addFileTypeFilter(".xml", "XML Parameter File");
        add(btnLoad);
    }

    public abstract void saveParamsToFile(String filePath);

    public abstract void loadParamsFromFile(String filePath);

}
