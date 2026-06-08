package ConstruccionSwing.InicioSesion;

public class ComboItem {
    private final String key;
    private final String label;

    public ComboItem(String key, String label) {
        this.key = key;
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
