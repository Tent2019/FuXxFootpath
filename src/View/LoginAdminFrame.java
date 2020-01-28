package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.UserManager;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginAdminFrame extends JFrame
{

	private JPanel contentPane;
	private JTextField textField_admin;
	private JPasswordField passwordField;

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
					LoginAdminFrame frame = new LoginAdminFrame();
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
	public LoginAdminFrame()
	{
		setTitle("Login 2 Administrator");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(150, 150, 368, 277);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton button = new JButton("Exit");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});
		button.setBounds(183, 149, 89, 23);
		contentPane.add(button);

		JLabel lblAdminname = new JLabel("admin_name");
		lblAdminname.setBounds(70, 58, 65, 14);
		contentPane.add(lblAdminname);

		JLabel label_1 = new JLabel("password");
		label_1.setBounds(70, 100, 48, 14);
		contentPane.add(label_1);

		textField_admin = new JTextField();
		textField_admin.setColumns(10);
		textField_admin.setBounds(170, 58, 114, 20);
		contentPane.add(textField_admin);

		passwordField = new JPasswordField();
		passwordField.setBounds(170, 97, 114, 20);
		contentPane.add(passwordField);

		JButton button_1 = new JButton("Login");
		button_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String s_admin = textField_admin.getText().trim();
				String s_password = passwordField.getText();

				if (s_admin.equals("") || s_password.equals(""))
				{
					JOptionPane.showMessageDialog(LoginAdminFrame.this, "please input Username and Password");
					return;
				}

				AdminFrame af = new AdminFrame();
				if (UserManager.isAdmin(s_admin, s_password))
				{
					af.setVisible(true);
					setVisible(false);
				} else
				{
					JOptionPane.showMessageDialog(LoginAdminFrame.this, "Admin Only");
				}

				textField_admin.setText("");
				passwordField.setText("");

			}
		});
		button_1.setBounds(84, 149, 89, 23);
		contentPane.add(button_1);
	}

}
