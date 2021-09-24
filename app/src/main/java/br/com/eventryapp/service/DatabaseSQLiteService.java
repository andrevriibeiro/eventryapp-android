package br.com.eventryapp.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.eventryapp.model.Usuario;
import br.com.eventryapp.util.Constants;

public class DatabaseSQLiteService extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String USUARIO_TABLE = "Usuario";

    private static final String DATABASE = "PerfilUsuario";

    /**
     * CREATE TABLE FOR USUARIO
     */
    private static final String SQL_CREATE_TABLE_USUARIO = "CREATE TABLE " + USUARIO_TABLE +
            " (" + Constants.ID_COLUMN + " INTEGER PRIMARY KEY, "
            + Constants.NOME_COLUMN + " TEXT UNIQUE NOT NULL, "
            + Constants.EMAIL_COLUMN + " TEXT, "
            + Constants.SENHA_COLUMN + " TEXT "
            + ")";

    /**
     * CONSTRUCTOR DatabaseSQLiteService
     * <p>
     * *
     */
    public DatabaseSQLiteService(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USUARIO);
    }

    /**
     * UPDATE TABALA USUARIO
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USUARIO_TABLE);
        onCreate(db);
    }

    public void deleteTabela(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + USUARIO_TABLE);
        onCreate(db);
    }

    /**
     * este metodo é responsavel por inserir os dados do usuario logado no Banco Interno
     */
    public void insertUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.ID_COLUMN, usuario.getId());
        values.put(Constants.NOME_COLUMN, usuario.getNome());
        values.put(Constants.EMAIL_COLUMN, usuario.getEmail());
        values.put(Constants.SENHA_COLUMN, usuario.getSenha());

        db.insert(USUARIO_TABLE, null, values);
    }

    /**
     * este metodo é responsavel por retornar os  dados do usuario logado
     */
    public Usuario getUsuario() {
        Usuario usuario = new Usuario();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + USUARIO_TABLE + ";", null);

        while (c.moveToNext()) {
            usuario.setId(c.getInt(c.getColumnIndex(Constants.ID_COLUMN)));
            usuario.setNome(c.getString(c.getColumnIndex(Constants.NOME_COLUMN)));
            usuario.setEmail(c.getString(c.getColumnIndex(Constants.EMAIL_COLUMN)));
            usuario.setSenha(c.getString(c.getColumnIndex(Constants.SENHA_COLUMN)));
        }

        return usuario;
    }
}
