package com.github.greycode;

import com.benjaminwan.ocrlibrary.OcrEngine;
import com.benjaminwan.ocrlibrary.OcrResult;

import java.net.URL;

public class OcrDriver {
  private OcrDriver() { }

  private static volatile OcrEngine ocrEngine;
  private static volatile OcrConfig orcConfig;

  public static OcrEngine getOcrEngine() {
    if (ocrEngine == null) {
      synchronized (OcrDriver.class) {
        if (ocrEngine == null) {
          ocrEngine = new OcrEngine();
        }
      }
    }
    return ocrEngine;
  }

  public static OcrConfig getOcrConfig() {
    if (orcConfig == null) {
      synchronized (OcrDriver.class) {
        if (orcConfig == null) {
          orcConfig = new OcrConfig();
        }
      }
    }
    return orcConfig;
  }

  public static OcrEngine initializeEngine() {
    OcrEngine ocrEngine = getOcrEngine();

    OcrConfig cfg = getOcrConfig();

    URL url = OcrDriver.class.getClassLoader().getResource("models");
    if (url == null) {
      throw new OcrException("Unable to find models on classpath");
    }

    cfg.setModelsDir(url.getPath());

    initEngine(ocrEngine, cfg);

    return ocrEngine;
  }

  public static OcrEngine initializeEngine(String modelsDir) {
    OcrEngine ocrEngine = getOcrEngine();

    OcrConfig cfg = getOcrConfig();

    cfg.setModelsDir(modelsDir);

    initEngine(ocrEngine, cfg);

    return ocrEngine;
  }

  private static void initEngine(OcrEngine ocrEngine, OcrConfig cfg) {
    ocrEngine.setNumThread(cfg.getNumThread());

    //------- init Logger -------
    ocrEngine.initLogger(
      true,
      false,
      false
    );

    //------- init Models -------
    boolean initModelsRet =
      ocrEngine.initModels(cfg.getModelsDir(), cfg.getDetName(), cfg.getClsName(), cfg.getRecName(), cfg.getKeysName());
    if (!initModelsRet) {
      throw new OcrException("Error in models initialization, please check the models/keys path!");
    }

    //------- set param -------
    ocrEngine.setPadding(cfg.getPadding());
    ocrEngine.setBoxScoreThresh(cfg.getBoxScoreThresh());
    ocrEngine.setBoxThresh(cfg.getBoxThresh());
    ocrEngine.setUnClipRatio(cfg.getUnClipRatio());
    ocrEngine.setDoAngle(cfg.getDoAngleFlag() == 1);
    ocrEngine.setMostAngle(cfg.getMostAngleFlag() == 1);
  }

  public static OcrTextResult detect(String imagePath) {
    OcrConfig cfg = getOcrConfig();

    return new OcrTextResult(
      getOcrEngine().detect(
        imagePath,
        cfg.getPadding(),
        cfg.getMaxSideLen(),
        cfg.getBoxScoreThresh(),
        cfg.getBoxThresh(),
        cfg.getUnClipRatio(),
        cfg.getDoAngleFlag() == 1,
        cfg.getMostAngleFlag() == 1)
    );
  }

  public static OcrResult detectOrig(String imagePath) {
    OcrConfig cfg = getOcrConfig();

    return getOcrEngine().detect(
        imagePath,
        cfg.getPadding(),
        cfg.getMaxSideLen(),
        cfg.getBoxScoreThresh(),
        cfg.getBoxThresh(),
        cfg.getUnClipRatio(),
        cfg.getDoAngleFlag() == 1,
        cfg.getMostAngleFlag() == 1
    );
  }
}
