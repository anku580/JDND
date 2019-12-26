package edu.udacity.java.nano;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.*;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class WebSocketTestChatApplication {

    @Autowired
    private Environment env;

    private static String BASE_URL = "http://localhost:8090";
    private static String USERNAME = "dummy";
    private static String CHAT_URL = "http://localhost:8090/index?username=" + USERNAME;

    private WebDriver webDriver;



    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
    }

    @Test
    public void chatRoomLoginPage() {
        webDriver.get(BASE_URL);
        Assert.assertEquals(webDriver.getTitle(), "Chat Room Login");
        webDriver.close();
    }

    @Test
    public void chatRoomJoinedPage() {
        webDriver.get(BASE_URL);
        WebElement inputUsername = webDriver.findElement(By.id("username"));
        assertNotNull(inputUsername);
        inputUsername.sendKeys(USERNAME);
        WebElement loginButton = webDriver.findElement(By.className("submit"));
        loginButton.click();
        // This delay makes sure the output comes correctly after login
        WebDriverWait wait = new WebDriverWait(webDriver, 2);
        String getCurrentUrl = webDriver.getCurrentUrl();
        Assert.assertEquals(getCurrentUrl, String.format(CHAT_URL, USERNAME));

//        WebElement onlineUsers =  webDriver.findElement(By.className("chat-num"));
//        Assert.assertEquals(onlineUsers.getText(), "1");
        webDriver.close();
    }

    @Test
    public void chatRoomSendMessage() {
        String message = "Hi, How are you?";
        webDriver.get(CHAT_URL);

        WebElement messageInput = webDriver.findElement(By.id("msg"));
        messageInput.sendKeys(message);

        WebElement sendButton = webDriver.findElement(By.id("sendbtn"));
        sendButton.click();

        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("message-content"), 1));

        List<WebElement> messageElements = webDriver.findElements((By.className("message-content")));
        WebElement messageElement = messageElements.get(messageElements.size() - 1);
        Assert.assertEquals(USERNAME + "：" + message, messageElement.getText());
    }

    @Test
    public void chatRoomExit() {
        webDriver.get(String.format(CHAT_URL, USERNAME));
        WebElement exitApp = webDriver.findElement(By.id("exit-app"));
        exitApp.click();
        Assert.assertEquals(webDriver.getTitle(), "Chat Room Login");
        webDriver.close();
    }
}