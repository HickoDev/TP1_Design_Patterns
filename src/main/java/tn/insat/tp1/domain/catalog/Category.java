package tn.insat.tp1.domain.catalog;

import java.util.ArrayList;
import java.util.List;

public class Category implements CatalogComponent {
    private final String name;
    private final List<CatalogComponent> children = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public void add(CatalogComponent component) {
        children.add(component);
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + name);
        for (CatalogComponent child : children) {
            child.display(indent + "  ");
        }
    }
}
