package io.github.idarklightning.rematica.util;

public class SearchResult {
    private String name;
    private String sourceURL;

    SearchResult(String name, String sourceURL) {
        this.name = name;
        this.sourceURL = sourceURL;
    }

    public String getName() {
        return name;
    }

    public String getSourceURL() {
        return sourceURL;
    }
}