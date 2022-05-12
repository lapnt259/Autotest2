package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchResult {
    By lcTotal = By.cssSelector("h1.fs-search-result span");
    By lcLoadMore = By.cssSelector("div.c-comment-loadMore a");
    By lcProductItem = By.cssSelector("div#main div.cdt-product");
    WebDriver driver;
    public SearchResult(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public int getTotalItems()
    {
        List<WebElement> lstSpan = this.driver.findElements(lcTotal);
        return Integer.parseInt(lstSpan.get(0).getText());
    }

    public void clickOnLoadMore()
    {
        WebDriverWait waitLoadMore = new WebDriverWait(this.driver, Duration.ofSeconds(15));
        WebElement btnLoadMore = waitLoadMore.until(
                ExpectedConditions.visibilityOfElementLocated(lcLoadMore)
        );
        btnLoadMore.click();
    }

    public List<WebElement> getListOfProducts()
    {
        return this.driver.findElements(lcProductItem);
    }
}
