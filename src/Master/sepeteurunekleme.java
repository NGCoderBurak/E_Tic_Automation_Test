package Master;

import Utility.BaseDriver;
import Utility.MyFunc;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class sepeteurunekleme extends BaseDriver {
    @Test(priority = 1)
    public void loginTest() {
        driver.get("https://automationexercise.com");
        List<WebElement> consentButton = driver.findElements(By.xpath("//*[text()='Consent']"));
        if (!consentButton.isEmpty()) // bu element var ise ekranda
            consentButton.get(0).click();

        driver.findElement(By.xpath("//a[contains(text(),'Signup / Login')]")).click();

        // Doğru bilgilerle login (Önceki adımda oluşturulan veriler)
        driver.findElement(By.cssSelector("input[data-qa='login-email']")).sendKeys("testuser_99@mail.com");
        driver.findElement(By.cssSelector("input[data-qa='login-password']")).sendKeys("Password123");
        driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();

        // “Logged in as username” doğrulanır
        WebElement loggedInMsg = driver.findElement(By.xpath("//*[contains(text(),'Logged in as')]"));
        Assert.assertTrue(loggedInMsg.isDisplayed());
        Assert.assertTrue(loggedInMsg.getText().contains("TestUser"));


        List<WebElement> errorMsg = driver.findElements(By.xpath("//*[text()='Invalid credentials']"));
        if (!errorMsg.isEmpty()) {
            MyFunc.hataliEkran();
        }

    }

    @Test (priority = 2)
    public void sepeteurunekleme(){
        WebElement products = driver.findElement(By.linkText("products"));
        products.click();

        WebElement addToCart = driver.findElement(By.linkText("data-product-id"));
        addToCart.click();
        MyFunc.Bekle(2);

        WebElement viewCart = driver.findElement(By.linkText("View Cart"));
        viewCart.click();
        MyFunc.Bekle(2);

        WebElement cart = driver.findElement(By.linkText("product_details/1"));
        Assert.assertTrue(cart.isDisplayed());
    }
}
