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
        BaseFunctions myDriver = new BaseFunctions(driver);
        driver = myDriver.setUp();
    }

    @After
    public void afterScenario() {
        BaseFunctions myDriver = new BaseFunctions(driver);
        myDriver.tearDown(driver);
    }

    public WebDriver driver;
    public double lowestPrice;
    public String lowestPriceId;

    @Given("I add {int} different products to my wish list")
    public void iAddDifferentProductsToMyWishList(int numProducts) {
        WishlistFunctions wishlistFunctions = new WishlistFunctions(driver);
        wishlistFunctions.addToWishlist(numProducts);
    }

    @When("I view my wishlist table")
    public void iViewMyWishlistTable() {
        WishlistFunctions wishlistFunctions = new WishlistFunctions(driver);
        wishlistFunctions.goToWishlist();
    }

    @Then("I find total {int} selected items in my wishlist")
    public void iFindTotalSelectedItemsInMyWishlist(int numItems) {
        WishlistFunctions wishlistFunctions = new WishlistFunctions(driver);
        assertEquals(numItems, wishlistFunctions.getWishlistTableSize());
    }

    @When("I search for lowest price product")
    public void iSearchForLowestPriceProduct() {
        CartFunctions cartFunctions = new CartFunctions(driver);
        cartFunctions.findLowestPrice();
        lowestPrice = cartFunctions.getLowestPrice();
        lowestPriceId = cartFunctions.getLowestPriceId();
    }

    @And("I am able to add the lowest price item to my cart")
    public void iAmAbleToAddTheLowestPriceItemToMyCart() {
        CartFunctions cartFunctions = new CartFunctions(driver);
        cartFunctions.addToCart(lowestPriceId);
    }

    @Then("I am able to verify the item in my cart")
    public void iAmAbleToVerifyTheItemInMyCart() {
        CartFunctions cartFunctions = new CartFunctions(driver);
        cartFunctions.goToCart();
        assertEquals(1, cartFunctions.getCartSize());
        assertEquals(lowestPrice, cartFunctions.getCartProductCost());
    }
}
