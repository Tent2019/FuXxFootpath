package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import Model.EventCommentDB;
import Model.EventCommentManager;
import Model.FollowDB;
import Model.FollowManager;
import Model.UserDB;
import Model.UserManager;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FollowFrame extends JFrame
{

	private JPanel contentPane;
	private JTextField textField_search;
	private JTable table_follow;
	private JTable table_event;

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
					FollowFrame frame = new FollowFrame();
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
	public static boolean allowFF = true;
	private JComboBox comboBox_khet;
	private JTextField textField_id;
	private JTextField textField_date;
	private JComboBox comboBox_road;

	public FollowFrame()
	{
		if (allowFF == true)
			allowFF = false;

		setTitle("Follow");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(150, 150, 1043, 466);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField_search = new JTextField();
		textField_search.setBounds(24, 26, 96, 20);
		contentPane.add(textField_search);
		textField_search.setColumns(10);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				search();
			}
		});
		btnSearch.setBounds(135, 25, 89, 23);
		contentPane.add(btnSearch);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 65, 329, 305);
		contentPane.add(scrollPane);

		table_follow = new JTable();
		table_follow.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (table_follow.getSelectedRow() == -1)
					return;

				int index = table_follow.getSelectedRow();
				FollowDB x = allFollow.get(index);
				textField_id.setText("" + x.complain_id);
				textField_date.setText(x.date);
				comboBox_khet.setSelectedItem(x.khet);
				comboBox_road.setSelectedItem(x.road);
				textArea_follow.setText(x.detail);

				if (x.imgevent == null)
					imagePanelFF_event.setImg(loadImage);
				else
					imagePanelFF_event.setImg(x.imgevent);

				if (x.imgcorrect == null)
					imagePanelFF_correct.setImg(loadImage);
				else
					imagePanelFF_correct.setImg(x.imgcorrect);

				loadEC(x.complain_id);
			}
		});
		scrollPane.setViewportView(table_follow);

		imagePanelFF_event = new ImagePanelFF();
		imagePanelFF_event.setBounds(565, 66, 198, 180);
		contentPane.add(imagePanelFF_event);

		if (imagePanelFF_event.img == null)
			imagePanelFF_event.setImg(getImage());

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(567, 296, 274, 113);
		contentPane.add(scrollPane_1);

		table_event = new JTable();
		scrollPane_1.setViewportView(table_event);

		JTextArea txtrEvent = new JTextArea();
		txtrEvent.setBackground(SystemColor.control);
		txtrEvent.setLineWrap(true);
		txtrEvent.setFont(new Font("Monospaced", Font.ITALIC, 20));
		txtrEvent.setText("EVENT");
		txtrEvent.setEditable(false);
		txtrEvent.setBounds(565, 23, 129, 26);
		contentPane.add(txtrEvent);

		textArea_ec = new JTextArea();
		textArea_ec.setLineWrap(true);
		textArea_ec.setBounds(862, 296, 140, 68);
		contentPane.add(textArea_ec);

		JButton btnSend = new JButton("send");
		btnSend.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String s = textArea_ec.getText().trim(); 
				if (s.equals(""))
				{
					JOptionPane.showMessageDialog(FollowFrame.this, "Please, fill the comment.");
					return;
				}
				
				EventCommentDB ec = new EventCommentDB();
				ec.user_id = MainFrame.userId;
				ec.eventcommment = textArea_ec.getText().trim();
				ec.complain_id = Integer.parseInt(textField_id.getText()); 						
				
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(FollowFrame.this,
						"Are you sure ?", "Confirm", JOptionPane.YES_NO_OPTION))
				{
					EventCommentManager.insertEventComment(ec);
					JOptionPane.showMessageDialog(FollowFrame.this, "Insert Finish");
					loadEC(ec.complain_id);
					
					textArea_ec.setText("");
				}
			}
		});
		btnSend.setBounds(862, 375, 55, 23);
		contentPane.add(btnSend);

		imagePanelFF_correct = new ImagePanelFF();
		imagePanelFF_correct.setBounds(804, 65, 198, 180);
		contentPane.add(imagePanelFF_correct);

		if (imagePanelFF_correct.img == null)
			imagePanelFF_correct.setImg(getImage());

		JTextArea txtrCorrect = new JTextArea();
		txtrCorrect.setText("CORRECT");
		txtrCorrect.setLineWrap(true);
		txtrCorrect.setFont(new Font("Monospaced", Font.ITALIC, 20));
		txtrCorrect.setEditable(false);
		txtrCorrect.setBackground(SystemColor.control);
		txtrCorrect.setBounds(804, 26, 109, 23);
		contentPane.add(txtrCorrect);

		JButton btnEdit = new JButton("Update");
		btnEdit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!MainFrame.isAdmin)
				{
					JOptionPane.showMessageDialog(FollowFrame.this, "Admin only");
					return;
				}

				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(FollowFrame.this, "Are you sure ?",
						"Confirm", JOptionPane.YES_NO_OPTION))
				{
					FollowDB x = new FollowDB();
					x.complain_id = Integer.parseInt(textField_id.getText());
					x.imgcorrect = (BufferedImage) imagePanelFF_correct.img;

					FollowManager.editFollow(x);
					JOptionPane.showMessageDialog(FollowFrame.this, "Edit Finish");
					load();
				}
			}

		});
		btnEdit.setBounds(137, 386, 89, 23);
		contentPane.add(btnEdit);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!MainFrame.isAdmin)
				{
					JOptionPane.showMessageDialog(FollowFrame.this, "Admin only");
					return;
				}

				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(FollowFrame.this, "Are you sure ?",
						"Confirm", JOptionPane.YES_NO_OPTION))
				{
					FollowDB x = new FollowDB();
					x.complain_id = Integer.parseInt(textField_id.getText());

					FollowManager.deleteFollow(x);
					JOptionPane.showMessageDialog(FollowFrame.this, "Delete Finish");
					load();
				}

			}
		});
		btnDelete.setBounds(236, 386, 89, 23);
		contentPane.add(btnDelete);

		JLabel label = new JLabel("admin only");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(65, 390, 62, 14);
		contentPane.add(label);

		JLabel lblRoad = new JLabel("Khet");
		lblRoad.setHorizontalAlignment(SwingConstants.LEFT);
		lblRoad.setBounds(374, 137, 38, 14);
		contentPane.add(lblRoad);

		JLabel lblRoad_1 = new JLabel("Road");
		lblRoad_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblRoad_1.setBounds(373, 179, 38, 14);
		contentPane.add(lblRoad_1);

		JLabel lblSoi = new JLabel("( soi/ next to/ opposite/ ... )");
		lblSoi.setHorizontalAlignment(SwingConstants.LEFT);
		lblSoi.setBounds(373, 241, 144, 14);
		contentPane.add(lblSoi);

		JButton btnNewButton = new JButton("<<");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				allowFF = true;
				setVisible(false);
			}
		});
		btnNewButton.setBounds(968, 11, 49, 23);
		contentPane.add(btnNewButton);

		JLabel lblHome = new JLabel("back");
		lblHome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHome.setBounds(950, 41, 62, 14);
		contentPane.add(lblHome);

		JButton btnNewButton_1 = new JButton("Insert");
		btnNewButton_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				FollowDB x = new FollowDB();
				x.user_id = MainFrame.userId;
				x.date = textField_date.getText();
				x.khet = "" + comboBox_khet.getSelectedItem();
				x.road = "" + comboBox_road.getSelectedItem();
				x.detail = textArea_follow.getText();
				x.imgevent = (BufferedImage) imagePanelFF_event.img;

				if (x.date.equals(""))
				{
					JOptionPane.showMessageDialog(FollowFrame.this, "Please, fill the Date");
					return;
				}

				if (x.imgevent == loadImage)
				{
					JOptionPane.showMessageDialog(FollowFrame.this, "Please, choose the image");
					return;
				}

				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(FollowFrame.this,
						"Did you already SEARCH for preventing Duplication ?", "Confirm", JOptionPane.YES_NO_OPTION))
				{
					FollowManager.insertEvent(x);
					JOptionPane.showMessageDialog(FollowFrame.this, "Insert Finish");
					load();
				}
			}
		});
		btnNewButton_1.setBounds(428, 347, 89, 23);
		contentPane.add(btnNewButton_1);

		JButton btnImage = new JButton("img");
		btnImage.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fc = new JFileChooser();
				fc.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif"));
				fc.setAcceptAllFileFilterUsed(false);

				int openVal = fc.showOpenDialog(FollowFrame.this);
				if (JFileChooser.APPROVE_OPTION == openVal)
				{
					File f = fc.getSelectedFile();
					try
					{
						BufferedImage bimg = ImageIO.read(f);
						imagePanelFF_event.setImg(bimg);

					} catch (IOException e1)
					{
						e1.printStackTrace();
					}

				}
			}
		});
		btnImage.setBounds(565, 256, 55, 23);
		contentPane.add(btnImage);

		JButton button_2 = new JButton("img");
		button_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!MainFrame.isAdmin)
				{
					JOptionPane.showMessageDialog(FollowFrame.this, "Admin only");
					return;
				}
				
				JFileChooser fc = new JFileChooser();
				fc.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif"));
				fc.setAcceptAllFileFilterUsed(false);

				int openVal = fc.showOpenDialog(FollowFrame.this);
				if (JFileChooser.APPROVE_OPTION == openVal)
				{
					File f = fc.getSelectedFile();
					try
					{
						BufferedImage bimg = ImageIO.read(f);
						imagePanelFF_correct.setImg(bimg);

					} catch (IOException e1)
					{
						e1.printStackTrace();
					}

				}
			}
		});
		button_2.setBounds(804, 256, 55, 23);
		contentPane.add(button_2);

		comboBox_road = new JComboBox();
		comboBox_road.setBounds(421, 175, 96, 22);
		contentPane.add(comboBox_road);

		comboBox_khet = new JComboBox();
		comboBox_khet.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (comboBox_khet.getSelectedItem().equals("bang kapi"))
				{
					comboBox_road.setModel(new DefaultComboBoxModel(new String[]
					{ "ramkhamhaeng", "ladprao", "srinakarin" }));
				} else if (comboBox_khet.getSelectedItem().equals("bang na"))
				{
					comboBox_road.setModel(new DefaultComboBoxModel(new String[]
					{ "sukhumvit", "udomsuk", "bangna trad" }));
				} else if (comboBox_khet.getSelectedItem().equals("don mueang"))
				{
					comboBox_road.setModel(new DefaultComboBoxModel(new String[]
					{ "songprapa", "kampangpetch", "watwaruwanaram" }));
				} else if (comboBox_khet.getSelectedItem().equals("dusit"))
				{
					comboBox_road.setModel(new DefaultComboBoxModel(new String[]
					{ "rajvithi", "rama v", "samsen" }));
				} else if (comboBox_khet.getSelectedItem().equals("huai Khwang"))
				{
					comboBox_road.setModel(new DefaultComboBoxModel(new String[]
					{ "praram 9", "tiamruammit", "prachautid" }));
				} else
				{
					comboBox_road.setModel(new DefaultComboBoxModel(new String[]
					{}));
				}

			}
		});
		comboBox_khet.setModel(new DefaultComboBoxModel(new String[]
		{ "bang kapi", "bang na", "don mueang", "dusit", "huai Khwang", "khlong toei", "lat krabang", "lat phrao",
				"pathum wan", "phaya thai" }));
		comboBox_khet.setBounds(422, 136, 96, 22);
		contentPane.add(comboBox_khet);

		JLabel lblDate = new JLabel("id");
		lblDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate.setBounds(374, 44, 38, 14);
		contentPane.add(lblDate);

		textField_id = new JTextField();
		textField_id.setEditable(false);
		textField_id.setBounds(422, 41, 96, 20);
		contentPane.add(textField_id);
		textField_id.setColumns(10);

		JLabel label_1 = new JLabel("Date");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setBounds(374, 82, 38, 14);
		contentPane.add(label_1);

		textField_date = new JTextField();
		textField_date.setEditable(false);
		textField_date.setColumns(10);
		textField_date.setBounds(422, 79, 96, 20);
		contentPane.add(textField_date);

		JLabel lblClickToGet = new JLabel("( click to get Date )");
		lblClickToGet.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				LocalDateTime now = LocalDateTime.now();
				String date = ("" + now).substring(0, 10);
				textField_date.setText(date);
			}
		});
		lblClickToGet.setHorizontalAlignment(SwingConstants.LEFT);
		lblClickToGet.setBounds(424, 106, 96, 14);
		contentPane.add(lblClickToGet);

		JLabel label_2 = new JLabel("admin only");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setBounds(869, 256, 62, 22);
		contentPane.add(label_2);

		textArea_follow = new JTextArea();
		textArea_follow.setWrapStyleWord(true);
		textArea_follow.setLineWrap(true);
		textArea_follow.setBounds(374, 268, 144, 68);
		contentPane.add(textArea_follow);

		JLabel lblMoreDetails = new JLabel("more details");
		lblMoreDetails.setHorizontalAlignment(SwingConstants.LEFT);
		lblMoreDetails.setBounds(374, 218, 79, 14);
		contentPane.add(lblMoreDetails);

		JLabel lblClickTo = new JLabel("( click to Clear )");
		lblClickTo.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				textField_id.setText("");
				textField_date.setText("");
				textArea_follow.setText("");
				imagePanelFF_event.setImg(loadImage);
				imagePanelFF_correct.setImg(loadImage);
			}
		});
		lblClickTo.setHorizontalAlignment(SwingConstants.LEFT);
		lblClickTo.setBounds(435, 374, 84, 14);
		contentPane.add(lblClickTo);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				load();
			}
		});
		btnRefresh.setBounds(236, 25, 89, 23);
		contentPane.add(btnRefresh);

		load();
	}

	public ArrayList<FollowDB> allFollow;
	private JTextArea textArea_follow;
	private ImagePanelFF imagePanelFF_event;
	private ImagePanelFF imagePanelFF_correct;

	public void load()
	{
		allFollow = FollowManager.getAllFollow();

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("complain_id");
		model.addColumn("date");
		model.addColumn("khet");
		model.addColumn("road");

		for (FollowDB x : allFollow)
		{
			model.addRow(new Object[]
			{ x.complain_id, x.date, x.khet, x.road });
		}

		table_follow.setModel(model);
	}

	public ArrayList<FollowDB> search;

	public void search()
	{
		String s = textField_search.getText().trim();
		search = FollowManager.searchFollow(s);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("complain_id");
		model.addColumn("date");
		model.addColumn("khet");
		model.addColumn("road");

		for (FollowDB x : search)
		{
			model.addRow(new Object[]
			{ x.complain_id, x.date, x.khet, x.road });
		}

		table_follow.setModel(model);
	}

	public BufferedImage loadImage = getImage();

	public BufferedImage getImage()
	{
		BufferedImage bimg = null;
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			java.sql.Connection con = DriverManager.getConnection(url, "root", "");
			Statement st = con.createStatement();
			String query = "SELECT * FROM mainimage WHERE img_id = 2";
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

	public ArrayList<EventCommentDB> allEC;
	private JTextArea textArea_ec;

	public void loadEC(int x_complain_id)
	{
		allEC = EventCommentManager.getAllEventComment(x_complain_id);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("username");
		model.addColumn("comment");

		for (EventCommentDB x : allEC)
		{
			String s = "";
			if (x.usertype.equals("admin"))
				s += "Admin_" + x.name;
			else
				s += x.name;
			model.addRow(new Object[]
			{ s, x.eventcommment });
		}

		table_event.setModel(model);
	}

}
