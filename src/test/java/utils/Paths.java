package utils;

public enum Paths {
    SIMPLE("simple.json");

    String path;

    Paths(String path) {
        this.path = path;
    }
    public String getPath() {
        return this.path;
    }
}
