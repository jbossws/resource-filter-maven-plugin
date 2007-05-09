/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://glassfish.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://glassfish.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2006 Sun Microsystems Inc. All Rights Reserved
 */
package org.jboss.com.sun.xml.ws.server;

import org.jboss.com.sun.xml.ws.encoding.internal.InternalEncoder;
import org.jboss.com.sun.xml.ws.encoding.xml.XMLDecoder;
import org.jboss.com.sun.xml.ws.encoding.xml.XMLEPTFactory;
import org.jboss.com.sun.xml.ws.encoding.xml.XMLEncoder;
import org.jboss.com.sun.xml.ws.pept.encoding.Decoder;
import org.jboss.com.sun.xml.ws.pept.encoding.Encoder;
import org.jboss.com.sun.xml.ws.pept.ept.MessageInfo;
import org.jboss.com.sun.xml.ws.pept.presentation.TargetFinder;
import org.jboss.com.sun.xml.ws.pept.protocol.Interceptors;
import org.jboss.com.sun.xml.ws.pept.protocol.MessageDispatcher;

/**
 * @author WS Development Team
 */
public class XMLEPTFactoryImpl implements XMLEPTFactory {
    private Encoder encoder;
    private Decoder decoder;
    private XMLEncoder xmlEncoder;
    private XMLDecoder xmlDecoder;
    private InternalEncoder internalEncoder;
    private TargetFinder targetFinder;
    private MessageDispatcher messageDispatcher;

    public XMLEPTFactoryImpl(Encoder encoder, Decoder decoder,
            TargetFinder targetFinder, MessageDispatcher messageDispatcher) {
        this.encoder = encoder;
        this.decoder = decoder;
        this.targetFinder = targetFinder;
        this.messageDispatcher = messageDispatcher;
    }
    
    public XMLEPTFactoryImpl(XMLEncoder xmlEncoder, XMLDecoder xmlDecoder,
                             InternalEncoder internalEncoder,
                             TargetFinder targetFinder, MessageDispatcher messageDispatcher) {
        this.xmlEncoder = xmlEncoder;
        this.xmlDecoder = xmlDecoder;
        this.encoder = null;
        this.decoder = null;
        this.internalEncoder = internalEncoder;
        this.targetFinder = targetFinder;
        this.messageDispatcher = messageDispatcher;
    }

    public Encoder getEncoder(MessageInfo messageInfo) {
        messageInfo.setEncoder(encoder);
        return messageInfo.getEncoder();
    }

    public Decoder getDecoder(MessageInfo messageInfo) {
        messageInfo.setDecoder(decoder);
        return messageInfo.getDecoder();
    }

    public TargetFinder getTargetFinder(MessageInfo messageInfo) {
        return targetFinder;
    }

    public MessageDispatcher getMessageDispatcher(MessageInfo messageInfo) {
        messageInfo.setMessageDispatcher(messageDispatcher);
        return messageDispatcher;
    }

    /*
     * @see com.sun.istack.pept.ept.EPTFactory#getInterceptors(com.sun.istack.pept.ept.MessageInfo)
     */
    public Interceptors getInterceptors(MessageInfo x) {
        return null;
    }

    /*
     * @see org.jboss.com.sun.xml.ws.encoding.jaxb.LogicalEPTFactory#getSoapEncoder()
     */
    public XMLEncoder getXMLEncoder() {
        return xmlEncoder;
    }

    /*
     * @see org.jboss.com.sun.xml.ws.encoding.jaxb.LogicalEPTFactory#getSoapDecoder()
     */
    public XMLDecoder getXMLDecoder() {
        return xmlDecoder;
    }

    public InternalEncoder getInternalEncoder() {
        return internalEncoder;
    }


}
