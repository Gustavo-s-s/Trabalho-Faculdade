package com.controlador.gustavo;

import com.aeroportos.gustavo.Aeroporto;
import com.cargas.gustavo.Carga;
import com.cargas.gustavo.CargaInternacional;
import com.cargas.gustavo.CargaNacional;
import com.clientes.gustavo.Cliente;
import com.clientes.gustavo.PessoaFisica;
import com.clientes.gustavo.PessoaJuridica;
import com.compilador.gustavo.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ControladorGerente {
    Set<Carga> cargas = Main.cargas;
    Set<Cliente> clientes = Main.clientes;
    Set<Aeroporto> aeroportos = Main.aeroportos;
    Map<String, Aeroporto> aeroportoMap = Main.aeroportoMap;
    Alert num;
    Map<Integer, Carga> cargaMap = Main.cargaMap;
    Map<String, Cliente> cpfMap = Main.cpfMap;
    Map<String, Cliente> cnpjMap = Main.cnpjMap;
    Stage window;
    TextArea area;

    public ControladorGerente() {
        num = new Alert(Alert.AlertType.INFORMATION);
        window = new Stage();
        area = new TextArea();
        area.setEditable(false);
        window.setResizable(false);
    }

    @FXML
    private TextField textVerificadorDeCodigo;
    @FXML
    private RadioButton select;
    @FXML
    private TextField nome;
    @FXML
    private TextField email;
    @FXML
    private TextField end;
    @FXML
    private TextField textNomeFantasia;
    @FXML
    private TextField identificador;
    @FXML
    private RadioButton idCnpj;
    @FXML
    private TextField entradaDados;
    @FXML
    private TextField codCarga;
    @FXML
    private TextField altCarga;
    @FXML
    private TextField largCarga;
    @FXML
    private TextField profCarga;
    @FXML
    private TextField pesoCarga;
    @FXML
    private ComboBox<?> selectOrigem;
    @FXML
    private ComboBox<?> selectDestino;
    @FXML
    private TextField textTaxa;
    @FXML
    private RadioButton trasportar;
    @FXML
    private RadioButton cancelar;
    @FXML
    private RadioButton entregar;
    @FXML
    private TextField codIata;
    @FXML
    private TextField NomeAero;
    @FXML
    private TextField paisAero;
    @FXML
    private TextField latiAero;
    @FXML
    private TextField longAero;

    private boolean verificaNumeros(String aux) {
        double i = 0;
        try {
            i = Double.parseDouble(aux);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean verificaNumerosInteiros(String aux) {
        int i = 0;
        try {
            i = Integer.parseInt(aux);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @FXML
    public void confirmaCadastroAeroporto(ActionEvent event) {

        if (!verificaNumeros(latiAero.getText())) {
            num.setHeaderText("Latitide Incorreta");
            num.show();
            return;
        }

        if (!verificaNumeros(longAero.getText())) {
            num.setHeaderText("longitude Incorreta");
            num.show();
            return;
        }
        boolean allFilled = false;

        allFilled = naoEstaEmBranco(codIata, "obrigatorio!");
        allFilled = naoEstaEmBranco(paisAero, "obrigatorio!") && allFilled;
        allFilled = naoEstaEmBranco(NomeAero, "obrigatorio!") && allFilled;
        allFilled = naoEstaEmBranco(latiAero, "obrigatorio!") && allFilled;
        allFilled = naoEstaEmBranco(longAero, "obrigatorio!") && allFilled;

        if (!allFilled) {
            return;
        }
        if (aeroportoMap.containsKey(codIata.getText())) {
            num.setHeaderText("Codigo IATA existente");
            num.show();
            return;
        }

        if (aeroportos != null) {
            String nome = NomeAero.getText();
            String codigoIATA = codIata.getText();
            String pais = paisAero.getText();
            double lat = Double.parseDouble(latiAero.getText());
            double longi = Double.parseDouble(longAero.getText());

            Aeroporto aero = new Aeroporto(codigoIATA, nome, pais, lat, longi);
            aeroportos.add(aero);

            aeroportoMap.put(codigoIATA, aero);

            num.setHeaderText("Aeroporto Cadastrado com Sucesso");
            num.show();
        }
    }

    @FXML
    void alterarSitiuacaoCarga(ActionEvent event) {
        if (!verificaNumerosInteiros(textVerificadorDeCodigo.getText())) {
            num.setHeaderText("Código inválido");
            num.show();
            return;
        }
        int codigo = Integer.parseInt(textVerificadorDeCodigo.getText());

        if (!cargaMap.containsKey(codigo)) {
            num.setHeaderText("Código inexistente");
            num.show();
            return;
        }

        if (!entregar.isSelected() && !cancelar.isSelected() && !trasportar.isSelected()) {
            num.setHeaderText("Selecione uma opção");
            num.show();
            return;
        }

        Carga carga = cargaMap.get(codigo);

        if (entregar.isSelected()) {
            if (!carga.verificaSituacaoCargaParaEntrega()) {
                num.setHeaderText("Carga ainda está Pendente ou cancelada");
                num.show();
                return;
            } else {
                cargaMap.get(codigo).verificaSituacaoCargaParaEntrega();
                num.setHeaderText(carga.toString());
                num.show();
            }

        } else if (cancelar.isSelected()) {
            if (!carga.verificaSituacaoCargaParaCancelar()) {
                num.setHeaderText("Carga em tranporte ou entrege");
                num.show();
                return;

            } else {
                cargaMap.get(codigo).verificaSituacaoCargaParaCancelar();
                num.setHeaderText(carga.toString());
                num.show();
            }

        } else if (trasportar.isSelected()) {
            if (!carga.verificaSituacaoCargaParaTrasportar()) {
                num.setHeaderText("Carga cancelada ou entregue");
                num.show();
                return;
            } else {
                cargaMap.get(codigo).verificaSituacaoCargaParaTrasportar();
                num.setHeaderText(carga.toString());
                num.show();
            }
        }
    }

    @FXML
    void verificaRadioButton(ActionEvent event) {
        if (event.getSource().equals(cancelar)) {
            entregar.setDisable(!entregar.isDisable());
            trasportar.setDisable(!trasportar.isDisable());
        } else if (event.getSource().equals(entregar)) {
            cancelar.setDisable(!cancelar.isDisable());
            trasportar.setDisable(!trasportar.isDisable());
        } else if (event.getSource().equals(trasportar)) {
            cancelar.setDisable(!cancelar.isDisable());
            entregar.setDisable(!entregar.isDisable());
        }
    }

    @FXML
    void cadastrarCarga(ActionEvent event) {
        Aeroporto origem = null;
        Aeroporto destino = null;
        Carga carga = null;
        Cliente cliente = null;

        boolean allFilled = false;

        allFilled = naoEstaEmBranco(altCarga, "obrigatorio!");
        allFilled = naoEstaEmBranco(profCarga, "obrigatorio!") && allFilled;
        allFilled = naoEstaEmBranco(largCarga, "obrigatorio!") && allFilled;
        allFilled = naoEstaEmBranco(pesoCarga, "obrigatorio!") && allFilled;
        allFilled = naoEstaEmBranco(textTaxa, "obrigatorio!") && allFilled;
        allFilled = naoEstaEmBranco(entradaDados, "obrigatório") && allFilled;
        allFilled = naoEstaEmBranco(codCarga, "obrigatorio!")  && allFilled;

        if (!allFilled) {
            return;
        }

        if (!verificaNumeros(textTaxa.getText())) {
            num.setHeaderText("taxa inválida");
            num.show();
            return;
        }

        if (selectOrigem.getValue() == null || selectDestino.getValue() == null) {
            num.setHeaderText("origem ou destino não selecionado");
            num.show();
            return;
        }
        if (!verificaNumerosInteiros(codCarga.getText())) {
            num.setHeaderText("Codigo invalido");
            num.show();
            return;
        }
        if (!verificaNumeros(altCarga.getText()) ||
                !verificaNumeros(largCarga.getText()) ||
                !verificaNumeros(profCarga.getText()) ||
                !verificaNumeros(pesoCarga.getText())) {
            num.setHeaderText("Informações da carga invalida");
            num.show();
            return;
        }

        origem = (Aeroporto) selectOrigem.getValue();
        destino = (Aeroporto) selectDestino.getValue();
        int codigo = Integer.parseInt(codCarga.getText());

        if (cargaMap.containsKey(codigo)) {
            num.setHeaderText("codigo de carga já existente");
            num.show();
            return;
        }

        if (origem.equals(destino)) {
            num.setHeaderText("Destino igual a origem!");
            num.show();
            return;
        }


        double altura = Double.parseDouble(altCarga.getText());
        double profundidade = Double.parseDouble(profCarga.getText());
        double largura = Double.parseDouble(largCarga.getText());
        double peso = Double.parseDouble(pesoCarga.getText());
        double taxa = Double.parseDouble(textTaxa.getText());

        if (idCnpj.isSelected()) {
            if (!cnpjMap.containsKey(entradaDados.getText())) {
                num.setHeaderText("Cnpj não encontrado");
                num.show();
                return;
            }
            cliente = cnpjMap.get(entradaDados.getText());

        } else {
            if (!cpfMap.containsKey(entradaDados.getText())) {
                num.setHeaderText("CPF não encontrado");
                num.show();
                return;
            }
            cliente = cpfMap.get(entradaDados.getText());
        }

        carga = (origem.getPais().equalsIgnoreCase("brasil")) ?
                new CargaNacional(codigo, altura, largura, profundidade, peso, cliente, taxa, origem, destino) :
                new CargaInternacional(codigo, altura, largura, profundidade, peso, cliente, taxa, origem, destino);

        cargas.add(carga);
        cargaMap.put(codigo, carga);
        num.setHeaderText("Carga cadastrada com sucesso");
        num.show();

    }

    @FXML
    void atualizaCadastroDeCarga(MouseEvent event) {
        selectOrigem.getItems().clear();
        selectDestino.getItems().clear();
        converteLista(selectOrigem, aeroportos);
        converteLista(selectDestino, aeroportos, "brasil");
    }

    private void converteLista(ComboBox box, Collection lista) {
        lista.forEach(item -> {
            box.getItems().add(item);
        });
    }

    private void converteLista(ComboBox box, Set<Aeroporto> aeros, String paisDeComparacao) {
        aeros =  aeros.stream().
                filter(item -> item.getPais().equalsIgnoreCase(paisDeComparacao)).
                collect(Collectors.toSet());

        aeros.forEach(item -> box.getItems().add(item));
    }

    @FXML
    public void confirmaCadastroCliente(ActionEvent actionEvent) {
        boolean allFilled = false;

        allFilled = naoEstaEmBranco(nome, "campo obrigatorio!");
        allFilled =  naoEstaEmBranco(end, "campo obrigatorio!") && allFilled;
        allFilled =  naoEstaEmBranco(email, "campo obrigatorio!") && allFilled;
        allFilled =  naoEstaEmBranco(identificador, "campo obrigatorio!") && allFilled;

        if (select.isSelected()) {
            allFilled = naoEstaEmBranco(textNomeFantasia, "campo obrigatorio!") && allFilled;
        }

        if (!allFilled) {
            return;
        }

        Cliente cliente;
        String nomeString = nome.getText();
        String enderecoStrnig = end.getText();
        String emailString = email.getText();
        String identificadorString = identificador.getText();

        if (select.isSelected()) {
            if (cnpjMap.containsKey(identificadorString)) {
                num.setHeaderText("cnpj já existente");
                num.show();
                return;
            }

            String nomeFantasia = textNomeFantasia.getText();
            cliente = new PessoaJuridica(nomeString, emailString, enderecoStrnig, identificadorString, nomeFantasia);
            cnpjMap.put(identificadorString, cliente);
        } else {
            if (cpfMap.containsKey(identificadorString)) {
                num.setHeaderText("cpf já existente");
                num.show();
                return;
            }

            cliente = new PessoaFisica(nomeString, emailString, enderecoStrnig, identificadorString);
            cpfMap.put(identificadorString, cliente);
        }

        clientes.add(cliente);

        num.setHeaderText("cliente cadastrado com sucesso!");
        num.show();
    }

    @FXML
    public void displayNomeFantasia(ActionEvent actionEvent) {
        if (select.isSelected()) {
            identificador.setPromptText("CNPJ");
        } else {
            identificador.setPromptText("CPF");
        }
        textNomeFantasia.setDisable(!textNomeFantasia.isDisable());
        textNomeFantasia.setVisible(!textNomeFantasia.isVisible());
    }

    @FXML
    void selectCadastroClienteCarga(ActionEvent event) {
        if (idCnpj.isSelected()) {
            entradaDados.setPromptText("CNPJ");
        } else {
            entradaDados.setPromptText("CPF");
        }
    }

    @FXML
    void consultaCargas(ActionEvent event) {
        if (window.isShowing()) {
            window.close();
        }

        AnchorPane pane = new AnchorPane(area);
        AnchorPane.setBottomAnchor(area, 0.0);
        AnchorPane.setLeftAnchor(area, 0.0);
        AnchorPane.setRightAnchor(area, 0.0);
        AnchorPane.setTopAnchor(area, 0.0);
        Scene cena = new Scene(pane, 600, 400);

        area.clear();

        cargas.forEach(carga -> {
            area.appendText(carga.toString());
            area.appendText("\n");
        });

        window.setScene(cena);
        window.setTitle("Consulta Cargas");
        window.show();
    }

    @FXML
    public void consultaAeroportos(ActionEvent event) {
        if (window.isShowing()) {
            window.close();
        }
        AnchorPane pane = new AnchorPane(area);
        AnchorPane.setBottomAnchor(area, 0.0);
        AnchorPane.setLeftAnchor(area, 0.0);
        AnchorPane.setRightAnchor(area, 0.0);
        AnchorPane.setTopAnchor(area, 0.0);
        Scene cena = new Scene(pane, 600, 400);

        area.clear();

        aeroportos.forEach(aeroporto -> {
            area.appendText(aeroporto.toStringPesquisaTrace());
            area.appendText("\n");
        });

        window.setScene(cena);
        window.setTitle("Consulta Aeroportos");
        window.show();
    }


    @FXML
    public void consultaClientesCadastrados(ActionEvent event) {
        if (window.isShowing()) {
            window.close();
        }

        AnchorPane pane = new AnchorPane(area);
        AnchorPane.setBottomAnchor(area, 0.0);
        AnchorPane.setLeftAnchor(area, 0.0);
        AnchorPane.setRightAnchor(area, 0.0);
        AnchorPane.setTopAnchor(area, 0.0);
        Scene cena = new Scene(pane, 600, 400);

        area.clear();

        clientes.forEach(c -> {
            area.appendText(c.toString() + "\n");
            area.appendText("\n");
        });


        window.setScene(cena);
        window.setTitle("Consulta Clientes");
        window.show();
    }

    @FXML
    public void traceEXE(ActionEvent event) {
        if (window.isShowing()) {
            window.close();
        }

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        TextField field = new TextField();
        Label label = new Label("Carga de dados");
        Button button = new Button("Comfirmar");

        AnchorPane pane = new AnchorPane(textArea, field, label, button);

        resize(pane, label, 14, 368, 258, 258);
        resize(pane, textArea, 90, 20, 20, 20);
        resize(pane, field, 42, 332, 240, 240);
        resize(pane, button, 28, 336, 118, 409);



        button.setOnAction(action -> {
            Stack<Object> saco = new Stack<>();

            textArea.clear();

            boolean isfill = naoEstaEmBranco(field, "campo em branco!");

            if (!isfill) {
                return;
            }
            File arquivo = new File(field.getText());
            if (!arquivo.exists()) {
                num.setHeaderText("arquivo não encontrado!");
                num.show();
                return;
            }
            try {
                Scanner read;
                try{
                    read = new Scanner(arquivo).useLocale(new Locale("pt", "BR"));
                } catch (Exception e) {
                    System.out.println("encontado arquivo em inglês");
                    read = new Scanner(arquivo);
                }

                if (field.getText().endsWith("-clientes.dat")) {
                    String linha = "";

                    while (read.hasNextLine()) {
                        linha = read.nextLine();
                        Scanner separetor = new Scanner(linha).useDelimiter(";");
                        Cliente cliente;

                        int tipo = separetor.nextInt();
                        String nome = separetor.next();
                        String email = separetor.next();
                        String endeco = separetor.next();
                        String idetificador = separetor.next();

                        if (tipo == 1) {
                            cliente = new PessoaFisica(nome, email, endeco, idetificador);
                        } else {
                            String razao = "";
                            try{
                                razao =  separetor.next();
                            } catch(Exception e) {
                                num.setHeaderText("está faltando razão social no documento");
                                num.show();
                                return;
                            }
                            cliente = new PessoaJuridica(nome, email, endeco, idetificador, razao);
                        }

                        saco.push(cliente);
                    }

                } else if (field.getText().endsWith("-aeroportos.dat")) {
                    String linha = "";

                    while (read.hasNextLine()) {
                        linha = read.nextLine();
                        Scanner separetor = new Scanner(linha).useDelimiter(";");
                        String codigoIATA = separetor.next();
                        String nome = separetor.next();
                        String pais = separetor.next();
                        double lat = Double.parseDouble(separetor.next());
                        double log = Double.parseDouble(separetor.next());

                        Aeroporto aero = new Aeroporto(codigoIATA, nome, pais, lat, log);
                        saco.push(aero);
                    }
                } else if (field.getText().endsWith("-cargas.dat")) {
                    String linha = "";

                    while (read.hasNextLine()) {
                        linha = read.nextLine();
                        Scanner separetor = new Scanner(linha).useDelimiter(";");
                        int tipoCarga = separetor.nextInt();
                        int codigoCarga = separetor.nextInt();
                        int tipoCliente = separetor.nextInt();
                        String identificador = separetor.next();
                        double altura = Double.parseDouble(separetor.next());
                        double largura = Double.parseDouble(separetor.next());
                        double profundiade = Double.parseDouble(separetor.next());
                        double peso = Double.parseDouble(separetor.next());
                        String sitiuacao = separetor.next();
                        double taxa = Double.parseDouble(separetor.next());
                        String codigoIATAOrigem = separetor.next();
                        String codigoIATADestino = separetor.next();

                        Carga carga = null;
                        Cliente cliente = null;

                        if (tipoCliente == 1) {
                                cliente = cpfMap.get(identificador);
                        } else {
                                cliente = cnpjMap.get(identificador);
                        }

                        Aeroporto origem = aeroportoMap.get(codigoIATAOrigem);
                        Aeroporto destino = aeroportoMap.get(codigoIATADestino);

                        if (tipoCarga == 1) {
                            carga = new CargaNacional(codigoCarga, altura, largura, profundiade, peso, cliente, taxa, origem, destino);
                        } else {
                            carga = new CargaInternacional(codigoCarga, altura, largura, profundiade, peso, cliente, taxa, origem, destino);
                        }

                        if (sitiuacao.equals("Em transporte")) {
                            carga.verificaSituacaoCargaParaTrasportar();
                        } else if (sitiuacao.equals("cancelada")) {
                            carga.verificaSituacaoCargaParaCancelar();
                        } else if (sitiuacao.equals("entregue")) {
                            carga.verificaSituacaoCargaParaTrasportar();
                            carga.verificaSituacaoCargaParaEntrega();
                        }
                        saco.push(carga);
                    }

                } else {
                    num.setHeaderText("Arquivo não compativel");
                    num.show();
                    return;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            int tamanho = saco.size();
            for (int i = 0; i < tamanho; i++) {
                Object item = saco.pop();
                if (item instanceof Cliente) {
                    Cliente cliente = (Cliente) item;

                    boolean existe = (cliente instanceof PessoaFisica) ? cpfMap.containsKey(((PessoaFisica) cliente).getCpf()) :
                            cnpjMap.containsKey(((PessoaJuridica) cliente).getCnpj());
                    if (existe) {
                        textArea.appendText("cliente existente com esse cpf ou cnpj\n");
                        continue;
                    }

                    textArea.appendText(cliente.toString());
                    clientes.add(cliente);

                    if (cliente instanceof PessoaFisica) {
                        cpfMap.put(((PessoaFisica) cliente).getCpf(), cliente);
                    } else {
                        cnpjMap.put(((PessoaJuridica) cliente).getCnpj(), cliente);
                    }

                } else if (item instanceof Aeroporto) {
                    Aeroporto aeroporto = (Aeroporto) item;
                    if (aeroportoMap.containsKey(aeroporto.getCodigoIATA())) {
                        textArea.appendText("aeroporto já existente com esse codigo IATA\n");
                        continue;
                    }

                    textArea.appendText(aeroporto.toStringPesquisaTrace());
                    aeroportos.add(aeroporto);
                    aeroportoMap.put(aeroporto.getCodigoIATA(), aeroporto);
                } else {
                    Carga carga = (Carga) item;
                    carga = (carga instanceof CargaNacional) ? (CargaNacional) carga : (CargaInternacional) carga;

                    if(carga.getCliente() == null || carga.getOrigem() == null || carga.getDestino() == null) {
                        textArea.appendText("impossível ler carga\n");
                        continue;
                    }
                    if (cargaMap.containsKey(carga.getCodigo())) {
                        textArea.appendText("carga com o código já existente\n");
                        continue;
                    }
                    textArea.appendText(carga.toString());
                    cargas.add(carga);
                    cargaMap.put(carga.getCodigo(), carga);
                    primeiraCarga(carga.getCliente());
                }
            }
        });

        Scene scene = new Scene(pane);
        try {
            scene.getStylesheets().add(getClass().getResource("Style2.css").toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }
        window.setScene(scene);
        window.setTitle("Carregamentos de dados");
        window.show();
    }

    private void resize(AnchorPane pane, Node node, double arg1, double arg2, double arg3, double arg4) {
        pane.setTopAnchor(node, arg1);
        pane.setBottomAnchor(node, arg2);
        pane.setRightAnchor(node, arg3);
        pane.setLeftAnchor(node, arg4);
    }

    private boolean naoEstaEmBranco(TextField textField, String msg) {
        if (textField.getText().isBlank()) {
            textField.setPromptText(msg);
            return false;
        }
        return true;

    }
    @FXML
    private void clearText(MouseEvent mouseClick) {
        TextField field = (TextField) mouseClick.getSource();
        field.clear();
    }

    private boolean primeiraCarga(Cliente cliente) {
        ArrayList<Carga> cargass = (ArrayList<Carga>) cargas.stream().
                filter(carga -> carga.getCliente().equals(cliente)).
                collect(Collectors.toList());

        if (cargass.size() == 1) {
            cargass.get(0).verificaSituacaoCargaParaTrasportar();
            return true;
        }
        return false;
    }

    @FXML
    private void limparSistema(ActionEvent event) {
        clientes.clear();
        aeroportos.clear();
        cargas.clear();
        cnpjMap.clear();
        cpfMap.clear();
        aeroportoMap.clear();
        cargaMap.clear();
    }

    @FXML
    void sairSistema(ActionEvent event) {
        ControladorScena1.main.janela.close();
    }

}

