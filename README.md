# java_baidu_ocr
### Java调用百度OCR文字识别API实现图片文字识别软件

- 识别图一
![avatar](https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546752498640&di=5b5ddc9603707386fc541603973cc904&imgtype=0&src=http%3A%2F%2Fimage.xmcdn.com%2Fgroup33%2FM08%2FD7%2F39%2FwKgJnVmlF2CiRNi8AAF5KjQuG4E694.jpg)
- 图一识别结果
![avatar](http://m.qpic.cn/psb?/V11wOzXz1FrpQc/soimlQklAkMJO.cDIu0nPCSvZMdm0OCR6JKFl*lu3cc!/b/dDABAAAAAAAA&bo=hANYAgAAAAARB.0!&rf=viewer_4)


- 识别图二
![avatar](https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546752544596&di=a3ced18f2de8fc935d756c57b7cd08e7&imgtype=0&src=http%3A%2F%2Fcdn103.img.lizhi.fm%2Faudio_cover%2F2016%2F08%2F08%2F30513679195664391_320x320.jpg)

- 图二识别结果
![avatar](http://m.qpic.cn/psb?/V11wOzXz1FrpQc/D4EfuZFdEgaOmIsYamlobuvA7Lomcv4u5Ue8jvLN1yE!/b/dFQBAAAAAAAA&bo=hANYAgAAAAARB.0!&rf=viewer_4)

- 识别图三
![avatar](http://m.qpic.cn/psb?/V11wOzXz1FrpQc/Cj0.qemL2p432uTmCkG*pfhPeGEvq5jpTc*.SuJ6s.g!/b/dDcBAAAAAAAA&bo=BwInAQAAAAADBwE!&rf=viewer_4)

- 图三识别结果
![avatar](http://m.qpic.cn/psb?/V11wOzXz1FrpQc/dj6OFBPkUT4X1n1UpmwSX.ExiPotQgOfALfgepWKo1s!/b/dL8AAAAAAAAA&bo=hANYAgAAAAARB.0!&rf=viewer_4)
- 软件界面
![avatar](
http://m.qpic.cn/psb?/V11wOzXz1FrpQc/jR7BVWoYOZn*AgWOdSZApzOAHHtGeu6qNWchAOoi*CM!/b/dMIAAAAAAAAA&bo=hANYAgAAAAADB*8!&rf=viewer_4)

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
