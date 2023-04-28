package model;


public class ChessPiece {
    // the owner of the chess
    private PlayerColor owner;

    // Elephant? Cat? Dog? ...
    public String name;
    int rank;

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        if(this.rank == 8 && target.rank == 1)
            return false;
        if(this.rank == 1 && target.rank == 8)
            return true;
        if(this.rank >= target.rank)
            return true;
        else
            return false;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }
    public ChessPiece clone() {
        return new ChessPiece(owner, name, rank);
    }
    @Override
    public String toString() {
        return owner + " " + name;
    }

    public int getRank() {
        return this.rank;
    }
}
