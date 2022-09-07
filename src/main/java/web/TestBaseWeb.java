package web;

import java.util.concurrent.TimeUnit;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.kamatech.qaaf.graphics.action.SikuliWrap;
import ru.kamatech.qaaf.properties.Properties;

public class TestBaseWeb extends Properties {
    private SikuliWrap sikuliWrap;

    protected SikuliWrap sikuliWrap() {
        if (sikuliWrap == null) {
            sikuliWrap = new SikuliWrap();
        }

        return sikuliWrap;
    }
    protected ChromeDriver driverSelenium;

    protected void runDriverSelenium() {
        //ChromeOptions options = new ChromeOptions();
        //options.addArguments(new String[]{"user-data-dir=C:\\Users\\Admin\\AppData\\Local\\Google\\Chrome\\User Data\\"});
        //options.setBinary(WebDriverManager.chromedriver().getBinaryPath());

        //System.setProperty("webdriver.chrome.driver", getPathFromResources(pathWebDriver));
        //WebDriverManager.chromedriver().setup();

        //ChromeDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("headless");
        chromeOptions.addArguments("window-size=1920, 1080");
        chromeOptions.addArguments("start-maximized");
        WebDriverManager.chromedriver().setup();
        driverSelenium = new ChromeDriver(chromeOptions);
        driverSelenium.manage().window().maximize();
        driverSelenium.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
    }

    protected void quitDriverSelenium()
    {
        driverSelenium.quit();
    }



    }

