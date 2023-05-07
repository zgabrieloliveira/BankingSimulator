import java.io.*;
import java.util.ArrayList;

public abstract class ClientesCadastrados {

    ArrayList<Cliente> clientes;

    public static ArrayList<Cliente> loadClientes() {

        ArrayList<Cliente> listaClientes = new ArrayList<>();

        try {
            FileInputStream readData = new FileInputStream("clientes.ser");
            ObjectInputStream readStream = new ObjectInputStream(readData);
            listaClientes = (ArrayList<Cliente>) readStream.readObject();
            readStream.close();
            readData.close();
        }
        catch (IOException | ClassNotFoundException erro) {
            erro.printStackTrace();
        }

        return listaClientes;
    }

    public static void saveClientes(ArrayList<Cliente> clientes) {

        try {
            FileOutputStream writeData = new FileOutputStream("clientes.ser");
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
