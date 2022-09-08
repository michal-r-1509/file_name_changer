package controller;

public class UserTexts {
    private String ignoredText;
    private String forbiddenSignsReplacement;
    private String schemeText;

    public UserTexts(String ignoredText, String forbiddenSignsReplacement, String schemeText) {
        this.ignoredText = ignoredText;
        this.forbiddenSignsReplacement = forbiddenSignsReplacement;
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

}
