/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia;

import java.util.List;

/**
 *
 * @author Joao
 */
public class IA {
    Regras regras;
    int time;
    List<Jogada> proximaJogada;
    
    public IA(Regras regras, Tabuleiro tab, int time){
        this.regras = regras;
        this.time = time;
    }
    
    public void botJoga(Tabuleiro tabuleiro){
        Tabuleiro tab = tabuleiro.copia();
        proximaJogada = null;
        minMax(tab,Integer.MIN_VALUE, Integer.MAX_VALUE, time);
        
        //regras.realizaMovimento(tabuleiro, );
        
    }

    private void minMax(Tabuleiro tab, int MIN_VALUE, int MAX_VALUE, int time) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
