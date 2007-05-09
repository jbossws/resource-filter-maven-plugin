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

import javax.xml.ws.Provider;
import javax.xml.ws.soap.SOAPBinding;

import javax.xml.ws.http.HTTPBinding;

import org.jboss.com.sun.xml.ws.binding.BindingImpl;
import org.jboss.com.sun.xml.ws.encoding.internal.InternalEncoder;
import org.jboss.com.sun.xml.ws.encoding.soap.SOAPDecoder;
import org.jboss.com.sun.xml.ws.encoding.soap.SOAPEncoder;
import org.jboss.com.sun.xml.ws.encoding.soap.ServerEncoderDecoder;
import org.jboss.com.sun.xml.ws.encoding.soap.server.ProviderSED;
import org.jboss.com.sun.xml.ws.encoding.soap.server.SOAP12XMLDecoder;
import org.jboss.com.sun.xml.ws.encoding.soap.server.SOAP12XMLEncoder;
import org.jboss.com.sun.xml.ws.encoding.soap.server.SOAPXMLDecoder;
import org.jboss.com.sun.xml.ws.encoding.soap.server.SOAPXMLEncoder;
import org.jboss.com.sun.xml.ws.encoding.xml.XMLDecoder;
import org.jboss.com.sun.xml.ws.encoding.xml.XMLEncoder;
import org.jboss.com.sun.xml.ws.pept.ept.EPTFactory;
import org.jboss.com.sun.xml.ws.pept.ept.MessageInfo;
import org.jboss.com.sun.xml.ws.pept.presentation.TargetFinder;
import org.jboss.com.sun.xml.ws.pept.protocol.MessageDispatcher;
import org.jboss.com.sun.xml.ws.protocol.soap.server.ProviderSOAPMD;
import org.jboss.com.sun.xml.ws.protocol.soap.server.SOAPMessageDispatcher;
import org.jboss.com.sun.xml.ws.protocol.xml.server.ProviderXMLMD;
import org.jboss.com.sun.xml.ws.server.provider.ProviderPeptTie;
import org.jboss.com.sun.xml.ws.spi.runtime.MessageContext;
import org.jboss.com.sun.xml.ws.util.MessageInfoUtil;

/**
 * factory for creating the appropriate EPTFactory given the BindingId from the EndpointInfo
 * in the RuntimeContext of the MessageInfo
 * Based on MessageInfo data(Binding, Implementor) it selects one the static EPTFactories 
 * using Binding information
 * Has a static EPTFactory object for each particular binding. 
 * EPTFactories are reused for all the requests.
 * Has static provider EPTFactory objects. 
 * The provider EPTFactories are reused for all the requests.
 * The factories reuse encoder, decoder, message dispatcher objects since these objects 
 * are Stateless. They are reused for all the requests.
 *
 * @author WS Development Team
 */
public abstract class EPTFactoryFactoryBase {

    public static final ProviderSOAPMD providerMessageDispatcher =
        new ProviderSOAPMD();
    public static final SOAPEncoder soap11Encoder = new SOAPXMLEncoder();
    public static final SOAPDecoder soap11Decoder = new SOAPXMLDecoder();
    public static final SOAPEncoder soap12Encoder = new SOAP12XMLEncoder();
    public static final SOAPDecoder soap12Decoder = new SOAP12XMLDecoder();
    
    public static final XMLEncoder xmlEncoder = null; //new XMLEncoder();
    public static final XMLDecoder xmlDecoder = null; //new XMLDecoder();

    public static final SOAPMessageDispatcher soap11MessageDispatcher =
        new SOAPMessageDispatcher();
    public static final MessageDispatcher providerXmlMD =
        new ProviderXMLMD();
    public static final InternalEncoder internalSED = new ServerEncoderDecoder();
    public static final InternalEncoder providerSED = new ProviderSED();
    public static final TargetFinder providerTargetFinder =
            new TargetFinderImpl(new ProviderPeptTie());
    public static final TargetFinder targetFinder =
            new TargetFinderImpl(new PeptTie());

    public static final EPTFactory providerSoap11 =
            new EPTFactoryBase(soap11Encoder, soap11Decoder,
                    providerSED, providerTargetFinder,
                providerMessageDispatcher);

    public static final EPTFactory providerSoap12 =
            new EPTFactoryBase(soap12Encoder, soap12Decoder,
                    providerSED, providerTargetFinder,
                providerMessageDispatcher);

    public static final EPTFactory soap11 =
            new EPTFactoryBase(
                soap11Encoder, soap11Decoder,
                    internalSED, targetFinder,
                soap11MessageDispatcher);

    public static final EPTFactory soap12 =
            new EPTFactoryBase(
                soap12Encoder, soap12Decoder,
                    internalSED, targetFinder,
                soap11MessageDispatcher);
    
    public static final EPTFactory providerXml =
        new XMLEPTFactoryImpl(
            xmlEncoder, xmlDecoder,
                providerSED, providerTargetFinder,
            providerXmlMD);
    
    /**
     * Choose correct EPTFactory. MessageInfo contains all the needed
     * information like Binding, WSConnection to make that decision.
     * @param mi the MessageInfo object to obtain the BindingID from.
     * @return returns the appropriate EPTFactory for the BindingID in the mi
     */
    public static EPTFactory getEPTFactory(MessageInfo mi) {
        RuntimeContext rtCtxt = MessageInfoUtil.getRuntimeContext(mi);
        RuntimeEndpointInfo endpointInfo = rtCtxt.getRuntimeEndpointInfo();
        String bindingId = ((BindingImpl)endpointInfo.getBinding()).getBindingId();
        if(bindingId.equals(SOAPBinding.SOAP11HTTP_BINDING)){
            if (endpointInfo.getImplementor() instanceof Provider) {
                return providerSoap11;
            } else {
                return soap11;
            }
        }else if(bindingId.equals(SOAPBinding.SOAP12HTTP_BINDING)){
            if (endpointInfo.getImplementor() instanceof Provider) {
                return providerSoap12;
            } else {
                return soap12;
            }
        } else if(bindingId.equals(HTTPBinding.HTTP_BINDING)){
            if (endpointInfo.getImplementor() instanceof Provider) {
                return providerXml;
            }
        }
        return null;
    }
}
