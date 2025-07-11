package app;

public class Par<F, S> {
    private F first;
    private S second;

    public Par(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst(){return first;}
    public S getSecond(){return second;}

    @Override
    public String toString() {
        return first + " - " + second;
    }
}
