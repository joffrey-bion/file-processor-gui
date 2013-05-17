package com.joffrey_bion.file_processor_window;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class FilePicker {
    private Component parent;

    private JTextField pathTextField;

    private JFileChooser fileChooser;

    private int mode;
    public static final int MODE_OPEN = 1;
    public static final int MODE_SAVE = 2;

    public FilePicker(Component parent, JTextField pathTextField, JButton browseButton, int mode) {
        this.mode = mode;
        this.parent = parent;
        this.fileChooser = new JFileChooser();
        this.pathTextField = pathTextField;
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
    }

    private void buttonActionPerformed(@SuppressWarnings("unused") ActionEvent evt) {
        int userOption = JFileChooser.CANCEL_OPTION;
        if (mode == MODE_OPEN) {
            userOption = fileChooser.showOpenDialog(parent);
        } else if (mode == MODE_SAVE) {
            userOption = fileChooser.showSaveDialog(parent);
        }
        if (userOption == JFileChooser.APPROVE_OPTION) {
            pathTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    public void addFileTypeFilter(String extension, String description) {
        FileTypeFilter filter = new FileTypeFilter(extension, description);
        fileChooser.addChoosableFileFilter(filter);
    }

    public String getSelectedFilePath() {
        return pathTextField.getText();
    }

    public JFileChooser getFileChooser() {
        return this.fileChooser;
    }
    
    private class FileTypeFilter extends FileFilter {
        
        private String extension;
        private String description;
         
        public FileTypeFilter(String extension, String description) {
            this.extension = extension;
            this.description = description;
        }
         
        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }
            return file.getName().toLowerCase().endsWith(extension);
        }
         
        @Override
        public String getDescription() {
            return description + String.format(" (*%s)", extension);
        }
    }
}
