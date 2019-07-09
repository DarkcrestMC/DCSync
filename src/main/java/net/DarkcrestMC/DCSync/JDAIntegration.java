package net.DarkcrestMC.DCSync;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class JDAIntegration extends ListenerAdapter {
    public DCSync plugin;
    public JDA jda;

    public JDAIntegration(DCSync dcsync) {
        this.plugin = dcsync;
        startBot();
        jda.addEventListener(this);
    }

    private void startBot() {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken("").buildBlocking();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
