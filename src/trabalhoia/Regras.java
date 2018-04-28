/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
        if(tab.getPecasJogador(1).isEmpty())
            return 2;
        if(tab.getPecasJogador(2).isEmpty())
            return 1;
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
        ArrayList<Jogada> possiveisJogadas = possiveisJogadasPeca(tab, tab.getPeca(pos));
          if(possiveisJogadas.isEmpty()){
            System.out.println("Nao existe movimento valido para essa peca,escolhe outra");
            return false;
        }
          
        System.out.println("Sugestao de Jogadas");
        Jogada jogada;
        Posicao sugest;
        for(int k = 0; k<possiveisJogadas.size(); k++){
            jogada = possiveisJogadas.get(k);
            sugest = jogada.getPosFinal();
            System.out.println(sugest.toString());
        }
        /*se existe possibilidade de captura no turno,
        só pecas que possuem captura podem ser selecionadas*/
        if(existePossibilidadeDeCaptura(tab, time)){
            ArrayList<Jogada> jogadas = possiveisJogadasPeca(tab, tab.getPeca(pos));
            if(jogadas.stream().anyMatch((jog) -> (jog.houveCaptura()))){
                return jogadas.stream().anyMatch((jog) -> (jog.houveCaptura()));
            }else{
                 System.out.println("Vc eh obrigado a fazer a captura");
                 return false;
            }
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
        ArrayList<Jogada> possiveisJogadas = possiveisJogadasPeca(tab, tab.getPeca(posInicial));
        for(Jogada jog : possiveisJogadas){
            if(jog.getPosFinal().getI()==posFinal.getI() && jog.getPosFinal().getJ()==posFinal.getJ()){
                return jog;
            }
        }
        
        return null;
    }
    /**
     * Retorna as possiveis jogadas de uma peca. Se existir possibilidade de captura
     * só as capturas serão retornadas
     * @param tab
     * @param peca
     * @return 
     */
    
    ArrayList<Jogada> possiveisJogadasPeca(Tabuleiro tab, Peca peca){
        if(peca.isDama())
            return possiveisJogadasDama(tab, peca);
        return possiveisJogadasPecaNormal(tab, peca);
    }
    
    ArrayList<Jogada> possiveisJogadasPecaNormal(Tabuleiro tab, Peca peca){
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
        /*se existe captura, não adiciona mais jogadas*/
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
    
    ArrayList<Jogada> possiveisJogadasDama(Tabuleiro tab, Peca peca){
        Posicao posicaoPeca = tab.getPosicao(peca);
        ArrayList<Jogada> possiveisJogadas = new ArrayList<>();
        ArrayList<Jogada> possiveisCapturas = new ArrayList<>();
        
        Posicao esquerdaCima = new Posicao(posicaoPeca.getI() -1, posicaoPeca.getJ() -1);
        Posicao direitaCima = new Posicao(posicaoPeca.getI() -1, posicaoPeca.getJ() +1);
        Posicao esquerdaBaixo = new Posicao(posicaoPeca.getI() +1, posicaoPeca.getJ() -1);
        Posicao direitaBaixo = new Posicao(posicaoPeca.getI() +1, posicaoPeca.getJ() +1);
        
        
        while(tab.posValida(esquerdaCima) && !tab.existePecaPos(esquerdaCima)){
            Jogada jogada = new Jogada(null, peca, posicaoPeca, esquerdaCima);
            possiveisJogadas.add(jogada);
            esquerdaCima = new Posicao(esquerdaCima.getI() -1, esquerdaCima.getJ() -1);
        }
        if(tab.posValida(esquerdaCima) && tab.existePecaPos(esquerdaCima)&& tab.getPeca(esquerdaCima).getTime()!= peca.getTime()){
            Posicao comidaEsquerda = new Posicao(esquerdaCima.getI() -1, esquerdaCima.getJ() -1);
            if(tab.posValida(comidaEsquerda) && !tab.existePecaPos(comidaEsquerda)){
                Jogada jogada = new Jogada(tab.getPeca(esquerdaCima), peca, posicaoPeca, comidaEsquerda);
                possiveisCapturas.add(jogada);
            }
        }
        while(tab.posValida(esquerdaBaixo) && !tab.existePecaPos(esquerdaBaixo)){
            Jogada jogada = new Jogada(null, peca, posicaoPeca, esquerdaBaixo);
            possiveisJogadas.add(jogada);
            esquerdaBaixo = new Posicao(esquerdaBaixo.getI() +1, esquerdaBaixo.getJ() -1);
        }
        if(tab.posValida(esquerdaBaixo) && tab.existePecaPos(esquerdaBaixo)&& tab.getPeca(esquerdaBaixo).getTime()!= peca.getTime()){
            Posicao comidaEsquerdaBaixo = new Posicao(esquerdaBaixo.getI() +1, esquerdaBaixo.getJ() -1);
            if(tab.posValida(comidaEsquerdaBaixo) && !tab.existePecaPos(comidaEsquerdaBaixo)){
                Jogada jogada = new Jogada(tab.getPeca(esquerdaBaixo), peca, posicaoPeca, comidaEsquerdaBaixo);
                possiveisCapturas.add(jogada);
            }
        }
        
        while(tab.posValida(direitaCima) && !tab.existePecaPos(direitaCima)){
            Jogada jogada = new Jogada(null, peca, posicaoPeca, direitaCima);
            possiveisJogadas.add(jogada);
            direitaCima = new Posicao(direitaCima.getI() -1, direitaCima.getJ() +1);
        }
        if(tab.posValida(direitaCima) && tab.existePecaPos(direitaCima)&& tab.getPeca(direitaCima).getTime()!= peca.getTime()){
            Posicao comidadireitaCima = new Posicao(direitaCima.getI() -1, direitaCima.getJ() +1);
            if(tab.posValida(comidadireitaCima) && !tab.existePecaPos(comidadireitaCima)){
                Jogada jogada = new Jogada(tab.getPeca(direitaCima), peca, posicaoPeca, comidadireitaCima);
                possiveisCapturas.add(jogada);
            }
        }
        while(tab.posValida(direitaBaixo) && !tab.existePecaPos(direitaBaixo)){
            Jogada jogada = new Jogada(null, peca, posicaoPeca, direitaBaixo);
            possiveisJogadas.add(jogada);
            direitaBaixo = new Posicao(direitaBaixo.getI() +1, direitaBaixo.getJ() +1);
        }
        if(tab.posValida(direitaBaixo) && tab.existePecaPos(direitaBaixo)&& tab.getPeca(direitaBaixo).getTime()!= peca.getTime()){
            Posicao comidadireitaBaixo = new Posicao(direitaBaixo.getI() +1, direitaBaixo.getJ() +1);
            if(tab.posValida(comidadireitaBaixo) && !tab.existePecaPos(comidadireitaBaixo)){
                Jogada jogada = new Jogada(tab.getPeca(direitaBaixo), peca, posicaoPeca, comidadireitaBaixo);
                possiveisCapturas.add(jogada);
            }
        }
        
        
        return !possiveisCapturas.isEmpty() ? possiveisCapturas : possiveisJogadas;
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
    
    /**
     * Verifica se existe possibilidade de captura no turno. Funçao necessaria para validar posicao inicial
     * @param tab
     * @param time
     * @return 
     */
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
