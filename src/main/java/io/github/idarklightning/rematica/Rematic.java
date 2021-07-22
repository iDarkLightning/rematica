package io.github.idarklightning.rematica;

public class Rematic {
    private final String name;
    private final String searchURL;

    public Rematic(String name, String searchURL) {
        this.name = name;
        this.searchURL = searchURL;
    }

    public String getName() {
        return name;
    }

    public String getSearchURL() {
        return searchURL;
    }
}
