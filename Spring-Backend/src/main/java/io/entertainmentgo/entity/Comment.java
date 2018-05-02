package io.entertainmentgo.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table
@Data
public class Comment implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name="myuuid",strategy="uuid2")
	@GeneratedValue(generator="myuuid")
	private String commentId;
	private String comment;
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.EAGER)
	private User user;
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.EAGER)
	private Title title;
	private String date;
}
