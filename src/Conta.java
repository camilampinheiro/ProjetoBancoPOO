import java.math.BigDecimal;

public abstract class Conta {

    protected Integer numero;
    protected BigDecimal saldo;
    protected Cliente cliente;
    protected boolean tipo;

    public Conta() {

    }

    public Conta(Integer numero, Cliente cliente, boolean tipo) {
        this.numero = numero;
        this.cliente = cliente;
        this.saldo = BigDecimal.ZERO;
        this.tipo = tipo;
    }

    public boolean possuiSaldo() {
        return this.saldo.compareTo(BigDecimal.ZERO) != 0;
    }

    public void sacar(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
    }

    public void depositar(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
    }

    @Override
    public boolean equals(Object obj) {
        Conta conta = (Conta) obj;
        return this.numero.equals(conta.getNumero());
    }

    @Override
    public int hashCode() {
        return this.numero;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "Número = " + numero + '\'' +
                ", Saldo = " + saldo +
                ", Titular = " + cliente +
                '}';
    }

    public Integer getNumero() {
        return numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public boolean isTipo() {
        return tipo;
    }
}



