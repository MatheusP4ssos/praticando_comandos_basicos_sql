package application;

import db.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Recuperar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DB.getConnection();
            stmt = conn.createStatement();

            System.out.println("Which table do you need to check? (01) Department or (02) Seller ");
            char ch = sc.next().charAt(0);

            if (ch == '1') {
                rs = stmt.executeQuery("SELECT * FROM department");
                while (rs.next()) {
                    System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
                }
            } else if (ch == '2') {
                rs = stmt.executeQuery("SELECT * FROM seller");
                while (rs.next()) {
                    System.out.println(
                            rs.getInt("Id") + ", "
                                    + rs.getString("Name") + ", "
                                    + rs.getString("Email") + ", "
                                    + sdf.format(rs.getDate("BirthDate")) + ", "
                                    + rs.getDouble("BaseSalary") + ", "
                                    + rs.getInt("DepartmentId")
                    );
                }
            } else {
                System.out.println("Please enter a valid table");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(stmt);
            DB.closeConnection();
            sc.close();
        }
    }
}
