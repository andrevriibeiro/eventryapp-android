package br.com.eventryapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Presente implements Parcelable {

    private Integer id;
    private String nomepresente;
    private String descricao;
    private boolean confirmado;

    public Presente(){

    }

    public Presente(Parcel in) {
        readFromParcel(in);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomepresente() {
        return nomepresente;
    }

    public void setNomepresente(String nomepresente) {
        this.nomepresente = nomepresente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nomepresente);
        dest.writeString(descricao);
        dest.writeInt(confirmado ? 1 : 0);
    }

    public static final Creator CREATOR = new Creator<Presente>() {
        @Override
        public Presente createFromParcel(Parcel source) {
            return new Presente(source);
        }

        @Override
        public Presente[] newArray(int size) {
            return new Presente[size];
        }
    };

    public void readFromParcel(Parcel in) {
        id = in.readInt();
        nomepresente = in.readString();
        descricao = in.readString();
        confirmado = in.readInt() == 1;
    }
}
