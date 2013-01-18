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
package org.openmrs.module.authentication.api;

import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service exposes module's core functionality. It is a Spring managed bean which is configured in moduleApplicationContext.xml.
 * <p>
 * It can be accessed only via Context:<br>
 * <code>
 * Context.getService(AuthenticationService.class).someMethod();
 * </code>
 * 
 * @see org.openmrs.api.context.Context
 */
@Transactional
public interface AuthenticationService extends OpenmrsService {

    /**
     * Service method to perform authentication for a given username and password. If the username and password
     * constitute to a valid user record, the service will return an authorization token which can be used to interact
     * with OpenMRS.
     *
     * @param username the username
     * @param password the password
     * @param duration the duration for which the token will be considered a valid token
     * @return the token information
     * @throws APIException the authentication failed
     * @should authenticate valid user and return authorization token
     * @should throw exception if the user does not have valid account
     */
    String authenticate(final String username, final String password, final Long duration) throws Exception;

    /**
     * Service method to check the validity of of a given token. A token is still valid if the token belongs to the
     * correct user and the time frame is still within the duration of the token lifespan.
     *
     * @param username the username who claims to have the token information
     * @param token the token information
     * @return the correct token information
     * @throws APIException if the token is invalid
     */
    String authenticate(final String username, final String token) throws Exception;

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
    String authenticateUser(final String username, final String password, final String otherusername, final Long duration);
}