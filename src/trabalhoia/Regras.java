/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joao
 */
public class Regras {
    /**
     * Instancia do tabuleiro que vai ser regido pela Regras.
     */
    private Tabuleiro tabuleiro = new Tabuleiro();

    /**
     * Identificadores dos jogadores.
     */
    public static final int JOGADOR_UM = 1;
    public static final int JOGADOR_DOIS = 2;
    /**
     * Jogador atual do turno.
     */
    private int jogadorAtual = JOGADOR_UM;

    /**
     * Numero de pecas de cada jogador.
     */
    private int nPecasJogador1 = 12;
    private int nPecasJogador2 = 12;

    private boolean jogoFinalizado;

    private List<Jogada> historicoJogador1 = new ArrayList<>();
    private List<Jogada> historicoJogador2 = new ArrayList<>();

    /*
    mover peça(regras de movimento)
    gerar as possiveis jogadas
    existe jogada
    capturar peça
    verificar se existe captura
    realizar jogada
    */
    /**
     * funcao que verifica se o jogo terminou
     * retorna 0 caso nao tenha terminado
     * retorna o jogador vencedor caso tenha terminado
     */
    int jogoTerminou(Tabuleiro tab) {
        
        return 0;
    }
    /**
     * 
     * @param tab
     * @param pos
     * @param time
     * @return 
     */
    boolean validaPosicaoInicial(Tabuleiro tab, Posicao pos,int time) {
        if(!tab.posValida(pos)){
            System.out.println("posicao invalida");
            return false;
        }
        if(!tab.existePecaPos(pos) || !(tab.getPeca(pos).getTime()==time)){
            System.out.println("nem tem uma peca sua nessa posicao");
            return false;
        }
        return true;
    }

    boolean validaMovimento(Tabuleiro tab, Posicao posInicial, Posicao posFinal, int time) {
        if(!tab.posValida(posFinal)){
            System.out.println("posicao invalida");
            return false;
        }
        if(tab.existePecaPos(posFinal)){
            System.out.println("Eieiei já tem uma peca aqui");
            return false;
        }
        return true;
    }
    
    void realizaMovimento(Tabuleiro tab, Posicao posInicial, Posicao posFinal,int time){
        System.out.println("regra ta falando que eh a vez do time " + time);
        Peca peca = tab.getPeca(posInicial);
        tab.movePeca(peca, posFinal);
    }
}
