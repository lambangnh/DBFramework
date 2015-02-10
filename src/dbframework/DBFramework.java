/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbframework;

import i.project.core.Model;
import java.util.List;

/**
 *
 * @author auliayf
 */
public class DBFramework {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        
        TestModel2 model = new TestModel2();
        model.set("Kode_KK", "01");
        model.set("Kode_mata_diklat", "01");
        List<Model> models = model.join(new TestModel(), Model.JOIN_EMPTY);
        for(Model m : models){
            System.out.println(m.get("Kode_KK") + " | " + m.get("Nama_mata_diklat") + " | " + m.get("Nama_kompetensi_keahlian"));
        }
    }

}
