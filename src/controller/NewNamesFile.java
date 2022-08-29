package controller;

import java.io.File;

public class NewNamesFile {

    private File file;

    public NewNamesFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String getFileName(){
        return file.getName();
    }
}
