package es.unican.is.appgasolineras.activities.filtrar;

import java.util.ArrayList;
import java.util.List;

import es.unican.is.appgasolineras.model.IDCCAAs;

public class FiltroMarcaMapper {
    private List<String> marcas;
    public FiltroMarcaMapper() {
        marcas = new ArrayList<String>();
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
    public String getMarca(int i){
        return marcas.get(i);
    }
    public int getMarcaIndex(String s){
        return marcas.indexOf(s);
    }
}
