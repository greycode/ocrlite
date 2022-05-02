package com.github.greycode;

public class OcrApp {
  public static void main(String[] args) throws Exception {

    OcrDriver.initializeEngine();

    String imagePath = "run-test/test_imgs/c.png";

    OcrTextResult ocrResult = OcrDriver.detect(imagePath);
    System.out.println("\n===========================");
    //------- print result -------
    System.out.println(ocrResult);
  }
}
