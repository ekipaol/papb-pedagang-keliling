package com.example.eki.pedagangkelilingpapb;

/**
 * Created by Eki on 10/12/2017.
 */


public class MyData {
    private int id;
    private String description, image_link, jarak;

    public MyData(int id, String description, String image_link, String jarak) {
        this.id = id;
        this.description = description;
        this.image_link = image_link;
        this.jarak = jarak;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }
}
