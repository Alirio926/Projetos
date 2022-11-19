/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.squallsoft.api.dominios;


public class Board  {

    private Long id;
    private String board_name;
    private Integer jumpers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getBoard_name() {
            return board_name;
    }

    public void setBoard_name(String board_name) {
            this.board_name = board_name;
    }

    public Integer getJumpers() {
            return jumpers;
    }

    public void setJumpers(Integer jumpers) {
            this.jumpers = jumpers;
    }
}
