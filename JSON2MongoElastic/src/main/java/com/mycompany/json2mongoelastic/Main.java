/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.json2mongoelastic;

import java.io.IOException;

/**
 *
 * @author bruno
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        PopulaMongo pmongo = new PopulaMongo();
        PopulaElastic pelastic = new PopulaElastic();
        
        // pasta onde se encontram os JSON
        String dir = "/home/grupobd22/Trbalho-BD/documentos";
        // database do MongoDB para onde vao os JSON
        String mongoDatabase = "fofos";
        // collection do MongoDB para onde vao os JSON
        String mongoCollection = "suppliers";
        // index do ElasticSearch para onde vao os JSON
        String elasticIndex = "suppliers";
        // os JSON inseridos no ElasticSerch serao inseridos com id em ordem crescente comecando por initId
        int initId = 1;
        
        pmongo.setDir(dir);
        pmongo.setDatabase(mongoDatabase);
        pmongo.setCollection(mongoCollection);
        pelastic.setDir(dir);
        pelastic.setIndex(elasticIndex);
        pelastic.setInitId(initId);
        pmongo.push();
        pelastic.push();
    }
    
}
