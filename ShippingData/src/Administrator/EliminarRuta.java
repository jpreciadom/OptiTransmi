package Administrator;

import Base.BasePackage;

public class EliminarRuta extends BasePackage{
    private final String codigo_ruta;
    private final String dia;

    public EliminarRuta(int idRequest, String codigo_ruta, String dia) {
        super(2, idRequest);
        this.codigo_ruta = codigo_ruta;
        this.dia = dia;

    }

    public String getCodigo_ruta() {
        return codigo_ruta;
    }

    public String getDia() {
        return dia;
    }


}
