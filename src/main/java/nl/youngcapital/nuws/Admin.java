
package nl.youngcapital.nuws;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Admin {
    @Id
    @GeneratedValue
    private Long id;
    private String naam;
    private String password;
    private Long admintype;

    public Admin() {
    }

    public Admin(Long id, String naam, String password, Long admintype) {
        this.id = id;
        this.naam = naam;
        this.password = password;
        this.admintype = admintype;
    }

    public Long getAdmintype() {
        return admintype;
    }

    public void setAdmintype(Long admintype) {
        this.admintype = admintype;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
