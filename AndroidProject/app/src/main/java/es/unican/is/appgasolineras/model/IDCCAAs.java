package es.unican.is.appgasolineras.model;

/**
 * Static collection of Comunidades Autonomas ID's, as used by the RESt API
 * Alternatively, these ID's can also be fetched from the REST API itself,
 * using this endpoint: https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/Listados/ComunidadesAutonomas/
 */
public enum IDCCAAs {
    TODAS(""),
    ANDALUCIA("01"),
    ARAGON("02"),
    ASTURIAS("03"),
    BALEARES("04"),
    CANARIAS("05"),
    CANTABRIA("06"),
    CASTILLA_LA_MANCHA("07"),
    CASTILLA_Y_LEON("08"),
    CATALUNYA("09"),
    COMUNIDAD_VALENCIANA("10"),
    EXTREMADURA("11"),
    GALICIA("12"),
    MADRID("13"),
    MURCIA("14"),
    NAVARRA("15"),
    PAIS_VASCO("16"),
    RIOJA("17"),
    CEUTA("18"),
    MELILLA("19");



    public final String id;

    private IDCCAAs(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public static IDCCAAs getEnumByString(String code){
        for(IDCCAAs e : IDCCAAs.values()){
            if(e.name().equals(code)) return e ;
        }
        return null;
    }

}
