package com.squallsoft.api.dominios;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tbl_boards")
public class Board extends AbstractPersistable<Long> {

	@Column(nullable=false)
	private String board_name;
	
	@Column(nullable=false)
	private Integer jumpers;
	
	@JsonIgnore
	@Column(nullable=false)
	private Long mapperId;

	public Board() {}
	
	public Board(String board_name, Integer jumpers, Long mapperId) {
		super();
		this.board_name = board_name;
		this.jumpers = jumpers;
		this.mapperId = mapperId;
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

	public Long getMapperId() {
		return mapperId;
	}

	public void setMapperId(Long mapperId) {
		this.mapperId = mapperId;
	}
}
