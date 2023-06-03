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


@WebServlet("/Deposit")
public class Deposit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deposit() {
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
		double damount=Double.parseDouble(request.getParameter("amt"));
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sathyadb","sathyadb");
			
			
			PreparedStatement ps1=con.prepareStatement("select amount from account where acountno=? and name=? and password=?");
			ps1.setLong(1, acountno);
			ps1.setString(2, name);
			ps1.setString(3, password);
			ResultSet rs=ps1.executeQuery();
			double amount=0.0;
			if(rs.next())
			{
				amount = rs.getDouble("amount");
				
			}
			out.println ("Before Deposite Account Balance is :"+amount+"<br>");
			out.println("Deposite Amount is :"+damount+"<br>");
			amount = amount+damount;
			out.println("After Deposite Available Balance is :"+amount+"<br>");
			
			PreparedStatement ps=con.prepareStatement("update account set amount=? where acountno=? and name=? and password=?");
			ps.setDouble(1, amount);
			ps.setLong(2, acountno);
			ps.setString(3, name);
			ps.setString(4, password);
			int j=ps.executeUpdate();
            		


			con.close();
			
		} catch (Exception e) {
			out.println(e);
		}
	}

}
