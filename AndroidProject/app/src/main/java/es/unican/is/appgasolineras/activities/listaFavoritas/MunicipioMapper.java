package es.unican.is.appgasolineras.activities.listaFavoritas;

import java.util.List;

import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.GasolinerasRepository;

public class MunicipioMapper {
    public static List<Gasolinera> getGasolinerasMunicipio (String idMun, GasolinerasRepository rep) {
        return rep.gasolinerasMunicipio(idMun);
    }
}
