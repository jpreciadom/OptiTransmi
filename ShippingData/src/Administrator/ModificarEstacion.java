package Administrator;

import Base.BasePackage;

public class ModificarEstacion extends BasePackage {
    private final String nombre;
    private final String direccion;
    private final String zona;
    private final int nVagones;

    public ModificarEstacion(int idRequest, String nombre, String direccion, String zona, int nVagones) {
        super(2, idRequest);
        this.nombre = nombre;
        this.direccion = direccion;
        this.zona = zona;
        this.nVagones = nVagones;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getZona() {
        return zona;
    }

    public int getnVagones() {
        return nVagones;
    }
}
