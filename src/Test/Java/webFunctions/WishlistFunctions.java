package webFunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WishlistFunctions extends BaseFunctions {

    By wishListTable = By.xpath("//table/tbody/tr");
    private final WebDriver driver;
    private final String[] idArray = {"22", "15", "16", "14", "20"};
    private int WishlistTableSize = -1;

    public String[] getIdArray() {
        return idArray;
    }

    public WishlistFunctions(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void addToWishlist(int numProducts) {
        if (numProducts > idArray.length)
            numProducts = idArray.length;
        for(int i = 0; i < numProducts; i++) {
            String url = "https://testscriptdemo.com/?add_to_wishlist=" + idArray[i];
            navigateTo(url);
        }
    }

    public void goToWishlist() {
        String url = "https://testscriptdemo.com/?page_id=233&wishlist-action";
        navigateTo(url);
    }

    public int getWishlistTableSize() {
        if(WishlistTableSize == -1) {
            WishlistTableSize = driver.findElements(wishListTable).size();
        }
        return WishlistTableSize;
    }
}
