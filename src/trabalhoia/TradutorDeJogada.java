/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Joao
 */
public class TradutorDeJogada {
    private Map<String, Integer> tabela;
    private Map<Integer, String> tabelaSug;

    public TradutorDeJogada() {
        this.tabela = new HashMap<>();
        tabela.put("A", 0);
        tabela.put("B", 1);
        tabela.put("C", 2);
        tabela.put("D", 3);
        tabela.put("E", 4);
        tabela.put("F", 5);
        tabela.put("G", 6);
        tabela.put("H", 7);
        
        this.tabelaSug = new HashMap<>();
        tabelaSug.put(0,"A");
        tabelaSug.put(1,"B");
        tabelaSug.put(2,"C");
        tabelaSug.put(3,"D");
        tabelaSug.put(4,"E");
        tabelaSug.put(5,"F");
        tabelaSug.put(6,"G");
        tabelaSug.put(7,"H");
        
    }
    
    public Posicao Traduz(String jogada){
        try{
        String[] ij = jogada.toUpperCase().split(";");
        int j = tabela.get(ij[0]);
        int i = 8-Integer.parseInt(ij[1]);
        Posicao posicao = new Posicao(i,j);
        return posicao;
        }catch(Exception e){
            System.out.println("posicao invalida");
        }
        return null;
    }
    
    public String Traduz(Posicao pos){
    String sugestao = tabelaSug.get(pos.getJ())+ ";" + (8-pos.getI());
    return sugestao;
    }
    
}
