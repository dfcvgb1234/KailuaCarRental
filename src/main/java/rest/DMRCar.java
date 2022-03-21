package rest;

import java.util.ArrayList;
import java.sql.Date;

public class DMRCar{

    private String regNr;
    private String stelNr;
    private String status;
    private String statusDato;
    private String foersteRegistreringDato;
    private boolean bilLeaset;
    private String leasingGyldigFra;
    private String leasingGyldigTil;
    private String leasingPeriode;
    private String foersteRegistreringDatoFormateret;
    private String koeretoejArtNavn;
    private String koeretoejAnvendelseNavn;
    private int totalVaegt;
    private String egenVaegt;
    private String vogntogVaegt;
    private int koereklarVaegtMinimum;
    private int tekniskTotalVaegt;
    private String paahaengVognTotalVaegt;
    private int akselAntal;
    private int siddepladserMinimum;
    private boolean tilkoblingsmulighed;
    private String tilkoblingsvaegtUdenBremser;
    private String tilkoblingsvaegtMedBremser;
    private boolean ncapTest;
    private String koeretoejstand;
    private String maerkeTypeNavn;
    private String modelTypeNavn;
    private String variantTypeNavn;
    private String farveTypeNavn;
    private String typeGodkendelsesNummer;
    private String modelAar;
    private String karrosseriTypeNavn;
    private double motorSlagVolumen;
    private double motorStoerrelse;
    private double motorStoersteEffekt;
    private int motorHestekraefter;
    private double motorKmPerLiter;
    private int motorKilometerstand;
    private String motorElektriskForbrug;
    private String drivkraftTypeNavn;
    private ArrayList<String> koeretoejUdstyrSamling;
    private String motorMaerkning;
    private String maksimumHastighed;
    private int motorCylinderAntal;
    private int antalDoere;
    private String faelgDaek;
    private String sporviddenForrest;
    private String sporviddenBagest;
    private String stelNummerAnbringelse;
    private String passagerAntal;
    private String traekkendeAksler;
    private String akselAfstand;
    private String stoersteAkselTryk;
    private String miljoeOplysningCO2Udslip;
    private String miljoeOplysningEmissionCO;
    private String miljoeOplysningEmissionHCPlusNOX;
    private String miljoeOplysningEmissionNOX;
    private boolean miljoeOplysningPartikelFilter;
    private String miljoeOplysningRoegtaethed;
    private String miljoeOplysningRoegtaethedOmdrejningstal;
    private String miljoeOplysningPartikler;
    private String motorKoerselStoej;
    private String motorStandStoej;
    private String motorStandStoejOmdrejningstal;
    private String normTypeNavn;
    private String adressePostNummer;
    private String adressePostNummerBy;
    private long koeretoejId;
    private String sekundaerStatus;
    private String sekundaerStatusDato;

    public String getRegNr() {
        return regNr;
    }

