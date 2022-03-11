package driver.enums;

import java.util.Objects;

public enum BrowserType {

    CHROME("chrome"),
    FIREFOX("firefox");

    private String name;

    private BrowserType (String name) {
        this.name = name;
    }

    public String getName () {
        return this.name;
    }

    public static BrowserType getBrowserTypeByName (String name) {
        if (Objects.nonNull(name) && !name.isEmpty()) {
            for(BrowserType type : values()) {
                if (type.getName().equals(name)) {
                    return type;
                }
            }
        }
        return null;
    }
}
