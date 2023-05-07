public interface Transacoes {

    boolean saque(int senha, double valor, String meio) throws ValorInvalidoException, SaldoInsuficienteException, SenhaInvalidaException, ContaInativaException;
    boolean deposito(int senha, double valor, String meio) throws ValorInvalidoException, SenhaInvalidaException, ContaInativaException;
    boolean pagamento(int senha, double valor, String meio) throws ValorInvalidoException, SenhaInvalidaException, SaldoInsuficienteException, ContaInativaException;
    boolean consultaSaldo(int senha) throws SenhaInvalidaException, ContaInativaException;
    boolean extrato(int senha) throws SenhaInvalidaException, ContaInativaException;
    
}
