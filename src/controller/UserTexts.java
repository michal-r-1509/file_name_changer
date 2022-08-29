package controller;

public class UserTexts {
    private String ignoredText;
    private String forbiddenSignsReplacement;
    private String addedText1;
    private String addedText2;
    private String schemeText;

    public UserTexts(String ignoredText, String forbiddenSignsReplacement,
                     String addedText1, String addedText2, String schemeText) {
        this.ignoredText = ignoredText;
        this.forbiddenSignsReplacement = forbiddenSignsReplacement;
        this.addedText1 = addedText1;
        this.addedText2 = addedText2;
        this.schemeText = schemeText;
    }

    public String getSchemeText() {
        return schemeText;
    }

    public String getForbiddenSignsReplacement() {
        return forbiddenSignsReplacement;
    }

    public String getIgnoredText() {
        return ignoredText;
    }

    public String getAddedText1() {
        return addedText1;
    }

    public String getAddedText2() {
        return addedText2;
    }

}
