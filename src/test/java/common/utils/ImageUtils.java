package common.utils;

import aquality.selenium.browser.AqualityServices;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.util.UUID;
import javax.imageio.ImageIO;

public class ImageUtils {

  public static boolean compareImages(String url, File file) {

    AqualityServices.getLogger().info("Comparing image from " + url.substring(0, 20) + "..." + " to " + file.getName());

    File siteFile = downloadImage(url);
    float v = compareImages(siteFile, file);
    return v > 90;
  }

  private static File downloadImage(String url) {

    try {
      URL imageURL = new URL(url);

      BufferedImage saveImage = ImageIO.read(imageURL);
      String filePath = "./" + UUID.randomUUID().toString().substring(0, 10) + ".jpg";
      File file = new File(filePath);
      ImageIO.write(saveImage, "jpg", file);
      return file;

    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  private static float compareImages(File fileA, File fileB) {

    float percentage;
    try {

      BufferedImage biA = ImageIO.read(fileA);
      DataBuffer dbA = biA.getData().getDataBuffer();
      int sizeA = dbA.getSize();
      BufferedImage biB = ImageIO.read(fileB);
      DataBuffer dbB = biB.getData().getDataBuffer();
      int sizeB = dbB.getSize();
      int count = 0;

      if (sizeA == sizeB) {
        for (int i = 0; i < sizeA; i++) {
          if (dbA.getElem(i) == dbB.getElem(i)) {
            count = count + 1;
          }
        }
        percentage = (count * 100) / sizeA;
      } else {
        percentage = 0;
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
    return percentage;
  }
}