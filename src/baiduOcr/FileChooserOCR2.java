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
