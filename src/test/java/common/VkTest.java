package common;

import aquality.selenium.browser.AqualityServices;
import common.forms.Feed;
import common.forms.LoginPage;
import common.forms.MyPage;
import common.utils.VkApiUtils;
import common.utils.ConfigUtil;
import common.utils.ImageUtils;
import java.io.File;
import java.util.UUID;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VkTest extends BaseTest{

  @Test
  void test1() {

    LoginPage loginPage = new LoginPage();

    loginPage.submit(ConfigUtil.getUsername(), ConfigUtil.getPassword());

    Feed feed = new Feed();

    feed.clickBtnMyPage();

    String testText = UUID.randomUUID().toString();

    int postId = VkApiUtils.makePost(testText);

    MyPage myPage = new MyPage(postId);

    Assert.assertTrue(myPage.isNameAndPostNameEquals(), "Page owner name and post author name are not equals");
    Assert.assertTrue(myPage.isTextSame(testText), "Post text not equal to provided");

    testText = UUID.randomUUID().toString();

    File image = new File(ConfigUtil.getImg());

    VkApiUtils.changeMessageAndAddPhoto(image, testText, postId);

    Assert.assertTrue(myPage.isTextSame(testText), "Post text not equal to provided");
    Assert.assertTrue(ImageUtils.compareImages(myPage.getImgUrl(), image), "Image is not equal to provided");

    testText = UUID.randomUUID().toString();

    VkApiUtils.leaveCommentToPost(postId, testText);

    Assert.assertTrue(myPage.isCommentAuthorSame(), "Page owner name and comment author name are not equals");

    myPage.clickLblLikeButton();

    Assert.assertTrue(VkApiUtils.checkLike(postId), "Post is not liked by page owner");

    VkApiUtils.deletePost(postId);

    Assert.assertTrue(myPage.isPostDeleted(), "Post is not deleted");
  }
}
