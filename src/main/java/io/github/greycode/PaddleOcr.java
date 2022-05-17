package io.github.greycode;

import java.util.List;
import java.util.StringJoiner;

///
///  所有配置请参考 PaddleOcr 中 deploy/cpp_infer/readme_ch.md 说明<br/>
///  参考文档链接 https://gitee.com/paddlepaddle/PaddleOCR/blob/release/2.5/deploy/cpp_infer/readme_ch.md
///

public class PaddleOcr {

  /**
   * 前向是否执行文字检测
   */
  private boolean useDet;
  /**
   * 前向是否执行文字方向分类
   */
  private boolean useCls;
  /**
   * 是否使用方向分类器
   */
  private boolean useAngleCls;
  /**
   * 前向是否执行文字识别
   */
  private boolean useRec;

  /**
   * 是否使用GPU
   */
  private boolean useGpu;
  /**
   * GPU id，使用GPU时有效
   */
  private int gpuId;
  /**
   * 申请的GPU内存
   */
  private int gpuMem;
  /**
   * CPU预测时的线程数，在机器核数充足的情况下，该值越大，预测速度越快
   */
  private int cpuMath_LibraryNumThreads;
  /**
   * 是否使用mkldnn库
   */
  private boolean useMkldnn;

  /**
   * 默认 960，表示：输入图像长宽大于960时，等比例缩放图像，使得图像最长边为960
   */
  private int maxSideLen;
  /**
   * 用于过滤DB预测的二值化图像，设置为0.-0.3对结果影响不明显
   */
  private double detDbThresh;
  /**
   * DB后处理过滤box的阈值，如果检测存在漏框情况，可酌情减小
   */
  private double detDbBoxThresh;
  /**
   * 表示文本框的紧致程度，越小则文本框更靠近文本
   */
  private double detDbUnclipRatio;
  /**
   * slow:使用多边形框计算bbox score，fast:使用矩形框计算。矩形框计算速度更快，多边形框对弯曲文本区域计算更准确。
   */
  private String detDbScoreMode;
  /**
   * Whether use the dilation on output map.
   */
  private boolean useDilation;

  /**
   * 方向分类器的得分阈值
   */
  private double clsThresh;
  /**
   * 方向分类器batchsize
   */
  private int clsBatchNum;

  /**
   * 字典文件
   */
  private String labelPath;
  /**
   * 识别模型batchsize
   */
  private int recBatchNum;
  /**
   * 识别模型输入图像高度
   */
  private int recImgH;
  /**
   * 识别模型输入图像宽度
   */
  private int recImgW;

  /**
   * Whether use tensorrt
   */
  private boolean useTensorrt;

  /**
   * Precision be one of fp32/fp16/int8
   */
  private String precision;

  /**
   * 检测模型inference model地址
   */
  private String detModelDir;

  /**
   * 方向分类器inference model地址
   */
  private String clsModelDir;

  /**
   * 识别模型inference model地址
   */
  private String recModelDir;

  /**
   * 输入图片的目录地址，不用设置，保持默认即可。
   */
  private String imageDir;

  public PaddleOcr() {
    loadLib();
    configDefaultProps();
  }

  private void configDefaultProps() {
    useDet = true;
    useCls = true;
    useAngleCls = true;
    useRec = true;

    this.useGpu = false;
    this.gpuId = 0;
    this.gpuMem = 4000;
    this.cpuMath_LibraryNumThreads = 10;
    this.useMkldnn = false;
    this.maxSideLen = 960;
    this.detDbThresh = 0.3;
    this.detDbBoxThresh = 0.6;
    this.detDbUnclipRatio = 1.5;
    this.detDbScoreMode = "slow";
    this.useDilation = false;
    this.clsThresh = 0.9;
    this.clsBatchNum = 1;
    this.labelPath = "ppocr_keys_v1.txt";
    this.recBatchNum = 6;
    this.recImgH = 32;
    this.recImgW = 320;
    this.useTensorrt = false;
    this.precision = "fp32";

    this.detModelDir = "./det_db";
    this.clsModelDir = "./cls";
    this.recModelDir = "./rec_crnn";
    this.imageDir = ".";
  }

