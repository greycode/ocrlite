# OcrLite

### 介绍

本项目是对 [chineseocr_lite](https://github.com/DayBreak-u/chineseocr_lite) 的一个简单封装，详情请参考该项目文档。

### 如何使用

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


> 特别注意：1.0.3-RELEASE 为最新的简易封装版本。2.x.x 版本为实验版本，原项目自带的动态链接库不适用。
