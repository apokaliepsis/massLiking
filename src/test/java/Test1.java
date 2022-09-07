import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import web.TestBaseWeb;

public class Test1 extends TestBaseWeb {
    @Test
    public void test1(){
        sikuliWrap().clickByImage("screens/TerminalIco.jpg");


    }
}
