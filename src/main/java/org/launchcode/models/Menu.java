package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Menu {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15)
    private String name;

    @ManyToMany
    private List<Cheese> cheeses = new ArrayList<>();

    public Menu() {

    }

    public Menu(String name) {
        this.name = name;
    }

    /*public void removeCheese(int cheeseId) {
        for (Cheese cheese : this.cheeses) {
            if (cheese.getId() == cheeseId) {
                cheeses.remove(cheese);
                break;
            }
        }
    }*/

    public void addItem(Cheese item){
        cheeses.add(item);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<Cheese> getCheeses() {
        return cheeses;
    }

    public void setCheeses(List<Cheese> cheeses) {
        this.cheeses = cheeses;
    }

    public void setName(String name) {
        this.name = name;
    }
}
