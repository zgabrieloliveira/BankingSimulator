import java.time.LocalDate;

public class Gerente extends Funcionario {

    private LocalDate dataIngressoGerencia;
    private Agencia agencia;
    private boolean cursoGerencia;
    private static double comissao;
    
    public Gerente(String cpf, String nome, Endereco endereco, String estadoCivil, LocalDate dataNascimento, int clt,
            int rg, char sexo, String cargo, double salario, int anoIngresso, LocalDate dataIngressoGerencia, boolean cursoGerencia, double comissao) {
        super(cpf, nome, endereco, estadoCivil, dataNascimento, clt, rg, sexo, cargo, salario, anoIngresso);
        this.dataIngressoGerencia = dataIngressoGerencia;
        this.cursoGerencia = cursoGerencia;
        Gerente.comissao = comissao; // atributo estático, não usa this
    }

    public double salarioGerente(Gerente gerente, double salario) {
        salario += gerente.calculoSalario(salario) + getComissao(); 
        return salario;
    }

    public LocalDate getDataIngressoGerencia() {
        return dataIngressoGerencia;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public boolean isCursoGerencia() {
        return cursoGerencia;
    }

    public static double getComissao() {
        return comissao;
    }
    
    public static void setComissao(double comissao) {
        Gerente.comissao = comissao;
    }

    @Override
    public String toString() {
        return "Gerente [dataIngressoGerencia=" + dataIngressoGerencia + ", agencia=" + agencia + ", cursoGerencia="
                + cursoGerencia + "]";
    }
    
}