package es.unican.is.appgasolineras.activities.detail;

import es.unican.is.appgasolineras.model.Gasolinera;

public interface IDetailContract {
    public interface Presenter {
        /**
         * Initialization method
         */
        void init();

        /**
         * Calcula la media ponderada entre diesel y gasoliner
         */
        String calcula();

    }
    public interface View {
        void setInfo(String municipio, String rotulo, String horario, String normal95,
                     String dieselA, String media, String direccion);
    }
}
