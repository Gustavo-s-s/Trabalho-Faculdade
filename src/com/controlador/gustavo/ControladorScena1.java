package com.controlador.gustavo;

import com.compilador.gustavo.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ControladorScena1 {
   static public Main main;


    public ControladorScena1(){
        main = new Main();
    }

    @FXML
    void menuAtendente(ActionEvent event) {
        try {
            main.mudaTela(2);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void menuGerente(ActionEvent event) {
        try {
            main.mudaTela(1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void sairSistema(ActionEvent event) {
       Main.window.close();
    }

}
