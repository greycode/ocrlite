package io.github.greycode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.channels.Channels.newChannel;

public class OcrDriver {
  private OcrDriver() { }

  private static volatile PaddleOcr paddleOcr;

  public static PaddleOcr getPaddleOcr() {
    if (paddleOcr == null) {
      synchronized (OcrDriver.class) {
        if (paddleOcr == null) {
          paddleOcr = new PaddleOcr();
        }
      }
    }
    return paddleOcr;
  }

  ///
  /// 使用默认配置和默认模型文件
  ///
  public static PaddleOcr initialize() throws IOException {
    PaddleOcr ppocr = getPaddleOcr();

    initializeDefaultModel(ppocr);
    ppocr.init();

    return ppocr;
  }

  ///
  /// 使用默认配置，使用自定义模型文件
  ///
  public static PaddleOcr initialize(String detModelDir, String clsModelDir, String recModelDir,
                                     String recDictPath) {
    PaddleOcr ppocr = getPaddleOcr();
    ppocr.with(detModelDir, clsModelDir, recModelDir, recDictPath).init();
    return ppocr;
  }

  ///
  /// 使用自定义配置，使用默认模型文件
  ///
  public static PaddleOcr initialize(
    boolean useDet, boolean useCls, boolean useAngleCls, boolean useRec,
    boolean useGpu, int gpuId, int gpuMem,
    int cpuMath_LibraryNumThreads,
    boolean useMkldnn, int maxSideLen,
    double detDbThresh, double detDbBoxThresh,
    double detDbUnclipRatio, String detDbScoreMode,
    boolean useDilation, double clsThresh,
    int clsBatchNum, String labelPath, int recBatchNum,
    int recImgH, int recImgW,
    boolean useTensorrt, String precision ) throws IOException {

    PaddleOcr ppocr = getPaddleOcr();

    initializeDefaultModel(ppocr);

    ppocr.init(
      useDet, useCls, useAngleCls, useRec,
      useGpu, gpuId, gpuMem,
      cpuMath_LibraryNumThreads,
      useMkldnn, maxSideLen,
      detDbThresh, detDbBoxThresh,
      detDbUnclipRatio, detDbScoreMode,
      useDilation, clsThresh,
      clsBatchNum, labelPath, recBatchNum,
      recImgH, recImgW,
      useTensorrt, precision
    );

    return ppocr;
  }

  ///
  /// 使用自定义配置，使用自定义模型文件
  ///
  public static PaddleOcr initialize(
    boolean useDet, boolean useCls, boolean useAngleCls, boolean useRec,
    boolean useGpu, int gpuId, int gpuMem,
    String detModelDir, String clsModelDir, String recModelDir,
    String imageDir,
    int cpuMath_LibraryNumThreads,
    boolean useMkldnn, int maxSideLen,
    double detDbThresh, double detDbBoxThresh,
    double detDbUnclipRatio, String detDbScoreMode,
    boolean useDilation, double clsThresh,
    int clsBatchNum, String labelPath, int recBatchNum,
    int recImgH, int recImgW,
    boolean useTensorrt, String precision ) {
    PaddleOcr ppocr = getPaddleOcr();

    ppocr.initFull(
      useDet, useCls, useAngleCls, useRec,
      detModelDir, clsModelDir, recModelDir,
      imageDir,
      useGpu, gpuId, gpuMem,
      cpuMath_LibraryNumThreads,
      useMkldnn, maxSideLen,
      detDbThresh, detDbBoxThresh,
      detDbUnclipRatio, detDbScoreMode,
      useDilation, clsThresh,
      clsBatchNum, labelPath, recBatchNum,
      recImgH, recImgW,
      useTensorrt, precision
    );

    return ppocr;
  }

  ///
  /// 将自带模型从 JAR 包复制到外部绝对路径，方便动态库读取
  ///
  public static void initializeDefaultModel(PaddleOcr ppocr) throws IOException {
    copyResouceFile("","", OcrConfig.PPOCR_KEYS_V1);
    ppocr.with(
      copyDefaultResourceModel(OcrConfig.DEFAULT_DET_DIR),
      copyDefaultResourceModel(OcrConfig.DEFAULT_CLS_DIR),
      copyDefaultResourceModel(OcrConfig.DEFAULT_REC_DIR),
      new File(OcrConfig.PPOCR_KEYS_V1).getAbsolutePath()
    );
  }

