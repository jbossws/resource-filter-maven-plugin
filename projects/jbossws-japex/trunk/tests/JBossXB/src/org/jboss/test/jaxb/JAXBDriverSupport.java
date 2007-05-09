package org.jboss.test.jaxb;

import primer.po.PurchaseOrderType;

import javax.xml.bind.*;
import java.io.FileInputStream;
import java.io.StringWriter;

/**
 * @author Heiko Braun <heiko.braun@jboss.com>
 * @since May 18, 2006
 */
public class  JAXBDriverSupport {

   public static JAXBElement<PurchaseOrderType> prepareObjectModel(String xmlFilename)
   {
      try
      {
         Unmarshaller u = createUnmarshaller();
         JAXBElement<PurchaseOrderType> poElement =
             (JAXBElement<PurchaseOrderType>)u.unmarshal( new FileInputStream( xmlFilename ) );

         return poElement;
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw new RuntimeException(e.getMessage());
      }

   }

   public static Marshaller createMarshaller() {

      Marshaller marshaller = null;

      try
      {
         JAXBContext jaxbContext = JAXBContext.newInstance("primer.po");
         marshaller = jaxbContext.createMarshaller();
         marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
         return marshaller;
      }
      catch (JAXBException e)
      {
         handleException(e);
      }

      return marshaller;
   }

   public static Unmarshaller createUnmarshaller() {

      Unmarshaller unmarshaller = null;
      try
      {
         JAXBContext jc = JAXBContext.newInstance( "primer.po" );
         unmarshaller = jc.createUnmarshaller();
      }
      catch (JAXBException e)
      {
         handleException(e);
      }

      return unmarshaller;
   }

   public static String prepareXMLModel(JAXBElement<PurchaseOrderType> rootElement)
   {
      Marshaller marshaller = createMarshaller();
      StringWriter xmlOutput = new StringWriter();
      try
      {
         marshaller.marshal(rootElement, xmlOutput);
      }
      catch (JAXBException e)
      {
         handleException(e);
      }
      return  xmlOutput.toString();
   }

   public static void handleException(Exception e)
   {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
   }
}
