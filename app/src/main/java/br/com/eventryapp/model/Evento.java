package br.com.eventryapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Evento implements Parcelable {

    private Integer id;
    private String eventonome;
    private String descricao;
    private String localizacao;
    private String codigoevento;
    private String dataevento;
    private String horaevento;
    private Usuario usuario;
    private List<Convidado> convidados = new ArrayList<>();
    private List<Presente> presentes = new ArrayList<>();

    public Evento(){}

    public Evento(Parcel in) {
        readFromParcel(in);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventonome() {
        return eventonome;
    }

    public void setEventonome(String eventonome) {
        this.eventonome = eventonome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Convidado> getConvidados() {
        return convidados;
    }

    public void setConvidados(List<Convidado> convidados) {
        this.convidados = convidados;
    }

    public List<Presente> getPresentes() {
        return presentes;
    }

    public void setPresentes(List<Presente> presentes) {
        this.presentes = presentes;
    }

    public String getCodigoevento() { return codigoevento; }

    public void setCodigoevento(String codigoevento) { this.codigoevento = codigoevento; }

    public String getDataevento() {
        return dataevento;
    }

    public void setDataevento(String dataevento) {
        this.dataevento = dataevento;
    }

    public String getHoraevento() {
        return horaevento;
    }

    public void setHoraevento(String horaevento) {
        this.horaevento = horaevento;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(eventonome);
        dest.writeString(descricao);
        dest.writeString(localizacao);
        dest.writeValue(usuario);
        dest.writeList(convidados);
        dest.writeList(presentes);
        dest.writeString(codigoevento);
        dest.writeString(dataevento);
        dest.writeString(horaevento);
    }

    public static final Creator CREATOR = new Creator<Evento>() {
        @Override
        public Evento createFromParcel(Parcel source) {
            return new Evento(source);
        }

        @Override
        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };

    public void readFromParcel(Parcel in) {
        id = in.readInt();
        eventonome = in.readString();
        descricao = in.readString();
        localizacao = in.readString();
        this.usuario = (Usuario) in.readValue(Usuario.class.getClassLoader());
        convidados = in.readArrayList(Convidado.class.getClassLoader());
        presentes = in.readArrayList(Presente.class.getClassLoader());
        codigoevento = in.readString();
        dataevento = in.readString();
        horaevento = in.readString();
    }
}
