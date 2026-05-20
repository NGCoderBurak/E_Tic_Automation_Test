package Master;

import Utility.BaseDriver;
import Utility.MyFunc;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class E_Tic_Automation extends BaseDriver {

    @Test(priority = 1)
    public void signUp() throws IOException {
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
        LocalDateTime dt=LocalDateTime.now();
        DateTimeFormatter format= DateTimeFormatter.ofPattern("dd-MM-yyyy_hh-mm");
        TakesScreenshot ts=(TakesScreenshot)driver;
        File hafizadakiEkranGoruntusu= ts.getScreenshotAs(OutputType.FILE);

        String path="ekranGoruntuleri/Screenshot_"+dt.format(format)+".jpg";
        FileUtils.copyFile(hafizadakiEkranGoruntusu, new File(path));
    }


    @Test(priority = 2)
    public void loginTest() {
        driver.get("https://automationexercise.com");
        List<WebElement> consentButton = driver.findElements(By.xpath("//*[text()='Consent']"));
        if (!consentButton.isEmpty()) // (!) bu element var ise ekranda
            consentButton.get(0).click();   // 0. indexte olanına tıklat

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
        // google reklamı sadece Products a tıklatınca geliyor..
        // reklam geldiğinde url değişiyor. google_vignette ifadesi yer alıyor.
        // burada eğer url'de bu ifade varsa sayfada geri git dedik ve
        // tekrar products butonuna tıklattırdık.
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
        int sayac1 = 0;     // dress içerenlerin sayacı
        int sayac2 = 0;     // dress içermeyenlerin sayacı
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

        driver.get("https://automationexercise.com");
        List<WebElement> consentButton = driver.findElements(By.xpath("//*[text()='Consent']"));
        if (consentButton.size() > 0) // bu element var ise ekranda
            consentButton.get(0).click();
        // 1. Ürünler sayfasına git
        //*** Önemli ve Güzel bir komut.. Teşekkürler Tuğçe ve Sevgi...
        WebElement products = bekle.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()=' Products']")));
        products.click();
        MyFunc.Bekle(2);
        // reklam geldiğinde url değişiyor. google_vignette ifadesi yer alıyor. burada eğer url'de bu ifade varsa sayfada geri git dedik ve tekrar products butonuna bastırdık.
        if (driver.getCurrentUrl().contains("google_vignette")) {
            driver.navigate().back();
            products.click();
        }
        System.out.println("Ürünler sayfasına gidildi.");

        // 2.Ürünü sepete ekle
        WebElement addToCart = bekle.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Add to cart']")));
        addToCart.click();
        MyFunc.Bekle(2);
        if (driver.getCurrentUrl().contains("google_vignette")) {
            driver.navigate().back();
            addToCart.click();
        }
        System.out.println("Ürün sepete eklendi.");

        MyFunc.Bekle(2);

        // 3. "View Cart" (Sepeti Görüntüle) tıkla
        WebElement viewCart = driver.findElement(By.xpath("//u[contains(text(),'View Cart')]"));
        viewCart.click();
        System.out.println("Sepete gidiliyor...");

        MyFunc.Bekle(2);

        // 4. Sepette olduğunu doğrula
        WebElement cartItem = driver.findElement(By.id("product-1"));

        Assert.assertTrue(cartItem.isDisplayed(),"Ürün sepette bulunamadı!");


    }



    @Test(priority = 6)//yiğit
    public void CheckoutTesti() {

        driver.get("https://opencart.abstracta.us/index.php?route=checkout/cart");

        driver.findElement(By.linkText("Checkout")).click();
        bekle.until(ExpectedConditions.elementToBeClickable(By.id("button-payment-address"))).click();
        bekle.until(ExpectedConditions.elementToBeClickable(By.id("button-shipping-address"))).click();
        bekle.until(ExpectedConditions.elementToBeClickable(By.id("button-shipping-method"))).click();
        WebElement agree = bekle.until(ExpectedConditions.elementToBeClickable(By.name("agree")));
        if (!agree.isSelected()) agree.click();
        driver.findElement(By.id("button-payment-method")).click();
        bekle.until(ExpectedConditions.elementToBeClickable(By.id("button-confirm"))).click();

        WebElement successMsg = bekle.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Your order has been placed!']")));
        Assert.assertEquals(successMsg.getText(), "Your order has been placed!", "Sipariş onayı alınamadı!");

    }

    @Test(priority = 6)//yiğit
    public void ActionTesti() {

    }

    @Test(priority = 7)//zeynep
    public void AlertTesti() {

        driver.get("https://opencart.abstracta.us/index.php?route=common/home");
        MyFunc.Bekle(2);
        driver.findElement(By.xpath("/html/body/footer/div/div/div[2]/ul/li[1]/a")).click();
        driver.findElement(By.id("input-name")).sendKeys("zeynep 1");
        driver.findElement(By.id("input-email")).sendKeys("zeynepati@gmail.com");
        driver.findElement(By.name("enquiry")).sendKeys("your website sucks");
        driver.findElement(By.cssSelector("[class='btn btn-primary']")).click();
        bekle.until(ExpectedConditions.urlContains("success"));
        Assert.assertTrue(driver.getCurrentUrl().contains("success"));


    }
//1. Formu doldur
//2. Submit tıkla
//3. Alert’i accept ediniz
//4. “Success! Your details have been submitted successfully.” Yazısını doğrulayınız
}