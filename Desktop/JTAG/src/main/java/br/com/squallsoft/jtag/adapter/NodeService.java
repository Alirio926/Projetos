/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.jtag.adapter;

import br.com.squallsoft.api.resteasy.RESTEasyClient;
import com.squallsoft.api.dominios.LogEntity;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author alirio.filho
 */
public class NodeService {    
    
    public static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        
        return Toolkit.getDefaultToolkit().getImage(url);
    }
    //private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy 'as' H':'mm':'ss");
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H':'mm':'ss");
    
    private static <E, K> Map<K, List<E>> groupBy(List<E> list, Function<E, K> keyFunction) {
        return Optional.ofNullable(list)
            .orElseGet(ArrayList::new)
            .stream()
            .collect(Collectors.groupingBy(keyFunction));
    }
    
    public static DefaultMutableTreeNode getNodeByDay(List<LogEntity> list){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        // Organiza
        Map<Integer, List<LogEntity>> byDay = groupBy(list, LogEntity::getDia);
        // Loop por todos os dias para pegar os dias com log
        for(int d = 0; d < 31; d++){
            // Se esse dia teve gravação
            if(byDay.containsKey(d)){
                // Obtem a quantidade de gravações do dia
                int qtdOcorrenciaDia = byDay.get(d).size();
                // Nó dia
                DefaultMutableTreeNode nodeDay = new DefaultMutableTreeNode("[" + qtdOcorrenciaDia + "] Dia " + (d+1));
                // Percore todas as gravações do dia
                for(int thiDay = 0; thiDay < qtdOcorrenciaDia; thiDay++){
                    // obtem entity
                    LogEntity l = byDay.get(d).get(thiDay);
                    // Gera novo nó com o nome do mapper
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(l.getMapper() + " " + (l.getSuccess() == true ? "SUCESSO" : "FALHOU"));
                    // Adiciona nó ao root
                    nodeDay.add(node);
                    
                }
                root.add(nodeDay);
            }
        }       
        return root;
    }
    
    public static DefaultMutableTreeNode getAllNodesByDay(List<LogEntity> list, String value){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(value);
        // Organiza
        Map<Integer, List<LogEntity>> byDay = groupBy(list, LogEntity::getDia);
        
        // Loop por todos os dias para pegar os dias com log
        for(int d = 0; d < 31; d++){
            // Se esse dia teve gravação
            if(byDay.containsKey(d)){
                // Obtem a quantidade de gravações do dia
                int qtdOcorrenciaDia = byDay.get(d).size();
                
                DefaultMutableTreeNode nodeMapper = getNodeByMapper(byDay.get(d), "[" + qtdOcorrenciaDia + "] Dia " + d);
                if(!nodeMapper.isLeaf()){ // se o mapper tiver filhos adiciona
                    root.add(nodeMapper);
                }  
            }
        }       
        return root;
    }
    
    public static DefaultMutableTreeNode getNodeByMapper(List<LogEntity> list, String v){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(v);
        Map<String, List<LogEntity>> byMapper = groupBy(list, LogEntity::getMapper);
        
        LogTree logTree;
        for(int idx = 0; idx < RESTEasyClient.listFirmware().size(); idx++){
            String map = RESTEasyClient.listFirmware().get(idx).getChip_name();
            System.err.println(map);
            if(byMapper.containsKey(map)){
                int qtdOcorrenciaMap = byMapper.get(map).size();
                DefaultMutableTreeNode nodeMonth = new DefaultMutableTreeNode("[" + qtdOcorrenciaMap + "] "+ map.toUpperCase());
                
                for(int thisMap = 0; thisMap < qtdOcorrenciaMap; thisMap++){
                    LogEntity l = byMapper.get(map).get(thisMap);
                    
                    if(l.getSuccess()){
                        logTree = new LogTree(l.getDataTime().format(formatter) + " - Ok", "chip_ok.png");
                    }else{
                        logTree = new LogTree(l.getDataTime().format(formatter) + " - Falhou", "chip_fail.png");
                    } 
                            
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(logTree);                       
                    nodeMonth.add(node);
                }
                root.add(nodeMonth);
            }
        } 
        return root;
    }
    
}
