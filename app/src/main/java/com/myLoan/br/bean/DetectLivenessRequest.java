package com.myLoan.br.bean;

public class DetectLivenessRequest {
    String verificationPackage = "";
    String verificationPackageFull = "";
    String verificationPackageWithFanpaiFull = "";

    public String getVerificationPackage() {
        return verificationPackage;
    }

    public void setVerificationPackage(String verificationPackage) {
        this.verificationPackage = verificationPackage;
    }

    public String getVerificationPackageFull() {
        return verificationPackageFull;
    }

    public void setVerificationPackageFull(String verificationPackageFull) {
        this.verificationPackageFull = verificationPackageFull;
    }

    public String getVerificationPackageWithFanpaiFull() {
        return verificationPackageWithFanpaiFull;
    }

    public void setVerificationPackageWithFanpaiFull(String verificationPackageWithFanpaiFull) {
        this.verificationPackageWithFanpaiFull = verificationPackageWithFanpaiFull;
    }
}
