/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Administrador
 */
public class Factory {
    private static EntityManagerFactory factory;

    static{
        Factory.factory = Persistence.createEntityManagerFactory("GMAPU");
    }
    public EntityManagerFactory getfactory(){
        return factory;
    }
}
