package com.benjaminwan.ocrlibrary;

import java.util.ArrayList;
import java.util.Objects;

public class OcrResult extends OcrOutput {
  private  double dbNetTime;

  private  ArrayList<TextBlock> textBlocks;
  private double detectTime;

  private String strRes;

  public OcrResult(double dbNetTime,  ArrayList<TextBlock> textBlocks, double detectTime,  String strRes) {
    super();
    this.dbNetTime = dbNetTime;
    this.textBlocks = textBlocks;
    this.detectTime = detectTime;
    this.strRes = strRes;
  }

  public  double getDbNetTime() {
    return this.dbNetTime;
  }


  public  ArrayList<TextBlock> getTextBlocks() {
    return this.textBlocks;
  }

  public  double getDetectTime() {
    return this.detectTime;
  }

  public  void setDetectTime(double var1) {
    this.detectTime = var1;
  }


  public  String getStrRes() {
    return this.strRes;
  }

  public  void setStrRes( String var1) {
    this.strRes = var1;
  }

  public  double component1() {
    return this.dbNetTime;
  }


  public  ArrayList<TextBlock> component2() {
    return this.textBlocks;
  }

  public  double component3() {
    return this.detectTime;
  }


  public  String component4() {
    return this.strRes;
  }


  public OcrResult copy(double dbNetTime,  ArrayList<TextBlock> textBlocks, double detectTime,  String strRes) {
    return new OcrResult(dbNetTime, textBlocks, detectTime, strRes);
  }

  public String toString() {
    return "OcrResult(dbNetTime=" + this.dbNetTime + ", textBlocks=" + this.textBlocks + ", detectTime=" + this.detectTime + ", strRes=" + this.strRes + ')';
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OcrResult ocrResult = (OcrResult) o;
    return Double.compare(ocrResult.dbNetTime, dbNetTime) == 0 && Double.compare(ocrResult.detectTime, detectTime) == 0 && Objects.equals(textBlocks, ocrResult.textBlocks) && Objects.equals(strRes, ocrResult.strRes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dbNetTime, textBlocks, detectTime, strRes);
  }
}
