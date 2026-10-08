package tn.insat.tp1.domain.catalog;

/** Feuille du Composite, distincte du Product utilise par la Factory. */
public final class CatalogProduct implements CatalogComponent {
    private final String name;

    public CatalogProduct(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Catalog product name must not be blank");
        }
        this.name = name;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "- " + name);
    }
}
