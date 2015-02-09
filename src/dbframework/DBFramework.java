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
        TestModel model = new TestModel();
        
//        model.set("Kode_mata_diklat", "01");
//        model.set("Nama_mata_diklat", "Mata Diklat 01");
//        
//        model.insert();
        
        List<Model> models = model.get_where("Kode_mata_diklat", "03");
        for (Model row : models) {
            System.out.println(row.get(row.PRIMARY_KEY) + " | " + row.get("Nama_mata_diklat"));
        }
    }

}
