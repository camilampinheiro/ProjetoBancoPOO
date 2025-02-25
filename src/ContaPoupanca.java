import java.math.BigDecimal;
public class ContaPoupanca extends Conta{

    public ContaPoupanca(Integer numero, Cliente cliente, boolean tipo) {
        this.numero = numero;
        this.cliente = cliente;
        this.saldo = BigDecimal.ZERO;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Conta Poupança:" + " Número = " + numero + " | Saldo = " + saldo + " " + cliente + " | Tipo = " + "Poupança";
    }
}
