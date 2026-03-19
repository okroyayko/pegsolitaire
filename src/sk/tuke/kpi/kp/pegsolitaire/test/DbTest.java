package sk.tuke.kpi.kp.pegsolitaire.test;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DbTest {
    public static void main(String[] args) {
        try {
            Properties props = new Properties();
            InputStream in = DbTest.class.getClassLoader().getResourceAsStream("db.properties");

            if (in == null) {
                throw new RuntimeException("db.properties NOT FOUND in classpath");
            }

            props.load(in);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            Connection c = DriverManager.getConnection(url, user, password);
            Statement s = c.createStatement();

            ResultSet rs = s.executeQuery("SELECT count(*) FROM score");

            if (rs.next()) {
                System.out.println("Score rows: " + rs.getInt(1));
            }

            rs.close();
            s.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}