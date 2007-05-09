package org.jboss.test.xb;

import org.apache.xerces.xs.XSModel;
import org.jboss.test.xb.po.PurchaseOrder;
import org.jboss.xb.binding.*;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBinding;
import org.jboss.xb.binding.sunday.unmarshalling.XsdBinder;
import org.jboss.xb.binding.sunday.MarshallerImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringWriter;

/**
 * @author Heiko Braun <heiko.braun@jboss.com>
 * @since May 18, 2006
 */
public class XBDriverSupport {

   public static SchemaBinding initSchemaBinding(String schemaFile) {
      try
      {
         return XsdBinder.bind(new FileInputStream(schemaFile), null);
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
         throw new RuntimeException(e.getMessage());
      }
   }

   public static PurchaseOrder prepareObjectModel(SchemaBinding schemaBinding, String xmlFilename)
   {
      try
      {
         Unmarshaller unmarshaller = createUnmarshaller();
         PurchaseOrder po = (PurchaseOrder)unmarshaller.unmarshal(
             new FileInputStream(xmlFilename), schemaBinding
         );

         return po;
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw new RuntimeException(e.getMessage());
      }
   }

   public static String prepareXMLModel(XSModel xsModel, Object rootElement)
   {
      XercesXsMarshaller marshaller = createXercesXsMarshaller();

      // we need to specify what elements are top most (roots) providing namespace URI, prefix and local name
      marshaller.addRootElement("", "", "purchaseOrder");

      // add schema location by declaring xsi namespace and adding xsi:schemaReader attribute
      marshaller.declareNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");

      marshaller.setProperty(XercesXsMarshaller.PROP_OUTPUT_XML_VERSION, "false");
      marshaller.setProperty(XercesXsMarshaller.PROP_OUTPUT_INDENTATION, "false");
      marshaller.declareNamespace("xsi", Constants.NS_XML_SCHEMA_INSTANCE);
      marshaller.setSupportNil(true);
      marshaller.setSimpleContentProperty("_value");

      StringWriter xmlOutput = new StringWriter();
      try
      {
         marshaller.marshal(xsModel, new MappingObjectModelProvider(), rootElement, xmlOutput);
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw new RuntimeException(e.getMessage());
      }

      return xmlOutput.toString();
   }

   public static XercesXsMarshaller createXercesXsMarshaller()
   {
      System.setProperty(Marshaller.PROP_MARSHALLER, XercesXsMarshaller.class.getName());
      XercesXsMarshaller marshaller = (XercesXsMarshaller) Marshaller.FACTORY.getInstance();

      marshaller.addRootElement("", "", "purchaseOrder");
      marshaller.declareNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");

      marshaller.setProperty(XercesXsMarshaller.PROP_OUTPUT_XML_VERSION, "false");
      marshaller.setProperty(XercesXsMarshaller.PROP_OUTPUT_INDENTATION, "false");
      marshaller.declareNamespace("xsi", Constants.NS_XML_SCHEMA_INSTANCE);
      marshaller.setSupportNil(true);
      marshaller.setSimpleContentProperty("_value");

      return marshaller;
   }

   public static MarshallerImpl createMarshaller()
   {
      System.setProperty(Marshaller.PROP_MARSHALLER, XercesXsMarshaller.class.getName());
      MarshallerImpl marshaller = new MarshallerImpl();

      marshaller.addRootElement("", "", "purchaseOrder");
      marshaller.declareNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");

      marshaller.setProperty(XercesXsMarshaller.PROP_OUTPUT_XML_VERSION, "false");
      marshaller.setProperty(XercesXsMarshaller.PROP_OUTPUT_INDENTATION, "false");
      marshaller.declareNamespace("xsi", Constants.NS_XML_SCHEMA_INSTANCE);
      marshaller.setSupportNil(true);
      //marshaller.setSimpleContentProperty("_value");

      return marshaller;
   }

   public static Unmarshaller createUnmarshaller()
   {
      Unmarshaller unmarshaller = UnmarshallerFactory.newInstance().newUnmarshaller();
      return unmarshaller;
   }

   public static void handleException(Exception e)
   {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
   }
}
