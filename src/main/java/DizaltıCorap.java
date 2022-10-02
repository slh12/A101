import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.SourceType;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.management.StandardEmitterMBean;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DizaltıCorap {



    @Test
    public void giyimMenuTesti() throws InterruptedException, AWTException {




        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://a101.com.tr/");

        driver.manage().window().maximize();

        //Burada popup ve cookie ekranlarını kapatacağız.
        driver.findElement(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).click();

        driver.findElement(By.xpath("/html/body/section/section[1]/div/div/div/div[1]/div/div/ul/li[4]/a")).click();
        Thread.sleep(2000);

        /*Cookie ve popup bildirimlerini devre dışı bıraktıktan sonra Ana sayfadaki
        giyim aksesuar linkinin üzerine geliyoruz ve açılan menuden "dizaltı çorap"ı sağ tıklayıp yeni sekmede
        açıyoruz. Çünkü otomasyon testinde "dizaltı çorap" linkinin üzerine tıklamayı manual denesem bile gerçekleştirmediği için, ilgili linki
        sağ tıklayıp yeni sekmede açtım. Diğer türlü case'in diğer aşamalarına geçmek mümkün olmazdı.  */

       // Bu metodda a101 ana sayfasından dizaltı çorap sayfasına geçiş yaptığımız bölüme kadar olan test bulunmaktadır.



        WebElement dizaltıCorapLinki=driver.findElement(By.xpath("//a[@title=\"Dizaltı Çorap\"]"));
        WebElement giyimAksesuarLinki=driver.findElement(By.xpath("/html/body/section/section[1]/div/div/div/div[1]/div/div/ul/li[4]/a"));

        Actions actions;
        actions = new Actions(driver);
        actions.moveToElement(dizaltıCorapLinki).perform();



        actions.contextClick(dizaltıCorapLinki).build().perform();

        Thread.sleep(1000);

           //Yukarıda "dizaltı çorap" linkinin üstüne gelip sağa tıkladık. Aşağıda ise robot sınıfı yardımıyla
           // "aşağı ok" ve "ENTER" tuşlarına basarak dizaltı çorap sayfasını yeni sekmede açıyoruz.

        Robot robot=new Robot();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);

        //Aşağıdaki satırda açtığımız yeni sekmeye geçiş yaptık.
        Thread.sleep(2000);

        driver.getWindowHandles().forEach(tab->driver.switchTo().window(tab));

        Thread.sleep(2000);


            //Açılan sayfadaki ilk ürünü seçiyorum.
            //Test Case'de hangi ürünün seçilmesi gerektiği belirtilmediği için ilk ürünü seçtim.

            //Sepeti ekle butonuna basarak ürün sayfasını açıyoruz.
        driver.findElement(By.xpath("/html/body/section/section[4]/div[3]/div[2]/div/div[2]/div[2]/div/ul/li[1]/article/div/div[2]/div[2]")).click();

            /*Aşağıdaki satırda Ürün sayfasının açılıp açılmadığını doğrulayacağız.  Fakat bu doğrulamayı
            diğerleri gibi currentURL ile actual URL i karşılaştırarak yapmayacağız. Çünkü ileride ürün detayları
             değişirse URL de değişebilir ve testimizin başarısız olmasına neden olur.

             Bundan dolayı bütün ürün sayfalarında aynı olan bir detay üzerinden ilerlememiz gerekiyor.
             Bu da Ürün Resminin altında yer alan "Ürün Bilgileri" sekmesi olabilir.
             Eğer bir sayfada "ürün Bilgileri" sekmesi varsa bu sayfanın bir ürünün sayfası olduğundan emin olabiliriz.
             */

        Assert.assertTrue(driver.findElement(By.xpath("//a[@title='Ürün Bilgileri']")).isDisplayed());



        //Açılan ürünün renginin siyah olup olmadığını doğruluyoruz.
        String ürünRengi=driver.findElement(By.xpath("//div[@class='selected-variant-text']")).getText();

        Assert.assertTrue(ürünRengi.equalsIgnoreCase("Seçılen Renk: SİYAH"));

        Thread.sleep(1000);


        //Ürün sayfasındaki sepeti ekle butonuna basıyoruz.
        driver.findElement(By.xpath("/html/body/section/section[3]/div[2]/div[1]/div/div[3]/div[2]/div[1]/button")).click();

        //Sepeti görüntüle linkine tıklıyoruz.
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[@onclick=\"document.location.href='https://www.a101.com.tr/baskets/basket/';\"]")).click();

        //Aşağıdaki satırda "SEPETİM" sayfasına gittiğini doğruluyoruz.
        Assert.assertEquals("https://www.a101.com.tr/baskets/basket/",driver.getCurrentUrl());
        Thread.sleep(3000);


        //Sepeti onayla'ya tıklıyoruz.
        driver.findElement(By.xpath("//a[@href=\"/orders/checkout/\"]")).click();







        //Üye olmadan devam et seçeneğine tıklıyoruz.
        driver.findElement(By.xpath("//a[@title=\"ÜYE OLMADAN DEVAM ET\"]")).click();

        Thread.sleep(3000);

        //Email kutusunun görünür olup lmadığını kontrol ediyoruz.
        Assert.assertTrue(driver.findElement(By.xpath("//input[@name='user_email']")).isDisplayed());
        //Emailimizi girip butona basıyoruz ve devam et butonuna tıklıyoruz.
        driver.findElement(By.xpath("//input[@name=\"user_email\"]")).sendKeys("asdfghjkl123@gmail.com");
        driver.findElement(By.xpath("//button[@class='button block green']")).click();


        Thread.sleep(2000);



        //Yeni adres oluştur bölümüne tıklıyoruz.
        driver.findElement(By.xpath("/html/body/section/section/div/div[2]/div/div[1]/div/div[1]/div[2]/ul[2]/li/a")).click();




        //Aşağıda ise adres bilgilerini dolduruyoruz.

        WebElement adresbaşlığı = driver.findElement(By.xpath("//input[@placeholder=\"Ev adresim, iş adresim vb.\"]"));
        adresbaşlığı.sendKeys("Ev Adresi");
        WebElement isimKutusu = driver.findElement(By.xpath("//input[@name=\"first_name\"]"));
        isimKutusu.sendKeys("Salih");
        WebElement soyisimKutusu = driver.findElement(By.xpath("//input[@name=\"last_name\"]"));
        soyisimKutusu.sendKeys("Pala");
        WebElement telefonNum = driver.findElement(By.xpath("//input[@name=\"phone_number\"]"));
        telefonNum.sendKeys("5441234567");

        WebElement ilSecimi = driver.findElement(By.xpath("//select[@name='city']"));
        WebElement ilceSecimi = driver.findElement(By.xpath("//select[@name='township']"));
        WebElement mahalleSecimi = driver.findElement(By.xpath("//select[@name='district']"));

            /*Select yardımıyla adres sayfasındaki dropdown menuları kullanarak
            il-ilçe-mahalle kısımlarını dolduruyoruz. Arka arkaya aynı adresi girdiğimizde sistem hata
             verdiğinden dolayı random class  ile il,ilçe ve mahalle indexlerini rastgele atıyoruz.
            Böylece her seferinde otomatik olarak farklı bir adres ile test edebilme imkanımız oluyor.
                         */
        Select select = new Select(ilSecimi);
        Random random=new Random();
        int şehirIndex=random.nextInt(80);
        if (şehirIndex==0) şehirIndex+=1;
        select.selectByIndex(1);

        Thread.sleep(2000);

        Select select1 = new Select(ilceSecimi);
        Random random1=new Random();

        List<WebElement> ilçeList=driver.findElements(By.xpath("//select[@name=\"township\"]/option"));
        int ilçeIndex=random1.nextInt(ilçeList.size()-1);
        if (ilçeIndex==0)  ilçeIndex+=1;

        select1.selectByIndex(ilçeIndex);


        Thread.sleep(1000);

        Select select2 = new Select(mahalleSecimi);
        Random random2=new Random();

        List<WebElement> mahalleList=driver.findElements(By.xpath("//select[@name=\"district\"]/option"));
        int mahalleIndex= random2.nextInt(mahalleList.size()-1);
        if (mahalleIndex==0) mahalleIndex+=1;

        select2.selectByIndex(mahalleIndex);


        Thread.sleep(1000);

        WebElement adresKutusu = driver.findElement(By.xpath("//textarea[@name=\"line\"]"));

        adresKutusu.sendKeys("Burhaniye Mh, Nagehan Sokağı No: 4B D:1");

        WebElement kaydetButonu = driver.findElement(By.xpath("//button[@class='button green address-modal-submit-button js-set-country js-prevent-emoji js-address-form-submit-button']"));

        //Adres bilgilerini doldurup kaydet butonuna tıkladık.
        Thread.sleep(1000);
        kaydetButonu.click();

        Thread.sleep(5000);

        //Adres bilgilerini dolduruğ "Kaydet" e tıkladığımızda bizi adres ve kargo bilgilerinin yer aldığı
        //checkout sayfasına götürüp götürmediğini doğruluyoruz.

        Assert.assertEquals("https://www.a101.com.tr/orders/checkout/",driver.getCurrentUrl());

        //Adres oluşturulduktan sonra açılan ekranda kargo seçeneğini seçiyoruz ve "kaydet ve devam et" butonuna tıklıyoruz.
        WebElement kaydetVeÇık = driver.findElement(By.xpath("//button[@data-index='1']"));
        WebElement kargoRadioButton=driver.findElement(By.xpath("//input[@value='333']"));

        Actions actions1=new Actions(driver);
        actions1.moveToElement(kargoRadioButton).click().perform();

        kaydetVeÇık.click();




            //Ödeme ekranına geldik ve bu sayfada olduğumuzu doğruluyoruz.

        Assert.assertEquals("https://www.a101.com.tr/orders/checkout/",driver.getCurrentUrl());






        }


        }




