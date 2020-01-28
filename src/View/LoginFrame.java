package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.UserManager;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

public class LoginFrame extends JFrame
{

	private JPanel contentPane;
	private JTextField textField_username;
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
					LoginFrame frame = new LoginFrame();
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
	public LoginFrame()
	{
		setTitle("Login 2 FuXxFootpath");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 368, 277);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField_username = new JTextField();
		textField_username.setBounds(169, 48, 114, 20);
		contentPane.add(textField_username);
		textField_username.setColumns(10);

		JLabel lblUsername = new JLabel("username");
		lblUsername.setBounds(69, 48, 48, 14);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(69, 90, 48, 14);
		contentPane.add(lblPassword);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String s_username = textField_username.getText().trim();
				String s_password = passwordField.getText();

				if (s_username.equals("") || s_password.equals(""))
				{
					JOptionPane.showMessageDialog(LoginFrame.this, "please input Username and Password ");
					return;
				}
				
				if (UserManager.isAdmin(s_username, s_password))
					MainFrame.isAdmin = true;
				
				MainFrame.userId = UserManager.getUserId(s_username, s_password);
								
				MainFrame mf = new MainFrame();
				if (UserManager.loginFF(s_username, s_password))
				{
					mf.setVisible(true);
					setVisible(false);				
					
				} else
				{
					JOptionPane.showMessageDialog(LoginFrame.this, "invalid Username or Password ");
				}

				textField_username.setText("");
				passwordField.setText("");

			}
		});
		btnLogin.setBounds(83, 139, 89, 23);
		contentPane.add(btnLogin);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});
		btnExit.setBounds(182, 139, 89, 23);
		contentPane.add(btnExit);

		JLabel lblRegister = new JLabel("register");
		lblRegister.setCursor(getCursor());
		lblRegister.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				lblRegister.setForeground(Color.BLUE);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				lblRegister.setForeground(Color.BLACK);
			}

		});
		lblRegister.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				RegisterFrame rf = new RegisterFrame();
				rf.setVisible(true);

				textField_username.setText("");
				passwordField.setText("");
			}
		});
		lblRegister.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRegister.setBounds(216, 176, 48, 14);
		contentPane.add(lblRegister);

		JLabel lblAdminOnly = new JLabel("admin only");
		lblAdminOnly.setCursor(getCursor());
		lblAdminOnly.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				lblAdminOnly.setForeground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				lblAdminOnly.setForeground(Color.BLACK);
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				LoginAdminFrame laf = new LoginAdminFrame();
				laf.setVisible(true);

				textField_username.setText("");
				passwordField.setText("");
			}
		});
		lblAdminOnly.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAdminOnly.setBounds(180, 196, 82, 14);
		contentPane.add(lblAdminOnly);

		passwordField = new JPasswordField();
		passwordField.setBounds(169, 87, 114, 20);
		contentPane.add(passwordField);
	}
}
