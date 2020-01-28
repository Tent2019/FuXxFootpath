package Model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class RequestManager
{
	public static ArrayList<RequestDB> getAllRequest()
	{
		ArrayList<RequestDB> list = new ArrayList<RequestDB>();

		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			Statement st = con.createStatement();
			String query = "SELECT r.request_id, r.requestcomment, r.user_id, u.username\r\n" + 
					"FROM request AS r\r\n" + 
					"INNER JOIN users AS u\r\n" + 
					"ON r.user_id = u.user_id";
			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				RequestDB x = new RequestDB();
								
				x.request_id = rs.getInt(1);
				x.requestcomment = rs.getString(2);
				x.user_id = rs.getInt(3);
				x.name = rs.getString(4);

				list.add(x);				
			}
			st.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	public static void insertRequest(RequestDB x)
	{
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			String query = "INSERT INTO request VALUES (0, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, x.requestcomment);
			ps.setInt(2, x.user_id);

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	//test
//	public static void main(String[] args)
//	{
//		RequestManager.getAllRequest();
//	}
}
