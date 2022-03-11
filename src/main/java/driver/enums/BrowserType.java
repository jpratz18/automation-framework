package driver.enums;

import java.util.Objects;

public enum BrowserType {

    CHROME("chrome"),
    CHROME_LINUX("chrome_linux"),
    FIREFOX("firefox"),
    FIREFOX_LINUX("firefox_linux");

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
