package com.icommerce.app.shopping.common.authentication.filter;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.AuthorityUtils;

public class TokenAuthentication extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private static final String USER_LOGIN = "USER_LOGIN";

    final private Object principal;

    public TokenAuthentication(final Object details, final Object principal) {
        super(AuthorityUtils.createAuthorityList(USER_LOGIN));
        this.setDetails(details);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return getDetails();
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String currentToken() {
        return getDetails().toString();
    }
}
