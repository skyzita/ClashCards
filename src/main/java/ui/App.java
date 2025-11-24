package ui;

import core.*;
import data.GerenciadorDeDados;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        final String caminhoPasta = "src/main/resources/data";
        final Path caminhoDoArquivo = Paths.get(caminhoPasta, "novasCartas.csv");

        //Carregar CSV
        GerenciadorDeDados ger = new GerenciadorDeDados();
        //Caminho dos Arquivos
        List<Carta> cartasCadastradas = ger.lerCartasDaPasta("src/main/resources/data");
        final AtomicReference<File> selectedFileRef = new AtomicReference<>();
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

        ImageView imagemCartaView = new ImageView();
        imagemCartaView.setFitWidth(120); // Ajuste o tamanho
        imagemCartaView.setFitHeight(150); // Ajuste o tamanho
        Label nomeLabel = new Label("Nome: ");
        Label tipoLabel = new Label("Tipo: ");
        Label nivelLabel = new Label("Nível: ");
        Label custoElixirLabel = new Label("Custo de Elixir: ");
        Label raridadeLabel = new Label("Raridade: ");
        Label velocidadeLabel = new Label("Velocidade: ");
        Label danoLabel = new Label("Dano: ");
        Label dpsLabel = new Label("Dano por Segundo: ");
        Label hpLabel = new Label("Pontos de Vida: ");
        Label alvosLabel = new Label("Alvos: ");
        Label alcanceLabel = new Label("Alcance: ");
        Label impactoLabel = new Label("Velocidade de Impacto: ");

        Button editarButton = new Button("Editar");
        editarButton.setDisable(true);

        VBox descricaoLayout = new VBox(10);
        descricaoLayout.setPadding(new Insets(10));
        descricaoLayout.getChildren().addAll(
                new Label("Detalhes da Carta"),
                new Separator(),
                imagemCartaView,
                nomeLabel,
                tipoLabel,
                nivelLabel,
                custoElixirLabel,
                raridadeLabel,
                velocidadeLabel,
                danoLabel,
                dpsLabel,
                hpLabel,
                alvosLabel,
                alcanceLabel,
                impactoLabel
        );
        descricaoLayout.getChildren().addAll(new Separator(), editarButton);

        ScrollPane scrollDescricao = new ScrollPane();
        scrollDescricao.setContent(descricaoLayout);
        Tab descricaoTab = new Tab("Detalhes", scrollDescricao);
        descricaoTab.setClosable(false);
        tabPane.getTabs().add(descricaoTab);

        listaCartas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Encontra a carta na lista
                Carta cartaSelecionada = cartasCadastradas.stream()
                        .filter(c -> c.getNome().equals(newSelection))
                        .findFirst()
                        .orElse(null);

                if (cartaSelecionada != null) {
                    // Atualiza os detalhes na aba "Detalhes"
                    if (cartaSelecionada.getImagem() != null) {
                        imagemCartaView.setImage(cartaSelecionada.getImagem());
                    } else {
                        imagemCartaView.setImage(null);
                    }
                    nomeLabel.setText("Nome: " + cartaSelecionada.getNome());
                    tipoLabel.setText("Tipo: " + cartaSelecionada.getTipo());
                    nivelLabel.setText("Nível: " + cartaSelecionada.getNivel());
                    custoElixirLabel.setText("Custo de Elixir: " + cartaSelecionada.getcustoElixir());
                    raridadeLabel.setText("Raridade: " + cartaSelecionada.getRaridade());
                    velocidadeLabel.setText("Velocidade: " + cartaSelecionada.getVelocidade());
                    danoLabel.setText("Dano: " + cartaSelecionada.getDano());
                    dpsLabel.setText("Dano por Segundo: " + cartaSelecionada.getDanoPorSegundo());
                    hpLabel.setText("Pontos de Vida: " + cartaSelecionada.getPontosVida());
                    alvosLabel.setText("Alvos: " + cartaSelecionada.getAlvos());
                    alcanceLabel.setText("Alcance: " + cartaSelecionada.getAlcance());
                    impactoLabel.setText("Velocidade de Impacto: " + cartaSelecionada.getVelocidadeImpacto());

                    // Muda para a aba de descrição
                    editarButton.setDisable(false);
                    editarButton.setOnAction(e -> {
                        showEditCardPopup(cartaSelecionada, cartasCadastradas, listaCartas, ger, caminhoDoArquivo, stage);
                    });
                    tabPane.getSelectionModel().select(descricaoTab);
                }
            } else {
                nomeLabel.setText("Nome: ");
                editarButton.setDisable(true);
                editarButton.setOnAction(null);
            }
        });

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

        final TextField caminhoImagem = new TextField();
        caminhoImagem.setEditable(false);
        caminhoImagem.setPromptText("Nenhuma imagem Selecionada");

        final Button selecionarImagemButton = new Button("Selecionar Imagem");

        selecionarImagemButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Selecionar Imagem da Carta");

            // Filtros de extensão para garantir que apenas imagens sejam selecionadas
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Arquivos de Imagem", "*.png", "*.jpg", "*.jpeg")
            );


            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                    selectedFileRef.set(file);
                caminhoImagem.setText(file.getName()); // Mostra apenas o nome do arquivo no TextField
            }
        });







        final Button salvarButton = new Button("Salvar Cadastro");


        final Path imagensPath = Paths.get(caminhoPasta, ".." ,"images").normalize();


        salvarButton.setOnAction(e -> {
                    try {
                        Carta novaCarta = new Carta();
                        novaCarta.setNome(nomeCarta.getText().trim());
                        novaCarta.setTipo(TipoCarta.valueOf(tipoChoice.getValue()));
                        novaCarta.setNivel(Integer.parseInt(nivelCarta.getText().trim()));
                        novaCarta.setcustoElixir(Integer.parseInt(custoElixir.getText().trim()));
                        novaCarta.setRaridade(Raridade.valueOf(raridadeChoice.getValue()));
                        novaCarta.setVelocidade(Velocidade.valueOf(velocidadeChoice.getValue()));
                        novaCarta.setDano(Integer.parseInt(dano.getText().trim()));
                        novaCarta.setDanoPorSegundo(Double.parseDouble(danoPorSegundo.getText().trim()));
                        novaCarta.setPontosVida(Integer.parseInt(pontosVida.getText().trim()));
                        novaCarta.setAlvos(Alvos.valueOf(alvosChoice.getValue()));
                        novaCarta.setAlcance(Double.parseDouble(alcance.getText().trim()));
                        novaCarta.setVelocidadeImpacto(Double.parseDouble(velocidadeImpacto.getText().trim()));

                        File sourceFile = selectedFileRef.get();

                        if (sourceFile != null && sourceFile.exists()) {
                            String nomeArquivoImagem = sourceFile.getName();
                            Path destino =  imagensPath.resolve(nomeArquivoImagem);


                            if (!java.nio.file.Files.exists(imagensPath)) {
                                java.nio.file.Files.createDirectories(imagensPath);
                            }

                            java.nio.file.Files.copy(sourceFile.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

                            javafx.scene.image.Image img = new javafx.scene.image.Image(destino.toString());
                            novaCarta.setImagem(img);
                            novaCarta.setNomeArquivoImagem(nomeArquivoImagem);
                        }   else{
                            novaCarta.setImagem(null);
                        }

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
                        caminhoImagem.clear();
                        selectedFileRef.set(null);
                    } catch (java.io.IOException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao copiar o arquivo de imagem: " + ex.getMessage());
                        alert.showAndWait();
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


        tipoChoice.getItems().addAll(Arrays.stream(TipoCarta.values()).map(Enum::name).collect(Collectors.toList()));
        raridadeChoice.getItems().addAll(Arrays.stream(Raridade.values()).map(Enum::name).collect(Collectors.toList()));
        velocidadeChoice.getItems().addAll(Arrays.stream(Velocidade.values()).map(Enum::name).collect(Collectors.toList()));
        alvosChoice.getItems().addAll(Arrays.stream(Alvos.values()).map(Enum::name).collect(Collectors.toList()));

        VBox cadastroLayout = new VBox(10);
        cadastroLayout.setPadding(new Insets(10));
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
        cadastroLayout.getChildren().add(new Separator());
        cadastroLayout.getChildren().add(new Label("Caminho da Imagem"));
        cadastroLayout.getChildren().add(caminhoImagem);
        cadastroLayout.getChildren().add(selecionarImagemButton);
        cadastroLayout.getChildren().add(new Separator());
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
        decksLayout.setPadding(new Insets(10));
        Tab decksTab = new Tab("Decks", decksLayout);
        decksTab.setClosable(false);
        tabPane.getTabs().addAll(decksTab);

        VBox creditosLayout = new VBox(10, new Label("Créditos"));
        creditosLayout.setPadding(new Insets(10));
        Tab creditosTab = new Tab("Creditos", creditosLayout);
        creditosTab.setClosable(false);
        tabPane.getTabs().addAll(creditosTab);

        Scene scene = new Scene(tabPane, 400, 600);
        stage.setTitle("Clash Cards");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void showEditCardPopup(Carta carta, List<Carta> cartasCadastradas, ListView<String> listaCartas, GerenciadorDeDados ger, Path caminhoDoArquivo, Stage primaryStage){

        Stage popupStage = new Stage();
        popupStage.setTitle("Editar Carta: " + carta.getNome());
        popupStage.initOwner(primaryStage);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setResizable(false);

        final AtomicReference<File> selectedFileRef = new AtomicReference<>();
        final Path imagensPath = Paths.get("src", "main", "resources", "images");

        TextField nomeField = new TextField(carta.getNome());
        nomeField.setDisable(true);

        ChoiceBox<TipoCarta> tipoChoice = new ChoiceBox<>(javafx.collections.FXCollections.observableArrayList(TipoCarta.values()));
        tipoChoice.setValue(carta.getTipo());

        TextField nivelField = new TextField(String.valueOf(carta.getNivel()));
        TextField custoElixirField = new TextField(String.valueOf(carta.getcustoElixir()));
        ChoiceBox<Raridade> raridadeChoiceBox = new ChoiceBox<>(javafx.collections.FXCollections.observableArrayList(Raridade.values()));
        raridadeChoiceBox.setValue(carta.getRaridade());
        ChoiceBox<Velocidade> velocidadeChoiceBox = new ChoiceBox<>(javafx.collections.FXCollections.observableArrayList(Velocidade.values()));
        velocidadeChoiceBox.setValue(carta.getVelocidade());
        TextField danoField = new TextField(String.valueOf(carta.getDano()));
        TextField dpsField = new TextField(String.valueOf(carta.getDanoPorSegundo()));
        TextField hpField = new TextField(String.valueOf(carta.getPontosVida()));
        ChoiceBox<Alvos> alvosChoiceBox = new ChoiceBox<>(javafx.collections.FXCollections.observableArrayList(Alvos.values()));
        alvosChoiceBox.setValue(carta.getAlvos());
        TextField alcanceField = new TextField(String.valueOf(carta.getAlcance()));
        TextField impactoField = new TextField(String.valueOf(carta.getVelocidadeImpacto()));


        final TextField caminhoImagem = new TextField();
        caminhoImagem.setEditable(false);

        String nomeArquivoAtual = (carta.getImagem() != null && carta.getImagem().getUrl() != null) ?
                carta.getImagem().getUrl().substring(carta.getImagem().getUrl().lastIndexOf("/") + 1) : "Nenhuma imagem selecionada";
        caminhoImagem.setText(nomeArquivoAtual);

        final Button selecionarImagemButton = new Button("Selecionar Nova Imagem");

        selecionarImagemButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Selecionar Nova Imagem");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Arquivos de Imagem", "*.png", "*.jpg", "*.jpeg")
            );

            File file = fileChooser.showOpenDialog(primaryStage);

            if (file != null) {
                selectedFileRef.set(file);
                caminhoImagem.setText(file.getName());
            }
        });

        Button salvarButton = new Button("Salvar Alterações");


        VBox editLayout = new VBox(10);
        editLayout.setPadding(new Insets(10));
        editLayout.getChildren().addAll(
                new Label("Nome:"), nomeField,
                new Label("Tipo:"), tipoChoice,
                new Label("Nível:"), nivelField,
                new Label("Custo de Elixir:"), custoElixirField,
                new Label("Raridade:"), raridadeChoiceBox,
                new Label("Velocidade:"), velocidadeChoiceBox,
                new Label("Dano:"), danoField,
                new Label("Dano por Segundo:"), dpsField,
                new Label("Pontos de Vida:"), hpField,
                new Label("Alvos:"), alvosChoiceBox,
                new Label("Alcance:"), alcanceField,
                new Label ("Velocidade de Impacto:"), impactoField,
                new Separator(),
                new Label("Nova Imagem:"), caminhoImagem,
                selecionarImagemButton,
                new Separator(),
                salvarButton
        );

        ScrollPane scrollPane = new ScrollPane(editLayout);
        Scene scene = new Scene(scrollPane, 380, 700);
        popupStage.setScene(scene);
        popupStage.show();

        salvarButton.setOnAction(e -> {
            try{
                carta.setTipo(tipoChoice.getValue());
                carta.setNivel(Integer.parseInt(nivelField.getText().trim()));
                carta.setcustoElixir(Integer.parseInt(custoElixirField.getText().trim()));
                carta.setRaridade(Raridade.valueOf(raridadeChoiceBox.getValue().toString()));
                carta.setVelocidade(Velocidade.valueOf(velocidadeChoiceBox.getValue().toString()));
                carta.setDano(Integer.parseInt(danoField.getText().trim()));


                carta.setDanoPorSegundo(Double.parseDouble(dpsField.getText().trim().replace(",", ".")));
                carta.setPontosVida(Integer.parseInt(hpField.getText().trim()));
                carta.setAlvos(Alvos.valueOf(alvosChoiceBox.getValue().toString()));
                carta.setAlcance(Double.parseDouble(alcanceField.getText().trim().replace(",",".")));
                carta.setVelocidadeImpacto(Double.parseDouble(impactoField.getText().trim().replace(",",".")));

               File sourceFile = selectedFileRef.get();
               if(sourceFile != null && sourceFile.exists()) {
                   String nomeArquivoImagem = sourceFile.getName();
                   Path destino = imagensPath.resolve(nomeArquivoImagem);

                   if(!java.nio.file.Files.exists(destino.getParent())){
                       java.nio.file.Files.createDirectories(destino.getParent());
                   }

                   java.nio.file.Files.copy(sourceFile.toPath(),destino, StandardCopyOption.REPLACE_EXISTING);

                   javafx.scene.image.Image img = new javafx.scene.image.Image(destino.toUri().toURL().toExternalForm());
                   carta.setImagem(img);
                   carta.setNomeArquivoImagem(nomeArquivoImagem);
               }
               ger.salvarCartasEmArquivo(cartasCadastradas, caminhoDoArquivo);

               popupStage.close();

               Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Carta '" + carta.getNome() + "' atualizada e salva!");
               successAlert.showAndWait();

               listaCartas.getSelectionModel().select(carta.getNome());
            } catch(NumberFormatException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro de Formato Numérico: Verifique se todos os campos numéricos estão corretos.");
            alert.showAndWait();
            } catch(Exception ex){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro inesperado ao salvar: " + ex.getMessage());
                alert.showAndWait();
                System.err.println("Erro: " + ex.getMessage());
            }
        });
    }
    public static void main(String[] args) {
        launch(args);}
}