  private void loadLib() {
    try {
      JniLoader.load(System.mapLibraryName("paddle_inference"));
      JniLoader.load(System.mapLibraryName("PaddleOcrJni"));
    } catch (Exception var2) {
      var2.printStackTrace();
    }
  }

  public PaddleOcr(
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
    boolean useTensorrt, String precision) {

    loadLib();

    this.useDet = useDet;
    this.useCls = useCls;
    this.useAngleCls = useAngleCls;
    this.useRec = useRec;
    this.useGpu = useGpu;
    this.gpuId = gpuId;
    this.gpuMem = gpuMem;
    this.cpuMath_LibraryNumThreads = cpuMath_LibraryNumThreads;
    this.useMkldnn = useMkldnn;
    this.maxSideLen = maxSideLen;
    this.detDbThresh = detDbThresh;
    this.detDbBoxThresh = detDbBoxThresh;
    this.detDbUnclipRatio = detDbUnclipRatio;
    this.detDbScoreMode = detDbScoreMode;
    this.useDilation = useDilation;
    this.clsThresh = clsThresh;
    this.clsBatchNum = clsBatchNum;
    this.labelPath = labelPath;
    this.recBatchNum = recBatchNum;
    this.recImgH = recImgH;
    this.recImgW = recImgW;
    this.useTensorrt = useTensorrt;
    this.precision = precision;

    this.detModelDir = detModelDir;
    this.clsModelDir = clsModelDir;
    this.recModelDir = recModelDir;
    this.imageDir = imageDir;
  }

  public PaddleOcr with(String detModelDir, String clsModelDir, String recModelDir,
                        String recDictPath) {

    this.detModelDir = detModelDir;
    this.clsModelDir = clsModelDir;
    this.recModelDir = recModelDir;
    this.labelPath = recDictPath;

    return this;
  }

  public boolean init() {
    return init(
      this.detModelDir, this.clsModelDir, this.recModelDir,
      this.labelPath
    );
  }

  public boolean initAll() {
    return initFull(
      this.useDet, this.useCls, this.useAngleCls, this.useRec,
      this.detModelDir, this.clsModelDir, this.recModelDir,
      this.imageDir,
      this.useGpu, this.gpuId, this.gpuMem,
      this.cpuMath_LibraryNumThreads,
      this.useMkldnn, this.maxSideLen,
      this.detDbThresh, this.detDbBoxThresh,
      this.detDbUnclipRatio, this.detDbScoreMode,
      this.useDilation, this.clsThresh,
      this.clsBatchNum, this.labelPath, this.recBatchNum,
      this.recImgH, this.recImgW,
      this.useTensorrt, this.precision
    );
  }

