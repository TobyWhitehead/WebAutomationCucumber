package webFunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartFunctions extends BaseFunctions {

    final By cartTable = By.cssSelector(".cart");
    final By productCost = By.cssSelector(".product-price bdi");
    private final WebDriver driver;
    private double lowestPrice = -1.0;
    private String lowestPriceId;

    public CartFunctions(WebDriver driver) {
        super(driver);
        this.driver = driver;
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

    public void findLowestPrice() {
        WishlistFunctions wishlistFunctions = new WishlistFunctions(driver);
        String[] idArray = wishlistFunctions.getIdArray();
        for(int j = 0; j < wishlistFunctions.getWishlistTableSize(); j++) {
            double doublePrice = getItemPrice(idArray[j]);
            if(lowestPrice == -1.0 || doublePrice < lowestPrice) {
                lowestPrice = doublePrice;
                lowestPriceId = idArray[j];
            }
        }
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public String getLowestPriceId() {
        return lowestPriceId;
    }

    public void addToCart(String id) {
        String url = "https://testscriptdemo.com/?add-to-cart="+id+"&remove_from_wishlist_after_add_to_cart="+id
                +"&wishlist_id=5101&wishlist_token=DIQKC0IR0XLH";
        navigateTo(url);
    }

    public void goToCart() {
        String url = "https://testscriptdemo.com/?page_id=299";
        navigateTo(url);
    }
}
