package es.unican.is.appgasolineras.activities.filtrosPermanentes;

public interface IPermanenteContract {

    public interface presenter {
        /**
         * Initialization method
         */
        void init();

        /**
         * This method stores the filters
         * @param idComunidad position at the spinner of the CCAA
         * @param tipoGasolina position at the spinner of the fuel
         */
        void guardaFiltroPermanente(int idComunidad, int tipoGasolina);

        /**
         * This method resets the filters
         */
        void reseteaFiltroPermanente();

    }


    public interface view{
        /**
         * Initialization method
         */
        void init();
    }

}
