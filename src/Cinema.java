import java.time.LocalTime;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Cinema {
    public static void main(String[] args) {
        List<Sessao> sessoes = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        boolean isQuitTrue = false;
        do{
            menu();
            int option = input.nextInt();
            switch (option) {
                case 1 -> checkDailySessions(sessoes);
                case 2 -> boxOffice(sessoes, input);
                case 3 -> includeSession(sessoes, input);
                case 4 -> checkSession(sessoes, input);
                case 5 -> {
                    System.out.println("===========");
                    System.out.println("Fechando...");
                    isQuitTrue = true;
                }
                default -> System.out.println("Opção inválida");
            }
        }while(!isQuitTrue);
        input.close();
    }

    private static void menu() {
        System.out.println("Cinema Emma");
        System.out.println("===============================");
        System.out.println("1 - Checar Sessões do Dia");
        System.out.println("2 - Venda de Ingressos");
        System.out.println("3 - Incluir Sessão");
        System.out.println("4 - Consultar Sessão");
        System.out.println("5 - Fechar Sistema");
        System.out.println("==============================");
        System.out.println("Qual operação deseja realizar?");
        System.out.print("R: ");
    }

    private static void menuGenre() {
        System.out.println("Gênero de Filme");
        System.out.println("===============");
        System.out.println("1 - Ação");
        System.out.println("2 - Aventura");
        System.out.println("3 - Suspense");
        System.out.println("4 - Comédia");
        System.out.println("5 - Drama");
        System.out.println("===============");
        System.out.print("R: ");
    }

    private static Filme.EnumGeneroFilme generoEscolhido(Scanner input) {
        boolean isQuitTrue = false;
        Filme.EnumGeneroFilme filme = null;
        do{
            menuGenre();
            int option = input.nextInt();
            switch (option) {
                case 1 -> filme = Filme.EnumGeneroFilme.ACAO;
                case 2 -> filme = Filme.EnumGeneroFilme.AVENTURA;
                case 3 -> filme = Filme.EnumGeneroFilme.SUSPENSE;
                case 4 -> filme = Filme.EnumGeneroFilme.COMEDIA;
                case 5 -> filme = Filme.EnumGeneroFilme.DRAMA;
                default -> System.out.println("Opção inválida");
            }
            if(filme != null)
                isQuitTrue = true;

        }while(!isQuitTrue);

        return filme;
    }

    private static void checkDailySessions(List<Sessao> sessoes) {
        if(sessoes.isEmpty()) {
            System.out.println("Não há sessões disponíveis para hoje.");
            return;
        }

        LocalTime horarioAtual = LocalTime.now();
        boolean existemSessoesFuturas = false;

        for(Sessao sessao : sessoes) {
            int minutosDesdeMeiaNoiteInicio = sessao.getHorario() / 100 * 60 + sessao.getHorario() % 100;
            int minutosDuracaoFilme = sessao.getFilme().getDuracao();
            LocalTime horarioSessaoInicio = LocalTime.of(minutosDesdeMeiaNoiteInicio / 60, minutosDesdeMeiaNoiteInicio % 60);
            LocalTime horarioSessaoFim = horarioSessaoInicio.plusMinutes(minutosDuracaoFilme);

            if(horarioAtual.isBefore(horarioSessaoFim) || (horarioAtual.isAfter(horarioSessaoInicio) && horarioAtual.isBefore(horarioSessaoFim))) {
                existemSessoesFuturas = true;
                if(horarioAtual.isBefore(horarioSessaoInicio) || horarioAtual.equals(horarioSessaoInicio))
                    System.out.println(sessao);
            }
        }
        if(!existemSessoesFuturas)
            System.out.println("Todas as sessões para hoje já ocorreram.");
    }

    private static void boxOffice(List<Sessao> sessoes, Scanner input) {
        if(sessoes.isEmpty()) {
            System.out.println("Não há sessões disponíveis para hoje.");
            return;
        }

        System.out.println("Box Office");
        System.out.println("==========");
        System.out.println("Insira o nome do Filme: ");
        input.nextLine();
        String titulo = input.nextLine();


        for(Sessao sessao : sessoes) {
            if(sessao.checkFilme(sessoes, titulo)) {
                if (sessao.getFilme().getTitulo().equals(titulo)) {
                    System.out.println(sessao);

                    System.out.println("Quantos ingressos você deseja comprar?");
                    int quantidadeIngressos = input.nextInt();

                    if (!sessao.assentosDisponiveis(quantidadeIngressos)) {
                        System.out.println("Não há assentos suficientes disponíveis para o número desejado de ingressos.");
                        return;
                    }

                    for (int i = 0; i < quantidadeIngressos; i++) {
                        System.out.println("Selecione o tipo de ingresso (1 - Meio, 2 - Inteiro): ");
                        int tipoIngressoOption = input.nextInt();
                        Ingresso.EnumTipoIngresso tipoIngresso;
                        switch (tipoIngressoOption) {
                            case 1 -> tipoIngresso = Ingresso.EnumTipoIngresso.MEIO;
                            case 2 -> tipoIngresso = Ingresso.EnumTipoIngresso.INTEIRO;
                            default -> {
                                System.out.println("Opção inválida");
                                return;
                            }
                        }

                        System.out.println("Selecione a categoria de ingresso (1 - Físico, 2 - Online): ");
                        int categoriaIngressoOption = input.nextInt();
                        Ingresso.EnumCategoriaIngresso categoriaIngresso;
                        switch (categoriaIngressoOption) {
                            case 1 -> categoriaIngresso = Ingresso.EnumCategoriaIngresso.FISICO;
                            case 2 -> categoriaIngresso = Ingresso.EnumCategoriaIngresso.ONLINE;
                            default -> {
                                System.out.println("Opção inválida");
                                return;
                            }
                        }
                        Ingresso ingresso = new Ingresso(tipoIngresso, categoriaIngresso, sessao);
                        sessao.registraIngresso(ingresso);
                    }
                }
                else
                    System.out.println("Filme não catalogado.");
            }
        }
    }


    private static void includeSession(List<Sessao> sessoes, Scanner input) {
        System.out.println("Inclusão de Sessão");
        System.out.println("==================");

        System.out.print("Título do Filme: ");
        input.nextLine();

        String tituloFilme = "";

        while (tituloFilme.isEmpty()) {
            tituloFilme = input.nextLine().trim();

            if (tituloFilme.isEmpty()) {
                System.out.println("Por favor, insira um título válido para o filme. Tente novamente.");
            }
        }


        System.out.print("Duração: ");
        int duracao = 0;
        while (duracao <= 0) {
            if (input.hasNextInt()) {
                duracao = input.nextInt();
                if (duracao <= 0) {
                    System.out.println("A duração deve ser um número inteiro positivo. Tente novamente.");
                }
            } else {
                System.out.println("Por favor, insira um valor válido para a duração. Tente novamente.");
                input.next(); // consume the invalid input
            }
        }

        Filme.EnumGeneroFilme genero = generoEscolhido(input);

        System.out.print("Número de Assentos: ");
        int nAssentos = 0;
        while (nAssentos <= 0) {
            if (input.hasNextInt()) {
                nAssentos = input.nextInt();
                if (nAssentos <= 0) {
                    System.out.println("O número de assentos deve ser um número inteiro positivo. Tente novamente.");
                }
            } else {
                System.out.println("Por favor, insira um valor válido para o número de assentos. Tente novamente.");
                input.next(); // consume the invalid input
            }
        }

        System.out.print("Tipo de Tela: ");
        input.nextLine(); // consume the newline character
        String tipoTela = input.next();

        System.out.print("Local: ");
        String local = input.next();

        System.out.print("Horário da Sessão: ");
        int horarioStr = 0;
        while (horarioStr <= 0 || horarioStr > 23) {
            if (input.hasNextInt()) {
                horarioStr = input.nextInt();
                if (horarioStr <= 0 || horarioStr > 23) {
                    System.out.println("O horário da sessão deve ser um número inteiro positivo entre 1 e 23. Tente novamente.");
                }
            } else {
                System.out.println("Por favor, insira um valor válido para o horário da sessão. Tente novamente.");
                input.next(); // consume the invalid input
            }
        }

        Sessao novaSessao = new Sessao(horarioStr);
        Filme filme = new Filme(tituloFilme, duracao, genero, novaSessao);
        Sala sala = new Sala(nAssentos, tipoTela, local);
        novaSessao.registraFilme(filme);
        novaSessao.addSala(sala);
        sessoes.add(novaSessao);

        System.out.println("Sessão agendada com sucesso!");
    }

    private static void checkSession(List<Sessao> sessoes, Scanner input) {
        if(sessoes.isEmpty()) {
            System.out.println("Não há sessões disponíveis para hoje.");
            return;
        }
        System.out.println("==========");
        System.out.println("Insira o nome do filme: ");
        input.nextLine();
        String nome = input.nextLine();
        for(Sessao sessao : sessoes) {
            if(sessao.getFilme().getTitulo().equals(nome))
                System.out.println(sessao);
        }
    }
}