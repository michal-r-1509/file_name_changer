package controller;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
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
        return filesRenaming();
    }

    private boolean filesRenaming() {
        List<Path> files = getFilesFromDirectory();
        List<String> oldFilesNames = new ArrayList<>();
        List<String> newFilesAppendingText = null;

        if (controller.getNamesFile() != null) {
            newFilesAppendingText = getNameListFromFile();
        }

        if (controller.getNamesFile() != null && newFilesAppendingText.size() != files.size()) {
            return false;
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
                Files.copy(files.get(i), Path.of(outputPath + "/" + newFilesNames.get(i) + "."
                        + controller.getFilesType()));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private List<String> getNameListFromFile() {
                try {
            CharsetDecoder charset = Charset.forName("windows-1250").newDecoder();
            ByteBuffer buffer = ByteBuffer.wrap(Files.readAllBytes(controller.getNamesFile().getFile().toPath()));
            String newContent = charset.decode(buffer).toString();
            Stream<String> names = newContent.lines();
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
            return Files.walk(controller.getDirectory(), 1)
                    .filter(Files::isRegularFile)
                    .filter(file -> {
                                String fileName = file.toString();
                                int index = fileName.lastIndexOf(".");
                                return fileName.substring(index + 1).equals(controller.getFilesType());
                            }
                    )
                    .sorted(Path::compareTo)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
