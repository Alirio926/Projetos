/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estoque.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Alirio
 */

public class RelatorioDeRemessaEntity {
    private String remessa;
    private String notafiscal;
    private String passagem;
    private Date dataRemessa;
    private Integer qtd_rolos_pendentes;
    private Integer qtd_rolos_enviados;
    private String rol1, rol2, rol3, rol4, rol5, rol6, rol7, rol8, rol9, rol10,
                   rol11, rol12, rol13, rol14, rol15, rol16, rol17, rol18;

    public String getRemessa() {
        return remessa;
    }

    public void setRemessa(String remessa) {
        this.remessa = remessa;
    }

    public String getNotafiscal() {
        return notafiscal;
    }

    public void setNotafiscal(String notafiscal) {
        this.notafiscal = notafiscal;
    }

    public String getPassagem() {
        return passagem;
    }

    public void setPassagem(String passagem) {
        this.passagem = passagem;
    }

    public Date getDataRemessa() {
        return dataRemessa;
    }

    public void setDataRemessa(Date dataRemessa) {
        this.dataRemessa = dataRemessa;
    }

    public Integer getQtd_rolos_pendentes() {
        return qtd_rolos_pendentes;
    }

    public void setQtd_rolos_pendentes(Integer qtd_rolos_pendentes) {
        this.qtd_rolos_pendentes = qtd_rolos_pendentes;
    }

    public Integer getQtd_rolos_enviados() {
        return qtd_rolos_enviados;
    }

    public void setQtd_rolos_enviados(Integer qtd_rolos_enviados) {
        this.qtd_rolos_enviados = qtd_rolos_enviados;
    }

    public String getRol1() {
        return rol1;
    }

    public void setRol1(String rol1) {
        this.rol1 = rol1;
    }

    public String getRol2() {
        return rol2;
    }

    public void setRol2(String rol2) {
        this.rol2 = rol2;
    }

    public String getRol3() {
        return rol3;
    }

    public void setRol3(String rol3) {
        this.rol3 = rol3;
    }

    public String getRol4() {
        return rol4;
    }

    public void setRol4(String rol4) {
        this.rol4 = rol4;
    }

    public String getRol5() {
        return rol5;
    }

    public void setRol5(String rol5) {
        this.rol5 = rol5;
    }

    public String getRol6() {
        return rol6;
    }

    public void setRol6(String rol6) {
        this.rol6 = rol6;
    }

    public String getRol7() {
        return rol7;
    }

    public void setRol7(String rol7) {
        this.rol7 = rol7;
    }

    public String getRol8() {
        return rol8;
    }

    public void setRol8(String rol8) {
        this.rol8 = rol8;
    }

    public String getRol9() {
        return rol9;
    }

    public void setRol9(String rol9) {
        this.rol9 = rol9;
    }

    public String getRol10() {
        return rol10;
    }

    public void setRol10(String rol10) {
        this.rol10 = rol10;
    }

    public String getRol11() {
        return rol11;
    }

    public void setRol11(String rol11) {
        this.rol11 = rol11;
    }

    public String getRol12() {
        return rol12;
    }

    public void setRol12(String rol12) {
        this.rol12 = rol12;
    }

    public String getRol13() {
        return rol13;
    }

    public void setRol13(String rol13) {
        this.rol13 = rol13;
    }

    public String getRol14() {
        return rol14;
    }

    public void setRol14(String rol14) {
        this.rol14 = rol14;
    }

    public String getRol15() {
        return rol15;
    }

    public void setRol15(String rol15) {
        this.rol15 = rol15;
    }

    public String getRol16() {
        return rol16;
    }

    public void setRol16(String rol16) {
        this.rol16 = rol16;
    }

    public String getRol17() {
        return rol17;
    }

    public void setRol17(String rol17) {
        this.rol17 = rol17;
    }

    public String getRol18() {
        return rol18;
    }

    public void setRol18(String rol18) {
        this.rol18 = rol18;
    }

}
