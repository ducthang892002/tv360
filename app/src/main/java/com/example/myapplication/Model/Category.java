package com.example.myapplication.Model;

import java.util.List;

public class Category {
    String id;
    String name,description,slug,type,itemType;
    private List<Product> content;

    public Category(String id, String name, String description, String slug, String type, String itemType, List<Product> content) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.type = type;
        this.itemType = itemType;
        this.content = content;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public List<Product> getContent() {
        return content;
    }

    public void setContent(List<Product> content) {
        this.content = content;
    }
}
