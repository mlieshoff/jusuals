# How to deploy to Nexus? #

Normally the artifacts are in the org.mili-repository but Nexus has some problems with such repositories.

Just replace the distributionManagement section in the jusuals/pom.xml to deploy to your own Nexus:

```
<distributionManagement>
    <repository>
        <id>nexus</id>
        <name>My Nexus Repo Name</name>
        <url>http://myserver:9091/nexus/content/repositories/mythirdpartylibs</url>
    </repository>
</distributionManagement>
```

then run Maven to deploy:

```
mvn deploy
```