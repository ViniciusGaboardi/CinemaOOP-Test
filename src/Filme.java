public class Filme implements InterfaceCinema {
    public enum EnumGeneroFilme {
        ACAO("Ação"),
        AVENTURA("Aventura"),
        SUSPENSE("Suspense"),
        COMEDIA("Comedia"),
        DRAMA("Drama");

        private String genre;

        EnumGeneroFilme(String genre){
            this.genre = genre;
        }

        public String getGenre() {
            return genre;
        }
    }
    private String titulo;
    private int duracao;
    EnumGeneroFilme genero;
    private Sessao sessao;

    public Filme(String titulo, int duracao, EnumGeneroFilme genero, Sessao sessao) {
        this.titulo = titulo;
        this.duracao = duracao;
        this.genero = genero;
        this.sessao = sessao;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracao() {
        return duracao;
    }

    public EnumGeneroFilme getGenero() {
        return genero;
    }

    public Sessao getSessao(){
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    @Override
    public String toString() {
        return "[Filme: " + getTitulo() + ", duração: " + getDuracao() +
                ", gênero: " + genero.getGenre() + "]";
    }
}