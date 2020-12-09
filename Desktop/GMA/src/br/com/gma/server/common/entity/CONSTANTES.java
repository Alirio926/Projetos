/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.common.entity;

/**
 *
 * @author Administrador
 */
public abstract class CONSTANTES {
    public static final Integer ERRO        = 0;
    public static final Integer INFORMACAO  = 1;
    public static final Integer PERGUNTA    = 2;
    public static final Integer UPDATE      = 3;
    public static final Integer EXPEDICAO_AND_MOAGEM   = 0;
    public static final Integer EXPEDICAO_ONLY   = 1;
    public static final int INDUSTRIAL     = 0;
    public static final int DOMESTICA      = 1;
    public static final int BIG_BAG        = 3;
    public static final int BIG_BAG_SMALL  = 4;
    public static final int FARELO         = 2;
    public static final String SERVIDOR_IP     = "servidor_ip";
    public static final String SERVIDOR_PORT   = "servidor_port";
    public static final String RMI             = "rmi://";
    public static final String PEDIDO_URL      = "pedido_url";
    public static final String PRODUTO_URL     = "produto_url";
    public static final String USUARIO_URL     = "usuario_url";
    public static final String SERVIDOR_URL    = "servidor_url";
    public static final String CARGO_URL       = "cargo_url";
    public static final String VERSION_URL     = "versao_url";
    public static final String CLIENTE_URL     = "cliente_url";
    public static final String AUTO_CONNECT    = "auto_connect";
    public static final String URL_CARREGAMENTO        = "jrxml_local_0";
    public static final String URL_MOAGEM              = "jrxml_local_1";
    public static final String URL_COMERCIAL           = "jrxml_local_2";
    public static final String URL_PRODUCAO            = "jrxml_local_3";
    public static final int CARREGAMENTO_BUSY  = 0;
    public static final int CARREGAMENTO_ERRO  = 1;
    public static final int CARREGAMENTO_EXCE  = 2;
    public static final int CARREGAMENTO_OK    = 3;
    public static final String FILE_UPDATE_ORIG = "d:\\gmacliente\\gmaupdate.jar";
    public static final String FILE_UPDATE_DEST = "d:\\gmacliente\\update\\GMA.jar";
    public static final String FILE_INFO_DEST   = "d:\\gmacliente\\update.properties";
    public static final String FILE_INFO_ORIG   = "d:\\gma\\update.properties";
    
}

