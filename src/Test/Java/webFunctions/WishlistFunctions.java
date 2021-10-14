package webFunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WishlistFunctions extends BaseFunctions {
    private WebDriver driver;
    By wishListTable = By.xpath("//table/tbody/tr");

    public WishlistFunctions(WebDriver driver) {
        super(driver);
    }

    public int getWishlistTableSize() {
        return driver.findElements(wishListTable).size();
    }



}
