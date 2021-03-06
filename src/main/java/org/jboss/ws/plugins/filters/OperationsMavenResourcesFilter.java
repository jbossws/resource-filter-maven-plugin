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

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Resource;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;
import org.apache.maven.shared.filtering.FilteringUtils;
import org.apache.maven.shared.filtering.MavenFileFilter;
import org.apache.maven.shared.filtering.MavenFilteringException;
import org.apache.maven.shared.filtering.MavenResourcesExecution;
import org.apache.maven.shared.filtering.MavenResourcesFiltering;
import org.apache.maven.shared.filtering.MultiDelimiterInterpolatorFilterReaderLineEnding;
import org.codehaus.plexus.interpolation.InterpolationPostProcessor;
import org.codehaus.plexus.interpolation.PrefixAwareRecursionInterceptor;
import org.codehaus.plexus.interpolation.PrefixedObjectValueSource;
import org.codehaus.plexus.interpolation.RecursionInterceptor;
import org.codehaus.plexus.interpolation.SimpleRecursionInterceptor;
import org.codehaus.plexus.interpolation.SingleResponseValueSource;
import org.codehaus.plexus.interpolation.ValueSource;
import org.codehaus.plexus.interpolation.multi.MultiDelimiterStringSearchInterpolator;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.apache.maven.shared.utils.io.FileUtils;
import org.apache.maven.shared.utils.io.FileUtils.FilterWrapper;
import org.codehaus.plexus.util.PathTool;
import org.codehaus.plexus.util.ReaderFactory;
import org.codehaus.plexus.util.Scanner;
import org.codehaus.plexus.util.StringUtils;
import org.sonatype.plexus.build.incremental.BuildContext;

/**
 * 
 * @plexus.component role="org.apache.maven.shared.filtering.MavenResourcesFiltering"
 *                   role-hint="custom"
 */
