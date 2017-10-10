

package nl.youngcapital.nuws;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Gebruiker {
    @Id
    @GeneratedValue
    private Long id;
    private String naam;
    private String password;

    public Gebruiker() {
    }

    public Gebruiker(Long id, String naam, String password) {
        this.id = id;
        this.naam = naam;
        this.password = password;
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
