package Utility;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MyFunc extends BaseDriver {
    public static void Bekle(int sn)
    {
        try {
            Thread.sleep(sn*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void hataliEkran(){
        LocalTime saat = LocalTime.now();

        List<WebElement> errorMsg=driver.findElements(By.xpath("//*[text()='Invalid credentials']"));
        {
            System.out.println("Hata oldu, ekran görüntüsünü alalım");

            TakesScreenshot ts=(TakesScreenshot)driver;
            File hafizadakiEkranGoruntusu= ts.getScreenshotAs(OutputType.FILE);

            LocalDateTime an = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
            String zaman = an.format(formatter);

            try {
                FileUtils.copyFile(hafizadakiEkranGoruntusu, new File("ekranGoruntuleri/screenShot_" + zaman + ".jpg"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

}
