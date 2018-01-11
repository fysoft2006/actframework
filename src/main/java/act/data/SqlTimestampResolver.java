package act.data;

/*-
 * #%L
 * ACT Framework
 * %%
 * Copyright (C) 2014 - 2017 ActFramework
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import act.conf.AppConfig;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;

@Singleton
public class SqlTimestampResolver extends DateResolverBase<Timestamp> {

    @Inject
    public SqlTimestampResolver(AppConfig config) {
        super(config);
    }

    public SqlTimestampResolver(String pattern) {
        super(pattern);
    }

    @Override
    protected String configuredPattern(AppConfig config) {
        return config.dateTimeFormat();
    }

    @Override
    protected Timestamp cast(java.util.Date date) {
        return new Timestamp(date.getTime());
    }

}
