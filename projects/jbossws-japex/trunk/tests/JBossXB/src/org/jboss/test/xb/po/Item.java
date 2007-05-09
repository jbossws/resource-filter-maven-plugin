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
import java.util.Calendar;

/**
 * @author <a href="mailto:alex@jboss.org">Alexey Loubyansky</a>
 * @version <tt>$Revision: 1.2 $</tt>
 */
public class Item
{
   private String productName;
   private BigInteger quantity;
   private BigDecimal USPrice;
   private String comment;
   private Calendar shipDate;
   private String partNum;

   public String getProductName()
   {
      return productName;
   }

   public void setProductName(String productName)
   {
      this.productName = productName;
   }

   public BigInteger getQuantity()
   {
      return quantity;
   }

   public void setQuantity(BigInteger quantity)
   {
      this.quantity = quantity;
   }

   public BigDecimal getUSPrice()
   {
      return USPrice;
   }

   public void setUSPrice(BigDecimal USPrice)
   {
      this.USPrice = USPrice;
   }

   public String getComment()
   {
      return comment;
   }

   public void setComment(String comment)
   {
      this.comment = comment;
   }

   public Calendar getShipDate()
   {
      return shipDate;
   }

   public void setShipDate(Calendar shipDate)
   {
      this.shipDate = shipDate;
   }

   public String getPartNum()
   {
      return partNum;
   }

   public void setPartNum(String partNum)
   {
      this.partNum = partNum;
   }

   public boolean equals(Object o)
   {
      if(this == o)
      {
         return true;
      }
      if(!(o instanceof Item))
      {
         return false;
      }

      final Item item = (Item)o;

      if(USPrice != null ? !USPrice.equals(item.USPrice) : item.USPrice != null)
      {
         return false;
      }
      if(comment != null ? !comment.equals(item.comment) : item.comment != null)
      {
         return false;
      }
      if(partNum != null ? !partNum.equals(item.partNum) : item.partNum != null)
      {
         return false;
      }
      if(productName != null ? !productName.equals(item.productName) : item.productName != null)
      {
         return false;
      }
      if(quantity != null ? !quantity.equals(item.quantity) : item.quantity != null)
      {
         return false;
      }
      if(shipDate != null ? !shipDate.equals(item.shipDate) : item.shipDate != null)
      {
         return false;
      }

      return true;
   }

   public int hashCode()
   {
      int result;
      result = (productName != null ? productName.hashCode() : 0);
      result = 29 * result + (quantity != null ? quantity.hashCode() : 0);
      result = 29 * result + (USPrice != null ? USPrice.hashCode() : 0);
      result = 29 * result + (comment != null ? comment.hashCode() : 0);
      result = 29 * result + (shipDate != null ? shipDate.hashCode() : 0);
      result = 29 * result + (partNum != null ? partNum.hashCode() : 0);
      return result;
   }

   public String toString()
   {
      return "[Item productName=" +
         productName +
         ", quantity=" +
         quantity +
         ", USPrice=" +
         USPrice +
         ", comment=" +
         comment +
         ", shipDate=" + (shipDate == null ? "null" : new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(
            shipDate.getTime())
         ) +
         ", partNum=" + partNum + "]";
   }
}
