package ui;

import core.Carta;
import data.GerenciadorDeDados;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        //Carregar CSV
        GerenciadorDeDados ger = new GerenciadorDeDados();
        //Caminho dos Arquivos
        List<Carta> cartasCadastradas = ger.lerCartasDaPasta("src/main/resources/data");

        TabPane tabPane = new TabPane();

        //Mostrar Lista de Cartas
        ListView<String> listaCartas = new ListView<>();
        List<String> nomesCartas = cartasCadastradas.stream()
                .map(Carta::getNome)
                .collect(Collectors.toList());
        listaCartas.getItems().addAll(nomesCartas);

        Tab colecaoTab = new Tab("Colecao", listaCartas);
        colecaoTab.setClosable(false);

        //Cadastro
        VBox cadastroLayout = new VBox(10);
        cadastroLayout.setPadding(new javafx.geometry.Insets(10));
        cadastroLayout.getChildren().add(new Label("Cadastro de Cartas"));
        cadastroLayout.getChildren().add(new Separator());

        cadastroLayout.getChildren().add(new Label("Nome da Carta"));
        cadastroLayout.getChildren().add(new TextField());
        cadastroLayout.getChildren().add(new Label("Nível:"));
        cadastroLayout.getChildren().add(new TextField());
        cadastroLayout.getChildren().add(new Button("Salvar Cadastro"));

        Tab cartasTab = new Tab("Cartas", cadastroLayout);
        cartasTab.setClosable(false);

        //Decks
        VBox decksLayout = new VBox(10, new Label("Cadastro de Decks"));
        decksLayout.setPadding(new javafx.geometry.Insets(10));
        Tab decksTab = new Tab("Decks", decksLayout);
        decksTab.setClosable(false);

        Scene scene = new Scene(tabPane, 400, 600);
        stage.setTitle("Clash Cards");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);}
}
