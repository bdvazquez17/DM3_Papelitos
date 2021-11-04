package com.esei.dm.papelitos;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper {
    public static final String db_name = "papelitos";
    public static final int db_version = 1;
    public static final String tabla_jugador = "jugador";
    public static final String tabla_equipo = "equipo";
    public static final String tabla_puntuacion = "puntuacion";

    /*Campos de tabla JUGADOR*/
    public static String JUGADOR_id = "id_jugador";
    public static String JUGADOR_nombre = "nombre";

    /*Campos tabla EQUIPO, existe relacion 1 a n entre equipo y jugadores*/
    public static String EQUIPO_id = "id_equipo";
    public static String JUGADOR_id_fk = "id_jugador";
    public static String EQUIPO_nombre = "equipo_nombre";

    /*Campos tabla PUNTUACION*/
    public static String EQUIPO_id_fk = "id_equipo";
    public static int puntuacion = 0;

    public DBManager(Context context){
        super(context, db_name, null, db_version);
    }
     @Override
    public void onCreate(SQLiteDatabase db){
        Log.i("DBManager", "Creando BBDD "+db_name+" v"+db_version);

        /*Creacion tabla Jugador*/
        try{
            db.beginTransaction();
            db.execSQL("CREATE TABLE IF NOT EXISTS " + tabla_jugador + "("
                    + JUGADOR_id + " INTEGER PRIMARY KEY,"
                    + JUGADOR_nombre + " TEXT NOT NULL"
                    + ")"
            );
        }catch (SQLException e){
            Log.e("DBManager.onCreate", "Creando "+tabla_jugador+": "+e.getMessage());
        }finally {
            db.endTransaction();
        }

        /*Creacion tabla Equipo*/
         try{
             db.beginTransaction();
             db.execSQL("CREATE TABLE IF NOT EXISTS " + tabla_equipo + "("
                     + EQUIPO_id + " INTEGER NOT NULL,"
                     + JUGADOR_id_fk  + " INTEGER NOT NULL,"
                     + EQUIPO_nombre + " TEXT NOT NULL,"
                     + "PRIMARY KEY (EQUIPO_id,JUGADOR_id_fk)"
                     + ")"
             );
         }catch (SQLException e){
             Log.e("DBManager.onCreate", "Creando "+tabla_equipo+": "+e.getMessage());
         }finally {
             db.endTransaction();
         }

         /*Creacion tabla Puntuacion*/
         try{
             db.beginTransaction();
             db.execSQL("CREATE TABLE IF NOT EXISTS " + tabla_equipo + "("
                     + EQUIPO_id + " INTEGER NOT NULL,"
                     + JUGADOR_id_fk  + " INTEGER NOT NULL,"
                     + EQUIPO_nombre + " TEXT NOT NULL,"
                     + "PRIMARY KEY (EQUIPO_id,JUGADOR_id_fk)"
                     + ")"
             );
         }catch (SQLException e){
             Log.e("DBManager.onCreate", "Creando "+tabla_equipo+": "+e.getMessage());
         }finally {
             db.endTransaction();
         }
     }


}
