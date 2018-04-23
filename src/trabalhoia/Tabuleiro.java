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

/**
 *
 * @author Joao
 */
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
}
/*
moverpecas
removerpecas
numero de pecas
pegar peça
pegar posição
verificar posiçao valida
verificar se existe peça
*/