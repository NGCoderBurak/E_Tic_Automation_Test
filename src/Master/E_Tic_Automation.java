package Master;

import Utility.BaseDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class E_Tic_Automation extends BaseDriver {
    @Test (priority=1)
    public void loginTest()
    {
        driver.get("https://automationexercise.com");
        WebElement login=driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[4]/a"));
        login.click();

        WebElement username=driver.findElement(By.id("username"));
        username.sendKeys("admin");
        WebElement password=driver.findElement(By.id("password"));
        password.sendKeys("admin");
        WebElement submit=driver.findElement(By.id("submit"));
        submit.click();

    }

    @Test (priority = 2)
    public void UrunArama()
    {




    }



}
