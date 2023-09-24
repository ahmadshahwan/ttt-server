package com.acme.ttt;

public enum Mark {
    X, O;

    public Mark other() {
        return this == X ? O : X;
    }
}
