package org.sdet.qa;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class LoginTest {
    public ChromeOptions option = new ChromeOptions();
    public WebDriver driver;


    public void start() {
        option.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver", ConfProps.getProperty("chromedriver"));
        driver = new ChromeDriver(option);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void Case1() {
        start();

        driver.get("https://www.saucedemo.com/");

        //Ввод авторизационных данных пользователя
        WebElement TxtBoxContent = driver.findElement(By.id("user-name"));
        TxtBoxContent.sendKeys("standard_user");
        WebElement Password = driver.findElement(By.id("password"));
        Password.sendKeys("secret_sauce");
        WebElement Submit = driver.findElement(By.id("login-button"));
        Submit.click();

        //Добавление товара в корзину и переход к оформлению заказа
        WebElement AddtoCart = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        AddtoCart.click();
        WebElement Cart = driver.findElement(By.cssSelector(".shopping_cart_badge"));
        Cart.click();
        WebElement checkout = driver.findElement(By.id("checkout"));
        checkout.click();

        //Заполнение данных пользователя
        WebElement FirstName = driver.findElement(By.id("first-name"));
        FirstName.sendKeys(ConfProps.getProperty("firstName"));
        WebElement LastName = driver.findElement(By.id("last-name"));
        LastName.sendKeys(ConfProps.getProperty("lastName"));
        WebElement Code = driver.findElement(By.id("postal-code"));
        Code.sendKeys(ConfProps.getProperty("zipCode"));
        WebElement ButtonContinue = driver.findElement(By.id("continue"));
        ButtonContinue.click();
        WebElement ButtonFinish = driver.findElement(By.id("finish"));
        ButtonFinish.click();

        //Проверка корректносит редиректа
        Assert.assertEquals("Проверка адреса страницы", "https://www.saucedemo.com/checkout-complete.html", driver.getCurrentUrl());
        WebElement Success = driver.findElement(By.xpath("//*[@id=\"checkout_complete_container\"]/h2"));

        //Проверка вывода сообщения об успешности операции
        Assert.assertEquals("Проверка успешности операции", ConfProps.getProperty("successMessage"), Success.getText());
        driver.close();
        driver.quit();
    }

    @Test
    public void Case2() {
        start();
        driver.get("https://www.saucedemo.com/");

        //Ввод неверных авторизационных данных
        WebElement TxtBoxContent = driver.findElement(By.id("user-name"));
        TxtBoxContent.sendKeys(ConfProps.getProperty("userName"));
        WebElement Password = driver.findElement(By.id("password"));
        Password.sendKeys(ConfProps.getProperty("password"));
        WebElement Submit = driver.findElement(By.id("login-button"));
        Submit.click();

        WebElement Success = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3"));

        //Проверка вывода ошибки авторизации
        Assert.assertEquals("Проверка вывода ошибки авторизации", ConfProps.getProperty("epicSadface"), Success.getText());

        driver.close();
        driver.quit();
    }

}


