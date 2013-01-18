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
package org.openmrs.module.authentication;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.BaseOpenmrsMetadata;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */
public class Authentication extends BaseOpenmrsData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;

    private String token;

    private String username;

    private String seed;

    private Long duration;

    // expirationTime
    // TODO: consider the timezone values, time
    // TODO: store the value as timestamp
    private Date expirationDate;

    public Authentication(final String token, final String username, final String seed, final Long duration) {
        this.token = token;
        this.username = username;
        this.seed = seed;
        this.duration = duration;

        Long currentTime = System.currentTimeMillis();
        this.expirationDate = new Date(currentTime + duration);
    }

    @Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(final String seed) {
        this.seed = seed;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(final Long duration) {
        this.duration = duration;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(final Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}