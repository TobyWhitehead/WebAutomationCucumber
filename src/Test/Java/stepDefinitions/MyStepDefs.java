package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import webFunctions.BaseFunctions;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import webFunctions.CartFunctions;
import webFunctions.WishlistFunctions;

import static org.junit.jupiter.api.Assertions.*;

public class MyStepDefs {

    @Before
    public void beforeScenario() {
        driver = myDriver.setUp();
    }

    @After
    public void afterScenario() {
        myDriver.tearDown(driver);
    }

    public WebDriver driver;
    BaseFunctions myDriver = new BaseFunctions();
    public String[] idArray = {"22", "15", "16", "14", "20"};
    public double lowestPrice = 0.0;
    public String lowestPriceId;
    public int WishListTableSize;

    @Given("I add {int} different products to my wish list")
    public void iAddDifferentProductsToMyWishList(int numProducts) {
        if (numProducts > 5)
            numProducts = 5;
        for(int i = 0; i < numProducts; i++) {
            String url = "https://testscriptdemo.com/?add_to_wishlist=" + idArray[i];
            myDriver.navigateTo(url);
        }
    }

    @When("I view my wishlist table")
    public void iViewMyWishlistTable() {
        String url = "https://testscriptdemo.com/?page_id=233&wishlist-action";
        myDriver.navigateTo(url);
    }

    @Then("I find total {int} selected items in my wishlist")
    public void iFindTotalSelectedItemsInMyWishlist(int numItems) {
        if (numItems > 5)
            numItems = 5;
        WishlistFunctions wishListDriver = new WishlistFunctions(driver);
        WishListTableSize = wishListDriver.getWishlistTableSize();
        assertEquals(numItems, WishListTableSize);
    }

    @When("I search for lowest price product")
    public void iSearchForLowestPriceProduct() {
        CartFunctions cartFunctions = new CartFunctions(driver);
        for(int j = 0; j < WishListTableSize; j++) {
            double doublePrice = cartFunctions.getItemPrice(idArray[j]);
            if(lowestPrice == 0.0 || doublePrice < lowestPrice) {
                lowestPrice = doublePrice;
                lowestPriceId = idArray[j];
            }
        }
    }

    @And("I am able to add the lowest price item to my cart")
    public void iAmAbleToAddTheLowestPriceItemToMyCart() {
        String url = "https://testscriptdemo.com/?add-to-cart="+lowestPriceId+"&remove_from_wishlist_after_add_to_cart="+lowestPriceId+"&wishlist_id=5101&wishlist_token=DIQKC0IR0XLH";
        myDriver.navigateTo(url);
    }

    @Then("I am able to verify the item in my cart")
    public void iAmAbleToVerifyTheItemInMyCart() {
        CartFunctions cartFunctions = new CartFunctions(driver);
        String url = "https://testscriptdemo.com/?page_id=299";
        myDriver.navigateTo(url);
        assertEquals(1, cartFunctions.getCartSize());
        assertEquals(lowestPrice, cartFunctions.getCartProductCost());
    }
}