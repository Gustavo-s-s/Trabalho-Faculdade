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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class ControladorAtendente {
    Main main;
    Set<Carga> cargas = Main.cargas;
    Set<Cliente> clientes = Main.clientes;
    Set<Aeroporto> aeroportos = Main.aeroportos;
    Alert num;
    Map<Integer, Carga> cargaMap = Main.cargaMap;
    Map<String, Cliente> cpfMap = Main.cpfMap;
    Map<String, Cliente> cnpjMap = Main.cnpjMap;
    TextArea area;
    Stage window;

    public ControladorAtendente() {
        num = new Alert(Alert.AlertType.INFORMATION);
        window = new Stage();
        area = new TextArea();
        main = new Main();
    }

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
    private TextField textVerificadorDeCodigo;
    @FXML
    private RadioButton trasportar;
    @FXML
    private RadioButton cancelar;
    @FXML
    private RadioButton entregar;


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
    void consultaCargas() {
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
        window.show();
    }

    @FXML
    void alterarSitiuacaoCarga(ActionEvent event) {
        if (!verificaNumerosInteiros(textVerificadorDeCodigo.getText())) {
            num.setHeaderText("Formato de código inválido");
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

        Aeroporto origem = null;
        Aeroporto destino = null;
        Carga carga = null;
        Cliente cliente = null;

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
        num.setHeaderText("carga cadastrada com sucesso");
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
        lista.forEach(item -> box.getItems().add(item));
    }

    private void converteLista(ComboBox box, Set<Aeroporto> aeros, String paisDeComparacao) {
        aeros =  aeros.stream().filter(item -> item.getPais().equalsIgnoreCase(paisDeComparacao)).collect(Collectors.toSet());
        aeros.forEach(item -> box.getItems().add(item));
    }

    @FXML
    public void confirmaCadastroCliente(ActionEvent actionEvent) {
        boolean allFilled = false;

        if(allFilled == false) {
            allFilled = naoEstaEmBranco(nome, "campo obrigatorio!");
            allFilled = naoEstaEmBranco(end, "campo obrigatorio!") && allFilled;
            allFilled = naoEstaEmBranco(email, "campo obrigatorio!") && allFilled;
            allFilled = naoEstaEmBranco(identificador, "campo obrigatorio!") && allFilled;
        }
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

    private boolean naoEstaEmBranco(TextField textField, String msg) {
        if (textField.getText().isBlank()) {
            textField.setPromptText(msg);
            return false;
        }
        return true;
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
    void sairSistema(ActionEvent event) {
       ControladorScena1.main.janela.close();
    }
}

