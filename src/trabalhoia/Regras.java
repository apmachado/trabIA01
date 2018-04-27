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
        if(existePossibilidadeDeCaptura(tab, time)){
            ArrayList<Jogada> jogadas = possiveisJogadasPeca(tab, tab.getPeca(pos));
            return jogadas.stream().anyMatch((jog) -> (jog.houveCaptura()));
        }
        return true;
    }

    Jogada validaMovimento(Tabuleiro tab, Posicao posInicial, Posicao posFinal, int time) {
        if(!tab.posValida(posFinal)){
            System.out.println("posicao invalida");
            return null;
        }
        if(tab.existePecaPos(posFinal)){
            System.out.println("Eieiei já tem uma peca aqui");
            return null;
        }
        Jogada jogada = new Jogada();
        jogada.setPosInicial(posInicial);
        jogada.setPosFinal(posFinal);
        jogada.setPecaMovida(tab.getPeca(posInicial));
        
        if(posInicial.getI()-posFinal.getI()==2 ||posInicial.getI()-posFinal.getI()==-2){
            Posicao pos = new Posicao(posInicial.getI()+posFinal.getI()/2, posInicial.getJ()+posFinal.getJ()/2);
            Peca pecaCapturada = tab.getPeca(pos);
            jogada.setPecaCapturada(pecaCapturada);
        }
        
        ArrayList<Jogada> possiveisJogadas = possiveisJogadasPeca(tab, tab.getPeca(posInicial));
        /*É obrigado a comer*/
        for(Jogada jog : possiveisJogadas){
            if(jog.houveCaptura()&& !jogada.houveCaptura())
                return null; 
        }
        for(Jogada jog : possiveisJogadas){
            if(jog.getPosFinal().getI()==posFinal.getI() && jog.getPosFinal().getJ()==posFinal.getJ()){
                return jog;
            }
        }
        
        return null;
    }
    
    ArrayList<Jogada> possiveisJogadasPeca(Tabuleiro tab, Peca peca){
        Posicao posicaoPeca = tab.getPosicao(peca);
        ArrayList<Jogada> possiveisJogadas = new ArrayList<>();
        
        int vertical = peca.getTime()==1 ? -1 : +1;
        
        Posicao esquerda = new Posicao(posicaoPeca.getI() + vertical, posicaoPeca.getJ() -1);
        Posicao direita = new Posicao(posicaoPeca.getI() + vertical, posicaoPeca.getJ() +1);

        if(tab.posValida(esquerda) && tab.existePecaPos(esquerda)&& tab.getPeca(esquerda).getTime()!= peca.getTime()){
            Posicao comidaEsquerda = new Posicao(esquerda.getI() + vertical, esquerda.getJ() -1);
            if(tab.posValida(comidaEsquerda) && !tab.existePecaPos(comidaEsquerda)){
                Jogada jogada = new Jogada(tab.getPeca(esquerda), peca, posicaoPeca, comidaEsquerda);
                possiveisJogadas.add(jogada);
            }
        }
        if(tab.posValida(direita) && tab.existePecaPos(direita)&& tab.getPeca(direita).getTime()!= peca.getTime()){
            Posicao comidaDireita = new Posicao(direita.getI() + vertical, direita.getJ() +1);
            if(tab.posValida(comidaDireita) && !tab.existePecaPos(comidaDireita)){
                Jogada jogada = new Jogada(tab.getPeca(direita), peca, posicaoPeca, comidaDireita);
                possiveisJogadas.add(jogada);
            }
        }
        if(!possiveisJogadas.isEmpty())
            return possiveisJogadas;
        
        if(tab.posValida(esquerda) && !tab.existePecaPos(esquerda)){
            Jogada jogada = new Jogada(null, peca, posicaoPeca, esquerda);
            possiveisJogadas.add(jogada);
        }
        if(tab.posValida(direita) && !tab.existePecaPos(direita)){
            Jogada jogada = new Jogada(null, peca, posicaoPeca, direita);
            possiveisJogadas.add(jogada);
        }

        return possiveisJogadas;
    }
    
    void realizaMovimento(Tabuleiro tab, Jogada jogada){
        System.out.println("regra ta falando que eh a vez do time " + jogada.getPecaMovida().getTime());
        
        tab.movePeca(jogada.getPecaMovida(), jogada.getPosFinal());
        if(jogada.getPosFinal().getI()==0&& jogada.getPecaMovida().getTime()==1 ||
                jogada.getPosFinal().getI()==7&& jogada.getPecaMovida().getTime()==2)
            jogada.getPecaMovida().setDama();
        if(jogada.houveCaptura())
            tab.removePeca(jogada.getPecaCapturada());
    }
    
    boolean existePossibilidadeDeCaptura(Tabuleiro tab, int time){
        Posicao pos;
        ArrayList<Jogada> possiveisJogadas = new ArrayList<>();

        for(int i=0; i<tab.DIMEN;i++){
            for(int j=0; j<tab.DIMEN;j++){
                pos = new Posicao(i,j);
                if(tab.existePecaPos(pos)&&tab.getPeca(pos).getTime()==time){
                    possiveisJogadas = possiveisJogadasPeca(tab, tab.getPeca(pos));
                    for(Jogada jog : possiveisJogadas){
                        if(jog.houveCaptura())
                            return true;
                    }
                }
            }
        }
        return false;
    }
}
