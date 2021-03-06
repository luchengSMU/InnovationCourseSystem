package smu.vccs.model;

import java.sql.*;

public class DataBase
{
	private String driverStr = "com.mysql.jdbc.Driver";
	// connStr="jdbc:mysql://localhost:3306/lc284?useUnicode=true&characterEncoding=utf-8";
	// private String dbusername="lc284";
	// private String dbpassword="lc284";
	private String connStr = "jdbc:mysql://localhost:3306/choose_course?useUnicode=true&characterEncoding=utf-8";
	private String dbusername = "root";
	public static String dbpassword = "";
//	public static String dbpassword = "root";
	private Connection conn = null;
	private Statement stmt = null;

	public DataBase()
	{
		try
		{
			Class.forName(driverStr);
			conn = DriverManager.getConnection(connStr, dbusername, dbpassword);
			stmt = conn.createStatement();
		}
		catch (Exception ex)
		{
			System.out.println("Can not connect to the db" + ex);
		}
	}

	public static int course_count(String type)
	{
		ResultSet rs;
		ResultSet rs2;
		DataBase db = new DataBase();
		try
		{

			rs2 = db.executeQuery("select " + type + " from status");
			rs2.next();
			int a = Integer.parseInt(rs2.getString(1));
			rs = db.executeQuery("select count(*) from course where ctype='"
					+ type + "'");
			rs.next();

			return a - rs.getInt(1);
		}
		catch (SQLException e)
		{

			e.printStackTrace();
			return -1;
		}

	}

	public String status()
	{
		String sql = "select period from status";
		ResultSet rs;
		try
		{
			rs = stmt.executeQuery(sql);
			String period = "";
			if (rs.next())
			{
				period = rs.getString(1);
			}
			int a = Integer.parseInt(period);
			if (a == 0)
				period = "课程发布阶段";
			else
				if (a == 1)
					period = "预选阶段";
				else
					period = "正选阶段";
			return period;
		}
		catch (SQLException e)
		{

			e.printStackTrace();
			return null;
		}

	}

	public int executeUpdate(String s)
	{
		int result = 0;
		try
		{
			result = stmt.executeUpdate(s);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
		return result;
	}

	public ResultSet executeQuery(String s)
	{
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(s);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
		return rs;
	}

	public void execute(String s)
	{
		try
		{
			stmt.execute(s);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
	}

	public void close()
	{
		try
		{
			stmt.close();
			conn.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	


	public static void main(String[] args) throws SQLException
	{
		DataBase db = new DataBase();
		ResultSet rs = db.executeQuery("select * from status");
		rs.next();
		System.out.print(rs.getInt(2));

	}
}