  ///
  /// 将自带模型从 JAR 包复制到外部绝对路径，方便动态库读取
  ///
  public static PaddleOcr initializeCustomModel(String targetDir) throws IOException {
    copyResouceFile("",targetDir, OcrConfig.PPOCR_KEYS_V1);
    PaddleOcr ppocr = getPaddleOcr();
    ppocr.with(
      copyResourceModel(OcrConfig.DEFAULT_DET_DIR, targetDir + File.separator +  OcrConfig.DEFAULT_DET_DIR),
      copyResourceModel(OcrConfig.DEFAULT_CLS_DIR, targetDir + File.separator +  OcrConfig.DEFAULT_CLS_DIR),
      copyResourceModel(OcrConfig.DEFAULT_REC_DIR, targetDir + File.separator +  OcrConfig.DEFAULT_REC_DIR),
      new File(targetDir, OcrConfig.PPOCR_KEYS_V1).getAbsolutePath()
    );
    return ppocr;
  }

  ///
  /// 将自带模型从 JAR 包复制到外部绝对路径，方便动态库读取
  ///
  public static PaddleOcr initializeCustomModel(String detModelDir, String clsModelDir, String recModelDir,
                                           String recDictPath, String targetParentDir) throws IOException {
    copyResouceFile("",targetParentDir, recDictPath);
    PaddleOcr ppocr = getPaddleOcr();
    ppocr.with(
      copyResourceModel(detModelDir, targetParentDir + File.separator +  OcrConfig.DEFAULT_DET_DIR),
      copyResourceModel(clsModelDir, targetParentDir + File.separator +  OcrConfig.DEFAULT_CLS_DIR),
      copyResourceModel(recModelDir, targetParentDir + File.separator +  OcrConfig.DEFAULT_REC_DIR),
      new File(targetParentDir, OcrConfig.PPOCR_KEYS_V1).getAbsolutePath()
    );
    return ppocr;
  }

  private static String copyDefaultResourceModel(String dir) throws IOException {
    return copyResourceModel(dir, dir);
  }

  ///
  /// 将 resource 目录下模型文件复制到指定目录，并返回复制后目录的绝对路径
  ///
  public static String copyResourceModel(String dir, String targetDir) throws IOException {
    Path dirPath = Files.createDirectories(Paths.get(new File(targetDir).getAbsolutePath()));

    copyResouceFile(dir, targetDir, OcrConfig.PDIPARAMS);
    copyResouceFile(dir, targetDir, OcrConfig.PDIPARAMS_INFO);
    copyResouceFile(dir, targetDir, OcrConfig.PDMODEL);

    return dirPath.toString();
  }

  private static void copyResouceFile(String dir, String targetDir, String fileName) throws IOException {
    String resource = "/" + dir + "/" + fileName;
    if (dir == null || "".equals(dir)) {
      resource = "/" + fileName;
      if (!"".equals(targetDir)) {
        Files.createDirectories(Paths.get(new File(targetDir).getAbsolutePath()));
      }
      targetDir = new File(targetDir).getAbsolutePath();
    }

    try (InputStream in = OcrDriver.class.getResourceAsStream(resource)) {
      File file = new File(targetDir, fileName);
      if (file.exists()) {
        if (!file.isFile()) {
          throw new IllegalArgumentException(file.getAbsolutePath() + " is not a file.");
        }
        file.delete();
      }
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

  public static List<List<OCRPredictResult>> ocr(String imagePath) {
    return getPaddleOcr().ocr(imagePath);
  }

  public static List<List<OCRPredictResult>> ocr(String imagePath, boolean useDet, boolean useRec, boolean useCls) {
    return getPaddleOcr().ocr(imagePath, useDet, useRec, useCls);
  }

}
