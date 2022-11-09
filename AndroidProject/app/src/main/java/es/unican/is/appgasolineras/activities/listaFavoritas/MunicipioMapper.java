package es.unican.is.appgasolineras.activities.listaFavoritas;

import java.util.List;
import java.util.Map;

import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.GasolinerasRepository;
import es.unican.is.appgasolineras.repository.IGasolinerasRepository;

public class MunicipioMapper {
    public static List<Gasolinera> getGasolinerasMunicipio (String idMun, IGasolinerasRepository rep) {
        return rep.gasolinerasMunicipio(idMun);
    }

}
