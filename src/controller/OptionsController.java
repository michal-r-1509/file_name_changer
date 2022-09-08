package controller;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalTime;

public class OptionsController {
    private String filesType;
    private UserTexts userTexts;
    private File directory;
    private NewNamesFile newNamesFile;

    public Path getOutputDirectory() {
        LocalTime time = LocalTime.now();
        String timeText = time.toString().replaceAll(":", "_").substring(0, 8);
        return Path.of(getDirectory().toString() + "\\output_" + timeText);
    }

    public String getFilesType() {
        return filesType;
    }

    public UserTexts getUserTexts() {
        return userTexts;
    }

    public Path getDirectory() {
        return directory == null ? null : directory.toPath();
    }

    public NewNamesFile getNamesFile() {
        return newNamesFile;
    }

    public void setUserText(UserTexts userTexts) {
        this.userTexts = userTexts;
    }

    public void setNamesFile(NewNamesFile newNamesFile) {
        this.newNamesFile = newNamesFile;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }

    public void setFilesType(String filesType) {
        this.filesType = filesType;
    }
}
