/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbframework;

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
        model.set("Kode_mata_diklat", "01");
        model.set("Nama_mata_diklat", "Mata Diklat 01");
        model.insert();
        
//        model.set("Nama_mata_diklat", "Nama Mata Diklat 01");
//        model.update();
        
//        model.delete();
    }

}
