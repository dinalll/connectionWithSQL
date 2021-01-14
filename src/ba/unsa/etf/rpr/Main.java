package ba.unsa.etf.rpr;

import java.sql.SQLException;

public class Main {
    private static GeografijaDAO x=GeografijaDAO.getInstance();
    public static void main(String[] args) {
        System.out.println(ispisiGradove());
    }
    public static String ispisiGradove(){
        String povratni="";
        try {
            for(Grad g: x.gradovi()){
                for(Drzava d: x.drzave()){
                  if(g.getDrzavaB()==d.getId()){
                    povratni+=g.getNaziv()+" ("+d.getNaziv()+") - "+g.getBrojstanovnika()+"\n";
                  }
                }
            }
            } catch (SQLException e) {
            e.printStackTrace();
        }
    return povratni;
    }

}
