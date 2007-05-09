package org.jboss.test.jaxb;

import com.sun.japex.JapexDriverBase;
import com.sun.japex.TestCase;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBElement;

import primer.po.PurchaseOrderType;

import java.io.FileInputStream;

/**
 * @author Heiko Braun <heiko.braun@jboss.com>
 * @since May 18, 2006
 */
public class JAXBUnmarshallerDriver extends JapexDriverBase {

   Unmarshaller unmarshaller;

   public void initializeDriver() {
      unmarshaller = JAXBDriverSupport.createUnmarshaller();
   }

   public void run(TestCase testCase) {
      try
      {
         JAXBElement<PurchaseOrderType> rootElement = (JAXBElement<PurchaseOrderType>)
             unmarshaller.unmarshal(
                 new FileInputStream( testCase.getParam("dataFile") )
             );         
      }
      catch (Exception e)
      {
         JAXBDriverSupport.handleException(e);
      }

   }
}
