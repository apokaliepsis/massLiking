import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import web.TestBaseWeb;

public class InstagramTest extends TestBaseWeb {

    private static final int FOLLOWERS_COUNT = 32;
    private static int UNSUBSCRIBE_COUNT = 12;
    private static final String LOGIN = "antonio.alekseev";
    //private static final String LOGIN = "liliya_stefan_official";

    @Test
    public void increaseFollowers() {
        List<String> list = getHashtag();
        System.out.println(list);
        String hashTag = String.valueOf(list.get((new Random()).nextInt(list.size())));
        int countScrollingPhoto = 0;
        int countLikes = 0;

        try {
            runDriverSelenium();
            openPage("Start test instagram", "https://www.instagram.com");
            //Авторизация
            setAuthorize();
            driverSelenium.findElementByXPath("//button[text()='Не сейчас']").click();
            //driverSelenium.findElementByXPath("//button[text()='Не сейчас']").click();
            openPage("Hashtag: " + hashTag, "https://www.instagram.com/explore/tags/" + hashTag);
            driverSelenium.findElementByCssSelector("#react-root > section > main > article > div.EZdmt > div > div > div:nth-child(1) > div:nth-child(1)").click();
            String likeElement = "//*[@class='QBdPU rrUvL']//*[name()='svg' and @aria-label='Нравится' and @color='#262626' and @height='24' and @role='img']";
            String likesPhotoLocator = "//*[@class='_7UhW9   xLCgt        qyrsm KV-D4              fDxYl    T0kll ']/*[name()='span']";
            String subscribeButton = "//*[@class='bY2yH']//*[text()='Подписаться']";
            while(countLikes < FOLLOWERS_COUNT && countScrollingPhoto < 300) {
                logger.info("countScrollingPhoto < 300");
                int likesPhoto = 0;
                if(driverSelenium.findElementsByXPath(likesPhotoLocator).size()>0){
                    likesPhoto = Integer.parseInt(driverSelenium.findElementByXPath(likesPhotoLocator)
                            .getText().replaceAll(" ","").trim());
                }
                logger.info("Likes in the photo: "+likesPhoto);

                int maxLikesPhoto = 500;
                if (driverSelenium.findElementsByXPath(likeElement).size()>0 && driverSelenium.findElementsByXPath(subscribeButton).size()>0
                        && likesPhoto < maxLikesPhoto) {
                        logger.info("Contains LikeButton.jpg");
                    new WebDriverWait(driverSelenium, 10).until(ExpectedConditions.elementToBeClickable(By.xpath(likeElement))).click();

                        logger.info("Click by LikeButton element");
                        ++countLikes;
                        //sikuliWrap().clickByImage("screens/NextRightButton.jpg");
                        logger.info("Click by Subscribe");

                    driverSelenium.findElementByXPath(subscribeButton).click();
                        driverSelenium.findElementByXPath("//*[@class='QBdPU ']//*[name()='svg' and @aria-label='Далее']").click();

                        //pressHotKey(new int[]{KeyEvent.VK_RIGHT});
                        logger.info("Next photo");
                        //driverSelenium.findElement(By.cssSelector("body > div._2dDPU.QPGbb.CkGkG > div.EfHg9 > div > div > div.l8mY4 > button > div > span > svg > path")).click();
                        ++countScrollingPhoto;
                        TimeUnit.SECONDS.sleep(7);

                    } else {
                        //sikuliWrap().clickByImage("screens/NextRightButton.jpg");
                        driverSelenium.findElementByXPath("//*[@class='QBdPU ']//*[name()='svg' and @aria-label='Далее']").click();
                        ++countScrollingPhoto;
                    }
                }

                logger.info("Hashtag: " + hashTag);
                logger.info("Количество пролистываний фото: " + countScrollingPhoto);

            //unsubscribeAction();

        }
        catch (Exception var) {
            logger.info("Аварийное завершение теста instagram");
            logger.warn(var.getMessage());
            logger.info("Hashtag: " + hashTag);
            logger.info("Количество пролистываний фото: " + countScrollingPhoto);
            var.printStackTrace();
            driverSelenium.quit();
        }

    }

    private void setAuthorize() {
        driverSelenium.findElementByName("username").sendKeys(LOGIN);
        driverSelenium.findElementByName("password").sendKeys("");
        driverSelenium.findElement(By.xpath("//*[@type=\"submit\"]")).click();
    }

    private void openPage(String s, String s2) {
        logger.info(s);
        driverSelenium.get(s2);
    }

    private List<String> getHashtag(){
        List<String> result = null;
        try (Stream<String> lines = Files.lines(Paths.get(getPathFromResources("hashtags")))) {
            result = lines.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void unsubscribeAction() {
        logger.info("Unsubscribe from subscriptions");
        String subscriptionsButton = "screens/SubscriptionsButton.jpg";
        String downButton = "screens/DownButton.jpg";
        String regionDownForm = "screens/RegionDownForm.jpg";
        driverSelenium.get("https://www.instagram.com/antonio.alekseev");
        driverSelenium.findElementByXPath("/html/body/div[1]/section/main/div/header/section/ul/li[3]/a").click();

        for(; UNSUBSCRIBE_COUNT > 0; --UNSUBSCRIBE_COUNT) {
            if (sikuliWrap().isContainsElement(subscriptionsButton, 0.7F)) {
                sikuliWrap().clickByImage(subscriptionsButton);
                driverSelenium.findElementByXPath("/html/body/div[6]/div/div/div/div[3]/button[1]").click();
            } else {
                for(int i = 0; i < 9; ++i) {
                    sikuliWrap().clickBySimilarImage(downButton, 0.85F);
                }
            }
        }

    }
    @AfterTest(alwaysRun = true)
    private void closeBrowser(){
        logger.info("Stop test instagram");
        driverSelenium.quit();
    }
}
