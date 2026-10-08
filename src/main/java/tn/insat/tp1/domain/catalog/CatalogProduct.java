package tn.insat.tp1.domain.catalog;

/** Feuille du Composite, distincte du Product utilise par la Factory. */
public class CatalogProduct implements CatalogComponent {
    private final String name;

    public CatalogProduct(String name) {
        this.name = name;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "- " + name);
    }
}
