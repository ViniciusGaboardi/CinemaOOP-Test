public class Ingresso implements InterfaceCinema{
    public enum EnumTipoIngresso {
        MEIO(1),
        INTEIRO(2);

        private int tipoIngresso;

        EnumTipoIngresso(int tipoIngresso){
            this.tipoIngresso = tipoIngresso;
        }

        public int getTipoIngresso() {
            return tipoIngresso;
        }
    }

    public enum EnumCategoriaIngresso {
        FISICO(1),
        ONLINE(2);

        private int categoriaIngresso;

        EnumCategoriaIngresso(int categoriaIngresso){
            this.categoriaIngresso = categoriaIngresso;
        }

        public int getTicketType() {
            return categoriaIngresso;
        }
    }

    EnumTipoIngresso tipo;
    EnumCategoriaIngresso categoria;

    public Sessao sessao;

    public Ingresso(EnumTipoIngresso tipo, EnumCategoriaIngresso categoria, Sessao sessao) {
        this.tipo = tipo;
        this.categoria = categoria;
        this.sessao = sessao;
    }

    public EnumTipoIngresso getTipo() {
        return tipo;
    }

    public EnumCategoriaIngresso getCategoria() {
        return categoria;
    }

    public Sessao getSessao(){
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    @Override
    public String toString() {
        return "Ingresso [Tipo: " + getTipo() + ", Categoria: "
                + getCategoria() + ", SessÃ£o: " + getSessao() + "]" + "\n";
    }
}