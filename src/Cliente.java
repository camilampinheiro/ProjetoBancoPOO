import java.util.Objects;

public class Cliente extends Pessoa{

    public Cliente() {

    }
    public Cliente(String nome, String cpf, String email, int numero, boolean tipo) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.numero = numero;
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return this.cpf.hashCode();
    }

    @Override
    public String toString() {
        return "Cliente: " + "Nome = " + nome + " | CPF = " + cpf + " | Email = " + email;
    }
}




