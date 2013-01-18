/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.authentication.api.impl;

import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.authentication.Authentication;
import org.openmrs.module.authentication.api.AuthenticationService;
import org.openmrs.module.authentication.api.db.AuthenticationDAO;
import org.openmrs.module.authentication.util.DigestUtils;

import static org.apache.commons.lang.Validate.isTrue;

/**
 * It is a default implementation of {@link AuthenticationService}.
 */
public class AuthenticationServiceImpl extends BaseOpenmrsService implements AuthenticationService {

    protected final Log log = LogFactory.getLog(this.getClass());

    private AuthenticationDAO dao;

    /**
     * @param dao the dao to set
     */
    public void setDao(AuthenticationDAO dao) {
        this.dao = dao;
    }

    /**
     * @return the dao
     */
    public AuthenticationDAO getDao() {
        return dao;
    }

    /**
     * Service method to perform authentication for a given username and password. If the username and password
     * constitute to a valid user record, the service will return an authorization token which can be used to interact
     * with OpenMRS.
     *
     * @param username the username
     * @param password the password
     * @param duration the duration for which the token will be considered a valid token
     * @return the token information
     * @throws org.openmrs.api.APIException the authentication failed
     */
    @Override
    public String authenticate(final String username, final String password, final Long duration) throws Exception {
        Authentication authentication = null;
        Context.authenticate(username, password);
        if (Context.isAuthenticated()) {
            dao.purgeAuthentications();
            // clear up stale authentications before we insert new one
            String seed = UUID.randomUUID().toString();
            String usernameChecksum = DigestUtils.generateChecksum(username + ":" + seed);
            String tokenChecksum = DigestUtils.generateChecksum(username + ":" + seed + ":" + duration);
            authentication = new Authentication(tokenChecksum, usernameChecksum, seed, duration);
            dao.saveAuthentication(authentication);

            isTrue(authentication.getId() != null);
        }
        return (authentication == null ? null : authentication.getToken());
    }

    /**
     * Service method to check the validity of of a given token. A token is still valid if the token belongs to the
     * correct user and the time frame is still within the duration of the token lifespan.
     *
     * @param username the username who claims to have the token information
     * @param token    the token information
     * @return the correct token information
     * @throws org.openmrs.api.APIException if the token is invalid
     */
    @Override
    public String authenticate(final String username, final String token) throws APIException {
        // clear up stale authentications before we query the authentication
        // TODO: check the authentication class, we're not authenticated here, nor we have the correct password to access the service layer!
        dao.purgeAuthentications();
        Authentication authentication = dao.getAuthenticationByToken(token);
        return authentication.getToken();
    }

    /**
     * TODO: Need to have "Remotely Authenticate Others" privilege
     * TODO: make sure it's not gaining privilege
     * TODO: make sure the user asking to auth other have higher or all privilege of the other user
     *
     * @param username
     * @param password
     * @param otherusername
     * @param duration
     * @return
     */
    @Override
    public String authenticateUser(final String username, final String password, final String otherusername, final Long duration) {
        return null;
    }
}