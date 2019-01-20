package com.kudubisa.app.navdrawertemplate.component;

/**
 * Created by asus on 8/7/18.
 */

public class CooperMethod {

    /**
     * hitung berat badan ideal ibu hamil
     * @param tb (tinggi badan)
     * @param uk (usia kandungan)
     * @return bbih
     */
    public double getBBIH(int tb, int uk){
        double bbih = 0;
        int bbi = 0;
        if (tb > 160) {
            bbi = tb - 110;
        } else if(tb < 150) {
            bbi = tb - 100;
        } else {
            bbi = tb - 105;
        }

        bbih = (bbi + (uk * 0.35));

        return bbih;
    }

    private double getAKal(double bbih) {
        return 1 * 24 * bbih;
    }

    private double getBKal(double bbih, int lamaTidur) {
        return 0.1 * lamaTidur * bbih;
    }

    public double getKTidur(double bbih, int lamaTIdur) {
        return getAKal(bbih) + getBKal(bbih, lamaTIdur);
    }

    public double getAktifitas(double aktifitas, double cKal) {
        double dKal = aktifitas * cKal;
        return dKal + cKal;
    }

    public double getSda(double eKal, double x) {
        double fKal = x * eKal;
        return fKal + eKal;
    }
}
