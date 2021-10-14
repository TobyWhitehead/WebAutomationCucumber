package webFunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartFunctions extends BaseFunctions {
    By cartTable = By.cssSelector(".cart");
    By productCost = By.cssSelector(".product-price bdi");

    public CartFunctions(WebDriver driver) {
        super(driver);
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
}
