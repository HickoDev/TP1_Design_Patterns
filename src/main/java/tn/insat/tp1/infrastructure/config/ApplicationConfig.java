package tn.insat.tp1.infrastructure.config;

/** L'initialisation statique garantit la publication de l'instance unique. */
public final class ApplicationConfig {
    private static final ApplicationConfig INSTANCE = new ApplicationConfig();
    private String databaseUrl;
    private String applicationName;

    private ApplicationConfig() { }

    public static ApplicationConfig getInstance() { return INSTANCE; }

    public String getDatabaseUrl() { return databaseUrl; }
    public String getApplicationName() { return applicationName; }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
