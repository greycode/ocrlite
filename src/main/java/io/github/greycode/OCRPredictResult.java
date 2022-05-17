package io.github.greycode;

import java.util.List;

public class OCRPredictResult {
  private List<List<Integer>> box;

  private String text;
  private float score = -1.0f;
  private float clsScore;
  private int clsLabel = -1;

  public OCRPredictResult() {
  }

  public OCRPredictResult(List<List<Integer>> box, String text, float score, float clsScore, int clsLabel) {
    this.box = box;
    this.text = text;
    this.score = score;
    this.clsScore = clsScore;
    this.clsLabel = clsLabel;
  }

  public List<List<Integer>> getBox() {
    return box;
  }

  public void setBox(List<List<Integer>> box) {
    this.box = box;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public float getScore() {
    return score;
  }

  public void setScore(float score) {
    this.score = score;
  }

  public float getClsScore() {
    return clsScore;
  }

  public void setClsScore(float clsScore) {
    this.clsScore = clsScore;
  }

  public int getClsLabel() {
    return clsLabel;
  }

  public void setClsLabel(int clsLabel) {
    this.clsLabel = clsLabel;
  }
}
