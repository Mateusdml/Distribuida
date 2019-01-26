package service;

import db.MovieDBHelper;
import exception.SoapException;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import model.Filme;

/**
 *
 * @author mateu
 */
@WebService(serviceName = "FilmesService")
public class MovieDBService {
    private MovieDBHelper db = MovieDBHelper.getInstance();

    /**
     *
     * @param titulo
     * @param diretor
     * @param estudio
     * @param genero
     * @param lancamento
     * @return
     * @throws SoapException
     */
    @WebMethod(operationName = "cadastrarFilme")
    public Filme cadastrarFilme(
            @WebParam(name = "titulo") String titulo,
            @WebParam(name = "diretor") String diretor,
            @WebParam(name = "estudio") String estudio,
            @WebParam(name = "genero") String genero,
            @WebParam(name = "lancamento") String lancamento) throws SoapException {
            return db.cadastrarFilme(new Filme(titulo, diretor, estudio, genero, lancamento));
    }

    @WebMethod(operationName = "listarTodos")
    public Filme[] listarFilmes() throws SoapException {
        ArrayList<Filme> filmes = db.listarFilmes();
        return filmes.toArray(new Filme[filmes.size()]);
    }

    @WebMethod(operationName = "buscarFilmes")
    public Filme[] buscarFilme(@WebParam(name = "buscarPor") String searchText) throws SoapException {
        ArrayList<Filme> movies = db.findByGlobal(searchText);
        return movies.toArray(new Filme[movies.size()]);
    }

    @WebMethod(operationName = "atualizarFilme")
    @WebResult(name = "filme")
    public Filme atualizarFilme(
            @WebParam(name = "id") String id,
            @WebParam(name = "novoTitulo") String newTitle) throws SoapException {
        return db.renomearFilme(id, newTitle);
    }

    @WebMethod(operationName = "apagarFilme")
    public Filme deleteMovie(@WebParam(name = "id") String id) throws SoapException {
        return db.deletarFilme(id);
    }
}
