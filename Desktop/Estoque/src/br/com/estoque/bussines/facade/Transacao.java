/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estoque.bussines.facade;

import br.com.estoque.entity.RemessaEntity;
import br.com.estoque.entity.RoloEntity;
import br.com.estoque.persistencia.EstoqueJpa;
import br.com.estoque.persistencia.Factory;
import br.com.estoque.persistencia.RemessaJpa;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alirio
 */
public class Transacao implements TransacaoIn {

    private final EstoqueJpa estoqueJpa;
    private final RemessaJpa remessaJpa;

    public Transacao(Factory em) {
        estoqueJpa = new EstoqueJpa(em.getfactory());
        remessaJpa = new RemessaJpa(em.getfactory());
    }

    @Override
    public Options adicionarEstoque(RoloEntity e) {
        try {
            estoqueJpa.incluir(e);
            return Options.OK;
        } catch (Exception ex) {
            Logger.getLogger(Transacao.class.getName()).log(Level.SEVERE, null, ex);
            return Options.NOTOK;
        }
    }

    @Override
    public void adicionarEstoque(RoloEntity[] e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizarEstoque(RoloEntity e) {
        try {
            estoqueJpa.alterar(e);
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    @Override
    public Options atualizarEstoque(Iterator<RoloEntity> it) {
        while (it.hasNext()) {
            try {
                estoqueJpa.alterar(it.next());
            } catch (Exception ex) {
                Logger.getLogger(Transacao.class.getName()).log(Level.SEVERE, null, ex);
                return Options.NOTOK;
            }
        }
        return Options.OK;
    }

    @Override
    public Options adicionarRemessa(RemessaEntity e) {
        try {
            remessaJpa.incluir(e);
            return Options.OK;
        } catch (Exception ex) {
            Logger.getLogger(Transacao.class.getName()).log(Level.SEVERE, null, ex);
            return Options.NOTOK;
        }
    }

    @Override
    public void atualizarRemessa(RemessaEntity e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<RoloEntity> checarSerial(String Serial) {
        Collection<RoloEntity> collection;
        try {
            collection = estoqueJpa.procurarPorSerialDinamico(Serial);
        } catch (Exception ex) {
            collection = new HashSet<>();
            Logger.getLogger(Transacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return collection;
    }

    @Override
    public void excluirRemessa(RemessaEntity e) {

    }

    @Override
    public Collection<RemessaEntity> listarRemessas() {
        Collection<RemessaEntity> collection;
        try {
            collection = remessaJpa.listarTodasRemessas();
        } catch (Exception ex) {
            collection = new HashSet<>();
            Logger.getLogger(Transacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return collection;
    }

    @Override
    public void removerRolo(RoloEntity e) {
        try {
            estoqueJpa.apagar(e);
        } catch (Exception ex) {
            Logger.getLogger(Transacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
