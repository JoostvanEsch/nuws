
package nl.youngcapital.nuws;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Review {
    @Id
    @GeneratedValue
    private Long id;
    private String tekst;
    @ManyToOne
    private NieuwsItem nieuwsitem;
    @ManyToOne
    private Gebruiker gebruiker;

    public Review(Long id, String tekst, NieuwsItem nieuwsitem, Gebruiker gebruiker) {
        this.id = id;
        this.tekst = tekst;
        this.nieuwsitem = nieuwsitem;
        this.gebruiker = gebruiker;
    }

    public Review() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public NieuwsItem getNieuwsitem() {
        return nieuwsitem;
    }

    public void setNieuwsitem(NieuwsItem nieuwsitem) {
        this.nieuwsitem = nieuwsitem;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }
    
    
    
}
