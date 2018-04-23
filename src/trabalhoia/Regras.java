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
     * Turno atual do jogo.
     */
    private int turnoAtual = 0;

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
}
