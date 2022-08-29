package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class FileNameParser {

    public List<String> getNewNames(UserTexts userTexts,
                                    List<String> oldFilesNames,
                                    List<String> newFilesAppendingText) {
        List<String> newNames = new ArrayList<>();
        for (int i = 0; i < oldFilesNames.size(); i++) {
            String textFromFile = newFilesAppendingText == null ? "" :
                    (i > newFilesAppendingText.size() - 1 ? "" : newFilesAppendingText.get(i));
            newNames.add(schemeParser(userTexts, oldFilesNames.get(i), textFromFile));
        }
        return newNames;
    }

    private String schemeParser(UserTexts userTexts, String oldName, String textFromFile) {
        String oldFileName = oldName.replace(userTexts.getIgnoredText(), "");
        String schemeResult = userTexts.getSchemeText()
                .replaceAll("/", oldFileName)
                .replaceAll("\\*", LocalDate.now().toString())
                .replaceAll("\\?", textFromFile)
                .replaceAll(":", replacing(userTexts.getAddedText1(), ""))
                .replaceAll(">", replacing(userTexts.getAddedText2(), ""));
        if (schemeResult.isBlank()){
            return oldFileName;
        }
        if (schemeResult.length() > 150){
            return replaceForbiddenSigns(schemeResult.substring(0, 150)).trim();
        }
        return replaceForbiddenSigns(schemeResult).trim();
    }

    private String replaceForbiddenSigns(String result) {
        return replaceForbiddenSigns(result, "");
    }

    public String replaceForbiddenSigns(String text, String forbiddenSignsReplacement) {
        String replacement = replacing(forbiddenSignsReplacement, "");
        return replacing(text, replacement);
    }

    private String replacing(String toCheck, String replacement) {
        return toCheck.replaceAll("/", replacement)
                .replaceAll("\\\\", replacement)
                .replaceAll(":", replacement)
                .replaceAll("\"", replacement)
                .replaceAll("\\?", replacement)
                .replaceAll("\\*", replacement)
                .replaceAll("\\|", replacement)
                .replaceAll("<", replacement)
                .replaceAll(">", replacement);
    }
}
