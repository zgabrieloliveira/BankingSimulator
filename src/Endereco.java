import java.io.Serializable;

public class Endereco implements Serializable {

    private String cidade;
    private String bairro;
    private String estado;
    
    public Endereco(String cidade, String bairro, String estado) {
        this.cidade = cidade;
        this.bairro = bairro;
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Endereco [cidade=" + cidade + ", bairro=" + bairro + ", estado=" + estado + "]";
    }
    
}