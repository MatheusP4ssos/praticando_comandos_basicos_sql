package db;

import javax.imageio.IIOException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

// Classe utilitária para gerenciar conexões com o banco
public class DB {

    private static Connection conn = null;

    public static Connection getConnection() {
        // Se ainda não foi criada uma conexão...
        if (conn == null) {
            try {
                Properties props = loadProperties();// Carrega as propriedades do arquivo db.properties
                String url = props.getProperty("dburl");// Lê a URL de conexão do arquivo
                conn = DriverManager.getConnection(url, props);// Abre conexão passando URL e propriedades (user, password, etc.)
            } catch (SQLException e) {  // Se ocorrer um erro ao tentar conectar, lança uma exceção personalizada
                throw new DbException(e.getMessage());
            }
        }
        return conn;// Retorna a conexão existente
    }

    //Método para fechar a conexão
    public static void closeConnection() {
        if (conn != null) { //Caso a conexão tenha sido estanciada
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    // Método privado para carregar as configurações do banco a partir de um arquivo
    private static Properties loadProperties() {
        // Abre o arquivo de propriedades (espera-se que esteja no diretório do projeto)
        try (FileInputStream fis = new FileInputStream("db.properties")) {
            Properties prop = new Properties();// Cria um objeto de properties
            prop.load(fis);// Carrega os dados do arquivo no objeto
            return prop;// Retorna o objeto com as configurações carregadas
        } catch (IOException e) {
            // Se der erro ao carregar o arquivo, lança exceção personalizada
            throw new DbException(e.getMessage());
        }
    }

    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}

