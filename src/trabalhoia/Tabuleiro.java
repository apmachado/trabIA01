/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    public static final int DIMEN = 8;
    private Peca[][] tabuleiro;
    public static int DAMA_TIME1 = 3;
    public static int DAMA_TIME2 = 4;
    public static int PECA_TIME1 = 1;
    public static int PECA_TIME2 = 2;
    public static int VAZIO = 0;
    
    public Tabuleiro() {
        inicializaTabuleiro();
    }
    public Tabuleiro(Peca[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    private void inicializaTabuleiro() {
        tabuleiro = new Peca[DIMEN][DIMEN];

        //Inicializa peças do jogador 1.
        for (int i = 0; i < (DIMEN / 2) - 1; i++) {
            for (int j = 0; j < DIMEN; j++) {
                if (i % 2 == 0) {
                    if (j % 2 != 0) {
                        tabuleiro[i][j] = new Peca(2, false);
                    }
                } else if (i % 2 != 0) {
                    if (j % 2 == 0) {
                        tabuleiro[i][j] = new Peca(2, false);
                    }
                }
            }
        }

        //Inicializa peças do segundo jogador.
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < DIMEN; j++) {
                if (i % 2 == 0) {
                    if (j % 2 != 0) {
                        tabuleiro[i][j] = new Peca(1, false);
                    }
                } else if (i % 2 != 0) {
                    if (j % 2 == 0) {
                        tabuleiro[i][j] = new Peca(1, false);
                    }
                }
            }
        }
    }
    public int nPecasJogador(int time){   
        List<Peca> pecas = getPecasJogador(time);
        
        return pecas.size();
    }
    public int nPecasNormalJogador(int time){
        List<Peca> pecas = getPecasJogador(time);
        int n=0;
        for(Peca peca : pecas){
            if(!peca.isDama())
                n++;
        }
        return n;
    }
    public int nDamasJogador(int time){
        List<Peca> pecas = getPecasJogador(time);
        int n=0;
        for(Peca peca : pecas){
            if(peca.isDama())
                n++;
        }
        return n;
    }
    public Peca getPeca(Posicao posicao) {
        return tabuleiro[posicao.getI()][posicao.getJ()];
    }
    
    public Posicao getPosicao(Peca peca) {
        for (int i = 0; i < DIMEN; i++) {
            for (int j = 0; j < DIMEN; j++) {
                if (tabuleiro[i][j] != null) {
                    if (tabuleiro[i][j].equals(peca)) {
                        return new Posicao(i, j);
                    }
                }
            }
        }

        return null;
    }
    public void movePeca(Peca peca, Posicao posFinal) {
        Posicao posAtual = getPosicao(peca);

        tabuleiro[posAtual.getI()][posAtual.getJ()] = null;

        tabuleiro[posFinal.getI()][posFinal.getJ()] = peca;
    }
    public void removePeca(Peca peca) {
        Posicao posAtual = getPosicao(peca);

        tabuleiro[posAtual.getI()][posAtual.getJ()] = null;
    }
    public boolean posValida(Posicao pos) {
        if (pos.getI() >= 8 || pos.getJ() >= 8) {
            return false;
        }

        if (pos.getI() < 0 || pos.getJ() < 0) {
            return false;
        }

        return true;
    }
    public boolean existePecaPos(Posicao pos) {
        return getPeca(pos) != null;
    }
    public List<Peca> getPecas() {
        List<Peca> pecas = new ArrayList<>();
        for (int i = 0; i < DIMEN; i++) {
            for (int j = 0; j < DIMEN; j++) {
                Peca peca = getPeca(new Posicao(i, j));
                if (peca != null) {
                    pecas.add(peca);
                }
            }
        }

        return pecas;
    }
    public List<Peca> getPecasJogador(int time){
        List<Peca> pecas = new ArrayList<>();

        for (int i = 0; i < DIMEN; i++) {
            for (int j = 0; j < DIMEN; j++) {
                Peca peca = getPeca(new Posicao(i, j));
                if (peca != null) {
                    if(peca.getTime() == time){
                        pecas.add(peca);
                    }
                }
            }
        }

        return pecas;
    }
    
    Tabuleiro copia() {
        
        Peca[][] tabuleiroPecas = new Peca[DIMEN][DIMEN];
        for(int i = 0; i < DIMEN; i++){
        
            for(int j = 0; j < DIMEN; j++){
                Peca original = tabuleiro[i][j];
                if(original == null)
                    tabuleiroPecas[i][j] = null;
                else{
                    Peca nova = new Peca(original.getTime(),original.isDama());
                    tabuleiroPecas[i][j] = nova;
                }            
            }            
        }        
        
        return new Tabuleiro(tabuleiroPecas);
    }
}