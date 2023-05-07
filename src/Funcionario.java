import java.time.LocalDate;

public class Funcionario extends Pessoa {

    private int clt;
    private int rg;
    private char sexo;
    private String cargo;
    private double salario;
    private int anoIngresso;
    
    public Funcionario(String cpf, String nome, Endereco endereco, String estadoCivil, LocalDate dataNascimento, int clt,
            int rg, char sexo, String cargo, double salario, int anoIngresso) {
        super(cpf, nome, endereco, estadoCivil, dataNascimento);
        this.clt = clt;
        this.rg = rg;
        this.sexo = sexo;
        this.cargo = cargo;
        this.salario = salario;
        this.anoIngresso = anoIngresso;
    }

    public double calculoSalario(double salario) {
        
        LocalDate dataAtual = LocalDate.now();

        if ((dataAtual.getYear() - anoIngresso) > 15)
            salario += salario*0.10;

        return salario;

    }

    public int getClt() {
        return clt;
    }

    public int getRg() {
        return rg;
    }

    public char getSexo() {
        return sexo;
    }

    public String getCargo() {
        return cargo;
    }

    public double getSalario() {
        return salario;
    }

    public int getAnoIngresso() {
        return anoIngresso;
    }

}