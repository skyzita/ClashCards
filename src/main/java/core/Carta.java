package core;

import java.awt.*;

public class Carta {
    private String nome;
    private TipoCarta tipo;
    private int nivel;
    private int custoElixir;
    private Raridade raridade;
    private Velocidade velocidade;
    private Image imagem;
    private int dano;
    private double danoPorSegundo;
    private int pontosVida;
    private Alvos alvos;
    private double alcance;
    private double velocidadeImpacto;


    //Construtor Principal
    public Carta(String nome, int nivel, int custoElixir, TipoCarta tipo, Raridade raridade, Image imagem, double dano, double danoPorSegundo, int pontosVida, Alvos alvos, double alcance, Velocidade velocidade, double velocidadeDeImpacto){
        this.nome = nome;
        this.tipo = tipo;
        this.nivel = custoElixir;
        this.custoElixir = custoElixir;
        this.raridade = raridade;
        this.velocidade = velocidade;
        this.imagem = imagem;
        this.dano = (int) dano;
        this.danoPorSegundo = danoPorSegundo;
        this.pontosVida = pontosVida;
        this.alvos = alvos;
        this.alcance = alcance;
        this.velocidadeImpacto = velocidadeDeImpacto;
    }

    //Construtor Vazio para os .CSV
    public Carta(){

    }

    //GET & SET
//NOME
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
//TIPO
    public TipoCarta getTipo() {
        return tipo;
    }
    public void setTipo(TipoCarta tipo) {
        this.tipo = tipo;
    }
//NIVEL
    public int getNivel() {
        return this.nivel;
    }
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
//CUSTO
    public int getcustoElixir() {
        return this.custoElixir;
    }
    public void setcustoElixir(int custoElixir) {
        this.custoElixir = custoElixir;
    }
//RARIDADE
    public Raridade getRaridade() {
        return this.raridade;
    }
    public void setRaridade(Raridade raridade) {
        this.raridade = raridade;
    }
//VELOCIDADE
    public Velocidade getVelocidade() {
        return velocidade;
    }
    public void setVelocidade(Velocidade velocidade) {
        this.velocidade = velocidade;
    }
//IMAGEM
    public Image getImagem() {
        return this.imagem;
    }
    public void setImagem(Image imagem) {
        this.imagem = imagem;
    }
//DANO
    public int getDano() {
        return this.dano;
    }
    public void setDano(int dano) {
        this.dano = dano;
    }
//DPS
    public double getDanoPorSegundo() {
        return this.danoPorSegundo;
    }
    public void setDanoPorSegundo(double danoPorSegundo) {
        this.danoPorSegundo = danoPorSegundo;
    }
//HP
    public int getPontosVida() {
        return this.pontosVida;
    }
    public void setPontosVida(int pontosVida) {
        this.pontosVida = pontosVida;
    }
//ALVOS
    public Alvos getAlvos() {
        return this.alvos;
    }
    public void setAlvos(Alvos alvos) {
        this.alvos = alvos;
    }
//ALCANCE
    public double getAlcance() {
        return this.alcance;
    }
    public void setAlcance(double alcance) {
        this.alcance = alcance;
    }
//VELOCIDADE IMPACTO
    public double getVelocidadeImpacto() {
        return this.velocidadeImpacto;
    }
    public void setVelocidadeImpacto(double velocidadeImpacto) {
        this.velocidadeImpacto = velocidadeImpacto;
    }

    // Checar por duplicatas
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Carta carta = (Carta) obj;

        if (this.nome == null) {
            return carta.nome == null;
        }

        return this.nome.equals(carta.nome);
    }

    public int hashCode() {
        return nome != null ? nome.hashCode() : 0;
    }


}
