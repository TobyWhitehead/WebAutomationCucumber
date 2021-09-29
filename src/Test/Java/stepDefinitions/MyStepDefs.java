package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import webFunctions.BaseFunctions;

import static org.junit.jupiter.api.Assertions.*;

public class MyStepDefs {

    public WebDriver driver;
    public String[] inputArray = {"22", "15", "16", "14", "20"};
    public double lowestPrice = 0.0;
    public String lowestPriceId;

    @Given("I add {int} different products to my wish list")
    public void iAddDifferentProductsToMyWishList(int arg0) {
        if (arg0 > 5)
            arg0 = 5;
        driver = BaseFunctions.setUp();
        for(int i = 0; i < arg0; i++) {
            driver.get("https://testscriptdemo.com/?add_to_wishlist=" + inputArray[i]);
        }
    }

    @When("I view my wishlist table")
    public void iViewMyWishlistTable() {
        driver.get("https://testscriptdemo.com/?page_id=233&wishlist-action");
    }

    @Then("I find total {int} selected items in my wishlist")
    public void iFindTotalSelectedItemsInMyWishlist(int arg0) {
        if (arg0 > 5)
            arg0 = 5;
        int total = driver.findElements(By.xpath("//table/tbody/tr")).size();
        assertEquals(arg0, total);
    }

    @When("I search for lowest price product")
    public void iSearchForLowestPriceProduct() {
        for(int j = 0; j < driver.findElements(By.xpath("//table/tbody/tr")).size(); j++) {
            String stringPrice = driver.findElement(By.cssSelector("#yith-wcwl-row-" + inputArray[j] + " ins bdi")).getText();
            double doublePrice = Double.parseDouble(stringPrice.substring(1));
            if(lowestPrice == 0.0 || doublePrice < lowestPrice) {
                lowestPrice = doublePrice;
                lowestPriceId = inputArray[j];
            }
        }
    }

    @And("I am able to add the lowest price item to my cart")
    public void iAmAbleToAddTheLowestPriceItemToMyCart() {
        driver.get("https://testscriptdemo.com/?add-to-cart="+lowestPriceId+"&remove_from_wishlist_after_add_to_cart="+lowestPriceId+"&wishlist_id=5101&wishlist_token=DIQKC0IR0XLH");
    }

    @Then("I am able to verify the item in my cart")
    public void iAmAbleToVerifyTheItemInMyCart() {
        driver.get("https://testscriptdemo.com/?page_id=299");
        assertEquals(1, driver.findElements(By.cssSelector(".cart")).size());
        assertEquals(lowestPrice, Double.parseDouble(driver.findElement(By.cssSelector(".product-price bdi")).getText().substring(1)));
        BaseFunctions.tearDown(driver);
    }
}