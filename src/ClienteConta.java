import java.io.Serializable;

public class ClienteConta implements Serializable {

    private Conta conta;
    private Cliente cliente;
    
    public ClienteConta() {
    }

    public ClienteConta(Conta conta, Cliente cliente) {
        this.conta = conta;
        this.cliente = cliente;
    }

    public Conta getConta() {
        return conta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Override
    public String toString() {
        return "ClienteConta [conta=" + conta.toString() + ", cliente=" + cliente.toString() + "]";
    }
    
}