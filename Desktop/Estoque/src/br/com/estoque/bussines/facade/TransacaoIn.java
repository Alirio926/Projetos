/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estoque.bussines.facade;

import br.com.estoque.entity.RemessaEntity;
import br.com.estoque.entity.RoloEntity;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Alirio
 */
public interface TransacaoIn {
    public Options adicionarEstoque(RoloEntity e);
    public void adicionarEstoque(RoloEntity[] e);
    public void atualizarEstoque(RoloEntity e);
    public void removerRolo(RoloEntity e);
    public Options atualizarEstoque(Iterator<RoloEntity> it);
    public Collection<RoloEntity> checarSerial(String Serial);
    public Options adicionarRemessa(RemessaEntity e);
    public void atualizarRemessa(RemessaEntity e);
    public void excluirRemessa(RemessaEntity e);
    public Collection<RemessaEntity> listarRemessas();
}
