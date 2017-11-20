package com.freerschool.report_it_fix_it;

/**
 * Created by dfreer on 11/20/2017.
 */

public class ThingsToFix {
    private int things_id, userId, locationId;
    private String image, description;
    private boolean fixed;

    public ThingsToFix(){
        //empty constructor!
    }

    public ThingsToFix(int things_id, int userId, int locationId, String image, String description, boolean fixed) {
        this.things_id = things_id;
        this.userId = userId;
        this.locationId = locationId;
        this.image = image;
        this.description = description;
        this.fixed = fixed;
    }

    public int getThings_id() {
        return things_id;
    }

    public void setThings_id(int things_id) {
        this.things_id = things_id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }
}
