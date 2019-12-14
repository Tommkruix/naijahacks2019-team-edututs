package com.codebrainz.campuschat;

/**
 * Created by Tommkruix on 12/11/2019.
 */

public class Bg_U_Model_OnlineTest {
    String title;
    String desc;
    int icon;

    //constructor

    public Bg_U_Model_OnlineTest(String title, String desc, int icon) {
        this.title = title;
        this.desc = desc;
        this.icon = icon;
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

    public int getIcon()
    {
        return this.icon;
    }
}
