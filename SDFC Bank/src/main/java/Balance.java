import java.sql.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Balance
 */
@WebServlet("/Balance")
public class Balance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Balance() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		long acountno=Long.parseLong(request.getParameter("accno"));
		String name=request.getParameter("uname");
		String password=request.getParameter("psw");
		out.print("<table border='2'>");
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sathyadb","sathyadb");
			PreparedStatement ps=con.prepareStatement("select * from account where acountno=? and name=? and password=?");
			ps.setLong(1, acountno);
			ps.setString(2, name);
			ps.setString(3, password);
			
			ResultSet rs=ps.executeQuery();
			ResultSetMetaData rss=rs.getMetaData();
			int n=rss.getColumnCount();
			
			
			for(int i=1;i<=n;i++)
			out.print("<td><br>"+rss.getColumnName(i));
			
			out.print("<tr>");
			
			while(rs.next())
			{
				for(int i=1;i<=n;i++)
					out.print("<td><font color='blue' size='3'><br>"+rs.getString(i));
					out.print("<tr>");
			}
			out.println("</table>");
			con.close();
		
			
			
			
		} catch (Exception e) {
			out.print(e);
		}
	}

}
