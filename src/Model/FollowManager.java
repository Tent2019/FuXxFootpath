package Model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class FollowManager
{
	public static ArrayList<FollowDB> getAllFollow()
	{
		ArrayList<FollowDB> list = new ArrayList<FollowDB>();

		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			Statement st = con.createStatement();
			String query = "SELECT * FROM footpath";
			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				FollowDB x = new FollowDB();
				x.complain_id = rs.getInt(1);
				x.user_id = rs.getInt(2);
				x.date = rs.getString(3);
				x.khet = rs.getString(4);
				x.road = rs.getString(5);
				x.detail = rs.getString(6);

				byte[] bE = rs.getBytes(7);
				ByteArrayInputStream baisE = new ByteArrayInputStream(bE);
				BufferedImage bimgE = ImageIO.read(baisE);
				x.imgevent = bimgE;

				byte[] bC = rs.getBytes(8);
				ByteArrayInputStream baisC = new ByteArrayInputStream(bC);
				BufferedImage bimgC = ImageIO.read(baisC);
				x.imgcorrect = bimgC;

				list.add(x);
			}

			st.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return list;
	}

	public static ArrayList<FollowDB> searchFollow(String s)
	{
		ArrayList<FollowDB> search = new ArrayList<FollowDB>();

		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			Statement st = con.createStatement();
			String query = "SELECT * FROM footpath WHERE khet = '" + s + "' OR road = '" + s + "'";
			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				FollowDB x = new FollowDB();
				x.complain_id = rs.getInt(1);
				x.user_id = rs.getInt(2);
				x.date = rs.getString(3);
				x.khet = rs.getString(4);
				x.road = rs.getString(5);
				x.detail = rs.getString(6);

				byte[] bE = rs.getBytes(7);
				ByteArrayInputStream baisE = new ByteArrayInputStream(bE);
				BufferedImage bimgE = ImageIO.read(baisE);
				x.imgevent = bimgE;

				byte[] bC = rs.getBytes(8);
				ByteArrayInputStream baisC = new ByteArrayInputStream(bC);
				BufferedImage bimgC = ImageIO.read(baisC);
				x.imgcorrect = bimgC;

				search.add(x);
			}

			st.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return search;
	}

	public static void insertEvent(FollowDB x)
	{
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			String query = "INSERT INTO footpath VALUES (0, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query);

			ps.setInt(1, x.user_id);
			ps.setString(2, x.date);
			ps.setString(3, x.khet);
			ps.setString(4, x.road);
			ps.setString(5, x.detail);

			if (x.imgevent != null)
			{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(x.imgevent, "png", baos);
				byte[] b = baos.toByteArray();
				ps.setBytes(6, b);

			} else
			{
				byte[] b = new byte[0];
				ps.setBytes(6, b);
			}

			if (x.imgcorrect != null)
			{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(x.imgevent, "png", baos);
				byte[] b = baos.toByteArray();
				ps.setBytes(7, b);

			} else
			{
				byte[] b = new byte[0];
				ps.setBytes(7, b);
			}

			ps.executeUpdate();
			ps.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void editFollow(FollowDB x)
	{
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			String query = "UPDATE footpath SET imgcorrect = ? WHERE complain_id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			
			if (x.imgcorrect != null)
			{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(x.imgcorrect, "png", baos);
				byte[] b = baos.toByteArray();
				ps.setBytes(1, b);

			} else
			{
				byte[] b = new byte[0];
				ps.setBytes(1, b);
			}
			
			ps.setInt(2, x.complain_id);

			ps.executeUpdate();
			ps.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void deleteFollow(FollowDB x)
	{
		try
		{
			String url = "jdbc:mysql://127.0.0.1:3306/fuxxfootpath";
			Connection con = DriverManager.getConnection(url, "root", "");
			String query = "DELETE FROM footpath WHERE complain_id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, x.complain_id);

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
//		FollowManager.searchFollow("dusit");
//	}
}