public class OperationsMavenResourcesFilter extends AbstractLogEnabled implements MavenResourcesFiltering,
        Initializable {
    
    private static final String[] EMPTY_STRING_ARRAY = {};

    private static final String[] DEFAULT_INCLUDES = { "**/**" };

    /** @plexus.requirement */
    private BuildContext buildContext;
    
    /**
     * @plexus.requirement role-hint="default"
     */
    protected MavenResourcesFiltering defaultMavenResourcesFiltering;
    
    /**
     * @plexus.requirement role="org.jboss.ws.plugins.filters.OperationsValueSourceCreator"
     */
    private List<ValueSourceCreator> valueSourceCreators;

    
    // ------------------------------------------------
    //  Plexus lifecycle
    // ------------------------------------------------
    @Override
    public void initialize() throws InitializationException {
    }

    /**
     * @plexus.requirement
     *  role-hint="default"
     */
    private MavenFileFilter mavenFileFilter;

    @Override
    public boolean filteredFileExtension( String fileName, List<String> userNonFilteredFileExtensions ) {
        final List<String> nonFilteredFileExtensions = new ArrayList<String>(this.getDefaultNonFilteredFileExtensions());
        if (userNonFilteredFileExtensions != null) {
            nonFilteredFileExtensions.addAll(userNonFilteredFileExtensions);
        }
        final boolean filteredFileExtension = !nonFilteredFileExtensions.contains(StringUtils.lowerCase(FileUtils
                .extension(fileName)));
        if (this.getLogger().isDebugEnabled()) {
            this.getLogger().debug("file " + fileName + " has a" + (filteredFileExtension ? " " : " non ")
                    + "filtered file extension");
        }
        return filteredFileExtension;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getDefaultNonFilteredFileExtensions() {
        return this.defaultMavenResourcesFiltering.getDefaultNonFilteredFileExtensions();
    }

    @Override
    public void filterResources(final MavenResourcesExecution mavenResourcesExecution) throws MavenFilteringException {
        if (mavenResourcesExecution == null) {
            throw new MavenFilteringException("mavenResourcesExecution cannot be null");
        }

        if (mavenResourcesExecution.getResources() == null) {
            this.getLogger().info("No resources configured skip copying/filtering");
            return;
        }

        if (mavenResourcesExecution.getOutputDirectory() == null) {
            throw new MavenFilteringException("outputDirectory cannot be null");
        }

        if (mavenResourcesExecution.getEncoding() == null || mavenResourcesExecution.getEncoding().length() < 1) {
            this.getLogger().warn("Using platform encoding (" + ReaderFactory.FILE_ENCODING
                    + " actually) to copy filtered resources, i.e. build is platform dependent!");
        }
        else {
            this.getLogger().info("Using '" + mavenResourcesExecution.getEncoding()
                    + "' encoding to copy filtered resources.");
        }

        if (valueSourceCreators.size() == 0) {
            this.getLogger().warn("No ValueSourceCreators were defined, no additional filtering will be done");
        }
        
        //Create value sources
        final List<ValueSource> valueSources = new ArrayList<ValueSource>(valueSourceCreators.size());
        for (final ValueSourceCreator valueSourceCreator : this.valueSourceCreators) {
            final ValueSource valueSource = valueSourceCreator.createValueSource(mavenResourcesExecution);
            if (valueSource != null) {
                valueSources.add(valueSource);
            }
        }
        
        if (valueSources.size() == 0) {
            this.getLogger().info("No ValueSources were created, no additional filtering will be done");
        }
        
        final FilterWrapper filterWrapper = this.createFilterWrapper(mavenResourcesExecution, valueSources);
        final List<FilterWrapper> filterWrappers = Arrays.asList(filterWrapper);

        //For each resources directory ...
        for (final Resource resource : mavenResourcesExecution.getResources()) {
            
            if (!resource.isFiltering()) {
                //Only bother with filtered resources
                this.getLogger().debug("skip non filtered resourceDirectory " + resource.getDirectory());
                continue;
            }

            if (this.getLogger().isDebugEnabled()) {
                final String ls = System.getProperty("line.separator");
                final StringBuffer debugMessage = new StringBuffer("resource with targetPath "
                        + resource.getTargetPath()).append(ls);
                debugMessage.append("directory " + resource.getDirectory()).append(ls);
                debugMessage.append("excludes "
                        + (resource.getExcludes() == null ? " empty " : resource.getExcludes().toString())).append(ls);
                debugMessage.append("includes "
                        + (resource.getIncludes() == null ? " empty " : resource.getIncludes().toString()));
                this.getLogger().debug(debugMessage.toString());
            }

            final String targetPath = resource.getTargetPath();

            File resourceDirectory = new File(resource.getDirectory());

            if (!resourceDirectory.isAbsolute()) {
                resourceDirectory = new File(mavenResourcesExecution.getResourcesBaseDirectory(),
                        resourceDirectory.getPath());
            }

            if (!resourceDirectory.exists()) {
                this.getLogger().info("skip non existing resourceDirectory " + resourceDirectory.getPath());
                continue;
            }

            // this part is required in case the user specified "../something" as destination
            // see MNG-1345
            final File outputDirectory = mavenResourcesExecution.getOutputDirectory();
            final boolean outputExists = outputDirectory.exists();
            if (!outputExists && !outputDirectory.mkdirs()) {
                throw new MavenFilteringException("Cannot create resource output directory: " + outputDirectory);
            }

            final boolean ignoreDelta = !outputExists
                    || this.buildContext.hasDelta(mavenResourcesExecution.getFileFilters())
                    || this.buildContext.hasDelta(this.getRelativeOutputDirectory(mavenResourcesExecution));
            this.getLogger().debug("ignoreDelta " + ignoreDelta);
            Scanner scanner = this.buildContext.newScanner(resourceDirectory, ignoreDelta);

            this.setupScanner(resource, scanner);

            scanner.scan();

            final List<String> includedFiles = Arrays.asList(scanner.getIncludedFiles());

            this.getLogger().info("Copying " + includedFiles.size() + " resource"
                    + (includedFiles.size() > 1 ? "s" : "") + (targetPath == null ? "" : " to " + targetPath));

            for (final Iterator<String> j = includedFiles.iterator(); j.hasNext();) {
                final String name = j.next();

                final File source = new File(resourceDirectory, name);

                final File destinationFile = this.getDestinationFile(outputDirectory, targetPath, name);

                final boolean filteredExt = this.filteredFileExtension(source.getName(),
                        mavenResourcesExecution.getNonFilteredFileExtensions());
                
                if (filteredExt) {
                    //do property decrypting on already filtered resources by copy the already filtered resource
                    //to a temp file and then filtering that back into the dest
                    File tempFile = null;
                    try {
                        tempFile = File.createTempFile(destinationFile.getName(), ".tmp", destinationFile.getParentFile());
                        FileUtils.copyFile(destinationFile, tempFile);
                        
                        this.mavenFileFilter.copyFile(tempFile,
                                destinationFile,
                                true,
                                filterWrappers,
                                mavenResourcesExecution.getEncoding(),
                                true);
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    finally {
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                }
            }
        }

    }

    /**
     * Adds a new FilterWrapper that uses the specified value source
     */
    protected FileUtils.FilterWrapper createFilterWrapper(final MavenResourcesExecution mavenResourcesExecution, final List<ValueSource> valueSources) {
        return new FileUtils.FilterWrapper() {
            public Reader getReader(Reader reader) {
                MultiDelimiterStringSearchInterpolator interpolator = new MultiDelimiterStringSearchInterpolator();
                final LinkedHashSet<String> delimiters = mavenResourcesExecution.getDelimiters();
                interpolator.setDelimiterSpecs(delimiters);

                final List<String> projectStartExpressions = mavenResourcesExecution.getProjectStartExpressions();
                RecursionInterceptor ri = null;
                if (projectStartExpressions != null && !projectStartExpressions.isEmpty()) {
                    ri = new PrefixAwareRecursionInterceptor(projectStartExpressions, true);
                }
                else {
                    ri = new SimpleRecursionInterceptor();
                }

                for (final ValueSource valueSource : valueSources) {
                    interpolator.addValueSource(valueSource);
                }

                final MavenProject project = mavenResourcesExecution.getMavenProject();
                if (project != null) {
                    interpolator.addValueSource(new PrefixedObjectValueSource(projectStartExpressions, project, true));
                }

                final MavenSession mavenSession = mavenResourcesExecution.getMavenSession();
                if (mavenSession != null) {
                    interpolator.addValueSource(new PrefixedObjectValueSource("session", mavenSession));

                    final Settings settings = mavenSession.getSettings();
                    if (settings != null) {
                        interpolator.addValueSource(new PrefixedObjectValueSource("settings", settings));
                        interpolator.addValueSource(new SingleResponseValueSource("localRepository", settings
                                .getLocalRepository()));
                    }
                }

                final String escapeString = mavenResourcesExecution.getEscapeString();
                interpolator.setEscapeString(escapeString);

                final boolean escapeWindowsPaths = mavenResourcesExecution.isEscapeWindowsPaths();
                if (escapeWindowsPaths) {
                    interpolator.addPostProcessor(new InterpolationPostProcessor() {
                        public Object execute(String expression, Object value) {
                            if (value instanceof String) {
                                return FilteringUtils.escapeWindowsPath((String) value);
                            }

                            return value;
                        }
                    });
                }

                final boolean supportMultiLineFiltering = mavenResourcesExecution.isSupportMultiLineFiltering();
                MultiDelimiterInterpolatorFilterReaderLineEnding filterReader = new MultiDelimiterInterpolatorFilterReaderLineEnding(
                        reader, interpolator, supportMultiLineFiltering);
                filterReader.setRecursionInterceptor(ri);
                filterReader.setDelimiterSpecs(delimiters);

                filterReader.setInterpolateWithPrefixPattern(false);
                filterReader.setEscapeString(escapeString);

                return filterReader;
            }
        };
    }

    private File getDestinationFile(File outputDirectory, String targetPath, String name) {
        String destination = name;

        if (targetPath != null) {
            destination = targetPath + "/" + name;
        }

        File destinationFile = new File(destination);
        if (!destinationFile.isAbsolute()) {
            destinationFile = new File(outputDirectory, destination);
        }

        if (!destinationFile.getParentFile().exists()) {
            destinationFile.getParentFile().mkdirs();
        }
        return destinationFile;
    }

    @SuppressWarnings("unchecked")
    private String[] setupScanner(Resource resource, Scanner scanner) {
        String[] includes = null;
        if (resource.getIncludes() != null && !resource.getIncludes().isEmpty()) {
            includes = (String[]) resource.getIncludes().toArray(EMPTY_STRING_ARRAY);
        }
        else {
            includes = DEFAULT_INCLUDES;
        }
        scanner.setIncludes(includes);

        String[] excludes = null;
        if (resource.getExcludes() != null && !resource.getExcludes().isEmpty()) {
            excludes = (String[]) resource.getExcludes().toArray(EMPTY_STRING_ARRAY);
            scanner.setExcludes(excludes);
        }

        scanner.addDefaultExcludes();
        return includes;
    }

    private String getRelativeOutputDirectory(MavenResourcesExecution execution) {
        String relOutDir = execution.getOutputDirectory().getAbsolutePath();

        if (execution.getMavenProject() != null && execution.getMavenProject().getBasedir() != null) {
            final String basedir = execution.getMavenProject().getBasedir().getAbsolutePath();
            relOutDir = PathTool.getRelativeFilePath(basedir, relOutDir);
            if (relOutDir == null) {
                relOutDir = execution.getOutputDirectory().getPath();
            }
            else {
                relOutDir = relOutDir.replace('\\', '/');
            }
        }

        return relOutDir;
    }

}
