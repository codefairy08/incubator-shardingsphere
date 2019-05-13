/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.core.rewrite.placeholder;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Encrypt update item column placeholder for rewrite.
 *
 * @author panjuan
 */
@RequiredArgsConstructor
@Getter
public final class EncryptUpdateItemColumnPlaceholder implements ShardingPlaceholder {
    
    private final String columnName;
    
    private final Comparable<?> columnValue;
    
    private final String assistedColumnName;
    
    private final Comparable<?> assistedColumnValue;
    
    private final int parameterMarkerIndex;
    
    public EncryptUpdateItemColumnPlaceholder(final String columnName) {
        this(columnName, null, null, null, 0);
    }
    
    public EncryptUpdateItemColumnPlaceholder(final String columnName, final Comparable<?> columnValue) {
        this(columnName, columnValue, null, null, -1);
    }
    
    public EncryptUpdateItemColumnPlaceholder(final String columnName, final String assistedColumnName) {
        this(columnName, null, assistedColumnName, null, 0);
    }
    
    public EncryptUpdateItemColumnPlaceholder(final String columnName,
                                              final Comparable<?> columnValue, final String assistedColumnName, final Comparable<?> assistedColumnValue) {
        this(columnName, columnValue, assistedColumnName, assistedColumnValue, -1);
    }
    
    @Override
    @SuppressWarnings("all")
    public String toString() {
        if (Strings.isNullOrEmpty(assistedColumnName)) {
            return -1 != parameterMarkerIndex ? String.format("%s = ?", columnName) : String.format("%s = %s", columnName, toStringForColumnValue(columnValue));
        }
        return -1 != parameterMarkerIndex ? String.format("%s = ?, %s = ?", columnName, assistedColumnName) 
                : String.format("%s = %s, %s = %s", columnName, toStringForColumnValue(columnValue), assistedColumnName, toStringForColumnValue(assistedColumnValue));
    }
    
    private String toStringForColumnValue(final Comparable<?> columnValue) {
        return String.class == columnValue.getClass() ? String.format("'%s'", columnValue) : columnValue.toString();
    }
}
