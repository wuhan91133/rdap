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
package org.restfulwhois.rdap.core.domain.controller;

import javax.servlet.http.HttpServletRequest;

import org.restfulwhois.rdap.common.dto.DomainDto;
import org.restfulwhois.rdap.common.dto.UpdateResponse;
import org.restfulwhois.rdap.common.model.Domain;
import org.restfulwhois.rdap.common.service.UpdateService;
import org.restfulwhois.rdap.common.support.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for domain update.
 * 
 * @author jiashuo
 * 
 */
@Controller
@RequestMapping(value = { "/u/domain" })
public class DomainUpdateController {
    /**
     * logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DomainUpdateController.class);
    /**
     * domain create service.
     */
    @Autowired
    @Qualifier("domainCreateServiceImpl")
    private UpdateService<DomainDto, Domain> createService;
    /**
     * domain update service.
     */
    @Autowired
    @Qualifier("domainUpdateServiceImpl")
    private UpdateService<DomainDto, Domain> updateService;
    /**
     * domain delete service.
     */
    @Autowired
    @Qualifier("domainDeleteServiceImpl")
    private UpdateService<DomainDto, Domain> deleteService;

    /**
     * create domain.
     * 
     * @param domainDto
     *            domainDto.
     * @param request
     *            HttpServletRequest.
     * @return JSON formated result,with HTTP code.
     * @throws  DecodeException
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = { "" }, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity create(@RequestBody DomainDto domainDto,
            HttpServletRequest request) {
        LOGGER.debug("create domain begin...");
        UpdateResponse response = createService.execute(domainDto);
        return RestResponse.createUpdateResponse(response);
    }

    /**
     * update domain.
     * @param domainDto
     *            domainDto.
     * @param handle
     *            handle.
     * @param request
     *            HttpServletRequest.
     * @return JSON formated result,with HTTP code.
     * @throws DecodeException
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = { "/{handle}" }, method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity update(@RequestBody DomainDto domainDto,
            @PathVariable String handle, HttpServletRequest request) {
        LOGGER.debug("update domain begin...");
        domainDto.setHandle(handle);
        UpdateResponse response = updateService.execute(domainDto);
        return RestResponse.createUpdateResponse(response);
    }

    /**
     * delete domain.
     * @param handle 
     *           handle.
     * @param request 
     *           HttpServletRequest.
     * @return JSON formated result,with HTTP code.
     * @throws DecodeException
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = { "/{handle}" }, method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity delete(@PathVariable String handle,
            HttpServletRequest request) {
        LOGGER.debug("delete domain begin...");
        DomainDto dto = new DomainDto();
        dto.setHandle(handle);
        UpdateResponse response = deleteService.execute(dto);
        return RestResponse.createUpdateResponse(response);
    }

}
