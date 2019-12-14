package com.codebrainz.campuschat;

/**
 * Created by Tommkruix on 12/11/2019.
 */

public class Bg_Model_TourSchool {
    String title;
    String desc;
    String type;
    int icon;

    //constructor

    public Bg_Model_TourSchool(String title, String desc, String type, int icon) {
        this.title = title;
        this.desc = desc;
        this.icon = icon;
        this.type = type;
    }

    //getters


    public String getTitle()
    {
        return this.title;
    }

    public String getDesc()
    {

        return this.desc;
    }

    public String getType()
    {

        return this.type;
    }

    public int getIcon()
    {
        return this.icon;
    }
}
