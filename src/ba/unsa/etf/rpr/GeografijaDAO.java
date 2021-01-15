package ba.unsa.etf.rpr;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GeografijaDAO {
    private static GeografijaDAO instance=null;
    private Connection conn;
    private PreparedStatement poredakgrad,poredakdrzava,obrisi,dodaju,obrisiDr,obrisiGradByIdDrzave,dodajDrzavu;

    private GeografijaDAO(){

        String url="jdbc:sqlite:baza.db";
        try {
            conn = DriverManager.getConnection(url);
            inicijalizirajBazu();
            poredakgrad = conn.prepareStatement("Select * from grad order by broj_stanovnika desc");
            poredakdrzava = conn.prepareStatement("Select * from drzava order by drzava_id");
            obrisi= conn.prepareStatement("DELETE FROM grad Where naziv=?");
            obrisiGradByIdDrzave = conn.prepareStatement("Delete from grad where drzava=?");
            obrisiDr= conn.prepareStatement("DELETE FROM drzava where naziv=?");
            dodaju= conn.prepareStatement("INSERT INTO grad VALUES(?,?,?,?)");
            dodajDrzavu= conn.prepareStatement("INSERT INTO DRZAVA VALUES(?,?,?)");
        } catch (SQLException throwables) {
            inicijalizirajBazu();
            System.out.println("Greska prilikom ocitavanja baze! ");
            throwables.printStackTrace();
        }
    }
    public void inicijalizirajBazu(){
        Scanner squeri= null;
        try {
            squeri = new Scanner(new FileInputStream("baza.db.sql"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String upit="";
        while(squeri.hasNext()){
            upit+=squeri.nextLine();
            if(upit.contains(";")){
                Statement stmt= null;
                try {
                    stmt = conn.createStatement();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    stmt.execute(upit);
                } catch (SQLException throwables) { }
                upit="";
            }
        }
        squeri.close();

    }
    public static GeografijaDAO getInstance(){
        if(instance==null){
                return instance=new GeografijaDAO();
        }
        return instance;
    }

    public ArrayList<Grad> gradovi() {
        ResultSet privrs= null;
        try {
            privrs = poredakgrad.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ArrayList<Grad> povratni = new ArrayList<>();
        while(true){
            try {
                if (!privrs.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                povratni.add(new Grad(privrs.getInt(1), privrs.getInt(2),privrs.getInt(3),
                        privrs.getString(4),dajDrzavuPoId(privrs.getInt(3))));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return povratni;
    }
    public ArrayList<Drzava> drzave() {
        ResultSet privrs= null;
        try {
            privrs = poredakdrzava.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ArrayList<Drzava> povratni= new ArrayList<Drzava>();
        while(true){
            try {
                if (!privrs.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                povratni.add(new Drzava(privrs.getInt(1),privrs.getInt(2),privrs.getString(3)));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return povratni;
    }
    public static void removeInstance(){
        if(instance!=null) {
            try {
                instance.conn.close();
                instance=null;
            } catch (SQLException throwables) {
                System.out.println("Greska u zatvaranju instance");
                throwables.printStackTrace();
            }
        }
    }

    public Grad glavniGrad(String drzava) {
        Grad povratni= null;
        for(Grad g: gradovi()){
            if(g.getDrzava().getNaziv().equals(drzava)){
                return g;
            }
        }
        return povratni;
    }

    public void dodajGrad(Grad g){
        try {
            if(g.getId()==0)g.setId(gradovi().size()+1);
            if(g.getDrzavaB()==0)g.setDrzavaB(g.getDrzava().getId());
            dodaju.setInt(1,g.getId());
            dodaju.setInt(2,g.getBrojstanovnika());
            dodaju.setInt(3,g.getDrzavaB());
            dodaju.setString(4,g.getNaziv());
            dodaju.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void obrisiGrad(String grad){
        try {
            obrisi.setString(1,grad);
            obrisi.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void obrisiDrzavu(String drzava){
        int id_drzave=-1;
        try {
            for(Drzava d:drzave()){
                if(drzava.equals(d.getNaziv()))id_drzave=d.getId();
            }
            obrisiGradByIdDrzave.setInt(1,id_drzave);
            obrisiDr.setString(1,drzava);
            obrisiGradByIdDrzave.executeUpdate();
            obrisiDr.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Drzava nadjiDrzavu(String drzava) {
        for(Drzava d: drzave()){
            if(drzava.equals(d.getNaziv()))return d;
        }
        return null;
    }

    public void dodajDrzavu(Drzava drzava) {
        try {
            if(drzava.getId()==0)drzava.setId(drzave().size()+1);
            dodajDrzavu.setInt(1,drzava.getId());
            dodajDrzavu.setInt(2,drzava.getGlavni_grad());
            dodajDrzavu.setString(3,drzava.getNaziv());
            dodajDrzavu.executeUpdate();

        } catch (SQLException throwables) {
            System.out.println("Neuspjelo dodavanje nove države!");
            throwables.printStackTrace();
        }
    }
    public Drzava dajDrzavuPoId(int x){
        for(Drzava d:drzave()){
            if(d.getId()==x)return d;
        }
        return null;
    }
    public Grad dajGradPoId(int x){
        for(Grad g:gradovi()){
            if(g.getId()==x)return g;
        }
        return null;
    }
    public void izmijeniGrad(Grad grad) {
        obrisiGrad(dajGradPoId(grad.getId()).getNaziv());
        dodajGrad(grad);
    }

    public void vratiBazuNaDefault() {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM grad");
            stmt.executeUpdate("DELETE FROM drzava");
            inicijalizirajBazu();
        } catch (SQLException throwables) {
            System.out.println("OK");
        }
    }
    public Grad nadjiGrad(String graz) {
        for(Grad g:gradovi()){
            if(g.getNaziv().equals(graz))return g;
        }
        return null;
    }
}
