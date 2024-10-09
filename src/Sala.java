public class Sala {
    private int nAssentos;
    private String tipoTela, local;

    public Sala(int nAssentos, String tipoTela, String local) {
        this.nAssentos = nAssentos;
        this.tipoTela = tipoTela;
        this.local = local;
    }

    public int getnAssentos() {
        return nAssentos;
    }

    public String getTipoTela() {
        return tipoTela;
    }

    public String getLocal() {
        return local;
    }

    public void setnAssentos(int nAssentos) {
        this.nAssentos = nAssentos;
    }

    public void setTipoTela(String tipoTela) {
        this.tipoTela = tipoTela;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "Sala: " + getLocal() +", assentos: " + getnAssentos() +
                ", tela: " + getTipoTela();
    }
}