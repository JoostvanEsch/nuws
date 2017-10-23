package nl.youngcapital.nuws;

import java.io.IOException;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.*;


@Entity
public class NieuwsItem {
    @Id
    @GeneratedValue
    private Long id;
    private String url;
    private String tags;
    @ManyToOne
    private Admin admin;
    private String datetime;
    private String title;
    private String sub;
    private String bodytext;
    

	public NieuwsItem() {
    }

    public NieuwsItem(Long id, String url, String tags, Admin admin, String datetime, String title, String sub, String bodytext) {
        this.id = id;
        this.url = url;
        this.tags = tags;
        this.admin = admin;
        this.datetime = datetime;
        this.title = title;
        this.sub = sub;
        this.bodytext = bodytext;
    }
	
    

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    
    
    
    public String getBodytext() {
		return bodytext;
	}

	public void setBodytext(String bodytext) {
		this.bodytext = bodytext;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public void setTitle(String title) {
		this.title = title;
	}
    
    public String getTitle() {
		return title;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    
}