    public String getStelNr() {
        return stelNr;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusDato() {
        return statusDato;
    }

    public String getFoersteRegistreringDato() {
        return foersteRegistreringDato;
    }

    public boolean isBilLeaset() {
        return bilLeaset;
    }

    public String getLeasingGyldigFra() {
        return leasingGyldigFra;
    }

    public String getLeasingGyldigTil() {
        return leasingGyldigTil;
    }

    public String getLeasingPeriode() {
        return leasingPeriode;
    }

    public String getFoersteRegistreringDatoFormateret() {
        return foersteRegistreringDatoFormateret;
    }

    public String getKoeretoejArtNavn() {
        return koeretoejArtNavn;
    }

    public String getKoeretoejAnvendelseNavn() {
        return koeretoejAnvendelseNavn;
    }

    public int getTotalVaegt() {
        return totalVaegt;
    }

    public String getEgenVaegt() {
        return egenVaegt;
    }

    public String getVogntogVaegt() {
        return vogntogVaegt;
    }

    public int getKoereklarVaegtMinimum() {
        return koereklarVaegtMinimum;
    }

    public int getTekniskTotalVaegt() {
        return tekniskTotalVaegt;
    }

    public String getPaahaengVognTotalVaegt() {
        return paahaengVognTotalVaegt;
    }

    public int getAkselAntal() {
        return akselAntal;
    }

    public int getSiddepladserMinimum() {
        return siddepladserMinimum;
    }

    public boolean isTilkoblingsmulighed() {
        return tilkoblingsmulighed;
    }

    public String getTilkoblingsvaegtUdenBremser() {
        return tilkoblingsvaegtUdenBremser;
    }

    public String getTilkoblingsvaegtMedBremser() {
        return tilkoblingsvaegtMedBremser;
    }

    public boolean isNcapTest() {
        return ncapTest;
    }

    public String getKoeretoejstand() {
        return koeretoejstand;
    }

    public String getMaerkeTypeNavn() {
        return maerkeTypeNavn;
    }

    public String getModelTypeNavn() {
        return modelTypeNavn;
    }

    public String getVariantTypeNavn() {
        return variantTypeNavn;
    }

    public String getFarveTypeNavn() {
        return farveTypeNavn;
    }

    public String getTypeGodkendelsesNummer() {
        return typeGodkendelsesNummer;
    }

    public String getModelAar() {
        return modelAar;
    }

    public String getKarrosseriTypeNavn() {
        return karrosseriTypeNavn;
    }

    public double getMotorSlagVolumen() {
        return motorSlagVolumen;
    }

    public double getMotorStoerrelse() {
        return motorStoerrelse;
    }

    public double getMotorStoersteEffekt() {
        return motorStoersteEffekt;
    }

    public int getMotorHestekraefter() {
        return motorHestekraefter;
    }

    public double getMotorKmPerLiter() {
        return motorKmPerLiter;
    }

    public int getMotorKilometerstand() {
        return motorKilometerstand;
    }

    public String getMotorElektriskForbrug() {
        return motorElektriskForbrug;
    }

    public String getDrivkraftTypeNavn() {
        return drivkraftTypeNavn;
    }

    public ArrayList<String> getKoeretoejUdstyrSamling() {
        return koeretoejUdstyrSamling;
    }

    public String getMotorMaerkning() {
        return motorMaerkning;
    }

    public String getMaksimumHastighed() {
        return maksimumHastighed;
    }

    public int getMotorCylinderAntal() {
        return motorCylinderAntal;
    }

    public int getAntalDoere() {
        return antalDoere;
    }

    public String getFaelgDaek() {
        return faelgDaek;
    }

    public String getSporviddenForrest() {
        return sporviddenForrest;
    }

    public String getSporviddenBagest() {
        return sporviddenBagest;
    }

    public String getStelNummerAnbringelse() {
        return stelNummerAnbringelse;
    }

    public String getPassagerAntal() {
        return passagerAntal;
    }

    public String getTraekkendeAksler() {
        return traekkendeAksler;
    }

    public String getAkselAfstand() {
        return akselAfstand;
    }

    public String getStoersteAkselTryk() {
        return stoersteAkselTryk;
    }

    public String getMiljoeOplysningCO2Udslip() {
        return miljoeOplysningCO2Udslip;
    }

    public String getMiljoeOplysningEmissionCO() {
        return miljoeOplysningEmissionCO;
    }

    public String getMiljoeOplysningEmissionHCPlusNOX() {
        return miljoeOplysningEmissionHCPlusNOX;
    }

    public String getMiljoeOplysningEmissionNOX() {
        return miljoeOplysningEmissionNOX;
    }

    public boolean isMiljoeOplysningPartikelFilter() {
        return miljoeOplysningPartikelFilter;
    }

    public String getMiljoeOplysningRoegtaethed() {
        return miljoeOplysningRoegtaethed;
    }

    public String getMiljoeOplysningRoegtaethedOmdrejningstal() {
        return miljoeOplysningRoegtaethedOmdrejningstal;
    }

    public String getMiljoeOplysningPartikler() {
        return miljoeOplysningPartikler;
    }

    public String getMotorKoerselStoej() {
        return motorKoerselStoej;
    }

    public String getMotorStandStoej() {
        return motorStandStoej;
    }

    public String getMotorStandStoejOmdrejningstal() {
        return motorStandStoejOmdrejningstal;
    }

    public String getNormTypeNavn() {
        return normTypeNavn;
    }

    public String getAdressePostNummer() {
        return adressePostNummer;
    }

    public String getAdressePostNummerBy() {
        return adressePostNummerBy;
    }

    public long getKoeretoejId() {
        return koeretoejId;
    }

    public String getSekundaerStatus() {
        return sekundaerStatus;
    }

    public String getSekundaerStatusDato() {
        return sekundaerStatusDato;
    }
}
