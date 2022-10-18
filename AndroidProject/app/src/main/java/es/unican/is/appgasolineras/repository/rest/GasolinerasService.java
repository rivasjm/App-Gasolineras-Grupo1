package es.unican.is.appgasolineras.repository.rest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import es.unican.is.appgasolineras.common.Callback;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.model.GasolinerasResponse;
import es.unican.is.appgasolineras.model.IDCCAAs;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class to access the gas stations REST API using Retrofit
 * Usage: https://square.github.io/retrofit/
 */
public class GasolinerasService {

    public static long TIMEOUT_SECONDS = 60L;

    private static GasolinerasAPI api;

    private static GasolinerasAPI getAPI() {
        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GasolinerasServiceConstants.getAPIURL())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api = retrofit.create(GasolinerasAPI.class);
        }
        return api;
    }

    /**
     * Download gas stations located in Cantabria from the REST API asynchronously
     * @param cb the callback that processes the response asynchronously
     */
    public static void requestGasolineras(Callback<GasolinerasResponse> cb) {
        final Call<GasolinerasResponse> call = getAPI().gasolineras(IDCCAAs.CANTABRIA.id);
        call.enqueue(new CallbackAdapter(cb));
    }

    /**
     * Download gas stations located in a autonomic comunity from the REST API synchronously
     * @return the response object that contains the gasolineras located in the autonomic comunity
     */
    public static GasolinerasResponse getGasolineras(String id) {
        final Call<GasolinerasResponse> call = getAPI().gasolineras(id);

        List<Gasolinera> gasolineras;
        ExecutorService executor = Executors.newFixedThreadPool(1);
        CallRunnable<GasolinerasResponse> runnable = new CallRunnable<>(call);
        executor.execute(runnable);

        // wait until background task finishes
        executor.shutdown();
        try {
            executor.awaitTermination(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {}

        // if there was some problem, response is null
        GasolinerasResponse response = runnable.response;
        return response;
    }

    /**
     * Download gas stations located in Spain from the REST API synchronously
     * @return the response object that contains the gasolineras located in Spain
     */
    public static GasolinerasResponse todasGasolineras() {
        final Call<GasolinerasResponse> call = getAPI().todasGasolineras();

        List<Gasolinera> gasolineras;
        ExecutorService executor = Executors.newFixedThreadPool(1);
        CallRunnable<GasolinerasResponse> runnable = new CallRunnable<>(call);
        executor.execute(runnable);

        // wait until background task finishes
        executor.shutdown();
        try {
            executor.awaitTermination(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {}

        // if there was some problem, response is null
        GasolinerasResponse response = runnable.response;
        return response;
    }

}
