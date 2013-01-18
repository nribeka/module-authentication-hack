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
package org.openmrs.module.authentication.api.db.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.authentication.Authentication;
import org.openmrs.module.authentication.api.db.AuthenticationDAO;

/**
 * It is a default implementation of  {@link AuthenticationDAO}.
 */
public class HibernateAuthenticationDAO implements AuthenticationDAO {

    protected final Log log = LogFactory.getLog(this.getClass());

    private SessionFactory sessionFactory;

    /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Authentication saveAuthentication(final Authentication authentication) {
        sessionFactory.getCurrentSession().save(authentication);
        return authentication;
    }

    @Override
    public Authentication getAuthenticationById(final Integer id) {
        return (Authentication) sessionFactory.getCurrentSession().get(Authentication.class, id);
    }

    @Override
    public Authentication getAuthenticationByUuid(final String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Authentication.class);
        criteria.add(Restrictions.eq("uuid", uuid));
        return (Authentication) criteria.uniqueResult();
    }

    @Override
    public Authentication getAuthenticationByToken(final String token) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Authentication.class);
        criteria.add(Restrictions.eq("token", token));
        return (Authentication) criteria.uniqueResult();
    }

    @Override
    public List<Authentication> getAuthenticationsByUsername(final String username) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Authentication.class);
        criteria.add(Restrictions.eq("username", username));
        return criteria.list();
    }

    @Override
    public void purgeAuthentications() {
        String queryString = "delete from Authentication where expirationDate <= :currentDate";
        Query query = sessionFactory.getCurrentSession().createQuery(queryString);
        query.setDate("currentDate", new Date());
        Integer deletedEntities = query.executeUpdate();
        log.info(deletedEntities + " authentication(s) was removed.");
    }
}