package ui;

import core.Carta;
import data.GerenciadorDeDados;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.IllegalFormatException;
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
        tabPane.getTabs().addAll(colecaoTab);

        //Cadastro

        final TextField nomeCarta = new TextField();
        final ChoiceBox<String> tipoChoice = new ChoiceBox<>();
        final TextField nivelCarta = new TextField();
        final TextField custoElixir = new TextField();
        final ChoiceBox<String> raridadeChoice = new ChoiceBox<>();
        final ChoiceBox<String> velocidadeChoice = new ChoiceBox<>();
        final TextField dano =  new TextField();
        final TextField danoPorSegundo = new TextField();
        final TextField pontosVida = new TextField();
        final ChoiceBox<String> alvosChoice = new ChoiceBox<>();
        final TextField alcance = new TextField();
        final TextField velocidadeImpacto = new TextField();

        final Button salvarButton = new Button("Salvar Cadastro");
        final String caminhoPasta = "src/main/resources/data";
        final Path caminhoDoArquivo = java.nio.file.Paths.get(caminhoPasta, "novasCartas.csv");

        salvarButton.setOnAction(e -> {
                    try {
                        core.Carta novaCarta = new Carta();
                        novaCarta.setNome(nomeCarta.getText().trim());
                        novaCarta.setTipo(core.TipoCarta.valueOf(tipoChoice.getValue()));
                        novaCarta.setNivel(Integer.parseInt(nivelCarta.getText().trim()));
                        novaCarta.setcustoElixir(Integer.parseInt(custoElixir.getText().trim()));
                        novaCarta.setRaridade(core.Raridade.valueOf(raridadeChoice.getValue()));
                        novaCarta.setVelocidade(core.Velocidade.valueOf(velocidadeChoice.getValue()));
                        novaCarta.setDano(Integer.parseInt(dano.getText().trim()));
                        novaCarta.setDanoPorSegundo(Integer.parseInt(danoPorSegundo.getText().trim()));
                        novaCarta.setPontosVida(Integer.parseInt(pontosVida.getText().trim()));
                        novaCarta.setAlvos(core.Alvos.valueOf(alvosChoice.getValue()));
                        novaCarta.setAlcance(Double.parseDouble(alcance.getText().trim()));
                        novaCarta.setVelocidadeImpacto(Double.parseDouble(velocidadeImpacto.getText().trim()));

                        cartasCadastradas.add(novaCarta);


                        ger.salvarCartasEmArquivo(cartasCadastradas, caminhoDoArquivo);

                        listaCartas.getItems().add(novaCarta.getNome());
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Carta '" + novaCarta.getNome() + "' salva com sucesso!");
                        alert.showAndWait();

                        nomeCarta.clear();
                        nivelCarta.clear();
                        custoElixir.clear();
                        dano.clear();
                        danoPorSegundo.clear();
                        pontosVida.clear();
                        alcance.clear();
                        velocidadeImpacto.clear();
                        tipoChoice.getSelectionModel().clearSelection();
                        raridadeChoice.getSelectionModel().clearSelection();
                        velocidadeChoice.getSelectionModel().clearSelection();
                        alvosChoice.getSelectionModel().clearSelection();
                    } catch (NumberFormatException ex) {
                        // Tratamento específico para campos numéricos
                        System.err.println("Erro de formato numérico na carta: " + nomeCarta + ". Erro:" + ex.getMessage());
                    } catch (IllegalArgumentException ex) {
                        // Tratamento específico para erros de Enum (Tipo/Raridade/Alvos/Velocidade)
                        System.err.println("Erro de enum (Tipo/Raridade) ao ler carta: " + nomeCarta + ". Erro:" + ex.getMessage());
                    } catch (Exception ex) {
                        // Tratamento para qualquer outro erro
                        System.err.println("Erro inesperado ao processar a carta: " + nomeCarta + ". Erro:" + ex.getMessage());
                    }
                });


        tipoChoice.getItems().addAll(java.util.Arrays.stream(core.TipoCarta.values()).map(Enum::name).collect(Collectors.toList()));
        raridadeChoice.getItems().addAll(java.util.Arrays.stream(core.Raridade.values()).map(Enum::name).collect(Collectors.toList()));
        velocidadeChoice.getItems().addAll(java.util.Arrays.stream(core.Velocidade.values()).map(Enum::name).collect(Collectors.toList()));
        alvosChoice.getItems().addAll(java.util.Arrays.stream(core.Alvos.values()).map(Enum::name).collect(Collectors.toList()));

        VBox cadastroLayout = new VBox(10);
        cadastroLayout.setPadding(new javafx.geometry.Insets(10));
        ScrollPane scrollCadastro = new ScrollPane();
        scrollCadastro.setContent(cadastroLayout);
        cadastroLayout.getChildren().add(new Label("Cadastro de Cartas"));
        cadastroLayout.getChildren().add(new Separator());

        cadastroLayout.getChildren().add(new Label("Nome da Carta"));
        cadastroLayout.getChildren().add(nomeCarta);
        cadastroLayout.getChildren().add(new Label("Tipo:"));
        cadastroLayout.getChildren().add(tipoChoice);
        cadastroLayout.getChildren().add(new Label("Nível:"));
        cadastroLayout.getChildren().add(nivelCarta);
        cadastroLayout.getChildren().add(new Label("Custo de Elixir:"));
        cadastroLayout.getChildren().add(custoElixir);
        cadastroLayout.getChildren().add(new Label("Raridade:"));
        cadastroLayout.getChildren().add(raridadeChoice);
        cadastroLayout.getChildren().add(new Label("Velocidade:"));
        cadastroLayout.getChildren().add(velocidadeChoice);
        cadastroLayout.getChildren().add(new Label("Dano:"));
        cadastroLayout.getChildren().add(dano);
        cadastroLayout.getChildren().add(new Label("Dano por segundo:"));
        cadastroLayout.getChildren().add(danoPorSegundo);
        cadastroLayout.getChildren().add(new Label("Pontos de vida:"));
        cadastroLayout.getChildren().add(pontosVida);
        cadastroLayout.getChildren().add(new Label("Alvos:"));
        cadastroLayout.getChildren().add(alvosChoice);
        cadastroLayout.getChildren().add(new Label("Alcance:"));
        cadastroLayout.getChildren().add(alcance);
        cadastroLayout.getChildren().add(new Label("Velocidade de impacto:"));
        cadastroLayout.getChildren().add(velocidadeImpacto);

        cadastroLayout.getChildren().add(salvarButton);

        Tab cartasTab = new Tab("Cartas", scrollCadastro);
        cartasTab.setClosable(false);
        tabPane.getTabs().addAll(cartasTab);

        //Decks
        VBox decksLayout = new VBox(10, new Label("Cadastro de Decks"));
        decksLayout.setPadding(new javafx.geometry.Insets(10));
        Tab decksTab = new Tab("Decks", decksLayout);
        decksTab.setClosable(false);
        tabPane.getTabs().addAll(decksTab);

        Scene scene = new Scene(tabPane, 400, 600);
        stage.setTitle("Clash Cards");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);}
}
