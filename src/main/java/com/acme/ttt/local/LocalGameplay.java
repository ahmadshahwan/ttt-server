package com.acme.ttt.local;

import com.acme.ttt.i18n.Text;
import com.acme.ttt.terminal.TerminalGameplay;

public class LocalGameplay extends TerminalGameplay {
    public LocalGameplay(Text text) {
        super(new LocalTerminal(), text);
    }

    public LocalGameplay() {
        this(Text.en());
    }


    public static void main(String[] args) {
        new LocalGameplay().run();
    }
}
