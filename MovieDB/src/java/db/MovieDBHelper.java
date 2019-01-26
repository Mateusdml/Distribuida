package db;
/**
 *
 * @author mateu
 */

import exception.SoapException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import model.Filme;

public class MovieDBHelper {
    private static MovieDBHelper ourInstance = new MovieDBHelper();
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DIRECTOR = "director";
    private static final String STUDIO = "studio";
    private static final String GENRE = "genre";
    private static final String LAUNCH_YEAR = "launch_year";
    static List<String> insertColumns = new ArrayList<>(Arrays.asList(TITLE, DIRECTOR, STUDIO, GENRE, LAUNCH_YEAR));
    static String tableName = "movies";

    private MovieDBHelper() {
    }

    public static MovieDBHelper getInstance() {
        return ourInstance;
    }

    public final Filme cadastrarFilme(Filme filme) throws SoapException {
        Connection connection = DatabaseHelper.getConnection();
        Filme resultado;

        if (connection != null) {
            resultado = findByEquals(connection, TITLE, filme.getTitulo());
            if (resultado != null) {
                DatabaseHelper.closeConnection(connection);
                throw new SoapException("Filme " + filme.getTitulo() +  " já cadastrado.");
            } else {
                List<String> values = new ArrayList<>(Arrays.asList(filme.getTitulo(), filme.getDiretor(), filme.getEstudio(), filme.getGenero(), filme.getLancamento()));
                Map<String, String> keyVal = new LinkedHashMap<>();

                for (int i = 0; i < insertColumns.size(); i++) {
                    keyVal.put(insertColumns.get(i), values.get(i));
                }

                int res = DatabaseHelper.insertInto(connection, tableName, keyVal);

                if (res > 0) {
                    resultado = findByEquals(connection, TITLE, filme.getTitulo());
                } else {
                    DatabaseHelper.closeConnection(connection);
                    throw new SoapException("Não foi possível cadastrar o filme \"" + filme.getTitulo() + "\".");
                }
            }
            DatabaseHelper.closeConnection(connection);
        } else throw new SoapException("Conexão falhou");
        return resultado;
    }

    public final Filme renomearFilme(String id, String newTitle) throws SoapException {
        Connection connection = DatabaseHelper.getConnection();
        Filme filme;

        if (connection != null) {

            Filme match = this.findByEquals(connection, ID, id);

            if (match == null) {
                DatabaseHelper.closeConnection(connection);
                throw new SoapException("Filme não encontrado.");
            } else {
                Map<String, String> fieldsToUpdate = new LinkedHashMap<>();
                Map<String, String> whereClause = new LinkedHashMap<>();

                fieldsToUpdate.put(TITLE, newTitle);
                whereClause.put(ID, match.getId());

                int resultado = DatabaseHelper.update(connection, tableName, fieldsToUpdate, whereClause);

                if (resultado > 0) {
                    filme = findByEquals(connection, ID, match.getId());
                } else {
                    DatabaseHelper.closeConnection(connection);
                    throw new SoapException("Filme não atualizado");
                }

            }
            DatabaseHelper.closeConnection(connection);
            
        } else throw new SoapException("Conexão falhou");

        return filme;
    }

    public final Filme deletarFilme(String id) throws SoapException {
        Connection connection = DatabaseHelper.getConnection();
        Filme filme;

        if (connection != null) {
            Filme match = this.findByEquals(connection, ID, id);

            if (match == null) {
                DatabaseHelper.closeConnection(connection);
                throw new SoapException("Filme não encontrado.");
            } else {
                Map<String, String> whereCondition = new LinkedHashMap<>();

                whereCondition.put(ID, match.getId());

                int resultado = DatabaseHelper.delete(connection, tableName, whereCondition);

                if (resultado > 0) {
                    filme = match;
                } else {
                    DatabaseHelper.closeConnection(connection);
                    throw new SoapException("Filme não deletado");
                }
            }

            DatabaseHelper.closeConnection(connection);
        } else throw new SoapException("Conexão falhou");

        return filme;
    }

    private Filme findByEquals(Connection connection, String column, String value) {
        try {
            PreparedStatement sql;
            sql = connection.prepareStatement("select * from " + tableName + " where " + column + " = ?");
            sql.setString(1, value.toUpperCase());
            ResultSet resultado = sql.executeQuery();

            ArrayList<Filme> filmes = this.iterateOverResultSet(resultado);

            return filmes.size() > 0 ? filmes.get(0) : null;
        } catch (SQLException e) {
            return null;
        }

    }

    private ArrayList<Filme> findByLike(Connection connection, String column, String value) {
        try {
            PreparedStatement sql;
            sql = connection.prepareStatement("select * from " + tableName + " where " + column + " like ?");
            sql.setString(1, "%" + value.toUpperCase() + "%");
            ResultSet resultado = sql.executeQuery();
            return this.iterateOverResultSet(resultado);
        } catch (SQLException e) {
            return new ArrayList<>();
        }

    }

    public final ArrayList<Filme> listarFilmes() throws SoapException {
        Connection connection = DatabaseHelper.getConnection();

        if (connection == null) {
            throw new SoapException("Conexão Falhou!");
        } else {
            try {
                PreparedStatement sql = connection.prepareStatement("SELECT * FROM " + tableName);
                ResultSet resultado = sql.executeQuery();
                return this.iterateOverResultSet(resultado);
            } catch (SQLException e) {
                return new ArrayList<>();
            }
        }
    }

    public final ArrayList<Filme> findByGlobal(String search) throws SoapException {
        Connection connection = DatabaseHelper.getConnection();

        if (connection != null) {
            Filme byId = this.findByEquals(connection, ID, search);
            ArrayList<Filme> filmes = new ArrayList<>();
            if (byId == null) {
                ArrayList<Filme> byTitle = this.findByLike(connection, TITLE, search);
                if (byTitle.isEmpty()) {
                    ArrayList<Filme> byDirector = this.findByLike(connection, DIRECTOR, search);
                    if (byDirector.isEmpty()) {
                        ArrayList<Filme> byGenre = this.findByLike(connection, GENRE, search);
                        filmes = byGenre.isEmpty() ? this.findByLike(connection, LAUNCH_YEAR, search) : byGenre;
                    } else {
                        filmes = byDirector;
                    }
                } else {
                    filmes = byTitle;
                }
            } else {
                filmes.add(byId);
            }

            DatabaseHelper.closeConnection(connection);

            return filmes;
        } else {
            throw new SoapException("Conexão Falhou!");
        }
    }

    private ArrayList<Filme> iterateOverResultSet(ResultSet rs) {
        ArrayList<Filme> filmes = new ArrayList<>();
        try {
            while (rs.next()) {
                String id = rs.getString(ID);
                String titulo = rs.getString(TITLE);
                String diretor = rs.getString(DIRECTOR);
                String estudio = rs.getString(STUDIO);
                String genero = rs.getString(GENRE);
                String lancamento = rs.getString(LAUNCH_YEAR);
                filmes.add(new Filme(id, titulo, diretor, estudio, genero, lancamento));
            }
        } catch (SQLException e) {
        }
        return filmes;
    }

}
