package com.benjaminwan.ocrlibrary;

import io.github.greycode.JniLoader;

public class OcrEngine {
  private int padding;
  private float boxScoreThresh;
  private float boxThresh;
  private float unClipRatio;
  private boolean doAngle;
  private boolean mostAngle;

  public OcrEngine() {
    try {
      JniLoader.load(System.mapLibraryName("OcrLiteOnnx"));
    } catch (Exception var2) {
      var2.printStackTrace();
    }

    this.padding = 50;
    this.boxScoreThresh = 0.6F;
    this.boxThresh = 0.3F;
    this.unClipRatio = 2.0F;
    this.doAngle = true;
    this.mostAngle = true;
  }

  public final int getPadding() {
    return this.padding;
  }

  public final void setPadding(int var1) {
    this.padding = var1;
  }

  public final float getBoxScoreThresh() {
    return this.boxScoreThresh;
  }

  public final void setBoxScoreThresh(float var1) {
    this.boxScoreThresh = var1;
  }

  public final float getBoxThresh() {
    return this.boxThresh;
  }

  public final void setBoxThresh(float var1) {
    this.boxThresh = var1;
  }

  public final float getUnClipRatio() {
    return this.unClipRatio;
  }

  public final void setUnClipRatio(float var1) {
    this.unClipRatio = var1;
  }

  public final boolean getDoAngle() {
    return this.doAngle;
  }

  public final void setDoAngle(boolean var1) {
    this.doAngle = var1;
  }

  public final boolean getMostAngle() {
    return this.mostAngle;
  }

  public final void setMostAngle(boolean var1) {
    this.mostAngle = var1;
  }

  public final OcrResult detect(String input, int maxSideLen) {
    return this.detect(input, this.padding, maxSideLen, this.boxScoreThresh, this.boxThresh, this.unClipRatio, this.doAngle, this.mostAngle);
  }

  public final native boolean setNumThread(int var1);

  public final native void initLogger(boolean var1, boolean var2, boolean var3);

  public final native void enableResultText(String var1);

  public final native boolean initModels(String var1, String var2, String var3, String var4, String var5);

  public final native String getVersion();

  public final native OcrResult detect(String var1, int var2, int var3, float var4, float var5, float var6, boolean var7, boolean var8);
}
