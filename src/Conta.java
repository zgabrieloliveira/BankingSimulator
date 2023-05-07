import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Conta implements Transacoes, Serializable {

    protected int numConta;
    protected double saldoAtual;
    protected LocalDate dataAbertura;
    protected LocalDate dataUltimaMov;
    protected Agencia agencia;
    protected boolean estadoConta;
    protected int senha;
    protected Cliente donosConta;
    protected String corCartao;
    protected ArrayList<Transacao> historicoTransacoes;

    public Conta(int numConta, double saldoAtual, LocalDate dataAbertura, LocalDate dataUltimaMov, Agencia agencia,
            boolean estadoConta, int senha, Cliente donosConta) {
        this.numConta = numConta;
        this.saldoAtual = saldoAtual;
        this.dataAbertura = dataAbertura;
        this.dataUltimaMov = dataUltimaMov;
        this.agencia = agencia;
        this.estadoConta = estadoConta;
        this.senha = senha;
        this.donosConta = donosConta;
        historicoTransacoes = new ArrayList<>();
    }

    public boolean consultaSaldo(int senha) throws SenhaInvalidaException, ContaInativaException {

        if (validadorSenha(senha) == true && isEstadoConta() == true) {
            System.out.println("\nSALDO ATUAL:  R$" + getSaldoAtual());
            return true;
        }

        else if (isEstadoConta() == false)
            throw new ContaInativaException("\nCONTA INATIVA! Não é possível realizar uma operação em conta inativa");
        
        return false;

    }

    public boolean deposito(int senha, double valor, String meio) throws ValorInvalidoException, SenhaInvalidaException, ContaInativaException {

        if (validadorSenha(senha) == true && isEstadoConta() == true && valor > 0) {
            System.out.println("\nSaldo Antes da Operação: R$" + getSaldoAtual());
            setSaldoAtual(getSaldoAtual() + valor);
            System.out.println("DEPOSITO REALIZADO COM SUCESSO!\nSaldo Atual: R$" + getSaldoAtual());
            dataUltimaMov = LocalDate.now();
            Transacao novaTransacao = new Transacao(dataUltimaMov, valor, meio);
            historicoTransacoes.add(novaTransacao);
            return true;
        }

        else if (valor <= 0)
            throw new ValorInvalidoException("VALOR INVÁLIDO! Tente um valor maior que 0");

        else if (isEstadoConta() == false)
            throw new ContaInativaException("CONTA INATIVA! Não é possível realizar uma operação em uma conta inativa");
        
        return false;

    }

    public boolean saque(int senha, double valor, String meio) throws ValorInvalidoException, SaldoInsuficienteException, 
                                                        SenhaInvalidaException, ContaInativaException {
        
        if (validadorSenha(senha) == true && isEstadoConta() == true && getSaldoAtual() >= valor && valor > 0) {
            System.out.println("\nSaldo Antes da Operação: R$" + getSaldoAtual());
            setSaldoAtual(getSaldoAtual() - valor);
            System.out.println("SAQUE REALIZADO COM SUCESSO!\nSaldo Atual: R$" + getSaldoAtual());
            dataUltimaMov = LocalDate.now();
            Transacao novaTransacao = new Transacao(dataUltimaMov, valor, meio);
            historicoTransacoes.add(novaTransacao);
            return true;
        }

        else if (saldoAtual < valor) 
            throw new SaldoInsuficienteException("SALDO INSUFICIENTE! Não é possível realizar a operação");

        else if (valor <= 0)
            throw new ValorInvalidoException("VALOR INVÁLIDO! Tente um valor maior 0");

        else if (isEstadoConta() == false)
            throw new ContaInativaException("CONTA INATIVA! Não é possível realizar uma operação em uma conta inativa");
    
        return false;
    }

    public boolean pagamento(int senha, double valor, String meio) throws ValorInvalidoException, SaldoInsuficienteException, 
                                                                    SenhaInvalidaException, ContaInativaException {
                                                                    
        if (validadorSenha(senha) == true && isEstadoConta() == true && valor > 0) {
            System.out.println("\nSaldo Antes da Operação: R$" + getSaldoAtual());
            setSaldoAtual(getSaldoAtual() - valor);
            System.out.println("PAGAMENTO REALIZADO COM SUCESSO!\nSaldo Atual: R$" + getSaldoAtual());
            dataUltimaMov = LocalDate.now();
            Transacao novaTransacao = new Transacao(dataUltimaMov, valor, meio);
            historicoTransacoes.add(novaTransacao);
            return true;
        }
        
        else if (saldoAtual < valor) 
            throw new SaldoInsuficienteException("\nSALDO INSUFICIENTE! Não é possível realizar a operação");

        else if (valor <= 0)
            throw new ValorInvalidoException("\nVALOR INVÁLIDO! Tente um valor maior que 0");

        else if (isEstadoConta() == false)
            throw new ContaInativaException("\nCONTA INATIVA! Não é possível realizar uma operação em conta inativa");

        return false;

    }

    public boolean extrato(int senha) throws SenhaInvalidaException, ContaInativaException {

        if (validadorSenha(senha) == true && isEstadoConta() == true) {
            for (Transacao t : historicoTransacoes) {
                System.out.println(t);
            }

            return true;
                
        }

        else if (isEstadoConta() == false)
            throw new ContaInativaException("\nCONTA INATIVA! Não é possível realizar uma operação em conta inativa");

        return false;

    }

    public boolean validadorSenha(int senha) throws SenhaInvalidaException {

        if (this.senha == senha)
            return true;
        else
            throw new SenhaInvalidaException("\nSENHA INVÁLIDA! Tente novamente com a senha correta");

    }

    public abstract void corCartao(); // método abstrato (requisito inventado)

    public ArrayList<Transacao> getHistoricoTransacoes() {
        return historicoTransacoes;
    }

    public void setCorCartao(String corCartao) {
        this.corCartao = corCartao;
    }

    public String getCorCartao() {
        return corCartao;
    }
    
    public int getNumConta() {
        return numConta;
    }

    public double getSaldoAtual() {
        return saldoAtual;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public LocalDate getDataUltimaMov() {
        return dataUltimaMov;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public boolean isEstadoConta() {
        return estadoConta;
    }

    public int getSenha() {
        return senha;
    }

    public void setSaldoAtual(double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public Cliente getDonosConta() {
        return donosConta;
    }

}   