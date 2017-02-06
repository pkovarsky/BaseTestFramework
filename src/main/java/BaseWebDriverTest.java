import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.util.HashMap;

/**
 * Created by antonreznikov on 1/14/17.
 */
@Listeners(TestListener.class)
public class BaseWebDriverTest {
    public WebDriver driver;
    public WebDriverWait wait;
    public SoftAssert softAssert;
    private long startTime;

    public WebDriver getDriver(){
        return driver;
    }

    @BeforeMethod
    public void initDriver(){
        if(System.getProperty("selenium.browser")==null) {
            System.setProperty("selenium.browser", "chrome");
        }

        driver = getNewDriver();
        startTime = Reporter.getCurrentTestResult().getStartMillis();
        wait = new WebDriverWait(driver, 15);
        softAssert = new SoftAssert();
    }

    @AfterMethod
    public void destroyDriver(){
        if(driver!=null) {
            driver.quit();
        }

        Reporter.log("Elapsed time: "+(Reporter.getCurrentTestResult().getEndMillis()-startTime/1000),true);
    }

    private WebDriver getNewDriver(){
        String path = System.getProperty("user.dir");
        if(driver == null && System.getProperty("selenium.browser").equals("chrome")) {
            if (System.getProperty("os.name").contains("Windows")) {
                Reporter.log("Getting Chrome driver for win", true);
                Reporter.log("Getting IE driver", true);
                System.setProperty("webdriver.chrome.driver", path + "/src/main/resources/chromedriver.exe");
            } else if (System.getProperty("os.name").contains("Mac")) {
                Reporter.log("Getting Chrome driver for mac", true);
                System.setProperty("webdriver.chrome.driver", path + "/src/main/resources/chromedriver");
            }
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-extensions");
            options.addArguments("--desktop-window-1080p");
            options.addArguments("start-maximized");

            String downloadFilepath = path + "/downloadedFiles";
            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", downloadFilepath);
            HashMap<String, Object> chromeOptionsMap = new HashMap<>();
            options.setExperimentalOption("prefs", chromePrefs);
            options.addArguments("--test-type");
            DesiredCapabilities cap = DesiredCapabilities.chrome();

            cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
            cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            cap.setCapability(ChromeOptions.CAPABILITY, options);

            driver = new ChromeDriver(options);
        }else if(driver == null && System.getProperty("selenium.browser").equals("firefox")) {
            driver = new FirefoxDriver();

        } else if(driver == null && System.getProperty("selenium.browser").equals("ie")) {
            System.setProperty("webdriver.ie.driver",new File("/src/test/resources/", "IEDriverServer.exe").getAbsolutePath());
            driver = new InternetExplorerDriver();
        }
        return driver;
    }
}
