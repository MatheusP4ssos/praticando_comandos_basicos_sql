package application;

import db.DB;
import db.DbIntegrityException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Deletar {
    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DB.getConnection();

            ps = conn.prepareStatement(
                    "DELETE FROM department"
                            + " WHERE " // não esquecer de usar "where" para especificar oq se quer deletar, pois do contrario pode deletar a tabela inteira
                            + "Id = ?");

            ps.setInt(1, 2);

            int rowsAffected = ps.executeUpdate();

            System.out.println("Done! Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            throw new DbIntegrityException("Não é possível atualizar ou excluir porque há registros relacionados (chave estrangeira).");
        } finally {
            DB.closeStatement(ps);
            DB.closeConnection();
        }
    }
}
