package com.aprosoft.bfitgym;

import io.realm.RealmObject;

/**
 * Created by jasvi on 5/24/2018.
 */

public class WeighmeasurementObject extends RealmObject {

    String weight,neck,shoulder,chest,biceps,waist,hips,thighs,calves,date,duedate;

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getNeck() {
        return neck;
    }

    public void setNeck(String neck) {
        this.neck = neck;
    }

    public String getShoulder() {
        return shoulder;
    }

    public void setShoulder(String shoulder) {
        this.shoulder = shoulder;
    }

    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public String getBiceps() {
        return biceps;
    }

    public void setBiceps(String biceps) {
        this.biceps = biceps;
    }

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }

    public String getHips() {
        return hips;
    }

    public void setHips(String hips) {
        this.hips = hips;
    }

    public String getThighs() {
        return thighs;
    }

    public void setThighs(String thighs) {
        this.thighs = thighs;
    }

    public String getCalves() {
        return calves;
    }

    public void setCalves(String calves) {
        this.calves = calves;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }
}
