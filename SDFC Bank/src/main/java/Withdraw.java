import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Withdraw
 */
@WebServlet("/Withdraw")
public class Withdraw extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Withdraw() {
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
		double amount=Double.parseDouble(request.getParameter("amt"));
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sathyadb","sathyadb");
			PreparedStatement ps=con.prepareStatement("select amount from account where acountno=? and name=? and password=?");
			ps.setLong(1, acountno);
			ps.setString(2, name);
			ps.setString(3, password);
			
			ResultSet rs=ps.executeQuery();
			ResultSetMetaData rss=rs.getMetaData();
			int n=rss.getColumnCount();
			while(rs.next())
			{
				for(int i=1;i<=n;i++)
					out.println("Before Withdraw A/C Balance is :"+rs.getDouble(i)+"<br>");
							}
			
			PreparedStatement ps1=con.prepareStatement("update account set amount=amount-? where acountno=? and name=? and password=?");
			ps1.setDouble(1, amount);
			ps1.setLong(2, acountno);
			ps1.setString(3, name);
			ps1.setString(4, password);
			ps1.executeUpdate();
			out.println("Withdraw Amount is:"+amount+"<br>");

			PreparedStatement ps2=con.prepareStatement("select amount from account where acountno=? and name=? and password=?");
			ps2.setLong(1, acountno);
			ps2.setString(2, name);
			ps2.setString(3, password);
			
			ResultSet rs1=ps2.executeQuery();
			ResultSetMetaData rss1=rs1.getMetaData();
			int n1=rss1.getColumnCount();
			while(rs1.next())
			{
				for(int i=1;i<=n;i++)
					out.println("After Withdraw A/C Balance is :"+rs1.getDouble(i)+"<br>");
							
			}
			
			con.close();
	
		} catch (Exception e) {
			out.println(e);
		}
		
	}

}
