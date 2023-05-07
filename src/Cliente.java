import java.time.LocalDate;

public class Cliente extends Pessoa {

    private String escolaridade;
    private Agencia agencia;

    public Cliente (String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public Cliente(String cpf, String nome, Endereco endereco, String estadoCivil, LocalDate dataNascimento,
            String escolaridade, Agencia agencia) {
        super(cpf, nome, endereco, estadoCivil, dataNascimento);
        this.escolaridade = escolaridade;
        this.agencia = agencia;
    }
    
    public Cliente(String cpf, String nome, Endereco endereco, String estadoCivil, LocalDate dataNascimento,
            String escolaridade) {
        super(cpf, nome, endereco, estadoCivil, dataNascimento);
        this.escolaridade = escolaridade;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    @Override
    public String toString() {
        return "\nCPF: " + imprimeCPF(cpf) + "\nNOME: " + nome + "\nENDEREÇO: " 
            + endereco.getEstado() + ", " + endereco.getCidade() + ", " + endereco.getBairro() + "\nESTADO CIVIL: " + estadoCivil
            + "\nDATA DE NASCIMENTO: " + dataNascimento + "\nAGÊNCIA: " + agencia.getNomeAg();
    }

}