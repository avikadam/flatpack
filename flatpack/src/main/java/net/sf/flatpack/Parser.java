/*
 * ObjectLab, http://www.objectlab.co.uk/open is supporting FlatPack.
 *
 * Based in London, we are world leaders in the design and development
 * of bespoke applications for the securities financing markets.
 *
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more</a>
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/
 *
 *                     www.ObjectLab.co.uk
 *
 * $Id: ColorProvider.java 74 2006-10-24 22:19:05Z benoitx $
 *
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.sf.flatpack;

import java.util.stream.Stream;

/**
 * PZParser is ready to parse the data and return an object that can then be
 * traversed. The default parser should NOT handle short lines, the user can
 * change it prior to calling parse.
 *
 * @author Benoit Xhenseval
 * @author Paul Zepernick
 */
public interface Parser {

    /**
     * Start the parsing. Will return "null" if the parse fails and the DataSet
     * cannot be created
     *
     * @return the data set resulting from parsing
     */
    DataSet parse();

    /**
     * Parse the data and return an interface where one can extract one record at a time, until
     * next returns false;
     * @since 3.4
     * @return a stream of Dataset
     */
    StreamingDataSet parseAsStream();

    /**
     * Parse the data and return a stream or records;
     * @since 4.0
     * @return a stream of Record
     */
    Stream<Record> stream();

    /**
     * @return true, lines with less columns then the amount of column headers
     *         will be added as empty's instead of producing an error
     */
    boolean isHandlingShortLines();

    /**
     * @param handleShortLines -
     *            when flagged as true, lines with less columns then the amount
     *            of column headers will be added as empty's instead of
     *            producing an error
     * @return the Parser
     */
    Parser setHandlingShortLines(boolean handleShortLines);

    /**
     * @param addSuffixToDuplicateColumnNames
     *          when true, add a count to duplicate colum names. eg the second column called "Asset" will become "Asset2".
     * @return the Parser
     */
    Parser setAddSuffixToDuplicateColumnNames(boolean addSuffixToDuplicateColumnNames);

    /**
     *
     * @return true, detail lines with a length or column count &gt; the mapping
     *         definition will be truncated and the reader will NOT register
     *         these lines as errors in the DataError collection.
     */
    boolean isIgnoreExtraColumns();

    /**
     *
     * @param ignoreExtraColumns
     *            when true, detail lines with a length or column count &gt; the
     *            mapping definition will be truncated and the reader will NOT
     *            register these lines as errors in the DataError collection.
     * @return the Parser
     */
    Parser setIgnoreExtraColumns(boolean ignoreExtraColumns);

    /**
     * Defaults to <code>true</code>.
     * @return true, the parser will preserve leading whitespace in each column when splitting a line
     */
    boolean isPreserveLeadingWhitespace();

    /**
     * Defaults to <code>true</code>.
     * @param preserveLeadingWhitespace
     *            when true, the parser will preserve leading whitespace in each column when splitting a line
     * @return the Parser
     */
    Parser setPreserveLeadingWhitespace(boolean preserveLeadingWhitespace);

    /**
     * Defaults to <code>false</code>.
     * @return true, the parser will preserve trailing whitespace in each column when splitting a line
     */
    boolean isPreserveTrailingWhitespace();

    /**
     * Defaults to <code>false</code>.
     * @param preserveTrailingWhitespace
     *            when true, the parser will preserve trailing whitespace in each column when splitting a line
      * @return the Parser
    */
    Parser setPreserveTrailingWhitespace(boolean preserveTrailingWhitespace);

    /**
     * Default is false
     *
     * @return true, column names will have to be an exact match when retrieving
     *         the value of a column. Example when true: Column name =
     *         AColumnName ; getString("acolumnname") would fail Example when
     *         false: Column name = AColumnName ; getString("acolumnname") would
     *         pass
     */
    boolean isColumnNamesCaseSensitive();

    /**
     * Default is false
     *
     * @param columnNamesCaseSensitive
     *            when true, column names will have to be an exact match when
     *            retrieving the value of a column. Example when true: Column
     *            name = AColumnName ; getString("acolumnname") would fail
     *            Example when false: Column name = AColumnName ;
     *            getString("acolumnname") would pass
     * @return the Parser
    */
    Parser setColumnNamesCaseSensitive(boolean columnNamesCaseSensitive);

