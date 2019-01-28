package model;

/**
 *
 * @author mateus
 */
public class Filme {
    private String id;
    private String titulo;
    private String diretor;
    private String estudio;
    private String genero;
    private String lancamento;

    public Filme() {}

    public Filme(String titulo, String diretor, String estudio, String genero, String lancamento) {
        this.id = null;
        this.titulo = titulo;
        this.diretor = diretor;
        this.estudio = estudio;
        this.genero = genero;
        this.lancamento = lancamento;
    }

    public Filme(String id, String titulo, String diretor, String estudio, String genero, String lancamento) {
        this.id = id;
        this.titulo = titulo;
        this.diretor = diretor;
        this.estudio = estudio;
        this.genero = genero;
        this.lancamento = lancamento;
    }

    public final String getId() { return this.id; }

    public final String getTitulo() {
        return this.titulo;
    }

    public final String getDiretor() {
        return this.diretor;
    }

    public final String getEstudio() {
        return this.estudio;
    }

    public final String getGenero() {
        return this.genero;
    }

    public final String getLancamento() {
        return this.lancamento;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setLancamento(String lancamento) {
        this.lancamento = lancamento;
    }

    @Override
    public String toString() {
        return "Filme (id=" + this.id + ", titulo=" + this.titulo + ", diretor=" + this.diretor + ", estudio=" + this.estudio + ", genero=" + this.genero + ", lancamento=" + this.lancamento + ")";
    }
}

