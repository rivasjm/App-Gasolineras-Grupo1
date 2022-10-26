package es.unican.is.appgasolineras.activities.filtrar;

import java.util.ArrayList;
import java.util.List;

public class FiltroMarcaMapper {
    private List<String> marcas;
    public FiltroMarcaMapper() {
        marcas = new ArrayList<>();
        marcas.add("");
        marcas.add("AVIA");
        marcas.add("CAMPSA");
        marcas.add("CARREFOUR");
        marcas.add("CEPSA");
        marcas.add("GALP");
        marcas.add("PETRONOR");
        marcas.add("REPSOL");
        marcas.add("SHELL");

    }
    public String getMarca(int index){
        return marcas.get(index);
    }

    public int getMarcaIndex(String marca){
        return marcas.indexOf(marca);
    }
}
