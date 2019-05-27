/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.json2mongoelastic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author bruno
 */
class PopulaElastic {
    
    private String dir;
    private String index;
    private int initId;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public int getInitId() {
        return initId;
    }

    public void setInitId(int initId) {
        this.initId = initId;
    }
    
    public void index(int id, String json) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        String url = "http://localhost:9200/"+index+"/_doc/"+id;
        HttpPut put = new HttpPut(url);
        put.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        put.setHeader("Accept", "application/json");
        put.setHeader("Content-type", "application/json");
        CloseableHttpResponse response = client.execute(put);
        client.close();
        response.close();
    }
    
    public void push() throws IOException {
        //nome dos arquivos json
        File pasta = new File(dir);
        String[] arqName = pasta.list();
        for (int i=0;i<arqName.length;i++)
            arqName[i] = dir + "/" + arqName[i];
        
        //arquivos json para string
        String[] arqj = new String[arqName.length];
        for (int i=0;i<arqj.length;i++) {
            int crte = 0;
            arqj[i] = "";
            try {
                FileReader arq = new FileReader(arqName[i]);
                BufferedReader lerArq = new BufferedReader(arq);
                crte = lerArq.read();
                while (crte!=-1) {
                    arqj[i] += (char) crte;
                    crte = lerArq.read();
                }
            }
            catch (IOException e) {
                System.err.printf("Erro na leitura\n", e.getMessage());
            }
        }
        
        //string pro elastic
        for (int i=0;i<arqj.length;i++) {
            this.index(i+initId, arqj[i]);
        }
        
    }
}
