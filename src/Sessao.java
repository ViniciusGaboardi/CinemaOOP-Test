import java.util.List;
import java.util.ArrayList;
import java.time.LocalTime;

public class Sessao {
    private boolean estadoSessao;
    private int horario;
    private List<Sala> salas;
    private List<Ingresso> ingressos;
    private Filme filme;
    private Sala sala;

    public Sessao(int horario){
        this.horario = horario;
        salas = new ArrayList<>();
        ingressos = new ArrayList<>();
    }

    public boolean isEstadoSessao() {
        return estadoSessao;
    }

    public int getHorario() {
        return horario;
    }

    public List<Sala> getSalas(){
        return salas;
    }

    public Sala getSala() {
        return sala;
    }

    public List<Ingresso> getIngressos() {
        return ingressos;
    }

    public Filme getFilme(){
        return filme;
    }

    public void registraFilme(Filme filme) {
        this.filme = filme;
    }

    public void setEstadoSessao(boolean estadoSessao) {
        this.estadoSessao = checkEstadoSessao(horario);
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public void addSala(Sala sala){
        salas.add(sala);
    }

    public String getHorarioFormatado() {
        if(horario >= 0 && horario < 2400 && horario % 100 < 60) {
            int horas = horario / 100;
            int minutos = horario % 100;
            return String.format("%02d:%02d", horas, minutos);
        }
        return "";
    }

    public String estadoSessaoText() {
        LocalTime horarioAtual = LocalTime.now();

        int minutosDesdeMeiaNoite = getHorario() / 100 * 60 + getHorario() % 100;
        int minutosDuracaoFilme = getFilme().getDuracao();
        LocalTime horarioSessaoInicio = LocalTime.of(minutosDesdeMeiaNoite / 60, minutosDesdeMeiaNoite % 60);
        LocalTime horarioSessaoFim = horarioSessaoInicio.plusMinutes(minutosDuracaoFilme);

        if (horarioAtual.isBefore(horarioSessaoInicio))
            return "Logo mais";

        else if (horarioAtual.isBefore(horarioSessaoFim))
            return "Em andamento";

        else
            return "Terminado";
    }

    public boolean checkEstadoSessao(int horario){
        LocalTime horarioAtual = LocalTime.now();
        int minutosDesdeMeiaNoite = getHorario() / 100 * 60 + getHorario() % 100;
        LocalTime horarioSessao = LocalTime.of(minutosDesdeMeiaNoite / 60, minutosDesdeMeiaNoite % 60);

        if(horarioAtual.isBefore(horarioSessao))
            return true;

        else return horarioAtual.equals(horarioSessao);
    }

    public boolean checkFilme(List<Sessao> sessoes, String titulo) {
        for(Sessao sessao : sessoes){
            if(sessao.getFilme().getTitulo().equals(titulo))
                return true;
        }
        return false;
    }

    public boolean checkSala(List<Sessao> sessoes, String local){
        for(Sessao sessao : sessoes) {
            for(Sala sala : salas){
                if(sala.getLocal().equals(local))
                    return true;
            }
        }
        return false;
    }

    public void registraIngresso(Ingresso ingresso) {
        if(checkEstadoSessao(horario)) {
            boolean ingressoRegistrado = false;

            for(Sala sala : salas) {
                if(sala.getnAssentos() > 0) {
                    ingresso.setSessao(this);
                    ingressos.add(ingresso);
                    sala.setnAssentos(sala.getnAssentos() - 1);
                    ingressoRegistrado = true;
                    System.out.println("Ingresso registrado com sucesso: " + "\n" + getIngressos());
                    break;
                }
            }
            if(!ingressoRegistrado)
                System.out.println("Não há mais assentos disponíveis para esta sessão.");
        }
        else
            System.out.println("Não é possível registrar ingresso. Sessão Terminada.");
    }



    public boolean assentosDisponiveis(int nIngressos) {
        for(Sala sala : salas) {
            int assentosDisponiveis = sala.getnAssentos() - nIngressos;
            if(assentosDisponiveis >= 0)
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return filme.toString() + "\n"
                + "[Sessão das: " + getHorarioFormatado()
                + ", status: " + estadoSessaoText() + "]\n"
                + salas.toString()+"\n"
                +"========================================";
    }
}