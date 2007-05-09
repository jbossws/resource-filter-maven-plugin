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

/**
 * BookCharacter class that represents the character element in XML content.
 *
 * @version <tt>$Revision: 1.2 $</tt>
 * @author <a href="mailto:alex@jboss.org">Alexey Loubyansky</a>
 */
public class BookCharacter
{
   private String name;
   private String friendOf;
   private String since;
   private String qualification;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getFriendOf()
   {
      return friendOf;
   }

   public void setFriendOf(String friendOf)
   {
      this.friendOf = friendOf;
   }

   public String getSince()
   {
      return since;
   }

   public void setSince(String since)
   {
      this.since = since;
   }

   public String getQualification()
   {
      return qualification;
   }

   public void setQualification(String qualification)
   {
      this.qualification = qualification;
   }

   public boolean equals(Object o)
   {
      if(this == o) return true;
      if(!(o instanceof BookCharacter)) return false;

      final BookCharacter bookCharacter = (BookCharacter)o;

      if(friendOf != null ? !friendOf.equals(bookCharacter.friendOf) : bookCharacter.friendOf != null) return false;
      if(name != null ? !name.equals(bookCharacter.name) : bookCharacter.name != null) return false;
      if(qualification != null ? !qualification.equals(bookCharacter.qualification) : bookCharacter.qualification != null) return false;
      if(since != null ? !since.equals(bookCharacter.since) : bookCharacter.since != null) return false;

      return true;
   }

   public int hashCode()
   {
      int result;
      result = (name != null ? name.hashCode() : 0);
      result = 29 * result + (friendOf != null ? friendOf.hashCode() : 0);
      result = 29 * result + (since != null ? since.hashCode() : 0);
      result = 29 * result + (qualification != null ? qualification.hashCode() : 0);
      return result;
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer(50);
      sb.append('[')
         .append("name=").append(name)
         .append(", friend-of=").append(friendOf)
         .append(", since=").append(since)
         .append(", qualification=").append(qualification)
         .append(']');
      return sb.toString();
   }
}
