package Request;

import Base.BasePackage;

public class RutaListAnswer extends BasePackage {
    private final String codigo;
    private final String dia;
    private final String inicio;
    private final String fin;

    public RutaListAnswer(int idRequest, String codigo, String dia, String inicio, String fin){
        super(codigo == null ? 2 : 1, idRequest);
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
