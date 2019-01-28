/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
/**
 *
 * @author Mateus 
 */
public class FilmeClient {
    
    public static void main(String args[]) throws Exception {
        URL url = new URL("http://desktop-s7nb9j6:8080/MovieDB/FilmesServico?wsdl");
        QName qname = new QName(
                "http://filmes.pd.tsi.ifpb.edu.br/", 
                "FilmeServerImplService"
        );
        Service ws = Service.create(url, qname);
        System.out.println(ws);
        MovieDBService filme = ws.getPort(MovieDBService.class);

        System.out.println(filme.cadastrarFilme("Troia", "Ronilly", "IFPB", "Guerra", "2008"));
    }
}
