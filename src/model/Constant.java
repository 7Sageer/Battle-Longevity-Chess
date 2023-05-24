package model;

import java.io.Serializable;

public enum Constant implements Serializable {
    CHESSBOARD_ROW_SIZE(9),CHESSBOARD_COL_SIZE(7);

    private final int num;
    Constant(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