  public boolean init(
    boolean useDet, boolean useCls, boolean useAngleCls, boolean useRec,
    boolean useGpu, int gpuId, int gpuMem,
    int cpuMath_LibraryNumThreads,
    boolean useMkldnn, int maxSideLen,
    double detDbThresh, double detDbBoxThresh,
    double detDbUnclipRatio, String detDbScoreMode,
    boolean useDilation, double clsThresh,
    int clsBatchNum, String labelPath, int recBatchNum,
    int recImgH, int recImgW,
    boolean useTensorrt, String precision) {
    return initFull(
      useDet, useCls, useAngleCls, useRec,
      this.detModelDir, this.clsModelDir, this.recModelDir,
      this.imageDir,
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
  }

  public boolean init(String detModelDir, String clsModelDir, String recModelDir,
                      String recDictPath) {
    return initLite(useDet, useCls, useAngleCls, useRec,
                    detModelDir, clsModelDir, recModelDir, recDictPath);
  }

  public boolean init(boolean useDet, boolean useCls, boolean useAngleCls, boolean useRec,
                      String detModelDir,String clsModelDir,String recModelDir,
                      String recDictPath) {
    this.useDet = useDet;
    this.useCls = useCls;
    this.useAngleCls = useAngleCls;
    this.useRec = useRec;
    return initLite(useDet, useCls, useAngleCls, useRec,
      detModelDir, clsModelDir, recModelDir, recDictPath);
  }

  public List<List<OCRPredictResult>> ocr(String imgDir) {
    return ocr(imgDir, useDet, useRec, useCls);
  }

  public final native boolean initLite(
    boolean use_det, boolean use_cls, boolean use_angle_cls, boolean use_rec,
    String det_model_dir, String cls_model_dir, String rec_model_dir,
    String rec_char_dict_path);

  public final native boolean initFull(
    boolean use_det, boolean use_cls, boolean use_angle_cls, boolean use_rec,
    String det_model_dir, String cls_model_dir, String rec_model_dir,
    String image_dir,
    boolean use_gpu,
    int gpu_id,
    int gpu_mem,
    int cpu_math_library_num_threads,
    boolean use_mkldnn,

    int max_side_len,
    double det_db_thresh,
    double det_db_box_thresh,
    double det_db_unclip_ratio,
    String det_db_score_mode,
    boolean use_dilation,

    double cls_thresh,
    int cls_batch_num,

    String label_path,
    int rec_batch_num,
    int rec_img_h,
    int rec_img_w,

    boolean use_tensorrt,
    String precision);

  public final native List<List<OCRPredictResult>> ocr(String imageDir, boolean use_det, boolean use_rec, boolean use_cls);

  public boolean isUseDet() {
    return useDet;
  }

  public PaddleOcr setUseDet(boolean useDet) {
    this.useDet = useDet;
    return this;
  }

  public boolean isUseCls() {
    return useCls;
  }

  public PaddleOcr setUseCls(boolean useCls) {
    this.useCls = useCls;
    return this;
  }

  public boolean isUseAngleCls() {
    return useAngleCls;
  }

  public PaddleOcr setUseAngleCls(boolean useAngleCls) {
    this.useAngleCls = useAngleCls;
    return this;
  }

  public boolean isUseRec() {
    return useRec;
  }

  public PaddleOcr setUseRec(boolean useRec) {
    this.useRec = useRec;
    return this;
  }

  public boolean isUseGpu() {
    return useGpu;
  }

  public PaddleOcr setUseGpu(boolean useGpu) {
    this.useGpu = useGpu;
    return this;
  }

  public int getGpuId() {
    return gpuId;
  }

  public PaddleOcr setGpuId(int gpuId) {
    this.gpuId = gpuId;
    return this;
  }

  public int getGpuMem() {
    return gpuMem;
  }

  public PaddleOcr setGpuMem(int gpuMem) {
    this.gpuMem = gpuMem;
    return this;
  }

  public int getCpuMath_LibraryNumThreads() {
    return cpuMath_LibraryNumThreads;
  }

  public PaddleOcr setCpuMath_LibraryNumThreads(int cpuMath_LibraryNumThreads) {
    this.cpuMath_LibraryNumThreads = cpuMath_LibraryNumThreads;
    return this;
  }

  public boolean isUseMkldnn() {
    return useMkldnn;
  }

  public PaddleOcr setUseMkldnn(boolean useMkldnn) {
    this.useMkldnn = useMkldnn;
    return this;
  }

  public int getMaxSideLen() {
    return maxSideLen;
  }

  public PaddleOcr setMaxSideLen(int maxSideLen) {
    this.maxSideLen = maxSideLen;
    return this;
  }

  public double getDetDbThresh() {
    return detDbThresh;
  }

  public PaddleOcr setDetDbThresh(double detDbThresh) {
    this.detDbThresh = detDbThresh;
    return this;
  }

  public double getDetDbBoxThresh() {
    return detDbBoxThresh;
  }

  public PaddleOcr setDetDbBoxThresh(double detDbBoxThresh) {
    this.detDbBoxThresh = detDbBoxThresh;
    return this;
  }

  public double getDetDbUnclipRatio() {
    return detDbUnclipRatio;
  }

  public PaddleOcr setDetDbUnclipRatio(double detDbUnclipRatio) {
    this.detDbUnclipRatio = detDbUnclipRatio;
    return this;
  }

  public String getDetDbScoreMode() {
    return detDbScoreMode;
  }

  public PaddleOcr setDetDbScoreMode(String detDbScoreMode) {
    this.detDbScoreMode = detDbScoreMode;
    return this;
  }

  public boolean isUseDilation() {
    return useDilation;
  }

  public PaddleOcr setUseDilation(boolean useDilation) {
    this.useDilation = useDilation;
    return this;
  }

  public double getClsThresh() {
    return clsThresh;
  }

  public PaddleOcr setClsThresh(double clsThresh) {
    this.clsThresh = clsThresh;
    return this;
  }

  public int getClsBatchNum() {
    return clsBatchNum;
  }

  public PaddleOcr setClsBatchNum(int clsBatchNum) {
    this.clsBatchNum = clsBatchNum;
    return this;
  }

  public String getLabelPath() {
    return labelPath;
  }

  public PaddleOcr setLabelPath(String labelPath) {
    this.labelPath = labelPath;
    return this;
  }

  public int getRecBatchNum() {
    return recBatchNum;
  }

  public PaddleOcr setRecBatchNum(int recBatchNum) {
    this.recBatchNum = recBatchNum;
    return this;
  }

  public int getRecImgH() {
    return recImgH;
  }

  public PaddleOcr setRecImgH(int recImgH) {
    this.recImgH = recImgH;
    return this;
  }

  public int getRecImgW() {
    return recImgW;
  }

  public PaddleOcr setRecImgW(int recImgW) {
    this.recImgW = recImgW;
    return this;
  }

  public boolean isUseTensorrt() {
    return useTensorrt;
  }

  public PaddleOcr setUseTensorrt(boolean useTensorrt) {
    this.useTensorrt = useTensorrt;
    return this;
  }

  public String getPrecision() {
    return precision;
  }

  public PaddleOcr setPrecision(String precision) {
    this.precision = precision;
    return this;
  }

  public String getDetModelDir() {
    return detModelDir;
  }

  public PaddleOcr setDetModelDir(String detModelDir) {
    this.detModelDir = detModelDir;
    return this;
  }

  public String getClsModelDir() {
    return clsModelDir;
  }

  public PaddleOcr setClsModelDir(String clsModelDir) {
    this.clsModelDir = clsModelDir;
    return this;
  }

  public String getRecModelDir() {
    return recModelDir;
  }

  public PaddleOcr setRecModelDir(String recModelDir) {
    this.recModelDir = recModelDir;
    return this;
  }

  public String getImageDir() {
    return imageDir;
  }

  public PaddleOcr setImageDir(String imageDir) {
    this.imageDir = imageDir;
    return this;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", PaddleOcr.class.getSimpleName() + "[", "]")
      .add("useDet=" + useDet)
      .add("useCls=" + useCls)
      .add("useAngleCls=" + useAngleCls)
      .add("useRec=" + useRec)
      .add("useGpu=" + useGpu)
      .add("gpuId=" + gpuId)
      .add("gpuMem=" + gpuMem)
      .add("cpuMath_LibraryNumThreads=" + cpuMath_LibraryNumThreads)
      .add("useMkldnn=" + useMkldnn)
      .add("maxSideLen=" + maxSideLen)
      .add("detDbThresh=" + detDbThresh)
      .add("detDbBoxThresh=" + detDbBoxThresh)
      .add("detDbUnclipRatio=" + detDbUnclipRatio)
      .add("detDbScoreMode='" + detDbScoreMode + "'")
      .add("useDilation=" + useDilation)
      .add("clsThresh=" + clsThresh)
      .add("clsBatchNum=" + clsBatchNum)
      .add("labelPath='" + labelPath + "'")
      .add("recBatchNum=" + recBatchNum)
      .add("recImgH=" + recImgH)
      .add("recImgW=" + recImgW)
      .add("useTensorrt=" + useTensorrt)
      .add("precision='" + precision + "'")
      .add("detModelDir='" + detModelDir + "'")
      .add("clsModelDir='" + clsModelDir + "'")
      .add("recModelDir='" + recModelDir + "'")
      .add("imageDir='" + imageDir + "'")
      .toString();
  }
}
