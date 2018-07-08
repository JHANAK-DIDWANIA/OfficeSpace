package com.jhanakdidwania.officespace;

import org.xmlpull.v1.XmlPullParser;

import java.io.Serializable;

public class Features implements Serializable{
    public String title;
    public String name;
    public String description;
    public String UID;
    public int val;
    public int RANGE_MIN;
    public int RANGE_MAX;
    public int VER_BASIC;
    public int VER_SILVER;
    public int VER_GOLD;
    public int VER_PLATINUM;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public void setRANGE_MIN(int RANGE_MIN) {
        this.RANGE_MIN = RANGE_MIN;
    }

    public void setRANGE_MAX(int RANGE_MAX) {
        this.RANGE_MAX = RANGE_MAX;
    }

    public void setVER_BASIC(int VER_BASIC) {
        this.VER_BASIC = VER_BASIC;
    }

    public void setVER_SILVER(int VER_SILVER) {
        this.VER_SILVER = VER_SILVER;
    }

    public void setVER_GOLD(int VER_GOLD) {
        this.VER_GOLD = VER_GOLD;
    }

    public void setVER_PLATINUM(int VER_PLATINUM) {
        this.VER_PLATINUM = VER_PLATINUM;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUID() {
        return UID;
    }

    public int getVal() {
        return val;
    }

    public int getRANGE_MIN() {
        return RANGE_MIN;
    }

    public int getRANGE_MAX() {
        return RANGE_MAX;
    }

    public int getVER_BASIC() {
        return VER_BASIC;
    }

    public int getVER_SILVER() {
        return VER_SILVER;
    }

    public int getVER_GOLD() {
        return VER_GOLD;
    }

    public int getVER_PLATINUM() {
        return VER_PLATINUM;
    }


}
