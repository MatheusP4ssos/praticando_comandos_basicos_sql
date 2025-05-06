package application;

import db.DB;
import db.DbException;
import db.DbIntegrityException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Transacao {
    public static void main(String[] args) {

        Connection conn = null; // Objeto para a conexão com o banco de dados
        Statement ps = null; // Objeto para executar comandos SQL

        try {
            conn = DB.getConnection(); // Obtém a conexão com o banco

            conn.setAutoCommit(false); // Desativa o auto-commit para controlar a transação manualmente

            ps = conn.createStatement(); // Cria um objeto Statement para executar SQL

            // Executa uma atualização: aumenta o salário de vendedores do departamento 1
            int rows1 = ps.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");

            int x = 1;
            // Simula um erro proposital para testar o rollback
            if (x < 2) {
                throw new SQLException("Fake error");
            }

            // Esta linha não será executada por causa do erro acima
            int rows2 = ps.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");

            conn.commit(); // Confirma a transação (não será chamada se houver exceção)

            System.out.println("rows1: " + rows1); // Exibe o número de linhas afetadas pela primeira atualização
            System.out.println("rows2: " + rows2); // Esta linha não será alcançada


        } catch (SQLException e) {
            try {
                conn.rollback(); // Cancela todas as alterações feitas até aqui
                throw new DbException("Transação cancelada! Causa: " + e.getMessage()); // Lança exceção personalizada
            } catch (SQLException e1) {
                throw new DbException("Erro ao tentar cancelar transação! Causa: " + e1.getMessage()); // Se falhar o rollback
            }
        } finally {
            DB.closeStatement(ps); // Fecha o Statement
            DB.closeConnection(); // Fecha a conexão com o banco
        }
    }
}
