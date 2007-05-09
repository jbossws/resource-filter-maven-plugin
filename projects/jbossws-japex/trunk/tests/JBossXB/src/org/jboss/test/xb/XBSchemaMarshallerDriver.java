package org.jboss.test.xb;

import com.sun.japex.JapexDriverBase;
import com.sun.japex.TestCase;
import org.apache.xerces.xs.XSModel;
import org.jboss.test.xb.po.PurchaseOrder;
import org.jboss.xb.binding.MappingObjectModelProvider;
import org.jboss.xb.binding.Util;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBinding;
import org.jboss.xb.binding.sunday.MarshallerImpl;

import java.io.FileInputStream;
import java.io.StringWriter;

/**
 * @author Heiko Braun <heiko.braun@jboss.com>
 * @since May 17, 2006
 */
public class XBSchemaMarshallerDriver extends JapexDriverBase {

   private PurchaseOrder rootObject;
   private MappingObjectModelProvider provider;
   private XSModel xsModel;
   private SchemaBinding schemaBinding;

   private MarshallerImpl marshaller;

   public void initializeDriver() {
     marshaller = XBDriverSupport.createMarshaller();
   }

   public void prepare(TestCase testCase) {

      try
      {
         String schemaFile = testCase.getParam("schemaFile");
         String dataFile = testCase.getParam("dataFile");

         provider = new MappingObjectModelProvider();
         xsModel = Util.loadSchema(new FileInputStream(schemaFile), null, null);

         schemaBinding = XBDriverSupport.initSchemaBinding(schemaFile);
         rootObject = XBDriverSupport.prepareObjectModel(schemaBinding, dataFile);
      }
      catch (Exception e)
      {
         XBDriverSupport.handleException(e);
      }

   }

   public void run(TestCase testCase) {

      try
      {
         StringWriter xmlOutput = new StringWriter();
         marshaller.marshal( schemaBinding, provider, rootObject, xmlOutput );                  
      }
      catch(Exception e)
      {
        XBDriverSupport.handleException(e);
      }

   }
}
