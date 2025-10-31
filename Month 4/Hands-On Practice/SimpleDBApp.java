import java.sql.*;
import java.io.*;

public class SimpleDBApp {
    public static void main(String[] args) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:sqlite:test.db");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Statement st = con.createStatement();
        st.execute("CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY, name TEXT, balance REAL)");

        while(true){
            System.out.println("\n1) Insert  2) Transfer Money  3) View All  4) Exit");
            String c = br.readLine();
            if(c.equals("1")){
                System.out.print("Name: "); String name = br.readLine();
                System.out.print("Balance: "); double bal = Double.parseDouble(br.readLine());
                PreparedStatement ps = con.prepareStatement("INSERT INTO users(name,balance) VALUES(?,?)");
                ps.setString(1,name); ps.setDouble(2,bal);
                ps.executeUpdate();
                System.out.println("✅ Inserted.");
            } else if(c.equals("2")){
                con.setAutoCommit(false);
                System.out.print("From ID: "); int from = Integer.parseInt(br.readLine());
                System.out.print("To ID: "); int to = Integer.parseInt(br.readLine());
                System.out.print("Amount: "); double amt = Double.parseDouble(br.readLine());
                try {
                    PreparedStatement ps1 = con.prepareStatement("UPDATE users SET balance = balance - ? WHERE id=?");
                    ps1.setDouble(1, amt); ps1.setInt(2, from);
                    ps1.executeUpdate();

                    PreparedStatement ps2 = con.prepareStatement("UPDATE users SET balance = balance + ? WHERE id=?");
                    ps2.setDouble(1, amt); ps2.setInt(2, to);
                    ps2.executeUpdate();

                    con.commit();
                    System.out.println("✅ Transfer completed.");
                } catch(Exception e){
                    con.rollback();
                    System.out.println("❌ Transaction failed. Rolled back.");
                }
                con.setAutoCommit(true);
            } else if(c.equals("3")){
                ResultSet rs = st.executeQuery("SELECT * FROM users");
                while(rs.next()) System.out.println(rs.getInt(1)+". "+rs.getString(2)+" - ₹"+rs.getDouble(3));
            } else break;
        }
        con.close();
    }
}
