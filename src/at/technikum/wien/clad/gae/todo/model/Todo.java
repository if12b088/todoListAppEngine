package at.technikum.wien.clad.gae.todo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Todo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String author;

  private String summary;
  private String description;
  private String url;
  private Date createDate;
  private Date finishDate;
  private Integer importance;
  boolean finished;

  public Todo(String author, String summary, String description,
      String url, Integer importance) {
    this.author = author;
    this.summary = summary;
    this.description = description;
    this.url = url;
    this.importance = importance;
    createDate = new Date();
    finished = false;
  }

  /**
 * @return the createDate
 */
public Date getCreateDate() {
	return createDate;
}

/**
 * @param createDate the createDate to set
 */
public void setCreateDate(Date createDate) {
	this.createDate = createDate;
}

/**
 * @return the finishDate
 */
public Date getFinishDate() {
	return finishDate;
}

/**
 * @param finishDate the finishDate to set
 */
public void setFinishDate(Date finishDate) {
	this.finishDate = finishDate;
}

public Long getId() {
    return id;
  }

	public String getAuthor() {
    return author;
  }

public Integer getImportance() {
	return importance;
}

public void setImportance(Integer importance) {
	this.importance = importance;
}

public void setAuthor(String author) {
    this.author = author;
  }

  public String getShortDescription() {
    return summary;
  }

  public void setShortDescription(String shortDescription) {
    this.summary = shortDescription;
  }

  public String getLongDescription() {
    return description;
  }

  public void setLongDescription(String longDescription) {
    this.description = longDescription;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public boolean isFinished() {
    return finished;
  }

  public void setFinished(boolean finished) {
    this.finished = finished;
  }

} 