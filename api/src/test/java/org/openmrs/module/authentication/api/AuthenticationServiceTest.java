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

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

public class AuthenticationServiceTest extends BaseModuleWebContextSensitiveTest {

    /**
     * @verifies authenticate valid user and return authorization token
     * @see AuthenticationService#authenticate(String, String, Long)
     */
    @Test
    public void authenticate_shouldAuthenticateValidUserAndReturnAuthorizationToken() throws Exception {
        AuthenticationService service = Context.getService(AuthenticationService.class);
        String token = service.authenticate("admin", "test", 1000L);
        Assert.assertNotNull(token);
    }

    /**
     * @verifies throw exception if the user does not have valid account
     * @see AuthenticationService#authenticate(String, String, Long)
     */
    @Test(expected = APIException.class)
    public void authenticate_shouldThrowExceptionIfTheUserDoesNotHaveValidAccount() throws Exception {
        AuthenticationService service = Context.getService(AuthenticationService.class);
        service.authenticate("admin", "testarius", 1000L);
    }
}
