/*
 * Copyright (c) 2012 - 2015, Internet Corporation for Assigned Names and
 * Numbers (ICANN) and China Internet Network Information Center (CNNIC)
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * * Neither the name of the ICANN, CNNIC nor the names of its contributors may
 *  be used to endorse or promote products derived from this software without
 *  specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL ICANN OR CNNIC BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package org.restfulwhois.rdap.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.restfulwhois.rdap.BaseTest;
import org.restfulwhois.rdap.common.util.DomainUtil;
import org.restfulwhois.rdap.core.domain.queryparam.DomainQueryParam;
import org.restfulwhois.rdap.redirect.bean.RedirectResponse;
import org.restfulwhois.rdap.redirect.dao.RedirectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

/**
 * Test for domain redirect DAO.
 * 
 * @author jiashuo
 * 
 */
@SuppressWarnings("rawtypes")
public class DomainRedirectDaoTest extends BaseTest {
    /**
     * domainRedirectDao.
     */
    @Autowired
    @Qualifier("domainRedirectDao")
    private RedirectDao redirectDao;

    /**
     * test query exist.
     */
    @Test
    @DatabaseTearDown("teardown.xml")
    @DatabaseSetup("domain-redirect.xml")
    public void testQueryExist() {
        String domainName = "cnnic.cn";
        String punyDomainName = DomainUtil.geneDomainPunyName(domainName);
        RedirectResponse redirect =
                redirectDao.query(DomainQueryParam.generateQueryParam(
                        domainName, punyDomainName));
        assertNotNull(redirect);
        assertEquals("http://cnnic.cn/rdap", redirect.getUrl());
    }

    /**
     * query invalid.
     */
    @Test
    @DatabaseTearDown("teardown.xml")
    @DatabaseSetup("domain-redirect.xml")
    public void testQueryInvalidTld() {
        // with '
        String domainName = "cnnic.c'n";
        String punyDomainName = DomainUtil.geneDomainPunyName(domainName);
        RedirectResponse redirect =
                redirectDao.query(DomainQueryParam.generateQueryParam(
                        domainName, punyDomainName));
        assertNull(redirect);
        // with "
        domainName = "cnnic.c'n";
        punyDomainName = DomainUtil.geneDomainPunyName(domainName);
        redirect =
                redirectDao.query(DomainQueryParam.generateQueryParam(
                        domainName, punyDomainName));
        assertNull(redirect);
    }

    /**
     * test query non exist.
     */
    @Test
    @DatabaseTearDown("teardown.xml")
    @DatabaseSetup("domain-redirect.xml")
    public void testQueryNotExist() {
        String domainName = "baidu.com";
        String punyDomainName = DomainUtil.geneDomainPunyName(domainName);
        RedirectResponse redirect =
                redirectDao.query(DomainQueryParam.generateQueryParam(
                        domainName, punyDomainName));
        assertNull(redirect);
    }

}
