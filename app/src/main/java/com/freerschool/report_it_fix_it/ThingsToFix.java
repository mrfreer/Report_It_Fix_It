package com.freerschool.report_it_fix_it;

/**
 * Created by dfreer on 11/20/2017.
 */

public class ThingsToFix {
    private int things_id;
    private String image, category, userName, location;
    private boolean fixed;

    public ThingsToFix(){
        //empty constructor!
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ThingsToFix(int things_id, String userName, String location, String image, String category, boolean fixed) {
        this.things_id = things_id;
        this.userName = userName;
        this.location = location;
        this.image = image;
        this.category = category;
        this.fixed = fixed;
    }

    public int getThings_id() {
        return things_id;
    }

    public void setThings_id(int things_id) {
        this.things_id = things_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }
}
