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

import org.jboss.xb.binding.ObjectModelFactory;
import org.jboss.xb.binding.UnmarshallingContext;
import org.xml.sax.Attributes;

/**
 * org.jboss.xb.binding.ObjectModelFactory implementation that accepts data chuncks from unmarshaller
 * and assembles them into an instance Book.
 *
 * @author <a href="mailto:alex@jboss.org">Alexey Loubyansky</a>
 * @version <tt>$Revision: 1.8 $</tt>
 */
public class BookObjectFactory
   implements ObjectModelFactory
{
   // ObjectModelFactory implementation

   /**
    * Return the root.
    */
   public Object newRoot(Object root,
                         UnmarshallingContext navigator,
                         String namespaceURI,
                         String localName,
                         Attributes attrs)
   {
      final Book book;
      if(root == null)
      {
         root = book = new Book();
      }
      else
      {
         book = (Book) root;
      }

      if(attrs.getLength() > 0)
      {
         for(int i = 0; i < attrs.getLength(); ++i)
         {
            if(attrs.getLocalName(i).equals("isbn"))
            {
               book.setIsbn(attrs.getValue(i));
            }
         }
      }

      return root;
   }

   public Object completeRoot(Object root, UnmarshallingContext ctx, String uri, String name)
   {
      return root;
   }

   // Methods discovered by introspection

   /**
    * Called when a child element with simple content is read for book.
    */
   public void setValue(Book book,
                        UnmarshallingContext navigator,
                        String namespaceURI,
                        String localName,
                        String value)
   {
      if("title".equals(localName))
      {
         book.setTitle(value);
      }
      else if("author".equals(localName))
      {
         book.setAuthor(value);
      }
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(Book book,
                          UnmarshallingContext navigator,
                          String namespaceURI,
                          String localName,
                          Attributes attrs)
   {
      Object child = null;
      if("character".equals(localName))
      {
         child = new BookCharacter();
      }
      return child;
   }

   /**
    * Called when a child element with simple content is read for character.
    */
   public void setValue(BookCharacter character,
                        UnmarshallingContext navigator,
                        String namespaceURI,
                        String localName,
                        String value)
   {
      if("name".equals(localName))
      {
         character.setName(value);
      }
      else if("friend-of".equals(localName))
      {
         character.setFriendOf(value);
      }
      else if("since".equals(localName))
      {
         if(value.endsWith("Z"))
         {
            value = value.substring(0, value.length() - 1);
         }
         character.setSince(value);
      }
      else if("qualification".equals(localName))
      {
         character.setQualification(value);
      }
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Book book,
                        BookCharacter character,
                        UnmarshallingContext navigator,
                        String namespaceURI,
                        String localName)
   {
      book.addCharacter(character);
   }
}
