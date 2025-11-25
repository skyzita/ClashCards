package data;

import core.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class GerenciadorDeDados {

    private static final String DELIMITADOR = ";";

    public List<Carta> lerCartasDoArquivo(Path caminhoDoArquivo){
        List<Carta> cartas = new ArrayList<>();

        String linha;
        String dados[];

        try (BufferedReader br = Files.newBufferedReader(caminhoDoArquivo)){

            br.readLine();


            while ((linha = br.readLine()) != null) {

                linha = linha.trim();
                if(linha.startsWith("\"") && linha.endsWith("\"")){
                    linha = linha.substring(1, linha.length() - 1);
                }


                    dados = linha.split(DELIMITADOR, -1);

                if (dados.length == 13) {
                    try{
                        Carta carta = new Carta();
                        carta.setNome(dados[0].trim());

                    String imagePath = dados[5].trim();
                    if(!imagePath.isEmpty()){
                        try{
                            String resourcePath = "/images/" + imagePath;
                            javafx.scene.image.Image image = new javafx.scene.image.Image(getClass().getResourceAsStream(resourcePath));
                            carta.setImagem(image);

                            carta.setNomeArquivoImagem(imagePath);
                        } catch (Exception e){
                            System.err.println("Aviso: Não foi possível carregar a imagem para a carta '" + carta.getNome() + "'. Caminho esperado: /images/" + imagePath);
                            carta.setImagem(null);
                            carta.setNomeArquivoImagem("");
                        }
                    }
                    else{
                        carta.setImagem(null);
                        carta.setNomeArquivoImagem("");
                    }
                        carta.setNivel(Integer.parseInt(dados[1].trim()));
                        carta.setcustoElixir(Integer.parseInt(dados[2].trim()));

                        carta.setTipo(TipoCarta.valueOf(dados[3].toUpperCase()));
                        carta.setRaridade(Raridade.valueOf(dados[4].toUpperCase()));
                        //Mudar quando começar a trabalhar com INTERMEDIARIO
                        //Nivel BASICO não mexe com Imagens ainda
                        //carta.setImagem(null);
                        carta.setDano(Integer.parseInt(dados[6].trim()));
                        carta.setDanoPorSegundo(Double.parseDouble(dados[7].trim().replace(',', '.')));
                        carta.setPontosVida(Integer.parseInt(dados[8].trim()));

                        carta.setAlvos(Alvos.valueOf(dados[9].toUpperCase()));
                        carta.setAlcance(Double.parseDouble(dados[10].trim().replace(',', '.')));
                        carta.setVelocidade(Velocidade.valueOf(dados[11].toUpperCase()));
                        carta.setVelocidadeImpacto(Double.parseDouble(dados[12].trim().replace(',', '.')));

                        cartas.add(carta);
                    } catch (NumberFormatException e) {
                        System.err.println("Erro de formato numérico na carta: " + dados[0] + ". Erro:" + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Erro de enum (Tipo/Raridade) ao ler carta: " + dados[0] + ". Erro:" + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("Erro inesperado ao processar a carta. Linha: " + linha + ". Erro:" + e.getMessage());
                    }
                } else {
                    System.err.println("Linha ignorada no arquivo devido ao numero incorreto de campos: " + linha);
                }
            }
        }
        catch (IOException e){
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return cartas;
    }

    public List<Carta> lerCartasDaPasta(String caminhoDaPasta){
        List<Carta> todasAsCartas = new ArrayList<>();
        try {
            Files.walk(Paths.get(caminhoDaPasta), 1)
                    .filter(path -> path.toString().endsWith(".csv"))
                    .forEach(path -> todasAsCartas.addAll(lerCartasDoArquivo(path)));
        }  catch (IOException e){
            System.err.println("Erro ao ler a pasta: " + e.getMessage());
        }
        return todasAsCartas;
    }

    public void salvarCartasEmArquivo(List<Carta> cartas, Path caminhoDoArquivo ) {
        String cabecalho = "Nome;Nivel;CustoElixir;Tipo;Raridade;Imagem;Dano;DanoPorSegundo;PontosVida;Alvos;Alcance;Velocidade;VelocidadeImpacto";

        try (BufferedWriter bw = Files.newBufferedWriter(caminhoDoArquivo)) {

            bw.write(cabecalho);
            bw.newLine();


            for (Carta carta : cartas) {
        String imagemNome = carta.getNomeArquivoImagem() != null ? carta.getNomeArquivoImagem().trim() : "";
        if(carta.getImagem() != null && carta.getImagem().getUrl() != null){
            String url = carta.getImagem().getUrl();
            imagemNome = url.substring(url.lastIndexOf('/') + 1);
        }

                String[] dados = {
                    carta.getNome(),
                    String.valueOf(carta.getNivel()),
                    String.valueOf(carta.getcustoElixir()),
                    carta.getTipo().toString(),
                    carta.getRaridade().toString(),
                    imagemNome, //Imagem
                    String.valueOf(carta.getDano()),
                    String.valueOf(carta.getDanoPorSegundo()),
                    String.valueOf(carta.getPontosVida()),
                    carta.getAlvos().toString(),
                    String.valueOf(carta.getAlcance()),
                    carta.getVelocidade().toString(),
                    String.valueOf(carta.getVelocidadeImpacto())
                };

                String linha = String.join(DELIMITADOR, dados);

                bw.write(linha);
                bw.newLine();
            }
        }

        catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo CSV: " + e.getMessage());
        }
    }

    public Deck lerDeckDoArquivo(Path caminhoDoArquivo, List<Carta> todasAsCartas){
        Deck deck = new Deck();
        String linha;

        String nomeArquivo = caminhoDoArquivo.getFileName().toString();
        String nomeDeck = nomeArquivo.substring(0, nomeArquivo.lastIndexOf('.')).replace("_", " ");
        deck.setNome(nomeDeck);

        try(BufferedReader br = Files.newBufferedReader(caminhoDoArquivo)){


            if((linha = br.readLine()) != null) {
                linha = linha.trim();


                if(linha.startsWith("\"") && linha.endsWith("\"")){
                    linha = linha.substring(1, linha.length() - 1);
                }

                String[] nomesCartas = linha.split(DELIMITADOR, -1);


                if(nomesCartas.length == 8) {
                    List<Carta> cartasDoDeck = Arrays.stream(nomesCartas)
                            .map(nome -> todasAsCartas.stream()
                                    .filter(c -> c.getNome().equals(nome.trim()))
                                    .findFirst()
                                    .orElse(null))
                            .filter(java.util.Objects::nonNull)
                            .collect(Collectors.toList());

                    if (cartasDoDeck.size() == 8) {
                        deck.setCartas(cartasDoDeck);
                        return deck;
                    } else {
                        System.err.println("Erro: Não foi possível encontrar todas as 8 cartas referenciadas no Deck: " + nomeDeck);
                    }
                } else {
                    System.err.println("Erro: Arquivo do Deck '" + nomeDeck + "' não têm 8 campos de cartas.  Linha: "+ linha);
                }
            }
        }
        catch (IOException e) {
        System.err.println("Erro ao ler o arquivo do Deck: " + nomeDeck + ". Erro: " + e.getMessage());
        }
        return null;
    }

    public List<Deck> lerDeckDaPasta(String caminhoPasta, List<Carta> todasAsCartas){
        List<Deck> todosOsDecks = new ArrayList<>();
        try {
            Files.walk(Paths.get(caminhoPasta), 1)
                    .filter(path -> path.toString().endsWith(".csv"))
                    .forEach(path -> {
                        Deck deck = lerDeckDoArquivo(path, todasAsCartas);
                        if(deck != null){
                            todosOsDecks.add(deck);
                        }
                    });
        } catch (IOException e) {
            System.err.println("Erro ao ler a pasta de decks: " + e.getMessage());
        }
         return todosOsDecks;
        }


        public void salvarDeckEmArquivo(Deck deck, Path caminhoPasta){
        String nomeArquivo = deck.getNome().replaceAll("\\s", "_") + ".csv";
        Path caminhoDoArquivo = caminhoPasta.resolve(nomeArquivo);

        String linha  = deck.getCartas().stream()
                .limit(8)
                .map(Carta::getNome)
                .collect(Collectors.joining(DELIMITADOR));

        try{
            if(!Files.exists(caminhoPasta)){
                Files.createDirectories(caminhoPasta);
            }

            try(BufferedWriter bw = Files.newBufferedWriter(caminhoDoArquivo)) {
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar o Deck '" + deck.getNome() + "': " + e.getMessage());
        }
    }
}


