/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mini.server;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Pichau
 */
@ServerEndpoint("/operadoraserver/{num}")
public class OperadoraServer {
   private static Map<String, Session> conexoesAtivas = 
            Collections.synchronizedMap(new HashMap<String, Session>());
   private static Map<String, Linha> linhasRegistradas = 
            Collections.synchronizedMap(new HashMap<String, Linha>());
   
    @OnOpen
    public void handleOpen(@PathParam("num")String num, Session session){
        try {
            session.getBasicRemote().sendText("Seja bem vindo,"+num);
            conexoesAtivas.put(num, session);
            
            if(!this.linhasRegistradas.containsKey(num)){
                this.addLinha(num);
            }
           
            if(this.linhasRegistradas.containsKey(num)){
                Linha client = this.linhasRegistradas.get(num);
                Session clientConnection = this.conexoesAtivas.get(num);
            }
            session.getBasicRemote().sendText("Para obter seu saldo, digite: saldo\n");
            session.getBasicRemote().sendText("Para enviar um sms, digite: msg [numero_destino] [mensagem]\n");
        } catch (IOException ex) {
            Logger.getLogger(OperadoraServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @OnMessage
    public String handleMessage(@PathParam("num")String num,String message){
        Linha cliente = this.linhasRegistradas.get(num);
        
        if(message.equals("saldo")){
            return cliente.getNum() +" ,seu saldo é --> "+cliente.getSaldo().toString();
        }else if(message.trim().split(" ")[0].equalsIgnoreCase("creditar")){
            
            String[] valor = message.trim().split(" ");
            Double quantia = Double.parseDouble(valor[1]);  
            cliente.setSaldo(quantia);
            return "Vc creditou: "+ valor[1];
        }
        if(message.trim().split(" ")[0].equalsIgnoreCase("msg")){
            String[] inputDoCliente = message.trim().split(" ");
            String numDestino = inputDoCliente[1];
            String texto = "";
            
            for(int x = 1; x<inputDoCliente.length;x++){
                texto += inputDoCliente[x] + " ";
            }
            if(!this.linhasRegistradas.containsKey(numDestino)){
                return "O numero de destino não existe! ("+numDestino+")";
            }
            if(!this.conexoesAtivas.containsKey(numDestino)&&cliente.getSaldo() > 0.50){
                return "O número esta fora de area ou desligado!";
            }  
            
            if (cliente.getSaldo() < 0.50) {
            return "Vc está sem saldo, credite digitando: creditar [valor]!";
            }
            try {
                this.conexoesAtivas.get(numDestino).getBasicRemote()
                        .sendText(texto);
                cliente.debitarSaldo();
                return "Msg enviada: "+texto;
            } catch (IOException ex) {
                Logger.getLogger(OperadoraServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            return "o numero destino foi "+ numDestino+" e o texto foi "+ texto;
            
        }
        
        return "n entrou no if";
    }
    @OnClose
    public void handleClose(@PathParam("num")String num){
        this.conexoesAtivas.remove(num);
    }
    @OnError
    public void handleError(Throwable t){
        
    }
    
    public void saveConnection(String num, Session session){
        
    }
    
private void addLinha(String numero) {
        linhasRegistradas.put(numero, new Linha(numero));
    }
    
    
}
