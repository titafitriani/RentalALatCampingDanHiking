/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentalalatcampingdanhiking;

import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.io.File;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ConfigDB {
private String Password="";
private String Username="root";
private String Database="";

public Connection koneksi;
public ConfigDB() {
try {
Class.forName("com.mysql.jdbc.Driver").newInstance();
koneksi=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+this.Database,this.Username,this.Password);
System.out.println("Koneksi Berhasil");
} catch (Exception e) 
{
JOptionPane.showMessageDialog(null,"Maaf Terjadi Kesalahan pada bagian :\n ["+e.toString() +"]");
}
}
  public Object[][] isiTabel(String SQL, int jumlah){
Object[][] data=null;
try {
Statement st = ConfigDB.this.koneksi.createStatement();
ResultSet rs = st.executeQuery(SQL);
rs.last();
int baris=rs.getRow();
rs.beforeFirst();
int j=0;
data = new Object[baris][jumlah];
while (rs.next()) {
for (int i = 0; i < jumlah; i++) {
data[j][i]=rs.getString(i+1);
}
j++;
}
} catch (Exception e) {
JOptionPane.showMessageDialog(null,"Maaf Terjadi Kesalahan pada bagian method isiTable : \n ["+e.toString() +"]");
}
return data;
  }
  public void tampilTabel(String Judul[],String SQL, JTable Tabel){
try {
String title[]=Judul;
int jum =title.length;
Tabel.setModel(new DefaultTableModel(isiTabel(SQL,jum), title));
} catch (Exception e) {
JOptionPane.showMessageDialog(null,"Maaf Terjadi Kesalahan pada bagian method tampilTabel : \n ["+e.toString() +"]");
}
}
  public void aturLebarKolom(JTable tabel,int baris[]){
try {
int getBaris[]=baris;
int JumlahBaris=getBaris.length;
tabel.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
for (int i = 0; i < JumlahBaris-1; i++) {tabel.getColumnModel().getColumn(i).setPreferredWidth(getBaris[i]);
}
} catch (Exception e) { JOptionPane.showMessageDialog(null,"Maaf Terjadi Kesalahan pada bagian method aturLebarKolom : \n ["+e.toString() +"]");
}
}
  public void simpanData(String SQL){
try {
Statement st = ConfigDB.this.koneksi.createStatement();
st.execute(SQL);
st.close();
JOptionPane.showMessageDialog(null,"Data berhasil disimpan");
} catch (Exception e) {
JOptionPane.showMessageDialog(null,"Maaf Terjadi Kesalahan pada bagian method simpanData : \n ["+e.toString() +"]");
}
}
  public void ubahData(String SQL){
try {
Statement st = ConfigDB.this.koneksi.createStatement();
st.execute(SQL);
st.close();
JOptionPane.showMessageDialog(null,"Data berhasil diubah");
} catch (Exception e) {
JOptionPane.showMessageDialog(null,"Maaf Terjadi Kesalahan pada bagian method ubahData : \n ["+e.toString() +"]");
}
}
public void hapusData(String SQL){
try {
Statement st = ConfigDB.this.koneksi.createStatement();
st.execute(SQL);
st.close();
JOptionPane.showMessageDialog(null,"Data berhasil dihapus");
} catch (Exception e) {
JOptionPane.showMessageDialog(null,"Maaf Terjadi Kesalahan pada bagian method hapusData : \n ["+e.toString() +"]");
}
}    
public boolean duplikasiData(String tabel,String Key,String nilai){
boolean ada =false;
try {
Statement st = ConfigDB.this.koneksi.createStatement();
ResultSet rs = st.executeQuery("SELECT*FROM "+tabel+" WHERE"+Key+"="+nilai);
if (rs.next()){
ada=true;
}else {ada=false;}
} catch (Exception e) {
JOptionPane.showMessageDialog(null,"Maaf Terjadi Kesalahan pada bagian method duplikasiData : \n ["+e.toString() +"]");
}
return ada;
}
public void cariData(String Judul[],String Cari, JTable Tabel){
try {
Statement st = ConfigDB.this.koneksi.createStatement();
String title[]=Judul;
int jum =title.length;
Tabel.setModel(new DefaultTableModel(isiTabel(Cari,jum), title));
} catch (Exception e) {
JOptionPane.showMessageDialog(null,"Maaf Terjadi Kesalahan pada bagian method cariData : \n ["+e.toString() +"]");
}
}
public void tampilLaporan(String laporanFile, String SQL){
try {
File file = new File(laporanFile);
JasperDesign jasDes = JRXmlLoader.load(file);
JRDesignQuery sqlQuery = new JRDesignQuery();
sqlQuery.setText(SQL);
jasDes.setQuery(sqlQuery);
JasperReport JR = JasperCompileManager.compileReport(jasDes);
JasperPrint JP = JasperFillManager.fillReport(JR,null,ConfigDB.this.koneksi);
JasperViewer.viewReport(JP);
} catch (Exception e) {
JOptionPane.showMessageDialog(null,"Maaf Terjadi Kesalahan pada bagian method TampilLaporan : \n ["+e.toString() +"]");
}
}
public int jumlahRecord(String SQL){
int hasil=0;
int i=0;
try {
Statement st=ConfigDB.this.koneksi.createStatement();
ResultSet rs = st.executeQuery(SQL);
while (rs.next()) {
i++; }
hasil=i;
} catch (Exception e) {
JOptionPane.showMessageDialog(null,"Maaf Terjadi Kesalahan pada bagian method jumlahRecord : \n ["+e.toString() +"]");
}
return hasil;
}
}