    /**
     * Default is false
     *
     * @return true, warnings encountered during parsing will not be included in
     *         the DataSet errors
     */
    boolean isIgnoreParseWarnings();

    /**
     *
     * @param ignoreParseWarnings
     *            when true, warnings encountered during parsing will not be
     *            included in the DataSet errors
     * @return the Parser
     */
    Parser setIgnoreParseWarnings(boolean ignoreParseWarnings);

    /**
     *
     * @return true, empty Strings will get returned as NULL when calling
     *         DataSet.getString()
     */
    boolean isNullEmptyStrings();

    /**
     *
     * @param nullEmptyStrings
     *            when true, empty Strings will get returned as NULL when
     *            calling DataSet.getString()
     * @return the Parser
     */
    Parser setNullEmptyStrings(boolean nullEmptyStrings);

    /**
     *
     * @return flagEmptyRows when true, will analyze the row to see if all
     *         elements are empty and place a flag on the DataSet indicating if
     *         the row is empty
     */
    boolean isFlagEmptyRows();

    /**
     * when true, will analyze the row to see if all elements are empty and
     * place a flag on the DataSet indicating if the row is empty.  This will slow
     * down the parse and should only be used when necessary.  It is off by default.
     *
     * @param flagEmptyRows true if we need to flag empty rows
     * @return the Parser
     */
    Parser setFlagEmptyRows(boolean flagEmptyRows);

    /**
     * @return when true, the parser will place the data of the line which failed the parse and
     * place it into the DataError object.  DataError.getRawData() can be called to retrieve
     * the line.
     */
    boolean isStoreRawDataToDataError();

    /**
     * when true, the parser will place the data of the line which failed the parse and
     * place it into the DataError object.  DataError.getRawData() can be called to retrieve
     * the line.
     *
     * @param storeRawDataToDataError true if we need to store each lines
     * @return the Parser
     */
    Parser setStoreRawDataToDataError(boolean storeRawDataToDataError);

    /**
     * @return when true, the parser will place the data of the line into the DataSet object.
     * DataSet.getRawData() can be called to retrieve the line.
     */
    boolean isStoreRawDataToDataSet();

    /**
     * WARNING!!  Setting this option has potential to cause high memory usage.
     *
     * when true, the parser will place the data of the line into the DataSet object.
     * DataSet.getRawData() can be called to retrieve the line.
     *
     * @param storeRawDataToDataError true if we need to store the raw data
     * @return the Parser
     */
    Parser setStoreRawDataToDataSet(boolean storeRawDataToDataError);

    /**
     * Returns the table name that will be used to read the MetaData from the db.  The
     * default table name is DATAFILE.  This may be problimatic for some who are using case
     * sensistive db table names or who wish to provide a different table name in the db.
     *
     * This is only applicable when using a database for file mappings.
     *
     * @return the DATAFILE table name
     */
    String getDataFileTable();

    /**
     * Sets the table name that will be used to read the MetaData from the db.  The
     * default table name is DATAFILE.  This may be problimatic for some who are using case
     * sensistive db table names or who wish to provide a different table name in the db.
     *
     * This is only applicable when using a database for file mappings.
     *
     * @param dataFileTable
     * 			Name of the table name to use in place of "DATAFILE"
     * @return the Parser
     */
    Parser setDataFileTable(String dataFileTable);

    /**
     * Returns the table name that will be used to read the MetaData from the db.  The
     * default table name is DATASTRUCTURE.  This may be problimatic for some who are using case
     * sensistive db table names or who wish to provide a different table name in the db.
     *
     * This is only applicable when using a database for file mappings.
     *
     * @return the DATASTRUCTURE table name
     */
    String getDataStructureTable();

    /**
     * Sets the table name that will be used to read the MetaData from the db.  The
     * default table name is DATASTRUCTURE.  This may be problimatic for some who are using case
     * sensistive db table names or who wish to provide a different table name in the db.
     *
     * This is only applicable when using a database for file mappings.
     *
     * @param dataStructureTable
     * 			Name of the table name to us in placfe of "DATASTRUCTURE"
     *
     * @return the Parser
     */
    Parser setDataStructureTable(String dataStructureTable);
}
