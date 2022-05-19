package io.github.greycode;

public class OcrConfig {

  public static final String PDIPARAMS = "inference.pdiparams";
  public static final String PDMODEL = "inference.pdmodel";
  public static final String PDIPARAMS_INFO = "inference.pdiparams.info";
  public static final String PPOCR_KEYS_V1 = "ppocr_keys_v1.txt";

  public static final String DEFAULT_DET_DIR = "det_db";
  public static final String DEFAULT_CLS_DIR = "cls";
  public static final String DEFAULT_REC_DIR = "rec_crnn";

  public static final String LIB_PADDLE_INFERENCE = "paddle_inference";
  public static final String LIB_PADDLE_OCR_JNI = "PaddleOcrJni";

  private OcrConfig() {
  }
}
