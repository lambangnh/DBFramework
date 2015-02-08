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
        DB.getConnection();

        DB.insert("Mata_diklat", new String[][]{{"Kode_mata_diklat", "01"}, {"Nama_mata_diklat", "Nama 1"}});
        DB.update("Mata_diklat", new String[][]{{"Nama_mata_diklat", "Nama satu"}}, new String[][]{{"Kode_mata_diklat", "01"}});
        DB.delete("Mata_diklat", new String[][]{{"Kode_mata_diklat", "01"}});
    }

}
