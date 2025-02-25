import java.math.BigDecimal;

public class ContaCorrente extends Conta {
    public ContaCorrente(Integer numero, Cliente cliente, boolean tipo) {
        this.numero = numero;
        this.cliente = cliente;
        this.saldo = BigDecimal.ZERO;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Conta Corrente:" + " Número = " + numero + " | Saldo = " + saldo + " " + cliente + " | Tipo = " + "Corrente";
    }
}
