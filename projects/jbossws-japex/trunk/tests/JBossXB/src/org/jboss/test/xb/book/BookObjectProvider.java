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
package org.jboss.test.xb.book;

import org.jboss.xb.binding.MarshallingContext;
import org.jboss.xb.binding.ObjectModelProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * org.jboss.xb.binding.ObjectModelProvider implementation that provides data to marshaller given
 * specific Book instance.
 *
 * @author <a href="mailto:alex@jboss.org">Alexey Loubyansky</a>
 * @version <tt>$Revision: 1.8 $</tt>
 */
public class BookObjectProvider
   implements ObjectModelProvider
{
   public Object getRoot(Object o, MarshallingContext ctx, String namespaceURI, String localName)
   {
      return o;
   }

   public Object getChildren(Book book, String namespaceUri, String localName)
   {
      Object children = null;
      if(localName.equals("book"))
      {
         children = book;
      }
      else if(localName.equals("character"))
      {
         children = book.getCharacters();
      }
      return children;
   }

   public Object getAttributeValue(Book book, String namespaceUri, String localName)
   {
      Object value;
      if("isbn".equals(localName))
      {
         value = book.getIsbn();
      }
      else
      {
         value = null;
      }
      return value;
   }

   public Object getElementValue(Book book, String namespaceUri, String localName)
   {
      Object value;
      if("title".equals(localName))
      {
         value = book.getTitle();
      }
      else if("author".equals(localName))
      {
         value = book.getAuthor();
      }
      else
      {
         value = null;
      }
      return value;
   }

   public Object getElementValue(BookCharacter character, String namespaceUri, String localName)
   {
      Object value = null;
      if("name".equals(localName))
      {
         value = character.getName();
      }
      else if("friend-of".equals(localName))
      {
         value = character.getFriendOf();
      }
      else if("since".equals(localName))
      {
         String since = character.getSince();
         if(since == null)
         {
            value = null;
         }
         else
         {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            java.util.Date date = null;
            try
            {
               date = dateFormat.parse(since);
            }
            catch(ParseException e)
            {
               throw new IllegalStateException("Failed to parse date " + since + ": " + e.getMessage());
            }

            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            cal.setTime(date);
            value = cal;
         }
      }
      else if("qualification".equals(localName))
      {
         value = character.getQualification();
      }
      return value;
   }
}
