package Administrator;

import Base.BasePackage;

public class AddRuta extends BasePackage {
    private final String codigo;
    private final String dia;
    private final String inicio;
    private final String fin;

    public AddRuta(int idRequest, String codigo, String dia, String inicio, String fin) {
        super(2, idRequest);
        this.codigo = codigo;
        this.dia = dia;
        this.inicio = inicio;
        this.fin = fin;
    }

    public String getCodigo() {
        return codigo;
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
