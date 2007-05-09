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

import java.util.ArrayList;
import java.util.List;

/**
 * Book class that represents book element in XML content.
 *
 * @version <tt>$Revision: 1.4 $</tt>
 * @author <a href="mailto:alex@jboss.org">Alexey Loubyansky</a>
 */
public class Book
{
   public static Book getInstance()
   {
      Book book = new Book();
      book.setIsbn("0836217462");
      book.setTitle("Being a Dog Is a Full-Time Job");
      book.setAuthor("Charles M. Schulz");

      BookCharacter character = new BookCharacter();
      character.setName("Snoopy");
      character.setFriendOf("Peppermint Patty");
      character.setSince("1950-10-04");
      character.setQualification("extroverted beagle");
      book.addCharacter(character);

      character = new BookCharacter();
      character.setName("Peppermint Patty");
      character.setSince("1966-08-22");
      character.setQualification("bold, brash and tomboyish");
      book.addCharacter(character);

      return book;
   }
   
   private String isbn;
   private String title;
   private String author;
   private List characters = new ArrayList();

   public String getIsbn()
   {
      return isbn;
   }

   public void setIsbn(String isbn)
   {
      this.isbn = isbn;
   }

   public String getAuthor()
   {
      return author;
   }

   public void setAuthor(String author)
   {
      this.author = author;
   }

   public String getTitle()
   {
      return title;
   }

   public void setTitle(String title)
   {
      this.title = title;
   }

   public List getCharacters()
   {
      return characters;
   }

   public void setCharacters(List characters)
   {
      this.characters = characters;
   }

   public void addCharacter(BookCharacter character)
   {
      characters.add(character);
   }

   public int getCharactersTotal()
   {
      return characters.size();
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer(100);
      sb.append('[')
         .append("isbn=").append(isbn)
         .append(", title=").append(title)
         .append(", author=").append(author)
         .append(", characters=").append(characters)
         .append(']');
      return sb.toString();
   }

   public boolean equals(Object o)
   {
      if(this == o) return true;
      if(!(o instanceof Book)) return false;

      final Book book = (Book)o;

      if(author != null ? !author.equals(book.author) : book.author != null) return false;
      if(characters != null ? !characters.equals(book.characters) : book.characters != null) return false;
      if(isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
      if(title != null ? !title.equals(book.title) : book.title != null) return false;

      return true;
   }

   public int hashCode()
   {
      int result;
      result = (isbn != null ? isbn.hashCode() : 0);
      result = 29 * result + (title != null ? title.hashCode() : 0);
      result = 29 * result + (author != null ? author.hashCode() : 0);
      result = 29 * result + (characters != null ? characters.hashCode() : 0);
      return result;
   }
}
