package Master;

import Utility.BaseDriver;
import Utility.MyFunc;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class E_Tic_Automation extends BaseDriver {

    @Test(priority = 1)
    public void signUp()  {
        // 1. Home Page açılır
        driver.get("https://automationexercise.com");
        List<WebElement> consentButton = driver.findElements(By.xpath("//*[text()='Consent']"));
        if (consentButton.size() > 0) // bu element var ise ekranda
            consentButton.get(0).click();

        // 2. Signup / Login tıklanır
        driver.findElement(By.xpath("//a[contains(text(),'Signup / Login')]")).click();

        // 3. Yeni kullanıcı bilgileri girilir
        driver.findElement(By.cssSelector("input[data-qa='signup-name']")).sendKeys("TestUser");
        driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys("testuser_99@mail.com");
        driver.findElement(By.cssSelector("button[data-qa='signup-button']")).click();

        // 4. Formdaki dropdown’lar seçilir (Wait kullanımı)
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_gender1"))).click();
        driver.findElement(By.id("password")).sendKeys("Password123");

        Select days = new Select(driver.findElement(By.id("days")));
        days.selectByVisibleText("10");

        Select months = new Select(driver.findElement(By.id("months")));
        months.selectByVisibleText("May");

        Select years = new Select(driver.findElement(By.id("years")));
        years.selectByValue("1995");

        // 5. Adres bilgileri doldurulur
        driver.findElement(By.id("first_name")).sendKeys("Ali");
        driver.findElement(By.id("last_name")).sendKeys("Can");
        driver.findElement(By.id("address1")).sendKeys("Karanfil Sokak No:5");

        Select country = new Select(driver.findElement(By.id("country")));
        country.selectByVisibleText("Canada");

        driver.findElement(By.id("state")).sendKeys("Ontario");
        driver.findElement(By.id("city")).sendKeys("Toronto");
        driver.findElement(By.id("zipcode")).sendKeys("M5H");
        driver.findElement(By.id("mobile_number")).sendKeys("1234567890");

        // 6. Hesap oluşturulur
        driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();

        // 7. “ACCOUNT CREATED!” doğrulanır (Assertion)
        WebElement successMsg = driver.findElement(By.cssSelector("h2[data-qa='account-created']"));
        Assert.assertEquals(successMsg.getText(), "ACCOUNT CREATED!");

        // 8. Screenshot alınır
        // takeScreenshot("AccountCreatedSuccess");
    }


    @Test(priority = 2)
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




        List<WebElement> errorMsg=driver.findElements(By.xpath("//*[text()='Invalid credentials']"));
        if (!errorMsg.isEmpty()){
            MyFunc.hataliEkran();
        }

    }

    @Test(priority = 3)
    public void UrunArama() {


    }

    @Test(priority = 4)
    public void UrunDetay() {


    }

    @Test(priority = 5)
    public void sepeteUrunEkleme() {
        
    }


    @Test(priority = 6)
    public void CheckoutTesti() {


    }





    /*List<WebElement> errorMsg=driver.findElements(By.xpath("//*[text()='Invalid credentials']"));
        if (errorMsg.size() > 0)  //hata olmuş demektir, ekran kaydını alalım
    {
        System.out.println("Hata oldu, ekran görüntüsünü alalım");

        TakesScreenshot ts=(TakesScreenshot)driver; // ekran kaydını alabilir hale getirildi
        File hafizadakiEkranGoruntusu= ts.getScreenshotAs(OutputType.FILE);  // dosyaya yazacak formatta ekrna görünütüsü al

        // hafızadaki bu bilgiyi fiziksel DOSYAYA yaz, fakat Java nın böyle bir komutu yok
        // bunun içilmiş içinde bir çokl utility olan commons.io  yu projemize ekleyelim.
        FileUtils.copyFile(hafizadakiEkranGoruntusu, new File("ekranGoruntuleri/screenShot.jpg"));
    }

    // TODO :  Öyle bir şey yapınki her ekran kaydı , AYRI AYRI kaydedilsin. 2025_03_26_13_56

    E_Tic_Automation();

/* YZ önerisi
    public void takeScreenshot(String fileName) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshots/" + fileName + ".png";
        FileUtils.copyFile(srcFile, new File(path));
    }
    */


}
