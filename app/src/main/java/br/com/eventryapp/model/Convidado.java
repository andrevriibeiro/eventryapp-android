package br.com.eventryapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Convidado implements Parcelable {

    private Integer id;
    private Usuario usuario;
    private String statusconfirmacao;

    public Convidado(){

    }

    public Convidado(Parcel in) {
        readFromParcel(in);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getStatusconfirmacao() {
        return statusconfirmacao;
    }

    public void setStatusconfirmacao(String statusconfirmacao) {
        this.statusconfirmacao = statusconfirmacao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeValue(usuario);
        dest.writeString(statusconfirmacao);
    }

    public static final Creator CREATOR = new Creator<Convidado>() {
        @Override
        public Convidado createFromParcel(Parcel source) {
            return new Convidado(source);
        }

        @Override
        public Convidado[] newArray(int size) {
            return new Convidado[size];
        }
    };

    public void readFromParcel(Parcel in) {
        id = in.readInt();
        this.usuario = (Usuario) in.readValue(Usuario.class.getClassLoader());
        statusconfirmacao = in.readString();
    }
}
