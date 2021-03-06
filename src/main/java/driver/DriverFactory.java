package driver;

import driver.enums.BrowserType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
                ChromeOptions chromeLinuxOptions = new ChromeOptions();
                chromeLinuxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                try {
                    driver = new RemoteWebDriver(new URL("http://selenium-hub:4444/wd/hub"), chromeLinuxOptions);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case FIREFOX:
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/java/driver/drivers/geckodriver.exe");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case FIREFOX_LINUX:
                FirefoxOptions firefoxLinuxOptions = new FirefoxOptions();
                firefoxLinuxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                try {
                    driver = new RemoteWebDriver(new URL("http://selenium-hub:4444/wd/hub"), firefoxLinuxOptions);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
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
        String name = System.getProperty("browserType");
        try {
            if (Objects.isNull(name) || name.isEmpty()) {
                Properties properties = new Properties();
                FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/properties/config.properties");
                properties.load(file);
                name = properties.getProperty("browser").toLowerCase().trim();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return BrowserType.getBrowserTypeByName(name);
    }

}
