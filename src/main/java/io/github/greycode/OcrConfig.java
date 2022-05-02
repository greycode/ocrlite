package io.github.greycode;

public class OcrConfig {

  public OcrConfig() {
  }

  private String modelsDir = "";
  private String detName = "dbnet.onnx";
  private String clsName = "angle_net.onnx";
  private String recName = "crnn_lite_lstm.onnx";
  private String keysName = "keys.txt";

  //------- numThread -------
  private int numThread = Runtime.getRuntime().availableProcessors();

  //------- padding -------
  // 图像外接白框，用于提升识别率，文字框没有正确框住所有文字时，增加此值。
  private int padding = 50;

  //------- maxSideLen -------
  // 按图像长边进行总体缩放，放大增加识别耗时但精度更高，缩小减小耗时但精度降低，maxSideLen=0代表不缩放
  private int maxSideLen = 1024;

  //------- boxScoreThresh -------
  // 文字框置信度门限，文字框没有正确框住所有文字时，减小此值
  private float boxScoreThresh = 0.6f;

  //------- boxThresh -------
  private float boxThresh = 0.3f;

  //------- unClipRatio -------
  // 单个文字框大小倍率，越大时单个文字框越大
  private float unClipRatio = 2f;

  //------- doAngle -------
  // 启用(1)/禁用(0) 文字方向检测，只有图片倒置的情况下(旋转90~270度的图片)，才需要启用文字方向检测
  private int doAngleFlag = 1;

  //------- mostAngle -------
  // 启用(1)/禁用(0) 角度投票(整张图片以最大可能文字方向来识别)，当禁用文字方向检测时，此项也不起作用
  private int mostAngleFlag = 1;

  public String getModelsDir() {
    return modelsDir;
  }

  public void setModelsDir(String modelsDir) {
    this.modelsDir = modelsDir;
  }

  public String getDetName() {
    return detName;
  }

  public void setDetName(String detName) {
    this.detName = detName;
  }

  public String getClsName() {
    return clsName;
  }

  public void setClsName(String clsName) {
    this.clsName = clsName;
  }

  public String getRecName() {
    return recName;
  }

  public void setRecName(String recName) {
    this.recName = recName;
  }

  public String getKeysName() {
    return keysName;
  }

  public void setKeysName(String keysName) {
    this.keysName = keysName;
  }

  public int getNumThread() {
    return numThread;
  }

  public void setNumThread(int numThread) {
    this.numThread = numThread;
  }

  public int getPadding() {
    return padding;
  }

  public void setPadding(int padding) {
    this.padding = padding;
  }

  public int getMaxSideLen() {
    return maxSideLen;
  }

  public void setMaxSideLen(int maxSideLen) {
    this.maxSideLen = maxSideLen;
  }

  public float getBoxScoreThresh() {
    return boxScoreThresh;
  }

  public void setBoxScoreThresh(float boxScoreThresh) {
    this.boxScoreThresh = boxScoreThresh;
  }

  public float getBoxThresh() {
    return boxThresh;
  }

  public void setBoxThresh(float boxThresh) {
    this.boxThresh = boxThresh;
  }

  public float getUnClipRatio() {
    return unClipRatio;
  }

  public void setUnClipRatio(float unClipRatio) {
    this.unClipRatio = unClipRatio;
  }

  public int getDoAngleFlag() {
    return doAngleFlag;
  }

  public void setDoAngleFlag(int doAngleFlag) {
    this.doAngleFlag = doAngleFlag;
  }

  public int getMostAngleFlag() {
    return mostAngleFlag;
  }

  public void setMostAngleFlag(int mostAngleFlag) {
    this.mostAngleFlag = mostAngleFlag;
  }

}
