package com.example.demo.entity;

import java.time.LocalDateTime;

import com.example.demo.constant.ListStatusKind;
import com.example.demo.entity.converter.ListStatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="to_do_list")
@Data
public class ToDoListInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private String listId;
	private String userId;
	private String title;
	private String body;
	
	private String categoryId;
	
	@Column(name="task_status")
	@Convert(converter = ListStatusConverter.class)
	private ListStatusKind status;
	
	@Column(name="created_time")
	private LocalDateTime createdTime;
	
	@Column(name="updated_time")
	private LocalDateTime updatedTime;
}
