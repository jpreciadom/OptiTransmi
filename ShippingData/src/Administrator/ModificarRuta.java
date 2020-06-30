package Administrator;

import  Base.BasePackage;

public class ModificarRuta extends BasePackage{
    private final String codigo_ruta;
    private final String dia;
    private final String inicio;
    private final String fin;

    public ModificarRuta(int idRequest, String codigo_ruta, String dia, String inicio, String fin) {
        super(2, idRequest);
        this.codigo_ruta = codigo_ruta;
        this.dia = dia;
        this.inicio = inicio;
        this.fin = fin;
    }

    public String getCodigo_ruta() {
        return codigo_ruta;
    }

    public String getDia() {
        return dia;
    }

    public String getInicio() {
        return inicio;
    }

    public String getFin() {
        return fin;
    }
}

