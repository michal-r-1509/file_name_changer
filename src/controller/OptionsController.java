package controller;

import java.nio.file.Path;
import java.time.LocalTime;

public class OptionsController {
    private String filesType;
    private UserTexts userTexts;
    private FilesDirectory directory;
    private NewNamesFile newNamesFile;

    public Path getOutputDirectory() {
        LocalTime time = LocalTime.now();
        String timeText = time.toString().replaceAll(":", "_").substring(0, 8);
        return Path.of(directory.getDirectoryPath().toString() + "\\output_" + timeText);
    }

    public String getFilesType() {
        return filesType;
    }

    public UserTexts getUserTexts() {
        return userTexts;
    }

    public FilesDirectory getDirectory() {
        return directory;
    }

    public NewNamesFile getNamesFile() {
        return newNamesFile;
    }

    public void setUserText(UserTexts userTexts) {
        this.userTexts = userTexts;
    }

    public void setDirectory(FilesDirectory directory) {
        this.directory = directory;
    }

    public void setNamesFile(NewNamesFile newNamesFile) {
        this.newNamesFile = newNamesFile;
    }

    public void setFilesType(String filesType) {
        this.filesType = filesType;
    }
}
