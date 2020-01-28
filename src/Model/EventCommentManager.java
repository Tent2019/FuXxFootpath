package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EventCommentManager
{
	public static ArrayList<EventCommentDB> getAllEventComment(int x_complain_id)
	{
		ArrayList<EventCommentDB> list = new ArrayList<EventCommentDB>();

		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			Statement st = con.createStatement();
			String query = "SELECT u.username, u.usertype, f.complain_id, e.eventcomment\r\n" + 
					"FROM users AS u \r\n" + 
					"INNER JOIN footpath AS f\r\n" + 
					"INNER JOIN eventcomment AS e\r\n" + 
					"ON f.complain_id = e.complain_id AND e.user_id = u.user_id\r\n" + 
					"WHERE e.complain_id = "+x_complain_id ;
			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				EventCommentDB x = new EventCommentDB();
								
				x.name = rs.getString(1);
				x.usertype = rs.getString(2);
				x.eventcommment = rs.getString(4);				

				list.add(x);
				
			}
			st.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	public static void insertEventComment(EventCommentDB x)
	{
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			String query = "INSERT INTO eventcomment VALUES (0, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, x.user_id);
			ps.setString(2, x.eventcommment);
			ps.setInt(3, x.complain_id);

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	// test
//	public static void main(String[] args)
//	{
//		EventCommentManager.getAllEventComment(1);
//	}
}
