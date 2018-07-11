/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia;

public class Peca {
    
    private int time;
    private boolean dama;
    
    public Peca(int time, boolean dama){
        this.time = time;
        this.dama = dama;
    }
    
    public Peca(){
        
    }
    
    public void setTime(int time){
        this.time = time;
    }
    
    public int getTime(){
        return time;
    }
    
    public void setDama(){
        this.dama = true;
    }
    
    public boolean isDama(){
        return dama;
    }
    
}
