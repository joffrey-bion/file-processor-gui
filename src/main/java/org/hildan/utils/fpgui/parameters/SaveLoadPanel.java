package org.hildan.utils.fpgui.parameters;

import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.hildan.utils.fpgui.fpickers.FilePicker;

@SuppressWarnings("serial")
public abstract class SaveLoadPanel extends JPanel {

    private final FilePicker saveFilePicker;

    private final FilePicker loadFilePicker;

    public SaveLoadPanel() {
        this("Save...", "Load...");
    }

    public SaveLoadPanel(String saveBtnText, String loadBtnText) {
        super();
        final FlowLayout flowLayout = (FlowLayout) getLayout();
        flowLayout.setHgap(0);
        flowLayout.setVgap(0);

        final JButton btnSave = new JButton(saveBtnText);
        saveFilePicker = new FilePicker(this, btnSave, FilePicker.MODE_SAVE) {

            @Override
            protected void onSelect() {
                final String paramFilePath = getSelectedFilePath();
                try {
                    saveToFile(paramFilePath);
                } catch (final IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        };
        add(btnSave);

        final JButton btnLoad = new JButton(loadBtnText);
        loadFilePicker = new FilePicker(this, btnLoad, FilePicker.MODE_OPEN) {

            @Override
            protected void onSelect() {
                final String paramFilePath = getSelectedFilePath();
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
