package com.joffrey_bion.file_processor_window;
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

    public JFilePickersPanel(String inFileTitle, String outFileTitle) {
        this(new String[] {inFileTitle}, new String[] {outFileTitle});
    }

    public JFilePickersPanel(String[] inFilesTitles, String outFileTitle) {
        this(inFilesTitles, new String[] {outFileTitle});
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
        for (int i = 0; i < nbInFiles; i++) {
            JLabel lblFileRole = new JLabel(inFilesTitles[i] + ":");
            add(lblFileRole, "1, " + (2 * i + 1) + ", left, center");
            JTextField tfPath = new JTextField(30);
            add(tfPath, "3, " + (2 * i + 1) + ", fill, center");
            JButton btnBrowse = new JButton("Browse...");
            add(btnBrowse, "5, " + (2 * i + 1) + ", fill, center");
            inFilePickers[i] = new FilePicker(this, tfPath, btnBrowse, FilePicker.MODE_OPEN);
        }
        outFilePickers = new FilePicker[nbOutFiles];
        for (int i = 0; i < nbOutFiles; i++) {
            JLabel lblFileRole = new JLabel(outFilesTitles[i] + ":");
            add(lblFileRole, "1, " + (2 * (nbInFiles + i) + 1) + ", left, center");
            JTextField tfPath = new JTextField(30);
            add(tfPath, "3, " + (2 * (nbInFiles + i) + 1) + ", fill, center");
            JButton btnBrowse = new JButton("Browse...");
            add(btnBrowse, "5, " + (2 * (nbInFiles + i) + 1) + ", fill, center");
            outFilePickers[i] = new FilePicker(this, tfPath, btnBrowse, FilePicker.MODE_SAVE);
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
}
