package de.excellence.core;

import de.excellence.util.TokenUtil;

import javax.persistence.*;

@Entity
@Table(name = "token")
@NamedQuery(name = "excellence.Token.byToken",
        query = "SELECT t FROM AuthToken t WHERE t.value = ?1")
public class AuthToken {
    private static final int TOKEN_LENGTH = 128;
    @Id
    private String value;
    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    protected AuthToken() {
    }

    public AuthToken(User user) {
        this.user = user;
        this.value = TokenUtil.randomToken(TOKEN_LENGTH);
    }

    public User getUser() {
        return user;
    }

    public String getValue() {
        return value;
    }
}
