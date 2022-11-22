package org.jaguirre.springcloud.msvc.usuarios.models.dto;

public class Item {
    private String name;
    private Detail detail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }
}
