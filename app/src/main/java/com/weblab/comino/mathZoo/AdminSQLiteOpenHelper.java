package com.weblab.comino.mathZoo;
/**Class to do connections and actions with the local database.
 * Name of the database is BD.
 * This class will have methods for updating, inserting and getting dates of the valoration table.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "BD";
    SQLiteDatabase BD;

    public AdminSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase BD) {
        BD.execSQL("create table valoration(name text, score int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Method: get data from the player with the highest score.
     * Return object of the valoration class
     */
    public Valoration getBestPlayer (){
        SQLiteDatabase BD = this.getWritableDatabase();
        Cursor cursor = BD.rawQuery(
                "select * from valoration order by score desc limit 1", null);
        Valoration valoration = null;
        if(cursor.moveToFirst()){
            valoration = new Valoration(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("score")));
        }
        BD.close();
        cursor.close();
        return valoration;
    }//end method getBestPlayer

    /**
     * Method: get data from the current player
     * @param name: is the name of the player
     * @return obj of the class valoration
     */
    public Valoration getDataPlayer (String name){
        SQLiteDatabase BD = this.getWritableDatabase();
        Cursor cursor = BD.rawQuery(
                "select * from valoration where name='"+name+"'", null);
        Valoration valoration = null;
        if(cursor.moveToFirst()){
            valoration = new Valoration(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("score")));
        }
        BD.close();
        cursor.close();
        return valoration;
    }//end method getDataPlayer

    /**
     * Method to check if the player exists in the database.
     * If it exists it returns true otherwise false.
     * @param name: name of the player.
     * @return boolean
     */
    public Boolean getExistScoreUser(String name){
        Boolean existUser = false;
        SQLiteDatabase BD = this.getWritableDatabase();
        Cursor cursor = BD.rawQuery(
                "select * from valoration where name='"+name+"'", null);
        if(cursor.moveToFirst()){
            existUser = true;
        }
        BD.close();
        cursor.close();
        return existUser;
    }//end method getExistScoreUser

    /**
     * Method to insert the user data in the valoration table.
     * We pass like a parameter an object of the valoration class which is made by the name of the player
     * and his punctuation because they are the necessary data to make
     * In the valoration table since it is composed by the same fields.
     *
     * @param valoration : object class valoration
     * @return boolean
     */
    public Boolean InsertPoints(Valoration valoration){
        SQLiteDatabase BD = this.getWritableDatabase();
        ContentValues registro = new ContentValues();

        registro.put("name", valoration.getName());
        registro.put("score", valoration.getScore());

        long result = BD.insert("valoration", null, registro);
        BD.close();
        if (result == -1) return false;
        else return true;
    }//end method InsertPoints

    /**
     * Method to update the user valoration.
     * We update the field of the valoration table. We pass as a parameter the valoration object
     * which contains the user name data and his valoration .
     * @param valoration : is the object of the valoration class
     * @return boolean
     */
    public Boolean UpdatePoints(Valoration valoration){
        SQLiteDatabase BD = this.getWritableDatabase();
        ContentValues registro = new ContentValues();

        registro.put("name", valoration.getName());
        registro.put("score", valoration.getScore());

        long result = BD.update("valoration", registro,"name='"+ valoration.getName()+"'", null);
        BD.close();
        if (result == -1) return false;
        else return true;
    }//end mehtod UpdatePoints
}
