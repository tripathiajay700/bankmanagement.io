import java.sql.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.module.ResolutionException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AccOpen
 */
@WebServlet("/AccOpen")
public class AccOpen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccOpen() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		long accuntno=Long.parseLong(request.getParameter("accno"));
		String name=request.getParameter("uname");
		String password=request.getParameter("psw");
		String cnfpassword=request.getParameter("cpsw");
		double amount=Double.parseDouble(request.getParameter("amt"));
		String address=request.getParameter("addr");
		long mobileno=Long.parseLong(request.getParameter("mno"));
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sathyadb","sathyadb");
			PreparedStatement ps=con.prepareStatement("insert into account values(?,?,?,?,?,?,?)");
			ps.setLong(1, accuntno);
			ps.setString(2, name);
			ps.setString(3, password);
			ps.setString(4, cnfpassword);
			ps.setDouble(5, amount);
			ps.setString(6, address);
			ps.setLong(7, mobileno);
			if(password.equals(cnfpassword))
				out.println(" Record inserted Successfully......"+ps.executeUpdate());
			else {
				out.println("Confirm Password don't match");
				out.println("Please Enter the correct Password......!");
			}
			
		} catch (Exception e) {
			out.println(e);
		}
	
	}

}
