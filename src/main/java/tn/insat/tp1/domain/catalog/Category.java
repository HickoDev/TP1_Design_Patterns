package tn.insat.tp1.domain.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Category implements CatalogComponent {
    private final String name;
    private final List<CatalogComponent> children = new ArrayList<>();

    public Category(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Category name must not be blank");
        }
        this.name = name;
    }

    public void add(CatalogComponent component) {
        Objects.requireNonNull(component, "component");
        if (component == this || component instanceof Category category && category.contains(this)) {
            throw new IllegalArgumentException("A category cannot contain a cycle");
        }
        children.add(component);
    }

    public void remove(CatalogComponent component) { children.remove(component); }

    private boolean contains(Category target) {
        for (CatalogComponent child : children) {
            if (child == target || child instanceof Category category && category.contains(target)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + name);
        for (CatalogComponent child : children) {
            child.display(indent + "  ");
        }
    }
}
