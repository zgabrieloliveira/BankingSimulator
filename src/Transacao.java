import java.time.LocalDate;
import java.io.Serializable;

public class Transacao implements Serializable {

    protected ContaCorrente conta;
    protected LocalDate dataTransacao;
    protected double valorTransacao;
    protected String meioTransacao;

    public Transacao(ContaCorrente conta, LocalDate dataTransacao) {
        this.conta = conta;
        this.dataTransacao = dataTransacao;
    }

    public Transacao(LocalDate dataTransacao, double valorTransacao, String meioTransacao) {
        this.dataTransacao = dataTransacao;
        this.valorTransacao = valorTransacao;
        this.meioTransacao = meioTransacao;
    }

    public LocalDate getDataTransacao() {
        return dataTransacao;
    }

    public double getValorTransacao() {
        return valorTransacao;
    }

    public String getMeioTransacao() {
        return meioTransacao;
    }

    @Override
    public String toString() {
        return "*TRANSAÇÕES*\n" + "\nDATA: " + dataTransacao
            + "\nVALOR DA TRANSAÇÃO: " + valorTransacao + "\nMEIO DA TRANSAÇÃO: " + meioTransacao + "\n";
    }
    
}
