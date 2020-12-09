/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estoque.bussines.facade;

import br.com.estoque.entity.InstallEntity;
import br.com.estoque.persistencia.Factory;
import br.com.estoque.persistencia.InstallJpa;

/**
 *
 * @author Alirio
 */
public class InstallService implements InstallIn{

    private Factory factory;
    private final InstallJpa jpa;
    
    public InstallService(Factory f) {
        jpa = new InstallJpa(f.getfactory());
    }    
    @Override
    public Options adicionarInstalacao(InstallEntity entity) {
        try{
            jpa.incluir(entity);
            return Options.OK;            
        }catch(Exception e){
            e.printStackTrace();
            return Options.NOTOK;
        }
    }
    
}
