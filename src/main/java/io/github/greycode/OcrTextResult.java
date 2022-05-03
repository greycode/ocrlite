package io.github.greycode;

import io.github.greycode.ocrlibrary.OcrResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class OcrTextResult implements Serializable {

  private static final long serialVersionUID = -8829952278781548196L;

  private double dbNetTime;

  private double detectTime;

  private List<String> textLines;

  public OcrTextResult() {
  }

  public OcrTextResult(OcrResult result) {
   if (result != null) {
     this.dbNetTime = result.getDbNetTime();
     this.detectTime = result.getDetectTime();
     if (result.getTextBlocks() != null) {
       textLines = new ArrayList<>(result.getTextBlocks().size());
       result.getTextBlocks().forEach(textBlock -> {
         textLines.add(textBlock.getText());
       });
     }
   }
  }

  public double getDbNetTime() {
    return dbNetTime;
  }

  public void setDbNetTime(double dbNetTime) {
    this.dbNetTime = dbNetTime;
  }

  public double getDetectTime() {
    return detectTime;
  }

  public void setDetectTime(double detectTime) {
    this.detectTime = detectTime;
  }

  public List<String> getTextLines() {
    return textLines;
  }

  public void setTextLines(List<String> textLines) {
    this.textLines = textLines;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", OcrTextResult.class.getSimpleName() + "[", "]")
      .add("dbNetTime=" + dbNetTime)
      .add("detectTime=" + detectTime)
      .add("textLines=" + textLines)
      .toString();
  }
}
