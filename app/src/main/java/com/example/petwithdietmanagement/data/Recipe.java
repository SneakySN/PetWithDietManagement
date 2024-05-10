package com.example.petwithdietmanagement.data;

import java.util.List;

public class Recipe {
    private String rcpSeq;
    private String rcpNm;
    private String rcpWay2;
    private String rcpPat2;
    private String infoWgt;
    private String infoEng;
    private String infoCar;
    private String infoPro;
    private String infoFat;
    private String infoNa;
    private String hashTag;
    private String attFileNoMain;
    private String attFileNoMk;
    private String rcpPartsDtls;
    private List<String> manual;
    private List<String> manualImg;
    private String rcpNaTip;  // 추가된 필드

    // Getters and Setters
    public String getRcpSeq() { return rcpSeq; }
    public void setRcpSeq(String rcpSeq) { this.rcpSeq = rcpSeq; }

    public String getRcpNm() { return rcpNm; }
    public void setRcpNm(String rcpNm) { this.rcpNm = rcpNm; }

    public String getRcpWay2() { return rcpWay2; }
    public void setRcpWay2(String rcpWay2) { this.rcpWay2 = rcpWay2; }

    public String getRcpPat2() { return rcpPat2; }
    public void setRcpPat2(String rcpPat2) { this.rcpPat2 = rcpPat2; }

    public String getInfoWgt() { return infoWgt; }
    public void setInfoWgt(String infoWgt) { this.infoWgt = infoWgt; }

    public String getInfoEng() { return infoEng; }
    public void setInfoEng(String infoEng) { this.infoEng = infoEng; }

    public String getInfoCar() { return infoCar; }
    public void setInfoCar(String infoCar) { this.infoCar = infoCar; }

    public String getInfoPro() { return infoPro; }
    public void setInfoPro(String infoPro) { this.infoPro = infoPro; }

    public String getInfoFat() { return infoFat; }
    public void setInfoFat(String infoFat) { this.infoFat = infoFat; }

    public String getInfoNa() { return infoNa; }
    public void setInfoNa(String infoNa) { this.infoNa = infoNa; }

    public String getHashTag() { return hashTag; }
    public void setHashTag(String hashTag) { this.hashTag = hashTag; }

    public String getAttFileNoMain() { return attFileNoMain; }
    public void setAttFileNoMain(String attFileNoMain) { this.attFileNoMain = attFileNoMain; }

    public String getAttFileNoMk() { return attFileNoMk; }
    public void setAttFileNoMk(String attFileNoMk) { this.attFileNoMk = attFileNoMk; }

    public String getRcpPartsDtls() { return rcpPartsDtls; }
    public void setRcpPartsDtls(String rcpPartsDtls) { this.rcpPartsDtls = rcpPartsDtls; }

    public List<String> getManual() { return manual; }
    public void setManual(List<String> manual) { this.manual = manual; }

    public List<String> getManualImg() { return manualImg; }
    public void setManualImg(List<String> manualImg) { this.manualImg = manualImg; }

    public String getRcpNaTip() { return rcpNaTip; }
    public void setRcpNaTip(String rcpNaTip) { this.rcpNaTip = rcpNaTip; }
}
