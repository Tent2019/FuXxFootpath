package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.EventCommentManager;
import Model.LikeReqDB;
import Model.LikeReqManager;
import Model.RequestDB;
import Model.RequestManager;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import java.awt.SystemColor;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RequestFrame extends JFrame
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
					RequestFrame frame = new RequestFrame();
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
	public static boolean allowRF = true;

	public RequestFrame()
	{
		if (allowRF == true)
			allowRF = false;

		loadLike();

		setTitle("RequestFrame");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(500, 100, 379, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(17, 63, 327, 200);
		contentPane.add(scrollPane);

		table = new JTable();

		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				ArrayList<LikeReqDB> x = LikeReqManager.getAllLike();
				ArrayList<Integer> reqIdLike = new ArrayList<Integer>();
				for (LikeReqDB xx : x)
				{
					reqIdLike.add(xx.request_id);
				}

				int index = table.getSelectedRow();

				int likeCount = 0;
				for (int i = 0; i < x.size(); i++)
				{
					if (x.get(i).request_id == (index + 1))
						likeCount = x.get(i).count;
				}

				textField_like.setText("" + likeCount);

			}
		});
		scrollPane.setViewportView(table);

		JTextArea txtrRequest = new JTextArea();
		txtrRequest.setText("REQUEST");
		txtrRequest.setLineWrap(true);
		txtrRequest.setFont(new Font("Monospaced", Font.ITALIC, 20));
		txtrRequest.setEditable(false);
		txtrRequest.setBackground(SystemColor.menu);
		txtrRequest.setBounds(26, 15, 103, 31);
		contentPane.add(txtrRequest);

		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setBounds(34, 276, 223, 60);
		contentPane.add(textArea);

		JButton button = new JButton("send");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String s = textArea.getText().trim();
				if (s.equals(""))
				{
					JOptionPane.showMessageDialog(RequestFrame.this, "Please, fill the comment.");
					return;
				}

				RequestDB x = new RequestDB();
				x.requestcomment = s;
				x.user_id = MainFrame.userId;

				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(RequestFrame.this, "Are you sure ?",
						"Confirm", JOptionPane.YES_NO_OPTION))
				{
					RequestManager.insertRequest(x);
					JOptionPane.showMessageDialog(RequestFrame.this, "Insert Finish");
					load();

					textArea.setText("");
				}

			}
		});
		button.setBounds(267, 277, 55, 23);
		contentPane.add(button);

		ImagePanelFF imagePanelFF_like = new ImagePanelFF();
		imagePanelFF_like.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				int index = table.getSelectedRow();

				if (index == -1)
				{
					JOptionPane.showMessageDialog(RequestFrame.this, "Please, select the Request.");
					return;
				}
				
				LikeReqDB x = new LikeReqDB();
				x.user_id = MainFrame.userId;
//				x.request_id = getAllLike.get(index).request_id;
				x.request_id = index + 1;
				x.like_id = LikeReqManager.getLikeId(x);

				if (LikeReqManager.isLike(x))
				{
					JOptionPane.showMessageDialog(RequestFrame.this, "You already Like this request.");
					return;
					//LikeReqManager.cancelLike(x);
										

				} else
				{
					LikeReqManager.insertLike(x);
					
				}
				
				loadLike();
				
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{

				int index = table.getSelectedRow();
				int likeCount = getAllLike.get(index).count;
				textField_like.setText("" + likeCount);
				
				loadLike();
			}

		});

		imagePanelFF_like.setBounds(137, 21, 23, 23);
		imagePanelFF_like.setImg(loadImage);
		contentPane.add(imagePanelFF_like);

		JButton button_1 = new JButton("<<");
		button_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				allowRF = true;
				setVisible(false);
			}
		});
		button_1.setBounds(295, 11, 49, 23);
		contentPane.add(button_1);

		JLabel label_1 = new JLabel("back");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(276, 38, 62, 14);
		contentPane.add(label_1);

		textField_like = new JTextField();
		textField_like.setHorizontalAlignment(SwingConstants.CENTER);
		textField_like.setEditable(false);
		textField_like.setBounds(169, 21, 23, 23);
		contentPane.add(textField_like);
		textField_like.setColumns(10);
		
//		ImagePanelFF imagePanelFF_dislike = new ImagePanelFF();
//		imagePanelFF_dislike.setBounds(167, 21, 23, 23);
//		imagePanelFF_dislike.setImg(loadImage2);
//		contentPane.add(imagePanelFF_dislike);

		load();

	}

	public BufferedImage loadImage = getImage();
	private JTextField textField_like;
	private JTable table;

	public BufferedImage getImage()
	{
		BufferedImage bimg = null;
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			java.sql.Connection con = DriverManager.getConnection(url, "root", "");
			Statement st = con.createStatement();
			String query = "SELECT * FROM mainimage WHERE img_id = 3";
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
	
	public BufferedImage loadImage2 = getImage2();
	public BufferedImage getImage2()
	{
		BufferedImage bimg = null;
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			java.sql.Connection con = DriverManager.getConnection(url, "root", "");
			Statement st = con.createStatement();
			String query = "SELECT * FROM mainimage WHERE img_id = 4";
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
	
	public ArrayList<RequestDB> allRequest;
	public ArrayList<Integer> reqId; // **

	public void load()
	{
		allRequest = RequestManager.getAllRequest();
		reqId = new ArrayList<Integer>(); // **

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("request_id");
		model.addColumn("username");
		model.addColumn("request");

		for (RequestDB x : allRequest)
		{
			model.addRow(new Object[]
			{ x.request_id, x.name, x.requestcomment });

			reqId.add(x.request_id); // **
		}

		table.setModel(model);
	}

	public ArrayList<LikeReqDB> getAllLike;

	public void loadLike()
	{
		getAllLike = LikeReqManager.getAllLike();
	}
}
