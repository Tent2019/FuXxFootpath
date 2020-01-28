package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;

import Model.UserDB;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame
{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					MainFrame frame = new MainFrame();
					frame.setVisible(true);

				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public static boolean isAdmin = false;
	public static int userId = -1;

	public MainFrame()
	{
		setTitle("FuXx Footpath");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 357, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Follow | Notify");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (FollowFrame.allowFF == true)
				{
					FollowFrame ff = new FollowFrame();
					ff.setVisible(true);
				} else
				{
					JOptionPane.showMessageDialog(MainFrame.this, "Follow page already OPEN");
				}

			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnNewButton.setBounds(0, 153, 341, 99);
		contentPane.add(btnNewButton);

		JButton btnSuggest = new JButton("Request");
		btnSuggest.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (RequestFrame.allowRF == true)
				{
					RequestFrame rf = new RequestFrame();
					rf.setVisible(true);
					
				} else
				{
					JOptionPane.showMessageDialog(MainFrame.this, "Request page already OPEN");
				}				
			}
		});
		btnSuggest.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnSuggest.setBounds(0, 252, 341, 99);
		contentPane.add(btnSuggest);

		ImagePanelFF imagePanelFF = new ImagePanelFF();
		imagePanelFF.setImg(getImage());
		imagePanelFF.setBounds(0, 0, 341, 152);
		contentPane.add(imagePanelFF);

		// getImage();
	}

	// Head image
	public BufferedImage getImage()
	{
		BufferedImage bimg = null;
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			java.sql.Connection con = DriverManager.getConnection(url, "root", "");
			Statement st = con.createStatement();
			String query = "SELECT * FROM mainimage WHERE img_id = 1";
			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				byte[] b = rs.getBytes(2);
				ByteArrayInputStream bais = new ByteArrayInputStream(b);
				bimg = ImageIO.read(bais);
			}
			rs.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return bimg;

	}
}
