package ba.unsa.etf.rpr;

public class Grad {
    private int id,brojStanovnika,drzavaB;
    private String naziv;
    private Drzava drzava;
    private boolean glavniGrad=false;

    public Grad(int id, int broj_stanovnika, int drzavaB, String naziv, Drzava drzava) {
        this.id = id;
        this.brojStanovnika = broj_stanovnika;
        this.drzavaB = drzavaB;
        this.naziv = naziv;
        this.drzava=drzava;

    }
    public Grad(){
    }
    public boolean isGlavniGrad() {
        return glavniGrad;
    }

    public void setGlavniGrad(boolean glavniGrad) {
        this.glavniGrad = glavniGrad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrojstanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int broj_stanovnika) {
        this.brojStanovnika = broj_stanovnika;
    }

    public int getDrzavaB() {
        return drzavaB;
    }

    public void setDrzavaB(int drzava) {
        this.drzavaB = drzava;
    }


    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Drzava getDrzava() {
        return this.drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;this.setDrzavaB(drzava.getId());
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    @Override
    public String toString() {
        return naziv;
    }
}
