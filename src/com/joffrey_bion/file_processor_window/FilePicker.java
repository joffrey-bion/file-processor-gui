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
    public static final int MODE_OPEN = 1;
    public static final int MODE_SAVE = 2;

    private Component parent;
    private JTextField pathTextField;
    private JFileChooser fileChooser;
    private int mode;

    /**
     * Associates a {@link JFileChooser} to the specified button, in the specified
     * mode.
     * 
     * @param parent
     *            The parent {@link Component} used to open the file chooser window
     *            from.
     * @param browseButton
     *            The {@link JButton} triggering the file chooser.
     * @param mode
     *            One of {@link #MODE_OPEN} or {@link #MODE_SAVE}.
     */
    public FilePicker(Component parent, JButton browseButton, int mode) {
        this(parent, null, browseButton, mode);
    }

    /**
     * Associates a {@link JFileChooser} to the specified button and text field, in
     * the specified mode.
     * 
     * @param parent
     *            The parent {@link Component} used to open the file chooser window
     *            from.
     * @param pathTextField
     *            A text field to allow the user to enter manually the file's path.
     *            This text field is updated if the user uses the browse button.
     * @param browseButton
     *            The {@link JButton} triggering the file chooser.
     * @param mode
     *            One of {@link #MODE_OPEN} or {@link #MODE_SAVE}.
     */
    public FilePicker(Component parent, JTextField pathTextField, JButton browseButton, int mode) {
        this.mode = mode;
        this.parent = parent;
        this.fileChooser = new JFileChooser();
        this.pathTextField = pathTextField;
        if (browseButton == null)
            throw new IllegalArgumentException("browseButton can't be null");
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
        if (userOption == JFileChooser.APPROVE_OPTION && pathTextField != null) {
            pathTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
        onSelect();
    }
    
    protected void onSelect() {
        // nothing by default
    }

    public void addFileTypeFilter(String extension, String description) {
        FileTypeFilter filter = new FileTypeFilter(extension, description);
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);
    }

    public String getSelectedFilePath() {
        if (pathTextField == null) {
            File file = fileChooser.getSelectedFile();
            if (file == null)
                return "";
            return file.getAbsolutePath();
        } else {
            return pathTextField.getText();
        }
    }

    public void setSelectedFilePath(String path) {
        if (pathTextField == null) {
            fileChooser.setSelectedFile(new File(path));
        } else {
            pathTextField.setText(path);
        }
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
