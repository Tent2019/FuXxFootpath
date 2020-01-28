package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LikeReqManager
{
	public static ArrayList<LikeReqDB> getAllLike()
	{
		ArrayList<LikeReqDB> list = new ArrayList<LikeReqDB>();

		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			Statement st = con.createStatement();
			String query = "SELECT request_id, COUNT(request_id)\r\n" + "FROM `likereq` \r\n" + "GROUP BY request_id";
			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				LikeReqDB x = new LikeReqDB();

				x.request_id = rs.getInt(1);
				x.count = rs.getInt(2);

				list.add(x);
			}
			st.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public static boolean isLike(LikeReqDB x)
	{
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			Statement st = con.createStatement();
			String query = "SELECT * FROM likereq";
			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				LikeReqDB lr = new LikeReqDB();
				lr.user_id = rs.getInt(2);
				lr.request_id = rs.getInt(3);

				if (lr.user_id == x.user_id && lr.request_id == x.request_id)
					return true;
			}
			st.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	public static void insertLike(LikeReqDB x)
	{
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			String query = "INSERT INTO likereq VALUES (0, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, x.user_id);
			ps.setInt(2, x.request_id);

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	//**
	public static void cancelLike(LikeReqDB x)
	{
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			String query = "DELETE FROM likereq WHERE like_id = "+x.like_id;
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	//**
	public static int getLikeId(LikeReqDB x)
	{
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			Statement st = con.createStatement();
			String query = "SELECT * FROM likereq";
			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				if (x.user_id == rs.getInt(2) && x.request_id == rs.getInt(3))
					return rs.getInt(1);
			}
			st.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return -1;
	}

	// test
//	public static void main(String[] args)
//	{
//		LikeReqDB x = new LikeReqDB();
//		x.like_id = 32;
//		LikeReqManager.cancelLike(x);
//		
//	}
}
