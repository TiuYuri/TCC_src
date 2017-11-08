package com.tiyuri.periocularrecognition.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.lang.Object;

public class UserDAO extends SQLiteOpenHelper {

    public UserDAO(Context context) {
        super(context, "Users", null, 1);
    }

    /*
    * Função: onCreate;
    * Descrição: Cria um novo banco de dados no celular caso não exista;
    * Entrada:
    *   db: o banco de dados;
    * Saida: ---;
    *
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Users " +
                "(id INTEGER PRIMARY KEY," +
                " name TEXT," +
                " histogram1 TEXT," +
                " histogram2 TEXT," +
                " histogram3 TEXT)";
        db.execSQL(sql);

    }

    /*
    * Função: onUpgrade;
    * Descrição: Funcão para caso o banco de dados mude;
    * ATENÇÃO: é obrigatorio a existencia dessa função;
    * Entrada:
    *   db: o banco de dados modificado;
    *   i: a versão do banco de dados;
    * Saida: ---;
    *
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXIST Users;";
        db.execSQL(sql);
        onCreate(db);
    }

    /*
    * Função: insert;
    * Descrição: Insere um user no banco de dados;
    * Entrada:
    *   user: Um User;
    * Saida: ---;
    *
     */

    public void insert(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("name", user.getName());
        dados.put("histogram1", user.getPhoto1Lbp());
        dados.put("histogram2", user.getPhoto2Lbp());
        dados.put("histogram3", user.getPhoto3Lbp());

        db.insert("Users", null, dados);
    }


    /*
    * Função: getAllUsers;
    * Descrição: Retorna uma lista com todos os user contidos no banco de dados;
    * Entrada: ---;
    * Saida:
    *   users: Uma List de user contendo todos os user do banco de dados;
    *
     */

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM Users;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<User> users = new ArrayList<User>();
        while(c.moveToNext()){
            User user = new User();
            user.set_id(c.getLong(c.getColumnIndex("id")));
            user.setName(c.getString(c.getColumnIndex("name")));
            user.setPhoto1Lbp(c.getString(c.getColumnIndex("histogram1")));
            user.setPhoto2Lbp(c.getString(c.getColumnIndex("histogram2")));
            user.setPhoto3Lbp(c.getString(c.getColumnIndex("histogram3")));

            users.add(user);
        }
        c.close();

        return users;
    }


    /*
    * Função: delete;
    * Descrição: Deleta um user do banco de dados;
    * Entrada:
    *   user: Um User existente no banco de dados;
    * Saida: ---;
    *
     */

    public void delete(User user) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {Long.toString(user.get_id())};
        db.delete("Users", "id = ?", params);
    }
}
