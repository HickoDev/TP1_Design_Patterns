package tn.insat.tp1.infrastructure.config;

/** L'initialisation statique garantit la publication de l'instance unique. */
public final class ApplicationConfig {
    private static final ApplicationConfig INSTANCE = new ApplicationConfig();
    private String databaseUrl = "jdbc:demo:shop";
    private String applicationName = "TP1 Design Patterns";

    private ApplicationConfig() { }

    public static ApplicationConfig getInstance() { return INSTANCE; }

    public String getDatabaseUrl() { return databaseUrl; }
    public String getApplicationName() { return applicationName; }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = requireText(databaseUrl);
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = requireText(applicationName);
    }

    private static String requireText(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Configuration value must not be blank");
        }
        return value;
    }
}
