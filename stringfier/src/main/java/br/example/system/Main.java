package br.example.system;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            File fIn = new File("/tmp/marketing/base852k.csv");
            File fOut = new File("/tmp/marketing/base852k-out.csv");

            BufferedReader bufIn = new BufferedReader(new FileReader(fIn));
            BufferedWriter bufOut = new BufferedWriter(new FileWriter(fOut));

            List<String> rows = new ArrayList<>();

            String linha;
            while ((linha = bufIn.readLine()) != null){
                String s = normalize(linha);

                rows.add(s);

                bufOut.write(s);
                bufOut.newLine();
            }

            bufOut.flush();
            bufOut.close();

            bufOut.close();



            insertDB(rows);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Connection createConnection() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String url = "jdbc:mysql://localhost/marketing";
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(url, "root", "mysql");

        return conn;
    }



    private static void insertDB(List<String> rows) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        Connection conn = createConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO clientesfull(nome, sobrenome, email, documento, genero) VALUES (?, ?, ?, ?, ?)");

        int rowNum = 0;
        for (String row : rows) {
            rowNum++;
            if (rowNum == 1){
                continue;
            }

            String[] tmp = row.split(";");

            ps.setString(1, tmp[0]);
            ps.setString(2,tmp[1]);
            ps.setString(3,tmp[2]);

            ps.setString(4,"");
            ps.setString(5,"");

            ps.addBatch();

            if (rowNum % 30000 == 0){
                ps.executeLargeBatch();
                ps.clearBatch();
            }
        }

        if (rowNum % 30000 != 0){
            ps.executeLargeBatch();
        }

    }

    private static String normalize(String value){
        value = Normalizer.normalize(value, Normalizer.Form.NFD);
        return value.replaceAll("[^\\x00-\\x7F]", "");
    }
}
