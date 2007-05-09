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

import org.jboss.xb.binding.GenericObjectModelProvider;
import org.jboss.xb.binding.MarshallingContext;

/**
 * @author <a href="mailto:alex@jboss.org">Alexey Loubyansky</a>
 * @version <tt>$Revision: 1.5 $</tt>
 */
public class BookGenericObjectModelProvider
   implements GenericObjectModelProvider
{
   public Object getChildren(Object o, MarshallingContext ctx, String namespaceURI, String localName)
   {
      Object children = null;
      if(o instanceof Book)
      {
         Book book = (Book)o;
         if(localName.equals("character"))
         {
            children = book.getCharacters();
         }
         else if(localName.equals("book"))
         {
            children = book;
         }
      }
      return children;
   }

   public Object getElementValue(Object o, MarshallingContext ctx, String namespaceURI, String localName)
   {
      Object value = null;
      if(o instanceof Book)
      {
         Book book = (Book)o;
         if("title".equals(localName))
         {
            value = book.getTitle();
         }
         else if("author".equals(localName))
         {
            value = book.getAuthor();
         }
      }
      else if(o instanceof BookCharacter)
      {
         BookCharacter character = (BookCharacter)o;
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
            value = character.getSince();
         }
         else if("qualification".equals(localName))
         {
            value = character.getQualification();
         }
      }
      return value;
   }

   public Object getAttributeValue(Object o, MarshallingContext ctx, String namespaceURI, String localName)
   {
      Object value = null;
      if(o instanceof Book)
      {
         Book book = (Book)o;
         if("isbn".equals(localName))
         {
            value = book.getIsbn();
         }
      }
      return value;
   }

   public Object getRoot(Object o, MarshallingContext ctx, String namespaceURI, String localName)
   {
      return o;
   }
}
