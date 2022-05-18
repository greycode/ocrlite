<!-- PROJECT SHIELDS -->

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

# OcrLite

## 介绍

本项目是对 [PaddleOCR](https://github.com/PaddlePaddle/PaddleOCR) （3.0.0 以上版本）的一个简单封装，详情请参考 PaddleOCR 文档。

> 3.0.0 以下版本封装的 OCR 为 [chineseocr_lite](https://github.com/DayBreak-u/chineseocr_lite) 

## 如何使用

### 3.x.x 版本

Maven 添加依赖：

```xml
<dependency>
  <groupId>io.github.greycode</groupId>
  <artifactId>ocrlite</artifactId>
  <version>3.0.0</version>
</dependency>
```

将 3.x.x 版本专用的 **动态链接库** 放到 ``resouces`` 目录下。

测试代码：

```java
// 注意请务必使用绝对路径
String imagePath = "run-test/test_imgs/c.png";

OcrDriver.initialize();
List<List<OCRPredictResult>> ocrResult = OcrDriver.ocr(imagePath);
```

JAR 包自带的模型为超轻量模型，如果打算使用自定义模型文件，那么请将模型文件放到 resource （编译后为 classes 文件根目录）下，如果您的文件结构如下：

```

resources/
|-- det_db
|   |--inference.pdiparams
|   |--inference.pdmodel
|-- rec_rcnn
|   |--inference.pdiparams
|   |--inference.pdmodel
|-- cls
|   |--inference.pdiparams
|   |--inference.pdmodel
```

那么可以使用如下代码初始化引擎：

```java
// 注意请务必使用绝对路径
String imagePath = "run-test/test_imgs/c.png";

OcrDriver
  .initializeCustomModel("paddle_models")
  .init();

List<List<OCRPredictResult>> ocrResult = OcrDriver.ocr(imagePath);
```

如果您的文件结构与以上不同，那么需要使用其他初始化方法：

```java
OcrDriver
        .initializeCustomModel(
          "det_db",
          "cls",
          "rec_crnn",
          "ppocr_keys_v1.txt",
          "paddle_models")
        .init();
```
> 注意：上面代码中的 ``det_db`` 等仍是相对于 resource 目录的子目录地址

> 注意：如果使用自定义模型，则可以使用更轻量的 -RELEASE 版本
----

### 2.x.x 版本

Maven 添加依赖：

```xml
<dependency>
  <groupId>io.github.greycode</groupId>
  <artifactId>ocrlite</artifactId>
  <version>2.0.2</version>
</dependency>
```

将 2.x.x 版本专用的 **动态链接库** 放到 ``resouces`` 目录下。

测试代码：

```java

OcrDriver.initializeEngine();

String imagePath = "run-test/test_imgs/c.png";

OcrTextResult ocrResult = OcrDriver.detect(imagePath);
```

### 1.x.x 版本

Maven 添加依赖：

```xml
<dependency>
  <groupId>io.github.greycode</groupId>
  <artifactId>ocrlite</artifactId>
  <version>1.0.3-RELEASE</version>
</dependency>
```

将最新的 **动态链接库** 和 **模型文件** 放到 ``resouces`` 目录下。

测试代码：

```java

OcrDriver.initializeEngine();

String imagePath = "run-test/test_imgs/c.png";

OcrTextResult ocrResult = OcrDriver.detect(imagePath);
```


> 特别注意：
>
> 1.0.3-RELEASE 为最新的简易封装版本。
>
> 2.x.x 版本为实验版本，原项目自带的动态链接库不适用。
>
> 3.x.x 版本改为封装 PaddleOCR 。

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/greycode/ocrlite.svg?style=for-the-badge
[contributors-url]: https://github.com/greycode/ocrlite/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/greycode/ocrlite.svg?style=for-the-badge
[forks-url]: https://github.com/greycode/ocrlite/network/members
[stars-shield]: https://img.shields.io/github/stars/greycode/ocrlite.svg?style=for-the-badge
[stars-url]: https://github.com/greycode/ocrlite/stargazers
[issues-shield]: https://img.shields.io/github/issues/greycode/ocrlite.svg?style=for-the-badge
[issues-url]: https://github.com/greycode/ocrlite/issues
[license-shield]: https://img.shields.io/github/license/greycode/ocrlite.svg?style=for-the-badge
[license-url]: https://github.com/greycode/ocrlite/blob/master/LICENSE.txt
