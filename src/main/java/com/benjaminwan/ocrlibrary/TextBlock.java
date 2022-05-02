package com.benjaminwan.ocrlibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class TextBlock {

  private  ArrayList<Point> boxPoint;
  private float boxScore;
  private  int angleIndex;
  private  float angleScore;
  private  double angleTime;

  private  String text;

  private  float[] charScores;
  private  double crnnTime;
  private  double blockTime;

  public TextBlock(ArrayList<Point> boxPoint, float boxScore, int angleIndex, float angleScore, double angleTime,  String text,  float[] charScores, double crnnTime, double blockTime) {
    super();
    this.boxPoint = boxPoint;
    this.boxScore = boxScore;
    this.angleIndex = angleIndex;
    this.angleScore = angleScore;
    this.angleTime = angleTime;
    this.text = text;
    this.charScores = charScores;
    this.crnnTime = crnnTime;
    this.blockTime = blockTime;
  }


  public ArrayList<Point> getBoxPoint() {
    return this.boxPoint;
  }

  public float getBoxScore() {
    return this.boxScore;
  }

  public void setBoxScore(float var1) {
    this.boxScore = var1;
  }

  public int getAngleIndex() {
    return this.angleIndex;
  }

  public float getAngleScore() {
    return this.angleScore;
  }

  public double getAngleTime() {
    return this.angleTime;
  }

  public String getText() {
    return this.text;
  }

  public float[] getCharScores() {
    return this.charScores;
  }

  public double getCrnnTime() {
    return this.crnnTime;
  }

  public double getBlockTime() {
    return this.blockTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TextBlock textBlock = (TextBlock) o;
    return Float.compare(textBlock.boxScore, boxScore) == 0 && angleIndex == textBlock.angleIndex && Float.compare(textBlock.angleScore, angleScore) == 0 && Double.compare(textBlock.angleTime, angleTime) == 0 && Double.compare(textBlock.crnnTime, crnnTime) == 0 && Double.compare(textBlock.blockTime, blockTime) == 0 && Objects.equals(boxPoint, textBlock.boxPoint) && Objects.equals(text, textBlock.text) && Arrays.equals(charScores, textBlock.charScores);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(boxPoint, boxScore, angleIndex, angleScore, angleTime, text, crnnTime, blockTime);
    result = 31 * result + Arrays.hashCode(charScores);
    return result;
  }

  public  ArrayList<Point> component1() {
    return this.boxPoint;
  }

  public  float component2() {
    return this.boxScore;
  }

  public  int component3() {
    return this.angleIndex;
  }

  public  float component4() {
    return this.angleScore;
  }

  public  double component5() {
    return this.angleTime;
  }


  public  String component6() {
    return this.text;
  }


  public  float[] component7() {
    return this.charScores;
  }

  public  double component8() {
    return this.crnnTime;
  }

  public double component9() {
    return this.blockTime;
  }


  public TextBlock copy(ArrayList<Point> boxPoint, float boxScore, int angleIndex, float angleScore, double angleTime, String text, float[] charScores, double crnnTime, double blockTime) {
    return new TextBlock(boxPoint, boxScore, angleIndex, angleScore, angleTime, text, charScores, crnnTime, blockTime);
  }

  public String toString() {
    return "TextBlock(boxPoint=" + this.boxPoint + ", boxScore=" + this.boxScore + ", angleIndex=" + this.angleIndex + ", angleScore=" + this.angleScore + ", angleTime=" + this.angleTime + ", text=" + this.text + ", charScores=" + Arrays.toString(this.charScores) + ", crnnTime=" + this.crnnTime + ", blockTime=" + this.blockTime + ')';
  }
}
