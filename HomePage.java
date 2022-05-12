package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    @FindBy(xpath = "//input[@id=\"key\"]")
    public WebElement tbSearch;
    By suggestion = By.xpath("//div[contains(@class, \" suggest \")]");
    By topHits = By.xpath("//li[@class=\"ais-Hits-item\"]");

    By releated = By.xpath("//div[@class=\"suggest-title suggest-news\"]");
    By newlists = By.xpath("//li[@class=\"ais-Hits-item news-hits-item hits-js\"]");
    WebDriver webDriver;
    public HomePage(WebDriver webDriver)
    {
        this.webDriver = webDriver;
        PageFactory.initElements(this.webDriver, this); //Map các WebElement với locator thông annotaion @FindBy
    }

    public void inputKeyword(String keyword)
    {
        this.tbSearch.sendKeys(keyword);
    }

    public void searchWithKeyword(String keyword)
    {
        this.tbSearch.sendKeys(keyword);
        this.tbSearch.sendKeys(Keys.ENTER);
    }

    public WebElement getSuggestionBox()
    {
        WebDriverWait waitSuggestion = new WebDriverWait(this.webDriver, Duration.ofSeconds(15));
        WebElement pnlSuggestion = waitSuggestion.until(
                ExpectedConditions.visibilityOfElementLocated(suggestion)
        );
        return  pnlSuggestion;
    }

    public List<WebElement> getTopHits()
    {
        WebElement pnlSuggestion = this.getSuggestionBox();
        List<WebElement> lstTopHits = pnlSuggestion.findElements(topHits);
        return lstTopHits;
    }

    public WebElement getRelated()
    {
        WebDriverWait waitreleated = new WebDriverWait(this.webDriver, Duration.ofSeconds(15));
        WebElement pnlreleated = waitreleated.until(
                ExpectedConditions.visibilityOfElementLocated(releated)
        );
        return  pnlreleated;
    }

    public List<WebElement> getnewlists()
    {
        WebElement pnlreleated = this.getSuggestionBox();
        List<WebElement> lstnewlist = pnlreleated.findElements(newlists);
        return lstnewlist;
    }

}
