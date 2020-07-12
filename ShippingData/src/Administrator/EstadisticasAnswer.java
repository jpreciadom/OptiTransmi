package Administrator;

import Base.BasePackage;

public class EstadisticasAnswer extends BasePackage {
    private final String codigo;
    private final int cantidad;

    public EstadisticasAnswer(int idRequest, String codigo, int cantidad) {
        super(2, idRequest);
        this.codigo = codigo;
        this.cantidad = cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCantidad() {
        return cantidad;
    }
}
