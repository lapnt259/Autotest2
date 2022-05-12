package vn.bravo;

import PageObjects.HomePage;
import PageObjects.SearchResult;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class fptShop {
    WebDriver driver;
    HomePage homePage;
    String keyword = "iPhone";
    @Before
    public void Setup()
    {
       WebDriverManager.chromedriver().setup();
     //   System.setProperty("webdriver.chrome.driver", "/Users/lapnt/Downloads/chromedriver.exe");
       // System.setProperty("webdriver.gecko.driver", "/Users/lapnt/Downloads/geckodriver.exe");
        this.driver = new ChromeDriver();
        this.driver.manage().window().maximize();
        this.driver.get("https://fptshop.com.vn");
        //Phải implicit wait để chờ page load xong
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        this.homePage =new HomePage(this.driver); //Khởi tạo HomePage pageobject
    }

    @After
    public void TearDown() throws InterruptedException {
        Thread.sleep(2000);
        //this.driver.quit();
    }

    @Test
    public void Suggestion_box_should_show_after_keyword_inputted()
    {
        this.homePage.inputKeyword(this.keyword);
        WebElement pnlSuggestion = this.homePage.getSuggestionBox();
        Assert.assertTrue(pnlSuggestion.isDisplayed());
    }

    @Test
    public void Top_3_hit_items_should_show_on_suggestion_box()
    {
        this.homePage.inputKeyword(this.keyword);
        List<WebElement> lstTopHit =this.homePage.getTopHits();
        //Kiểm tra xem trên list có đúng là có 3 thằng hay không?
        Assert.assertEquals(3, lstTopHit.size());

        //Kiểm tra xem keyword có bold hay không?
        boolean isBold= true;
        boolean isKeywordInluded = true;
        for (WebElement item: lstTopHit) {
            WebElement keyword = item.findElement(By.xpath("//em"));
            String boldStyle = keyword.getCssValue("font-weight");
            isBold = Integer.parseInt(boldStyle)>=500;
            if(isBold==false)
                break;

            isKeywordInluded = keyword.getText().equalsIgnoreCase(this.keyword);
            if(isKeywordInluded==false)
                break;
        }
        //Kiểm tra keyword có bold
        Assert.assertTrue(isBold);
        Assert.assertTrue(isKeywordInluded);
    }

    @Test
    public void should_show_top3_news_releated_to_search_keyword()
    {
        //Mọi người làm tương tự
        this.homePage.inputKeyword(this.keyword);
        List<WebElement> lstnewlist =this.homePage.getnewlists();
        //Kiểm tra xem trên list có đúng là có 3 thằng hay không?
        Assert.assertEquals(3, lstnewlist.size());

        //Kiểm tra xem keyword có bold hay không?
        boolean isBold= true;
        boolean isKeywordInluded = true;
        for (WebElement item: lstnewlist) {
            WebElement keyword = item.findElement(By.xpath("//em"));
            String boldStyle = keyword.getCssValue("font-weight");
            isBold = Integer.parseInt(boldStyle)>=700;
            if(isBold==false)
                break;

            isKeywordInluded = keyword.getText().equalsIgnoreCase(this.keyword);
            if(isKeywordInluded==false)
                break;
        }
        //Kiểm tra keyword có bold
        Assert.assertTrue(isBold);
        Assert.assertTrue(isKeywordInluded);
    }

    @Test
    public void should_show_result_list_with_keyword_included_on_product_name() throws InterruptedException {
        //Tự làm
        this.homePage.searchWithKeyword(this.keyword);
        Thread.sleep(1000);
        SearchResult resultPage = new SearchResult(this.driver);
       // int totalItems = resultPage.getTotalItems();
        List<WebElement> lstProducts = resultPage.getListOfProducts();
        //Kiểm tra xem keyword có bold hay không?
        boolean isBold= true;
        boolean isKeywordInluded = true;
        for (WebElement item: lstProducts) {
            WebElement keyword = item.findElement(By.xpath("//em"));
            String boldStyle = keyword.getCssValue("font-weight");
            isBold = Integer.parseInt(boldStyle)>=700;
            if(isBold==false)
                break;

            isKeywordInluded = keyword.getText().equalsIgnoreCase(this.keyword);
            if(isKeywordInluded==false)
                break;
        }
        //Kiểm tra keyword có bold
       // Assert.assertTrue(isBold);
        Assert.assertTrue(isKeywordInluded);

    }

    @Test
    public void pager_should_show_and_work() throws InterruptedException {
        this.homePage.searchWithKeyword(this.keyword);
        SearchResult resultPage = new SearchResult(this.driver);

        int totalItems = resultPage.getTotalItems();
        //tính có bao nhiêu page dựa vào total
        int totalPage = (int) Math.ceil((double) totalItems / 8);

        for (int i = 0; i < totalPage; i++) {
            resultPage.clickOnLoadMore();
        }

        Thread.sleep(1000);
        List<WebElement> lstProducts = resultPage.getListOfProducts();
        Assert.assertEquals(totalItems, lstProducts.size());
    }
}
