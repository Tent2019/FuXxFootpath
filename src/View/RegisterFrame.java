package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.UserManager;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterFrame extends JFrame
{

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textField_username;

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
					RegisterFrame frame = new RegisterFrame();
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
	public RegisterFrame()
	{
		setTitle("Regitster");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(150, 150, 368, 277);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("username");
		label.setBounds(73, 59, 48, 14);
		contentPane.add(label);

		JLabel label_1 = new JLabel("password");
		label_1.setBounds(73, 101, 48, 14);
		contentPane.add(label_1);

		passwordField = new JPasswordField();
		passwordField.setBounds(173, 98, 114, 20);
		contentPane.add(passwordField);

		textField_username = new JTextField();
		textField_username.setColumns(10);
		textField_username.setBounds(173, 59, 114, 20);
		contentPane.add(textField_username);

		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String s_username = textField_username.getText().trim();
				String s_password = passwordField.getText();

				if (s_username.equals("") || s_password.equals(""))
				{
					JOptionPane.showMessageDialog(RegisterFrame.this, "please input Username and Password");
					return;
				}

				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(RegisterFrame.this, "press Yes to confirm",
						"Sure ?", JOptionPane.YES_NO_OPTION))
				{
					UserManager.submitUser(s_username, s_password);
					JOptionPane.showMessageDialog(RegisterFrame.this, "Register Finish");

					textField_username.setText("");
					passwordField.setText("");

				}

			}
		});
		btnNewButton.setBounds(136, 156, 89, 23);
		contentPane.add(btnNewButton);
	}
}
