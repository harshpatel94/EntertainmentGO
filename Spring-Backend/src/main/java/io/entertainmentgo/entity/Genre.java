package io.entertainmentgo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table
@Data
public class Genre implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name="myuuid",strategy="uuid2")
	@GeneratedValue(generator="myuuid")
	private String genreId;
	private String genre;
}
