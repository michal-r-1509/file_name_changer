package user_interface;

import controller.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class UserInterface extends JPanel {

    private JLabel fileTypeLabel, ignoredStringLabel;
    private JLabel filesFolderChosen;
    private JLabel nameFileChosen;
    private JLabel forbiddenSignsLabel;
    private JLabel schemeLabel;

    private JTextField ignoredText;
    private JTextField forbiddenSignsReplacement;
    private JTextField schemeText;

    private JTextArea help;
    private JTextArea info;

    private JButton choosingDirectory, choosingNamesFile, renameButton;

    private JCheckBox fromFileNameCheckbox;

    private JComboBox fileTypes;

    private JFileChooser directoryChooser;
    private JFileChooser nameFileChooser;

    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();

    int margin = 5;

    private final String[] options = {"pdf", "jpg", "jpeg", "png"};
    private final FileNameExtensionFilter fileTypeFilter = new FileNameExtensionFilter("*.txt", "txt");
    private OptionsController optionsController = new OptionsController();
    private AppService service = new AppService();
    private InfoController infoController = new InfoController();

    public UserInterface() {
        setLayout(layout);
        initWindow();
    }

    private void initWindow() {
        c.insets = new Insets(margin, margin, margin, margin);

        fileTypeLabel = new JLabel("file type");
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        add(fileTypeLabel, c);

        fileTypes = new JComboBox(options);
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        add(fileTypes, c);

        ignoredStringLabel = new JLabel("ignore text");
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 2;
        c.gridy = 0;
        add(ignoredStringLabel, c);

        ignoredText = new JTextField(10);
        c.gridwidth = 2;
        c.gridx = 3;
        c.gridy = 0;
        add(ignoredText, c);

        choosingDirectory = new JButton("source directory");
        choosingDirectory.addActionListener(new ChoosingDirectory());
        c.anchor = GridBagConstraints.WEST;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        add(choosingDirectory, c);

        directoryChooser = new JFileChooser();
        directoryChooser.setDialogTitle("Select directory");
        directoryChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        directoryChooser.setAcceptAllFileFilterUsed(false);

        filesFolderChosen = new JLabel("");
        c.gridwidth = 3;
        c.gridx = 2;
        c.gridy = 1;
        add(filesFolderChosen, c);

        fromFileNameCheckbox = new JCheckBox("append text from file");
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        fromFileNameCheckbox.addActionListener(new CheckboxListener());
        add(fromFileNameCheckbox, c);

        choosingNamesFile = new JButton("choose file");
        choosingNamesFile.setEnabled(false);
        choosingNamesFile.addActionListener(new FileChooser());
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 2;
        add(choosingNamesFile, c);

        nameFileChosen = new JLabel("");
        c.gridwidth = 2;
        c.gridx = 3;
        c.gridy = 2;
        add(nameFileChosen, c);

        nameFileChooser = new JFileChooser();
        nameFileChooser.setDialogTitle("Select file");
        nameFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        nameFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        nameFileChooser.setMultiSelectionEnabled(false);
        nameFileChooser.setAcceptAllFileFilterUsed(false);
        nameFileChooser.setFileFilter(fileTypeFilter);

        forbiddenSignsLabel = new JLabel("replace forbidden signs");
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 3;
        add(forbiddenSignsLabel, c);

        forbiddenSignsReplacement = new JTextField("");
        forbiddenSignsReplacement.setColumns(8);
        forbiddenSignsReplacement.setDocument(new JTextFieldLimit(2));
        c.gridwidth = 3;
        c.gridx = 2;
        c.gridy = 3;
        add(forbiddenSignsReplacement, c);

        schemeLabel = new JLabel("scheme");
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 4;
        add(schemeLabel, c);

        schemeText = new JTextField(15);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.gridx = 1;
        c.gridy = 4;
        add(schemeText, c);

        renameButton = new JButton("rename files");
        renameButton.addActionListener(new Rename());
        c.gridwidth = 1;
        c.gridx = 4;
        c.gridy = 4;
        add(renameButton, c);

        help = new JTextArea(new HelpText().toString());
        help.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 5;
        c.gridheight = 5;
        c.gridx = 0;
        c.gridy = 5;
        add(help, c);

        info = new JTextArea("");
        info.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 5;
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 11;
        add(info, c);
    }

    private class ChoosingDirectory implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            directoryChooser.showOpenDialog(UserInterface.this);
            var directory = directoryChooser.getSelectedFile();
            if (directory != null) {
                optionsController.setDirectory(directory);
                String path = optionsController.getDirectory().toString();
                filesFolderChosen.setText(path.length() > 35 ? "..." + path.substring(path.length() - 35) : path);
                filesFolderChosen.setToolTipText(path);
                infoController.reset(info);
            }
        }
    }

    private class FileChooser implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            nameFileChooser.showOpenDialog(UserInterface.this);
            if (fromFileNameCheckbox.isSelected() && nameFileChooser.getSelectedFile() != null) {
                optionsController.setNamesFile(new NewNamesFile(nameFileChooser.getSelectedFile()));
                nameFileChosen.setText(optionsController.getNamesFile().getFileName());
                infoController.reset(info);
            }
        }
    }

    private class Rename implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            infoController.reset(info);

            if (optionsController.getDirectory() == null) {
                infoController.setErrorText(info, "select source directory");
                return;
            }

            String type = Objects.requireNonNull(fileTypes.getSelectedItem()).toString();
            String ignored = ignoredText.getText();
            String forbiddenReplacement = forbiddenSignsReplacement.getText();
            String scheme = schemeText.getText();
            if (scheme.equals("")) {
                infoController.setErrorText(info, "type scheme");
                return;
            }
            boolean nameFromFile = fromFileNameCheckbox.isSelected();
            if (!nameFromFile) {
                optionsController.setNamesFile(null);
            }
            if (nameFromFile && optionsController.getNamesFile() == null) {
                infoController.setErrorText(info, "select data file");
                return;
            }

            optionsController.setFilesType(type);
            optionsController.setUserText(new UserTexts(ignored, forbiddenReplacement, scheme));

            boolean isDone = service.runAppService(optionsController);
            if (!isDone) {
                infoController.setErrorText(info, "something gone wrong");
                return;
            }
            infoController.setSuccessText(info, "we made it!");
        }
    }

    private class CheckboxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            choosingNamesFile.setEnabled(fromFileNameCheckbox.isSelected());
        }
    }
}
