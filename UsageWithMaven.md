# Usage with Maven #

For the core you need two dependencies unless a good assembly is created:

```
        <dependency>
            <groupId>org.mili.jusuals.core.api</groupId>
            <artifactId>jusuals-core-api</artifactId>
            <version>VERSION</version>
        </dependency>
        <dependency>
            <groupId>org.mili.jusuals.core.impl</groupId>
            <artifactId>jusuals-core-impl</artifactId>
            <version>VERSION</version>
        </dependency>
```

For test you need one dependency:
```
        <dependency>
            <groupId>org.mili.jusuals.test</groupId>
            <artifactId>jusuals-test</artifactId>
            <version>VERSION</version>
            <scope>test</scope>
        </dependency>
```