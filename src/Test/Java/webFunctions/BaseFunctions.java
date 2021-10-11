package webFunctions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;


public class BaseFunctions {
    private WebDriver driver;
    By wishListTable = By.xpath("//table/tbody/tr");
    By cartTable = By.cssSelector(".cart");
    By productCost = By.cssSelector(".product-price bdi");


    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    public void navigateTo(String url) {
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public int getWishlistTableSize() {
        return driver.findElements(wishListTable).size();
    }

    public int getCartSize() {
        return driver.findElements(cartTable).size();
    }

    public double getCartProductCost() {
        return Double.parseDouble(driver.findElement(productCost).getText().substring(1));
    }

    public double getItemPrice(String id) {
        By cartItemPrice = By.cssSelector("#yith-wcwl-row-" + id + " ins bdi");
        String stringPrice = driver.findElement(cartItemPrice).getText();
        return Double.parseDouble(stringPrice.substring(1));
    }

    public void tearDown() {
        driver.quit();
    }
}
