/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.json2mongoelastic;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.bson.Document;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
class PopulaMongo {
    
    private String dir;
    private String database;
    private String collection;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
    
    public void push() {
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
        
        //string para json
        JSONObject[] jsonCV = new JSONObject[arqj.length];
        for (int i=0;i<jsonCV.length;i++) {
            jsonCV[i] = new JSONObject(arqj[i]);
        }
        
        //json pro mongo
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase db = mongoClient.getDatabase(database);
        MongoCollection coll = db.getCollection(collection);
        for (JSONObject jsonCV1 : jsonCV) {
            Document doc = Document.parse(jsonCV1.toString());
            coll.insertOne(doc);
        }
        
    }
}
