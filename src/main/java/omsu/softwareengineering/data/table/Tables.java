package omsu.softwareengineering.data.table;

public enum Tables {
    Category("category");
    private final String name;

    Tables(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
