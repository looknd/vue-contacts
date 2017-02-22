package servletTest;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import servletTest.sqlSearch;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.*;

/**
 * Servlet implementation class servletSql
 * @author water
 * @version 1.0
 * @email 625592890@qq.com
 */
@WebServlet("/servletSql")
public class servletSql extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection conn;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 加载访问数据库的驱动
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		// 访问数据库的路径
		final String DB_URL = "jdbc:mysql://localhost/TEST?useUnicode=true&characterEncoding=utf-8";
		// 连接数据库的用户名和密码
		final String USER = "root";
		final String PASS = "root";

		// 设置请求头格式
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		// 设置CORS
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 设置编码
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Statement stmt = null;
		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = ((Connection) conn).createStatement();
			String sql;
			sql = "SELECT birthday,battery,name,province,city,address,detailAddress,phoneNumber,email,zip FROM personList";
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<Object> list = new ArrayList<>();

			while (rs.next()) {

				String birthday = rs.getString("birthday");
				String name = rs.getString("name");
				String province = rs.getString("province");
				String city = rs.getString("city");
				String address = rs.getString("address");
				String detailAddress = rs.getString("detailAddress");
				String phoneNumber = rs.getString("phoneNumber");
				String email = rs.getString("email");
				String group = rs.getString("battery");
				int zip = rs.getInt("zip");
				sqlSearch person = new sqlSearch();
				person.setBirthday(birthday);
				person.setName(name);
				person.setProvince(province);
				person.setCity(city);
				person.setAddress(address);
				person.setDetailAddress(detailAddress);
				person.setPhoneNumber(phoneNumber);
				person.setEmail(email);
				person.setZip(zip);
				person.setGroup(group);
				list.add(person);
			}
			String jsonString = JSON.toJSONString(list);
			out.println(jsonString);

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {

			se.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
}