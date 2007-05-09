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

/**
 * @author <a href="mailto:alex@jboss.org">Alexey Loubyansky</a>
 * @version <tt>$Revision: 1.2 $</tt>
 */
public class USAddress
{
   private final String country = "US";
   private String name;
   private String street;
   private String city;
   private String state;
   private BigDecimal zip;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getStreet()
   {
      return street;
   }

   public void setStreet(String street)
   {
      this.street = street;
   }

   public String getCity()
   {
      return city;
   }

   public void setCity(String city)
   {
      this.city = city;
   }

   public String getState()
   {
      return state;
   }

   public void setState(String state)
   {
      this.state = state;
   }

   public BigDecimal getZip()
   {
      return zip;
   }

   public void setZip(BigDecimal zip)
   {
      this.zip = zip;
   }

   public boolean equals(Object o)
   {
      if(this == o)
      {
         return true;
      }
      if(!(o instanceof USAddress))
      {
         return false;
      }

      final USAddress usAddress = (USAddress)o;

      if(city != null ? !city.equals(usAddress.city) : usAddress.city != null)
      {
         return false;
      }
      if(country != null ? !country.equals(usAddress.country) : usAddress.country != null)
      {
         return false;
      }
      if(name != null ? !name.equals(usAddress.name) : usAddress.name != null)
      {
         return false;
      }
      if(state != null ? !state.equals(usAddress.state) : usAddress.state != null)
      {
         return false;
      }
      if(street != null ? !street.equals(usAddress.street) : usAddress.street != null)
      {
         return false;
      }
      if(zip != null ? !zip.equals(usAddress.zip) : usAddress.zip != null)
      {
         return false;
      }

      return true;
   }

   public int hashCode()
   {
      int result;
      result = (country != null ? country.hashCode() : 0);
      result = 29 * result + (name != null ? name.hashCode() : 0);
      result = 29 * result + (street != null ? street.hashCode() : 0);
      result = 29 * result + (city != null ? city.hashCode() : 0);
      result = 29 * result + (state != null ? state.hashCode() : 0);
      result = 29 * result + (zip != null ? zip.hashCode() : 0);
      return result;
   }

   public String toString()
   {
      return "[USAddress name=" + name +
         ", street=" + street +
         ", city=" + city +
         ", state=" + state +
         ", zip=" + zip + "]";
   }
}
