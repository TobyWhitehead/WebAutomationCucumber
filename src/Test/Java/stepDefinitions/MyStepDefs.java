package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import webFunctions.BaseFunctions;
import io.cucumber.java.Before;
import io.cucumber.java.After;

import static org.junit.jupiter.api.Assertions.*;

public class MyStepDefs {

    @Before
    public void beforeScenario() {
        myDriver.setUp();
    }

    @After
    public void afterScenario() {
        myDriver.tearDown();
    }

    BaseFunctions myDriver = new BaseFunctions();
    public String[] idArray = {"22", "15", "16", "14", "20"};
    public double lowestPrice = 0.0;
    public String lowestPriceId;
    public int WishListTableSize;

    @Given("I add {int} different products to my wish list")
    public void iAddDifferentProductsToMyWishList(int arg0) {
        if (arg0 > 5)
            arg0 = 5;
        for(int i = 0; i < arg0; i++) {
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
    public void iFindTotalSelectedItemsInMyWishlist(int arg0) {
        if (arg0 > 5)
            arg0 = 5;
        WishListTableSize = myDriver.getWishlistTableSize();
        assertEquals(arg0, WishListTableSize);
    }

    @When("I search for lowest price product")
    public void iSearchForLowestPriceProduct() {
        for(int j = 0; j < myDriver.getWishlistTableSize(); j++) {
            double doublePrice = myDriver.getItemPrice(idArray[j]);
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
        String url = "https://testscriptdemo.com/?page_id=299";
        myDriver.navigateTo(url);
        assertEquals(1, myDriver.getCartSize());
        assertEquals(lowestPrice, myDriver.getCartProductCost());
    }
}