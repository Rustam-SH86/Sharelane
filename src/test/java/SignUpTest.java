import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignUpTest {
    private WebDriver driver;


    @Test
    public void checkPositiveSignUp() {
        driver = new ChromeDriver();
        driver.get("https://www.sharelane.com/");
        driver.findElement(By.xpath("//b[text() = 'ENTER']")).click();
        driver.findElement(By.xpath("//a[@href='./register.py']")).click();
        WebElement zipCode = driver.findElement(By.cssSelector("input[name='zip_code']"));
        zipCode.sendKeys("12345");
        driver.findElement(By.cssSelector("input[value = 'Continue']")).click();
        driver.findElement(By.name("first_name")).sendKeys("Timofei");
        driver.findElement(By.name("last_name")).sendKeys("Borodich");
        driver.findElement(By.name("email")).sendKeys("blabla@bla.com");
        driver.findElement(By.name("password1")).sendKeys("12345678");
        driver.findElement(By.name("password2")).sendKeys("12345678");
        driver.findElement(By.cssSelector("input[value='Register']")).click();
        String signUpMessage = driver.findElement(By.className("confirmation_message")).getText();
        Assert.assertEquals(signUpMessage, "Account is created!");
        //driver.quit();
    }

    @Test
    public void incorrectEmailSignUp() {
        driver = new ChromeDriver();
        driver.get("https://www.sharelane.com/");
        driver.findElement(By.xpath("//b[text() = 'ENTER']")).click();
        driver.findElement(By.xpath("//a[@href='./register.py']")).click();
        WebElement zipCode = driver.findElement(By.cssSelector("input[name='zip_code']"));
        zipCode.sendKeys("12345");
        driver.findElement(By.cssSelector("input[value = 'Continue']")).click();
        driver.findElement(By.name("first_name")).sendKeys("Timofei");
        driver.findElement(By.name("last_name")).sendKeys("Borodich");
        driver.findElement(By.name("email")).sendKeys("blabla@bla");
        driver.findElement(By.name("password1")).sendKeys("12345678");
        driver.findElement(By.name("password2")).sendKeys("12345678");
        driver.findElement(By.cssSelector("input[value='Register']")).click();
        String signUpMessage = driver.findElement(By.className("error_message")).getText();
        Assert.assertEquals(signUpMessage, "Oops, error on page. Some of your fields have invalid data or email was previously used");
        //driver.quit();
    }

}
