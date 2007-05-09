package org.jboss.test.xb;

import com.sun.japex.JapexDriverBase;
import com.sun.japex.TestCase;
import org.jboss.test.xb.po.PurchaseOrder;
import org.jboss.xb.binding.Unmarshaller;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBinding;

import java.io.FileInputStream;


/**
 * @author Heiko Braun <heiko.braun@jboss.com>
 * @since May 18, 2006
 */
public class XBUnmarshallerDriver extends JapexDriverBase {

   private Unmarshaller unmarshaller;
   private SchemaBinding schemaBinding;

   public void initializeDriver() {
      unmarshaller = XBDriverSupport.createUnmarshaller();
   }

   public void prepare(TestCase testCase) {
      schemaBinding = XBDriverSupport.initSchemaBinding(testCase.getParam("schemaFile"));
   }

   public void run(TestCase testCase) {
      try
      {
         String dataFile = testCase.getParam("dataFile");
         PurchaseOrder rootElement = (PurchaseOrder)
             unmarshaller.unmarshal(new FileInputStream(dataFile), schemaBinding);
      }
      catch (Exception e)
      {
         XBDriverSupport.handleException(e);
      }
   }

}
