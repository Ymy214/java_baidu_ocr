# java_baidu_ocr
### Java调用百度OCR文字识别API实现图片文字识别软件
#### 这是一款小巧方便，强大的文字识别软件，由Java编写，配上了窗口界面
#### 调用了百度ocr文字识别API 识别精度高。

# 更新日志

## 新的改变 OcrViewer 2.0 更新于 2019.1.18

#### 我对OcrViewer进行了一些功能拓展与界面美化，除了标准的本地图片识别功能，我增加了如下几点新功能，帮助你更方便使用本工具，进而更好地识别图像中的文字：

 - 1. **全新的界面设计，将会带来全新的使用体验；** 
 - 2. 在创作中心设置你喜爱的代码高亮样式，Markdown **将代码片显示选择的高亮样式** 进行展示；
 - 3. 增加了 **截图识别** 功能，你可以通过截取图片直接直接进行识别；
 - 5. 增加了**图片预览**功能，更加方便进行图文对比，减少容错；
 - 6. 增加了 **清除图片预览** 功能，可以清清除览的图片；
 - 7. 增加了 **重新识别** 等功能，如果不确定可以多次识别提高识别精确率；

## 下面是功能以及新界面展示

- **识别出师表.png**
![avatar](https://www.cnblogs.com/images/cnblogs_com/runtu/1386194/o_%e5%87%ba%e5%b8%88%e8%a1%a8.png)
- **截图出师表 .png识别结果**
![avatar](https://www.cnblogs.com/images/cnblogs_com/runtu/1386194/o_%e6%88%aa%e5%9b%be%e8%af%86%e5%88%ab%e5%87%ba%e5%b8%88%e8%a1%a8%20.png)


- **识别代码.png**
![avatar](https://www.cnblogs.com/images/cnblogs_com/runtu/1386194/o_%e4%bb%a3%e7%a0%81%e8%af%86%e5%88%ab.png)

- **代码.png识别结果**
![avatar](https://www.cnblogs.com/images/cnblogs_com/runtu/1386194/o_%e4%bb%a3%e7%a0%81%e8%af%86%e5%88%ab%e7%bb%93%e6%9e%9c.png)

- **识腾讯新闻弹窗.png**
![avatar](https://www.cnblogs.com/images/cnblogs_com/runtu/1386194/o_%e8%85%be%e8%ae%af%e6%96%b0%e9%97%bb%e5%bc%b9%e7%aa%97.png)

- **腾讯新闻弹窗.png识别结果**
![avatar](https://www.cnblogs.com/images/cnblogs_com/runtu/1386194/o_%e6%88%aa%e5%9b%be%e8%85%be%e8%ae%af%e6%96%b0%e9%97%bb%e8%af%86%e5%88%ab%e7%bb%93%e6%9e%9c.png)
- **软件界面**
![avatar](https://www.cnblogs.com/images/cnblogs_com/runtu/1386194/o_%e7%ac%ac%e4%ba%8c%e4%bb%a3%e4%b8%bb%e7%95%8c%e9%9d%a2.jpg)

#### Java源代码：
- **主窗口类 FileChooserOCR2.java**
////////////////////////////////////////////////////////////////////////////////////////////////

- **截图窗口类 ScreenShotTest.java（参考自:[https://www.jb51.net/article/48968.htm](https://www.jb51.net/article/48968.htm)）**



#### ↑↑↑↑↑↑↑↑↑↑↑↑以上为2019.1.18日OcrViewer2.0第二代↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

-
-
-

#### ↓↓↓↓↓↓↓↓↓↓↓↓以下为2019.1.12日OcrViewer1.0第一代↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓



- 打包生成了jar可执行程序
- 完整项目GitHub地址 新人有帮助有用的话请给个star谢谢了！
- https://github.com/Ymy214/java_baidu_ocr
- 

- **识别图一**
![avatar](https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546752498640&di=5b5ddc9603707386fc541603973cc904&imgtype=0&src=http%3A%2F%2Fimage.xmcdn.com%2Fgroup33%2FM08%2FD7%2F39%2FwKgJnVmlF2CiRNi8AAF5KjQuG4E694.jpg)
- **图一识别结果**
![avatar](http://images.cnblogs.com/cnblogs_com/runtu/1377550/o_%E5%B1%B1%E5%B1%85%E7%A7%8B%E6%9A%9D2%E8%AF%86%E5%88%ABxaioguo.jpg)


- **识别图二**
![avatar](https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546752544596&di=a3ced18f2de8fc935d756c57b7cd08e7&imgtype=0&src=http%3A%2F%2Fcdn103.img.lizhi.fm%2Faudio_cover%2F2016%2F08%2F08%2F30513679195664391_320x320.jpg)

- **图二识别结果**
![avatar](https://www.cnblogs.com/images/cnblogs_com/runtu/1377550/o_%e5%b1%b1%e5%b1%85%e7%a7%8b%e6%9a%9d2%e8%af%86%e5%88%abxaioguo.jpg)

- **识别图三**
![avatar](https://www.cnblogs.com/images/cnblogs_com/runtu/1377550/o_%e5%b1%b1%e5%b1%85%e7%a7%8b%e6%9a%9d1.png)

- **图三识别结果**
![avatar](https://www.cnblogs.com/images/cnblogs_com/runtu/1377550/o_%e5%b1%b1%e5%b1%85%e7%a7%8b%e6%9a%9d1%e8%af%86%e5%88%ab%e6%95%88%e6%9e%9c.jpg)
- **软件界面**
![avatar](
https://www.cnblogs.com/images/cnblogs_com/runtu/1377550/o_%e8%bd%af%e4%bb%b6%e7%95%8c%e9%9d%a2.png)
#### Java源代码：


### 最后，附上完整项目GitHub地址：
https://github.com/Ymy214/java_baidu_ocr
