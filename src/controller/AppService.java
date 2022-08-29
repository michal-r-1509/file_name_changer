package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppService {

    private OptionsController controller;
    private FileNameParser parser = new FileNameParser();

    public boolean runAppService(OptionsController controller) {
        this.controller = controller;
        boolean isDone = filesRenaming();
        return isDone;
    }

    private boolean filesRenaming() {
        List<Path> files = getFilesFromDirectory();
        List<String> oldFilesNames = new ArrayList<>();
        List<String> newFilesAppendingText = null;

        if (controller.getNamesFile() != null) {
            newFilesAppendingText = getNameListFromFile();
        }

        if (files != null) {
            for (Path path : files) {
                String oldFileName = path.toFile().getName();
                String fileName = oldFileName.substring(0, oldFileName.lastIndexOf("."));
                oldFilesNames.add(fileName);
            }
        }
        if (files.isEmpty()) {
            return false;
        }
        List<String> newFilesNames = parser.getNewNames(controller.getUserTexts(),
                oldFilesNames,
                newFilesAppendingText);

        return saveRenamedFiles(files, newFilesNames);
    }

    private boolean saveRenamedFiles(List<Path> files, List<String> newFilesNames) {
        File outputPath = controller.getOutputDirectory().toFile();
        if (!outputPath.exists()) {
            outputPath.mkdir();
            if (!outputPath.exists()) {
                outputPath.getParentFile().mkdir();
                try {
                    outputPath.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        for (int i = 0; i < files.size(); i++) {
            try {
                Files.copy(files.get(i), Path.of(outputPath + "/" + newFilesNames.get(i) + "." + controller.getFilesType()));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private List<String> getNameListFromFile() {
        try {
            Stream<String> names = Files.lines(controller.getNamesFile().getFile().toPath());
            return names
                    .map(fileName -> parser.replaceForbiddenSigns(fileName,
                            controller.getUserTexts().getForbiddenSignsReplacement()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Path> getFilesFromDirectory() {
        try {
            return Files.walk(controller.getDirectory().getDirectoryPath(), 1)
                    .filter(Files::isRegularFile)
                    .filter(file -> {
                                String fileName = file.toString();
                                int index = fileName.lastIndexOf(".");
                                return fileName.substring(index + 1).equals(controller.getFilesType());
                            }

                    ).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
