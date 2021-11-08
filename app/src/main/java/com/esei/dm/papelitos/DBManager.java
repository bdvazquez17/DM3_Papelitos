package com.esei.dm.papelitos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    public static String puntuacion = "punt";

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
                    + JUGADOR_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + JUGADOR_nombre + " TEXT NOT NULL"
                    + ")"
            );
            db.setTransactionSuccessful();
        }catch (SQLException exec){
            Log.e("DBManager.onCreate", "Creando "+tabla_jugador+": "+exec.getMessage());
        }finally {
            db.endTransaction();
        }

        /*Creacion tabla Equipo*/
         try{
             db.beginTransaction();
             db.execSQL("CREATE TABLE IF NOT EXISTS " + tabla_equipo + "("
                     + EQUIPO_id + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                     + EQUIPO_nombre + " TEXT NOT NULL,"
                     + JUGADOR_id_fk +" INTEGER NOT NULL , FOREIGN KEY ("+JUGADOR_id_fk+") REFERENCES " + tabla_jugador + "("+JUGADOR_id+")"
                     + ")"
             );
             db.setTransactionSuccessful();
         }catch (SQLException exec){
             Log.e("DBManager.onCreate", "Creando "+tabla_equipo+": "+exec.getMessage());
         }finally {
             db.endTransaction();
         }

         /*Creacion tabla Puntuacion*/
         try{
             db.beginTransaction();
             db.execSQL("CREATE TABLE IF NOT EXISTS " + tabla_puntuacion + "("
                     + puntuacion + " INTEGER NOT NULL,"
                     + EQUIPO_id_fk +" INTEGER NOT NULL, FOREIGN KEY ("+EQUIPO_id_fk+") REFERENCES " + tabla_equipo + "("+EQUIPO_id+")"
                     + ")"
             );
             db.setTransactionSuccessful();
         }catch (SQLException exec){
             Log.e("DBManager.onCreate", "Creando "+tabla_puntuacion+": "+exec.getMessage());
         }finally {
             db.endTransaction();
         }
     }

    public void onUpgrade(SQLiteDatabase db, int v1, int v2){
        Log.i("DBManager", "DB: "+db_name+":v "+v1+ " -> "+v2);
        try{
            db.beginTransaction();
            db.execSQL("DROP TABLE IF EXISTS " + tabla_jugador);
            db.setTransactionSuccessful();
        }catch(SQLException exec){
            Log.e("DBManager.onUpgrade", exec.getMessage());
        }finally {
            db.endTransaction();
        }
    }

    public boolean insertarJugador(String nombre){ //registrar jugador
        boolean toret = false;
        Cursor cursor = null;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(JUGADOR_nombre, nombre);

        try{
            db.beginTransaction();
            cursor = db.query(tabla_jugador, null, JUGADOR_nombre + "=?", new String[]{nombre}, null, null, null, null); //Se busca si existe ya ese jugador
            if(cursor.getCount() > 0){
                db.update(tabla_jugador, values, JUGADOR_nombre + "=?", new String[]{nombre}); //Si existe, se actualiza el registro en vez de insertar
            }
            else{
                db.insert(tabla_jugador, null, values); // Si no existe, se inserta simplemente
            }
            db.setTransactionSuccessful();
            toret = true;
        }catch(SQLException exc){
            Log.e("DBManager.insertarJugador", exc.getMessage());
        }
        finally {
            if(cursor != null){
                cursor.close();
            }
            db.endTransaction();
        }
        return toret;
    }

    public boolean insertarEquipo(String nombre_equipo){
        boolean toret = false;
        Cursor cursor = null;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =  new ContentValues();

        values.put(EQUIPO_nombre, nombre_equipo);

        try{
            db.beginTransaction();
            cursor = db.query(tabla_equipo, null, EQUIPO_nombre + "=?", new String[]{nombre_equipo}, null, null, null, null);
            if(cursor.getCount() > 0){
                db.update(tabla_equipo, values, EQUIPO_nombre + "=?", new String[]{nombre_equipo});
            }
            else{
                db.insert(tabla_equipo, null, values);
            }
            db.setTransactionSuccessful();
            toret = true;
        }catch (SQLException exc){
            Log.e("DBManager.insertarEquipo", exc.getMessage());
        }
        finally {
            if(cursor != null){
                cursor.close();
            }
            db.endTransaction();
        }
        return toret;
    }

    public boolean asignarJugador_Equipo(String nombre, String nombre_equipo){
        boolean toret = false;
        Cursor cursor = null;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        return toret;
    }


}
