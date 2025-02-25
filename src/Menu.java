import java.io.IOException;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class Menu {

    private static Service service = new Service();
    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) throws RegraDeNegocioException {
        exibirMenu();
        int i = 0;
        menu(i);

        System.out.println("Finalizando a aplicação.");
    }
    
    private static void menu(int i) throws RegraDeNegocioException {
        while (i != 11) {
            try {
                i = teclado.nextInt();
                if(i < 1 || i > 11) {
                    throw new RegraDeNegocioException("Escolha um número de 1 a 10 para executar as funcionalidades do sistema!");
                }
                if(i ==1) {
                    listarContas();
                } else if (i ==2) {
                    abrirConta();
                }
                else if (i ==3) {
                    encerrarConta();
                }
                else if (i ==4) {
                    consultarSaldo();
                }
                else if (i ==5) {
                    realizarSaque();
                }
                else if (i ==6) {
                    realizarDeposito();
                }
                else if (i == 7) {
                    rendimentoAno();
                }
                else if (i == 8) {
                    listarClientes();
                }
                else if (i == 9) {
                    imprimirConta();
                }
                else if (i == 10) {
                    lerConta();
                }
            } catch (RegraDeNegocioException e) {
                System.out.println("Erro: " +e.getMessage());
                System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu");
                exibirMenu();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("""
               \nBYTEBANK - ESCOLHA UMA OPÇÃO:
               1 - Listar contas abertas
               2 - Abertura de conta
               3 - Encerramento de conta
               4 - Consultar saldo de uma conta
               5 - Realizar saque em uma conta
               6 - Realizar depósito em uma conta
               7 - Verificar rendimento anual da conta
               8 - Listar clientes cadastrados
               9 - Imprimir conta
               10 - Ler conta
               11 - Sair
               """); //Aspas triplas usadas para crias strings multi-linhas, como se estivessemos usando \n2
    }

    private static void listarContas() {
        System.out.println("Contas cadastradas:");
        Set<Conta> contas = service.listarContasAbertas();
        for (Conta conta : contas) {
            System.out.println(conta);
        }
        exibirMenu();
    }

    private static void abrirConta() throws RegraDeNegocioException, ErroDigitoException {
        try {
            System.out.println("Digite o número da conta:");
            int numeroDaConta = teclado.nextInt();


            System.out.println("Digite o nome do cliente:");
            String util = teclado.nextLine();


            String nome = teclado.nextLine();


            System.out.println("Digite o CPF do cliente:");
            String cpf = teclado.next();


            System.out.println("Digite o email do cliente:");
            String email = teclado.next();


            System.out.println("Digite 1 se você vai querer que sua conta seja poupança ou 2 se deseja que ela seja corrente:");
            int resposta1 = teclado.nextInt();
            System.out.println("Digite 1 se você é um cliente especial ou 2 se você é um cliente normal:");
            int resposta2 = teclado.nextInt();
            if(selecionaCliente(resposta2)) {
                service.abrir(new ContaPoupanca(numeroDaConta, new ClienteEspecial(nome, cpf, email, numeroDaConta, selecionaCliente(resposta2)), selecionaTipo(resposta1)));
            } else {
                service.abrir(new ContaPoupanca(numeroDaConta, new ClienteNormal(nome, cpf, email, numeroDaConta, selecionaCliente(resposta2)), selecionaTipo(resposta1)));


            }

            System.out.println("Conta aberta com sucesso!");
            exibirMenu();
        }catch (ErroDigitoException e){
            System.out.println("Digite os valores corretamente");
            exibirMenu();
        }
    }

    private static boolean selecionaTipo(int resposta) {
        boolean tipo;
        while(true) {
            if (resposta == 1) {
                tipo = true;
                break;
            }else if (resposta == 2) {
                tipo = false;
                break;
            }else {
                System.out.println("Digite 1 se você vai querer que sua conta seja poupança ou 2 se deseja que ela seja corrente:");
                resposta = teclado.nextInt();
            }
        }
        return tipo;
    }

    private static boolean selecionaCliente(int resposta) {
        boolean tipo;
        while(true) {
            if (resposta == 1) {
                tipo = true;
                break;
            }else if (resposta == 2) {
                tipo = false;
                break;
            }else {
                System.out.println("Digite 1 se você é um cliente especial ou 2 se você é um cliente normal:");
                resposta = teclado.nextInt();
            }
        }
        return tipo;
    }

    private static void encerrarConta() throws RegraDeNegocioException {
        System.out.println("Digite o número da conta:");
        Integer numeroDaConta = teclado.nextInt();


        service.encerrar(numeroDaConta);


        System.out.println("Conta encerrada com sucesso!");
        exibirMenu();
    }

    private static void consultarSaldo() {
        System.out.println("Digite o número da conta:");
        BigDecimal saldo = service.consultarSaldo(teclado.nextInt());
        System.out.println("Saldo da conta: " +saldo);


        exibirMenu();
    }

    private static void realizarSaque() throws RegraDeNegocioException {
        try {
            System.out.println("Digite o número da conta:");
            Integer numeroDaConta = teclado.nextInt();
            System.out.println("Digite o valor do saque:");
            BigDecimal valor = teclado.nextBigDecimal();


            service.realizarSaque(numeroDaConta, valor);
            System.out.println("Saque realizado com sucesso!");
            exibirMenu();
        }catch (NullPointerException e) {
            System.out.println("Não existe uma conta cadastrada com esse número");
            exibirMenu();
        }catch (InputMismatchException e) {
            System.out.println("Valor invalido");
            exibirMenu();
        }
    }

    private static void realizarDeposito() throws RegraDeNegocioException {
        System.out.println("Digite o número da conta:");
        Integer numeroDaConta = teclado.nextInt();


        System.out.println("Digite o valor do depósito:");
        BigDecimal valor = teclado.nextBigDecimal();


        service.realizarDeposito(numeroDaConta, valor);


        System.out.println("Depósito realizado com sucesso!");
        exibirMenu();
    }

    private static void rendimentoAno() throws RegraDeNegocioException {
        System.out.println("Digite o número da conta:");
        Integer numeroDaConta = teclado.nextInt();
        System.out.println("Sua conta irá render no final do ano o equivalente à: " + service.rendimentoAno(numeroDaConta));
        exibirMenu();
    }

    private static void listarClientes() {
        System.out.println("Clientes cadastrados:");
        Set<Cliente> clientes = service.listarClientes();
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
        exibirMenu();
    }

    private static void imprimirConta() throws IOException {
        System.out.println("Digite o número da conta que você deseja imprimir");
        Integer numero = teclado.nextInt();
        service.escreverConta(numero);
        System.out.println("Conta gravada em arquivo!");
        exibirMenu();
    }

    private static void lerConta() throws IOException {
        service.lerConta();
        exibirMenu();
    }
}
