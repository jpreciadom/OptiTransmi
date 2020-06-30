package Administrator;

import Base.BasePackage;

public class EliminarEstacion extends BasePackage {
    private final String nombre;

    public EliminarEstacion(int idRequest, String nombre) {
        super(2, idRequest);
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
