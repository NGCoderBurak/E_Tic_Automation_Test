package Master;

import Utility.BaseDriver;
import Utility.MyFunc;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class E_Tic_Automation extends BaseDriver {
    @Test(priority = 1)
    public void loginTest() {
        driver.get("https://automationexercise.com");
        List<WebElement> consentButton = driver.findElements(By.xpath("//*[text()='Consent']"));
        if (consentButton.size() > 0) // bu element var ise ekranda
            consentButton.get(0).click();




        List<WebElement> errorMsg=driver.findElements(By.xpath("//*[text()='Invalid credentials']"));
        if (!errorMsg.isEmpty()){
            MyFunc.hataliEkran();
        }

    }

    @Test(priority = 2)
    public void UrunArama() {


    }

    @Test(priority = 3)
    public void UrunDetay() {


    }

    @Test(priority = 4)
    public void sepeteUrunEkleme() {
        
    }


    @Test(priority = 5)
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
