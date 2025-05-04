package metier;

import java.util.Date;
import java.util.Timer;

public class Intervention {
    private int id;
    private String date;
    private String heure;
    private String observation;
    private int idCli;

    public Intervention(String date, String heure, String observation, int idCli) {
        this.date = date;
        this.heure = heure;
        this.observation = observation;
        this.idCli = idCli;
    }

    @Override
    public String toString() {
        return "Intervention{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", heure='" + heure + '\'' +
                ", observation='" + observation + '\'' +
                ", idCli=" + idCli +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public int getIdCli() {
        return idCli;
    }

    public void setIdCli(int idCli) {
        this.idCli = idCli;
    }
}
