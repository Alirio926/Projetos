package br.com.estoque.entity;

import br.com.estoque.entity.RoloEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-10T12:24:46")
@StaticMetamodel(RemessaEntity.class)
public class RemessaEntity_ { 

    public static volatile SingularAttribute<RemessaEntity, Integer> qtdRolos;
    public static volatile SingularAttribute<RemessaEntity, String> motivo;
    public static volatile SingularAttribute<RemessaEntity, RoloEntity> re;
    public static volatile SetAttribute<RemessaEntity, RoloEntity> rolo;
    public static volatile SingularAttribute<RemessaEntity, Date> dataRetorno;
    public static volatile SingularAttribute<RemessaEntity, Integer> qtdRolosPendentes;
    public static volatile SingularAttribute<RemessaEntity, Long> id;
    public static volatile SingularAttribute<RemessaEntity, String> destino;
    public static volatile SingularAttribute<RemessaEntity, Long> numRemessa;
    public static volatile SingularAttribute<RemessaEntity, Integer> numNotaFiscal;
    public static volatile SingularAttribute<RemessaEntity, Date> dataEnvio;

}