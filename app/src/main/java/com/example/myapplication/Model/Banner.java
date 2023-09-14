package com.example.myapplication.Model;

public class Banner {
    private   String id;
    private   String name;
    private  String description;
    private  String slug;
    private  String durationStr;
    private  String resolution;
    private String coverImage;
    private String type;
    private  String animationImage;


    public Banner(String id, String name, String description, String slug, String durationStr, String resolution, String coverImage, String type, String animationImage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.durationStr = durationStr;
        this.resolution = resolution;
        this.coverImage = coverImage;
        this.type = type;
        this.animationImage = animationImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDurationStr() {
        return durationStr;
    }

    public void setDurationStr(String durationStr) {
        this.durationStr = durationStr;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnimationImage() {
        return animationImage;
    }

    public void setAnimationImage(String animationImage) {
        this.animationImage = animationImage;
    }
}
