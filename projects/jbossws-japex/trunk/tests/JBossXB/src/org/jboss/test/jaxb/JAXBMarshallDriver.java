package org.jboss.test.jaxb;

import com.sun.japex.JapexDriverBase;
import com.sun.japex.TestCase;
import primer.po.PurchaseOrderType;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;

/**
 * @author Heiko Braun <heiko.braun@jboss.com>
 * @since May 17, 2006
 */
public class JAXBMarshallDriver extends JapexDriverBase {

   private Marshaller marshaller;
   private JAXBElement<PurchaseOrderType> rootElement;

   public void initializeDriver() {
      marshaller = JAXBDriverSupport.createMarshaller();
   }

   public void prepare(TestCase testCase) {
      String dataFile = testCase.getParam("dataFile");
      rootElement = JAXBDriverSupport.prepareObjectModel(dataFile);
   }

   public void run(TestCase testCase) {

      try
      {
         ByteArrayOutputStream bout = new ByteArrayOutputStream();
         marshaller.marshal( rootElement, bout );
      }
      catch (Exception e)
      {
         JAXBDriverSupport.handleException(e);
      }

   }

}
