import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by antonreznikov on 1/24/17.
 */
public class TestListener implements ITestListener {
    public void onTestStart(ITestResult result) {

    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("Yay test passed!");

    }

    public void onTestFailure(ITestResult result) {
        System.out.println("Sad but test has failed ");
        Object currentClass = result.getInstance();
        WebDriver driver = ((BaseWebDriverTest) currentClass).getDriver();
        File output = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File dstFile = new File(System.getProperty("user.dir")+"/reports", "screenshot"+Math.random()+".jpg");
        dstFile.getParentFile().mkdirs();
        try {
            Files.copy(output.toPath(), dstFile.toPath());
        }catch (IOException e){

        }

    }

    public void onTestSkipped(ITestResult result) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void onStart(ITestContext context) {

    }

    public void onFinish(ITestContext context) {

    }
}
