/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mini.server;

/**
 *
 * @author Pichau
 */
public class Linha {
    private String num;
    private double saldo;    
    
    public Linha(String num) {
        this.num = num;
        this.saldo = 0.0;
    }
    
    public void setNum(String num){
        this.num = num;
    }
    
    public String getNum(){
        return this.num;
    }
    public Double getSaldo() {
        return saldo;
    }
    public void setSaldo(Double Saldo) {
        this.saldo += Saldo;
    }
    
    public void debitarSaldo() {
        this.saldo = saldo - 0.50;
    }
  
    public void addCredito(Double credito) {
        this.saldo = saldo + credito; 
    }
    public void debitarMensagem() {
        this.saldo = saldo - 0.50;
    }
   
    public Boolean consultaSaldo(String messagem) {
        return messagem.trim().equalsIgnoreCase("saldo");
    }
}
