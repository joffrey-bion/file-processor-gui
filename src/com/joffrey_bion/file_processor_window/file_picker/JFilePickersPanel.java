package com.joffrey_bion.file_processor_window.file_picker;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

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
        ColumnSpec[] colSpec = new ColumnSpec[] { FormFactory.MIN_COLSPEC,
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("120px:grow"),
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.MIN_COLSPEC, };
        RowSpec[] rowSpec = new RowSpec[2 * (nbInFiles + nbOutFiles) - 1];
        for (int i = 0; i < rowSpec.length; i++) {
            if (i % 2 == 0) {
                rowSpec[i] = FormFactory.MIN_ROWSPEC;
            } else {
                rowSpec[i] = FormFactory.RELATED_GAP_ROWSPEC;
            }
        }
        setLayout(new FormLayout(colSpec, rowSpec));
        inFilePickers = new FilePicker[nbInFiles];
        inLabels = new JLabel[nbInFiles];
        inTFPaths = new JTextField[nbInFiles];
        inBrowseBtns = new JButton[nbInFiles];
        for (int i = 0; i < nbInFiles; i++) {
            inLabels[i] = new JLabel(inFilesTitles[i] + ":");
            add(inLabels[i], "1, " + (2 * i + 1) + ", left, center");
            inTFPaths[i] = new JTextField(30);
            add(inTFPaths[i], "3, " + (2 * i + 1) + ", fill, center");
            inBrowseBtns[i] = new JButton("Browse...");
            add(inBrowseBtns[i], "5, " + (2 * i + 1) + ", fill, center");
            inFilePickers[i] = new FilePicker(this, inTFPaths[i], inBrowseBtns[i],
                    FilePicker.MODE_OPEN);
        }
        outFilePickers = new FilePicker[nbOutFiles];
        outLabels = new JLabel[nbOutFiles];
        outTFPaths = new JTextField[nbOutFiles];
        outBrowseBtns = new JButton[nbOutFiles];
        for (int i = 0; i < nbOutFiles; i++) {
            outLabels[i] = new JLabel(outFilesTitles[i] + ":");
            add(outLabels[i], "1, " + (2 * (nbInFiles + i) + 1) + ", left, center");
            outTFPaths[i] = new JTextField(30);
            add(outTFPaths[i], "3, " + (2 * (nbInFiles + i) + 1) + ", fill, center");
            outBrowseBtns[i] = new JButton("Browse...");
            add(outBrowseBtns[i], "5, " + (2 * (nbInFiles + i) + 1) + ", fill, center");
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
