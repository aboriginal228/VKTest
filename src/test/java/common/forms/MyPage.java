package common.forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MyPage extends Form {

  ILabel lblName = getElementFactory().getLabel(By.xpath("//*[@id='page_info_wrap']/div[1]/h1"), "Name Label");
  ILabel lblFirstPostName;
  ILabel lblPostText;
  ILabel lblPostCommentAuthor;
  ILabel lblShowComments;
  IButton btnLikeButton;
  ILabel lblPostImage;

  public MyPage(int postId) {
    super(By.xpath("//*[@id='page_info_wrap']/div[1]/h1"), "Name Label");
    lblFirstPostName = getElementFactory().getLabel(By.xpath(String.format("//*[@id='page_wall_posts']/div[contains(@id, '_%d')]//*[contains(@class, 'post_author')]", postId)), "First Post Name Label");
    lblPostText = getElementFactory().getLabel(By.xpath(String.format("//*[@id='page_wall_posts']/div[contains(@id, '_%d')]//*[contains(@class, 'wall_post_text')]", postId)), "First Post Text");
    lblPostImage = getElementFactory().getLabel(By.xpath(String.format("//*[@id='page_wall_posts']/div[contains(@id, '_%d')]//*[contains(@class, 'page_post_sized_thumbs')]/a", postId)), "First Post Image");
    lblPostCommentAuthor = getElementFactory().getLabel(By.xpath(String.format("(//*[@id='page_wall_posts']/div[contains(@id, '_%d')]//*[@class = 'reply_author'])[last()]", postId)), "First Post Last Comment Author");
    lblShowComments = getElementFactory().getLabel(By.xpath(String.format("//*[@id='page_wall_posts']/div[contains(@id, '_%d')]//*[contains(@class, 'replies_list')]/a", postId)), "First Post Last Comment Text");
    btnLikeButton = getElementFactory().getButton(By.xpath(String.format("//*[@id='page_wall_posts']/div[contains(@id, '_%d')]//*[contains(@class, 'like_btns')]/div[contains(@class, 'PostBottomActionContainer')]/div[contains(@class, 'PostBottomAction')]", postId)), "First Post Like Button");
  }

  public boolean isNameAndPostNameEquals() {
    return AqualityServices.getConditionalWait().waitFor(() -> lblFirstPostName.getText().contains(lblName.getText()));
  }

  public void clickLblLikeButton() {
    AqualityServices.getConditionalWait().waitFor(() -> btnLikeButton.state().waitForDisplayed());
    btnLikeButton.focus();
    btnLikeButton.click();
    AqualityServices.getConditionalWait().waitFor(() -> btnLikeButton.getAttribute("data-reaction-counts") != null);
  }

  public boolean isCommentAuthorSame() {
    AqualityServices.getConditionalWait().waitFor(() -> lblShowComments.state().waitForDisplayed());
    lblShowComments.focus();
    lblShowComments.click();
    return AqualityServices.getConditionalWait().waitFor(() -> lblPostCommentAuthor.getText().contains(lblName.getText()));
  }

  public String getImgUrl() {
    String cssValue = lblPostImage.getCssValue("background-image");
    return cssValue.substring(cssValue.indexOf("\"") + 1, cssValue.indexOf("\"", cssValue.indexOf("\"") + 1));
  }

  public boolean isTextSame(String text) {
    return AqualityServices.getConditionalWait().waitFor(() -> lblPostText.getText().equals(text));
  }

  public boolean isPostDeleted() {
    return lblPostText.state().waitForNotDisplayed();
  }
}
