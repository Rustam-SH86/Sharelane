import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ShoppingCartTest {
    WebDriver driver;
    String discountPercent;
    String discountDollar;
    String total;
    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }
    public void getDiscount(int quantity){
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=2&zip_code=12345&first_name=test&last_ + " +
                "name=test&email=user%40pflb.ru&password1=12345678&password2=12345678");
        String email = driver.findElement(By.xpath(
                "/html/body/center/table/tbody/tr[6]/td/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/b")).getText();
        driver.get("https://www.sharelane.com/cgi-bin/main.py");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("1111");
        driver.findElement(By.cssSelector("[value = Login]")).click();
        driver.get("https://www.sharelane.com/cgi-bin/add_to_cart.py?book_id=1");
        driver.get("https://www.sharelane.com/cgi-bin/shopping_cart.py");
        driver.findElement(By.name("q")).clear();
        driver.findElement(By.name("q")).sendKeys(String.valueOf(quantity));
        driver.findElement(By.cssSelector("[value = Update]")).click();
        discountPercent = driver.
                findElement(By.xpath("//table/tbody/tr[6]/td/table/tbody/tr[2]/td[5]/p/b")).getText();
        discountDollar = driver.
                findElement(By.xpath("//table/tbody/tr[6]/td/table/tbody/tr[2]/td[6]")).getText();
        total = driver.
                findElement(By.xpath("//table/tbody/tr[6]/td/table/tbody/tr[2]/td[7]")).getText();
    }
        @Test
        public void checkDiscountFirstLevelUnder_20() {
            getDiscount(19);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(discountPercent,
                    "0", "На первом уровне скидка не предоставляется");
            softAssert.assertEquals(discountDollar, "0",
                    "На первом уровне скидка не предоставляется");
            softAssert.assertEquals(total, "190",
                    "Итоговая сумма должна составлять 190$");
            softAssert.assertAll();
        }
        @Test
        public void checkDiscountSecondLevelForTwenty() {
            getDiscount(20);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(discountPercent, "2",
                    "Ожидаемая скидка 2%");
            softAssert.assertEquals(discountDollar, "4.0",
                    "Ожидаемая скидка в валюте 4$");
            softAssert.assertEquals(total, "196",
                    "Итоговая суммы с учетом скидки должна составлять 196$");
            softAssert.assertAll();
        }
        @Test
        public void checkDiscountSecondLevelForNinetyNine() {
            getDiscount(49);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(discountPercent, "2");
            softAssert.assertEquals(discountDollar, "9.8");
            softAssert.assertEquals(total, "480.2");
            softAssert.assertAll();
    }
    @Test
    public void checkDiscountThirdLevelForFifty() {
        getDiscount(50);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(discountPercent, "3");
        softAssert.assertEquals(discountDollar, "15");
        softAssert.assertEquals(total, "485");
        softAssert.assertAll();
    }
    @Test
    public void checkDiscountThirdLevelForNinetyNine() {
        getDiscount(99);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(discountPercent, "3");
        softAssert.assertEquals(discountDollar, "29.7");
        softAssert.assertEquals(total, "960.3");
        softAssert.assertAll();
    }
    @Test
    public void checkDiscountForthLevelForHundred() {
        getDiscount(100);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(discountPercent, "4");
        softAssert.assertEquals(discountDollar, "40");
        softAssert.assertEquals(total, "960");
        softAssert.assertAll();
    }
    @Test
    public void checkDiscountForthLevelFourHundredNinetyNine() {
        getDiscount(499);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(discountPercent, "4");
        softAssert.assertEquals(discountDollar, "199.6");
        softAssert.assertEquals(total, "4790.4");
        softAssert.assertAll();
    }
    @Test
    public void checkDiscountFifthLevelFiveHundred() {
        getDiscount(500);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(discountPercent, "5");
        softAssert.assertEquals(discountDollar, "250");
        softAssert.assertEquals(total, "4750");
        softAssert.assertAll();
    }
    @Test
    public void checkDiscountFifthLevelNineHundredNinetyNine() {
        getDiscount(999);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(discountPercent, "5");
        softAssert.assertEquals(discountDollar, "499.5");
        softAssert.assertEquals(total, "9490.5");
        softAssert.assertAll();
    }
    @Test
    public void checkDiscountEighthLevelTenThousand() {
        getDiscount(10000);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(discountPercent, "8");
        softAssert.assertEquals(discountDollar, "8000");
        softAssert.assertEquals(total, "92000");
        softAssert.assertAll();
    }
        @AfterTest
        public void closeDriver () {
            if (driver != null) {
                driver.quit();
            }
        }
}