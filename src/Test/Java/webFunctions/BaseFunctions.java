package webFunctions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;


public class BaseFunctions {
    private static WebDriver driver;

    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public int getWishlistTableSize() {
        return driver.findElements(By.xpath("//table/tbody/tr")).size();
    }

    public int getCartSize() {
        return driver.findElements(By.cssSelector(".cart")).size();
    }

    public double getCartProductCost() {
        return Double.parseDouble(driver.findElement(By.cssSelector(".product-price bdi")).getText().substring(1));
    }

    public double getItemPrice(String id) {
        String stringPrice = driver.findElement(By.cssSelector("#yith-wcwl-row-" + id + " ins bdi")).getText();
        return Double.parseDouble(stringPrice.substring(1));
    }

    public void tearDown() {
        driver.quit();
    }
}
