package utils;

public enum Paths {
    SIMPLE("simple.json"),
    WITHOUT_INPUT("without_input.json"),
    INPUT_NULL("input_null.json"),
    WITHOUT_SEPARATOR("without_separator.json");

    String path;

    Paths(String path) {
        this.path = path;
    }
    public String getPath() {
        return this.path;
    }
}
