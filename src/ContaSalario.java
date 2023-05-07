import java.time.LocalDate;
import java.util.ArrayList;

public class ContaSalario extends Conta {

    private double limiteSaque;
    private double limiteTransferencia;
    
    public ContaSalario(int numConta, double saldoAtual, LocalDate dataAbertura, LocalDate dataUltimaMov, Agencia agencia,
            boolean estadoConta, int senha, Cliente donosConta, double limiteSaque, double limiteTransferencia) {
        super(numConta, saldoAtual, dataAbertura, dataUltimaMov, agencia, estadoConta, senha, donosConta);
        this.limiteSaque = limiteSaque;
        this.limiteTransferencia = limiteTransferencia;
        historicoTransacoes = new ArrayList<>();
    }

    // sobrescrita do método de saque obedecendo limitador de saque exclusivo de contas salário
    public boolean saque(int senha, double valor, String meio) throws ValorInvalidoException, SaqueInvalidoException, 
                                                        SaldoInsuficienteException, SenhaInvalidaException, ContaInativaException {
        
        if (validadorSenha(senha) == true && isEstadoConta() == true &&
        getSaldoAtual() >= valor && valor > 0 && valor <= getLimiteSaque()) {
            System.out.println("Saldo Antes da Operação: R$" + getSaldoAtual());
            setSaldoAtual(getSaldoAtual() - valor);
            System.out.println("SAQUE REALIZADO COM SUCESSO!\nSaldo Atual: R$" + getSaldoAtual());
            dataUltimaMov = LocalDate.now();
            Transacao novaTransacao = new Transacao(dataUltimaMov, valor, meio);
            historicoTransacoes.add(novaTransacao);
            return true;
        }
        
        else if (getLimiteSaque() < valor)
            throw new SaqueInvalidoException("\nLIMITE DE SAQUE EXCEDIDO! Não é possível sacar mais que R$" + getLimiteSaque() + " de uma vez!");

        else if (saldoAtual < valor) 
            throw new SaldoInsuficienteException("\nSALDO INSUFICIENTE! Não é possível realizar a operação");

        else if (valor <= 0)
            throw new ValorInvalidoException("\nVALOR INVÁLIDO! Tente um valor maior que 0");

        else if (isEstadoConta() == false)
            throw new ContaInativaException("\nCONTA INATIVA! Não é possível realizar uma operação em uma conta inativa");
    
        return false;

    }

    // sobrescrita do método de pagamento obedecendo limitador de saque exclusivo de contas salário
    public boolean pagamento(int senha, double valor, String meio) throws TransferenciaInvalidaException, ValorInvalidoException, SaldoInsuficienteException, 
                                                        SenhaInvalidaException, ContaInativaException {

        if (validadorSenha(senha) == true && isEstadoConta() == true
        && getSaldoAtual() >= valor && valor > 0 && valor <= getLimiteTransferencia()) {
            System.out.println("\nSaldo Antes da Operação: R$" + getSaldoAtual());
            setSaldoAtual(getSaldoAtual() - valor);
            System.out.println("\nPAGAMENTO REALIZADO COM SUCESSO!\nSaldo Atual: R$" + getSaldoAtual());
            dataUltimaMov = LocalDate.now();
            Transacao novaTransacao = new Transacao(dataUltimaMov, valor, meio);
            historicoTransacoes.add(novaTransacao);
            return true;
        }
        
        else if (getLimiteTransferencia() < valor)
            throw new TransferenciaInvalidaException("\nLIMITE DE TRANSFERÊNCIA EXCEDIDO! Não é possível realizar a operação");
        
        else if (getSaldoAtual() < valor) 
            throw new SaldoInsuficienteException("\nSALDO INSUFICIENTE! Não é possível realizar a operação");
        
        else if (valor <= 0)
            throw new ValorInvalidoException("\nVALOR INVÁLIDO! Tente novamente");

        else if (isEstadoConta() == false)
            throw new ContaInativaException("\nCONTA INATIVA! Não é possível realizar uma operação em conta inativa");

        return false;

    }

    public void corCartao() {
        
        LocalDate dataAtual = LocalDate.now();

        if ((dataAtual.getYear() - dataAbertura.getYear()) < 2)
            setCorCartao("Cartao Brown Wood");
        else if ((dataAtual.getYear() - dataAbertura.getYear()) > 2 && (dataAtual.getYear() - dataAbertura.getYear()) < 5)
            setCorCartao("Cartao Silver Tape");
        else if ((dataAtual.getYear() - getDataAbertura().getYear()) >= 5)
            setCorCartao("Cartao Golden Hour");

    }

    public double getLimiteSaque() {
        return limiteSaque;
    }

    public double getLimiteTransferencia() {
        return limiteTransferencia;
    }

    @Override
    public String toString() {
        return "\nNUMERO: " + numConta + "\nDONO: " + donosConta.getNome() + "\nCPF: " + Pessoa.imprimeCPF(donosConta.getCpf())
                +"\nSALDO ATUAL: R$" + saldoAtual + "\nDATA DE ABERTURA: " + dataAbertura
                + "\nAGÊNCIA: " + agencia.getNumAg() + "\nLIMITE DE SAQUE: R$" + limiteSaque 
                + "\nLIMITE DE TRANSFERÊNCIA: R$" + limiteTransferencia + "\nCARTÃO: " + corCartao;
    }

}