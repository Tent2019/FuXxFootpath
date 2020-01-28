package View;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.UserDB;
import Model.UserManager;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminFrame extends JFrame
{

	private JPanel contentPane;
	private JTable table;
	private JTextField textField_username;
	private JTextField textField_password;
	private JTextField textField_id;

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
					AdminFrame frame = new AdminFrame();
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
	public AdminFrame()
	{
		setTitle("Administrator");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(150, 150, 595, 356);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 22, 327, 274);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				int index = table.getSelectedRow();

				UserDB x = allUser.get(index);
				textField_id.setText("" + x.id);
				textField_username.setText(x.username);
				textField_password.setText(x.password);
				// System.out.println(x.usertype);
				comboBox.setSelectedItem(x.usertype);
			}
		});
		scrollPane.setViewportView(table);

		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				UserDB x = new UserDB();
				x.username = textField_username.getText().trim();
				x.password = textField_password.getText().trim();
				x.usertype = (String) comboBox.getSelectedItem();

				if (x.username.equals("") || x.password.equals(""))
				{
					JOptionPane.showMessageDialog(AdminFrame.this, "Please input Username and Password ");
					return;
				}

				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(AdminFrame.this, "Are you sure ?",
						"Confirm", JOptionPane.YES_NO_OPTION))
				{
					UserManager.insertUser(x);
					load();
					JOptionPane.showMessageDialog(AdminFrame.this, "Insert Finish");

					textField_id.setText("");
					textField_username.setText("");
					textField_password.setText("");
				}
			}
		});
		btnInsert.setBounds(449, 210, 89, 23);
		contentPane.add(btnInsert);

		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (table.getSelectedRow() == -1)
				{
					JOptionPane.showMessageDialog(AdminFrame.this, "Please select a row");
					return;
				}

				UserDB x = new UserDB();
				x.id = Integer.parseInt(textField_id.getText());
				x.username = textField_username.getText().trim();
				x.password = textField_password.getText().trim();
				x.usertype = (String) comboBox.getSelectedItem();

				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(AdminFrame.this, "Are you sure ?",
						"Confirm", JOptionPane.YES_NO_OPTION))
				{
					UserManager.editUser(x);
					load();
					JOptionPane.showMessageDialog(AdminFrame.this, "Edit Finish");
					textField_id.setText("");
					textField_username.setText("");
					textField_password.setText("");
				}
			}
		});
		btnEdit.setBounds(449, 238, 89, 23);
		contentPane.add(btnEdit);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (table.getSelectedRow() == -1)
				{
					JOptionPane.showMessageDialog(AdminFrame.this, "Please select a row");
					return;
				}
				
				UserDB x = new UserDB();
				x.id = Integer.parseInt(textField_id.getText());
				
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(AdminFrame.this, "Are you sure ?",
						"Confirm", JOptionPane.YES_NO_OPTION))
				{
					UserManager.deleteUser(x);
					load();
					JOptionPane.showMessageDialog(AdminFrame.this, "Delete Finish");
				}				
			}
		});
		btnDelete.setBounds(449, 267, 89, 23);
		contentPane.add(btnDelete);

		textField_username = new JTextField();
		textField_username.setBounds(449, 73, 89, 20);
		contentPane.add(textField_username);
		textField_username.setColumns(10);

		textField_password = new JTextField();
		textField_password.setColumns(10);
		textField_password.setBounds(449, 117, 89, 20);
		contentPane.add(textField_password);

		JLabel lblUsername = new JLabel("username");
		lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsername.setBounds(377, 76, 62, 14);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("password");
		lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
		lblPassword.setBounds(377, 120, 62, 14);
		contentPane.add(lblPassword);

		JLabel lblUsertype = new JLabel("usertype");
		lblUsertype.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsertype.setBounds(377, 165, 62, 14);
		contentPane.add(lblUsertype);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[]
		{ "admin", "user" }));
		comboBox.setBounds(449, 161, 89, 22);
		contentPane.add(comboBox);

		JLabel lblId = new JLabel("id");
		lblId.setEnabled(false);
		lblId.setHorizontalAlignment(SwingConstants.LEFT);
		lblId.setBounds(377, 32, 62, 14);
		contentPane.add(lblId);

		textField_id = new JTextField();
		textField_id.setEditable(false);
		textField_id.setBackground(Color.YELLOW);
		textField_id.setColumns(10);
		textField_id.setBounds(450, 29, 89, 20);
		contentPane.add(textField_id);

		load();
	}

	ArrayList<UserDB> allUser;
	private JComboBox comboBox;

	public void load()
	{
		allUser = UserManager.getAllUser();

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("id");
		model.addColumn("name");
		model.addColumn("password");
		model.addColumn("usertype");

		for (UserDB x : allUser)
		{
			model.addRow(new Object[]
			{ x.id, x.username, x.password, x.usertype });
		}

		table.setModel(model);
	}
}
