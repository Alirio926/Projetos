package br.com.estoque.entity;

import br.com.estoque.entity.RoloEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-10T12:24:46")
@StaticMetamodel(InstallEntity.class)
public class InstallEntity_ { 

    public static volatile SingularAttribute<InstallEntity, String> diagrama;
    public static volatile SetAttribute<InstallEntity, RoloEntity> rolo;
    public static volatile SingularAttribute<InstallEntity, Long> horimetro;
    public static volatile SingularAttribute<InstallEntity, Long> id;
    public static volatile SingularAttribute<InstallEntity, String> tag;
    public static volatile SingularAttribute<InstallEntity, Date> dataInstalacao;

}