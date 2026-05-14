package Master;

import Utility.BaseDriver;
import Utility.MyFunc;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class E_Tic_Automation extends BaseDriver {

    @Test(priority = 1)
    public void signUp() {
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


        List<WebElement> errorMsg = driver.findElements(By.xpath("//*[text()='Invalid credentials']"));
        if (!errorMsg.isEmpty()) {
            MyFunc.hataliEkran();
        }

    }

    @Test(priority = 3)//tuğçe
    public void UrunArama() {
        driver.get("https://automationexercise.com");
        WebElement products = driver.findElement(By.xpath("//*[text()=' Products']"));
        products.click();
        MyFunc.Bekle(2);
        // reklam geldiğinde url değişiyor. google_vignette ifadesi yer alıyor. burada eğer url'de bu ifade varsa sayfada geri git dedik ve tekrar products butonuna tıklattırdık.
        if (driver.getCurrentUrl().contains("google_vignette")) {
            driver.navigate().back();
            products.click();
        }
        WebElement searchProduct = driver.findElement(By.id("search_product"));
        searchProduct.sendKeys("dress");

        WebElement searchButton = driver.findElement(By.id("submit_search"));
        searchButton.click();
        // Sonuçların listelendiğini doğrula (listelenen ürün sayısı 0 dan büyük ise)
        // 5. Kaç üründe aranan kelimenin geçtiğini ve kaç üründe geçmediğini yazdırınız
        List<WebElement> sonuclar = driver.findElements(By.xpath("//div[@class='productinfo text-center']/p"));

        Assert.assertTrue(!sonuclar.isEmpty(), "Sonuçlar listelenmedi.");
        int sayac1 = 0;
        int sayac2 = 0;
        for (WebElement e : sonuclar) {
            if (e.getText().toLowerCase().contains("dress"))
                sayac1++;
            else
                sayac2++;
        }
        System.out.println("Dress içeren ürün sayısı: " + sayac1);
        System.out.println("Dress içermeyen ürün sayısı: " + sayac2);

    }

    @Test(priority = 4)//toktay
    public void UrunDetay() {
        driver.get("https://automationexercise.com");
        WebElement products = bekle.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()=' Products']")));
        products.click();
        MyFunc.Bekle(2);
        // reklam geldiğinde url değişiyor. google_vignette ifadesi yer alıyor. burada eğer url'de bu ifade varsa sayfada geri git dedik ve tekrar products butonuna bastırdık.
        if (driver.getCurrentUrl().contains("google_vignette")) {
            driver.navigate().back();
            products.click();
        }
        WebElement searchProduct = driver.findElement(By.id("search_product"));
        searchProduct.sendKeys("dress");
        WebElement searchButton = driver.findElement(By.id("submit_search"));
        searchButton.click();
        List<WebElement> productDetail = driver.findElements(By.xpath("//*[text()='View Product']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true)", productDetail.get(0));
        productDetail.get(0).click();
        // fiyat, kategori ve erişilebilirlik alanları ürün detayında görünüyor mu diye kontrol ettirdik.
        WebElement price = driver.findElement(By.xpath("//*[contains(text(),'Rs.')]"));
        Assert.assertTrue(price.isDisplayed());
        WebElement category = driver.findElement(By.xpath("//*[contains(text(),'Category:')]"));
        Assert.assertTrue(category.isDisplayed());
        WebElement availability = driver.findElement(By.xpath("//*[contains(text(),'Availability:')]"));
        Assert.assertTrue(availability.isDisplayed());
    }

    @Test(priority = 5)//sevgi
    public void sepeteUrunEkleme() {

        WebElement products = driver.findElement(By.linkText("products"));
        products.click();
        WebElement addToCart = driver.findElement(By.linkText("data-product-id"));
        addToCart.click();
        MyFunc.Bekle(2);
        WebElement viewCart = driver.findElement(By.linkText("View Cart"));
        viewCart.click();
        MyFunc.Bekle(2);
        WebElement cart = driver.findElement(By.linkText("product_details/1"));
        // deneme push

    }


    @Test(priority = 6)//yiğit
    public void CheckoutTesti() {


    }

    @Test(priority = 6)//yiğit
    public void ActionTesti() {

    }

    @Test(priority = 7)//zeynep
    public void AlertTesti() {
        System.out.println("atiye = " + true);

    }

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




