package wallet.model;

import wallet.logic.LogicManager;

import java.util.ArrayList;

public class WalletList {

    private ArrayList<Wallet> walletList;
    private int state;

    public WalletList() {
        this.walletList = new ArrayList<>();
        this.state = 0;
    }

    public ArrayList<Wallet> getWalletList() {
        return this.walletList;
    }

    public void setWalletList(ArrayList<Wallet> walletList) {
        this.walletList = walletList;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

}
