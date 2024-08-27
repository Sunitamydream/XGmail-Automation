package demo;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;


import java.util.logging.Level;

public class TestCases {
    ChromeDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.INFO);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // Connect to the chrome-window running on debugging port
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Set browser to maximize and wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    public void testCase01() throws InterruptedException {
        System.out.println("Start Test case: testCase01");

        // Navigate to Google Calendar
        driver.get("https://calendar.google.com/");
        Thread.sleep(4000);

        // Verify that the current URL contains "calendar."
        String currentURL = driver.getCurrentUrl();
        System.out.println(currentURL);
        if (currentURL.contains("calendar")) {
            System.out.println("Test case passed: URL contains 'calendar'.");
        } else {
            System.out.println("Test case failed: URL does not contain 'calendar'.");
        }

        System.out.println("End Test case: testCase01");
    }

    public void testCase02() {
        try {
            System.out.println("Start Test case: testCase02");
            driver.get("https://calendar.google.com/");
            Thread.sleep(5000);

            // Click on the month view button
            WebElement monthView = driver.findElement(By.xpath("//div[@class='XyKLOd']"));
            monthView.click();

            WebElement monthOption = driver.findElement(By.xpath("//span[contains(text(),'Month')]"));
            monthOption.click();

            String expectedView = "Month";
            WebElement currentView = driver.findElement(By.xpath("(//span[@class='VfPpkd-vQzf8d'])[5]"));
            String monthViewText = currentView.getText();
            if (monthViewText.equals(expectedView)) {
                System.out.println("Current view is Month");
            }

            WebElement createTab = driver.findElement(By.className("mr0WL"));
            createTab.click();
            Thread.sleep(2000);

            // Click on the "Tasks" tab
            WebElement taskTab = driver.findElement(By.xpath("(//div[@class='uyYuVb oJeWuf'])[2]"));
            Thread.sleep(5000);
            taskTab.click();
            Thread.sleep(5000);

            // Enter task details
            WebElement taskTitle = driver.findElement(By.xpath("//input[@placeholder='Add title']"));
            taskTitle.sendKeys("Crio INTV Task Automation");

            WebElement descriptionArea = driver.findElement(By.xpath("//textarea[@type='text']"));
            descriptionArea.sendKeys("Crio INTV Calendar Task Automation");

            // Save the task
            WebElement saveButton = driver.findElement(By.xpath("//button[@jsname='x8hlje']"));
            saveButton.click();

            System.out.println("End Test case: testCase02");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testCase03() {
        try {
            System.out.println("Start Test case: testCase03");
            driver.get("https://calendar.google.com/");
            Thread.sleep(5000);

            // Click on the task "Crio INTV Task Automation"
            WebElement existingTask = driver.findElement(By.xpath("(//span[@class='WBi6vc'])[last()]"));
            existingTask.click();
            Thread.sleep(3000);

            // Click on the Edit task button
            WebElement editTaskButton = driver.findElement(By.xpath("(//span[@class='VfPpkd-kBDsod meh4fc KU3dEf'])[3]"));
            editTaskButton.click();
            Thread.sleep(2000);

            // Update the description
            String textToUpdate = "Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application";
            WebElement descriptionTextArea = driver.findElement(By.xpath("//textarea"));
            descriptionTextArea.clear();
            descriptionTextArea.sendKeys(textToUpdate);

            // Click on Save button
            WebElement saveButton = driver.findElement(By.xpath("//button/span[text()='Save']"));
            saveButton.click();

            // Verify and display the updated description
            Thread.sleep(3000);
            //Click on Task again and verify the updated description 
            WebElement updatedDescription = driver.findElement(By.xpath("//span[text()='Crio INTV Task Automation']"));
            updatedDescription.click();

            Thread.sleep(3000);
            WebElement description = driver.findElement(By.xpath("//span[text()='Description:']/parent::div"));
            
            if(description.getText().contains("Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application"))
            {
                System.out.println("description has been verified for title window");
            }
            else{
                System.out.println("description has not been verified for title window");
            }
            // String actualDescription = description.getText();
            // System.out.println(actualDescription);

            // Assert.assertEquals(textToUpdate, actualDescription);

            System.out.println("End Test case: testCase03");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testCase04() {
        try {
            System.out.println("Start Test case: testCase04");
            driver.get("https://calendar.google.com/");
            Thread.sleep(3000);

            WebElement deleteTask = driver.findElement(By.xpath("//span[text()='Crio INTV Task Automation']"));
            deleteTask.click();
            Thread.sleep(3000);

            WebElement verifyTitle = driver.findElement(By.xpath("//span[@id='rAECCd']"));
            String titleText = verifyTitle.getText();
            System.out.println(titleText);

            WebElement deleteIcon = driver.findElement(By.xpath("//div[@jsaction='JIbuQc:qAGoT']"));
            deleteIcon.click();
            Thread.sleep(4000);

            WebElement verifyDelete = driver.findElement(By.xpath("//div[contains(text(),'Task deleted')]"));
            String expectedMessage = "Task deleted";
            String verifyDeleteMessage = verifyDelete.getText();
            System.out.println(verifyDeleteMessage);
            if (verifyDeleteMessage.equals(expectedMessage)) {
                System.out.println("Delete message displayed!");
            } else {
                System.out.println("Delete message not displayed!");
            }

            System.out.println("End Test case: testCase04");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestCases testCases = new TestCases();
        try {
            testCases.testCase01();
            testCases.testCase02();
            testCases.testCase03();
            testCases.testCase04();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            testCases.endTest();
        }
    }
}
