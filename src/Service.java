import java.io.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Service {


    private Set<Conta> contas = new HashSet<>();
    private Set<Cliente> clientes = new HashSet<>();


    public Set<Conta> listarContasAbertas() {
        if (contas.isEmpty()) {
            System.out.println("Não há contas cadastradas!");
        }
        return contas;
    }


    public BigDecimal consultarSaldo(Integer numeroDaConta) {
        Conta conta = buscarContaPorNumero(numeroDaConta);
        return conta.getSaldo();
    }
    public void abrir(Conta dadosDaConta) throws RegraDeNegocioException{
        if(!dadosDaConta.isTipo()) {
            Cliente cliente = getCliente(dadosDaConta);
            Conta conta = new ContaCorrente(dadosDaConta.getNumero(), dadosDaConta.getCliente(), dadosDaConta.isTipo());//Não precisamos passar  saldo pois ja instanciamos ele como BigDecimal.ZERO ou seja, ele ja é criado como 0
            if (contas.contains(conta)) {
                throw new RegraDeNegocioException("Já existe outra conta aberta com o mesmo número");
            } else {
                contas.add(conta);
            }
            clientes.add(cliente);
        }else {
            Cliente cliente = getCliente(dadosDaConta);
            Conta conta = new ContaPoupanca(dadosDaConta.getNumero(), dadosDaConta.getCliente(), dadosDaConta.isTipo());//Não precisamos passar  saldo pois ja instanciamos ele como BigDecimal.ZERO ou seja, ele ja é criado como 0
            if (contas.contains(conta)) {
                throw new RegraDeNegocioException("Já existe outra conta aberta com o mesmo número");
            } else {
                contas.add(conta);
            }
            clientes.add(cliente);
        }
    }


    private Cliente getCliente(Conta dadosDaConta) throws RegraDeNegocioException {
        Cliente cliente;
        if(!dadosDaConta.getCliente().isTipo()) {
            cliente = new ClienteEspecial(dadosDaConta.getCliente().getNome(), dadosDaConta.getCliente().getCpf(),
                    dadosDaConta.getCliente().getEmail(), dadosDaConta.getCliente().getNumero(), dadosDaConta.isTipo());
        }
        else {
            cliente = new ClienteNormal(dadosDaConta.getCliente().getNome(), dadosDaConta.getCliente().getCpf(),
                    dadosDaConta.getCliente().getEmail(), dadosDaConta.getCliente().getNumero(), dadosDaConta.isTipo());
        }
        if(clientes.contains(cliente)) {
            throw new RegraDeNegocioException("Já existe um cliente cadastrado com esse cpf");
        }
        return cliente;
    }


    public void realizarSaque(Integer numeroDaConta, BigDecimal valor) throws RegraDeNegocioException{
        Conta conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do saque deve ser superior a zero");
        }


        if (valor.compareTo(conta.getSaldo()) > 0) {
            throw new RegraDeNegocioException("Saldo insuficiente!");
        }


        conta.sacar(valor);
    }


    public void realizarDeposito(Integer numeroDaConta, BigDecimal valor) throws RegraDeNegocioException{
        Conta conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do deposito deve ser superior a zero");
        }
        if(conta.getCliente() instanceof ClienteEspecial) {
            BigDecimal mul = new BigDecimal("0.1");
            conta.depositar(valor);
            conta.depositar(valor.multiply(mul));
            System.out.println("Você recebeu um acrescimo de 10% no depósito por ser um cliente especial");
        }else {
            conta.depositar(valor);
        }
    }


    public void encerrar(Integer numeroDaConta) throws RegraDeNegocioException{
        Conta conta = buscarContaPorNumero(numeroDaConta);
        Cliente cliente = buscarClientePorNumero(numeroDaConta);
        if (conta.possuiSaldo()) {
            throw new RegraDeNegocioException("Conta não pode ser encerrada pois ainda possui saldo");
        }


        contas.remove(conta);
        clientes.remove(cliente);
    }


    public BigDecimal rendimentoAno(Integer numeroDaConta) throws RegraDeNegocioException {
        Conta conta = buscarContaPorNumero(numeroDaConta);
        if (!conta.possuiSaldo()) {
            throw new RegraDeNegocioException("Conta não possui saldo");
        }else if (conta instanceof ContaPoupanca){
            BigDecimal mul = new BigDecimal("0.15");
            return conta.getSaldo().multiply(mul);
        }else {
            throw new RegraDeNegocioException("Sua conta não é conta poupança, portanto, ela não irá render ao final do ano");
        }
    }


    public void escreverConta (Integer numeroDaConta) throws IOException {
        File arquivo = new File( "\\C:\\Users\\alvaro.bezerra\\Desktop\\conta.txt\\" );
        if(!arquivo.exists()) {
            arquivo.createNewFile();
        }
        FileWriter fw = new FileWriter( arquivo );
        BufferedWriter bw = new BufferedWriter(fw );
        bw.write("" + buscarContaPorNumero(numeroDaConta));
        bw.close();
        fw.close();
    }
    public void lerConta () throws IOException {
        File arquivo = new File("\\C:\\Users\\alvaro.bezerra\\Desktop\\conta.txt\\");
        if(!arquivo.exists()) {
            arquivo.createNewFile();
        }
        FileReader fr = new FileReader(arquivo);
        BufferedReader br = new BufferedReader(fr);
        while(br.ready()) {
            String linha = br.readLine();
            System.out.println(linha);
        }
        br.close();
        fr.close();
    }


    public Set<Cliente> listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados");
        }
        return clientes;
    }


    public Conta buscarContaPorNumero(Integer numero)  {
        for (Conta conta : contas) {
            if (Objects.equals(conta.numero, numero)) {
                return conta;
            }
        }
        return null;
    }


    public Cliente buscarClientePorNumero(Integer numero) {
        for (Cliente cliente : clientes) {
            if (cliente.getNumero() == numero) {
                return cliente;
            }
        }
        return null;
    }
}
