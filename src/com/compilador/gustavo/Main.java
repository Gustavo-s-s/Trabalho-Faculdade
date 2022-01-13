package com.compilador.gustavo;

import com.aeroportos.gustavo.Aeroporto;
import com.cargas.gustavo.Carga;
import com.clientes.gustavo.Cliente;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;


public class Main extends Application {

    public static Set<Aeroporto> aeroportos = new HashSet<>();
    public static Set <Cliente> clientes = new HashSet<>();
    public static Set <Carga> cargas = new HashSet<>();
    public static Map<String, Aeroporto> aeroportoMap = new HashMap<>();
    public static Map<Integer, Carga> cargaMap = new HashMap<>();
    public static Map<String, Cliente> cpfMap = new HashMap<>();
    public static Map<String, Cliente> cnpjMap = new HashMap<>();

    public static Stage window;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        Parent root = FXMLLoader.load(getClass().getResource("scene1.fxml"));
        Scene cena1 = new Scene(root);
        root.setId("img");
        cena1.getStylesheets().add(getClass().getResource("Style1.css").toExternalForm());
        stage.setResizable(false);
        stage.setScene(cena1);
        stage.show();
    }

    public Stage janela = new Stage();

    public void mudaTela(int muda) throws Exception{
        Parent root = null;
        Scene scene2 = null;
        switch (muda){
            case 1 : root = FXMLLoader.load(getClass().getResource("scena2Gerente.fxml"));
                break;
            case 2 : root = FXMLLoader.load(getClass().getResource("scenaAtendente.fxml"));
                break;
        }
        scene2 = new Scene(root);
        scene2.getStylesheets().add(getClass().getResource("Style1.css").toExternalForm());
        root.setId("janela-de-opcoes");
        janela.setResizable(false);
        janela.setScene(scene2);
        janela.show();
    }


    private static void salvaDadosAeroportos() {
        try {
            FileOutputStream arquivoAeroporto = new FileOutputStream("Aeroportos.bin");
            ObjectOutputStream aeroportoObjeto = new ObjectOutputStream(arquivoAeroporto);
            aeroportoObjeto.writeObject(aeroportos);
            arquivoAeroporto = new FileOutputStream("AeroportosMap.bin");
            aeroportoObjeto = new ObjectOutputStream(arquivoAeroporto);
            aeroportoObjeto.writeObject(aeroportoMap);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void salvaDadosCargas() {
        try {
            FileOutputStream arquivoCarga = new FileOutputStream("Cargas.bin");
            ObjectOutputStream cargaObejeto = new ObjectOutputStream(arquivoCarga);
            cargaObejeto.writeObject(cargas);
            arquivoCarga = new FileOutputStream("CargasMap.bin");
            cargaObejeto = new ObjectOutputStream(arquivoCarga);
            cargaObejeto.writeObject(cargaMap);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void salvaDadosClientes() {
        try {
            FileOutputStream client = new FileOutputStream("Clientes.bin");
            ObjectOutputStream cli = new ObjectOutputStream(client);
            cli.writeObject(clientes);
            client = new FileOutputStream("ClientesCpfMap.bin");
            cli = new ObjectOutputStream(client);
            cli.writeObject(cpfMap);
            client = new FileOutputStream("ClientesCnpjMap.bin");
            cli = new ObjectOutputStream(client);
            cli.writeObject(cnpjMap);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void carregaDados() {
        try {
            File file = new File("Clientes.bin");
            FileInputStream arquivo;
            ObjectInputStream ler;
            if(file.exists() && file.length() != 0){
                arquivo = new FileInputStream(file);
                ler = new ObjectInputStream(arquivo);
                clientes = (Set<Cliente>) ler.readObject();
            }
            file = new File("Cargas.bin");
            if(file.exists() && file.length() != 0){
                arquivo = new FileInputStream(file);
                ler = new ObjectInputStream(arquivo);
                 cargas = (Set<Carga>) ler.readObject();
            }

            file = new File("Aeroportos.bin");
            if(file.exists() && file.length() != 0){
                arquivo = new FileInputStream(file);
                ler = new ObjectInputStream(arquivo);
                aeroportos = (Set<Aeroporto>)  ler.readObject();
            }

            file = new File("CargasMap.bin");
            if(file.exists() && file.length() != 0){
                arquivo = new FileInputStream(file);
                ler = new ObjectInputStream(arquivo);
                cargaMap = (Map<Integer, Carga>) ler.readObject();
            }

            file = new File("AeroportosMap.bin");
            if(file.exists() && file.length() != 0){
                arquivo = new FileInputStream(file);
                ler = new ObjectInputStream(arquivo);
                aeroportoMap = (Map<String, Aeroporto>) ler.readObject();
            }

            file = new File("ClientesCpfMap.bin");
            if(file.exists() && file.length() != 0){
                arquivo = new FileInputStream(file);
                ler = new ObjectInputStream(arquivo);
                cpfMap = (Map<String, Cliente>) ler.readObject();
            }

            file = new File("ClientesCnpjMap.bin");
            if(file.exists() && file.length() != 0){
                arquivo = new FileInputStream(file);
                ler = new ObjectInputStream(arquivo);
                cnpjMap = (Map<String, Cliente>) ler.readObject();
            }

            System.out.println("arquivos carregados com sucesso!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String []args){

        carregaDados();
        launch(args);
        salvaDadosAeroportos();
        salvaDadosCargas();
        salvaDadosClientes();
    }
}