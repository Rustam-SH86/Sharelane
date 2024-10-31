import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
public class ZipCodeTest {
    private WebDriver browser;

    @BeforeTest
    public void setup(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--headless");
        browser = new ChromeDriver(options);

    }


    @Test
    public void test1() {
        browser.get("https://www.sharelane.com/");
        browser.findElement(By.xpath("//b[text() = 'ENTER']")).click();
        browser.findElement(By.xpath("//a[@href='./register.py']")).click();
        WebElement zipCode = browser.findElement(By.cssSelector("input[name='zip_code']"));
        zipCode.sendKeys("12345");

        browser.findElement(By.cssSelector("input[value = 'Continue']")).click();
        String url = browser.getCurrentUrl();
        Assert.assertTrue(url.contains("register.py?"));



    }
    @Test
    public void testLettersInsteadOfNumbers() {
        browser.get("https://www.sharelane.com/");

        browser.findElement(By.xpath("//b[text() = 'ENTER']")).click();
        browser.findElement(By.xpath("//a[@href='./register.py']")).click();
        WebElement zipCode = browser.findElement(By.cssSelector("input[name='zip_code']"));
        zipCode.click();
        zipCode.sendKeys("Hello");
        browser.findElement(By.cssSelector("input[value = 'Continue']")).click();
        String errorMessage = browser.findElement(By.cssSelector("[class=error_message]")).getText();
        Assert.assertEquals(errorMessage, "Oops, error on page. ZIP code should have 5 digits");

    }

    @Test
    public void testNumberWithSpace() {
        browser.get("https://www.sharelane.com/");

        browser.findElement(By.xpath("//b[text() = 'ENTER']")).click();
        browser.findElement(By.xpath("//a[@href='./register.py']")).click();
        WebElement zipCode = browser.findElement(By.cssSelector("input[name='zip_code']"));
        zipCode.click();
        zipCode.sendKeys("12 345 67");
        browser.findElement(By.cssSelector("input[value = 'Continue']")).click();
        String errorMessage = browser.findElement(By.cssSelector("[class=error_message]")).getText();
        Assert.assertEquals(errorMessage, "Oops, error on page. ZIP code should have 5 digits");

    }
    @Test
    public void testSymbolsInsteadOfNumbers() {
        browser.get("https://www.sharelane.com/");
        browser.findElement(By.xpath("//b[text() = 'ENTER']")).click();
        browser.findElement(By.xpath("//a[@href='./register.py']")).click();
        WebElement zipCode = browser.findElement(By.cssSelector("input[name='zip_code']"));
        zipCode.click();
        zipCode.sendKeys("!@#$%^^&**(");
        browser.findElement(By.cssSelector("input[value = 'Continue']")).click();
        String errorMessage = browser.findElement(By.cssSelector("[class=error_message]")).getText();
        Assert.assertEquals(errorMessage, "Oops, error on page. ZIP code should have 5 digits");

    }




    @AfterTest
    public void closeDriver() {
        if (browser != null){
            browser.quit();
        }
    }
}
