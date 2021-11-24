package common.forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class LoginPage extends Form {


    private final IButton btnSubmit = getElementFactory().getButton(By.xpath("//*[@id='index_login_button']"), "Submit");
    private final ITextBox txbUsername = getElementFactory().getTextBox(By.xpath("//*[@id='index_email']"), "Email Input");
    private final ITextBox txbPassword = getElementFactory().getTextBox(By.xpath("//*[@id='index_pass']"), "Password Input");

    public LoginPage() {
        super(By.xpath("//*[@id='index_email']"), "Email");
    }

    public void submit(String username, String password) {
        txbUsername.focus();
        txbUsername.type(username);
        txbPassword.focus();
        txbPassword.type(password);
        btnSubmit.focus();
        btnSubmit.clickAndWait();
    }
}
