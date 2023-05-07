import java.io.*;
import java.util.ArrayList;

public abstract class ContasCadastradas {

    ArrayList<ClienteConta> clientes;

    public static ArrayList<ClienteConta> loadContas() {

        ArrayList<ClienteConta> listaClientes = new ArrayList<>();

        try {
            FileInputStream readData = new FileInputStream("contasbancarias.ser");
            ObjectInputStream readStream = new ObjectInputStream(readData);
            listaClientes = (ArrayList<ClienteConta>) readStream.readObject();
            readStream.close();
            readData.close();
        }
        catch (IOException | ClassNotFoundException erro) {
            erro.printStackTrace();
        }

        return listaClientes;
    }

    public static void saveContas(ArrayList<ClienteConta> clientes) {
        
        try {
            FileOutputStream writeData = new FileOutputStream("contasbancarias.ser");
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
            writeStream.writeObject(clientes);
            writeStream.close();
            writeData.close();
        }
        catch (IOException erro) {
            erro.printStackTrace();
        }
    }

}
