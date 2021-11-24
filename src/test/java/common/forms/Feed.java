package common.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class Feed extends Form {

  private final IButton btnMyPage = getElementFactory().getButton(By.xpath("//*[@id='l_pr']"), "MyPage Button");

  public Feed() {
    super(By.xpath("//*[@id='l_pr']"), "MyPage Button");
  }

  public void clickBtnMyPage() {
    btnMyPage.focus();
    btnMyPage.clickAndWait();
  }
}
