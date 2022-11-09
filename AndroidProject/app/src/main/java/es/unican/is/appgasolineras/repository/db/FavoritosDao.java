package es.unican.is.appgasolineras.repository.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.unican.is.appgasolineras.model.Gasolinera;

@Dao
public interface FavoritosDao {
    @Query("SELECT * FROM gasolineras")
    List<Gasolinera> getAll();

    @Insert
    void insertAll(Gasolinera... gasolineras);

    @Query("DELETE FROM gasolineras")
    void deleteAll();
}
