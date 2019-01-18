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
