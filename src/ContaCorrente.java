import java.time.LocalDate;
import java.util.ArrayList;

public class ContaCorrente extends Conta {

    private double limiteChequeEsp;
    private double taxaAdministrativa;
    
    public ContaCorrente(int numConta, double saldoAtual, LocalDate dataAbertura, LocalDate dataUltimaMov, Agencia agencia,
            boolean estadoConta, int senha, Cliente donosConta, double limiteChequeEsp, double taxaAdministrativa) {
        super(numConta, saldoAtual, dataAbertura, dataUltimaMov, agencia, estadoConta, senha, donosConta);
        this.limiteChequeEsp = limiteChequeEsp;
        this.taxaAdministrativa = taxaAdministrativa;
        historicoTransacoes = new ArrayList<>();
    }
    
    // sobrescrita de saque com cheque especial, exclusiva de contas correntes
    public boolean saque(int senha, double valor, String meio) throws ValorInvalidoException, ChequeEspecialException, SaldoInsuficienteException, 
                                                SenhaInvalidaException, ContaInativaException {
        
        if (validadorSenha(senha) == true && isEstadoConta() == true && valor > 0 && 
        (getSaldoAtual() >= valor || getSaldoAtual() + getLimiteChequeEsp() >= valor)) {

            // se o saldo não for o suficiente mas o cheque especial cobrir, haverá um aviso
            if ((getSaldoAtual() < valor) && (getSaldoAtual() + getLimiteChequeEsp() >= valor))
                System.out.println("\nVocê está sem saldo e entrando utilizando cheque especial!\n");

            System.out.println("\nSaldo Antes da Operação: R$" + getSaldoAtual());
            setSaldoAtual(getSaldoAtual() - valor);
            System.out.println("SAQUE REALIZADO COM SUCESSO!\nSaldo Atual: R$" + getSaldoAtual());

            dataUltimaMov = LocalDate.now();
            Transacao novaTransacao = new Transacao(dataUltimaMov, valor, meio);
            historicoTransacoes.add(novaTransacao);
            return true;
        }

        else if (getSaldoAtual() + getLimiteChequeEsp() < valor)
            throw new ChequeEspecialException("\nCHEQUE ESPECIAL INSUFICIENTE! Seu cheque especial não cobre o valor da transação");

        else if (getSaldoAtual() < valor) 
            throw new SaldoInsuficienteException("\nSALDO INSUFICIENTE! Não é possível realizar a operação");

        else if (valor <= 0)
            throw new ValorInvalidoException("\nVALOR INVÁLIDO! Tente um valor maior que 0");

        else if (isEstadoConta() == false)
            throw new ContaInativaException("\nCONTA INATIVA! Não é possível realizar uma operação em conta inativa");
    
        return false;
    }

    // sobrescrita de pagamento com cheque especial, exclusiva de contas correntes
    public boolean pagamento(int senha, double valor, String meio) throws ValorInvalidoException, ChequeEspecialException, SaldoInsuficienteException, 
                                                SenhaInvalidaException, ContaInativaException {
        
        if (validadorSenha(senha) == true && isEstadoConta() == true && valor > 0 && 
        (getSaldoAtual() >= valor || getSaldoAtual() + getLimiteChequeEsp() >= valor)) {

            // se o saldo não for o suficiente mas o cheque especial cobrir, haverá um aviso
            if ((getSaldoAtual() < valor) && (getSaldoAtual() + getLimiteChequeEsp() >= valor))
                System.out.println("\nVocê está sem saldo e entrando utilizando cheque especial!");

            System.out.println("\nSaldo Antes da Operação: R$" + getSaldoAtual());
            setSaldoAtual(getSaldoAtual() - valor);
            System.out.println("PAGAMENTO REALIZADO COM SUCESSO!\nSaldo Atual: R$" + getSaldoAtual());

            dataUltimaMov = LocalDate.now();
            Transacao novaTransacao = new Transacao(dataUltimaMov, valor, meio);
            historicoTransacoes.add(novaTransacao);
            return true;
        }

        else if (getSaldoAtual() + getLimiteChequeEsp() < valor)
            throw new ChequeEspecialException("\nCHEQUE ESPECIAL INSUFICIENTE! Seu cheque especial não cobre o valor da transação");

        else if (getSaldoAtual() < valor) 
            throw new SaldoInsuficienteException("\nSALDO INSUFICIENTE! Não é possível realizar a operação");

        else if (valor <= 0)
            throw new ValorInvalidoException("\nVALOR INVÁLIDO! Tente um valor maior que 0");

        else if (isEstadoConta() == false)
            throw new ContaInativaException("\nCONTA INATIVA! Não é possível realizar uma operação em conta inativa");
    
        return false;
    }

    // método p/ alterar a cor do cartão do cliente conforme a fidelidade com a agência
    public void corCartao() {
        
        LocalDate dataAtual = LocalDate.now();

        if ((dataAtual.getYear() - getDataAbertura().getYear()) < 2)
            setCorCartao("Cartao Blue Pen");
        else if ((dataAtual.getYear() - getDataAbertura().getYear()) > 2 && (dataAtual.getYear() - getDataAbertura().getYear()) < 5)
            setCorCartao("Cartao Green Lemon");
        else if ((dataAtual.getYear() - getDataAbertura().getYear()) >= 5)
            setCorCartao("Cartao Black Mail");

    }


    public double getLimiteChequeEsp() {
        return limiteChequeEsp;
    }

    public double getTaxaAdministrativa() {
        return taxaAdministrativa;
    }

    @Override
    public String toString() {
        return "\nNUMERO: " + numConta + "\nDONO: " + donosConta.getNome() + "\nCPF: " + Pessoa.imprimeCPF(donosConta.getCpf())
            + "\nSALDO ATUAL: R$" + saldoAtual + "\nDATA DE ABERTURA: " + dataAbertura + "\nAGÊNCIA: " + agencia.getNumAg() 
            + "\nCHEQUE ESPECIAL: R$" + limiteChequeEsp + "\nTAXA ADMINISTRATIVA: R$" + taxaAdministrativa + "\nCARTÃO: " + corCartao;
    }

}