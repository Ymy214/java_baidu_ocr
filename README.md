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
```java
package baiduOcr;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.json.JSONArray;
import org.json.JSONObject;
import com.baidu.aip.ocr.AipOcr;

@SuppressWarnings("serial")
public class FileChooserOCR2 extends JFrame implements ActionListener
{
	// 设置APPID/AK/SK
	public static final String appId = "15289864";
	public static final String apiKey = "j0pj5Y7HVElkLnmn2LEXKeyO";
	public static final String secretKey = "FKVbH7EBcGy4DIaqPnXcqE47eACzn2W7";

	AipOcr client = new AipOcr(appId, apiKey, secretKey);
	JButton open, re0, copy, screen, reset, reocr, b6;
	JPanel ocrPanel, pNorth, pSouth, pWest, pEast, pCenter, pCenterLeft, pCenterRight;
	JTextArea ocrText;
	JLabel previewLabel, printLabel;
	ImageIcon defaultPreviewImg;
	JScrollPane areaScroll;
	String filePath = "./img/preview.jpg";
	BufferedImage screenImage;// 用与子窗体给父窗体传值
	// 构造方法

	public FileChooserOCR2()
	{
		// 主面板
		ocrPanel = new JPanel();
		ocrPanel.setLayout(new BorderLayout());

		// 各个按钮
		open = new JButton("[选择图片>>>文字识别]");
		re0 = new JButton("清空");
		copy = new JButton("复制");
		screen = new JButton("[截图识别]");
		reset = new JButton("清除");
		reocr = new JButton("重识");
		b6 = new JButton("b6");
		// 设置各按钮字体
		open.setFont(new Font("宋体", Font.BOLD, 18));
		screen.setFont(new Font("宋体", Font.BOLD, 18));
		copy.setFont(new Font("宋体", Font.BOLD, 18));
		re0.setFont(new Font("宋体", Font.BOLD, 18));
		reset.setFont(new Font("宋体", Font.BOLD, 18));
		reocr.setFont(new Font("宋体", Font.BOLD, 18));
		b6.setFont(new Font("宋体", Font.BOLD, 18));
		// 图片预览标签以及状态提示标签
		defaultPreviewImg = new ImageIcon("./img/preview.jpg");
		previewLabel = new JLabel(defaultPreviewImg);
		printLabel = new JLabel("输出：");
		// 文本域
		ocrText = new JTextArea("输出内容。。。");
		ocrText.setEditable(true);
		ocrText.setVisible(true);
		ocrText.setFont(new Font("宋体", Font.BOLD, 18));
		// 各方位分面板
		pWest = new JPanel(new GridLayout(2, 0));
		pEast = new JPanel(new GridLayout(2, 2));
		pSouth = new JPanel(new GridLayout(0, 2));
		pCenter = new JPanel(new GridLayout(0, 2));
		pCenterLeft = new JPanel(new CardLayout());
		pCenterRight = new JPanel(new CardLayout());

		// 默认预览图片是否伸缩
		// previewImg.setImage(previewImg.getImage().getScaledInstance(340,
		// 466,Image.SCALE_DEFAULT));
		// previewLabel.setIcon(previewImg);
		//

		// 文本域的滚动条
		areaScroll = new JScrollPane(ocrText);
		// 设置横向滚动条始终开启
		// areaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// 设置垂直滚动条始终开启
		// areaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// 设置横向滚动条自动(有需要时)开启
		areaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// 设置垂直滚动条自动(有需要时)开启
		areaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// 添加监听
		open.addActionListener(this);
		re0.addActionListener(this);
		copy.addActionListener(this);
		screen.addActionListener(this);
		reset.addActionListener(this);
		reocr.addActionListener(this);

		// 各组件依次加入相应方位面板
		pCenterLeft.add(previewLabel);
		// pCenterRight.add(ocrText);
		pCenterRight.add(areaScroll);

		pWest.add(reocr);
		pWest.add(reset);

		pSouth.add(screen);
		pSouth.add(printLabel);
		pCenter.add(pCenterLeft);
		pCenter.add(pCenterRight);
		pEast.add(copy);
		pEast.add(re0);

		// 各方位面板加入主面板
		ocrPanel.add(open, BorderLayout.NORTH);
		ocrPanel.add(pWest, BorderLayout.WEST);
		ocrPanel.add(pEast, BorderLayout.EAST);
		ocrPanel.add(pCenter, BorderLayout.CENTER);
		ocrPanel.add(pSouth, BorderLayout.SOUTH);

		ocrPanel.setSize(300, 300);
		this.add(ocrPanel);
		this.setBounds(400, 200, 900, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{

		if (e.getSource() == re0)
		{
			ocrText.setText("已清空内容。。。");
			printLabel.setText("输出：已清空内容。。。");
		}
		if (e.getSource() == copy)
		{
			String jianqieban = ocrText.getText();
			setSysClipboardText(jianqieban);
			printLabel.setText("输出：已复制到剪贴板。。。");
		}
		if (e.getSource() == screen)
		{
			new ScreenShotTest(this);
			// 现已实现为截屏功能, 截屏功能已经封装到一个类文件中，参考的代码
		}
		if (e.getSource() == reocr)
		{
			ocrText.setText(imgOcr(filePath));
			printLabel.setText("输出：已重新识别。。。");
		}
		if (e.getSource() == reset)
		{
			previewLabel.setIcon(defaultPreviewImg);
			filePath = "./img/preview.jpg";
			printLabel.setText("输出：已清除预览。。。");
		}
		if (e.getSource() == open)
		{
			printLabel.setText("输出：选择文件。。。");
			System.out.println(e.getSource());
			// TODO Auto-generated method stub
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			jfc.showDialog(new JLabel(), "选择");
			File file = jfc.getSelectedFile();
			if (file.isDirectory())
			{
				System.out.println("(选择目录) $ " + file.getAbsolutePath());
				System.out.println("请选择图片。。。");
			} else if (file.isFile())
			{
				filePath = file.getAbsolutePath();
				ImageIcon ocrImg = new ImageIcon(filePath);
				System.out.println("(选择文件) $ " + filePath);
				ocrText.setText("正在识别。。。");
				// 缩放后的图片，用到了一个图片缩放算法
				ocrImg.setImage(ocrImg.getImage().getScaledInstance(340, 470, Image.SCALE_DEFAULT));
				previewLabel.setIcon(ocrImg);
				String ocrStr = imgOcr(filePath);
				printLabel.setText("输出：正在识别。。。");
				ocrText.setText(ocrStr);
				printLabel.setText("输出：识别完毕！！！。。。");

			}
			System.out.println("正在识别>>>" + jfc.getSelectedFile().getName());
		}

	}

	// 复制到剪贴板的方法
	public static void setSysClipboardText(String writeMe)
	{
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable tText = new StringSelection(writeMe);
		clip.setContents(tText, null);
	}

	// 主方法
	public static void main(String[] args)
	{
		FileChooserOCR2 ocrWin = new FileChooserOCR2();
		ocrWin.setVisible(true);
	}

	/*
	 * 文字识别方法
	 */
	public String imgOcr(String imgpath)
	{
		// 传入可选参数调用接口
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("language_type", "CHN_ENG");
		options.put("detect_direction", "true");
		options.put("detect_language", "true");
		options.put("probability", "true");

		// 参数为本地路径
		JSONObject res = client.basicGeneral(imgpath, options);
		// 解析json-------------
		JSONArray wordsResult = (JSONArray) res.get("words_result");
		String ocrStr = "\n";
		for (Object obj : wordsResult)
		{
			JSONObject jo = (JSONObject) obj;
			ocrStr += jo.getString("words") + "\n";
		}

		// 解析json-------------
		return ocrStr;
		// return res.toString(2);

	}

	/*
	 * 文字识别方法(方法的重载)
	 */
	public String imgOcr(byte[] imgInbyte)
	{
		// 传入可选参数调用接口
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("language_type", "CHN_ENG");
		options.put("detect_direction", "true");
		options.put("detect_language", "true");
		options.put("probability", "true");

		// 参数为二进制数组
		JSONObject res = client.basicGeneral(imgInbyte, options);
		// 解析json-------------
		JSONArray wordsResult = (JSONArray) res.get("words_result");
		String ocrStr = "\n";
		for (Object obj : wordsResult)
		{
			JSONObject jo = (JSONObject) obj;
			ocrStr += jo.getString("words") + "\n";
		}

		// 解析json-------------
		return ocrStr;
		// return res.toString(2);

	}

}

////////////////////////////////////////////////////////////////////////////////////////////////




```
- **截图窗口类 ScreenShotTest.java（参考自:[https://www.jb51.net/article/48968.htm](https://www.jb51.net/article/48968.htm)）**
```java
package baiduOcr;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class ScreenShotTest
{
	private FileChooserOCR2 superWindow;

	public ScreenShotTest(FileChooserOCR2 superWimdow)
	{
		this.superWindow = superWimdow;
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run()
			{
				try
				{
					new ScreenShotWindow(superWindow);
				} catch (AWTException e)
				{
					e.printStackTrace();
				}
			}
		});
	}

}

/*
 * 截图窗口
 */
class ScreenShotWindow extends JWindow
{

	private int orgx, orgy, endx, endy;
	private BufferedImage image = null;
	private BufferedImage tempImage = null;
	private BufferedImage saveImage = null;

	private ToolsWindow tools = null;

	private FileChooserOCR2 superWin;

	public ScreenShotWindow(FileChooserOCR2 superWindow) throws AWTException
	{
		this.superWin = superWindow;

		// 获取屏幕尺寸
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, d.width, d.height);

		// 截取屏幕
		Robot robot = new Robot();
		image = robot.createScreenCapture(new Rectangle(0, 0, d.width, d.height));

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e)
			{
				// 鼠标松开时记录结束点坐标，并隐藏操作窗口
				orgx = e.getX();
				orgy = e.getY();

				if (tools != null)
				{
					tools.setVisible(false);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				// 鼠标松开时，显示操作窗口
				if (tools == null)
				{
					tools = new ToolsWindow(ScreenShotWindow.this, e.getX(), e.getY());
				} else
				{
					tools.setLocation(e.getX(), e.getY());
				}
				tools.setVisible(true);
				tools.toFront();
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e)
			{
				// 鼠标拖动时，记录坐标并重绘窗口
				endx = e.getX();
				endy = e.getY();

				// 临时图像，用于缓冲屏幕区域放置屏幕闪烁
				Image tempImage2 = createImage(ScreenShotWindow.this.getWidth(), ScreenShotWindow.this.getHeight());
				Graphics g = tempImage2.getGraphics();
				g.drawImage(tempImage, 0, 0, null);
				int x = Math.min(orgx, endx);
				int y = Math.min(orgy, endy);
				int width = Math.abs(endx - orgx) + 1;
				int height = Math.abs(endy - orgy) + 1;
				// 加上1防止width或height0
				g.setColor(Color.BLUE);
				g.drawRect(x - 1, y - 1, width + 1, height + 1);
				// 减1加1都了防止图片矩形框覆盖掉
				saveImage = image.getSubimage(x, y, width, height);
				g.drawImage(saveImage, x, y, null);

				ScreenShotWindow.this.getGraphics().drawImage(tempImage2, 0, 0, ScreenShotWindow.this);
			}
		});

		this.setVisible(true);
	}

	@Override
	public void paint(Graphics g)
	{
		RescaleOp ro = new RescaleOp(0.8f, 0, null);
		tempImage = ro.filter(image, null);
		g.drawImage(tempImage, 0, 0, this);
	}

	// 保存图像到文件
	public void saveImage() throws IOException
	{
		// 先隐藏窗口后台执行，显得程序执行很快
		this.setVisible(false);
		tools.setVisible(false);

		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("保存");

		// 文件过滤器，用户过滤可选择文件
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG", "jpg");
		jfc.setFileFilter(filter);

		// 初始化一个默认文件（此文件会生成到桌面上）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");
		String fileName = sdf.format(new Date());
		File filePath = FileSystemView.getFileSystemView().getHomeDirectory();
		File defaultFile = new File(filePath + File.separator + fileName + ".jpg");
		jfc.setSelectedFile(defaultFile);

		int flag = jfc.showSaveDialog(this);
		if (flag == JFileChooser.APPROVE_OPTION)
		{
			File file = jfc.getSelectedFile();
			String path = file.getPath();
			// 检查文件后缀，放置用户忘记输入后缀或者输入不正确的后缀
			if (!(path.endsWith(".jpg") || path.endsWith(".JPG")))
			{
				path += ".jpg";
			}
			// 写入文件
			superWin.printLabel.setText("输出：已保存截图！！！");
			ImageIO.write(saveImage, "jpg", new File(path));

			dispose();
		}
	}

	// 返回截取的图片
	public void okImage()
	{
		
		this.setVisible(false);
		tools.setVisible(false);
		superWin.printLabel.setText("输出：识别截图成功！！！");
		ByteArrayOutputStream baos = null;
		try
		{
			baos = new ByteArrayOutputStream();
			ImageIO.write(saveImage, "jpg", baos);
			byte[] imageInByte = baos.toByteArray();// 使用toByteArray()方法转换成字节数组
			superWin.ocrText.setText(superWin.imgOcr(imageInByte));
			baos.flush();// 会产生IOException异常

		} catch (IOException e1)
		{
			e1.printStackTrace();
		} finally
		{
			try
			{
				if (baos != null)
				{
					baos.close();
				}
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}

		dispose();
	}

	/*
	 * 文字识别方法(方法的重载) 用父窗口类的
	 */

	/*
	 * 文字识别方法
	 */

}

/*
 * 操作窗口
 */
class ToolsWindow extends JWindow implements ActionListener
{
	private ScreenShotWindow parent;

	JButton saveButton, closeButton, okButton;

	public ToolsWindow(ScreenShotWindow parent, int x, int y)
	{
		this.parent = parent;
		this.setLayout(new BorderLayout());
		JToolBar toolBar = new JToolBar("Java 截图");

		// 保存按钮
		saveButton = new JButton("◰");
		// 关闭按钮
		closeButton = new JButton("✘");
		// 选定按钮
		okButton = new JButton("✔");

		saveButton.addActionListener(this);
		closeButton.addActionListener(this);
		okButton.addActionListener(this);

		toolBar.add(saveButton);
		toolBar.add(closeButton);
		toolBar.add(okButton);

		this.add(toolBar, BorderLayout.NORTH);

		this.setLocation(x, y);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		if (e.getSource() == saveButton)
		{
			try
			{
				parent.saveImage();
				dispose();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		if (e.getSource() == closeButton)
		{
			parent.dispose();
			dispose();
			// System.exit(0);
		}
		if (e.getSource() == okButton)
		{
			// 返回选定的图片
			parent.okImage();
			dispose();
		}
	}

}


```

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
```java

package baiduOcr;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.json.JSONArray;
import org.json.JSONObject;
import com.baidu.aip.ocr.AipOcr;

@SuppressWarnings("serial")
public class FileChooserOCR extends JFrame implements ActionListener
{
	// 设置APPID/AK/SK
	public static final String appId = "15289864";
	public static final String apiKey = "j0pj5Y7HVElkLnmn2LEXKeyO";
	public static final String secretKey = "FKVbH7EBcGy4DIaqPnXcqE47eACzn2W7";
	
	AipOcr client = new AipOcr(appId, apiKey, secretKey);
	JButton open, b1, b2, b3;
	JPanel ocrPanel;
	JTextArea ocrText;
	JScrollPane areaScroll;
	
	// 构造方法
	public FileChooserOCR()
	{
		ocrPanel = new JPanel();
		ocrPanel.setLayout(new BorderLayout());
		open = new JButton("选择图片>>>文字识别");
		b1 = new JButton("清空");
		b2 = new JButton("复制");
		b3 = new JButton("配置");
		open.setFont(new Font("宋体", Font.BOLD, 18));
		b3.setFont(new Font("宋体", Font.BOLD, 18));
		b2.setFont(new Font("宋体", Font.BOLD, 18));
		b1.setFont(new Font("宋体", Font.BOLD, 18));
		
		//文本域的滚动条
//		areaScroll = new JScrollPane(ocrText);
//		areaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		areaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//添加监听
		open.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		
		ocrText = new JTextArea("输出内容。。。");
		ocrText.setEditable(true);
		ocrText.setVisible(true);
		ocrText.setFont(new Font("宋体", Font.BOLD, 18));
		ocrPanel.add(open, BorderLayout.NORTH);
		ocrPanel.add(b1, BorderLayout.EAST);
		ocrPanel.add(b2, BorderLayout.WEST);
		ocrPanel.add(b3, BorderLayout.SOUTH);
		ocrPanel.add(ocrText, BorderLayout.CENTER);
//		ocrPanel.add(areaScroll);
		ocrPanel.setSize(300, 300);
		this.add(ocrPanel);
		this.setBounds(400, 200, 900, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b1)
		{
			ocrText.setText("已清空内容。。。");
		}
		if(e.getSource()==b2)
		{
			String jianqieban = ocrText.getText();
			setSysClipboardText(jianqieban);
		}
		if (e.getSource()==b3)
		{
			//日后实现
		}
		if(e.getSource()==open)
		{
			System.out.println(e.getSource());
			// TODO Auto-generated method stub
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			jfc.showDialog(new JLabel(), "选择");
			File file = jfc.getSelectedFile();
			if (file.isDirectory())
			{
				System.out.println("(选择目录) $ " + file.getAbsolutePath());
				System.out.println("请选择图片。。。");
			} 
			else if (file.isFile())
			{
				System.out.println("(选择文件) $ " + file.getAbsolutePath());
				ocrText.setText("正在识别。。。");
				String ocrStr = this.imgOcr(file.getAbsolutePath());
				ocrText.setText(ocrStr);
			}
			System.out.println("正在识别>>>"+jfc.getSelectedFile().getName());
		}
		

	}

	//复制到剪贴板
	public static void setSysClipboardText(String writeMe) {  
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();  
        Transferable tText = new StringSelection(writeMe);  
        clip.setContents(tText, null);  
    } 

	// 主方法
	public static void main(String[] args)
	{
		FileChooserOCR ocrWin = new FileChooserOCR();
		ocrWin.setVisible(true);
	}
	/*
	 * 文字识别方法
	 */
	public String imgOcr(String imgpath)
	{
		// 传入可选参数调用接口
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("language_type", "CHN_ENG");
		options.put("detect_direction", "true");
		options.put("detect_language", "true");
		options.put("probability", "true");

		// 参数为本地路径
		JSONObject res = client.basicGeneral(imgpath, options);
		//解析json-------------
		JSONArray wordsResult = (JSONArray)res.get("words_result");
		String ocrStr = "\n";
		for(Object obj : wordsResult)
		{
			JSONObject jo = (JSONObject)obj;
			ocrStr += jo.getString("words") + "\n";
		}
		
		//解析json-------------
		return ocrStr;
//        return res.toString(2);
        
        
        
		// 参数为二进制数组
		// byte[] file = readFile("test.jpg");
		// res = client.basicGeneral(file, options);
		// System.out.println(res.toString(2));

		// 通用文字识别, 图片参数为远程url图片
		// JSONObject res = client.basicGeneralUrl(url, options);
		// System.out.println(res.toString(2));

	}

}


```
### 最后，附上完整项目GitHub地址：
https://github.com/Ymy214/java_baidu_ocr