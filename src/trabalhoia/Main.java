/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    
    public static void desenhaTabuleiro(Tabuleiro tab){
        int n;
        for(int i=0; i< tab.DIMEN; i++){
            for(int j=0;j<tab.DIMEN;j++){
                Posicao pos = new Posicao(i,j);
                if(tab.getPeca(pos)==null)
                    n=0;
                else if(tab.getPeca(pos).isDama())
                    n = tab.getPeca(pos).getTime()==1 ? 3 : 4;
                else
                    n = tab.getPeca(pos).getTime();
                System.out.print(n + " ");
            }
            System.out.println("");
        }
    }
    
    private static void botJoga(Tabuleiro tab, Regras regras, int time) {
    
    }
    public static void playerJoga(Tabuleiro tab, Regras regras, int time){
        Scanner s = new Scanner(System.in);
        int i,j;
        Posicao posInicial, posFinal;
        do{
            System.out.println("Escolha a peca que deseja mover (i j)");
            i=s.nextInt();j=s.nextInt();
            posInicial = new Posicao(i,j);

            }while(!regras.validaPosicaoInicial(tab, posInicial, time));
            do{
            System.out.println("Faca sua jogada (i j)");
                i=s.nextInt();j=s.nextInt();
                posFinal = new Posicao(i,j);
            }while(regras.validaMovimento(tab, posInicial, posFinal, time)==null);
            Jogada jogada = regras.validaMovimento(tab, posInicial, posFinal, time);
            regras.realizaMovimento(tab, jogada);          
    }
    
    public static int playerVsPlayer(Tabuleiro tab, Regras regras){
            int time, turno =1;
            
            while(regras.jogoTerminou(tab)==0){
                time = turno%2==1 ? 1 : 2;
                desenhaTabuleiro(tab);
                playerJoga(tab, regras, time);
                turno++;
            }
            return regras.jogoTerminou(tab);
}
    
    public static int playerVsBot(Tabuleiro tab, Regras regras){
            int time, turno =1;
            
            while(regras.jogoTerminou(tab)==0){
                time = turno%2==1 ? 1 : 2;
                desenhaTabuleiro(tab);
                if(time==1){
                    playerJoga(tab, regras, time);
                }else{
                    botJoga(tab, regras, time);
                }
                turno++;
            }
            return regras.jogoTerminou(tab);
}
    
    
    
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro();
        Regras regras = new Regras();
        Scanner s = new Scanner(System.in);
        
        System.out.println("Que modo deseja jogar? \n [1] Player vs Player [2] Player vs bot");
        int escolha;
        int vencedor;
        do{
            escolha = s.nextInt();
            switch(escolha){
                    case 1:
                        vencedor = playerVsPlayer(tabuleiro, regras);
                        System.out.println("Parabéns player "+ vencedor + " ganhou! Vc eh o cara");
                        break;
                    case 2:
                        vencedor = playerVsBot(tabuleiro, regras);
                        if(vencedor==1)
                            System.out.println("Parabéns voce venceu nossa IA");
                        else
                            System.out.println("Deu ruim Zé");
                        break;
                    default:
                        System.out.println("Escolheu errado animal, escolhe de novo");
                        break;
            }
        }while(escolha!=1 && escolha!=2);
        
    }
    
}
