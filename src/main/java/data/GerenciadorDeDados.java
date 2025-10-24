package data;

import core.Carta;
import core.Raridade;
import core.TipoCarta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorDeDados {

    private static final String DELIMITADOR = ";";

    public List<Carta> lerCartasDoArquivo(Path caminhoDoArquivo){
        List<Carta> cartas = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(caminhoDoArquivo)){

            br.readLine();

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(DELIMITADOR);

                if (dados.length == 13) {
                    try{
                        Carta carta = new Carta();
                        carta.setNome(dados[0].trim());
                        carta.setNivel(Integer.parseInt(dados[1].trim()));
                        carta.setcustoElixir(Integer.parseInt(dados[2].trim()));
                        carta.setTipo(TipoCarta.valueOf(dados[3].toUpperCase()));
                        carta.setRaridade(Raridade.valueOf(dados[4].toUpperCase()));
                        //Mudar quando começar a trabalhar com INTERMEDIARIO
                        //Nivel BASICO não mexe com Imagens ainda
                        carta.setImagem(null);
                        carta.setDano(Integer.parseInt(dados[6].trim()));
                        carta.setDanoPorSegundo(Double.parseDouble(dados[7].trim()));
                        carta.setPontosVida(Integer.parseInt(dados[8].trim()));
                        carta.setAlvos(Integer.parseInt(dados[9].trim()));
                        carta.setAlcance(Double.parseDouble(dados[10].trim()));
                        carta.setVelocidade(dados[11].trim());
                        carta.setVelocidadeImpacto(Double.parseDouble(dados[12].trim()));

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
}
