import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.edge.EdgeDriver;

import static org.junit.Assert.assertEquals;

public class IMDB {

    @Test
    public void searchMovieTest() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\moham\\Downloads\\edgedriver_win64\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://www.imdb.com/");
        Thread.sleep(2000);
        driver.findElement(By.id("suggestion-search")).sendKeys("The Shawshank Redemption");
        driver.findElement(By.id("suggestion-search-button")).click();
        Thread.sleep(2000);
        driver.quit();
    }

    @Test
    public void top250MoviesTest() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\moham\\Downloads\\edgedriver_win64\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://www.imdb.com/");
        driver.findElement(By.id("imdbHeader-navDrawerOpen")).click();
        //uncomment this for not full screen to select movie
        //driver.findElement(By.xpath("/html/body/div[2]/nav/div[2]/aside[1]/div/div[2]/div/div[1]/span/label")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[2]/nav/div[2]/aside[1]/div/div[2]/div/div[1]/span/div/div/ul/a[2]")).click(); //250
        Thread.sleep(1000);
        String actual = driver.findElement(By.xpath("/html/body/div[2]/main/div/div[3]/section/div/div[1]/div/div[2]/hgroup/h1")).getText();
        assertEquals("IMDb Top 250 Movies", actual);
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/div[2]/main/div/div[3]/section/div/div[2]/div/ul/li[1]/div[2]/div/div/div[1]/a/h3")).click();
        Thread.sleep(1000);
        String actualMovieTitle = driver.findElement(By.xpath("/html/body/div[2]/main/div/section[1]/section/div[3]/section/section/div[2]/div[1]/h1/span")).getText();
        assertEquals("The Shawshank Redemption", actualMovieTitle);
        driver.quit();
    }

    @Test
    public void advancedSearchTest() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\moham\\Downloads\\edgedriver_win64\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) driver; // for scrolling
        driver.navigate().to("https://www.imdb.com/");
        Thread.sleep(2000);

        //All
        driver.findElement(By.xpath("/html/body/div[2]/nav/div[2]/div[1]/form/div[1]/div/label")).click();
        Thread.sleep(2000);

        // When the user clicks on the "Advanced Search" link in the search bar ﬁlter The user would then be redirected to a page containing “Advanced Title Search” link, which he should click.
        driver.findElement(By.xpath("/html/body/div[2]/nav/div[2]/div[1]/form/div[1]/div/div/div/div/span/ul/a")).click();
        Thread.sleep(2000);

        //And selects "Movie" as title type
        js.executeScript("window.scrollBy(0,50)");
        Thread.sleep(2000);

        driver.findElement(By.xpath("/html/body/div[2]/main/div[2]/div[3]/section/section/div/section/section/div[2]/div/section/div[2]/div[1]/section/div/div[2]/div[1]/label")).click();
        driver.findElement(By.xpath("/html/body/div[2]/main/div[2]/div[3]/section/section/div/section/section/div[2]/div/section/div[2]/div[1]/section/div/div[2]/div[2]/div/section/button[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[2]/main/div[2]/div[3]/section/section/div/section/section/div[2]/div/section/div[2]/div[1]/section/div/div[2]/div[1]/label")).click();
        Thread.sleep(1000);

        //And enters a start year and end year in the "Release Date" fields (2010 - 2020).
        js.executeScript("window.scrollBy(0,100)");
        Thread.sleep(2000);

        driver.findElement(By.xpath("/html/body/div[2]/main/div[2]/div[3]/section/section/div/section/section/div[2]/div/section/div[2]/div[1]/section/div/div[3]/div[1]/label")).click();
        driver.findElement(By.xpath("/html/body/div[2]/main/div[2]/div[3]/section/section/div/section/section/div[2]/div/section/div[2]/div[1]/section/div/div[3]/div[2]/div/div/div[2]/div[1]/div/div/div/input")).sendKeys("2010");
        driver.findElement(By.xpath("/html/body/div[2]/main/div[2]/div[3]/section/section/div/section/section/div[2]/div/section/div[2]/div[1]/section/div/div[3]/div[2]/div/div/div[2]/div[2]/div/div/div/input")).sendKeys("2020");
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[2]/main/div[2]/div[3]/section/section/div/section/section/div[2]/div/section/div[2]/div[1]/section/div/div[3]/div[1]/label")).click();
        Thread.sleep(1000);

        //And selects the “Action” genre from Genres.
        js.executeScript("window.scrollBy(0,150)");
        Thread.sleep(2000);

        driver.findElement(By.xpath("/html/body/div[2]/main/div[2]/div[3]/section/section/div/section/section/div[2]/div/section/div[2]/div[1]/section/div/div[6]/div[1]/label")).click();
        driver.findElement(By.xpath("/html/body/div[2]/main/div[2]/div[3]/section/section/div/section/section/div[2]/div/section/div[2]/div[1]/section/div/div[6]/div[2]/div/section/button[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[2]/main/div[2]/div[3]/section/section/div/section/section/div[2]/div/section/div[2]/div[1]/section/div/div[6]/div[1]/label")).click();
        Thread.sleep(2000);

        //And clicks the "See results" button
        driver.findElement(By.xpath("/html/body/div[2]/main/div[2]/div[3]/section/section/div/section/section/div[2]/div/section/div[1]/button")).click();
        Thread.sleep(2000);

        //Then the search results page should display a list of Action movies released between 2010 and 2020, sorted by Popularity.
        driver.findElement(By.xpath("/html/body/div[2]/main/div[2]/div[3]/section/section/div/section/section/div[2]/div/section/div[2]/div[2]/div[1]/div[2]/div/span/span")).click();
        driver.findElement(By.xpath("/html/body/div[2]/main/div[2]/div[3]/section/section/div/section/section/div[2]/div/section/div[2]/div[2]/div[1]/div[2]/div/span/span/select/option[2]")).click();
        Thread.sleep(3000);
        js.executeScript("window.scrollBy(0,100)");
        Thread.sleep(2000);
        driver.quit();
    }








}
