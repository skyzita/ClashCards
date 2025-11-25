package core;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private String nome;
    private List<Carta> cartas = new ArrayList<>();


    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }


    public List<Carta> getCartas() {
        return this.cartas;
    }
    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }
}
