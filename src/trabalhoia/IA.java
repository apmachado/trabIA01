/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Joao
 */
public class IA {
    Regras regras;
    int time;
    ArrayList<Jogada> proximaJogada;
    int MAX_ITE= 6;
    String infoJogada;
    TradutorDeJogada tdj;
   
    
    public IA(Regras regras, Tabuleiro tab, int time){
        this.regras = regras;
        this.time = time;
        this.tdj = new TradutorDeJogada();
    }
    
    public void botJoga(Tabuleiro tabuleiro){
        Tabuleiro tab = tabuleiro.copia();
        proximaJogada = new ArrayList<>();
        Random r = new Random();
        Max(tab,Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        
        Jogada jogada = this.proximaJogada.get(r.nextInt(this.proximaJogada.size()));
        regras.realizaMovimento(tabuleiro, jogada.getPosInicial() ,jogada.getPosFinal());
        
        this.infoJogada = "Jogada do Bot: (" +
                tdj.Traduz(jogada.getPosInicial()) + ") -> ("+
                tdj.Traduz(jogada.getPosFinal());
        
    }

    private int Max(Tabuleiro tab, int alpha, int beta, int iteracao) {
        if(iteracao==MAX_ITE){
            return Utility(tab);
        }
        int v = Integer.MIN_VALUE;
        ArrayList<Jogada> possiveisJogadas = regras.jogadasPossiveis(tab, this.time);
        for(Jogada jogada : possiveisJogadas){
            Tabuleiro tabIte = tab.copia();
            regras.realizaMovimento(tabIte, jogada.getPosInicial(),jogada.getPosFinal());
            int vLinha = Min(tabIte, alpha, beta, iteracao+1);
            
            if(iteracao==0 && vLinha==v)
                this.proximaJogada.add(jogada);
            if(vLinha > v){
                v = vLinha;
                if(iteracao==0){
                    this.proximaJogada.clear();
                    this.proximaJogada.add(jogada);
                }
            }        
            if(vLinha>=beta)
                return v;
            if(vLinha>alpha)
                alpha = vLinha;
        } 
        return v;
    }

    private int Min(Tabuleiro tab, int alpha, int beta, int iteracao) {
        if(iteracao==MAX_ITE){
            return Utility(tab);
        }
        int v = Integer.MAX_VALUE;
        ArrayList<Jogada> possiveisJogadas = regras.jogadasPossiveis(tab, 1);
        for(Jogada jogada : possiveisJogadas){
            Tabuleiro tabIte = tab.copia();
            regras.realizaMovimento(tabIte, jogada.getPosInicial(),jogada.getPosFinal());
            int vLinha = Max(tabIte, alpha, beta, iteracao+1);
            if(vLinha<v)
                v = vLinha;
            if(vLinha<=alpha)
                return v;
            if(vLinha<beta)
                beta = vLinha;
            
        }
        return v;
    }
    
    private int Utility(Tabuleiro tab) {
        int timeInimigo = this.time==1 ? 2 : 1;
        int numPecas = tab.nPecasNormalJogador(this.time);
        int numPecasInimigas = tab.nPecasJogador(timeInimigo);
        int numDamas = tab.nDamasJogador(time);
        int numDamasInimigas = tab.nDamasJogador(timeInimigo);
        
        return (numPecas-numPecasInimigas)*3 + (numDamas-numDamasInimigas)*9;
    }
}
