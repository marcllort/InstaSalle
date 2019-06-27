public class Categories {
    private double likes;
    private String name;
    public Categories(String name){
        this.name = name;
        likes = 1;
    }
    public void addLIke(){
        likes++;
    }

    public double getLikes() {
        return likes;
    }

    public String getName() {
        return name;
    }

    public void setLikes(double likes) {
        this.likes = likes;
    }

    public void setName(String name) {
        this.name = name;
    }
}
