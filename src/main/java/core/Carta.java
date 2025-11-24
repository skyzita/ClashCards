package core;

import java.awt.*;

public class Carta {
    private String nome;
    private TipoCarta tipo;
    private int nivel;
    private int custoElixir;
    private Raridade raridade;
    private Velocidade velocidade;
    private javafx.scene.image.Image imagem;
    private int dano;
    private double danoPorSegundo;
    private int pontosVida;
    private Alvos alvos;
    private double alcance;
    private double velocidadeImpacto;

    private String nomeArquivoImagem;



    //Construtor Principal
    public Carta(String nome, int nivel, int custoElixir, TipoCarta tipo, Raridade raridade, javafx.scene.image.Image imagem, int dano, int danoPorSegundo, int pontosVida, Alvos alvos, double alcance, Velocidade velocidade, double velocidadeDeImpacto){
        this.nome = nome;
        this.tipo = tipo;
        this.setNivel(nivel);
        this.setcustoElixir(custoElixir);
        this.raridade = raridade;
        this.velocidade = velocidade;
        this.imagem = imagem;
        this.setDano(dano);
        this.setDanoPorSegundo(danoPorSegundo);
        this.setPontosVida(pontosVida);
        this.alvos = alvos;
        this.setAlcance(alcance);
        this.setVelocidadeImpacto(velocidadeImpacto);
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
        if (nivel > 15) {
            this.nivel = 15;
        } else if (nivel < 1) {
            this.nivel = 1;
        } else {
            this.nivel = nivel;
        }
    }
//CUSTO
    public int getcustoElixir() {
        return this.custoElixir;
    }
    public void setcustoElixir(int custoElixir) {
        if (custoElixir > 10) {
            this.custoElixir = 10;
        } else if (custoElixir < 1) {
            this.custoElixir = 1;
        } else {
            this.custoElixir = custoElixir;
        }
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
    public javafx.scene.image.Image getImagem() {
        return this.imagem;
    }
    public void setImagem(javafx.scene.image.Image imagem) {
        this.imagem = imagem;
    }
//DANO
    public int getDano() {
        return this.dano;
    }
    public void setDano(int dano) {
        if(dano < 0) {
            this.dano = 0;
        } else {
        this.dano = dano;
        }
    }
//DPS
    public double getDanoPorSegundo() {
        return this.danoPorSegundo;
    }
    public void setDanoPorSegundo(double danoPorSegundo) {
        if(danoPorSegundo < 0) {
            this.danoPorSegundo = 0;
        } else {
            this.danoPorSegundo = danoPorSegundo;
        }
    }
//HP
    public int getPontosVida() {
        return this.pontosVida;
    }
    public void setPontosVida(int pontosVida) {
        if(pontosVida < 1) {
            this.pontosVida = 1;
        } else {
            this.pontosVida = pontosVida;
        }
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
        if(alcance < 0) {
            this.alcance = 0;
        } else {
            this.alcance = alcance;
        }
    }
//VELOCIDADE IMPACTO
    public double getVelocidadeImpacto() {
        return this.velocidadeImpacto;
    }
    public void setVelocidadeImpacto(double velocidadeImpacto) {
        if(velocidadeImpacto < 0) {
            this.velocidadeImpacto = 0;
        } else {
            this.velocidadeImpacto = velocidadeImpacto;
        }
    }

    public String getNomeArquivoImagem() {
        return nomeArquivoImagem;
    }

    public void setNomeArquivoImagem(String nomeArquivoImagem) {
        this.nomeArquivoImagem = nomeArquivoImagem;
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
