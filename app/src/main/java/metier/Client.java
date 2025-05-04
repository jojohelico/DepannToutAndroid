package metier;

public class Client {
    private int id = 0;
    private String nom;
    private String prenom;
    private String numTel;
    private String adrMail;
    private String adrPostale;

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNumTel() {
        return numTel;
    }

    public String getAdrMail() {
        return adrMail;
    }

    public String getAdrPostale() {
        return adrPostale;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", numTel='" + numTel + '\'' +
                ", adrMail='" + adrMail + '\'' +
                ", adrPostale='" + adrPostale + '\'' +
                '}';
    }

    public Client(int id, String nom, String prenom, String numTel, String adrMail, String adrPostale) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.adrMail = adrMail;
        this.adrPostale = adrPostale;
    }

    public Client(String nom, String prenom, String numTel, String adrMail, String adrPostale) {
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.adrMail = adrMail;
        this.adrPostale = adrPostale;
    }

    public Client(int id) {
        this.id = id;
    }
}
