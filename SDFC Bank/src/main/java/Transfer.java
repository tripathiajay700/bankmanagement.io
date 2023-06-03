

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
 * Servlet implementation class Transfer
 */
@WebServlet("/Transfer")
public class Transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Transfer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		long acountno=Long.parseLong(request.getParameter("accno"));
		String name=request.getParameter("uname");
		String password=request.getParameter("psw");
		long tacountno=Long.parseLong(request.getParameter("taccno"));
		double amount=Double.parseDouble(request.getParameter("amt"));
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sathyadb","sathyadb");
			PreparedStatement ps1=con.prepareStatement("update account set amount=amount-? where acountno=? and name=? and password=?");
			ps1.setDouble(1, amount);
			ps1.setLong(2, acountno);
			ps1.setString(3, name);
			ps1.setString(4, password);
			ps1.executeUpdate();
			out.println("Transfer amout is :"+amount+"<br>");
			
			
			
			PreparedStatement ps=con.prepareStatement("select amount from account where acountno=?");
			ps.setLong(1, tacountno);
						
			ResultSet rs=ps.executeQuery();
			ResultSetMetaData rss=rs.getMetaData();
			int n=rss.getColumnCount();
			while(rs.next())
			{
				for(int i=1;i<=n;i++)
					out.println("Target Account Balance is :"+rs.getDouble(i)+"<br>");
							}
			
			PreparedStatement ps2=con.prepareStatement("update account set amount=amount+? where acountno=?");
			ps2.setDouble(1, amount);
			ps2.setLong(2, tacountno);;
			ps2.executeUpdate();
			//out.println("After Transfer Target A/C Balance is:"+amount+"<br>");

			PreparedStatement ps3=con.prepareStatement("select amount from account where acountno=?");
			ps3.setLong(1, tacountno);
			
			ResultSet rs2=ps3.executeQuery();
			ResultSetMetaData rss2=rs2.getMetaData();
			int n1=rss2.getColumnCount();
			while(rs2.next())
			{
				for(int i=1;i<=n1;i++)
					out.println("After Transfer Target A/C Balance is :"+rs2.getDouble(i)+"<br>");
							
			}
			out.println("After A/C detuction by "+amount+"<br>");
			
			PreparedStatement ps4=con.prepareStatement("select amount from account where acountno=?");
			ps4.setLong(1, acountno);
						
			ResultSet rs4=ps4.executeQuery();
			ResultSetMetaData rss4=rs4.getMetaData();
			int n4=rss4.getColumnCount();
			while(rs4.next())
			{
				for(int i=1;i<=n4;i++)
					out.println("After A/C No. 1st :"+rs4.getDouble(i)+"<br>");
							}
			
			
			con.close();
		}
		    catch(Exception e)
			{
				out.println(e);
			}
	}
}
