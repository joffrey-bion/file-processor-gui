package com.joffrey_bion.file_processor_window.file_picker;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class JFilePickersPanel extends JPanel {

    private FilePicker[] inFilePickers;
    private FilePicker[] outFilePickers;
    private JLabel[] inLabels;
    private JLabel[] outLabels;
    private JTextField[] inTFPaths;
    private JTextField[] outTFPaths;
    private JButton[] inBrowseBtns;
    private JButton[] outBrowseBtns;

    public JFilePickersPanel() {
        this("Input", "Output");
    }

    public JFilePickersPanel(String inFileTitle, String outFileTitle) {
        this(new String[] { inFileTitle }, new String[] { outFileTitle });
    }

    public JFilePickersPanel(String[] inFilesTitles, String outFileTitle) {
        this(inFilesTitles, new String[] { outFileTitle });
    }

    public JFilePickersPanel(String[] inFilesTitles, String[] outFilesTitles) {
        int nbInFiles = inFilesTitles.length;
        int nbOutFiles = outFilesTitles.length;
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 0, 0, 0 };
        gbl.rowHeights = new int[] { 0 };
        gbl.columnWeights = new double[] { 0.0, 1.0, 0.0 };
        gbl.rowWeights = new double[] { 0.0 };
        setLayout(gbl);
        
        inFilePickers = new FilePicker[nbInFiles];
        inLabels = new JLabel[nbInFiles];
        inTFPaths = new JTextField[nbInFiles];
        inBrowseBtns = new JButton[nbInFiles];
        for (int i = 0; i < nbInFiles; i++) {
            inLabels[i] = new JLabel(inFilesTitles[i] + ":");
            GridBagConstraints gbcLabel = new GridBagConstraints();
            gbcLabel.anchor = GridBagConstraints.WEST;
            gbcLabel.insets = new Insets(0, 0, 2, 5);
            gbcLabel.gridx = 0;
            gbcLabel.gridy = i;
            add(inLabels[i], gbcLabel);
            
            inTFPaths[i] = new JTextField(30);
            GridBagConstraints gbcPath = new GridBagConstraints();
            gbcPath.anchor = GridBagConstraints.CENTER;
            gbcPath.fill = GridBagConstraints.HORIZONTAL;
            gbcPath.insets = new Insets(0, 0, 2, 5);
            gbcPath.gridx = 1;
            gbcPath.gridy = i;
            add(inTFPaths[i], gbcPath);
            
            inBrowseBtns[i] = new JButton("Browse...");
            GridBagConstraints gbcBtn = new GridBagConstraints();
            gbcBtn.anchor = GridBagConstraints.CENTER;
            gbcBtn.insets = new Insets(0, 0, 2, 0);
            gbcBtn.gridx = 2;
            gbcBtn.gridy = i;
            add(inBrowseBtns[i], gbcBtn);
            
            inFilePickers[i] = new FilePicker(this, inTFPaths[i], inBrowseBtns[i],
                    FilePicker.MODE_OPEN);
        }
        outFilePickers = new FilePicker[nbOutFiles];
        outLabels = new JLabel[nbOutFiles];
        outTFPaths = new JTextField[nbOutFiles];
        outBrowseBtns = new JButton[nbOutFiles];
        for (int i = 0; i < nbOutFiles; i++) {
            outLabels[i] = new JLabel(outFilesTitles[i] + ":");
            GridBagConstraints gbcLabel = new GridBagConstraints();
            gbcLabel.anchor = GridBagConstraints.WEST;
            gbcLabel.insets = new Insets(0, 0, 2, 5);
            gbcLabel.gridx = 0;
            gbcLabel.gridy = i + nbInFiles;
            add(outLabels[i], gbcLabel);
            
            outTFPaths[i] = new JTextField(30);
            GridBagConstraints gbcPath = new GridBagConstraints();
            gbcPath.anchor = GridBagConstraints.CENTER;
            gbcPath.fill = GridBagConstraints.HORIZONTAL;
            gbcPath.insets = new Insets(0, 0, 2, 5);
            gbcPath.gridx = 1;
            gbcPath.gridy = i + nbInFiles;
            add(outTFPaths[i], gbcPath);
            
            outBrowseBtns[i] = new JButton("Browse...");
            GridBagConstraints gbcBtn = new GridBagConstraints();
            gbcBtn.anchor = GridBagConstraints.CENTER;
            gbcBtn.insets = new Insets(0, 0, 2, 0);
            gbcBtn.gridx = 2;
            gbcBtn.gridy = i + nbInFiles;
            add(outBrowseBtns[i], gbcBtn);
            
            outFilePickers[i] = new FilePicker(this, outTFPaths[i], outBrowseBtns[i],
                    FilePicker.MODE_SAVE);
        }
    }

    public FilePicker[] getInputFilePickers() {
        return inFilePickers;
    }

    public FilePicker[] getOutputFilePickers() {
        return outFilePickers;
    }

    private static String[] getSelectedPaths(FilePicker[] filePickers) {
        String[] inputPaths = new String[filePickers.length];
        for (int i = 0; i < inputPaths.length; i++) {
            inputPaths[i] = filePickers[i].getSelectedFilePath();
        }
        return inputPaths;
    }

    public String[] getInputFilePaths() {
        return getSelectedPaths(inFilePickers);
    }

    public String[] getOutputFilePaths() {
        return getSelectedPaths(outFilePickers);
    }

    public void setInputFilePickerEditable(int filePickerIndex, boolean editable) {
        inTFPaths[filePickerIndex].setEditable(editable);
        inBrowseBtns[filePickerIndex].setEnabled(editable);
    }

    public void setOutputFilePickerEditable(int filePickerIndex, boolean editable) {
        outTFPaths[filePickerIndex].setEditable(editable);
        outBrowseBtns[filePickerIndex].setEnabled(editable);
    }

    public void setInputFilePickerEnabled(int filePickerIndex, boolean enabled) {
        inLabels[filePickerIndex].setEnabled(enabled);
        inTFPaths[filePickerIndex].setEnabled(enabled);
        inBrowseBtns[filePickerIndex].setEnabled(enabled);
    }

    public void setOutputFilePickerEnabled(int filePickerIndex, boolean enabled) {
        outLabels[filePickerIndex].setEnabled(enabled);
        outTFPaths[filePickerIndex].setEnabled(enabled);
        outBrowseBtns[filePickerIndex].setEnabled(enabled);
    }
}
