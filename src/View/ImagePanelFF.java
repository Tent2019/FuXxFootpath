package View;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanelFF extends JPanel
{
	Image img;

	public void setImg(Image img)
	{
		this.img = img;
		repaint();
	}

	public void paint(Graphics g)
	{
		if (img != null)
		{
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0, img.getWidth(this), img.getHeight(this),
					this);
		} else if (img == null)
		{
			g.fillRect(0, 0, this.getWidth(), this.getHeight());

		}
	}

}
