package com.apis.blog_app.dto;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;

public class PostDto {
	
	private String title;
	private String content;
	

	private byte[] imageData;
	
	
	
	public byte[] getImageData() {
		return imageData;
	}
	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
