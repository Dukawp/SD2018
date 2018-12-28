/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectcloudserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Duka_
 */
public class UserQueue {

    private Map<String,ArrayList<Utilizador>> tqueue;  // <servername,username>
    private ReentrantLock l = new ReentrantLock();  
    //private Condition cond = l.newCondition();
    
    public UserQueue(){
        this.tqueue = new HashMap<>();
        this.l = new ReentrantLock();
        //this.cond = l.newCondition();
    }
    
    
    public Map<String, ArrayList<Utilizador>> getUQ(){
        return this.tqueue;
    }
    
    public void add(String servername, Utilizador user){
        try{
        l.lock();
        ArrayList<Utilizador> aux = new ArrayList<>();
        if(tqueue.containsKey(servername)){
            aux = tqueue.get(servername);  
        }
        aux.add(user);
        tqueue.put(servername,aux);   
        System.out.println("USER ADICIONADO A QUEUE!!!");
        }finally{
            l.unlock();
        }
    }

    public void remove(String servername){
        try{
            l.lock();
            System.out.println("ENTREI NO REMOVE!!!!!!!!!!");
            if(tqueue.containsKey(servername)){
                Utilizador u = tqueue.get(servername).get(0);
                System.out.println("USER " +u.getUsername());
                tqueue.get(servername).remove(0);
                System.out.println("REMOVIDO DA FILA DE ESPERA!!!!!!");
                u.condC.signal();
                System.out.println("DEI O SIGNAL!!!!");
            }
            else{
                System.out.println("NAO HA NINGUEM EM FILA DE ESPERA!!!");
            }
        }finally{
            l.unlock();
        }
        
       
    }
    
    
    
}
