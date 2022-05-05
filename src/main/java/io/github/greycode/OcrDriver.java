package io.github.greycode;

import io.github.greycode.ocrlibrary.OcrEngine;
import io.github.greycode.ocrlibrary.OcrResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import static java.nio.channels.Channels.newChannel;

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

    initializeDefaultModel(cfg);

    initEngine(ocrEngine, cfg);

    return ocrEngine;
  }

  public static void initializeDefaultModel(OcrConfig cfg) {
    if (cfg.getModelsDir() == null || "".equals(cfg.getModelsDir())) {
      cfg.setModelsDir(new File("").getAbsolutePath());
    }
    copyDefaultModelFile(cfg.getModelsDir(), OcrConfig.DEFAULT_CLSNAME);
    copyDefaultModelFile(cfg.getModelsDir(), OcrConfig.DEFAULT_RECNAME);
    copyDefaultModelFile(cfg.getModelsDir(), OcrConfig.DEFAULT_DETNAME);
    copyDefaultModelFile(cfg.getModelsDir(), OcrConfig.DEFAULT_KEYNAME);
  }

  private static void copyDefaultModelFile(String dir, String name) {
    try (InputStream in = OcrDriver.class.getResourceAsStream("/models/" + name)) {
      File file = new File(dir, name);
      if (file.exists() && !file.isFile())
        throw new IllegalArgumentException(file.getAbsolutePath() + " is not a file.");
      if (!file.exists()) {
        file.createNewFile();
        ReadableByteChannel src = newChannel(in);
        try (FileChannel dest = new FileOutputStream(file).getChannel()) {
          dest.transferFrom(src, 0, Long.MAX_VALUE);
        }
      }
    } catch (IOException e) {
      throw new OcrException(e);
    }
  }

  public static OcrEngine initializeEngine(OcrConfig cfg) {
    OcrEngine ocrEngine = getOcrEngine();

    initEngine(ocrEngine, getOcrConfig().copy(cfg));

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
      cfg.isPrintLog(),
      cfg.isSplitImg(),
      cfg.isGenImgTxt()
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
