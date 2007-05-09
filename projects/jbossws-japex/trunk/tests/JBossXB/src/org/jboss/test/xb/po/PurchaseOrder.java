/*
  * JBoss, Home of Professional Open Source
  * Copyright 2005, JBoss Inc., and individual contributors as indicated
  * by the @authors tag. See the copyright.txt in the distribution for a
  * full listing of individual contributors.
  *
  * This is free software; you can redistribute it and/or modify it
  * under the terms of the GNU Lesser General Public License as
  * published by the Free Software Foundation; either version 2.1 of
  * the License, or (at your option) any later version.
  *
  * This software is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * Lesser General Public License for more details.
  *
  * You should have received a copy of the GNU Lesser General Public
  * License along with this software; if not, write to the Free
  * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
  * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
  */
package org.jboss.test.xb.po;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

/**
 * @author <a href="mailto:alex@jboss.org">Alexey Loubyansky</a>
 * @version <tt>$Revision: 1.2 $</tt>
 */
public class PurchaseOrder
{
   public static final PurchaseOrder DEFAULT_INSTANCE = createDefaultInstance();

   public static final PurchaseOrder createDefaultInstance()
   {
      PurchaseOrder po = new PurchaseOrder();
      po.orderDate = new GregorianCalendar(1999, 9, 20);
      po.shipTo = new USAddress();
      po.shipTo.setName("Alice Smith");
      po.shipTo.setStreet("123 Maple Street");
      po.shipTo.setCity("Mill Valley");
      po.shipTo.setState("CA");
      po.shipTo.setZip(new BigDecimal(90952));
      po.billTo = new USAddress();
      po.billTo.setName("Robert Smith");
      po.billTo.setStreet("8 Oak Avenue");
      po.billTo.setCity("Old Town");
      po.billTo.setState("PA");
      po.billTo.setZip(new BigDecimal(95819));
      po.comment = "Hurry, my lawn is going wild!";
      po.items = new ArrayList();

      Item item = new Item();
      item.setPartNum("872-AA");
      item.setProductName("Lawnmower");
      item.setQuantity(BigInteger.valueOf(1));
      item.setUSPrice(new BigDecimal("148.95"));
      item.setComment("Confirm this is electric");
      po.items.add(item);

      item = new Item();
      item.setPartNum("926-AA");
      item.setProductName("Baby Monitor");
      item.setQuantity(BigInteger.valueOf(1));
      item.setUSPrice(new BigDecimal("39.98"));
      item.setShipDate(new GregorianCalendar(1999, 4, 21));
      po.items.add(item);

      item = new Item();
      item.setPartNum("927-XX");
      item.setProductName("Gadget");
      item.setQuantity(BigInteger.valueOf(1));
      item.setUSPrice(new BigDecimal("99.98"));
      item.setShipDate(new GregorianCalendar(2006, 4, 21));
      po.items.add(item);

      return po;
   }

   public USAddress shipTo;
   private USAddress billTo;
   private String comment;
   private Collection items;
   private Calendar orderDate;

   public USAddress getShipTo() {
      return shipTo;
   }

   public void setShipTo(USAddress shipTo) {
      this.shipTo = shipTo;
   }

   public USAddress getBillTo()
   {
      return billTo;
   }

   public void setBillTo(USAddress billTo)
   {
      this.billTo = billTo;
   }

   public String getComment()
   {
      return comment;
   }

   public void setComment(String comment)
   {
      this.comment = comment;
   }

   public Collection getItems()
   {
      return items;
   }

   public void setItems(Collection items)
   {
      this.items = items;
   }

   public Calendar getOrderDate()
   {
      return orderDate;
   }

   public void setOrderDate(Calendar orderDate)
   {
      this.orderDate = orderDate;
   }

   public boolean equals(Object o)
   {
      if(this == o)
      {
         return true;
      }
      if(!(o instanceof PurchaseOrder))
      {
         return false;
      }

      final PurchaseOrder purchaseOrder = (PurchaseOrder)o;

      if(billTo != null ? !billTo.equals(purchaseOrder.billTo) : purchaseOrder.billTo != null)
      {
         return false;
      }
      if(comment != null ? !comment.equals(purchaseOrder.comment) : purchaseOrder.comment != null)
      {
         return false;
      }
      if(items != null ? !items.equals(purchaseOrder.items) : purchaseOrder.items != null)
      {
         return false;
      }
      if(orderDate != null ? !orderDate.equals(purchaseOrder.orderDate) : purchaseOrder.orderDate != null)
      {
         return false;
      }
      if(shipTo != null ? !shipTo.equals(purchaseOrder.shipTo) : purchaseOrder.shipTo != null)
      {
         return false;
      }

      return true;
   }

   public int hashCode()
   {
      int result;
      result = (shipTo != null ? shipTo.hashCode() : 0);
      result = 29 * result + (billTo != null ? billTo.hashCode() : 0);
      result = 29 * result + (comment != null ? comment.hashCode() : 0);
      result = 29 * result + (items != null ? items.hashCode() : 0);
      result = 29 * result + (orderDate != null ? orderDate.hashCode() : 0);
      return result;
   }

   public String toString()
   {
      return "[purchaseOrder shipto=" + shipTo +
         ", billTo=" + billTo +
         ", comment=" + comment +
         ", items=" + items +
         ", orderDate=" + (orderDate == null ? "null" : new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(
            orderDate.getTime())) + "]";
   }
}
