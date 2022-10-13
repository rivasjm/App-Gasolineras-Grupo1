package es.unican.is.appgasolineras.activities.filtrar;

public interface IFiltrarPorPrecioContract {
    public interface Presenter {
        public void init();
        public void estableceRango(String max);
        public String subePrecio(String actual);
        public String bajaPrecio(String actual);

    }
    public interface View {
    }
}
