package Json;

import java.math.BigInteger;
import java.util.ArrayList;


public class Posts {


    private int id;
    private BigInteger published;
    private ArrayList<Float> location;
    private ArrayList<String> liked_by;
    private ArrayList<String> commented_by;
    private Double ordre;
    private Point point;
    private String category;
    private String user;


    public Posts() {
        liked_by = new ArrayList<>();
        commented_by = new ArrayList<>();

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setPoint(float x, float y) {
        point = new Point(x, y);

    }

    public Point getPoint() {
        return point;
    }

    public Double getOrdre() {
        return ordre;
    }

    public void setOrdre(double ordre) {
        this.ordre = ordre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPublished(BigInteger published) {
        this.published = published;
    }

    public void setLocation(ArrayList<Float> location) {
        this.location = location;
    }

    public void setLiked_by(ArrayList<String> liked_by) {
        this.liked_by = liked_by;
    }

    public void setCommented_by(ArrayList<String> commented_by) {
        this.commented_by = commented_by;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getLiked_by() {
        return liked_by;
    }

    public ArrayList<String> getCommented_by() {
        return commented_by;
    }

    public BigInteger getPublished() {
        return published;
    }

    public ArrayList<Float> getLocation() {
        return location;
    }


}
