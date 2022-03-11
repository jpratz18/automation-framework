package driver;

import driver.enums.BrowserType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class DriverFactory {

    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    private static WebDriver createDriver() {
        WebDriver driver = null;
        BrowserType browserType = getBrowserType();
        switch (browserType) {
            case CHROME:
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/java/driver/drivers/chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driver = new ChromeDriver(chromeOptions);
                break;
            case CHROME_LINUX:
                System.setProperty("webdriver.chrome.driver", "http://localhost:4444");
                ChromeOptions chromeLinuxOptions = new ChromeOptions();
                chromeLinuxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driver = new ChromeDriver(chromeLinuxOptions);
                break;
            case FIREFOX:
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/java/driver/drivers/geckodriver.exe");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case FIREFOX_LINUX:
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/java/driver/drivers/geckodriver");
                FirefoxOptions firefoxLinuxOptions = new FirefoxOptions();
                firefoxLinuxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driver = new FirefoxDriver(firefoxLinuxOptions);
                break;
        }
        driver.manage().window().maximize();
        return driver;
    }

    public static WebDriver getDriver() {
        if (Objects.isNull(webDriver.get())) {
            webDriver.set(createDriver());
        }
        return webDriver.get();
    }

    public static void cleanupDriver() {
        webDriver.get().quit();
        webDriver.remove();
    }

    private static BrowserType getBrowserType() {
        String name = null;
        try {
            Properties properties = new Properties();
            FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/properties/config.properties");
            properties.load(file);
            name = properties.getProperty("browser").toLowerCase().trim();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return BrowserType.getBrowserTypeByName(name);
    }

}
