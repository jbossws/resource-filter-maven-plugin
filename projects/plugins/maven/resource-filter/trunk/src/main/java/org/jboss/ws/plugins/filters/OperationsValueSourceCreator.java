/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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
package org.jboss.ws.plugins.filters;

import java.util.Properties;

import org.apache.maven.shared.filtering.MavenResourcesExecution;
import org.codehaus.plexus.interpolation.ValueSource;
import org.codehaus.plexus.logging.AbstractLogEnabled;

/**
 * 
 * @plexus.component role="org.jboss.ws.plugins.filters.OperationsValueSourceCreator"
 *                   role-hint="operations"
 */
public class OperationsValueSourceCreator extends AbstractLogEnabled implements ValueSourceCreator {

    @Override
    public ValueSource createValueSource(MavenResourcesExecution mavenResourcesExecution) {
        final Properties projectProperties = mavenResourcesExecution.getMavenProject().getProperties();

        OperationsValueSource valueSource = new OperationsValueSource(projectProperties);
        valueSource.enableLogging(this.getLogger());
        return valueSource;
    }

}