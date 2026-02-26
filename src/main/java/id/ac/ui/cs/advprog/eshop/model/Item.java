package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Item {
    private String id;
    private String name;
    private int quantity;
}
