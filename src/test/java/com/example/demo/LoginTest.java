package com.example.demo;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {

        ChromeOptions options = new ChromeOptions();

        // desactivar popup de contraseñas y brechas
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");

        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false
        ));

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.demoblaze.com/index.html");
    }

    public void realizarLogin(String email, String password) {

        WebElement btnLogin = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("login2")
        ));
        btnLogin.click();

        WebElement inputEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("loginusername")
        ));
        inputEmail.clear();
        inputEmail.sendKeys(email);

        WebElement inputPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("loginpassword")
        ));
        inputPassword.clear();
        inputPassword.sendKeys(password);

        WebElement btnSubmitLogin = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@onclick='logIn()']")
        ));
        btnSubmitLogin.click();
    }

    @Test
    @DisplayName("Prueba de Login Fallido")
    public void testLoginConCredentialsInvalid() throws InterruptedException {

        realizarLogin("pedrito123456@gmail.com", "Metro123$$");

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        String alertMsg = alert.getText();
        alert.accept();

        System.out.println("ALERT: " + alertMsg);

        if (!(alertMsg.contains("User") || alertMsg.contains("Wrong password"))) {
            throw new AssertionError("Mensaje inesperado: " + alertMsg);
        }

        System.out.println("Prueba negativa OK");

        Thread.sleep(3000);
    }

    @Test
    @DisplayName("Prueba de Login Exitoso")
    public void testLoginConCredentialsValid()throws InterruptedException {
        realizarLogin("pedrito123456@gmail.com", "123456");

        WebElement userLabel = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser"))
        );

        String texto = userLabel.getText();
        System.out.println("LOGIN OK: " + texto);

        if (!texto.contains("pedrito123456")) {
            throw new AssertionError("Login falló, no coincide el usuario");
        }

        System.out.println("Prueba positiva OK");

        Thread.sleep(3000);
    }

    @Test
    @DisplayName("Compra completa")
    public void testCompraCompleta() throws InterruptedException {

        realizarLogin("pedrito123456@gmail.com", "123456");

        // validar login
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));

        // ir a producto
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='prod.html?idp_=1']")
        )).click();

        // esperar pagina producto
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".name")));

        // agregar al carrito
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Add to cart')]")
        )).click();

        // manejar alert
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        // ir a Cart
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[text()='Cart']")
        )).click();

        // esperar tabla carrito
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));

        // click en Place Order
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Place Order']")
        )).click();

        // llenar formulario
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));

        driver.findElement(By.id("name")).sendKeys("Carlos");
        driver.findElement(By.id("country")).sendKeys("Peru");
        driver.findElement(By.id("city")).sendKeys("Lima");
        driver.findElement(By.id("card")).sendKeys("1234123412341234");
        driver.findElement(By.id("month")).sendKeys("12");
        driver.findElement(By.id("year")).sendKeys("2026");

        // click en Purchase
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Purchase']")
        )).click();

        // validar sweetalert
        WebElement sweetAlert = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".sweet-alert")
                )
        );

        String mensaje = sweetAlert.findElement(By.tagName("h2")).getText();
        System.out.println("MENSAJE: " + mensaje);

        if (!mensaje.contains("Thank you")) {
            throw new AssertionError("Compra no exitosa");
        }

        Thread.sleep(3000);

        // cerrar alerta
        sweetAlert.findElement(By.xpath(".//button[text()='OK']")).click();

        System.out.println("Compra completada OK");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
