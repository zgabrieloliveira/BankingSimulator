import java.time.LocalDate;
import java.util.ArrayList;

public class ContaPoupanca extends Conta {

    private double rendimentoMensal;

    public ContaPoupanca(int numConta, double saldoAtual, LocalDate dataAbertura, LocalDate dataUltimaMov, Agencia agencia,
            boolean estadoConta, int senha, Cliente donosConta, double rendimentoMensal) {
        super(numConta, saldoAtual, dataAbertura, dataUltimaMov, agencia, estadoConta, senha, donosConta);
        this.rendimentoMensal = rendimentoMensal;
        historicoTransacoes = new ArrayList<>();
    }

    // sobrescrita do método de consulta para incluir porcentagem da taxa de rendimento mensal, exclusivo de contas poupança
    public boolean consultaSaldo(int senha) throws SenhaInvalidaException, ContaInativaException {

        if (validadorSenha(senha) == true && isEstadoConta() == true) {
            System.out.println("\nSALDO ATUAL:  R$" + getSaldoAtual());
            System.out.println("TAXA DE RENDIMENTO: " + getRendimentoMensal()*100 + "%");
            return true;
        }

        else if (isEstadoConta() == false)
            throw new ContaInativaException("\nCONTA INATIVA! Não é possível realizar uma operação em conta inativa");
        
        return false;

    } 

    // altera saldo de acordo com a taxa de rendimento mensal de uma conta poupança (não utilizado)
    public boolean calculoRendimento(int senha) throws SenhaInvalidaException, ContaInativaException {
        
        if (validadorSenha(senha) == true && isEstadoConta() == true) {
            setSaldoAtual(getSaldoAtual() + (getSaldoAtual()*getRendimentoMensal()));
            return true;
        }

        else if (isEstadoConta() == false)
            throw new ContaInativaException("\nCONTA INATIVA! Não é possível realizar uma operação em conta inativa");
    
        return false;

    }

    public void corCartao() {
        
        LocalDate dataAtual = LocalDate.now();

        if ((dataAtual.getYear() - getDataAbertura().getYear()) < 2)
            setCorCartao("Cartao Yellow Coldplay");
        else if ((dataAtual.getYear() - getDataAbertura().getYear()) > 2 && (dataAtual.getYear() - getDataAbertura().getYear()) < 5)
            setCorCartao("Cartao Orange Juice");
        else if ((dataAtual.getYear() - getDataAbertura().getYear()) >= 5)
            setCorCartao("Cartao Pink Panther");

    }

    public double getRendimentoMensal() {
        return rendimentoMensal;
    }

    @Override
    public String toString() {
        return "\n\nNUMERO: " + numConta + "\nDONO: " + donosConta.getNome() + "\nCPF: " + Pessoa.imprimeCPF(donosConta.getCpf())
                + "\nSALDO ATUAL: R$" + saldoAtual + "\nDATA DE ABERTURA: " + dataAbertura + "\nAGÊNCIA: " + agencia.getNumAg()
                + "\nRENDIMENTO MENSAL: " + rendimentoMensal*100 + "%" + "\nCARTÃO: " + corCartao;
    }
}