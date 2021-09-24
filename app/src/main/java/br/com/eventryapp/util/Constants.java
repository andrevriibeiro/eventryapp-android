package br.com.eventryapp.util;

public class Constants {

    public final static String URL = "https://apieventryapp.herokuapp.com/eventryapp/";
    //public final static String URL = "http://localhost:8080/eventryapp/";

    //Rest para usuario
    public final static String LOGIN = URL + "user/login";
    public final static String CRIAR_USUARIO = URL + "user/createUser";

    //Rest para eventos
    public final static String CRIAR_EVENTO = URL + "evento/createEvento";
    public final static String EDITAR_EVENTO = URL + "evento/updateEvento";
    public final static String CRIAR_EVENTO_PRESENTE = URL + "evento/createEventoPresente";
    public final static String PRESENTE_BY_EVENTO = URL + "evento/getAllPresentesByEvento";
    public final static String CONVIDADO_BY_EVENTO = URL + "evento/getAllConvidadosByEvento";
    public final static String GET_EVENTOS_USUARIO = URL + "evento/findEventyByOrganizador";
    public final static String DELETAR_EVENTO = URL + "evento/deletarEvento";
    public final static String DELETAR_EVENTO_PRESENTE = URL + "evento/deletarEventoPresente";


    // SQLITe COLUMN NAME
    public static final String ID_COLUMN = "id";
    public static final String NOME_COLUMN = "nome";
    public static final String EMAIL_COLUMN = "email";
    public static final String SENHA_COLUMN = "senha";

}