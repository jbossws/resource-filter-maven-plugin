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

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.codehaus.plexus.interpolation.ValueSource;
import org.codehaus.plexus.logging.AbstractLogEnabled;

public class OperationsValueSource extends AbstractLogEnabled implements ValueSource {
	
	private static final String ADD_INT = "add_int";
	private Properties properties;
	
	public OperationsValueSource(Properties props) {
		this.properties = props;
	}

	@Override
	public Object getValue(String expression) {
		if (expression.startsWith(ADD_INT + "(") && expression.endsWith(")")) {
			expression = expression.substring(ADD_INT.length() + 1, expression.length() - 1);
			StringTokenizer st = new StringTokenizer(expression, ",", false);
			int sum = 0;
			while (st.hasMoreTokens()) {
				final String token = st.nextToken();
				sum = sum + get(token);
			}
			return sum;
		}
		return null;
	}
	
	private int get(String token) {
		String spv = System.getProperty(token);
		if (spv != null) {
			return Integer.valueOf(spv);
		} else {
			return Integer.valueOf(properties.getProperty(token, token));
		}
	}
	
	@Override
	public void clearFeedback() {
		// NOOP
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getFeedback() {
		return Collections.EMPTY_LIST;
	}
}
