package Model;

import java.sql.*;
import java.util.ArrayList;

public class UserManager
{
	public static ArrayList<UserDB> getAllUser()
	{
		ArrayList<UserDB> list = new ArrayList<UserDB>();

		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			Statement st = con.createStatement();
			String query = "SELECT * FROM users";
			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				UserDB x = new UserDB();
				x.id = rs.getInt(1);
				x.username = rs.getString(2);
				x.password = rs.getString(3);
				x.usertype = rs.getString(4);

				list.add(x);
			}

			st.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return list;
	}

	public static void insertUser(UserDB x)
	{
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			String query = "INSERT INTO users VALUES (0, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, x.username);
			ps.setString(2, x.password);
			ps.setString(3, x.usertype);

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void editUser(UserDB x)
	{
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			String query = "UPDATE users SET username = ?, password = ?, usertype = ? WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, x.username);
			ps.setString(2, x.password);
			ps.setString(3, x.usertype);
			ps.setInt(4, x.id);

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void deleteUser(UserDB x)
	{
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			String query = "DELETE FROM users WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, x.id);

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void submitUser(String username, String password)
	{
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			String query = "INSERT INTO users VALUES (0, ?, ?, 'user')";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	public static boolean isAdmin(String username, String password)
	{
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			Statement st = con.createStatement();
			String query = "SELECT * FROM users";
			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				String s_usertype = rs.getString(4);
				if (s_usertype.equals("admin"))
				{
					String s_username = rs.getString(2);
					String s_password = rs.getString(3);

					if (s_username.equals(username) && s_password.equals(password))
						return true;
				}
			}
			st.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	public static int getUserId(String username, String password)
	{
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			Statement st = con.createStatement();
			String query = "SELECT * FROM users";
			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				String s_username = rs.getString(2);
				String s_password = rs.getString(3);
								
				if (s_username.equals(username) && s_password.equals(password))
					return rs.getInt(1);
			}
			st.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return -1;
	}

	public static boolean loginFF(String username, String password)
	{
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			Statement st = con.createStatement();
			String query = "SELECT * FROM users";
			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				String s_username = rs.getString(2);
				String s_password = rs.getString(3);

				if (s_username.equals(username) && s_password.equals(password))
					return true;
			}

			st.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	// test
//	public static void main(String[] args)
//	{
//		System.out.println(UserManager.getUserName("Robin", "1234"));				
//		
//	}
}
