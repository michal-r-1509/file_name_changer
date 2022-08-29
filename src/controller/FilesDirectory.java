package controller;

import java.io.File;
import java.nio.file.Path;

public class FilesDirectory {
    private File directory;

    public FilesDirectory(File directory) {
        this.directory = directory;
    }

    public Path getDirectoryPath(){
        return directory.toPath();
    }

}
