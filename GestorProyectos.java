import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GestorProyectos {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:portafolio.db";
        Scanner lector = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(url)) {
            // 1. PEDIR DATOS AL USUARIO
            System.out.println("--- 📝 AGREGAR NUEVO PROYECTO ---");
            System.out.print("Nombre del proyecto: ");
            String nombre = lector.nextLine();
            System.out.print("Lenguaje utilizado: ");
            String lenguaje = lector.nextLine();

            // 2. GUARDAR EN LA BASE DE DATOS
            String sqlInsert = "INSERT INTO proyectos(nombre, lenguaje) VALUES(?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
                pstmt.setString(1, nombre);
                pstmt.setString(2, lenguaje);
                pstmt.executeUpdate();
                System.out.println("✅ Guardado en SQLite.");
            }

            // 3. EXPORTAR TODO A JSON PARA LA WEB
            String sqlSelect = "SELECT * FROM proyectos";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlSelect);

            StringBuilder json = new StringBuilder("[\n");
            while (rs.next()) {
                json.append("  {\n")
                        .append("    \"id\": ").append(rs.getInt("id")).append(",\n")
                        .append("    \"nombre\": \"").append(rs.getString("nombre")).append("\",\n")
                        .append("    \"lenguaje\": \"").append(rs.getString("lenguaje")).append("\"\n")
                        .append("  },");
            }
            if (json.length() > 2)
                json.setLength(json.length() - 1);
            json.append("\n]");

            try (FileWriter file = new FileWriter("proyectos.json")) {
                file.write(json.toString());
                System.out.println("🚀 ¡Base de datos sincronizada con 'proyectos.json'!");
            }

        } catch (SQLException | IOException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } finally {
            lector.close();
        }
    }
}