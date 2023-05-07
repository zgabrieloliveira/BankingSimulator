import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        menu();
    }

    // menu da aplicação
    public static void menu() {

        ArrayList<Cliente> clientes = ClientesCadastrados.loadClientes();
        ArrayList<ClienteConta> clientesCadastrados = ContasCadastradas.loadContas();

        Scanner sc = new Scanner(System.in);
        int opcao=0;

        while (opcao != 5) {

            System.out.println("---------------------------------------------");
            System.out.println("\n\tBEM-VINDO AO POLEBANK!\n");
            System.out.println("1. Cadastro de Cliente");
            System.out.println("2. Cadastro de Conta");
            System.out.println("3. Operações Bancárias");
            System.out.println("4. Conhecer Agências");
            System.out.println("5. Sair\n");
            System.out.println("---------------------------------------------\n");
            System.out.println("O que deseja fazer?\n");
            opcao = sc.nextInt();

            while (opcao<1 || opcao>5) {
                System.out.println("Digite uma opção válida!");
                opcao = sc.nextInt();
            }
            
            switch(opcao) {
                
                // CADASTRO DE CLIENTE
                case 1:  {
                    Cliente cadastro = cadastroCliente();
                    System.out.println(cadastro);
                    sleep();
                    System.out.println("\n<<Cadastro de Cliente Concluído!>>\n");
                    sleep();
                    
                    clientes.add(cadastro); // adicionando cadastro ao ArrayList de Clientes
                    break;
                }
                
                // CADASTRO DE CONTA
                case 2: {
                    System.out.println("\nO(a) dono(a) da conta já foi cadastrado(a) em alguma agência?");
                    System.out.println("\n1. Sim\t2. Não\n");
                    int confirmacaoCadastro = sc.nextInt();
                    Cliente clienteCadastrado = null;
                    Conta contaCadastrada = null;
                    
                    // opções inválidas
                    while (confirmacaoCadastro != 1 && confirmacaoCadastro != 2) {
                        System.out.println("Digite somente 1 ou 2!");
                        confirmacaoCadastro = sc.nextInt();
                    }
    
                    // em caso de cadastro prévio, ocorrerá identificação para prosseguimento da criação de conta
                    if (confirmacaoCadastro == 1) {

                        contaCadastrada = cadastroConta(clientes);

                        // conta não pôde ser criada
                        if (contaCadastrada == null) {
                            System.out.println("\n<<Não foi possível criar a conta!>>\n");
                            break;
                        }
                        else
                            clienteCadastrado = contaCadastrada.getDonosConta(); // associando conta ao cliente

                    }
                    
                    // caso o cliente não tenha sido cadastrado, primeiro ocorrerá a sessão de cadastro de cliente, depois da conta
                    else if (confirmacaoCadastro == 2) {
                        clienteCadastrado = cadastroCliente();
                        clientes.add(clienteCadastrado); // adicionando cadastro ao arraylist de clientes
                        contaCadastrada = cadastroConta(clientes);
                    }   
    
                    ClienteConta relacaoCC = new ClienteConta(contaCadastrada, clienteCadastrado);
                    contaCadastrada.corCartao(); // (requisito exclusivo): método que altera a cor do cartão do cliente de acordo com a fidelidade
                    System.out.println();
                    System.out.println("DADOS DA CONTA:\n");
                    System.out.println(contaCadastrada); // mostrando dados da conta
                    sleep();
                    System.out.println("\n<< Cadastro de Conta Concluído!>> \n");
                    clientesCadastrados.add(relacaoCC);
                    sleep();
                    break;
                }

                // TRANSAÇÕES/OPERAÇÕES BANCÁRIAS
                case 3: {

                    if (operacoesBancarias(clientesCadastrados) == true)
                        System.out.println("\n<< Operação Concluída! >>\n");
                        
                    else 
                        System.out.println("\n<< Não foi possível realizar a operação! >>\n");

                    sleep();
                    break;
                }
                
                // SESSÃO PARA ACESSAR OS DADOS DA EMPRESA
                case 4: {
                    printAgencias();
                    sleep();
                    sleep();
                    break;
                }
                
                // SAÍDA DO MENU
                case 5: {
                    System.out.println("\n<< Salvando Dados... >>\n");
                    ClientesCadastrados.saveClientes(clientes); // salvando arraylist de clientes em arquivo com método estático
                    ContasCadastradas.saveContas(clientesCadastrados); // salvando arraylist de clientes-conta em arquivo com método estático
                    sleep();
                    break;
                }
            
            }        
        }
    }

    // método p/ carregar agências pré-definidas no sistema
    public static Agencia[] loadAgencias() {

        // instanciando agencias
        LocalDate dataNascGerentes[] = new LocalDate[3];
        dataNascGerentes[0] = LocalDate.of(1980, 12, 25);
        dataNascGerentes[1] = LocalDate.of(1977, 04, 11);
        dataNascGerentes[2] = LocalDate.of(1970, 11, 22);

        LocalDate dataIngressoGerencia[] = new LocalDate[3];
        dataIngressoGerencia[0] = LocalDate.of(2018, 12, 25);
        dataIngressoGerencia[1] = LocalDate.of(2018, 02, 01);
        dataIngressoGerencia[2] = LocalDate.of(2011, 10, 28);

        Endereco[] enderecos = new Endereco[3];
        enderecos[0] = new Endereco("Uberlandia", "Sta. Monica", "Minas Gerais");
        enderecos[1] = new Endereco("São Paulo", "Liberdade", "São Paulo");
        enderecos[2] = new Endereco("Rio de Janeiro", "Copacabana", "Rio de Janeiro");

        Gerente[] gerentes = new Gerente[3];
        gerentes[0] = new Gerente("29243001524", "Arnaldo da Silva Montes", enderecos[0], "Casado", 
        dataNascGerentes[0], 1125, 16547987, 'M', "Gerente", 
        12500.00, 2015, dataIngressoGerencia[0], true, 1250.00);

        gerentes[1] = new Gerente("02315484388", "Marta de Castro Prestes", enderecos[1], "Solteira", 
        dataNascGerentes[1], 1456, 22765901, 'F', "Gerente", 
        15000.00, 2012, dataIngressoGerencia[1], true, 1500.00);

        gerentes[2] = new Gerente("82354303490", "Jorge Henrique de Sá", enderecos[2], "Casado",
        dataNascGerentes[2], 0325, 57421758, 'M', "Gerente", 18000, 2006, dataIngressoGerencia[2], false, 1800.00);

        Agencia[] agencias = new Agencia[3];
        agencias[0] = new Agencia(0001, "PoleBank Minas Gerais", enderecos[0], gerentes[0]);
        agencias[1] = new Agencia(0002, "PoleBank São Paulo", enderecos[1], gerentes[1]);
        agencias[2] = new Agencia(0003, "PoleBank Rio de Janeiro", enderecos[2], gerentes[2]); 

        return agencias;

    }

    // método p/ cadastrar clientes no sistema
    public static Cliente cadastroCliente() {

        Cliente cadastro=null;
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\t*CADASTRO DE CLIENTES*\n");

        System.out.println("Digite o cpf do cliente: ");
        String cpf = sc.nextLine();

        while (Pessoa.validadorCPF(cpf) == false) {
            System.out.println("\nCPF Inválido! Por favor, digite novamente\n");
            cpf = sc.nextLine();
        }

        System.out.println();
        System.out.println("Digite o nome do cliente: "); 
        String nome = sc.nextLine();
        System.out.println();
        System.out.println("Digite o ESTADO do endereco do cliente: ");
        String estado = sc.nextLine();
        System.out.println();
        System.out.println("Digite a CIDADE do endereco do cliente: ");
        String cidade = sc.nextLine();
        System.out.println();
        System.out.println("Digite o BAIRRO do endereco do cliente: ");
        String bairro = sc.nextLine();
        System.out.println();
        Endereco enderecoCliente = new Endereco(cidade, bairro, estado);
        System.out.println("Digite o estado civil do cliente (Solteiro ou Casado): ");
        String estadoCivil = sc.nextLine();
        System.out.println();
        System.out.println("Digite a DIA da data de nascimento do cliente: ");
        int diaNasc = sc.nextInt();
        System.out.println();
        System.out.println("Digite o MÊS da data de nascimento do cliente: ");
        int mesNasc = sc.nextInt();
        System.out.println();
        System.out.println("Digite o ANO da data de nascimento do cliente: ");
        int anoNasc = sc.nextInt();
        LocalDate dataNascCliente = LocalDate.of(anoNasc, mesNasc, diaNasc);
        System.out.println();
        System.out.println("Digite a escolaridade do cliente (Ensino Medio, Ensino Fundamental, Ensino Superior):");
        String escolaridade = sc.nextLine();
        clearBuffer(sc);
        System.out.println();

        Agencia[] agencias = loadAgencias();

        System.out.println("Digite o numero Agencia da conta a ser cadastrada: ");
        System.out.println("\n1. PoleBank Minas Gerais\t2. PoleBank São Paulo\t3. PoleBank Rio de Janeiro\n");
        int agenciaCliente = sc.nextInt();
        System.out.println();

        switch(agenciaCliente) {
            case 1: {
                cadastro = new Cliente(cpf, nome, enderecoCliente, estadoCivil, dataNascCliente, escolaridade, agencias[0]);
                break;
            }
            case 2: {
                cadastro = new Cliente(cpf, nome, enderecoCliente, estadoCivil, dataNascCliente, escolaridade, agencias[1]);
                break;
            }
            case 3: {
                cadastro = new Cliente(cpf, nome, enderecoCliente, estadoCivil, dataNascCliente, escolaridade, agencias[2]);
                break;
            }

            default: {
                while (agenciaCliente != 1 && agenciaCliente != 2 && agenciaCliente != 3) {
                    System.out.println("\nNumero Inválido! Escolha uma das três agencias\n");
                    System.out.println("\n1. PoleBank Minas Gerais\t2. PoleBank São Paulo\t3. PoleBank Rio de Janeiro\n");
                    agenciaCliente = sc.nextInt();
                    break;
                }
            }
        }
        return cadastro;
    }

    // método p/ cadastrar contas no sistema
    public static Conta cadastroConta(ArrayList<Cliente> clientes) {

        Conta cadastro = null;
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\t*CADASTRO DE CONTAS*\n");

        // solicita identificação para prosseguimento de cadastro
        System.out.println("Digite o CPF do cliente:");
        String cpf = sc.nextLine();
        System.out.println();

        while (Pessoa.validadorCPF(cpf) == false) {
            System.out.println("\nCPF Inválido! Por favor, digite novamente");
            cpf = sc.nextLine();         
        }

        Cliente clienteCadastrado = null;

        // buscando cliente na base de clientes através do cpf
        for (Cliente c : clientes) {
            if (cpf.equals(c.getCpf()))
                clienteCadastrado = c; // cliente encontrado
        }

        if (clienteCadastrado == null) {
            System.out.println("\nCliente não encontrado na base da dados! Por favor, cadastre-se antes\n");
            sleep();
            return null;
        }

        System.out.println("Olá, " + clienteCadastrado.getNome() + "!\n");
        System.out.println("Qual tipo de conta será aberta?");
        System.out.println("\n1. Conta Corrente\t2. Conta Poupança\t3. Conta Salário");
        int contaEscolhida = sc.nextInt();
        System.out.println();
    
        while (contaEscolhida != 1 && contaEscolhida != 2 && contaEscolhida != 3) {
            System.out.println("Digite um numero válido (1, 2 ou 3)");
            contaEscolhida = sc.nextInt();
        }
        
        Random numero = new Random();
        int numConta = 1 + numero.nextInt(9999);
        System.out.println("Escolha a SENHA DE NUMEROS da sua conta:");
        int senha = sc.nextInt();
        System.out.println();
    
        switch(contaEscolhida) {
        
            case 1: {
                System.out.println("Você está abrindo uma CONTA CORRENTE\n");
                cadastro = new ContaCorrente(numConta, 0.00, LocalDate.now(), null, clienteCadastrado.getAgencia(),
                true, senha, clienteCadastrado, 1000, 100);
                break;
            }
            
            case 2: {
                System.out.println("Você está abrindo uma CONTA POUPANÇA\n");
                cadastro = new ContaPoupanca(numConta, 0.00, LocalDate.now(), null, clienteCadastrado.getAgencia(),
                true, senha, clienteCadastrado, 0.005);
                break;
            }

            case 3: {
                System.out.println("Você está abrindo uma CONTA SALÁRIO");
                cadastro = new ContaSalario(numConta, 0.00, LocalDate.now(), null, clienteCadastrado.getAgencia(),
                true, senha, clienteCadastrado, 3000.00, 5000.00);
                break;
            }
        }
        return cadastro;
    }

    // método p/ verificar meio da transação
    public static String validaMeio(int opcaoTransacao) {

        String meioTransacao=null;

        switch(opcaoTransacao) {
            case 1: meioTransacao = "Internet Banking";
                break;
            case 2: meioTransacao = "Caixa Eletrônico";
                break;
            case 3: meioTransacao = "Caixa Físico";
                break;
        }

        return meioTransacao;
        
    }
    
    // método p/ realizar operações bancárias (consulta de saldo, depósito, saque, pagamento e extrato)
    public static boolean operacoesBancarias(ArrayList<ClienteConta> clientesCadastrados) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n\t*OPERAÇÕES BANCÁRIAS*");

        System.out.println("\nPor favor, digite seu CPF para encontrarmos a sua conta na base de dados:");
        String cpf = sc.nextLine();
        if (Pessoa.validadorCPF(cpf) == false) {
            while (Pessoa.validadorCPF(cpf) == false) {
                System.out.println("\nCPF Inválido! Por favor, digite novamente");
                cpf = sc.nextLine();
            }
        }
        System.out.println();

        ClienteConta clienteProcurado = null;

        // buscando cliente na base de clientes através do cpf
        for (ClienteConta c : clientesCadastrados) {

            if (cpf.equals(c.getCliente().getCpf())) {
                clienteProcurado = c; // cliente encontrado
            }
        }

        if (clienteProcurado == null) {
            System.out.println("\nCliente não encontrado na base da dados! Por favor, cadastre-se antes\n");
            sleep();
            return false;
        }

        System.out.println("Olá, " + clienteProcurado.getCliente().getNome() + "!");
        System.out.println("\nDigite o número da operação que deseja fazer:");
        System.out.println("\n1. Consultar Saldo\t2. Saques\t3. Depósitos\t4. Pagamentos\t5. Extrato");
        int operacao = sc.nextInt();
        System.out.println();

        while (operacao<1 || operacao>5) {
            System.out.println("\nNÚMERO INVÁLIDO! Digite somente 1, 2, 3, 4 ou 5");
            sleep();
            operacao = sc.nextInt();
            System.out.println();
        }

        // Selecionando Operação Bancária
        switch(operacao) {

            // CONSULTA SALDO
            case 1: {

                try {
                    System.out.println("Digite a sua senha para realizar a operação");
                    int senha = sc.nextInt();
                    clienteProcurado.getConta().validadorSenha(senha);
                    System.out.println("Qual o meio da operação?");
                    System.out.println("\n1. Internet Banking\t2. Caixa Eletrônico\t3. Caixa Físico");
                    int opcaoTransacao = sc.nextInt();
                    
                    while (opcaoTransacao != 1 && opcaoTransacao != 2 && opcaoTransacao != 3) {
                        System.out.println("\n*Escolha um número válido (1, 2, 3)*");
                        System.out.println("\n1. Internet Banking\t2. Caixa Eletrônico\t3. Caixa Físico");
                        opcaoTransacao = sc.nextInt();
                    }

                    boolean confirma = clienteProcurado.getConta().consultaSaldo(senha);
                    return confirma;
                        
                }
                // se a senha estiver errada, a operação não pode ser realizada
                catch (SenhaInvalidaException senhaIncorreta) {
                    System.out.println(senhaIncorreta.getMessage());
                    sleep();
                    return false;
                }
                // não é possível realizar operações em contas inativas
                catch (ContaInativaException contaDesabilitada) {
                    System.out.println(contaDesabilitada.getMessage());
                    sleep();
                    return false;
                }

            }

            // SAQUES
            case 2: {

                try {
                    System.out.println("Digite a sua senha para realizar a operação");
                    int senha = sc.nextInt();
                    clienteProcurado.getConta().validadorSenha(senha);
                    System.out.println("Qual o meio da transferência?");
                    System.out.println("\n1. Internet Banking\t2. Caixa Eletrônico\t3. Caixa Físico");
                    int opcaoTransacao = sc.nextInt();
                    
                    while (opcaoTransacao != 1 && opcaoTransacao != 2 && opcaoTransacao != 3) {
                        System.out.println("\n*Escolha um número válido (1, 2, 3)*");
                        System.out.println("\n1. Internet Banking\t2. Caixa Eletrônico\t3. Caixa Físico");
                        opcaoTransacao = sc.nextInt();
                    }

                    // o meio é selecionado conforme a escolha do usuário
                    String meioTransacao = validaMeio(opcaoTransacao);

                    System.out.println("\nQual valor deseja sacar?");
                    double valor = sc.nextDouble();
                    System.out.println();

                    boolean confirma = clienteProcurado.getConta().saque(senha, valor, meioTransacao);
                    return confirma;
                        
                }
                // não é possível sacar sem saldo suficiente
                catch (SaldoInsuficienteException semSaldo) {
                    System.out.println(semSaldo.getMessage());
                    sleep();
                    return false;
                }
                // se a senha estiver errada, a operação não pode ser realizada
                catch (SenhaInvalidaException senhaIncorreta) {
                    System.out.println(senhaIncorreta.getMessage());
                    sleep();
                    return false;
                }
                // não é possível realizar operações em contas inativas
                catch (ContaInativaException contaDesabilitada) {
                    System.out.println(contaDesabilitada.getMessage());
                    sleep();
                    return false;
                }
                // não é possível realizar a operação para valores inválidos
                catch (ValorInvalidoException valorInvalido) {
                    System.out.println(valorInvalido.getMessage());
                    sleep();
                    return false;
                }
                // cheque especial não cobre a operação, então não é possível realizá-la
                catch (ChequeEspecialException chequeNaoCobre) {
                    System.out.println(chequeNaoCobre.getMessage());
                    sleep();
                    return false;
                }
                // limite do saque foi excedido, não é possível realizar a operação
                catch (SaqueInvalidoException limiteExcedido) {
                    System.out.println(limiteExcedido.getMessage());
                    sleep();
                    return false;
                }   
            }

            // DEPÓSITOS
            case 3: {

                try {
                    System.out.println("Digite a sua senha para realizar a operação");
                    int senha = sc.nextInt();
                    clienteProcurado.getConta().validadorSenha(senha);
                    System.out.println("\nQual o meio da transferência?");
                    System.out.println("\n1. Internet Banking\t2. Caixa Eletrônico\t3. Caixa Físico");

                    int opcaoTransacao = sc.nextInt();
                    
                    while (opcaoTransacao != 1 && opcaoTransacao != 2 && opcaoTransacao != 3) {
                        System.out.println("\n*Escolha um número válido (1, 2, 3)*");
                        System.out.println("\n1. Internet Banking\t2. Caixa Eletrônico\t3. Caixa Físico");
                        opcaoTransacao = sc.nextInt();
                    }
                    // o meio é selecionado de acordo com a escolha do usuário
                    String meioTransacao = validaMeio(opcaoTransacao);

                    System.out.println("\nQual valor deseja depositar?");
                    double valor = sc.nextDouble();
                    
                    boolean confirma = clienteProcurado.getConta().deposito(senha, valor, meioTransacao);       
                    return confirma; 
                        
                }
                // se a senha estiver errada, a operação não pode ser realizada
                catch (SenhaInvalidaException senhaIncorreta) {
                    System.out.println(senhaIncorreta.getMessage());
                    sleep();
                    return false;
                }
                // não é possível realizar operações em contas inativas
                catch (ContaInativaException contaDesabilitada) {
                    System.out.println(contaDesabilitada.getMessage());
                    sleep();
                    return false;
                }
                // não é possível realizar a operação para valores inválidos
                catch (ValorInvalidoException valorInvalido) {
                    System.out.println(valorInvalido.getMessage());
                    sleep();
                    return false;
                }

            }

            // PAGAMENTOS
            case 4: {

                try {
                    System.out.println("Digite a sua senha para realizar a operação");
                    int senha = sc.nextInt();
                    clienteProcurado.getConta().validadorSenha(senha);
                    System.out.println("\nQual o meio da transferência?");  
                    System.out.println("\n1. Internet Banking\t2. Caixa Eletrônico\t3. Caixa Físico");
                    int opcaoTransacao = sc.nextInt();
                    
                    while (opcaoTransacao != 1 && opcaoTransacao != 2 && opcaoTransacao != 3) {
                        System.out.println("\n*Escolha um número válido (1, 2, 3)*");
                        System.out.println("\n1. Internet Banking\t2. Caixa Eletrônico\t3. Caixa Físico");
                        opcaoTransacao = sc.nextInt();
                    }
                    // o meio é selecionado de acordo com a escolha do usuário
                    String meioTransacao = validaMeio(opcaoTransacao);

                    System.out.println("\nQual valor do pagamento?");
                    double valor = sc.nextDouble();

                    // realizando operação através do método da classe Conta
                    boolean confirma = clienteProcurado.getConta().pagamento(senha, valor, meioTransacao);
                    return confirma;
                                
                }
                // não é possível sacar sem saldo suficiente
                catch (SaldoInsuficienteException semSaldo) {
                    System.out.println(semSaldo.getMessage());
                    sleep();
                    return false;
                }
                // se a senha estiver errada, a operação não pode ser realizada
                catch (SenhaInvalidaException senhaIncorreta) {
                    System.out.println(senhaIncorreta.getMessage());
                    sleep(); 
                    return false;
                }
                // não é possível realizar operações em contas inativas
                catch (ContaInativaException contaDesabilitada) {
                    contaDesabilitada.getMessage();
                    sleep();
                    return false;
                }
                // não é possível realizar a operação para valores inválidos
                catch (ValorInvalidoException valorInvalido) {
                    System.out.println(valorInvalido.getMessage());
                    sleep();
                    return false;
                }
                // cheque especial não cobre a operação, então não é possível realizá-la
                catch (ChequeEspecialException chequeNaoCobre) {
                    System.out.println(chequeNaoCobre.getMessage());
                    sleep();
                    return false;
                }
                // limite do transferência foi excedido, não foi possível realizar a operação
                catch (TransferenciaInvalidaException limiteExcedido) {
                    System.out.println(limiteExcedido.getMessage());
                    sleep();
                    return false;
                }

            }

            // EXTRATOS
            case 5: {

                try {
                    System.out.println("Digite a sua senha para realizar a operação");
                    int senha = sc.nextInt();
                    clienteProcurado.getConta().validadorSenha(senha);
                    System.out.println("\nQual o meio da operação?");
                    System.out.println("\n1. Internet Banking\t2. Caixa Eletrônico\t3. Caixa Físico");
                    int opcaoTransacao = sc.nextInt();
                    
                    while (opcaoTransacao != 1 && opcaoTransacao != 2 && opcaoTransacao != 3) {
                        System.out.println("\n*Escolha um número válido (1, 2, 3)*");
                        System.out.println("\n1. Internet Banking\t2. Caixa Eletrônico\t3. Caixa Físico");
                        opcaoTransacao = sc.nextInt();
                    }

                    // realizando operação através do método da classe Conta
                    boolean confirma = clienteProcurado.getConta().extrato(senha);
                    return confirma;
        
                }
                // se a senha estiver errada, a operação não pode ser realizada
                catch (SenhaInvalidaException senhaIncorreta) {
                    System.out.println(senhaIncorreta.getMessage());
                    sleep();
                    return false;
                }
                // não é possível realizar operações em contas inativas
                catch (ContaInativaException contaDesabilitada) {
                    System.out.println(contaDesabilitada.getMessage());
                    sleep();
                    return false;
                }

            }

        }

        return false; 
    }

    // método p/ printar as informações de cada agência, de acordo com o usuário
    public static void printAgencias() {

        Scanner sc = new Scanner(System.in);
        Agencia[] agencias = loadAgencias();

        System.out.println("\nPoleBank é uma empresa que tem como principal compromisso o consumidor.");
        System.out.println("Temos agências espalhadas pelas principais regiões do sudeste do Brasil:\n");
        System.out.println("1. PoleBank Minas Gerais\t2. PoleBank São Paulo\t3. PoleBank Rio de Janeiro\n");
        System.out.println("Deseja ver os dados de qual agência?");
        int agencia = sc.nextInt();

        while (agencia < 1 || agencia >3) {
            System.out.println("Escolha um número válido (1, 2 ou 3)");
            agencia = sc.nextInt();
        }

        switch(agencia) {

            case 1: System.out.println(agencias[0]);
                break;

            case 2: System.out.println(agencias[1]);
                break;

            case 3: System.out.println(agencias[2]);
                break;

        }

    }

    // método p/ solucionar problemas na leitura de dados
    private static void clearBuffer(Scanner sc) {
        if (sc.hasNextLine())
            sc.nextLine();
    }

    // método p/ desacelerar o prompt
    private static void sleep() {
        try { 
            Thread.sleep (1000); 
        } 
        catch (InterruptedException ex) {
            ex.getMessage();
        }
    }

}